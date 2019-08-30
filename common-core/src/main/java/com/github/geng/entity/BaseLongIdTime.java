package com.github.geng.entity;

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
public class BaseLongIdTime extends BaseLongId {
    private static final long serialVersionUID = 1L;

    private Date createdTime;               // 创建时间
    private Date modifiedTime;              // 修改时间

    // ----------------------------------------------------
    // constructors
    public BaseLongIdTime() {
        super();
    }
    public BaseLongIdTime(Long id) {
        super(id);
    }
    public BaseLongIdTime(Date date) {
        this.createdTime = this.modifiedTime = date;
    }
}
