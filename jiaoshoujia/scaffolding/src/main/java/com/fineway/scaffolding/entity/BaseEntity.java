package com.fineway.scaffolding.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "gmt_create")
    private Timestamp gmtCreate;
    @Column(name = "gmt_modify")
    @JSONField(serialize = false)
    private Timestamp gmtModify;
    @Column(name = "is_deleted")
    @JSONField(serialize = false)
    private int       isDeleted;
    @Column(name = "version")
    @JSONField(serialize = false)
    @Version
    private long      version;

    public BaseEntity() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        setGmtCreate(now);
        setIsDeleted(0);
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @PreUpdate
    public void updateTimestamp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        setGmtModify(now);
    }


    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Timestamp gmtModify) {
        this.gmtModify = gmtModify;
    }

    public long getVersion() {
        return version;
    }

    private void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", isDeleted=" + isDeleted +
                ", version=" + version +
                '}';
    }
}
