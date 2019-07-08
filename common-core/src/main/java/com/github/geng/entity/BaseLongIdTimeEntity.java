package com.github.geng.entity;

import java.util.Date;

public interface BaseLongIdTimeEntity extends BaseLongIdEntity {

    Date getCreatedTime();

    Date getModifiedTime();
}
