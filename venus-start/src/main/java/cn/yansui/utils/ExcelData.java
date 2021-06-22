package cn.yansui.utils;
import java.util.List;

public class ExcelData {
    private String filePath;
    private List<String> title;
    public List<List<String>> data;

    public ExcelData() {
    }

    public List<String> getTitle() {
        return this.title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<List<String>> getData() {
        return this.data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
