package cn.yansui.module.main.config;

import de.felixroske.jfxsupport.SplashScreen;

/**
 * @Description 加载动画类
 * @Author maogen.ymg
 * @Date 2020/2/24 20:35
 */
public class LocalSplashScreen extends SplashScreen {
    @Override
    public boolean visible() {
        return true;
    }

    @Override
    public String getImagePath() {
        return "/images/login/entry_page.png";
    }
}