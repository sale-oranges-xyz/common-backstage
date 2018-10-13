package com.github.geng.service;

import com.github.geng.dto.BaseForm;
import com.github.geng.exception.BizException;

/**
 * service 基础增删改查类
 * @param <T> 数据类型
 * @param <ID> 主键类型
 */
public interface CRUDService<T, ID> {

    /**
     * 新增操作
     * @param baseForm 表单数据
     * @return 保存数据
     */
    T save(BaseForm baseForm) throws BizException;

    /**
     * 修改数据
     * @param rowId 记录id
     * @param baseForm 表单数据
     * @return true 修改成功 | false 修改失败
     */
    boolean update(ID rowId, BaseForm baseForm) throws BizException;

    /**
     * 查找数据
     * @param rowId 记录id
     * @return 记录数据
     */
    T get(ID rowId);

    /**
     * 删除数据
     * @param rowId 记录id
     * @return true 删除成功 | false 删除失败
     */
    boolean delete(ID rowId) throws BizException;
}
