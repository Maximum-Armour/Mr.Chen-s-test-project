package com.ccit.area.sales.service.system.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.dao.domain.system.SystemSequencePO;
import com.ccit.area.sales.dao.mapper.system.SystemSequenceMapper;
import com.ccit.area.sales.service.system.ISystemSequenceService;

/**
 * 
 * 描述 : “序列号”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年08月22日 上午10:05:51
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemSequenceServiceImpl
 */
@Service
public class SystemSequenceServiceImpl extends ServiceImpl<SystemSequenceMapper, SystemSequencePO> implements ISystemSequenceService {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月22日 上午10:34:44
	 * 描述 : 获取序列号
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : get
	 *  String  
	 *  @throws
	 */
	@Override
	public synchronized String get(String code, Integer number) {
		String value = "";
		try {
			LambdaQueryWrapper<SystemSequencePO> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(SystemSequencePO::getCode, code);
			SystemSequencePO sequence = this.baseMapper.selectOne(wrapper);
			if (null == sequence) {
				sequence = new SystemSequencePO(code, 1L);
				this.baseMapper.insert(sequence);
			} else {
				sequence.setValue(sequence.getValue() + 1);
				this.baseMapper.update(sequence, wrapper);
			}
			value = code + String.format("%0" + number + "d", sequence.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
