package cn.yansui.module.main.controller;

import cn.yansui.domain.threadlocal.ThreadLocalInfo;
import cn.yansui.domain.util.AlertUtil;
import cn.yansui.domain.util.SpringContextUtil;
import cn.yansui.constant.VenusConstant;
import cn.yansui.entity.Cybercafe;
import cn.yansui.entity.Factory;
import cn.yansui.exception.BizException;
import cn.yansui.module.main.service.MainService;
import cn.yansui.utils.ExcelData;
import cn.yansui.utils.ExcelUtil;
import cn.yansui.utils.ExportUtil;
import cn.yansui.utils.FileChooserUtil;
import cn.hutool.poi.excel.ExcelFileUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.PropertySource;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static cn.yansui.utils.ExcelUtil.exportFile;

/**
 * @Description 主界面Controller层
 * @Author maogen.ymg
 * @Date 2020/3/11 12:30
 */
@Lazy
@FXMLController
@Slf4j
public class MainController implements Initializable {

    @FXML
    private JFXTextArea scaleproportion;

    @FXML
    private Label welcome;

    @FXML
    private Label introduce;

    @FXML
    private Button uploadFile;

    @FXML
    private Button handleFile;

    @FXML
    private TextField proportion;

    @FXML
    private TextField filePath;

    @FXML
    private String directoryPath;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextArea consoleInfo;


    @FXML
    private TableColumn<Cybercafe, String> cybercafeColumn;

    public List<Cybercafe> cybercafeData = new ArrayList<>();
    public List<Cybercafe> cybercafeDataEdit = new ArrayList<>();

    private List<Cybercafe> resultList27 = new ArrayList<>();
    private List<Cybercafe> resultList37 = new ArrayList<>();
    private List<Cybercafe> resultListEdit27 = new ArrayList<>();
    private List<Cybercafe> resultListEdit37 = new ArrayList<>();
    private List<Cybercafe> resultListNeedEdit27 = new ArrayList<>();
    private List<Cybercafe> resultListNeedEdit37 = new ArrayList<>();
    private List<Factory> factoryData = new ArrayList<>();
    private List<Factory> factoryDataEdit = new ArrayList<>();
    private List<Factory> factoryDataEdit27 = new ArrayList<>();
    private List<Factory> factoryDataEdit37 = new ArrayList<>();

    @Autowired
    MainService mainService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        borderPane.prefWidthProperty().bind(anchorPane.widthProperty());//宽度绑定为Pane宽度
        init();
    }

    private void init() {
//        this.cybercafeColumn.setCellValueFactory((cellData) -> {
//            return cellData.getValue();
////        });
//        this.cybercafeColumn.setCellFactory(new Callback<TableColumn<Cybercafe, String>, TableCell<Cybercafe, String>>() {
//            public TableCell<Cybercafe, String> call(TableColumn param) {
//                return new TableCell<Cybercafe, String>() {
//                    ObservableValue ov;
//                    public void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (!this.isEmpty()) {
//                            this.ov = this.getTableColumn().getCellObservableValue(this.getIndex());
//                            this.setText(item);
//                        }
//
//                    }
//                };
//            }
//        });


    }


    @FXML
    private void uploadFile() throws IOException {
        File file = FileChooserUtil.chooseTableFile("选择文件");

        if (null != file) {
            this.filePath.setText(file.getPath());
            File excelFile = new File(this.filePath.getText());
            this.cybercafeData.clear();
            log.info(file.getPath());
            directoryPath = file.getPath().substring(0, file.getPath().lastIndexOf("\\"));
            this.consoleInfo.appendText(directoryPath);
            this.consoleInfo.appendText(directoryPath + "\\" + "计算文件.xlsx");
            FileInputStream is = null;
            try {
                is = new FileInputStream(excelFile);
            } catch (FileNotFoundException var7) {
                var7.printStackTrace();
            }
            ExcelData data = ExcelUtil.readByInputstream(excelFile.getName(), is, false);
            Iterator var5 = data.getData().iterator();
            int rollNums = 1;
            while (var5.hasNext()) {
                List<String> Content = (List) var5.next();
                if (Content.get(0) == null) {
                    this.cybercafeData.add(new Cybercafe(null, null, rollNums, (String) Content.get(3), Integer.valueOf(Content.get(4)), Integer.valueOf(Content.get(5)), Integer.valueOf(Content.get(6)), Integer.valueOf(Content.get(7))));
//                    this.cybercafeDataEdit.add(new Cybercafe(null, null, rollNums, (String) Content.get(3), Integer.valueOf(Content.get(4)), Integer.valueOf(Content.get(5)), Integer.valueOf(Content.get(6)), Integer.valueOf(Content.get(7))));
                } else {
                    this.cybercafeData.add(new Cybercafe(Integer.valueOf(Content.get(0)), (String) Content.get(1), rollNums, (String) Content.get(3), Integer.valueOf(Content.get(4)), Integer.valueOf(Content.get(5)), Integer.valueOf(Content.get(6)), Integer.valueOf(Content.get(7))));
//                    this.cybercafeDataEdit.add(new Cybercafe(Integer.valueOf(Content.get(0)), (String) Content.get(1), rollNums, (String) Content.get(3), Integer.valueOf(Content.get(4)), Integer.valueOf(Content.get(5)), Integer.valueOf(Content.get(6)), Integer.valueOf(Content.get(7))));

                }
                if (rollNums == 1) {
                    this.factoryDataEdit.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));
                    if (Integer.valueOf(Content.get(7)) != 0) {
                        this.factoryDataEdit37.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));
                    } else {
                        this.factoryDataEdit27.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));
                    }

                } else {
                    if (Content.get(0) != null) {
                        int s = this.factoryDataEdit.get(factoryDataEdit.size() - 1).getId();
                        if (!Integer.valueOf(Content.get(0)).equals(s)) {
                            this.factoryDataEdit.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));
                            if (Integer.valueOf(Content.get(7)) != 0) {
                                this.factoryDataEdit37.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));
                            } else {
                                this.factoryDataEdit27.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));
                            }
                        }
                    }

                }

//


                rollNums++;
            }
        }

    }

    @FXML
    private void handleDetailsFile() {
//        List<Cybercafe> cybercafeSortName = cybercafeData.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
//        this.consoleInfo.appendText(cybercafeSortName.toString());
        if (AlertUtil.showConfirmAlert("是否确认开始计算？")) {
            this.consoleInfo.appendText("开始计算\n");
            File excelFile = new File(this.filePath.getText());
            FileInputStream is = null;
            try {
                is = new FileInputStream(excelFile);
            } catch (FileNotFoundException var17) {
                var17.printStackTrace();
            }

            ExcelData data = ExcelUtil.readByInputstream(excelFile.getName(), is, false);
            String filePathStr = this.filePath.getText();
            this.consoleInfo.appendText("计算进度如下：\n");
            Iterator var6 = data.getData().iterator();

            while (var6.hasNext()) {
                List content;
                for (content = (List) var6.next(); filePathStr.endsWith("\\") || filePathStr.endsWith("/"); filePathStr = filePathStr.substring(0, filePathStr.length() - 1)) {
                }
            }

            try {
                String afterfile = directoryPath + "\\" + "计算文件结果" + ".xlsx";
                this.scaleAll();
                cybercafeData = cybercafeData.stream().sorted(Comparator.comparing(Cybercafe::getFactoryId)).collect(Collectors.toList());
                cybercafeDataEdit = cybercafeDataEdit.stream().sorted(Comparator.comparing(Cybercafe::getFactoryId)).collect(Collectors.toList());
                try {
                    HSSFWorkbook workbook;
                    workbook = new HSSFWorkbook();
                    HSSFSheet sheet1=workbook.createSheet("最优算法");
                    HSSFSheet sheet2=workbook.createSheet("基于原先算法");
                    ExcelUtil.exportFile(this.cybercafeData, this.factoryData, sheet1, data,workbook);
                    ExcelUtil.exportFile(this.cybercafeDataEdit, this.factoryDataEdit, sheet2, data, workbook);
                    FileOutputStream os = new FileOutputStream(afterfile);
                    workbook.write(os);
                    os.close();
                } catch (IOException var4) {
                    var4.printStackTrace();
                    throw new BizException("生成文件流出错");
                }

            } catch (Exception e) {
                log.info("计算失败，请重试");
            }
//            AlertUtil.showInfoAlert("执行完毕，请去上传文件的目录下查看计算结果");


        }
    }

    @FXML
    protected void clearClick() {
        this.proportion.clear();
        this.filePath.clear();
    }

    public void scaleAll() {
        cybercafeSort(cybercafeData);
        cybercafeData.clear();
        factoryData = scale(resultList27, "27");
        List<Factory> factoryData1 = scale(resultList37, "37");
        factoryData.addAll(factoryData1);
        factoryDataEdit.clear();
        cybercafeDataEdit.clear();
        factoryDataEdit27 = scaleEdit(resultListNeedEdit27, resultListEdit27, "27", factoryDataEdit27);
        factoryDataEdit37 = scaleEdit(resultListNeedEdit37, resultListEdit37, "37", factoryDataEdit37);
        factoryDataEdit.addAll(factoryDataEdit27);
        factoryDataEdit.addAll(factoryDataEdit37);
    }

    public void cybercafeSort(List<Cybercafe> cybercafeData) {
        for (int i = 0; i < cybercafeData.size(); i++) {
            if (cybercafeData.get(i).getNums37() > 0) {
                Cybercafe cybercafe=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                resultList37.add(cybercafe);
                if (null != cybercafeData.get(i).getFactoryId()) {
                    Cybercafe cybercafe1=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                    resultListEdit37.add(cybercafe1);
                } else {
                    Cybercafe cybercafe1=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                    resultListNeedEdit37.add(cybercafe1);
                }
            } else {
                Cybercafe cybercafe=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                resultList27.add(cybercafe);
                if (null != cybercafeData.get(i).getFactoryId()) {
                    Cybercafe cybercafe1=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                    resultListEdit27.add(cybercafe1);
                } else {
                    Cybercafe cybercafe1=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                    resultListNeedEdit27.add(cybercafe1);
                }
            }
        }
    }

    public List scale(List<Cybercafe> resultList, String group) {
        int y = 0;
        List<Cybercafe> cybercafeSort = resultList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        List<Factory> factoryList = new ArrayList<>();
        while (cybercafeSort.size() > 0) {
            int num1 = 200 - cybercafeSort.get(cybercafeSort.size() - 1).getDisklessNums();
            Double scaleProportion = Double.parseDouble(this.proportion.getText());
            Double num2 = Double.valueOf(scaleProportion) * num1;
            Integer num3 = num2.intValue();
            int m = 0;
            int t = 0;
            int n = cybercafeSort.size() - 1;
            String s = group + y;
            for (int x = 0; x < num3; ) {
                while (t <= num3) {
                    t = m + cybercafeSort.get(n).getTerminalNums();
                    if (t < num3) {
                        m += cybercafeSort.get(n).getTerminalNums();
                        cybercafeSort.get(n).setFactoryId(Integer.parseInt(s));
                        cybercafeData.add(cybercafeSort.get(n));
                        cybercafeSort.remove(n);
                        if (n == 0) {
                            break;
                        } else {
                            n--;
                        }

                    }
                }
                if (t > num3) {
                    while (n >= 0) {
                        int l = cybercafeSort.get(n).getTerminalNums();
                        int j = m + l;
                        if (j <= num3) {
                            m += cybercafeSort.get(n).getTerminalNums();
                            cybercafeSort.get(n).setFactoryId(Integer.parseInt(s));
                            cybercafeData.add(cybercafeSort.get(n));
                            cybercafeSort.remove(n);
                            n--;
                        } else {
                            n--;
                        }
                    }
                }
                if (n == -1) {
                    break;
                }
                if (cybercafeSort.size() == 0) {
                    break;
                }
            }
            y++;
            Factory factory = new Factory(Integer.parseInt(s), s, num1);
            factoryList.add(factory);

        }
        return factoryList;
    }

    public List scaleEdit(List<Cybercafe> resultNeedEditList, List<Cybercafe> resultEditList, String group, List<Factory> factoryEditList) {
        int y = factoryEditList.size() - 1;
        List<Cybercafe> cybercafeSort = resultEditList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        cybercafeDataEdit.addAll(cybercafeSort);
        List<Cybercafe> cybercafeNeedEditSort = resultNeedEditList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        Map<Integer, List<Cybercafe>> cybercafeGroup = resultEditList.stream().collect(Collectors.groupingBy(Cybercafe::getFactoryId, Collectors.toList()));
        List<Factory> factorySort = factoryEditList.stream().sorted(Comparator.comparing(Factory::getNumber)).collect(Collectors.toList());
        List<Cybercafe> cycybercafeScaleList = new ArrayList<>();
        List<Integer> terminalNumsGroup = new ArrayList<>();
        List<Integer> maxDisklessNumsGroup = new ArrayList<>();
        for (Integer key : cybercafeGroup.keySet()) {
            List<Cybercafe> test = cybercafeGroup.get(key);
            Integer sum = test.stream().mapToInt(Cybercafe::getTerminalNums).sum();
            Integer maxSum = cybercafeGroup.get(key).stream().mapToInt(Cybercafe::getDisklessNums).max().getAsInt();
            terminalNumsGroup.add(sum);
            maxDisklessNumsGroup.add(maxSum);
        }
        int needRollNums = cybercafeNeedEditSort.size();
        while (needRollNums > 0) {
            int num1 = factorySort.get(y).getNumber();
            Double scaleProportion = Double.parseDouble(this.proportion.getText());
            Double num2 = Double.valueOf(scaleProportion) * num1;
            Integer num3 = num2.intValue();
            int m = 0;
            int t = 0;
            int n = resultNeedEditList.size() - 1;
            int l = cybercafeGroup.size() - 1;
            for (Integer key : cybercafeGroup.keySet()) {
                if (cybercafeNeedEditSort.size() == 0) {
                    break;
                }
                n = cybercafeNeedEditSort.size() - 1;
                Integer num = cybercafeNeedEditSort.get(n).getTerminalNums() + terminalNumsGroup.get(l);
                if (cybercafeNeedEditSort.get(n).getDisklessNums() <= maxDisklessNumsGroup.get(l) && num <= num3) {
                    Integer x = cybercafeGroup.get(key).get(0).getFactoryId();
                    cybercafeNeedEditSort.get(n).setFactoryId(x);
                    cybercafeSort.add(cybercafeNeedEditSort.get(n));
                    cybercafeDataEdit.add(cybercafeNeedEditSort.get(n));
                    cybercafeNeedEditSort.remove(n);
                    cybercafeSort = cybercafeSort.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());

                } else {
                    if (l == 0) {
                        cycybercafeScaleList.add(cybercafeNeedEditSort.get(n));
                        cybercafeNeedEditSort.remove(n);
                    }
                }
                l--;
            }
            needRollNums--;

        }
        List<Factory> factoryList1 = scaleTest(cycybercafeScaleList, group);
        factoryEditList.addAll(factoryList1);
        return factoryEditList;
    }


    public List scaleTest(List<Cybercafe> resultList, String group) {
        int y = 0;
        List<Cybercafe> cybercafeSort = resultList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        List<Factory> factoryList = new ArrayList<>();
        while (cybercafeSort.size() > 0) {
            int num1 = 200 - cybercafeSort.get(cybercafeSort.size() - 1).getDisklessNums();
            Double scaleProportion = Double.parseDouble(this.proportion.getText());
            Double num2 = Double.valueOf(scaleProportion) * num1;
            Integer num3 = num2.intValue();
            int m = 0;
            int t = 0;
            int n = cybercafeSort.size() - 1;
            String s = group + y;
            for (int x = 0; x < num3; ) {
                while (t <= num3) {
                    t = m + cybercafeSort.get(n).getTerminalNums();
                    if (t < num3) {
                        m += cybercafeSort.get(n).getTerminalNums();
                        cybercafeSort.get(n).setFactoryId(Integer.parseInt(s));
                        cybercafeDataEdit.add(cybercafeSort.get(n));
                        cybercafeSort.remove(n);
                        if (n == 0) {
                            break;
                        } else {
                            n--;
                        }

                    }
                }
                if (t > num3) {
                    while (n >= 0) {
                        int l = cybercafeSort.get(n).getTerminalNums();
                        int j = m + l;
                        if (j <= num3) {
                            m += cybercafeSort.get(n).getTerminalNums();
                            cybercafeSort.get(n).setFactoryId(Integer.parseInt(s));
                            cybercafeDataEdit.add(cybercafeSort.get(n));
                            cybercafeSort.remove(n);
                            n--;
                        } else {
                            n--;
                        }
                    }
                }
                if (n == -1) {
                    break;
                }
                if (cybercafeSort.size() == 0) {
                    break;
                }
            }
            y++;
            Factory factory = new Factory(Integer.parseInt(s), s, num1);
            factoryList.add(factory);

        }
        return factoryList;
    }


}
