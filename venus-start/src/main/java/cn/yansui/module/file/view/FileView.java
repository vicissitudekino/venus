package cn.yansui.module.file.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * @Description 文件视图层
 * @Author maogen.ymg
 * @Date 2020/3/19 22:25
 */
@Scope("prototype")
@Lazy
@FXMLView(value = "/fxml/file/File.fxml")
public class FileView extends AbstractFxmlView {
}
