package com.example.voice.controller;

import com.example.voice.service.impl.VoiceAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author jianhui.Yang
 * @version $Id VoiceAIController.java, v 0.1 2018-10-11 15:43 jianhui.Yang Exp $$
 */

@RestController
@RequestMapping("/voiceAI")
public class VoiceAIController {

    @Autowired
    private VoiceAIService voiceAIService;

    @RequestMapping(value = "/upVoiceAI",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String speechRecognition (String mp3filepath) throws Exception {
        if (StringUtils.isEmpty(mp3filepath)) {
            throw new Exception("mp3文件为空");
        }
        String res  = voiceAIService.voiceAI(mp3filepath);
        return res;
    }

}
