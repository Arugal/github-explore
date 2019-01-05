package net.abc.explore.entity.service;

import net.abc.explore.entity.LanguageCode;
import net.abc.explore.entity.dao.LanguageCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 16:32/2019-01-04
 */
@Component
public class LanguageCodeService {

    @Autowired
    protected LanguageCodeDao languageCodeDao;

    private static final String C = "c  ";

    private static final String C_TRANSFORM = "c++";

    protected String languageTransform(String name){
        if(name.equals(C)){
            return C_TRANSFORM;
        }
        return name;
    }

    public List<LanguageCode> getAllLanguageCode(){
        return languageCodeDao.getAllLanguageCode();
    }

    public LanguageCode getLanguageCodeByCode(Short code) {
        return languageCodeDao.getLanguageCodeByCode(code);
    }

    public LanguageCode getLanguageCodeByAliasName(String aliasName) {
        return languageCodeDao.getLanguageCodeByAliasName(aliasName);
    }

    public LanguageCode getLanguageCodeByName(String name) {
        name = languageTransform(name);
        return languageCodeDao.getLanguageCodeByName(name);
    }
}
