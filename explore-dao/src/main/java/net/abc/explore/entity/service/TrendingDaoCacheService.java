package net.abc.explore.entity.service;

import lombok.NoArgsConstructor;
import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.Trending;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 将 trending 查询结果缓存,根据 timeCode interval 字段自动失效,判断失效后如果上一个周期内存在引用，则重新刷新缓存
 * 否则直接删除数据
 * @author: zhangwei
 * @date: 21:05/2019-01-05
 */
@Component
@Lazy
public class TrendingDaoCacheService extends TrendingDaoService {

    /**
     * 缓存 Trending 的查询结果
     */
    private final Map<String, TrendingCache> TRENDING_CACHE = new ConcurrentHashMap<>();

    /**
     * 缓存所有 cache key
     * 1. 防止缓存穿透 （类似 布隆过滤器）
     * 2. 降低锁的粒度  (cache 为单位加锁)
     */
    private final Map<String, Object> CACHE_KEYS = new HashMap<>();

    private final List<Trending> EMPTY_LIST = new ArrayList<>(0);

    private static final Log log = LogFactory.getLog(TrendingDaoCacheService.class);

    private final long CACHE_INTERVAL = TimeUnit.MINUTES.toMillis(5);

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

    public TrendingDaoCacheService(TimeCodeCacheService timeCodeCacheService, LanguageCodeCacheService languageCodeCacheService){
        cacheThread.setName("TrendingDaoCacheThread");
        cacheThread.setDaemon(true);
        cacheThread.start();
        List<TimeCode> timeCodes = timeCodeCacheService.getAllTimeCode();
        List<LanguageCode> languageCodes = languageCodeCacheService.getAllLanguageCode();
        for(TimeCode timeCode : timeCodes){
            String timeCodeName = timeCode.getName();
            for(LanguageCode languageCode : languageCodes){
                CACHE_KEYS.put(cacheKey(timeCodeName, languageCode.getName()), new Object());
            }
        }
    }

    @Override
    public List<Trending> getTrending(String timeCode, String languageCode) {
        String cacheKey = cacheKey(timeCode, languageCode);
        final Object lock = CACHE_KEYS.get(cacheKey);
        if(lock != null){
            TrendingCache trendingCache = TRENDING_CACHE.get(cacheKey);
            if(trendingCache == null){
                // update cache
                // 双重锁定,缓解类似缓存击穿的效果,当大量请求同时请求同一个 cacheKey 时,过滤不必要的 db 访问
                synchronized (lock) {
                    trendingCache = TRENDING_CACHE.get(cacheKey);
                    if(trendingCache == null) {
                        trendingCache = getTrendingCache(timeCode, languageCode);
                        TRENDING_CACHE.put(cacheKey, trendingCache);
                    }
                }
            }
            return trendingCache.getTrendings();
        }
        return EMPTY_LIST;
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
            return referenceIdx.get() > 0;
        }
    }
}
