package net.abc.explore.entity.service;

import net.abc.explore.constant.ETimeCode;
import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.Repositorie;
import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.Trending;
import net.abc.explore.entity.dao.TrendingDao;
import net.abc.tool.util.base.ObjectUtils;
import net.abc.tool.util.date.DateUtil;
import net.abc.tool.util.date.ETimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 11:38/2019-01-05
 */
@Component
public class TrendingDaoService {

    @Autowired
    protected TrendingDao trendingDao;

    @Autowired
    protected RepositorieDaoService repositorieDaoService;

    @Autowired
    protected TimeCodeCacheService timeCodeCacheService;

    @Autowired
    protected LanguageCodeCacheService languageCodeCacheService;

    private static final long one_time = 1000 * 60 * 60 * 24L;

    public int add(Trending trending) {
        return trendingDao.add(trending);
    }

    public int delete(Short timeCode, Short languageCode, Date occurTime){
        return trendingDao.delete(timeCode, languageCode, occurTime);
    }

    public List<Trending> getTrending(String timeCode, String languageCode){
        TimeCode timeCode1 = timeCodeCacheService.getTimeCodeByName(timeCode);
        ObjectUtils.checkNotNull(timeCode1, timeCode);
        LanguageCode languageCode1 = languageCodeCacheService.getLanguageCodeByName(languageCode);
        ObjectUtils.checkNotNull(languageCode1, languageCode);
        List<Trending> trendings = trendingDao.getTrending(timeCode1.getCode(), languageCode1.getCode(),
                TimeCode.formatCurrentTime(timeCode1), null);
        for(Trending trending : trendings){
            Repositorie repositorie = repositorieDaoService.getRepositorie(trending.getRepositorieId());
            trending.setRepositorie(repositorie);
            trendingStatis(trending);
        }
        return trendings;
    }

    private void trendingStatis(Trending trending){
        List<Trending> trendings = trendingDao.getTrending(trending.getTimeCode(), trending.getLanguageCode(), null, trending.getRepositorieId());
        if(trendings.size() == 1){
            trending.setNew(true);
            trending.setConsecutiveDays(1);
        }else{
            Collections.sort(trendings);
            int consecutiveDays = 0;
            Date lastTime = null;
            ETimeCode timeCode = ETimeCode.getETimeCode(trending.getTimeCode());
            Long mistiming = 0L;
            boolean isMonth = false;
            switch (timeCode){
                case DEILY: mistiming = one_time; break;
                case WEEKLY: mistiming = one_time * 7; break;
                case MONTHLY: isMonth = true; break;
                default: break;
            }
            for(Trending formerly : trendings){
                if(isMonth && lastTime != null){
                    mistiming = lastTime.getTime() - DateUtil.addDate(lastTime, ETimeUnit.MONTH, -1).getTime();
                }
                if(lastTime == null || lastTime.getTime() - formerly.getOccurTime().getTime() == mistiming){
                    lastTime = formerly.getOccurTime();
                    consecutiveDays++;
                    if(consecutiveDays == 2){
                        trending.setLastRank(formerly.getRank());
                    }
                }else {
                    break;
                }
            }
            trending.setNew(false);
            trending.setConsecutiveDays(consecutiveDays);
        }
    }
}
