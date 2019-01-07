package net.abc.explore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zhangwei
 * @date: 23:06/2018-12-31
 */
@Data
@NoArgsConstructor
public class Trending implements java.io.Serializable, Comparable<Trending>{

    private static final long serialVersionUID = -9088547826452398568L;

    /**
     * trending id 数据库自增
     */
    private Integer id;

    /**
     * 仓库ID
     */
    private Integer repositorieId;

    /**
     * trending 日期
     */
    private Date occurTime;

    /**
     * 时间编号
     */
    private Short timeCode;

    /**
     * 语言编号
     */
    private Short languageCode;

    /**
     * 排名
     */
    private Short rank;

    /**
     * 上一个排名
     */
    private Short lastRank;

    /**
     * star 数量
     */
    private Integer star;

    /**
     * fork 数量
     */
    private Integer fork;

    /**
     * 新增 star 数量
     */
    private Integer newStar;


    // 页面展示时使用
    /**
     * 是否新增
     */
    private boolean isNew;

    /**
     * 连续天数
     */
    private Integer consecutiveDays;

    /**
     * 仓库, 方便操作
     */
    private Repositorie repositorie;

    public Trending(Date occurTime, Short timeCode, Short languageCode, Short rank, Integer star, Integer fork, Integer newStar) {
        this.occurTime = occurTime;
        this.timeCode = timeCode;
        this.languageCode = languageCode;
        this.rank = rank;
        this.star = star;
        this.fork = fork;
        this.newStar = newStar;
    }

    @Override
    public int compareTo(Trending o) {
        return -this.occurTime.compareTo(o.getOccurTime());
    }
}
