package com.example.voice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jianhui.Yang
 * @version $Id LoggingEventProperty.java, v 0.1 2018-11-08 11:09 jianhui.Yang Exp $$
 */

@Entity
@Table(name = "logging_event_property")
public class LoggingEventProperty implements Serializable {

    private static final long serialVersionUID = 24L;

    @Id
    @Column(name = "event_id",columnDefinition = "BIGINT NOT NULL  COMMENT 'PK' ")
    private int eventId;

    @Id
    @Column(name = "mapped_key",columnDefinition = "VARCHAR(254) NOT NULL COMMENT 'PK' ")
    private String mappedKey;

    @Column(name = "mapped_value",columnDefinition = "TEXT COMMENT '匹配值，一般是主机名称  如：john-PC' ")
    private String mappedValue;

    @Column(name = "gmt_create",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '日志产生时间' ")
    private Date gmtCreate;


    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getMappedKey() {
        return mappedKey;
    }

    public void setMappedKey(String mappedKey) {
        this.mappedKey = mappedKey;
    }

    public String getMappedValue() {
        return mappedValue;
    }

    public void setMappedValue(String mappedValue) {
        this.mappedValue = mappedValue;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
