package cn.chenxing.module.file.controller;

import cn.chenxing.domain.threadlocal.ThreadLocalInfo;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Description
 * @Author maogen.ymg
 * @Date 2020/3/19 23:53
 */
@Lazy
@FXMLController
@Slf4j
public class FileController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ThreadLocalInfo.get().setFilePane(anchorPane);
    }

    @FXML
    private void switchLocalData() {
    }

    @FXML
    private void switchOnlineData() {
    }
}
