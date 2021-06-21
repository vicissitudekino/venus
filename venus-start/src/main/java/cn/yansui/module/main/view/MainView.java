package cn.yansui.module.main.view;

import cn.yansui.domain.util.JFXBaseUtil;
import cn.yansui.constant.VenusConstant;
import com.jfoenix.controls.JFXDecorator;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import de.felixroske.jfxsupport.GUIState;
import javafx.scene.Parent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * @Description 主界面视图类
 * @Author maogen.ymg
 * @Date 2020/3/9 21:56
 */
@Scope("prototype")
@Lazy
@FXMLView(value = "/fxml/Main.fxml")
public class MainView extends AbstractFxmlView {
    @Override
    public Parent getView() {
        // 解决@Autowired注入问题等，以及Cannot set style once stage has been set visible问题
        JFXDecorator decorator;
        decorator = JFXBaseUtil.getDecorator(GUIState.getStage(), VenusConstant.MAIN_TITLE, VenusConstant.ICON_URL, super.getView(), true);
        return decorator;
    }
}
