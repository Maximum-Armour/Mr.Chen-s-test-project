package com.ccit.area.sales.service.archives.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.redis.RedisUtils;
import com.ccit.area.sales.common.utils.ListUtil;
import com.ccit.area.sales.dao.domain.archives.ProductClassPO;
import com.ccit.area.sales.dao.dto.archives.ProductClassAddDTO;
import com.ccit.area.sales.dao.dto.archives.ProductClassDeleteDTO;
import com.ccit.area.sales.dao.dto.archives.ProductClassEditDTO;
import com.ccit.area.sales.dao.mapper.archives.ProductClassMapper;
import com.ccit.area.sales.dao.vo.archives.ProductClassDetailsVO;
import com.ccit.area.sales.dao.vo.archives.ProductClassTreeVO;
import com.ccit.area.sales.service.archives.IProductClassService;
import com.ccit.area.sales.service.system.ISystemSequenceService;
import com.ccit.common.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * 描述 : “产品分类”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年7月15日 下午3:50:50
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.archives.impl
 * 类名 : ProductClassServiceImpl
 */
@Service
public class ProductClassServiceImpl extends ServiceImpl<ProductClassMapper, ProductClassPO> implements IProductClassService {

	/**
	 * “序列号”服务类
	 */
	@Autowired
	private ISystemSequenceService systemSequenceService;

	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:51
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : selectTreeList
	 *  List<ProductClassTreeVO>  
	 *  @throws
	 */
	@Override
	@SuppressWarnings({ "all" })
	public List<ProductClassTreeVO> selectTreeList(Map<String, Object> param) {
		List<ProductClassTreeVO> result = new ArrayList<>();
		LambdaQueryWrapper<ProductClassPO> wrapper = ProductClassPO.wrapper();
		wrapper.orderByAsc(ProductClassPO::getProductClassOrders);
		String notId = (String) param.get("notId");
		String parentId = (String) param.get("parentId");
		String maxHierarchical = (String) param.get("maxHierarchical");
		String status = (String) param.get("status");
		String productClassName = (String) param.get("productClassName");
		String difference = (String) param.get("difference");
		if (StringUtil.isNotBlank(status)) {
			wrapper.eq(ProductClassPO::getStatus, status);
		}
		if (StringUtil.isNotBlank(notId)) {
			wrapper.ne(ProductClassPO::getId, Long.parseLong(notId));
		}
		if (StringUtil.isNotBlank(parentId)) {
			wrapper.eq(ProductClassPO::getParentId, "root".equals(parentId) ? 0L : Long.parseLong(parentId));
		}
		List<ProductClassPO> productClassList = this.baseMapper.selectList(wrapper);
		if (CollectionUtils.isNotEmpty(productClassList)) {
			result = ListUtil.arrayCopyTo(productClassList, ProductClassTreeVO.class);
			result.stream().forEach(entity -> {
				entity.setStatusName(redisUtils.getDict("status_",entity.getStatus()));
				entity.setIsWhpName(redisUtils.getDict("whp_",entity.getIsWhp()));
				entity.setMainOrByproductName(redisUtils.getDict("zfproduct_",entity.getMainOrByproduct()));
			});
			List<Long> productClassIdList = result.stream().distinct().map(entity -> entity.getId())
					.collect(Collectors.toList());
			param = new HashMap<>(2);
			param.put("notId", notId);
			param.put("list", productClassIdList);
			List<Map<String, Object>> childrenCountList = this.baseMapper.getChildrenCount(param);
			if (CollectionUtils.isNotEmpty(childrenCountList)) {
				Map<Long, Long> childrenCountMap = childrenCountList.stream().collect(Collectors
						.toMap(map -> (Long) map.get("id"), map -> (Long) map.get("count"), (key1, key2) -> key1));
				for (ProductClassTreeVO entity : result) {
					Long count = childrenCountMap.get(entity.getId());
					if (count != null && count > 0) {
						entity.setLeaf(false);
					}
				}
			}
		}
		if (maxHierarchical.equals("0")){
			for (int i1 = 0; i1 < result.size(); i1++) {
				ProductClassTreeVO productClassTreeVO = result.get(i1);
				Long id = productClassTreeVO.getId();
				if (difference.equals("0")){
					List<ProductClassTreeVO> childrenList = this.baseMapper.getChildrenList(id);
					for (int i = 0; i < childrenList.size(); i++) {
						childrenList.get(i).setStatusName(redisUtils.getDict("status_",childrenList.get(i).getStatus()));
						childrenList.get(i).setIsWhpName(redisUtils.getDict("whp_",childrenList.get(i).getIsWhp()));
						childrenList.get(i).setMainOrByproductName(redisUtils.getDict("zfproduct_",childrenList.get(i).getMainOrByproduct()));
					}
					productClassTreeVO.setChildren(childrenList);
				}else {
					List<ProductClassTreeVO> childrenList = this.baseMapper.getChildrenLists(id,productClassName,status);
					for (int i = 0; i < childrenList.size(); i++) {
						childrenList.get(i).setStatusName(redisUtils.getDict("status_",childrenList.get(i).getStatus()));
						childrenList.get(i).setIsWhpName(redisUtils.getDict("whp_",childrenList.get(i).getIsWhp()));
						childrenList.get(i).setMainOrByproductName(redisUtils.getDict("zfproduct_",childrenList.get(i).getMainOrByproduct()));
					}
					productClassTreeVO.setChildren(childrenList);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:54
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(ProductClassAddDTO entity) {
		LambdaQueryWrapper<ProductClassPO> wrapper = ProductClassPO.wrapper();
		wrapper.eq(ProductClassPO::getProductClassName, entity.getProductClassName());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("产品分类名称已存在");
		}
		String code = "CPFL";
		String value = systemSequenceService.get(code, 6);
		ProductClassPO productClass = new ProductClassPO();
		entity.setProductClassNo(value);
		BeanUtils.copyProperties(entity, productClass);
		int insertFlag = this.baseMapper.insert(productClass);
		if (insertFlag > 0) {
			this.setProductClassSeq(productClass, true);
			return "新增成功";
		}
		throw new BusinessException("新增失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2:32:58
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : get
	 *  ProductClassDetailsVO  
	 *  @throws
	 */
	@Override
	public ProductClassDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("产品档案ID不能为空");
		}
		ProductClassPO productClass = this.baseMapper.selectById(id);
		if (null == productClass) {
			throw new BusinessException("获取产品档案失败");
		}
		ProductClassDetailsVO result = new ProductClassDetailsVO();
		String parentName = productClass.getParentId() == 0L ? "根目录" : "";
		if (productClass.getParentId() != null && productClass.getParentId() != 0L) {
			ProductClassPO parent = this.baseMapper.selectById(productClass.getParentId());
			parentName = parent.getProductClassName();
		}
		result.setParentName(parentName);
		BeanUtils.copyProperties(productClass, result);
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2::00
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(ProductClassEditDTO entity) {
		LambdaQueryWrapper<ProductClassPO> wrapper = ProductClassPO.wrapper();
		wrapper.eq(ProductClassPO::getProductClassName, entity.getProductClassName());
		wrapper.ne(ProductClassPO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("产品分类名称已存在");
		}
		Long id = entity.getId();
		Long parentId = entity.getParentId();
		String status = entity.getStatus();
		if (parentId == 0){
			this.baseMapper.updateChildren(status,id);
		}
		ProductClassPO productClass = new ProductClassPO();
		BeanUtils.copyProperties(entity, productClass);
		this.setProductClassSeq(productClass, false);
		int updateByIdFlag = this.baseMapper.updateById(productClass);
		if (updateByIdFlag > 0) {
			return "修改成功";
		}
		throw new BusinessException("修改失败");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月17日 下午2::02
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(ProductClassDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("产品档案ID不能为空");
		}
		LambdaQueryWrapper<ProductClassPO> wrapper = ProductClassPO.wrapper();
		wrapper.in(ProductClassPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new ProductClassPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功";
		}
		throw new BusinessException("删除失败");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月25日 下午4:00:14
	 * 描述 : 设置产品分类序列号(私有方法)
	 * 包名 : com.ccit.area.sales.service.archives.impl
	 * 方法名 : setProductClassSeq
	 *  void  
	 *  @throws
	 */
	private void setProductClassSeq(ProductClassPO productClass, Boolean isUpdate) {
		ProductClassPO parent = this.getById(productClass.getParentId());
		if (parent != null) {
			productClass.setProductClassSeq(parent.getProductClassSeq() + productClass.getId() + ".");
		} else {
			productClass.setProductClassSeq("." + productClass.getId() + ".");
		}
		if (isUpdate) {
			this.updateById(productClass);
		}
	}
			
}
