package net.abc.explore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zhangwei
 * @date: 22:49/2018-12-31
 */
@Data
@NoArgsConstructor
public class Repositorie implements java.io.Serializable{

    private static final long serialVersionUID = 6827089700196416168L;

    /**
     * repositorie id 数据库自增
     */
    private Integer id;

    /**
     * 语言编号
     */
    private Short languageCode;

    /**
     * 所有者
     */
    private String owner;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 描述
     */
    private String describe = "";

    /**
     * 首次更新时间
     */
    private Date firstExploreTime;

    /**
     * 最后更新时间
     */
    private Date lastExploreTime;

    public Repositorie(Short languageCode, String owner, String name, String describe, Date lastExploreTime) {
        this.languageCode = languageCode;
        this.owner = owner;
        this.name = name;
        this.describe = describe;
        this.lastExploreTime = lastExploreTime;
    }
}
