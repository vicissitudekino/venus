package cn.yansui;

import cn.yansui.domain.util.JFXBaseUtil;
import cn.yansui.module.main.config.LocalSplashScreen;
import cn.yansui.module.main.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description 启动类
 * @Author maogen.ymg
 * @Date 2020/2/24
 */
@SpringBootApplication(scanBasePackages="cn.yansui")
public class Application extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args){
        LocalSplashScreen splashScreen = new LocalSplashScreen();
        launch(Application.class, MainView.class, splashScreen, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        JFXBaseUtil.setMainScreenBounds(primaryStage);

        super.start(primaryStage);
    }


    @Override
    public void beforeInitialView(Stage stage, ConfigurableApplicationContext ctx) {
        super.beforeInitialView(stage, ctx);
        Scene scene = JFXBaseUtil.getDecoratorScene(stage, new AnchorPane());
        stage.setScene(scene);
        GUIState.setScene(scene);
    }

}
