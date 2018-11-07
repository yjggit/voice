package com.example.voice.vo;

/**
 * @author jianhui.Yang
 * @version $Id SendMailVO.java, v 0.1 2018-11-07 17:32 jianhui.Yang Exp $$
 */
public class SendMailVO {

    private String id;

    private String email;

    private String remark;

    private String gmtCreate;

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
