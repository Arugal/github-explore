package net.abc.explore.entity.service;

import net.abc.explore.entity.TimeCode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 *  简单缓存,当数据从数据库查询后便一直缓存
 *
 * @author: zhangwei
 * @date: 20:09/2019-01-04
 */
@Component
public class TimeCodeCacheService extends TimeCodeService {

    private static final Set<TimeCode> TIME_CODES = new CopyOnWriteArraySet<>();

    @Override
    public List<TimeCode> getAllTimeCode() {
        List<TimeCode> cacheList = new ArrayList<>(TIME_CODES);
        if(cacheList.isEmpty()){
            cacheList = super.getAllTimeCode();
            TIME_CODES.addAll(cacheList);
        }
        return super.getAllTimeCode();
    }

    @Override
    public TimeCode getTimeCodeByCode(Short code) {
        for(TimeCode code1 : TIME_CODES){
            if(code1.getCode().equals(code)){
                return code1;
            }
        }
        TimeCode code1 = super.getTimeCodeByCode(code);
        if(code1 != null){
            TIME_CODES.add(code1);
        }
        return code1;
    }

    @Override
    public TimeCode getTimeCodeByAliasName(String aliasName) {
        for(TimeCode code : TIME_CODES){
            if(code.getAliasName().equals(aliasName)){
                return code;
            }
        }
        TimeCode code = super.getTimeCodeByAliasName(aliasName);
        if(code != null){
            TIME_CODES.add(code);
        }
        return code;
    }

    @Override
    public TimeCode getTimeCodeByName(String name) {
        for(TimeCode code : TIME_CODES){
            if(code.getName().equals(name)){
                return code;
            }
        }
        TimeCode code = super.getTimeCodeByName(name);
        if(code != null){
            TIME_CODES.add(code);
        }
        return code;
    }


    public void clear(){
        TIME_CODES.clear();
    }
}
