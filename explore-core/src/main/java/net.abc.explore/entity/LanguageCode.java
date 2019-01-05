package net.abc.explore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangwei
 * @date: 23:04/2018-12-31
 */
@Data
@NoArgsConstructor
public class Language implements java.io.Serializable{

    private static final long serialVersionUID = 7724349035451670792L;

    private Short code;

    private String name;

    public Language(Short code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (code != null ? !code.equals(language.code) : language.code != null) return false;
        return name != null ? name.equals(language.name) : language.name == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
