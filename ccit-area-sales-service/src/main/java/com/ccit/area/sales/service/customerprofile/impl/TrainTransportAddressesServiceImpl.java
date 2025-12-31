package com.ccit.area.sales.service.customerprofile.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.domain.customer.TTrainTransportAddressesPO;
import com.ccit.area.sales.dao.mapper.customerprofile.TrainTransportAddressesMapper;
import com.ccit.area.sales.dao.vo.customer.TTrainTransportAddresseVO;
import com.ccit.area.sales.dao.vo.customer.TTrainTransportAddressesVO;
import com.ccit.area.sales.service.customerprofile.TrainTransportAddressesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Baishangqianxue
 * @description 针对表【t_train_transport_addresses(客户铁运配送地址表)】的数据库操作Service实现
 * @createDate 2024-10-14 15:00:57
 */
@Service
@Slf4j
public class TrainTransportAddressesServiceImpl extends ServiceImpl<TrainTransportAddressesMapper, TTrainTransportAddressesPO>
        implements TrainTransportAddressesService {

    private static final String TABLE_NAME = "t_train_transport_addresses";
    private static final String DATABASE_NAME = "ccit_area_sales_test";
    private static final String EXCLUDED_FIELD = "is_deleted";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Page<TTrainTransportAddresseVO> selectPageList(Map<String, Object> param) {
        // 获取并验证页码和每页大小
        Integer page = param.containsKey("page") ? (Integer) param.get("page") : null;
        Integer size = param.containsKey("size") ? (Integer) param.get("size") : null;

        int defaultPage = 1; // 默认页码
        int defaultSize = 10; // 默认每页大小

        if (page == null || page <= 0) {
            page = defaultPage;
        }
        if (size == null || size <= 0) {
            size = defaultSize;
        }

        // 创建分页对象
        Page<TTrainTransportAddresseVO> pages = new Page<>(page, size);

        // 设置查询条件，确保只查询未删除的记录
        param.put("is_deleted", GlobalConstants.DELETE_NO);
        List<TTrainTransportAddresseVO> tTrainTransportAddresseVOS = this.baseMapper.selectPageList(pages, param);
        if (tTrainTransportAddresseVOS != null) {
            tTrainTransportAddresseVOS.stream().forEach(t -> {
                t.setStatus(redisUtils.getDict("status_",t.getStatus()));
            });
        }
        // 执行数据库查询
        pages.setRecords(tTrainTransportAddresseVOS);

        return pages;
    }

    @Override
    public TTrainTransportAddressesVO getRailwayRransportMessage(Long id) {
        TTrainTransportAddressesVO tTrainTransportAddressesVO = new TTrainTransportAddressesVO();
        TTrainTransportAddressesPO tTrainTransportAddressesPO = this.baseMapper.selectById(id);
        tTrainTransportAddressesPO.setStatus(redisUtils.getDict("status_",tTrainTransportAddressesPO.getStatus()));

        BeanUtils.copyProperties(tTrainTransportAddressesPO, tTrainTransportAddressesVO);
        return tTrainTransportAddressesVO;
    }

    @Override
    public byte[] railwayDeriveExcel() {
        // 获取所有公司信息的列表
        List<TTrainTransportAddressesPO> tTrainTransportAddressesPOS = this.baseMapper.selectList(null);
        // 获取Excel表头信息
        List<String> headers = getHeaders();

        // 检查数据源是否为空
        if (tTrainTransportAddressesPOS == null || tTrainTransportAddressesPOS.isEmpty()) {
            throw new RuntimeException("数据源为空，无法导出文件");
        }
        Workbook workbook = null;
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

        try {
            // 初始化工作簿
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("报表"); // 创建新的工作表

            // 创建标题行
            Row titleRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = titleRow.createCell(i); // 创建单元格
                cell.setCellValue(headers.get(i)); // 设置表头值
            }

            // 填充数据
            int rowNum = 1; // 从第二行开始填充数据
            for (TTrainTransportAddressesPO company : tTrainTransportAddressesPOS) {
                company.setStatus(redisUtils.getDict("status_", company.getStatus()));
                Row dataRow = sheet.createRow(rowNum++); // 创建新行
                fillDataRow(dataRow, company); // 填充当前公司的数据
            }

            // 将工作簿内容写入到 ByteArrayOutputStream
            workbook.write(byteArrayOut);

            // 返回字节数组
            return byteArrayOut.toByteArray();
        } catch (IOException e) {
            log.error("文件导出失败: {}", e.getMessage()); // 记录错误信息
            throw new RuntimeException("文件导出失败", e);
        } finally {
            // 关闭工作簿
            if (workbook != null) {
                try {
                    workbook.close(); // 关闭工作簿
                } catch (IOException e) {
                    log.error("关闭工作簿失败: {}", e.getMessage()); // 记录关闭工作簿错误
                }
            }
        }
    }

    private void fillDataRow(Row dataRow, TTrainTransportAddressesPO company) {
        // 获取 TCompanyInfoPO 类的所有字段
        Field[] fields = TTrainTransportAddressesPO.class.getDeclaredFields();
        int columnIndex = 0; // 列索引
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 遍历每个字段
        for (Field field : fields) {
            if ("id".equalsIgnoreCase(field.getName())){
                continue;
            }
            if ("isDeleted".equalsIgnoreCase(field.getName())) {
                continue; // 如果是 "is_deleted" 字段，跳过
            }
            try {
                field.setAccessible(true); // 设置字段可访问
                Object value = field.get(company); // 获取字段值
                // 跳过空值
                if (value == null) {
                    columnIndex++; // 列索引递增
                    continue; // 跳过该字段
                }
                Cell cell = dataRow.createCell(columnIndex++); // 创建单元格并设置列索引
                // 根据值类型设置单元格值
                if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue()); // 设置数字类型值
                } else if (value instanceof Date) {
                    cell.setCellValue(dateFormat.format((Date) value)); // 设置日期类型值
                } else {
                    cell.setCellValue(value.toString()); // 设置字符串类型值
                }
            } catch (IllegalAccessException e) {
                log.error("访问字段 {} 出错: {}", field.getName(), e.getMessage()); // 记录字段访问错误
                throw new BusinessException(e.getMessage());
            }
        }
    }

    public List<String> getHeaders() {
        // 创建参数Map
        Map<String, String> param = new HashMap<>();
        param.put("tableName", TABLE_NAME);
        param.put("databaseName", DATABASE_NAME);

        // 从数据库获取列注释信息
        List<Map<String, String>> columnComments = this.baseMapper.getColumnComments(param);

        // 过滤掉不需要展示的字段并获取注释值
        return columnComments.stream()
                .filter(map -> !map.get("COLUMN_NAME").equalsIgnoreCase(EXCLUDED_FIELD)) // 过滤不需要的字段
                .filter(map -> !map.get("COLUMN_NAME").equalsIgnoreCase("isDeleted")) // 过滤 is_deleted 字段
                .filter(map -> !map.get("COLUMN_NAME").equalsIgnoreCase("id"))
                .map(map -> map.get("COLUMN_COMMENT")) // 获取注释值
                .collect(Collectors.toList()); // 转换为列表
    }
}




