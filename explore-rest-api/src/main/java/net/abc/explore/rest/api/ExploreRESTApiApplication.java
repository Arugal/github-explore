package net.abc.explore.rest.api;

import com.spring4all.swagger.EnableSwagger2Doc;
import net.abc.explore.entity.ExploreDAOApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
/**
 * @author: zhangwei
 * @date: 16:24/2019-01-05
 */
@SpringBootApplication
@Import({ExploreDAOApplication.class, WebConfiguration.class})
@EnableSwagger2Doc
public class ExploreRESTApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExploreRESTApiApplication.class, args);
    }
}
