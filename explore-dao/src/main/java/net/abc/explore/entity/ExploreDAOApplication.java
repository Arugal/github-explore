package net.abc.explore.entity.test;

import net.abc.explore.entity.ExploreDaoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author: zhangwei
 * @date: 19:36/2019-01-04
 */
@SpringBootApplication
@Import(ExploreDaoConfiguration.class)
public class ExploreDaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExploreDaoApplication.class, args);
    }
}
