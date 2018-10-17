package com.example.voice.vo;

/**
 * @author jianhui.Yang
 * @version $Id VoiceTTSVO.java, v 0.1 2018-10-17 15:36 jianhui.Yang Exp $$
 */
public class VoiceTTSVO {

    private String secretId;

    private String secretKey;

    private String engSerViceType;

    private String sourceType;

    private String voiceFormat;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEngSerViceType() {
        return engSerViceType;
    }

    public void setEngSerViceType(String engSerViceType) {
        this.engSerViceType = engSerViceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getVoiceFormat() {
        return voiceFormat;
    }

    public void setVoiceFormat(String voiceFormat) {
        this.voiceFormat = voiceFormat;
    }
}
