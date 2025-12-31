package com.ccit.area.sales.common.utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class CustomMergeStrategy extends AbstractMergeStrategy {
    private final List<Integer> mergeRows; // 需要合并的行索引
    private final int columnSize; // 需要合并的列数

    public CustomMergeStrategy(List<Integer> mergeRows, int columnSize) {
        this.mergeRows = mergeRows;
        this.columnSize = columnSize;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer rowIndex) {
        if (mergeRows.contains(rowIndex) && cell.getColumnIndex() == 0) {
            int lastColIndex = columnSize - 1;

            // **检查是否已合并，防止重复合并**
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
                if (mergedRegion.getFirstRow() == rowIndex) {
                    return;
                }
            }

            // **执行合并**
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, lastColIndex));
        }
    }

}
