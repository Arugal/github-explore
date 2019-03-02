package net.abc.explore.spider.gecco.pipeline;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 14:07/2019-01-01
 */
@PipelineName("PrintPipeline")
@Component("PrintPipeline")
@ConditionalOnProperty(prefix = "gecco.pipeline.print", name = "enabled", havingValue = "true")
public class PrintPipeline implements Pipeline {

    @Override
    public void process(SpiderBean bean) {
        System.out.println(bean.toString());
    }
}
