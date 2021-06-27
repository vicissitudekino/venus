package cn.yansui.domain.util;

import cn.yansui.exception.UsualException;

/**
 * @Description 常用异常Util
 * @Author maogen.ymg
 * @Date 2020/2/27 15:13
 */
public class UsualExceptionUtil {

    private UsualExceptionUtil() {}

    /**
     * 单个字符串异常
     * @param msg errorMessage
     */
    public static void of(String msg) {
        throw new UsualException(msg);
    }

    /**
     * 判断为真抛出异常
     * @param judgement 判断
     * @param msg errorMessage
     */
    public static void booleanOf(Boolean judgement, String msg) {
        if (judgement) {
            throw new UsualException(msg);
        }
    }

    /**
     * 为null时抛出异常
     * @param obj Object
     * @param msg errorMessage
     */
    public static void nullOf(Object obj, String msg) {
        if (null == obj) {
            throw new UsualException(msg);
        }
    }

    /**
     * 运行异常
     * @param e RuntimeException
     */
    public static void runtimeOf(RuntimeException e){
        throw new UsualException(e);
    }

    public static void of(Exception e) {
        throw new UsualException(e);
    }
}
