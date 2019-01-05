package net.abc.explore.entity.service;

import net.abc.explore.entity.LanguageCode;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * 简单缓存,当数据从数据库查询后便一直缓存
 *
 * @author: zhangwei
 * @date: 17:15/2019-01-04
 */
@Component
@Lazy
public class LanguageCodeCacheService extends LanguageCodeService {

    private static final Set<LanguageCode> LANGUAGE_CODES = new CopyOnWriteArraySet<>();

    @Override
    public List<LanguageCode> getAllLanguageCode() {
        List<LanguageCode> cacheList = new ArrayList<>(LANGUAGE_CODES);
        if(cacheList.isEmpty()){
            cacheList = super.getAllLanguageCode();
            LANGUAGE_CODES.addAll(cacheList);
        }
        return cacheList;
    }

    @Override
    public LanguageCode getLanguageCodeByAliasName(String aliasName) {
        for(LanguageCode code : LANGUAGE_CODES){
            if(code.getAliasName().equals(aliasName)){
                return code;
            }
        }
        LanguageCode code = super.getLanguageCodeByAliasName(aliasName);
        if(code != null){
            LANGUAGE_CODES.add(code);
        }
        return code;
    }

    @Override
    public LanguageCode getLanguageCodeByCode(Short code) {
        for(LanguageCode code1 : LANGUAGE_CODES){
            if(code1.getCode().equals(code)){
                return code1;
            }
        }
        LanguageCode code1 = super.getLanguageCodeByCode(code);
        if(code1 != null){
            LANGUAGE_CODES.add(code1);
        }
        return code1;
    }

    @Override
    public LanguageCode getLanguageCodeByName(String name) {
        name = languageTransform(name);
        for(LanguageCode code : LANGUAGE_CODES){
            if(code.getName().equals(name)){
                return code;
            }
        }
        LanguageCode code = super.getLanguageCodeByName(name);
        if(code != null){
            LANGUAGE_CODES.add(code);
        }
        return code;
    }

    public void clear(){
        LANGUAGE_CODES.clear();
    }
}
