package net.abc.explore.spider.gecco.pipeline;

import com.geccocrawler.boot.gecco.spider.SpringPipelineFactory;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 14:07/2019-01-01
 */
@PipelineName("PrintPipline")
@Component("PrintPipline")
@ConditionalOnBean(SpringPipelineFactory.class)
@ConditionalOnProperty(prefix = "gecco.print.pipline", value = "enabled", havingValue = "true", matchIfMissing = false)
public class PrintPipline implements Pipeline {

    @Override
    public void process(SpiderBean bean) {
        System.out.println(bean.toString());
    }
}
