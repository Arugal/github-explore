package net.abc.explore.rest.api;

import com.spring4all.swagger.EnableSwagger2Doc;
import net.abc.explore.entity.ExploreDAOApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: zhangwei
 * @date: 16:24/2019-01-05
 */
@SpringBootApplication
@Import({ExploreDAOApplication.class, WebConfiguration.class})
@EnableSwagger2Doc
@EnableSwagger2
public class ExploreRESTApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExploreRESTApiApplication.class, args);
    }
}
