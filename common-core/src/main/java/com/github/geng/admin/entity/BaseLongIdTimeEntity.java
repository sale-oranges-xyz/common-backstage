package com.github.geng.admin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 具有创建时间与修改时间的实体类父类
 * @author geng
 */
@Setter
@Getter
@MappedSuperclass
public class BaseLongIdTimeEntity extends BaseLongIdEntity {
    private static final long serialVersionUID = 1L;

    private Date createdTime;
    private Date modifiedTime;

    // ----------------------------------------------------
    // constructors
    public BaseLongIdTimeEntity() {
        super();
    }
    public BaseLongIdTimeEntity(Long id) {
        super(id);
    }

}
