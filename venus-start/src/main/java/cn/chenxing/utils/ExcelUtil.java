package cn.chenxing.utils;


import cn.chenxing.entity.Cybercafe;
import cn.chenxing.entity.Factory;
import cn.chenxing.exception.BizException;
import com.google.common.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {
    public static final String EXCEL_XLS = "xls";
    public static final String EXCEL_XLSX = "xlsx";
    private static Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    private static DecimalFormat df = new DecimalFormat("0.##");

    public ExcelUtil() {
    }

    public static void main(String[] args) throws Exception {
        File excelFile = new File("/Users/xiaobowang/document/_haigui/_testfile/5个案件模板.xls");
        FileInputStream is = new FileInputStream(excelFile);
        System.out.println(excelFile.getName());
        ExcelData data = readByInputstream(excelFile.getName(), is, false);
    }

    public static ExcelData uploadExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String encoding = "utf-8";
            request.setCharacterEncoding(encoding);
            response.setContentType("text/html; charset=utf-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sevletFileUpload = new ServletFileUpload(factory);
            sevletFileUpload.setSizeMax(-2147483648L);
            List<FileItem> fileItems = sevletFileUpload.parseRequest((RequestContext) request);
            ExcelData excelData = null;
            Iterator it = fileItems.iterator();

            FileItem item;
            do {
                if (!it.hasNext()) {
                    return null;
                }

                item = (FileItem)it.next();
            } while(item.isFormField());

            String fileFullName = item.getName();
            int tmpIndex = fileFullName.lastIndexOf(".");
            if (tmpIndex == -1) {
                throw new BizException("上传类型不支持");
            } else {
                InputStream is = item.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[4000];

                int len;
                while((len = is.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }

                byte[] excelByte = bos.toByteArray();
                String excelpath = "tmp/" + CommonUtil.random32() + fileFullName.substring(tmpIndex);
                ByteArrayInputStream bis = new ByteArrayInputStream(excelByte);
                excelData = readByInputstream(item.getName(), bis, false);
                excelData.setFilePath(excelpath);
                return excelData;
            }
        } catch (Exception var18) {
            log.error(var18.getMessage(), var18);
            throw new BizException(var18);
        }
    }

    public static ExcelData readByInputstream(String inputFileName, InputStream is, boolean ingoreError) {
        checkExcelVaild(inputFileName);
        Workbook workbook = getWorkbok(is, inputFileName);
        return getExcelData(0, workbook, ingoreError);
    }

    public static List<ExcelData> readListByInputstream(String inputFileName, InputStream is, boolean ingoreError) {
        checkExcelVaild(inputFileName);
        Workbook workbook = getWorkbok(is, inputFileName);
        int sheetCount = workbook.getNumberOfSheets();
        List<ExcelData> excelDataList = Lists.newArrayList();

        for(int i = 0; i < sheetCount; ++i) {
            excelDataList.add(getExcelData(i, workbook, ingoreError));
        }

        return excelDataList;
    }

    public static ExcelData getExcelData(int number, Workbook workbook, boolean ingoreError) {
        Sheet sheet = workbook.getSheetAt(number);
        List<String> title = new ArrayList();
        List<List<String>> content = new ArrayList();
        int rowIndex = 0;
        Iterator var7 = sheet.iterator();
        while(true) {
            while(var7.hasNext()) {
                Row row = (Row)var7.next();
                short columns;
                if (rowIndex == 0) {
                    columns = row.getLastCellNum();
                    for(int i = 0; i < columns; ++i) {
                        Cell cell = row.getCell(i);
                        if (cell == null && !ingoreError) {
                            throw new BizException("excel列头中存在空数据");
                        }

                        String value = getValue(cell);
                        if (value != null) {
                            value = value.trim();
                        }

                        title.add(value);
                    }

                    ++rowIndex;
                } else {
                    columns = row.getLastCellNum();
                    List<String> contentRow = new ArrayList();

                    for(int i = 0; i < columns; ++i) {
                        Cell cell = row.getCell(i);
                        String value = null;
                        if (cell != null) {
                            value = getValue(cell);
                        }

                        if (value != null) {
                            value = value.trim();
                        }

                        contentRow.add(value);
                    }

                    content.add(contentRow);
                    ++rowIndex;
                }
            }

            ExcelData excelData = new ExcelData();
            excelData.setData(content);
            excelData.setTitle(title);
            return excelData;
        }
    }

    private static String getValue(Cell cell) {
        if (cell != null&&cell.getCellType() != Cell.CELL_TYPE_BLANK) {
            if (cell.getCellType()==Cell.CELL_TYPE_STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
                return df.format(cell.getNumericCellValue());
            } else {
                throw new BizException("excel单元格格式不支持:" + cell.getCellType() + ", 请将excel中所有单元格格式设置为文本格式");
            }
        } else {
            return null;
        }
    }

    public static Workbook getWorkbok(InputStream in, String fileName) {
        try {
            Workbook wb = null;
            if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook(in);
            } else if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook(in);
            }

            return (Workbook)wb;
        } catch (Exception var3) {
            log.error(var3.getMessage(), var3);
            return null;
        }
    }

    public static void checkExcelVaild(File file) {
        if (!file.exists()) {
            throw new BizException("文件不存在");
        } else if (!file.isFile() || !file.getName().endsWith("xls") && !file.getName().endsWith("xlsx")) {
            throw new BizException("文件不是Excel");
        }
    }

    public static void checkExcelVaild(String fileName) {
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            throw new BizException("文件不是Excel");
        }
    }


    public static void exportFile(List<Cybercafe> cybercafeData, List<Factory> factoryData,String savePath,ExcelData data,String sheetName) {
        HSSFWorkbook workbook;

        try {
            workbook = new HSSFWorkbook();
            HSSFSheet sheet=workbook.createSheet(sheetName);
            setColumnWidth(sheet, 8);
            // 创建第一行
            HSSFRow row = sheet.createRow(0);
            // 创建一个单元格
            HSSFCell cell = null;
            for (int i = 0; i < data.getTitle().size(); i++) {
                cell = row.createCell(i);
                // 设置样式
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 设置字体居中
                // 设置字体
                Font font = workbook.createFont();
//                font.setFontName("宋体");
//                font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 字体加粗
                // font.setFontHeight((short)12);
//                font.setFontHeightInPoints((short) 13);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(data.getTitle().get(i));
            }
            for(int i=1;i<data.getData().size()+1;i++){
                Row nextRow = sheet.createRow(i);
                for(int n=0;n<8;n++){
                    Cell cell2 = nextRow.createCell(n);
                    Cybercafe cybercafe= cybercafeData.get(i-1);
                    Factory factory=new Factory();
                    for(int m=0;m<factoryData.size();m++){
                        Factory factory1= factoryData.get(m);
                        if(cybercafe.getFactoryId().equals(factory1.getId())){
                            factory=factoryData.get(m);
                        }
                    }
                    if (n == 0) {
                        cell2.setCellValue(cybercafe.getFactoryId());
                    }
                    if (n == 1) {
                        cell2.setCellValue(cybercafe.getFactoryName());
                    }
                    if (n == 2) {
                        cell2.setCellValue(factory.getNumber());
                    }
                    if (n == 3) {
                        cell2.setCellValue(cybercafe.getName());
                        log.info(cybercafe.getName());
                    }
                    if (n == 4) {
                        cell2.setCellValue(cybercafe.getDisklessNums());
                    }
                    if (n == 5) {
                        cell2.setCellValue(cybercafe.getTerminalNums());
                    }
                    if (n == 6) {
                        cell2.setCellValue(cybercafe.getNums27());
                    }
                    if (n == 7) {
                        cell2.setCellValue(cybercafe.getNums37());
                    }

                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            throw new BizException("创建文件流出错");
        }

        try {
            FileOutputStream os = new FileOutputStream(savePath);
            workbook.write(os);
            os.close();
        } catch (IOException var4) {
            var4.printStackTrace();
            throw new BizException("生成文件流出错");
        }
    }

    // 设置列宽()
    private static void setColumnWidth(Sheet sheet, int colNum) {
        for (int i = 0; i < colNum; i++) {
            int v = 0;
            v = Math.round(Float.parseFloat("15.0") * 37F);
            v = Math.round(Float.parseFloat("20.0") * 267.5F);
            sheet.setColumnWidth(i, v);
        }
    }
}
