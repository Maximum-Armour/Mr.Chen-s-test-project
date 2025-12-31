package com.ccit.area.sales.web.intercept;

import com.alibaba.fastjson.JSON;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.dao.vo.system.SystemDictListVO;
import com.ccit.area.sales.service.system.ISystemDictService;
import com.ccit.common.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }

    @Autowired
    private ISystemDictService iSystemDictService;
    @Autowired
    private RedisUtils redisUtils;

    private void initializeData() {

        try {
            List<SystemDictListVO> systemDictListVOS = iSystemDictService.selectList(null);
            // 使用流式处理将数据存入 Redis
            int size = 0;

            Map<String, String> map = systemDictListVOS.stream()
                    .filter(t -> t.getDictValue() != null)
                    .collect(Collectors.toMap(
                            SystemDictListVO::getDictCode,
                            SystemDictListVO::getDictName
                    ));
            size = map.size();
//                    redisUtils.remove("dictionary:dictionaryName");
            String encrypt = AESUtil.encrypt(JSON.toJSONString(map), GlobalConstants.AES_PARAM_KEY);
            redisUtils.set("dictionary:dictionaryName", encrypt);

            log.info("数据初始化完成，共初始化字典表信息 {} 条记录", size);
        } catch (Exception e) {
            log.error("数据初始化失败:{}", e.getMessage());
        }
    }

}
