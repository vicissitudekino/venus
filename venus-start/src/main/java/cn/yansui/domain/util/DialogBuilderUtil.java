package cn.yansui.domain.util;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * @Description 基于Jfoenix库的JFXDialog封装仿Android对话框的工具DialogBuilder
 * @Author maogen.ymg
 * @Date 2020/4/3 14:50
 */
@Slf4j
public class DialogBuilderUtil {

    private String title;
    private String message;
    private JFXButton negativeBtn = null;
    private JFXButton positiveBtn = null;
    private Window window;
    /**否定按钮文字颜色，默认灰色**/
    private Paint negativeBtnPaint = Paint.valueOf("#747474");
    /**确定按钮文字颜色**/
    private Paint positiveBtnPaint = Paint.valueOf("#0099ff");
    /**定位链接**/
    private Hyperlink hyperlink = null;
    private JFXAlert<String> alert;
    /**输入框**/
    private TextField textField = null;
    private OnInputListener onInputListener = null;

    /**
     * 显示确定框
     * @param judgement 判断条件
     * @param control 控件
     * @param title 标题
     * @param message 内容
     */
    public static void showPositiveAlert(Boolean judgement, Control control, String title, String message) {
        if(judgement) {
            new DialogBuilderUtil(control).setPositiveBtn("确定", () -> {})
                    .setTitle(title).setMessage(message).create();
            UsualExceptionUtil.of(title + message);
        }
    }

    /**
     * 显示确定框
     * @param control 控件
     * @param title 标题
     * @param message 内容
     */
    public static void showPositiveAlert(Control control, String title, String message) {
        new DialogBuilderUtil(control).setPositiveBtn("确定")
                .setTitle(title).setMessage(message).create();
    }

    /**
     * 显示确定框
     * @param window 窗口
     * @param title 标题
     * @param message 内容
     */
    public static void showPositiveAlert(Window window, String title, String message) {
        new DialogBuilderUtil(window).setPositiveBtn("确定")
                .setTitle(title).setMessage(message).create();
    }

    /**
     * 构造方法
     *
     * @param stage 任意一个控件
     */
    public DialogBuilderUtil(Stage stage) {
        window = stage.getScene().getWindow();
    }

    /**
     * 构造方法
     *
     * @param window 窗口
     */
    public DialogBuilderUtil(Window window) {
        this.window = window;
    }

    /**
     * 构造方法
     *
     * @param control 任意一个控件
     */
    public DialogBuilderUtil(Control control) {
        window = control.getScene().getWindow();
    }

    // ********可链式设定******//
    /**
     * 设置标题
     * @param title 标题
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置消息内容
     * @param message 内容
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置否定按钮
     * @param negativeBtnText 否定按钮内容
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setNegativeBtn(String negativeBtnText) {
        return setNegativeBtn(negativeBtnText, null, null);
    }

    /**
     * 设置否定按钮文字和文字颜色
     *
     * @param negativeBtnText 文字
     * @param color           文字颜色 十六进制 #fafafa
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setNegativeBtn(String negativeBtnText, String color) {
        return setNegativeBtn(negativeBtnText, null, color);
    }

    /**
     * 设置按钮文字和按钮文字颜色，按钮监听器和
     *
     * @param negativeBtnText 按钮文字
     * @param negativeBtnOnclickListener 按钮监听器
     * @param color                      文字颜色 十六进制 #fafafa
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setNegativeBtn(String negativeBtnText, OnClickListener negativeBtnOnclickListener, String color) {
        if (color != null) {
            this.negativeBtnPaint = Paint.valueOf(color);
        }
        return setNegativeBtn(negativeBtnText, negativeBtnOnclickListener);
    }


    /**
     * 设置按钮文字和点击监听器
     *
     * @param negativeBtnText            按钮文字
     * @param negativeBtnOnclickListener 点击监听器
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setNegativeBtn(String negativeBtnText, OnClickListener negativeBtnOnclickListener) {

        negativeBtn = new JFXButton(negativeBtnText);
        negativeBtn.setCancelButton(true);
        negativeBtn.setTextFill(negativeBtnPaint);
        negativeBtn.setButtonType(JFXButton.ButtonType.FLAT);
        negativeBtn.setOnAction(addEvent -> {
            alert.hideWithAnimation();
            if (negativeBtnOnclickListener != null) {
                negativeBtnOnclickListener.onClick();
            }
        });
        return this;
    }

    /**
     * 设置按钮文字
     *
     * @param positiveBtnText 文字
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setPositiveBtn(String positiveBtnText) {
        return setPositiveBtn(positiveBtnText, null);
    }

    /**
     * 设置按钮文字，颜色和点击监听器
     *
     * @param positiveBtnText            文字
     * @param positiveBtnOnclickListener 点击监听器
     * @param color                      颜色 十六进制 #fafafa
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setPositiveBtn(String positiveBtnText, OnClickListener positiveBtnOnclickListener, String color) {
        this.positiveBtnPaint = Paint.valueOf(color);
        return setPositiveBtn(positiveBtnText, positiveBtnOnclickListener);
    }

    /**
     * 设置按钮文字和监听器
     *
     * @param positiveBtnText            文字
     * @param positiveBtnOnclickListener 点击监听器
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setPositiveBtn(String positiveBtnText, OnClickListener positiveBtnOnclickListener) {
        positiveBtn = new JFXButton(positiveBtnText);
        positiveBtn.setDefaultButton(true);
        positiveBtn.setTextFill(positiveBtnPaint);
        positiveBtn.setOnAction(closeEvent -> {
            alert.hideWithAnimation();
            if (positiveBtnOnclickListener != null) {
                //回调onClick方法
                positiveBtnOnclickListener.onClick();
            }
        });
        return this;
    }

    /**
     * 设置浏览器打开链接
     * @param text URL
     * @return DialogBuilderUtil
     */
    public DialogBuilderUtil setHyperLink(String text) {
        hyperlink = new Hyperlink(text);
        hyperlink.setBorder(Border.EMPTY);
        hyperlink.setOnMouseClicked(event -> {
            if (text.contains("www") || text.contains("com") || text.contains("http")) {
                try {
                    Desktop.getDesktop().browse(new URI(text));
                } catch (IOException | URISyntaxException e) {
                    log.error("浏览器打开链接失败，原因为：{}", e.getLocalizedMessage(),e);
                }
            } else if (text.contains(File.separator)) {
                try {
                    Desktop.getDesktop().open(new File(text));
                } catch (IOException e) {
                    log.error("打开本地文本失败，原因为：{}", e.getLocalizedMessage(),e);
                }
            }
        });
        return this;
    }

    public DialogBuilderUtil setTextFieldText(OnInputListener onInputListener) {
        this.textField = new TextField();
        this.onInputListener = onInputListener;
        return this;
    }

    /**
     * 创建对话框并显示
     *
     * @return JFXAlert<String>
     */
    public JFXAlert<String> create() {
        alert = new JFXAlert<>((Stage) (window));
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        // 添加hyperlink超链接文本或者是输入框
        if (hyperlink != null) {
            // 一般使用HBox，这里特殊也使用VBox
            layout.setBody(new VBox(new Label(this.message), hyperlink));
        } else if (textField != null) {
            layout.setBody(new VBox(new Label(this.message), textField));
            positiveBtn.setOnAction(event -> {
                alert.setResult(textField.getText());
                alert.hideWithAnimation();
            });
        } else {
            layout.setBody(new VBox(new Label(this.message)));
        }

        //添加确定和取消按钮
        if (negativeBtn != null && positiveBtn != null) {
            layout.setActions(negativeBtn, positiveBtn);
        } else {
            if (negativeBtn != null) {
                layout.setActions(negativeBtn);
            } else if (positiveBtn != null) {
                layout.setActions(positiveBtn);
            }
        }

        alert.setContent(layout);
        Optional<String> input = alert.showAndWait();
        // 不为空，则回调接口
        if (input.isPresent() && !(input.toString().contains("关闭"))) {
            onInputListener.onGetText(input.get());
        }

        return alert;
    }

    public interface OnClickListener {
        void onClick();
    }
    public interface OnInputListener {
        void onGetText(String result);
    }
}
