package net.abc.explore.spider.gecco.pipeline;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: zhangwei
 * @date: 14:07/2019-01-01
 */
@PipelineName("PrintPipeline")
@Component("PrintPipeline")
public class PrintPipeline implements Pipeline {

    @Value("${gecco.pipeline.print.enabled}")
    private Boolean enabled = false;

    @Override
    public void process(SpiderBean bean) {
        if(enabled) {
            System.out.println(bean.toString());
        }
    }
}
