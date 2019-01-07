package net.abc.explore.entity.service;

import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.dao.TimeCodeDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 *  简单缓存,当数据从数据库查询后便一直缓存
 *
 * @author: zhangwei
 * @date: 20:09/2019-01-04
 */
@Component
@Lazy
public class TimeCodeCacheService extends TimeCodeService {

    private final List<TimeCode> TIME_CODES = new CopyOnWriteArrayList<>();

    public TimeCodeCacheService(TimeCodeDao timeCodeDao) {
        this.timeCodeDao = timeCodeDao;
        reLoad();
    }

    @Override
    public List<TimeCode> getAllTimeCode() {
        return Collections.unmodifiableList(TIME_CODES);
    }

    @Override
    public TimeCode getTimeCodeByCode(Short code) {
        for(TimeCode code1 : TIME_CODES){
            if(code1.getCode().equals(code)){
                return code1;
            }
        }
        return null;
    }

    @Override
    public TimeCode getTimeCodeByAliasName(String aliasName) {
        for(TimeCode code : TIME_CODES){
            if(code.getAliasName().equals(aliasName)){
                return code;
            }
        }
        return null;
    }

    @Override
    public TimeCode getTimeCodeByName(String name) {
        for(TimeCode code : TIME_CODES){
            if(code.getName().equals(name)){
                return code;
            }
        }
        return null;
    }


    public void clear(){
        TIME_CODES.clear();
    }

    public void reLoad(){
        clear();
        TIME_CODES.addAll(super.getAllTimeCode());
    }
}
