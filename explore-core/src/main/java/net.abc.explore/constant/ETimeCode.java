package net.abc.explore.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangwei
 * @date: 11:13/2019-01-05
 */
public enum ETimeCode {

    // 日
    DEILY((short)1),
    // 周
    WEEKLY((short)2),
    // 月
    MONTHLY((short)3);

    private short code;

    ETimeCode(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    private static final Map<Short, ETimeCode> CODE_MAP = new HashMap<Short, ETimeCode>(){
        {
            for(ETimeCode code : ETimeCode.values()){
                put(code.code, code);
            }
        }
    };

    public static ETimeCode getETimeCode(Short code){
        return CODE_MAP.get(code);
    }
}
