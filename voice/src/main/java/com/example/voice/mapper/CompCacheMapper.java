package com.example.voice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author jianhui.Yang
 * @version $Id CompCacheMapper.java, v 0.1 2018-11-09 10:10 jianhui.Yang Exp $$
 */
@Mapper
public interface CompCacheMapper {


    @Select("select val from comp_cache where id=#{id}")
    String geVal(@Param("id") String id);

}
