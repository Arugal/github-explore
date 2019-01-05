package net.abc.explore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.abc.explore.constant.ETimeCode;
import net.abc.tool.util.date.DateUtil;
import net.abc.tool.util.date.ETimeUnit;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhangwei
 * @date: 23:19/2018-12-31
 */
@Data
@NoArgsConstructor
public class TimeCode implements java.io.Serializable{

    private static final long serialVersionUID = 1264517842302281410L;

    private Short code;

    private String aliasName;

    private String name;

    private Long interval;

    private String timeUnit = "MINUTES";

    private TimeUnit unit;

    public TimeCode(Short code, String aliasName, String name, Long interval, String timeUnit) {
        this.code = code;
        this.aliasName = aliasName;
        this.name = name;
        this.interval = interval;
        this.timeUnit = timeUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeCode timeCode = (TimeCode) o;

        if (code != null ? !code.equals(timeCode.code) : timeCode.code != null) return false;
        if (aliasName != null ? !aliasName.equals(timeCode.aliasName) : timeCode.aliasName != null) return false;
        if (name != null ? !name.equals(timeCode.name) : timeCode.name != null) return false;
        if (interval != null ? !interval.equals(timeCode.interval) : timeCode.interval != null) return false;
        return timeUnit != null ? timeUnit.equals(timeCode.timeUnit) : timeCode.timeUnit == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (aliasName != null ? aliasName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (interval != null ? interval.hashCode() : 0);
        result = 31 * result + (timeUnit != null ? timeUnit.hashCode() : 0);
        return result;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public TimeUnit getUnit() {
        if(unit == null){
            unit = TimeUnit.valueOf(timeUnit);
        }
        return unit;
    }

    public static Date formatCurrentTime(TimeCode code){
        ETimeCode eCode = ETimeCode.getETimeCode(code.getCode());
        return formatCurrentTime(eCode);
    }

    public static Date formatCurrentTime(ETimeCode code){
        Date currentTime = new Date();
        switch (code){
            case DEILY:{
                currentTime = DateUtil.truncTime(currentTime, ETimeUnit.DAY);
                break;
            }
            case WEEKLY:{
                currentTime = DateUtil.truncTime(currentTime, ETimeUnit.WEEK);
                break;
            }
            case MONTHLY:{
                currentTime = DateUtil.truncTime(currentTime, ETimeUnit.MONTH);
                break;
            }
            default:{

            }
        }
        return currentTime;
    }
}
