package com.youedata.dfs.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * POI工具类
 * 
 * @Description: POI工具类
 * @author army.liu
 */
public class POIUtil {

	private static Logger logger = LoggerFactory.getLogger(POIUtil.class);
		
	@SuppressWarnings("resource")
	public static Map<String, Object> parseExcelFile(MultipartFile multipartFile, int rowStartIndex, int cellCount){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "300");

		try {
			InputStream inputStream = multipartFile.getInputStream();

			Workbook hssfWorkbook = null;

			boolean fileType = multipartFile.getOriginalFilename()
					.endsWith(".xlsx");// 2007以上版本标记

			// 文件检查
			try {
				// 文件大小限制
				long size = multipartFile.getSize();
				if (size > 1024 * 1024 * 10) {
					result.put("code", "300");
					result.put("msg", "导入的文件大小超过限制! (最大为10M)");
					return result;
				}
				// 文件格式限制
				if (!multipartFile.getOriginalFilename().endsWith(".xls")
						&& !multipartFile.getOriginalFilename().endsWith(
								".xlsx")) {
					result.put("code", "300");
					result.put("msg",
							"导入的文件格式错误, 必须为excel文件, 格式为'.xls'或'.xlsx' !");
					return result;
				}

				if (fileType) {
					hssfWorkbook = new XSSFWorkbook(inputStream);
				} else {
					POIFSFileSystem poifsFileSystem = new POIFSFileSystem(
							inputStream);
					hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
				}

			} catch (Exception e) {
				logger.error("导入失败", e);
				result.put("code", "300");
				result.put("msg", "当前文件读取发生错误, 请检查文件是否正常!");
				return result;
			}

			Map<String, Object> sheetDatas = new HashMap<String, Object>();
			// 解析sheet数据
			for (int sheetIndex = 0; sheetIndex < hssfWorkbook
					.getNumberOfSheets(); sheetIndex++) {

				Sheet sheetAt = null;
				if (fileType) {
					sheetAt = (XSSFSheet) hssfWorkbook
							.getSheetAt(sheetIndex);
				} else {
					sheetAt = (HSSFSheet) hssfWorkbook
							.getSheetAt(sheetIndex);
				}

				List<List<String>> importData = new ArrayList<List<String>>();// 导入数据
				for (int rowIndex = rowStartIndex; rowIndex <= sheetAt
						.getLastRowNum(); rowIndex++) { // 最后一行也读，从0开始

					Row row = null;
					if (fileType) {
						row = (XSSFRow) sheetAt.getRow(rowIndex);
					} else {
						row = (HSSFRow) sheetAt.getRow(rowIndex);
					}

					if( null == row ) {
						importData.add(null);
						continue;
					}
//					int lastCellNum = row.getLastCellNum();// 列数
//
//					// 模版文件的列数检查
//					if (cellCount != lastCellNum) {//从1开始
//						result.put("code", "300");
//						result.put("msg", "导入失败: 当前文件的列数为" + lastCellNum
//								+ "，请确认与模版一致!");
//						return result;
//
//					}

					// 当前行中格式不正确的信息
					List<String> data = new ArrayList<String>();
//					for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
					for (int columnIndex = 0; columnIndex < 1; columnIndex++) {

						Cell cell = null;
						if (fileType) {
							cell = (XSSFCell) row.getCell(columnIndex);
						} else {
							cell = (HSSFCell) row.getCell(columnIndex);
						}

						String cellValue = null;

						if (cell != null) {
							// 注意：一定要设成这个，否则可能会出现乱码
							// cell.setEncoding(HSSFCell.ENCODING_UTF_16);

							// 处理字段
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								cellValue = cell.getStringCellValue();
								break;

//								case HSSFCell.CELL_TYPE_NUMERIC:
//									if (HSSFDateUtil.isCellDateFormatted(cell)) {
//										Date date = cell.getDateCellValue();
//										if (date != null) {
//											cellValue = new SimpleDateFormat(
//													Constants.DATE_TIME_FORMAT_SHORT)
//													.format(date);
//										} else {
//											cellValue = "";
//
//										}
//
//									} else {
//										cellValue = new DecimalFormat(".##")
//												.format(cell
//														.getNumericCellValue());
//
//									}
//									break;
//
//								case HSSFCell.CELL_TYPE_FORMULA:
//									// 导入时如果为公式生成的数据则无值
//									if (!cell.getStringCellValue().equals("")) {
//										cellValue = cell.getStringCellValue();
//									} else {
//										cellValue = cell.getNumericCellValue()
//												+ "";
//
//									}
//									break;
//
//								case HSSFCell.CELL_TYPE_BLANK:
//									break;
//
//								case HSSFCell.CELL_TYPE_ERROR:
//									cellValue = "";
//									break;
//
//								case HSSFCell.CELL_TYPE_BOOLEAN:
//									cellValue = (cell.getBooleanCellValue() == true ? "Y"
//											: "N");
//									break;
							default:
								cellValue = "";

							}

							data.add(cellValue);

						}

					}
					importData.add(data);

				}
				sheetDatas.put(sheetIndex + "", importData);

			}
			result.put("data", sheetDatas);

			result.put("code", "200");
			result.put("msg", "数据解析成功! ");

		} catch (Exception e) {
			logger.error("导入失败", e);
			result.put("code", "300");
			result.put("msg", "导入失败: " + e.getMessage());
		}

		return result;
	}
}
