package com.github.geng.admin.entity;


import com.github.geng.util.IdEncryptUtils;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author geng
 */
@Data
@MappedSuperclass
public class BaseLongIdEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // id 生成策略 参考 https://blog.csdn.net/u011781521/article/details/72210980
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 判断是否新增数据
     * @return true 新增 | false 不是
     */
    @Transient
    public boolean isNewRaw() {
        if (null == id || id == -1L) {
            return true;
        }
        return false;
    }

    @Transient
    public String getHashId() {
        if (this.isNewRaw()) {
            return "";
        }
        return IdEncryptUtils.encode(this.id);
    }
    // ----------------------------------------------------
    // constructors
    public BaseLongIdEntity() {
        super();
    }
    public BaseLongIdEntity(Long id) {
        super();
        this.id = id;
    }
}
