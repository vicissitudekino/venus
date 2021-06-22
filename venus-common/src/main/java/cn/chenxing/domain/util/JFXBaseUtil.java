package cn.chenxing.domain.util;

import com.jfoenix.controls.JFXDecorator;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

/**
 * @Description JFX基础设置等
 * @Author maogen.ymg
 * @Date 2020/2/27 14:58
 */
public class JFXBaseUtil {
    private JFXBaseUtil() {}

    /**
     * 获取JFoenix面板--设置标题和图标和退出
     * @param stage 窗口
     * @param title 标题
     * @param iconUrl 图标Url
     * @param parent 面板
     * @param needExit 是否需要退出
     */
    public static JFXDecorator getDecorator(Stage stage, String title, String iconUrl, Parent parent, boolean needExit){
        JFXDecorator decorator = new JFXDecorator(stage, parent);
        decorator.setCustomMaximize(true);
        decorator.setTitle(title);
        if(StringUtils.isNotEmpty(iconUrl)){
            ImageView imageView = new ImageView(new Image(iconUrl));
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            decorator.setGraphic(imageView);
        }

        if(needExit) {
            decorator.setOnCloseButtonAction(() ->
                    new DialogBuilderUtil(stage).setNegativeBtn("取消", () -> {})
                            .setPositiveBtn("确定", () -> System.exit(0))
                            .setTitle("提示").setMessage("是否确定退出系统").create());
        }
        return decorator;
    }

    /**
     * 获取JFoenix面板--设置标题
     * @param stage 窗口
     * @param title 标题
     * @param parent 面板
     */
    public static JFXDecorator getDecorator(Stage stage, String title, Parent parent){
        JFXDecorator decorator = new JFXDecorator(stage, parent);
        decorator.setCustomMaximize(true);
        decorator.setTitle(title);
        return decorator;
    }

    /**
     * 获取JFoenix面板--基本窗口
     * @param stage 窗口
     * @param parent 面板
     */
    private static JFXDecorator getDecorator(Stage stage, Parent parent){
        JFXDecorator decorator = new JFXDecorator(stage, parent);
        decorator.setCustomMaximize(true);
        return decorator;
    }

    /**
     * 获取JFoenix Scene窗口
     * @param stage 窗口
     * @param root 显示的面板
     */
    public static Scene getDecoratorScene(Stage stage, Parent root){
        JFXDecorator decorator = getDecorator(stage, root);
        return getDecoratorScene(decorator);
    }

    /**
     * 获取JFoenix Scene窗口 -- 标题
     * @param stage 窗口
     * @param title 标题
     * @param root 显示的面板
     */
    public static Scene getDecoratorScene(Stage stage, String title, Parent root){
        JFXDecorator decorator = getDecorator(stage, title, root);
        return getDecoratorScene(decorator);
    }

    /**
     * 获取JFoenix Scene窗口--标题 图标 是否需要退出
     * @param stage 窗口
     * @param title 标题
     * @param iconUrl 图标
     * @param root 显示的面板
     * @param needExit 是否需要退出
     */
    public static Scene getDecoratorScene(Stage stage,String title, String iconUrl, Parent root, Boolean needExit){
        JFXDecorator decorator = getDecorator(stage,title,iconUrl,root, needExit);
        return getDecoratorScene(decorator);
    }

    /**
     * 获取JFoenix Scene窗口--标题 图标 关闭此窗口回到上一个窗口
     * @param stage 窗口
     * @param title 标题
     * @param iconUrl 图标
     * @param root 显示的面板
     * @param window 上一个窗口
     */
    public static Scene getDecoratorScene(Stage stage,String title, String iconUrl, Parent root, Window window){
        JFXDecorator decorator = getDecorator(stage,title,iconUrl,root, false);
        Stage stageWindow = (Stage) window;
        decorator.setOnCloseButtonAction(() -> {
            stageWindow.show();
            stage.close();
        });
        return getDecoratorScene(decorator);
    }

    /**
     * 获取JFoenix Scene窗口
     * @param decorator 显示的decorator面板
     */
    private static Scene getDecoratorScene(JFXDecorator decorator){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.width / 1.0;
        double height = screenSize.height / 1.0;
        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(JFXBaseUtil.class.getResource("/css/jfoenix-main.css").toExternalForm());
        return scene;
    }

    /**
     * 获取文件结构分析界面大小（适配注册，找回密码界面）
     * @param stage Stage
     */
    public static void setFileStructScreenBounds(Stage stage) {
        stage.setWidth(750.0);
        stage.setHeight(450.0);
    }

    /**
     * 获取登录界面大小（适配注册，找回密码界面）
     * @param stage Stage
     */
    public static void setLoginScreenBounds(Stage stage) {
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setWidth(primaryScreenBounds.getWidth()/4);
//        stage.setHeight(primaryScreenBounds.getHeight()/2);
        stage.setWidth(400.0);
        stage.setHeight(400.0);
    }

    /**
     * 获取主界面大小
     * @param stage Stage
     */
    public static void setMainScreenBounds(Stage stage) {
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setWidth(primaryScreenBounds.getWidth() / 2);
//        stage.setHeight(primaryScreenBounds.getHeight() / 1.8);
        stage.setWidth(650.0);
        stage.setHeight(450.0);
    }

}
