package cn.yansui.utils;

import cn.yansui.domain.util.AlertUtil;
import cn.yansui.domain.util.SpringContextUtil;
import de.felixroske.jfxsupport.AbstractFxmlView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description TabPane相关
 * @Author maogen.ymg
 * @Date 2020/4/14 12:49
 */
@Slf4j
public class TabPaneUtil {
    private TabPaneUtil() {}

    /**
     * TabPane添加Tab
     * @param tabPane TabPane
     * @param tabName tab名
     * @param viewClassName View的类地址
     */
    @SuppressWarnings("unchecked")
    public static void addTab(TabPane tabPane, String tabName, String viewClassName) {
        try {
            Class<AbstractFxmlView> viewClass = (Class<AbstractFxmlView>) ClassLoader.getSystemClassLoader().loadClass(viewClassName);
            AbstractFxmlView fxmlView = SpringContextUtil.getBean(viewClass);
            Tab tab = new Tab(tabName);
            tab.setContent(fxmlView.getView());

            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            //tab.setOnCloseRequest((Event event)->{
            //    JavaFxViewUtil.setControllerOnCloseRequest(fxmlView.getPresenter(),event);
            //});
        } catch (Exception e) {
            log.error("TabPane添加Tab失败，原因为：{}", e.getLocalizedMessage(), e);
            // 不能使用DialogBuilderUtil, tabPane使用为空
            AlertUtil.showWarnAlert("提示", "添加Tab失败，请检查！");
        }
    }
}
