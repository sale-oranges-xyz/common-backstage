package com.github.geng.jpa;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@NoRepositoryBean
public class SimpleJpaRepositoryImpl<M, ID extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * 分页查询
     * @param pageable 分页查询参数
     * @param queryStatement 查询语句
     * @param values 数量可变的参数,按顺序绑定
     * @return 分页数据
     */
    protected <M> Page<M> findAll(final Pageable pageable, final String queryStatement, final Object[] values) {
        return this.findAll(pageable, queryStatement, "", values);
    }

    /**
     * 查询指定条件的纪录
     * @param queryStatement 查询语句
     * @param firstResult 返回值的第一条
     * @param maxResult 最多返回的条码
     * @param values 数量可变的参数,按顺序绑定
     * @return 符合条件纪录列表
     */
    @SuppressWarnings("rawtypes")
    public <X> List<X> findAll(final String queryStatement, int firstResult, int maxResult, final Object[] values) {
        log.debug("firstResult = {}", firstResult);
        log.debug("maxResult = {}", maxResult);

        Query query = this.createQuery(queryStatement, values);
        if (firstResult > 0)
            query.setFirstResult(firstResult);
        if (maxResult > 0)
            query.setMaxResults(maxResult);
        return query.getResultList();
    }

    /**
     * 分页查询
     * @param pageable 分页查询参数
     * @param queryStatement 查询语句
     * @param aliasName 所要查询对象的别名
     * @param values 数量可变的参数,按顺序绑定
     * @return 分页数据
     */
    protected <M> Page<M> findAll(final Pageable pageable, final String queryStatement, final String aliasName, final Object[] values) {
        // 获取符合条件的纪录总数
        long total = this.count(queryStatement, values);
        if (total == 0 || pageable.getOffset() > total) {
            return new PageImpl(Collections.EMPTY_LIST, pageable, total);
        }

        // 处理排序字段
        StringBuilder pageQueryStatement = new StringBuilder(queryStatement);
        if (null != pageable.getSort()) {
            List<Sort.Order> orders = Lists.newArrayList(pageable.getSort().iterator());

            if (orders.size() > 0) {
                pageQueryStatement.append(" order by ");
                int orderIdx = 0;
                int orderSize = orders.size();
                for (Sort.Order order : orders) {
                    if(orderSize > 1 && orderIdx > 0)
                        pageQueryStatement.append(",");
                    if(StringUtils.hasText(aliasName))
                        pageQueryStatement.append(" ").append(String.format("%s %s", aliasName + "." + order.getProperty(), order.getDirection()));
                    else
                        pageQueryStatement.append(" ").append(String.format("%s %s", order.getProperty(), order.getDirection()));
                    orderIdx++;
                }
            }
        }
        List<M> content = this.findAll(pageQueryStatement.toString(), pageable.getOffset(), pageable.getPageSize(), values);
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 查询符合条件纪录的总数
     * @param queryStatement 查询语句
     * @param values 数量可变的参数,按顺序绑定
     * @return 返回符合条件总的数目
     */
    protected long count(final String queryStatement, final Object[] values) {
        String tmpQueryStatement = this.removeOrders(queryStatement);
        if (!tmpQueryStatement.startsWith("select")) {
            tmpQueryStatement = "select count(*) " + tmpQueryStatement;
        } else {
            tmpQueryStatement = "select count(*) " + this.removeSelects(tmpQueryStatement);
        }

        Long count = (Long)(this.createQuery(tmpQueryStatement, values)).getSingleResult();
        log.debug("符合条件的记录数为: {}", count);
        return null == count ? 0 : count;
    }

    /**
     * 剔除查询语句中的选择语句
     * @param queryStatement 查询的语句
     * @return 如果存在选择语句，则将选择语句剔除，否则返回原查询语句
     */
    protected String removeSelects(String queryStatement) {
        Assert.hasText(queryStatement, "查询语句不能够为空");
        int beginPos = queryStatement.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + queryStatement + " must has a keyword 'from'");
        return queryStatement.substring(beginPos);
    }

    /**
     * 剔除查询语句中的排序语句
     * @param queryStatement 查询的语句
     * @return 如果存在排序语句，则将排序语句剔除，否则返回原查询语句
     */
    protected String removeOrders(String queryStatement) {
        Assert.hasText(queryStatement, "查询语句不能够为空");
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(queryStatement);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 创建Query对象
     * @param queryStatement 查询语句
     * @param values 数量可变的参数,按顺序绑定
     * @return 构建好的查询对象
     */
    protected Query createQuery(final String queryStatement, final Object[] values) {
        Assert.hasText(queryStatement, "查询语句不能够为空");
        log.debug("query statement = {}", queryStatement);

        Query query = getEntityManager().createQuery(queryStatement);
        if (values != null && values.length > 0) {
            log.debug("params =============================================");

            for (int i = 0; i < values.length; i++) {
                log.debug("    param_{} = {}", i, values[i]);
                query.setParameter(i + 1, values[i]);
            }
        } else {
            log.debug("no params");
        }
        return query;
    }


    // append 部分 --------------------------------------------------------------------------------
    protected void appendLike(StringBuilder queryStatement, String aliasName, String fieldName, Object value, List<Object> params) {
        if (StringUtils.hasText(aliasName)) {
            queryStatement.append(String.format(" and %s.%s like ? ", aliasName, fieldName));
        } else {
            queryStatement.append(String.format(" and %s like ? ", fieldName));
        }
        params.add(" % " + value + "%");
    }

    protected void appendAnd(StringBuilder queryStatement, String aliasName, String fieldName, Object value, List<Object> params) {
        if (StringUtils.hasText(aliasName)) {
            queryStatement.append(String.format(" and %s.%s = ? ", aliasName, fieldName));
        } else {
            queryStatement.append(String.format(" and %s = ? ", fieldName));
        }
        params.add(value);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
