package net.abc.explore.spider.test;

import com.geccocrawler.gecco.spider.SpiderLoopGroup;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;

/**
 * @author: zhangwei
 * @date: 13:51/2019-01-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "net.abc.explore.spider")
public class ExploreSpiderApplicationTest {

    @Autowired
    private SpiderLoopGroup group;

    @After
    public void setDown(){
        synchronized (this){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    @Test
    public void geccoGithub(){
        group.executor("https://github.com/trending/java?since=daily");
    }
}
