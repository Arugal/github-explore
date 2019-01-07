package net.abc.explore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.abc.tool.util.base.ObjectUtils;
import net.abc.tool.util.string.StringUtils;

/**
 * @author: zhangwei
 * @date: 23:04/2018-12-31
 */
@Data
@NoArgsConstructor
public class LanguageCode implements java.io.Serializable{

    private static final long serialVersionUID = 7724349035451670792L;

    private Short code;

    private String aliasName;

    private String name;

    private String color;

    public LanguageCode(Short code, String aliasName, String name, String color) {
        this.code = code;
        this.aliasName = aliasName;
        this.name = name;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageCode that = (LanguageCode) o;

        return code != null ? code.equals(that.code) : that.code == null;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    public static boolean NullCheck(LanguageCode code){
        if(code == null){
            return false;
        }
        if(ObjectUtils.isEmpty(code.code)){
            return false;
        }
        if(StringUtils.isEmpty(code.aliasName)){
            return false;
        }
        if(StringUtils.isEmpty(code.name)){
            return false;
        }
        if(StringUtils.isEmpty(code.color)){
            return false;
        }
        return true;
    }

}
