package net.abc.explore.spider.process;

import net.abc.explore.entity.Repositorie;
import net.abc.explore.entity.Trending;
import net.abc.explore.entity.service.RepositorieDaoService;
import net.abc.explore.entity.service.TrendingDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 11:10/2019-01-05
 */
@Service
public class TrendingProcess {

    @Autowired
    private TrendingDaoService trendingDaoService;

    @Autowired
    private RepositorieDaoService repositorieDaoService;

    /**
     * 1. 由于一个 repositorie 会被多个 Trending 触发更新，触发死锁，暂时不在 processRepositore 方法上使用事务
     *
     * @param trendings
     */
    public void processRepositore(List<Trending> trendings){
        for(Trending trending : trendings){
            Repositorie repositorie = trending.getRepositorie(),
                    existRepositorie = repositorieDaoService.getRepositorie(repositorie.getOwner(), repositorie.getName());
            if(existRepositorie != null){
                repositorie.setId(existRepositorie.getId());
                repositorie.setFirstExploreTime(existRepositorie.getFirstExploreTime());
                repositorieDaoService.update(repositorie);
            }else{
                repositorie.setFirstExploreTime(repositorie.getLastExploreTime());
                repositorieDaoService.add(repositorie);
            }
            trending.setRepositorieId(repositorie.getId());
        }
    }

    /**
     * 1. 由于 Trending 每次的内容不一致,所以需先 delete 后 add,否则会遗留垃圾数据
     *
     * @param trendings
     * @param timeCode
     * @param languageCode
     * @param occurTime
     */
    @Transactional(rollbackFor = {Exception.class})
    public void processTrending(List<Trending> trendings, Short timeCode, Short languageCode, Date occurTime){
        trendingDaoService.delete(timeCode, languageCode, occurTime);
        for(Trending trending : trendings){
            trendingDaoService.add(trending);
        }
    }
}
