package com.github.geng.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.geng.entity.BaseLongIdEntity;
import com.github.geng.util.IdEncryptUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.MappedSuperclass;

/**
 * @author geng
 */
@MappedSuperclass
@Setter
@Getter
public class DtoEntity extends BaseDto {

    private String id;

    // =====================================================================
    // constructor
    public DtoEntity(BaseLongIdEntity baseLongIdEntity) {
        super();
        this.id = IdEncryptUtils.encode(baseLongIdEntity.getId());
    }

    public DtoEntity() {
        super();
    }

    // ======================================================================
    @JsonIgnore
    public boolean isNew() {
        return DtoEntity.checkIsNew(this.id);
    }

    public Long encodeId() {
        return IdEncryptUtils.decode(this.id);
    }

    /**
     * 判断是否新增记录
     * @param id id
     * @return true 是 | false 不是
     */
    public static boolean checkIsNew(String id) {
        return StringUtils.hasText(id) && ("".equals(id) || "-1".equals(id));
    }
}
