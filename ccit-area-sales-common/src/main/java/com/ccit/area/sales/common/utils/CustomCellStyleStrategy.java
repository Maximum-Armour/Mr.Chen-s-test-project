package com.ccit.area.sales.common.utils;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.List;

import static java.sql.Types.NUMERIC;
import static org.apache.xmlbeans.impl.piccolo.xml.Piccolo.STRING;

public class CustomCellStyleStrategy implements CellWriteHandler {
    // 定义每列的固定宽度（单位：像素）
    private static final int[] COLUMN_WIDTHS = {5000, 6000, 7000, 8000}; // 根据实际需求调整

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer relativeRowIndex, Boolean aBoolean) {
        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = sheet.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();

        // 设置边框样式
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        // 设置居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // **标题行**
        if (cell.getRowIndex() == 0) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 14);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }

        // 字段标题
        if (cell.getRowIndex() == 1) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // 设置背景颜色为灰色            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }

        // **获取单元格内容**
        String cellValue = "";
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            // 可以添加其他类型处理
            default:
                cellValue = "";
                break;
        }

        // **合计行高亮**
        if ("合计".equals(cellValue) || "小计".equals(cellValue)) {
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }

        // 统一设置边框样式
        cell.setCellStyle(cellStyle);

        // **跟踪所有列以支持自动调整列宽**
        SXSSFSheet sxssfSheet = (SXSSFSheet) sheet;
        sxssfSheet.trackAllColumnsForAutoSizing();

        // **设置固定列宽**
        int columnIndex = cell.getColumnIndex();
        if (columnIndex < COLUMN_WIDTHS.length) {
            sheet.setColumnWidth(columnIndex, COLUMN_WIDTHS[columnIndex]);
        }
        Row row = cell.getRow();
        row.setHeightInPoints(20); // 设置行高
    }

}
