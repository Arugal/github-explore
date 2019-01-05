package net.abc.explore.entity.test;

import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.service.LanguageCodeService;
import net.abc.explore.entity.service.TimeCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 17:20/2019-01-04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExploreDAOApplicationTest {

    @Autowired
    private LanguageCodeService languageCodeService;

    @Autowired
    private TimeCodeService timeCodeService;

    @Test
    public void testLanguageCode(){
        List<LanguageCode> languageCodes = languageCodeService.getAllLanguageCode();
        System.out.println(languageCodes.size());
        for(LanguageCode code : languageCodes){
            System.out.println(languageCodeService.getLanguageCodeByCode(code.getCode()));
            System.out.println(languageCodeService.getLanguageCodeByAliasName(code.getAliasName()));
            System.out.println(languageCodeService.getLanguageCodeByName(code.getName()));
            break;
        }
    }

    @Test
    public void testTimeCode(){
        List<TimeCode> timeCodes = timeCodeService.getAllTimeCode();
        System.out.println(timeCodes.size());
        for(TimeCode code : timeCodes){
            System.out.println(timeCodeService.getTimeCodeByCode(code.getCode()));
            System.out.println(timeCodeService.getTimeCodeByAliasName(code.getAliasName()));
            System.out.println(timeCodeService.getTimeCodeByName(code.getName()));
            break;
        }
    }
}
