package cn.yansui.domain.threadlocal;

import lombok.Data;

/**
 * @Description 进程保存的一些共用信息
 * @Author maogen.ymg
 * @Date 2020/3/6 15:15
 */
public class ThreadData {
    private ThreadData() {}

    private static ThreadLocal<CurrentThreadData> local = new ThreadLocal<>();

    public static CurrentThreadData get() {
        CurrentThreadData currentThreadData = local.get();
        if (currentThreadData == null) {
            local.set(new CurrentThreadData());
        }
        return local.get();
    }

    public static void set(CurrentThreadData data) {
        local.set(data);
    }

    public static void remove(){
        local.remove();
    }

    @Data
    public static class CurrentThreadData {
        /**
         * 文件地址
         **/
        private String fileUrl;
        /**
         * 是否包含速度字段
         **/
        private boolean hasSpeed;

    }
}
