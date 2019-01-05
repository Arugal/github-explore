package net.abc.explore.spider.process;

import net.abc.explore.entity.Repositorie;
import net.abc.explore.entity.Trending;
import net.abc.explore.entity.service.RepositorieDaoService;
import net.abc.explore.entity.service.TrendingDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 11:10/2019-01-05
 */
@Component
public class TrendingProcess {

    @Autowired
    private TrendingDaoService trendingDaoService;

    @Autowired
    private RepositorieDaoService repositorieDaoService;

    public void process(List<Trending> trendings){
        Short timeCode = null, languageCode = null;
        Date occurTime = null;
        for(Trending trending : trendings){
            Repositorie repositorie = trending.getRepositorie();
            mergeRepositorie(repositorie);

            if(timeCode == null){
                timeCode = trending.getTimeCode();
            }
            if(languageCode == null){
                languageCode = trending.getLanguageCode();
            }
            if(occurTime == null){
                occurTime = trending.getOccurTime();
            }
        }
        updateDB(trendings, timeCode, languageCode, occurTime);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void updateDB(List<Trending> trendings, Short timeCode, Short languageCode, Date occurTime){
        trendingDaoService.delete(timeCode, languageCode, occurTime);
        for(Trending trending : trendings){
            Repositorie repositorie = trending.getRepositorie();
            if(repositorie.getId() == null){
                repositorie.setFirstExploreTime(repositorie.getLastExploreTime());
                repositorieDaoService.add(repositorie);
            }else{
                repositorieDaoService.update(repositorie);
            }
            trending.setRepositorieId(repositorie.getId());
            trendingDaoService.add(trending);
        }
    }

    private void mergeRepositorie(Repositorie newRepositorie){
        Repositorie existRepositorie = repositorieDaoService.getRepositorie(newRepositorie.getOwner(), newRepositorie.getName());
        if(existRepositorie != null){
            newRepositorie.setId(existRepositorie.getId());
            newRepositorie.setFirstExploreTime(existRepositorie.getFirstExploreTime());
        }
    }
}
