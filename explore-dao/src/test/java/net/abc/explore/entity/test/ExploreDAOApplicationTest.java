package net.abc.explore.entity.test;

import net.abc.explore.entity.ExploreDaoConfiguration;
import net.abc.explore.entity.service.LanguageCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @author: zhangwei
 * @date: 17:20/2019-01-04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExploreDaoApplicationTest {

    @Autowired
    private LanguageCodeService languageCodeService;

    @Autowired
    private DataSource dataSource;

   @Test
    public void testLanguageCode(){
       languageCodeService.getAllLanguageCode();
   }
}
