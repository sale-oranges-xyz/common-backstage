package com.github.geng.dto;

import com.github.geng.entity.BaseLongIdEntity;
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
public class DtoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    // ==========================================================
    // constructor
    public DtoEntity(BaseLongIdEntity baseLongIdEntity) {
        super();
        this.id = IdEncryptUtils.encode(baseLongIdEntity.getId());
    }

    public DtoEntity() {
        super();
    }

}
