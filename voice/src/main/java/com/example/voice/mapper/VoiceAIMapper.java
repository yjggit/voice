package com.example.voice.mapper;

import com.example.voice.vo.VoiceAIVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jianhui.Yang
 * @version $Id VoiceAIDao.java, v 0.1 2018-10-11 15:32 jianhui.Yang Exp $$
 */
@Mapper
public interface VoiceAIMapper {

    VoiceAIVO selectVoiceAI();

}
