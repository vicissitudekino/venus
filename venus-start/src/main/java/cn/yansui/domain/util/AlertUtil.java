package cn.yansui.domain.util;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Optional;

/**
 * @Description 弹框信息
 * @Author maogen.ymg
 * @Date 2020/2/28 15:05
 */
public class AlertUtil {
    private AlertUtil() {}

    // ----------------------------提示框---------------------------
    /**
     * 信息提示框--信息
     * @param message 信息
     */
    public static void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * 信息提示框--标题 + 信息
     * @param title 标题
     * @param message 信息
     */
    public static void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * 等待信息提示框--信息
     * @param message 信息
     */
    public static void showAndWaitInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * 等待信息提示框--标题 + 信息
     * @param title 标题
     * @param message 信息
     */
    public static void showAndWaitInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ----------------------------警告框---------------------------
    /**
     * 信息警告框--信息
     * @param message 信息
     */
    public static void showWarnAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * 信息警告框--标题 + 信息
     * @param title 标题
     * @param message 信息
     */
    public static void showWarnAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * 信息警告框--判断条件 + 标题 + 信息
     * @param judgement 判断条件
     * @param title 标题
     * @param message 信息
     */
    public static void showWarnAlert(Boolean judgement, String title, String message) {
        if (judgement) {
            showWarnAlert(title, message);
        }
    }

    // ----------------------------异常框---------------------------
    /**
     * 信息异常框--信息
     * @param message 信息
     */
    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
        UsualExceptionUtil.of(String.format("运行错误，原因为： %s", message));
    }

    /**
     * 信息异常框--标题 + 信息
     * @param title 标题
     * @param message 信息
     */
    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
        UsualExceptionUtil.of(String.format("运行错误，原因为： %s", message));
    }

    /**
     * 信息异常框--判断条件 + 标题 + 信息
     * @param judgement 判断条件
     * @param message 信息
     */
    public static void showErrorAlert(Boolean judgement, String title, String message) {
        if (judgement) {
            showErrorAlert(title, message);
        }
    }

    // ----------------------------确认框---------------------------
    /**
     * 信息确认框--信息
     * @param message 信息
     * @return boolean
     */
    public static boolean showConfirmAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        Optional<ButtonType> optional = alert.showAndWait();
        return optional.isPresent() && ButtonType.OK == optional.get();
    }

    /**
     * 信息确认框--标题 + 信息
     * @param title 标题
     * @param message 信息
     * @return boolean
     */
    public static boolean showConfirmAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> optional = alert.showAndWait();
        return optional.isPresent() && ButtonType.OK == optional.get();
    }

    // ----------------------------输入框---------------------------
    /**
     * 信息输入提示框--提示标题
     * @param title 提示标题
     * @return String
     */
    public static String showInputAlert(String title) {
        return showInputAlertDefaultValue(title, null);
    }

    /**
     * 信息输入提示框--提示标题 + 输入框默认值
     * @param title 提示标题
     * @param defaultValue 输入框默认值
     * @return String
     */
    public static String showInputAlertDefaultValue(String title, String defaultValue) {
        TextField textField = new TextField();
        textField.setText(defaultValue);
        textField.setMinWidth(100);
        textField.prefColumnCountProperty().bind(textField.textProperty().length());
        Alert alert = new Alert(Alert.AlertType.NONE, null,
                new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
        alert.setTitle(title);
        alert.setGraphic(textField);
        alert.setWidth(200);
        Optional<ButtonType> buttonType = alert.showAndWait();
        // 根据点击结果返回
        if (buttonType.isPresent() && buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            return textField.getText();
        }
        return null;
    }

    /**
     * 信息输入提示框--提示标题 + 文本名称
     * @param title 提示标题
     * @param names 文本名称
     * @return String[]
     */
    public static String[] showInputAlertMore(String title, String... names) {
        return showInputAlertMore(title, names, new String[names.length]);
    }

    /**
     * 多个输入框--标题 + 文本名称 + 输入框默认值
     * @param title 提示标题
     * @param names 文本名称
     * @param defaultValue 输入框默认值
     * @return String[]
     */
    public static String[] showInputAlertMore(String title, String[] names, String[] defaultValue) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        TextField[] textFields = new TextField[names.length];
        for (int i = 0; i < names.length; i++) {
            TextField textField = new TextField();
            textField.setText(defaultValue[i]);
            textField.setMinWidth(100);
            textField.prefColumnCountProperty().bind(textField.textProperty().length());
            GridPane.setHgrow(textField, Priority.ALWAYS);
            gridPane.add(new Label(names[i]), 0, i);
            gridPane.add(textField, 1, i);
            textFields[i] = textField;
        }
        Alert alert = new Alert(Alert.AlertType.NONE, null,
                new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
        alert.setTitle(title);
        alert.setWidth(200);
        alert.setGraphic(gridPane);
        Optional<ButtonType> buttonType = alert.showAndWait();
        String[] stringS = new String[names.length];
        if (buttonType.isPresent() && buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            for (int i = 0; i < textFields.length; i++) {
                stringS[i] = textFields[i].getText();
            }
        }
        return stringS;
    }
}
