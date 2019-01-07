package net.abc.explore.entity.service;

import lombok.NoArgsConstructor;
import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.Trending;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 将 trending 查询结果缓存,根据 timeCode interval 字段自动失效,判断失效后如果上一个失效周期存在引用，则重新刷新缓存
 * 否则直接删除数据
 * @author: zhangwei
 * @date: 21:05/2019-01-05
 */
@Component
@Lazy
public class TrendingDaoCacheService extends TrendingDaoService {

    private static final Map<String, TrendingCache> TRENDING_CACHE = new ConcurrentHashMap<>();

    private static final Log log = LogFactory.getLog(TrendingDaoCacheService.class);

    private static final long CACHE_INTERVAL = TimeUnit.MINUTES.toMillis(5);

    private final Thread cacheThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true){
                try{
                    updateTrendingCache();
                    Thread.sleep(CACHE_INTERVAL);
                }catch (Throwable t){
                    log.error("cacheTread :"+t.getMessage(), t);
                }
            }
        }
    });

    public TrendingDaoCacheService(){
        cacheThread.setName("TrendingDaoCacheThread");
        cacheThread.setDaemon(true);
        cacheThread.start();
    }

    @Override
    public List<Trending> getTrending(String timeCode, String languageCode) {
        String cacheKey = cacheKey(timeCode, languageCode);
        TrendingCache trendingCache = TRENDING_CACHE.get(cacheKey);
        if(trendingCache == null){
            // update cache
            trendingCache = getTrendingCache(timeCode, languageCode);
            TRENDING_CACHE.put(cacheKey, trendingCache);
        }
        return trendingCache.getTrendings();
    }

    private static String cacheKey(String timeCode, String languageCode){
        return timeCode+"-"+languageCode;
    }

    private void updateTrendingCache(){
        Iterator<Map.Entry<String, TrendingCache>> iterable = TRENDING_CACHE.entrySet().iterator();
        while (iterable.hasNext()){
            Map.Entry<String, TrendingCache> entry = iterable.next();
            TrendingCache trendingCache = entry.getValue();
            if(trendingCache.isLose()){
                if(trendingCache.hasRerence()){
                    entry.setValue(getTrendingCache(trendingCache.getTimeCode(), trendingCache.getLanguageCode()));
                }else{
                    iterable.remove();
                }
            }
        }
    }

    private TrendingCache getTrendingCache(String timeCode, String languageCode){
        List<Trending> trendings = super.getTrending(timeCode, languageCode);
        TimeCode timeCode1 = timeCodeCacheService.getTimeCodeByName(timeCode);
        return new TrendingCache(trendings, System.currentTimeMillis(), timeCode1.getUnit().toMillis(timeCode1.getInterval())/2, timeCode, languageCode);
    }

    @NoArgsConstructor
    private class TrendingCache{

        private List<Trending> trendings;
        private final AtomicInteger referenceIdx = new AtomicInteger();
        private long startTime;
        private long loseDfficacy;
        private long endTime;
        private String timeCode;
        private String languageCode;

        public TrendingCache(List<Trending> trendings, long startTime, long loseDfficacy, String timeCode, String languageCode) {
            this.trendings = trendings;
            this.startTime = startTime;
            this.loseDfficacy = loseDfficacy;
            this.timeCode = timeCode;
            this.languageCode = languageCode;
            this.endTime = loseDfficacy + startTime;
        }

        public List<Trending> getTrendings() {
            referenceIdx.getAndIncrement();
            return trendings;
        }

        public void setTrendings(List<Trending> trendings) {
            this.trendings = trendings;
            referenceIdx.set(0);
        }

        public AtomicInteger getReferenceIdx() {
            return referenceIdx;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getLoseDfficacy() {
            return loseDfficacy;
        }

        public void setLoseDfficacy(long loseDfficacy) {
            this.loseDfficacy = loseDfficacy;
        }

        public String getTimeCode() {
            return timeCode;
        }

        public void setTimeCode(String timeCode) {
            this.timeCode = timeCode;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public boolean isLose(){
            return System.currentTimeMillis() > endTime;
        }

        public boolean hasRerence(){
            return referenceIdx.get() != 0;
        }
    }
}
