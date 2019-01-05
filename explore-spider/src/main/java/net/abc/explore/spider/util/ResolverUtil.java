package net.abc.explore.spider.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: zhangwei
 * @date: 15:22/2019-01-04
 */
public abstract class ResolverUtil {

    private static final Log logger = LogFactory.getLog(ResolverUtil.class);

    private static final String ONWER_SUFFIX = " /";

    public static int resolverInt(String val){
        if(val == null){
            logger.warn("resolver int val is null!");
            return 0;
        }
        char [] cvals = val.toCharArray();
        StringBuilder intBuilder = new StringBuilder();

        for(char  cvarl : cvals){
            if('0' <= cvarl && cvarl <= '9'){
                intBuilder.append(cvarl);
            }
        }
        return Integer.valueOf(intBuilder.toString());
    }

    public static String resolverOwner(String onwer){
        if(onwer == null){
            logger.warn("resolver owner is null");
        }
        if(onwer.endsWith(ONWER_SUFFIX)){
            return onwer.substring(0, onwer.length() - 2);
        }
        return onwer;
    }
}
