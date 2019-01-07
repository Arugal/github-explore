package net.abc.explore.rest.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.abc.explore.entity.Trending;
import net.abc.explore.entity.service.TrendingDaoCacheService;
import net.abc.explore.entity.service.TrendingDaoService;
import net.abc.explore.rest.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 16:27/2019-01-05
 */
@RestController
@RequestMapping(value = "/trending")
public class TrendingController {

    @Autowired
    private TrendingDaoCacheService trendingDaoCacheService;

    @Autowired
    private TrendingDaoService trendingDaoService;

    @ApiOperation(value = "trending 查询")
    @RequestMapping(value = "/{languageCode}", method = RequestMethod.GET)
    public Result trending(
            @ApiParam(required = true, name = "languageCode", value = "/constant/languageCode 接口对应", defaultValue = "java") @PathVariable String languageCode,
            @ApiParam(required = true, name = "since", value = "/constant/timeCode 接口对应", defaultValue = "daily") @RequestParam String since,
            Result result){
        List<Trending> trendings =  trendingDaoCacheService.getTrending(since, languageCode);
        result.addData("trending", trendings, true);
        return result;
    }
}
