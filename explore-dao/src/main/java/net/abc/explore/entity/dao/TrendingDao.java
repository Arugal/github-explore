package net.abc.explore.entity.dao;

import net.abc.explore.entity.Trending;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 20:56/2019-01-04
 */
@Mapper
public interface TrendingDao {

    int add(Trending trending);

    int update(Trending trending);

    int delete(@Param("timeCode") Short timeCode, @Param("languageCode") Short languageCode,
               @Param("occurTime") Date occurTime);

    /**
     * 查询展示名单
     * @param timeCode
     * @param languageCode
     * @param occurTime
     * @return
     */
    List<Trending> getTrending(@Param("timeCode") Short timeCode, @Param("languageCode") Short languageCode,
                               @Param("occurTime") Date occurTime, @Param("repositorieId") Integer repositorieId);

}
