package com.pyr.permission.common.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

public class ExcelGenerator {

    public static final int YEAR = 2023;
    public static final Month MONTH = Month.MAY;

    public static void main(String[] args) throws IOException {
        int month = getMonth();
        String fileName = YEAR + "年" + month + "月" + "ProductiveHours.xlsx";
        generateExcelWithDates(YEAR, MONTH, fileName);
    }

    private static int getMonth() {
        return MONTH.getValue() + 1;
    }

    public static void generateExcelWithDates(int year, Month month, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(year + "." + getMonth());

        // 创建红色背景样式
        CellStyle redCellStyle = workbook.createCellStyle();
        redCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        redCellStyle.setAlignment(HorizontalAlignment.CENTER);
        redCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font redFont = workbook.createFont();
        redFont.setFontHeightInPoints((short) 14);
        redCellStyle.setFont(redFont);

        // 创建加粗且字体为24的标题样式，并居中
        CellStyle titleCellStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 24);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 获取指定月份的21号到下个月20号的日期范围
        LocalDate startDate = LocalDate.of(year, month, 21);
        LocalDate endDate = startDate.plusMonths(1).withDayOfMonth(20);
        String titleText = getTitleText(year, month, startDate, endDate);

        // 添加标题行
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(titleText);
        titleCell.setCellStyle(titleCellStyle);

        // 合并单元格以显示标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 31));

        // 设置标题行高度
        titleRow.setHeightInPoints(30);

        // 创建表头行
        Row headerRow = sheet.createRow(1);

        // 创建表头样式
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置第一列的表头为“工作内容”
        Cell workContentCell = headerRow.createCell(0);
        workContentCell.setCellValue("工作内容");
        workContentCell.setCellStyle(headerCellStyle);

        // 创建日期单元格样式，字体为14，文本居中
        CellStyle dateCellStyle = workbook.createCellStyle();
        Font dateFont = workbook.createFont();
        dateFont.setFontHeightInPoints((short) 14);
        dateCellStyle.setFont(dateFont);
        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        int cellIndex = 1;
        Set<LocalDate> holidays = getHolidays(year); // 假设有一个方法获取假日列表

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Cell cell = headerRow.createCell(cellIndex++);
            cell.setCellValue(date.getDayOfMonth()); // 只显示日期的日部分

            // 如果是周末或者假日，设置红色背景
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || holidays.contains(date)) {
                cell.setCellStyle(redCellStyle);
            } else {
                cell.setCellStyle(dateCellStyle);
            }
        }

        // 设置工作内容列的宽度为45
        sheet.setColumnWidth(0, 45 * 256);

        // 设置其他列宽度为5
        for (int i = 1; i <= cellIndex; i++) {
            sheet.setColumnWidth(i, 5 * 256); // 列宽设置为5字符宽度
        }

        // 将文件写入输出流
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    private static String getTitleText(int year, Month month, LocalDate startDate, LocalDate endDate) {
        return year + "." + month.getValue() + "." + startDate.getDayOfMonth() + " - " + year + "." +
                +endDate.getMonthValue() + "." + endDate.getDayOfMonth();
    }

    private static Set<LocalDate> getHolidays(int year) {
        // 这里需要根据实际情况定义假日列表
        Set<LocalDate> holidays = new HashSet<>();
        holidays.add(LocalDate.of(year, 1, 1)); // 元旦
        holidays.add(LocalDate.of(year, 10, 1)); // 国庆节
        // 添加其他假日
        return holidays;
    }
}
