package com.github.geng.admin.entity;

import com.github.geng.util.IdEncryptUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author geng
 */
@MappedSuperclass
@Setter
@Getter
public class BaseDtoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    // ==========================================================
    // constructor
    public BaseDtoEntity(BaseLongIdEntity baseLongIdEntity) {
        super();
        this.id = IdEncryptUtils.encode(baseLongIdEntity.getId());
    }

    public BaseDtoEntity() {
        super();
    }

}
