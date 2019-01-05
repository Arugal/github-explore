package net.abc.explore.entity.dao;

import net.abc.explore.entity.Repositorie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: zhangwei
 * @date: 20:57/2019-01-04
 */
@Mapper
public interface RepositorieDao {

    int add(Repositorie repositorie);

    int update(Repositorie repositorie);

    Repositorie getRepositorie(@Param("id") Integer id, @Param("owner") String owner, @Param("name") String name);
}
