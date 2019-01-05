package net.abc.explore.entity.dao;

import net.abc.explore.entity.TimeCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 19:47/2019-01-04
 */
@Mapper
public interface TimeCodeDao {

    List<TimeCode> getAllTimeCode();

    TimeCode getTimeCodeByCode(@Param("code") Short code);

    TimeCode getTimeCodeByAliasName(@Param("aliasName") String aliasName);

    TimeCode getTimeCodeByName(@Param("name") String name);
}
