package com.example.voice.service.impl;
import com.example.voice.mapper.VoiceAIMapper;
import com.example.voice.vo.VoiceTTSVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.net.URLEncoder;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;

/**
 * @author jianhui.Yang
 * @version $Id VoiceTTSService.java, v 0.1 2018-10-17 15:09 jianhui.Yang Exp $$
 */
@Service
public class VoiceTTSService {

    private static String SecretId, SecretKey, EngSerViceType, SourceType, VoiceFormat, fileURI;

    @Autowired
    private VoiceAIMapper voiceAIMapper;

    public static String formSignstr(String serverUrl, Map<String, String> mapReq) {
        StringBuilder strBuilder = new StringBuilder(serverUrl);

        // to make that all the parameters are sorted by ASC order
        TreeMap<String, String> sortedMap = new TreeMap(mapReq);

        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            strBuilder.append(entry.getKey());
            strBuilder.append('=');
            strBuilder.append(entry.getValue());
            strBuilder.append('&');
        }

        if (mapReq.size() > 0) {
            strBuilder.setLength(strBuilder.length() - 1);
        }

        //System.out.println("sign str: " + strBuilder);

        return strBuilder.toString();
    }

    public static String formPostbody(Map<String, String> mapReq) {
        StringBuilder stringBuilder = new StringBuilder();
        // to make that all the parameters are sorted by ASC order
        TreeMap<String, String> sortedMap = new TreeMap(mapReq);
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            try {
                stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                stringBuilder.append('=');
                stringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                stringBuilder.append('&');
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    public static String base64_hmac_sha1(String value, String keyStr) {
        String encoded = "";
        String type = "HmacSHA1";
        try {
            byte[] key = (keyStr).getBytes("UTF-8");
            byte[] Sequence = (value).getBytes("UTF-8");

            Mac HMAC = Mac.getInstance(type);
            SecretKeySpec secretKey = new SecretKeySpec(key, type);

            HMAC.init(secretKey);
            byte[] Hash = HMAC.doFinal(Sequence);

            encoded = Base64.getEncoder().encodeToString(Hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }

    /*
     * 获得unix时间戳
     */
    public static String toUNIXEpoch() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime + "";
    }

    /*
     * 生成随机nonce
     */
    public static String toUNIXNonce() {
        long unixTime = System.currentTimeMillis() / 1000L;
        String str = unixTime + "";
        String nonce = str.substring(0, 4);
        return nonce;
    }

    public static String createSign(String signStr, String secretKey) {
        return base64_hmac_sha1(signStr, secretKey);
    }

    private static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    public static int setConfig(
            String SecretId,
            String SecretKey,
            String EngSerViceType,
            String SourceType,
            String VoiceFormat,
            String fileURI
    ) {
        if (SecretId.length() <= 0) {
            System.out.println("SecretId can not be empty!");
            return -1;
        }
        if (SecretKey.length() <= 0) {
            System.out.println("SecretKey can not be empty!");
            return -1;
        }
        if (EngSerViceType.length() <= 0 || (EngSerViceType.compareTo("8k") != 0 && EngSerViceType.compareTo("16k") != 0)) {
            System.out.println("EngSerViceTyp is not valied !");
            return -1;
        }
        if (SourceType.length() <= 0 || (SourceType.compareTo("0") != 0 && SourceType.compareTo("1") != 0)) {
            System.out.println("SourceType is not valied !");
            return -1;
        }
        if (VoiceFormat.length() <= 0 || (VoiceFormat.compareTo("mp3") != 0 && VoiceFormat.compareTo("wav") != 0)) {
            System.out.println("VoiceFormat is not valied !");
            return -1;
        }
        if (fileURI.length() <= 0) {
            System.out.println("fileURI can not be empty!");
            return -1;
        }
        VoiceTTSService.SecretId = SecretId;
        VoiceTTSService.SecretKey = SecretKey;
        VoiceTTSService.EngSerViceType = EngSerViceType;
        VoiceTTSService.SourceType = SourceType;
        VoiceTTSService.VoiceFormat = VoiceFormat;
        VoiceTTSService.fileURI = fileURI;
        return 0;
    }

    public static String sendVoice()  {
        Map<String, String> reqMap = new TreeMap();
        reqMap.put("Action", "SentenceRecognition");
        reqMap.put("SecretId", SecretId);
        reqMap.put("Timestamp", toUNIXEpoch());
        reqMap.put("Nonce", toUNIXNonce());
        reqMap.put("Version", "2018-05-22");
        reqMap.put("ProjectId", "0");
        reqMap.put("SubServiceType", "2");
        reqMap.put("EngSerViceType", EngSerViceType);
        reqMap.put("SourceType", SourceType);
        if (SourceType.compareTo("0") == 0) {
            try {
                String Url = URLEncoder.encode(fileURI, "UTF-8");
                reqMap.put("Url", Url);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (SourceType.compareTo("1") == 0) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File(fileURI));
                int datalen = fileInputStream.available();
                byte[] dataPacket = new byte[datalen];
                int n = fileInputStream.read(dataPacket);
                //System.out.println("n :"+n);
                String Data = Base64.getEncoder().encodeToString(dataPacket);
                String DataLen = datalen + "";
                // System.out.println("data len: "+DataLen);
                reqMap.put("Data", Data);
                reqMap.put("DataLen", DataLen);


            } catch (Exception e) {
                return "音频文件解析错误，请重新录入！！！";
//                e.printStackTrace();
            }
        } else {
            return "";
        }
        reqMap.put("VoiceFormat", VoiceFormat);
        String UsrAudioKey = getRandomString(16);
        reqMap.put("UsrAudioKey", UsrAudioKey);
        String _url = "POSTaai.tencentcloudapi.com/?";
        String signstr = formSignstr(_url, reqMap);
        // System.out.println("signstr: " + signstr);
        String signing = createSign(signstr, SecretKey);
        // System.out.println("签名: " + signing);
        String tmppostdata = formPostbody(reqMap);
        String sign = "";
        try {
            sign = URLEncoder.encode(signing, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder postdata = new StringBuilder(tmppostdata);
        postdata.append("Signature=");
        postdata.append(sign);
        String post = postdata.toString();
        //System.out.println("post : "+post);
        String serverUrl = "https://aai.tencentcloudapi.com";

        HttpURLConnection con = null;
        StringBuilder sbResult = new StringBuilder();
        try {
            URL url = new URL(serverUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Host", "aai.tencentcloudapi.com");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Charset", "utf-8");

            // 往服务器写入数据
            OutputStream out = con.getOutputStream();
            out.write(post.getBytes());
            out.flush();

            // 接收服务器返回的数据
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;// 每一行的数据
            while ((line = br.readLine()) != null) {
                sbResult.append(line);
            }
            System.out.println(sbResult.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                con.disconnect();
                con = null;
            }
        }

        return sbResult.toString();
    }


    public String voiceToWord(String fileURI) {

        String mp3 = UUID.randomUUID().toString();
        //Windows
        String cmdDos = "D:\\tools\\ffmpeg-20181007-0a41a8b-win64-static\\bin\\ffmpeg -i "+fileURI+ " -vn  -acodec libmp3lame -ac 1 -qscale:a 4 -ar 16000  "+ "D:\\mp3\\"+ mp3+".mp3";
        System.out.println(cmdDos);
        try{
            Runtime.getRuntime().exec(cmdDos);
            Thread.sleep(1500);
            String mm = "D:\\mp3\\"+mp3+".mp3";
//            System.out.println(mm);
            VoiceTTSVO voiceTTSVO = voiceAIMapper.selectVoiceTTS();
            setConfig(voiceTTSVO.getSecretId(),voiceTTSVO.getSecretKey(),
                    voiceTTSVO.getEngSerViceType(),voiceTTSVO.getSourceType(),voiceTTSVO.getVoiceFormat(),mm);

            String result = sendVoice();
            System.out.println(result);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
