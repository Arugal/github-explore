package net.abc.explore.rest.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.service.LanguageCodeCacheService;
import net.abc.explore.entity.service.TimeCodeCacheService;
import net.abc.explore.rest.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 16:32/2019-01-05
 */
@RestController
@RequestMapping(value = "/constant")
@Api
public class ConstantController {

    @Autowired
    private TimeCodeCacheService timeCodeCacheService;

    @Autowired
    private LanguageCodeCacheService languageCodeCacheService;

    @ApiOperation(value = "timeCode 查询")
    @RequestMapping(value = "/timcCode", method = RequestMethod.GET)
    public Result getTimeCode(Result result){
        List<TimeCode> timeCodes = timeCodeCacheService.getAllTimeCode();
        result.addData("timeCode", timeCodes, true);
        return result;
    }

    @ApiOperation(value = "languageCode 查询")
    @RequestMapping(value = "/languageCode", method = RequestMethod.GET)
    public Result getLanguageCode(Result result){
        List<LanguageCode> languageCodes = languageCodeCacheService.getAllLanguageCode();
        result.addData("languageCode", languageCodes, true);
        return result;
    }
}
