package com.example.voice.service.impl;

import com.baidu.aip.speech.AipSpeech;
import com.example.voice.mapper.VoiceAIMapper;
import com.example.voice.vo.VoiceAIVO;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.*;

/**
 * @author jianhui.Yang
 * @version $Id VoiceAIService.java, v 0.1 2018-10-11 16:52 jianhui.Yang Exp $$
 */
@Service
public class VoiceAIService {

    private static Logger logger = LoggerFactory.getLogger(VoiceAIService.class);

    public static AipSpeech client;

    @Autowired
    private VoiceAIMapper voiceAIMapper;

    public static AipSpeech getInstance(String appId, String apiKey,String secretKey){
        if (client == null) {
            synchronized (AipSpeech.class) {
                if (client == null) {
                    client = new AipSpeech(appId,apiKey,secretKey);
                }
            }
        }
        return client;
    }


    public String speechRecognition (String videoPath,String videoType,String appId,String apiKey,String secretKey) {
        client = getInstance(appId,apiKey,secretKey);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        JSONObject res = client.asr(videoPath,videoType,16000,null);
        logger.info(" ==SpeechRecognition " + res.toString());
        return res.toString();
    }

    public boolean convertMP32Pcm (String mp3filepath,String pcmfilepath) {
        String mp3 = UUID.randomUUID().toString();
        //Windows
        String cmdDos = "D:\\tools\\ffmpeg-20181007-0a41a8b-win64-static\\bin\\ffmpeg -i "+mp3filepath+ " -vn  -acodec libmp3lame -ac 1 -qscale:a 4 -ar 16000  "+ "D:\\tools\\"+ mp3+".mp3";
        //linux
        //        String linuxTerminal = "/crm/ffmpeg-4.0.2-64bit-static/ffmpeg -i "+mp3filepath+" -vn  -acodec libmp3lame -ac 1 -qscale:a 4 -ar 16000  /crm/tmp/"+ mp3 + ".mp3";
        try {
            Runtime.getRuntime().exec(cmdDos);
            //linux
//            Runtime runTime = Runtime.getRuntime();
//            runTime.exec(linuxTerminal);
            Thread.sleep(1500);
            String mm = "D:/tools/"+mp3+".mp3";
            AudioInputStream audioInputStreams = getPcmAudioInputStream(mm);
            logger.info("D:/tools/"+mp3+".mp3");
            AudioSystem.write(audioInputStreams, AudioFileFormat.Type.WAVE, new File(pcmfilepath));
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public AudioInputStream getPcmAudioInputStream (String mp3FilePath) {
        File mp3 = new File(mp3FilePath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        }catch (Exception e){
            e.printStackTrace();
        }
     return audioInputStream;
    }


    public String voiceAI(String mp3filepath) {
        VoiceAIVO voiceAIVO = voiceAIMapper.selectVoiceAI();
        String pcm = voiceAIVO.getAiUrl()+ UUID.randomUUID().toString()+".pcm";
        convertMP32Pcm(mp3filepath,pcm);
        String res = speechRecognition(pcm,"pcm",voiceAIVO.getAppId(),
                voiceAIVO.getApiKey(),voiceAIVO.getSecretKey());
        return res;
    }



//    public String voiceToWord(String fileURI) {
//        setConfig("AKID31NbfXbpBLJ4kGJrytc9UfgVAlGltJJ8","kKm26uXCgLtGRWVJvKtGU0LYdWCgOvGP",
//                "16","0","mp3",fileURI);
//
//        String result = sendVoice();
//        log.info("语音转换结果为: "+result);
//        return result;
//
//    }





}
