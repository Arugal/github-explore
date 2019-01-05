package net.abc.explore.entity.dao;

import net.abc.explore.entity.LanguageCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 15:38/2019-01-04
 */
@Mapper
public interface LanguageCodeDao {

    List<LanguageCode> getAllLanguageCode();

    LanguageCode getLanguageCodeByCode(@Param("code") Short code);

    LanguageCode getLanguageCodeByAliasName(@Param("aliasName") String aliasName);

    LanguageCode getLanguageCodeByName(@Param("name") String name);
}
