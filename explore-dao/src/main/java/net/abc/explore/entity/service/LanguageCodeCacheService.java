package net.abc.explore.entity.service;

import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.dao.LanguageCodeDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    private final List<LanguageCode> LANGUAGE_CODES = new CopyOnWriteArrayList<>();

    public LanguageCodeCacheService(LanguageCodeDao languageCodeDao) {
        this.languageCodeDao = languageCodeDao;
        reLoad();
    }

    @Override
    public List<LanguageCode> getAllLanguageCode() {
        return Collections.unmodifiableList(LANGUAGE_CODES);
    }

    @Override
    public LanguageCode getLanguageCodeByAliasName(String aliasName) {
        for(LanguageCode code : LANGUAGE_CODES){
            if(code.getAliasName().equals(aliasName)){
                return code;
            }
        }
        return null;
    }

    @Override
    public LanguageCode getLanguageCodeByCode(Short code) {
        for(LanguageCode code1 : LANGUAGE_CODES){
            if(code1.getCode().equals(code)){
                return code1;
            }
        }
        return null;
    }

    @Override
    public LanguageCode getLanguageCodeByName(String name) {
        for(LanguageCode code : LANGUAGE_CODES){
            if(code.getName().equals(name)){
                return code;
            }
        }
        return null;
    }

    public void clear(){
        LANGUAGE_CODES.clear();
    }

    public void reLoad(){
        clear();
        LANGUAGE_CODES.addAll(super.getAllLanguageCode());
    }
}
