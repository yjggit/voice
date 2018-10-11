package com.example.voice.vo;

/**
 * @author jianhui.Yang
 * @version $Id VoiceAIVO.java, v 0.1 2018-10-11 15:33 jianhui.Yang Exp $$
 */
public class VoiceAIVO {


    private String appId;

    private String apiKey;

    private String secretKey;

    private String aiUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAiUrl() {
        return aiUrl;
    }

    public void setAiUrl(String aiUrl) {
        this.aiUrl = aiUrl;
    }
}
