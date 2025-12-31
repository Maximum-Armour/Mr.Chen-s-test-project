package com.ccit.area.sales.common.ruuner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ccit.area.sales.feign.SystemFeignService;
import com.ccit.area.sales.vo.SystemDictListVO;
import com.ccit.common.utils.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 描述 : 字典初始化器
 * 创建人 : yn
 * 创建时间 : 2024年7月25日 下午2:10:38
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.ruuner
 * 类名 : DictionaryInitializer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DictionaryInitializer implements CommandLineRunner {

	/**
	 * “管理系统”Feign接口类
	 */
	private final SystemFeignService systemFeignService;
	
	/**
	 * 字典集合
	 */
	public static Map<String, Object> dictionaryMap = new HashMap<>();
	
	@Override
	public void run(String... args) throws Exception {
		Thread.sleep(10000); // 睡眠10秒钟
		log.info("******************************启动加载字典数据******************************");
//		List<SystemDictListVO> list = systemFeignService.getseDictList();
//		for (SystemDictListVO entity : list) {
//			if (StringUtil.isNotBlank(entity.getDictValue())) {
//				JSONObject jsonObject = null;
//				String key = entity.getDictCode().split("_")[0];
//				if (!dictionaryMap.containsKey(key)) {
//					jsonObject = new JSONObject();
//				} else {
//					jsonObject = (JSONObject) dictionaryMap.get(key);
//				}
//				jsonObject.put(entity.getDictValue(), entity.getDictName());
//				dictionaryMap.put(key, jsonObject);
//				dictionaryMap.put(entity.getDictCode(), entity.getDictName());
//			}
//		}
//		log.info(new JSONObject(dictionaryMap).toJSONString());
		log.info("******************************结束加载字典数据******************************");
	}

}
