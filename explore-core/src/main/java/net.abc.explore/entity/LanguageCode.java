package net.abc.explore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    public LanguageCode(Short code, String aliasName, String name) {
        this.code = code;
        this.aliasName = aliasName;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguageCode that = (LanguageCode) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (aliasName != null ? !aliasName.equals(that.aliasName) : that.aliasName != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (aliasName != null ? aliasName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
