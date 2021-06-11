package cn.chenxing.module.main.controller;

import cn.chenxing.domain.threadlocal.ThreadLocalInfo;
import cn.chenxing.domain.util.AlertUtil;
import cn.chenxing.domain.util.SpringContextUtil;
import cn.chenxing.constant.VenusConstant;
import cn.chenxing.entity.Cybercafe;
import cn.chenxing.entity.Factory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public List<Cybercafe> cybercafeData =new ArrayList<>();
    public List<Cybercafe> cybercafeDataEdit =new ArrayList<>();

    private List<Cybercafe> resultList27 =new ArrayList<>();
    private List<Cybercafe> resultList37 =new ArrayList<>();
    private List<Cybercafe> resultListEdit27 =new ArrayList<>();
    private List<Cybercafe> resultListEdit37 =new ArrayList<>();
    private List<Cybercafe> resultListEdited27 =new ArrayList<>();
    private List<Cybercafe> resultListEdited37 =new ArrayList<>();
    private List<Factory> factoryData = new ArrayList<>();
    private List<Factory> factoryDataEdit = new ArrayList<>();

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
            directoryPath=file.getPath().substring(0, file.getPath().lastIndexOf("\\"));
            this.consoleInfo.appendText(directoryPath);
            this.consoleInfo.appendText(directoryPath+"\\"+"计算文件.xlsx");
            FileInputStream is = null;
            try {
                is = new FileInputStream(excelFile);
            } catch (FileNotFoundException var7) {
                var7.printStackTrace();
            }
            ExcelData data = ExcelUtil.readByInputstream(excelFile.getName(), is, false);
            Iterator var5 = data.getData().iterator();
            int rollNums=1;
            while(var5.hasNext()) {
                List<String> Content = (List)var5.next();
                this.cybercafeData.add(new Cybercafe(Integer.valueOf(Content.get(0)),(String)Content.get(1),rollNums,(String)Content.get(3),Integer.valueOf(Content.get(4)),Integer.valueOf(Content.get(5)),Integer.valueOf(Content.get(6)),Integer.valueOf(Content.get(7))));
                this.cybercafeDataEdit.add(new Cybercafe(Integer.valueOf(Content.get(0)),(String)Content.get(1),rollNums,(String)Content.get(3),Integer.valueOf(Content.get(4)),Integer.valueOf(Content.get(5)),Integer.valueOf(Content.get(6)),Integer.valueOf(Content.get(7))));
                if (rollNums == 1) {
                    this.factoryDataEdit.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));

                }else{
                        if (!Integer.valueOf(Content.get(0)).equals(this.factoryDataEdit.get(factoryDataEdit.size()-1).getId())) {
                            log.info(String.valueOf(factoryDataEdit == null));
                            this.factoryDataEdit.add(new Factory(Integer.valueOf(Content.get(0)), (String) Content.get(1), Integer.valueOf(Content.get(2))));
                        }

                }
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

            try{
                String afterfile=directoryPath+"\\"+"计算文件"+System.currentTimeMillis()+".xlsx";
                this.scaleAll();
                ExcelUtil.exportFile(this.cybercafeData,this.factoryData,afterfile,data,"最优算法");
//                ExcelUtil.exportFile(this.cybercafeDataEdit,this.factoryDataEdit,afterfile,data,"基于原先算法");
//                ExportUtil.excelExport(cybercafeData,"jisuanjieguo",data);
            }catch(Exception e){
                log.info("shibai");
            }
            AlertUtil.showInfoAlert("执行完毕，请去上传文件的目录下查看计算结果");


        }
    }

    @FXML
    protected void clearClick() {
        this.proportion.clear();
        this.filePath.clear();
    }

    private void scaleAll(){
        cybercafeSort();
        scale(resultList27,"27");
        scale(resultList37,"37");
//        scaleEdit(resultListEdited27,resultListEdit27);
//        scaleEdit(resultListEdited37,resultListEdit37);
    }

    private void cybercafeSort(){
        log.info("sort1");
        for(int i=0;i<cybercafeData.size();i++){
            if(cybercafeData.get(i).getNums37()>0){
                resultList37.add(cybercafeData.get(i));
                if(null!=cybercafeData.get(i).getFactoryId()){
                    resultListEdit37.add(cybercafeData.get(i));
                }else{
                    resultListEdited37.add(cybercafeData.get(i));
                }
            }else{
                resultList27.add(cybercafeData.get(i));
                if(null!=cybercafeData.get(i).getFactoryId()){
                    resultListEdit27.add(cybercafeData.get(i));
                }else{
                    resultListEdited27.add(cybercafeData.get(i));
                }
            }
        }
    }

    private void scale(List<Cybercafe> resultList,String group) {
        log.info("scale1");
        int y=0;
        List<Cybercafe> cybercafeSort = resultList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        List<Factory> factoryList=new ArrayList<>();
        while(cybercafeSort.size()>0){
            int num1=200-cybercafeSort.get(cybercafeSort.size()-1).getDisklessNums();
            Double scaleProportion=Double.parseDouble(this.proportion.getText());
            Double num2=Double.valueOf(scaleProportion)*num1;
            Integer num3=num2.intValue();
            int m=0;
            int t=0;
            int n=cybercafeSort.size()-1;
            String s=group+y;
            for(int x=0;x<num3;) {
                while (t < num3) {
                    t = m + cybercafeSort.get(n).getTerminalNums();
                    if (t < num3) {
                        m += cybercafeSort.get(n).getTerminalNums();
                        cybercafeSort.get(n).setFactoryId(Integer.parseInt(s));
                        cybercafeSort.remove(n);
                        if (n == 0) {
                            break;
                        } else {
                            n--;
                        }

                    }
                }
                if(t>num3||n==0){
                    break;
                }
            }
            y++;
            Factory factory=new Factory(Integer.parseInt(s),s,num3);
            log.info(String.valueOf(Integer.parseInt(s)));
            factoryList.add(factory);
            factoryData.add(factory);
            log.info(String.format("%s\n%s\n%s\n",m,n,t));
        }
        log.info("scaleover");
    }

    private void scaleEdit(List<Cybercafe> resultList,List<Cybercafe> resultEditList) {
        log.info("scale2");
        int y=factoryDataEdit.size()-1;
        List<Cybercafe> cybercafeSort = resultList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        List<Cybercafe> cybercafeEditSort = resultEditList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
        Map<Integer,List<Cybercafe>> cybercafeGroup=resultEditList.stream().collect(Collectors.groupingBy(Cybercafe::getFactoryId));
        List<Factory> factorySort=factoryDataEdit.stream().sorted(Comparator.comparing(Factory::getNumber)).collect(Collectors.toList());
        List<Cybercafe> cycybercafeScaleList=new ArrayList<>();
        while(cybercafeEditSort.size()>0){
            int num1=factorySort.get(y).getNumber();
            Double scaleProportion=Double.parseDouble(this.proportion.getText());
            Double num2=Double.valueOf(scaleProportion)*num1;
            Integer num3=num2.intValue();
            int m=0;
            int t=0;
            int n=cybercafeEditSort.size()-1;
            List<Integer> terminalNumsGroup=new ArrayList<>();
            List<Integer> maxDisklessNumsGroup=new ArrayList<>();
            for(int z=cybercafeGroup.size()-1;z>=0;z--){
                Integer sum= cybercafeGroup.get(z).stream().collect(Collectors.summingInt(Cybercafe::getTerminalNums));
                Integer maxSum=cybercafeGroup.get(z).stream().mapToInt(Cybercafe::getDisklessNums).max().getAsInt();
                terminalNumsGroup.add(sum);
                maxDisklessNumsGroup.add(maxSum);
                log.info(terminalNumsGroup.toString());
                log.info(maxDisklessNumsGroup.toString());
            }
                for (int l = cybercafeGroup.size(); l > -1; l--) {
                    Integer num = cybercafeEditSort.get(n).getTerminalNums() + terminalNumsGroup.get(l);
                    if (cybercafeEditSort.get(n).getDisklessNums() <= maxDisklessNumsGroup.get(l) && num <= num3) {
                        cybercafeEditSort.get(n).setFactoryId(cybercafeGroup.get(l).get(0).getFactoryId());
                        cybercafeSort.add(cybercafeEditSort.get(n));
                        cybercafeEditSort.remove(n);
                        cybercafeSort = cybercafeSort.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
                    }else{
                        if(l==0){
                            cycybercafeScaleList.add(cybercafeEditSort.get(n));
                            cybercafeEditSort.remove(n);
                        }
                    }
                }
            }

//        scale(cycybercafeScaleList);

    }


//    private void scaleBesides(List<Cybercafe> cycybercafeScaleList){
//        int y=0;
//        cycybercafeScaleList=cycybercafeScaleList.stream().sorted(Comparator.comparing(Cybercafe::getDisklessNums)).collect(Collectors.toList());
//        while(cycybercafeScaleList.size()>0){
//            int num1=200-cycybercafeScaleList.get(cycybercafeScaleList.size()-1).getDisklessNums();
//            Double scaleProportion=Double.parseDouble(this.proportion.getText());
//            Double num2=Double.valueOf(scaleProportion)*num1;
//            Integer num3=num2.intValue();
//            int m=0;
//            int t=0;
//            int n=cycybercafeScaleList.size()-1;
//        for(int s=cycybercafeScaleList.size()-1;s>-1;s--){
//            for(int x=0;x<num3;) {
//                while (t < num3) {
//                    t = m + cycybercafeScaleList.get(n).getTerminalNums();
//                    if (t < num3) {
//                        m += cycybercafeScaleList.get(n).getTerminalNums();
//                        cycybercafeScaleList.get(n).setFactoryId(y);
//                        cycybercafeScaleList.remove(n);
//                        if (n == 0) {
//                            break;
//                        } else {
//                            n--;
//                        }
//
//                    }
//                }
//                if (t > num3 || n == 0) {
//                    break;
//                }
//            }
//            y++;
//            Factory factory=new Factory(y,String.valueOf(y),num3);
//            factoryDataEdit.add(factory);
//            this.factoryDataEdit.add(factory);
//            log.info(String.format("%s\n%s\n%s\n",m,n,t));
//    }




}
