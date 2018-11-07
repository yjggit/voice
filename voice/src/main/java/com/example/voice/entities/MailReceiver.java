package com.example.voice.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author jianhui.Yang
 * @version $Id MailReceiver.java, v 0.1 2018-11-07 11:00 jianhui.Yang Exp $$
 */
@Entity
@Table(name = "mail_receiver")
public class MailReceiver implements Serializable {

    private static final long serialVersionUID = 24L;

    @Id
    @Column(columnDefinition = "varchar(100) NOT NULL COMMENT 'PK' ")
    private String id;

    @Column(name = "email",columnDefinition = "varchar(100) NOT NULL COMMENT '邮箱' ")
    private String email;

    @Column(name = "remark",columnDefinition = "varchar(100) COMMENT '备注' ")
    private String remark;
    @Column(name = "gmt_create",columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间' ")
    private String gmtCreate;
    @Column(name = "gmt_update",columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间' ")
    private String gmtUpdate;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(String gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}
