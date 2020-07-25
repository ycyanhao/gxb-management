package com.youedata.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import com.youedata.base.exception.BusinessException;
import com.youedata.exception.BizExceptionEnum;
import com.youedata.sys.core.constant.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ExcelUtil {

    private static Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) throws UnsupportedEncodingException {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    /**
     * 导出excel
     *
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param storeFilePath
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, String storeFilePath) {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName), pojoClass, list);
        File store = new File(storeFilePath);
        if (!store.exists()) {
            store.mkdirs();
        }
        File file = new File(storeFilePath + File.separator + fileName);
        if (file.exists()) {
            file.delete();
        }
        try (FileOutputStream outputStream = new FileOutputStream(storeFilePath + File.separator + fileName)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 带导出到某路径下
     *
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param response
     * @param storeFilePath
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response, String storeFilePath) throws UnsupportedEncodingException {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName), storeFilePath);
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        defaultExport(list, fileName, response);
    }

    /**
     * 带导出到某路径下
     *
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     * @param exportParams
     * @param storeFilePath
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams, String storeFilePath) throws UnsupportedEncodingException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook, storeFilePath);
        }
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws UnsupportedEncodingException {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook, String tempFilePath) {
        response.setCharacterEncoding("UTF-8");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(BizExceptionEnum.CHRACTER_ERROR);
        }
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            //为得到导出大小，故先存储为文件
            long fileSize = 0;
            if (StringUtils.isNotBlank(tempFilePath)) {
                File file = new File(tempFilePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(file, UUID.randomUUID().toString() + ".xls");

                FileOutputStream fo = new FileOutputStream(file);
                workbook.write(fo);
                fileSize = file.length();
                fo.close();
            }
            //设置自定义文件长度头信息
            response.setHeader(Const.CUSTOM_FILE_LENGTH_HEADER, String.valueOf(fileSize));
            workbook.write(response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException error", e);
        } catch (IOException e) {
            log.error("IOException error", e);
        }
    }

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws UnsupportedEncodingException {
        downLoadExcel(fileName, response, workbook, null);
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            log.error("NoSuchElementException error", e);
            throw new ExcelExportException();
        } catch (Exception e) {
            log.error("Exception error", e);
            throw new ExcelExportException();
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new ExcelExportException("excel文件不能为空");
        } catch (Exception e) {
            throw new ExcelExportException(e.getMessage());
        }
        return list;
    }

    /**
     * 这是一个通用的方法，
     *
     * @param sheetName    表格sheet名
     * @param fileName     文件名
     * @param headers      表格属性列名数组
     * @param headersField 表格属性列名数组所对应的Map的Key值的集合
     * @param excelData    需要显示的数据集合,集合中一定要放置符合Map风格的类的对象。此方法支持的
     *                     javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     *                     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern      如果有时间数据，设定输出格式。
     */
    public static void exportExcel(String sheetName, String fileName, List<String> headers, List<String> headersField, List<Map<String, Object>> excelData, String pattern, HttpServletResponse response) {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        int index = 0;

        HSSFSheet sheet = workbook.createSheet(sheetName);

        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth(20);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);

        for (String header : headers) {
            HSSFCell cell = row.createCell(headers.indexOf(header));
            cell.setCellValue(new HSSFRichTextString(header));
        }

        // 遍历集合数据，产生数据行
        for (Map<String, Object> data : excelData) {
            index++;
            row = sheet.createRow(index);
            int m = 0;
            for (short n = 0; n < headersField.size(); n++) {
                if (n == 0) {
                    m = 0;
                }
                HSSFCell cell = row.createCell(m);
                m++;
                Object value = data.get(headersField.get(n));
                // 判断值的类型后进行强制类型转换
                String textValue = value == null ? null : value.toString();
                // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                cell.setCellValue(textValue);
            }
        }

        // 定义输出类型
        response.setContentType("application/msexcel");

        String filename = fileName + ".xls";

        try (OutputStream out = response.getOutputStream()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            response.setContentLength(byteArrayOutputStream.size());
            // 定义输出类型  ;charset=UTF-8
            response.setContentType("application/ms-excel;charset=UTF-8");
            // 设定输出文件头  new String(filename.getBytes())
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
            // 将数据写出去
            workbook.write(out);
            log.info("导出--->{}<---成功！", fileName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出--->{}<---失败！", fileName);
        }
    }
}
