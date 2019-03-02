package net.abc.explore.spider.gecco.pipeline;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.Repositorie;
import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.Trending;
import net.abc.explore.entity.service.LanguageCodeCacheService;
import net.abc.explore.entity.service.TimeCodeCacheService;
import net.abc.explore.spider.gecco.bean.RepositorieBean;
import net.abc.explore.spider.gecco.bean.TrendingBean;
import net.abc.explore.spider.process.TrendingProcess;
import net.abc.explore.spider.util.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 14:39/2019-01-04
 */
@Component("TrendingPipeline")
@PipelineName("TrendingPipeline")
public class TrendingPipeline implements Pipeline<TrendingBean> {

    @Autowired
    private LanguageCodeCacheService languageCodeCacheService;

    @Autowired
    private TimeCodeCacheService timeCodeCacheService;

    @Autowired
    private TrendingProcess trendingProcess;

    @Override
    public void process(TrendingBean trendingBean) {
       resolver(trendingBean);
    }


    private List<Trending> resolver(TrendingBean trendingBean){

        LanguageCode languageCode = languageCodeCacheService.getLanguageCodeByName(ResolverUtil.resolverLanguage(trendingBean.getLanguages()));
        TimeCode timeCode = timeCodeCacheService.getTimeCodeByName(trendingBean.getTime());

        short rank = 1;
        Date occurTime = TimeCode.formatCurrentTime(timeCode),
                lastTime = new Date();

        List<Trending> trendings = new ArrayList<>();

        for(RepositorieBean repositorieBean : trendingBean.getRepositories()){
            String owner = ResolverUtil.resolverOwner(repositorieBean.getOwner());
            String name = repositorieBean.getName();
            String describe = repositorieBean.getDescribe() == null ? "" : repositorieBean.getDescribe();
            int star = ResolverUtil.resolverInt(repositorieBean.getStar());
            int fork = ResolverUtil.resolverInt(repositorieBean.getFork());
            int newStar = ResolverUtil.resolverInt(repositorieBean.getNewStar());
            Trending trending = new Trending(occurTime, timeCode.getCode(), languageCode.getCode(), rank++, star, fork, newStar);
            trending.setRepositorie(new Repositorie(languageCode.getCode(), owner, name, describe, lastTime));
            trendings.add(trending);
        }
        trendingProcess.processRepositore(trendings);
        trendingProcess.processTrending(trendings, timeCode.getCode(), languageCode.getCode(), occurTime);
        return trendings;
    }
}
