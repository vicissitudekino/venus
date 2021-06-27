package cn.yansui.domain.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.charset.Charset;

/**
 * @Description 文件选择器
 * @Author maogen.ymg
 * @Date 2020/3/13 14:05
 */
public class FileChooserUtil {
    public enum FileType {
        /**文件*/
        FILE,
        /**文件夹*/
        FOLDER
    }

    /**
     * 选择文件夹
     * @return File
     */
    public static File chooseDirectory() {
        File file = null;
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            file = directoryChooser.showDialog(null);
        } catch (NullPointerException e) {
            UsualExceptionUtil.of("选择文件夹打开错误" + e + e.getMessage());
        }
        return file;
    }

    /**
     * 文件选择框
     * @param dialogTitle 对话框标题
     * @return File
     */
    public static File chooseFile(String dialogTitle) {
        return chooseFile(dialogTitle, (FileChooser.ExtensionFilter) null);
    }

    /**
     * ·表格类型文件选择器
     * @param dialogTitle 对话框标题
     * @return File
     */
    public static File chooseTableFile(String dialogTitle) {
        return chooseFile(dialogTitle,
                new FileChooser.ExtensionFilter("All File", "*.*"),
                new FileChooser.ExtensionFilter("文本文档", "*.txt"),
                new FileChooser.ExtensionFilter("Excel工作簿", "*.xlsx"),
                new FileChooser.ExtensionFilter("Excel 97-2003工作簿", "*.xls"),
                new FileChooser.ExtensionFilter("CSV(逗号分隔)", "*.csv"));
    }

    /**
     * 图片类型文件选择器
     * @param dialogTitle 对话框标题
     * @return File
     */
    public static File chooseImageFile(String dialogTitle) {
        return chooseFile(dialogTitle,
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("ICO", "*.ico"),
                new FileChooser.ExtensionFilter("RGBE", "*.rgbe"));
    }

    /**
     * 文件选择框 + 文件过滤器
     * @param dialogTitle 对话框标题
     * @param extensionFilter 文件过滤
     * @return File
     * new FileChooser.ExtensionFilter("文本文件 (*.*.txt)", "*.txt")
     */
    public static File chooseFile(String dialogTitle, FileChooser.ExtensionFilter... extensionFilter) {
        File file = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(dialogTitle);
            fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
            if (extensionFilter != null) {
                fileChooser.getExtensionFilters().addAll(extensionFilter);
            }
            file = fileChooser.showOpenDialog(null);
        } catch (NullPointerException e) {
            UsualExceptionUtil.of("文件选择器打开错误" + e + e.getMessage());
        }
        return file;
    }


    /**
     * 保存为表格类型
     * @param dialogTitle 对话框标题
     * @param fileName 初始文件名
     * @return File
     */
    public static File saveTableFile(String dialogTitle, String fileName) {
        return chooseSaveFile(dialogTitle, fileName,
                new FileChooser.ExtensionFilter("All File", "*.*"),
                new FileChooser.ExtensionFilter("文本文档", "*.txt"),
                new FileChooser.ExtensionFilter("CSV(逗号分隔)", "*.csv"));
    }

    /**
     * 保存文件选择框
     * @param fileName 保存文件的文件名
     * @return File
     */
    public static File chooseSaveFile(String fileName) {
        return chooseSaveFile(fileName, (FileChooser.ExtensionFilter) null);
    }

    /**
     * 保存文件选择框
     * @param dialogTitle 对话框标题
     * @param extensionFilter 文件过滤
     * @return File
     */
    public static File chooseSaveFile(String dialogTitle, FileChooser.ExtensionFilter... extensionFilter) {
        return chooseSaveFile(dialogTitle,null, extensionFilter);
    }

    /**
     * 保存文件选择框
     * @param dialogTitle 对话框标题
     * @param fileName 初始文件名
     * @param extensionFilter 文件过滤
     * @return File
     */
    public static File chooseSaveFile(String dialogTitle, String fileName, FileChooser.ExtensionFilter... extensionFilter) {
        File file = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(dialogTitle);
            fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
            if (fileName != null) {
                fileChooser.setInitialFileName(fileName);
            }
            if (extensionFilter != null) {
                fileChooser.getExtensionFilters().addAll(extensionFilter);
            }
            file = fileChooser.showSaveDialog(null);
        } catch (NullPointerException e) {
            UsualExceptionUtil.of("保存文件选择器打开错误" + e + e.getMessage());
        }
        return file;
    }

    /**
     * 添加文件拖拽获取路径 ---- 路径
     * @param textField 文本区域
     * @param fileType 文件类型(FILE, FOLDER)
     */
    public static void setOnDrag(TextField textField, FileType fileType) {
        textField.setOnDragOver(event -> {
            if (event.getGestureSource() != textField) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        textField.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                try {
                    File file = dragboard.getFiles().get(0);
                    if (file != null) {
                        boolean isFile = fileType == FileType.FILE && file.isFile();
                        boolean isFolder = fileType == FileType.FOLDER && file.isDirectory();
                        if (isFile || isFolder) {
                            textField.setText(file.getAbsolutePath());
                        }
                    }
                } catch (Exception e) {
                    UsualExceptionUtil.of("文件拖拽获取路径错误" + e + e.getMessage());
                }
            }
        });
    }

    /**
     * 添加文件拖拽获取文件内容 ----- 内容
     * @param textField 文本区域
     */
    public static void setOnDragByOpenFile(TextInputControl textField) {
        textField.setOnDragOver(event -> {
            if (event.getGestureSource() != textField) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        textField.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                try {
                    File file = dragboard.getFiles().get(0);
                    if (file != null && file.isFile()) {
                        textField.setAccessibleText(file.getAbsolutePath());
                        textField.setText(FileUtils.readFileToString(file, Charset.defaultCharset()));
                    }
                } catch (Exception e) {
                    UsualExceptionUtil.of("文件拖拽获取路径错误" + e + e.getMessage());
                }
            }
        });
    }

}
