package net.abc.explore.entity.service;

import net.abc.explore.entity.TimeCode;
import net.abc.explore.entity.dao.TimeCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 19:50/2019-01-04
 */
@Component
public class TimeCodeService{

    @Autowired
    private TimeCodeDao timeCodeDao;

    public List<TimeCode> getAllTimeCode() {
        return timeCodeDao.getAllTimeCode();
    }

    public TimeCode getTimeCodeByCode(Short code) {
        return timeCodeDao.getTimeCodeByCode(code);
    }


    public TimeCode getTimeCodeByAliasName(String aliasName) {
        return timeCodeDao.getTimeCodeByAliasName(aliasName);
    }

    public TimeCode getTimeCodeByName(String name) {
        return timeCodeDao.getTimeCodeByName(name);
    }
}
