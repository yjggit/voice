package com.example.voice.controller;

import com.example.voice.service.impl.VoiceAIService;
import com.example.voice.service.impl.VoiceTTSService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Autowired
    private VoiceTTSService voiceTTSService;


    @ApiOperation(value = "百度语音识别接口",notes = "根据插入的MP3文件转换成文字")
    @ApiImplicitParam(name = "mp3filepath" ,value = "mp3路径", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/upVoiceAI",method = {RequestMethod.POST,RequestMethod.GET},produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String speechRecognition (String mp3filepath) throws Exception {
        if (StringUtils.isEmpty(mp3filepath)) {
            throw new Exception("mp3文件为空");
        }
        String res  = voiceAIService.voiceAI(mp3filepath);
        return res;
    }

    @ApiOperation(value = "腾讯云语音识别接口",notes = "根据插入的MP3文件转换成文字")
    @ApiImplicitParam(name = "fileURL" ,value = "mp3路径", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/upVoice",method = {RequestMethod.POST,RequestMethod.GET}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String voiceToWord (String fileURL) throws Exception {
        if (StringUtils.isEmpty(fileURL)) {
            throw new Exception("mp3文件为空");
        }
        String result = voiceTTSService.voiceToWord(fileURL);
        return result;
    }


}
