package cn.chenxing.domain.exception;

import org.slf4j.helpers.MessageFormatter;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description 通常用的异常类
 * @Author maogen.ymg
 * @Date 2020/2/27 15:27
 */
public class UsualException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object errorObject;

    /**
     * 空
     */
    public UsualException() {
    }

    /**
     * 单个异常信息字符串
     * @param errorMsg 异常信息
     */
    public UsualException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * 多个异常信息字符串
     * @param format format
     * @param argArray 动态参数
     */
    public UsualException(String format, String... argArray) {
        super(MessageFormatter.arrayFormat(format, argArray).getMessage());
    }

    /**
     * Object是Throwable的父类
     * @param errorMsg Object
     */
    public UsualException(Object errorMsg) {
        super(errorMsg != null ? errorMsg.toString() : "");
    }

    /**
     * Throwable是Error和Exception的父类，用来定义所有可以作为异常被抛出来的类
     * @param e Throwable
     */
    public UsualException(Throwable e) {
        super(e);
    }

    /**
     * 运行时异常又称不受检查异常,比如经典的1/0，空指针等
     * @param e RuntimeException
     */
    public UsualException(RuntimeException e) {
        super(e);
    }

    public UsualException(String errorMsg, Map<String, Object> errorMap) {
        super(errorMsg);
        this.errorObject = errorMap;
    }

    public Object getErrorData() {
        return this.errorObject;
    }
}
