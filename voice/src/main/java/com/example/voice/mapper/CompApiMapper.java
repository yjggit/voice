package com.example.voice.mapper;

import com.example.voice.entities.CompApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jianhui.Yang
 * @version $Id CompApiMapper.java, v 0.1 2018-11-09 10:59 jianhui.Yang Exp $$
 */
@Mapper
public interface CompApiMapper {

    @Select("select * from comp_api where id=#{id}")
    CompApi get(String id );

    @Select("select * from comp_api")
    List<CompApi> finddocumentAddress();

}
