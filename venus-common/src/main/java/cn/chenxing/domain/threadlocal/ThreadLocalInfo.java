package cn.chenxing.domain.threadlocal;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import lombok.Data;

/**
 * @Description 进程保存的一些共用信息
 * @Author maogen.ymg
 * @Date 2020/3/6 15:15
 */
public class ThreadLocalInfo {
    private ThreadLocalInfo() {}

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
         * 当前登录的用户名
         */
        private String userName = "同学";

        /**
         * 上一个窗口
         */
        private Window previousWindow;

        //------------------File--------------------/
        /**
         * 文件AnchorPane
         */
        private AnchorPane filePane;
        /**
         * 本地数据AnchorPane
         */
        private AnchorPane localDataPane;
        /**
         * 在线数据AnchorPane
         */
        private AnchorPane onlineDataPane;
    }
}
