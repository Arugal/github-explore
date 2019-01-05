package net.abc.explore.spider;

import com.geccocrawler.gecco.spider.SpiderLoopGroup;
import net.abc.explore.entity.ExploreDAOApplication;
import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.service.LanguageCodeCacheService;
import net.abc.explore.entity.service.TimeCodeCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 13:46/2019-01-01
 */
@SpringBootApplication
@Import(ExploreDAOApplication.class)
public class ExploreSpiderApplication implements CommandLineRunner {

    private static final String BASE_URL = "https://github.com/trending/%s?since=%s";

    @Autowired
    private SpiderLoopGroup spiderLoopGroup;

    @Autowired
    private TimeCodeCacheService timeCodeCacheService;

    @Autowired
    private LanguageCodeCacheService languageCodeCacheService;

    public static void main(String[] args) {
       SpringApplication.run(ExploreSpiderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<TimeCode> timeCodes = timeCodeCacheService.getAllTimeCode();
        List<LanguageCode> languageCodes = languageCodeCacheService.getAllLanguageCode();
        for(LanguageCode languageCode : languageCodes){
            for(TimeCode timeCode : timeCodes){
                spiderLoopGroup.scheduleAtFixedRate(String.format(BASE_URL, languageCode.getName(), timeCode.getName()),
                        0, timeCode.getInterval(), timeCode.getUnit());
            }
        }
    }
}
