package cn.chenxing.module.main.controller;

import cn.chenxing.domain.threadlocal.ThreadLocalInfo;
import cn.chenxing.domain.util.AlertUtil;
import cn.chenxing.domain.util.SpringContextUtil;
import cn.chenxing.constant.VenusConstant;
import cn.chenxing.entity.Cybercafe;
import cn.chenxing.entity.Factory;
import cn.chenxing.exception.BizException;
import cn.chenxing.module.main.service.MainService;
import cn.chenxing.utils.ExcelData;
import cn.chenxing.utils.ExcelUtil;
import cn.chenxing.utils.ExportUtil;
import cn.chenxing.utils.FileChooserUtil;
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

import static cn.chenxing.utils.ExcelUtil.exportFile;

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
    }


    @FXML
    protected void clearClick() {
        this.proportion.clear();
        this.filePath.clear();
        this.cybercafeData.clear();
        this.cybercafeDataEdit.clear();
        this.cybercafeDataEdit.clear();
        this.cybercafeDataEdit.clear();
        this.resultList27.clear();
        this.resultList37.clear();
        this.resultListEdit27.clear();
        this.resultListEdit37.clear();
        this.resultListNeedEdit27.clear();
        this.resultListNeedEdit37.clear();
        this.factoryDataEdit.clear();
        this.factoryDataEdit27.clear();
        this.factoryDataEdit37.clear();
        this.factoryData.clear();
    }


    @FXML
    private void uploadFile() throws IOException {
        File file = FileChooserUtil.chooseTableFile("选择文件");

        if (null != file) {
            this.filePath.setText(file.getPath());
            File excelFile = new File(this.filePath.getText());
            this.cybercafeData.clear();
            this.consoleInfo.appendText(filePath.getText());
            directoryPath = file.getPath().substring(0, file.getPath().lastIndexOf("\\"));
            FileInputStream is = null;
            try {
                is = new FileInputStream(excelFile);
            } catch (FileNotFoundException var7) {
                var7.printStackTrace();
            }
            try {
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
                    log.info(String.valueOf(factoryDataEdit37.size()));
                    rollNums++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.consoleInfo.appendText("excel表格数据格式有误，请检查\n");
            }

        }

    }

    @FXML
    private void handleDetailsFile() {
//        List<Cybercafe> cybercafeSortName = cybercafeData.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
//        this.consoleInfo.appendText(cybercafeSortName.toString());
        if (AlertUtil.showConfirmAlert("是否确认开始计算？")) {
            this.consoleInfo.appendText("\n开始计算\n");
            File excelFile = new File(this.filePath.getText());
            FileInputStream is = null;
            try {
                is = new FileInputStream(excelFile);
            } catch (FileNotFoundException var17) {
                var17.printStackTrace();
            }

            ExcelData data = ExcelUtil.readByInputstream(excelFile.getName(), is, false);

            try {
                String afterfile = directoryPath + "\\" + "计算文件结果" + System.currentTimeMillis() + ".xlsx";
                this.scaleAll();
                log.info("1");
                cybercafeData = cybercafeData.stream().sorted(Comparator.comparing(Cybercafe::getFactoryId)).collect(Collectors.toList());
                log.info("2");
                if (resultListNeedEdit37.size() != 0 || resultListNeedEdit27.size() != 0) {
                    cybercafeDataEdit = cybercafeDataEdit.stream().sorted(Comparator.comparing(Cybercafe::getFactoryId)).collect(Collectors.toList());
                    log.info(String.valueOf(cybercafeDataEdit.size()));
                }
                log.info("3");
                try {
                    HSSFWorkbook workbook;
                    workbook = new HSSFWorkbook();
                    HSSFSheet sheet1 = workbook.createSheet("最优算法");
                    HSSFSheet sheet2 = workbook.createSheet("基于原先算法");
                    ExcelUtil.exportFile(this.cybercafeData, this.factoryData, sheet1, data, workbook);
                    log.info("4");
                    if (resultListNeedEdit37.size() != 0 || resultListNeedEdit27.size() != 0) {
                        ExcelUtil.exportFile(this.cybercafeDataEdit, this.factoryDataEdit, sheet2, data, workbook);
                    }
                    log.info("5");
                    FileOutputStream os = new FileOutputStream(afterfile);
                    workbook.write(os);
                    log.info("6");
                    os.close();
                    this.consoleInfo.appendText("计算结束，计算结果文件如下：\n");
                    this.consoleInfo.appendText(afterfile);
                } catch (IOException var4) {
                    var4.printStackTrace();
                    throw new BizException("生成文件流出错");
                }
                AlertUtil.showInfoAlert("执行完毕，请去上传文件的目录下查看计算结果");
            } catch (Exception e) {
                log.info("计算失败");
                AlertUtil.showInfoAlert("计算失败");
            }


        }
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
        factoryDataEdit.addAll(factoryDataEdit27);
        log.info(String.valueOf(factoryDataEdit37.size()));
        factoryDataEdit37 = scaleEdit(resultListNeedEdit37, resultListEdit37, "37", factoryDataEdit37);
        factoryDataEdit.addAll(factoryDataEdit37);

    }

    public void cybercafeSort(List<Cybercafe> cybercafeData) {
        for (int i = 0; i < cybercafeData.size(); i++) {
            if (cybercafeData.get(i).getNums37() > 0) {
//                Cybercafe cybercafe=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                resultList37.add(cybercafeData.get(i));
                if (null != cybercafeData.get(i).getFactoryId()) {
                    Cybercafe cybercafe1 = new Cybercafe(cybercafeData.get(i).getFactoryId(), cybercafeData.get(i).getFactoryName(), cybercafeData.get(i).getId(), cybercafeData.get(i).getName(), cybercafeData.get(i).getTerminalNums(), cybercafeData.get(i).getDisklessNums(), cybercafeData.get(i).getNums27(), cybercafeData.get(i).getNums37());
                    resultListEdit37.add(cybercafe1);
                } else {
                    Cybercafe cybercafe1 = new Cybercafe(cybercafeData.get(i).getFactoryId(), cybercafeData.get(i).getFactoryName(), cybercafeData.get(i).getId(), cybercafeData.get(i).getName(), cybercafeData.get(i).getTerminalNums(), cybercafeData.get(i).getDisklessNums(), cybercafeData.get(i).getNums27(), cybercafeData.get(i).getNums37());
                    resultListNeedEdit37.add(cybercafe1);
                }
            } else {
//                Cybercafe cybercafe=new Cybercafe(cybercafeData.get(i).getFactoryId(),cybercafeData.get(i).getFactoryName(),cybercafeData.get(i).getId(),cybercafeData.get(i).getName(),cybercafeData.get(i).getTerminalNums(),cybercafeData.get(i).getDisklessNums(),cybercafeData.get(i).getNums27(),cybercafeData.get(i).getNums37());
                resultList27.add(cybercafeData.get(i));
                if (null != cybercafeData.get(i).getFactoryId()) {
                    Cybercafe cybercafe1 = new Cybercafe(cybercafeData.get(i).getFactoryId(), cybercafeData.get(i).getFactoryName(), cybercafeData.get(i).getId(), cybercafeData.get(i).getName(), cybercafeData.get(i).getTerminalNums(), cybercafeData.get(i).getDisklessNums(), cybercafeData.get(i).getNums27(), cybercafeData.get(i).getNums37());
                    resultListEdit27.add(cybercafe1);
                } else {
                    Cybercafe cybercafe1 = new Cybercafe(cybercafeData.get(i).getFactoryId(), cybercafeData.get(i).getFactoryName(), cybercafeData.get(i).getId(), cybercafeData.get(i).getName(), cybercafeData.get(i).getTerminalNums(), cybercafeData.get(i).getDisklessNums(), cybercafeData.get(i).getNums27(), cybercafeData.get(i).getNums37());
                    resultListNeedEdit27.add(cybercafe1);
                }
            }
        }
    }

    public List scale(List<Cybercafe> resultList, String group) {
        int y = 1;
        List<Cybercafe> cybercafeSort = resultList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        List<Factory> factoryList = new ArrayList<>();
        while (cybercafeSort.size() > 0) {
            if (cybercafeSort.get(cybercafeSort.size() - 1).getTerminalNums() + cybercafeSort.get(cybercafeSort.size() - 1).getDisklessNums() >= 200) {
                String s1 = group + y;
                cybercafeSort.get(cybercafeSort.size() - 1).setFactoryId(Integer.parseInt(s1));
                cybercafeData.add(cybercafeSort.get(cybercafeSort.size() - 1));
                y++;
                Factory factory = new Factory(Integer.parseInt(s1), s1, 200 - cybercafeSort.get(cybercafeSort.size() - 1).getDisklessNums());
                factoryList.add(factory);
                cybercafeSort.remove(cybercafeSort.get(cybercafeSort.size() - 1));
            } else {
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
                        if (t <= num3) {
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
                    if (cybercafeSort.get(n).getTerminalNums() + cybercafeSort.get(n).getDisklessNums() >= 200) {
                        break;
                    }
                }
                y++;
                Factory factory = new Factory(Integer.parseInt(s), s, num1);
                factoryList.add(factory);
            }
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
