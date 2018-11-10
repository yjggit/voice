package com.example.voice.controller;

import com.example.voice.service.SystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author jianhui.Yang
 * @version $Id SystemController.java, v 0.1 2018-11-10 9:50 jianhui.Yang Exp $$
 */
@SuppressWarnings("ALL")
@Api(value = "components",tags = "获取系统运行相关数据")
@RequestMapping("components")
public class SystemController {

    @Autowired
    private SystemLogService logService;


    @ApiOperation(value = "查询系统调用接口出错日志",notes = "根据日期查询，默认查询当天的日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start" ,paramType = "query", value = "时间范围起，支持多种格式：yyyyMMddHHmmss、yyyyMMddHHmm、yyyyMMddHH、yyyyMMdd",dataType = "Integer"),
            @ApiImplicitParam(name = "end",paramType = "query",value = "时间范围止，支持多种格式：yyyyMMddHHmmss、yyyyMMddHHmm、yyyyMMddHH、yyyyMMdd",dataType = "Integer"),
            @ApiImplicitParam(name = "day",paramType = "query",value = "具体某天的日志，格式：yyyyMMdd",dataType = "Integer"),
            @ApiImplicitParam(name = "limitStart",paramType = "query",value = "开始条数",defaultValue = "0",dataType = "Integer"),
            @ApiImplicitParam(name = "limitSize",paramType = "query",value = "每页条数",defaultValue = "10000",dataType = "Integer")
    })
    @RequestMapping(path = {"/query/log.do","/query/log"},method = {RequestMethod.GET,RequestMethod.POST})
    public Object queryLog(Long start, Long end, Long day,
                           @RequestParam(defaultValue = "0") Integer limitStart,
                           @RequestParam(defaultValue = "10000") Integer limitSize,
                           HttpServletRequest request){

        String url = request.getScheme() + "://"+
                     request.getServerName() + ":" +
                     request.getServerPort()+
                     request.getContextPath()+
                     "/components/log/detail.do?id=";

        List<Map<String,Object>> logs = logService.queryLogEvent(start, end,day,limitStart,limitSize);

        logs.forEach(log -> {
            log.put("详情查看",url+log.get("event_id"));
        });
        return logs;
    }




    @ApiOperation(value = "日志详情",notes = "根据id查询日志详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",paramType = "query",value = "日志id",required = true,dataType = "Integer")
    })
    @RequestMapping(path = {"/log/detail","/log/detail.do"})
    public Object queryLog(Long id){

        Object [] ary = new Object[2];

        ary[0] = logService.logDetail(id);

        ary[1] = logService.logExceptionDetaill(id);

        return ary;



    }
}
