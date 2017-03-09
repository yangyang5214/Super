package com.springboot.common.entity;


import com.springboot.common.listener.EntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners({EntityListener.class})
public class BaseEntity {

    @Column(name = "CREATION_TIME")
    private Date creationTime;

    @Column(name = "LAST_UPDATE_TIME")
    private Date lastUpdateTime;

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}


