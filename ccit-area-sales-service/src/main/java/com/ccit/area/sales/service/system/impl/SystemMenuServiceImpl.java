package com.ccit.area.sales.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.area.sales.common.utils.ListUtil;
import com.ccit.area.sales.dao.domain.system.SystemMenuPO;
import com.ccit.area.sales.dao.dto.system.SystemMenuAddDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuDeleteDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuEditDTO;
import com.ccit.area.sales.dao.dto.system.SystemMenuMoveDTO;
import com.ccit.area.sales.dao.mapper.system.SystemMenuMapper;
import com.ccit.area.sales.dao.vo.system.SystemMenuDetailsVO;
import com.ccit.area.sales.dao.vo.system.SystemMenuTreeVO;
import com.ccit.area.sales.service.system.ISystemMenuService;
import com.ccit.area.sales.service.system.ISystemTreeNodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述 : “菜单”服务实现类
 * 创建人 : yn
 * 创建时间 : 2024年6月10日 下午5:44:29
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.service.system.impl
 * 类名 : SystemMenuServiceImpl
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenuPO> implements ISystemMenuService {
	
	/**
	 * “树节点”服务类
	 */
	@Autowired
	private ISystemTreeNodeService systemTreeNodeService;
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月15日 下午9:30:06
	 * 描述 : 树列表，支持高级查询
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : selectTreeList
	 *  List<SystemMenuTreeVO>  
	 *  @throws
	 */
	@Override
	@SuppressWarnings({ "all" })
	public List<SystemMenuTreeVO> selectTreeList(Map<String, Object> param) {
		List<SystemMenuTreeVO> result = new ArrayList<>();
		LambdaQueryWrapper<SystemMenuPO> wrapper = SystemMenuPO.wrapper();
		wrapper.orderByAsc(SystemMenuPO::getMenuOrders);
		String menuName = (String) param.get("menuName");
		if (StringUtils.isNoneBlank(menuName)) {
			wrapper.like(SystemMenuPO::getMenuName, menuName);
		}
		Integer status = (Integer) param.get("status");
		if (status != null) {
			wrapper.eq(SystemMenuPO::getStatus, status);
		}
		List<SystemMenuPO> systemMenuList = this.baseMapper.selectList(wrapper);
		if (!CollectionUtils.isEmpty(systemMenuList)) {
			List<SystemMenuTreeVO> list = ListUtil.arrayCopyTo(systemMenuList, SystemMenuTreeVO.class);
			list.stream().forEach(v -> {
				v.setStatusName(v.getStatus() == 0 ? "正常" : "禁用");
			});
			result = systemTreeNodeService.changeDataMenuTree(list);
		}
		return result;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:46:18
	 * 描述 : 新增一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : add
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String add(SystemMenuAddDTO entity) {
		LambdaQueryWrapper<SystemMenuPO> wrapper = SystemMenuPO.wrapper();
		wrapper.eq(SystemMenuPO::getParentId, entity.getParentId());
		wrapper.eq(SystemMenuPO::getMenuType, entity.getMenuType());
		wrapper.eq(SystemMenuPO::getMenuName, entity.getMenuName());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("菜单名称已存在，请进行检查！");
		}
		// 菜单类型为“按钮”和父级ID为“0”时抛出异常
		if (3 == entity.getMenuType() && entity.getParentId() == 0) {
			throw new BusinessException("上级菜单为目录类型，目前不支持添加按钮类型的菜单，请进行检查！");
		}
		// 菜单类型为“目录、菜单”时执行以下校验
		if (3 != entity.getMenuType()) {
			if (StringUtils.isBlank(entity.getMenuRouter())) {
				throw new BusinessException("路由路径不能为空，请进行检查！");
			}
			if (StringUtils.isBlank(entity.getMenuPath())) {
				throw new BusinessException("组件路径不能为空，请进行检查！");
			}
		}
		// 查询父级信息
		SystemMenuPO parent = this.getById(entity.getParentId());
		if (parent != null) {
			// 父级菜单类型为“目录”和当前菜单类型为“按钮”时抛出异常
			if (1 == parent.getMenuType() && 3 == entity.getMenuType()) {
				throw new BusinessException("上级菜单为目录类型，目前不支持添加按钮类型的菜单，请进行检查！");
			}
			// 父级菜单类型为“按钮”时抛出异常
			if (3 == parent.getMenuType()) {
				throw new BusinessException("上级菜单为按钮类型，目前不支持添加子节点菜单，请进行检查！");
			}
		}
		// 创建菜单对象并进行赋值
		SystemMenuPO systemMenu = new SystemMenuPO();
		BeanUtils.copyProperties(entity, systemMenu);
		// 赋值菜单排序
		wrapper = SystemMenuPO.wrapper();
		wrapper.eq(SystemMenuPO::getParentId, entity.getParentId());
		systemMenu.setMenuOrders(this.baseMapper.selectCount(wrapper) + 1);
		// 执行入库
		int insertFlag = this.baseMapper.insert(systemMenu);
		if (insertFlag > 0) {
			// 入库成功后获取主键进行赋值菜单排序
			this.setMenuSeq(systemMenu, parent);
			// 执行更新
			this.baseMapper.updateById(systemMenu);
			return "新增成功！";
		}
		throw new BusinessException("新增失败，请刷新浏览器重新操作！");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:50:35
	 * 描述 : 根据主键ID查询相应数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : get
	 *  SystemMenuDetailsVO  
	 *  @throws
	 */
	@Override
	public SystemMenuDetailsVO get(Long id) {
		if (null == id) {
			throw new BusinessException("菜单ID不能为空，请进行检查！");
		}
		SystemMenuPO systemMenu = this.baseMapper.selectById(id);
		if (null == systemMenu) {
			throw new BusinessException("获取菜单信息失败，请刷新浏览器重新操作！");
		}
		SystemMenuDetailsVO systemMenuDetailsVO = new SystemMenuDetailsVO();
		BeanUtils.copyProperties(systemMenu, systemMenuDetailsVO);
		return systemMenuDetailsVO;
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:51:30
	 * 描述 : 修改一条数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : edit
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String edit(SystemMenuEditDTO entity) {
		LambdaQueryWrapper<SystemMenuPO> wrapper = SystemMenuPO.wrapper();
		wrapper.eq(SystemMenuPO::getParentId, entity.getParentId());
		wrapper.eq(SystemMenuPO::getMenuType, entity.getMenuType());
		wrapper.eq(SystemMenuPO::getMenuName, entity.getMenuName());
		wrapper.ne(SystemMenuPO::getId, entity.getId());
		Integer selectCount = this.baseMapper.selectCount(wrapper);
		if (selectCount > 0) {
			throw new BusinessException("菜单名称已存在，请进行检查！");
		}
		// 菜单类型为“按钮”和父级ID为“0”时抛出异常
		if (3 == entity.getMenuType() && entity.getParentId() == 0) {
			throw new BusinessException("上级菜单为目录类型，目前不支持添加按钮类型的菜单，请进行检查！");
		}
		// 菜单类型为“目录、菜单”时执行以下校验
		if (3 != entity.getMenuType()) {
			if (StringUtils.isBlank(entity.getMenuRouter())) {
				throw new BusinessException("路由路径不能为空，请进行检查！");
			}
			if (StringUtils.isBlank(entity.getMenuPath())) {
				throw new BusinessException("组件路径不能为空，请进行检查！");
			}
		}
		// 查询父级信息
		SystemMenuPO parent = this.getById(entity.getParentId());
		if (parent != null) {
			// 父级菜单类型为“目录”和当前菜单类型为“按钮”时抛出异常
			if (1 == parent.getMenuType() && 3 == entity.getMenuType()) {
				throw new BusinessException("上级菜单为目录类型，目前不支持添加按钮类型的菜单，请进行检查！");
			}
			// 父级菜单类型为“按钮”时抛出异常
			if (3 == parent.getMenuType()) {
				throw new BusinessException("上级菜单为按钮类型，目前不支持添加子节点菜单，请进行检查！");
			}
		}
		// 创建菜单对象并进行赋值
		SystemMenuPO systemMenu = new SystemMenuPO();
		BeanUtils.copyProperties(entity, systemMenu);
		// 赋值菜单排序
		this.setMenuSeq(systemMenu, parent);
		// 执行入库
		int updateByIdFlag = this.baseMapper.updateById(systemMenu);
		if (updateByIdFlag > 0) {
			return "修改成功！";
		}
		throw new BusinessException("修改失败，请刷新浏览器重新操作！");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:53:04
	 * 描述 : 删除【一条&多条】数据
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : delete
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String delete(SystemMenuDeleteDTO entity) {
		if (CollectionUtils.isEmpty(entity.getIds())) {
			throw new BusinessException("菜单ID不能为空，请进行检查！");
		}
		LambdaQueryWrapper<SystemMenuPO> wrapper = SystemMenuPO.wrapper();
		wrapper.in(SystemMenuPO::getId, entity.getIds());
		int updateFlag = this.baseMapper.update(new SystemMenuPO(true, false), wrapper);
		if (updateFlag > 0) {
			return "删除成功！";
		}
		throw new BusinessException("删除失败，请刷新浏览器重新操作！");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:54:01
	 * 描述 : 移动位置
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : move
	 *  String  
	 *  @throws
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class })
	public String move(SystemMenuMoveDTO entity) {
		SystemMenuPO systemMenu = this.baseMapper.selectById(entity.getId());
		if (systemMenu != null) {
			LambdaQueryWrapper<SystemMenuPO> wrapper = SystemMenuPO.wrapper();
			wrapper.orderByAsc(SystemMenuPO::getMenuOrders);
			wrapper.in(SystemMenuPO::getParentId, systemMenu.getParentId());
			List<SystemMenuPO> systemMenuList = this.baseMapper.selectList(wrapper);
			if (!CollectionUtils.isEmpty(systemMenuList)) {
				int nowNum = systemMenu.getMenuOrders();
				int moveNum = entity.getMoveNum() > 0 ? (systemMenu.getMenuOrders() - 1)
						: (systemMenu.getMenuOrders() + 1);
				int num = nowNum  - moveNum;
				if (entity.getMoveNum() > 0) { // 向上移动
					/** if (systemMenu.getMenuOrders() == 0) {
						return Result.failed(ResultMsg.FAILED.fillArgs("当前节点为最顶端,无法移动"));
					} **/
					if (num == 0) {
						return "移动成功！";
					}
					for (int i = moveNum - 1; i < nowNum; i++) {
						if (nowNum == i + 1) {
							continue;
						}
						SystemMenuPO target = systemMenuList.get(i);
						target.setMenuOrders((i + 2));
					}
				} else { // 向下移动
					/** if (systemMenu.getMenuOrders() == systemMenuList.size()) {
						return Result.failed(ResultMsg.FAILED.fillArgs("当前节点为最尾端,无法移动"));
					} **/
					for (int i = nowNum; i < moveNum; i++) {
						SystemMenuPO target = systemMenuList.get(i);
						target.setMenuOrders(i);
					}
				}
				SystemMenuPO target = systemMenuList.get(nowNum - 1);
				target.setMenuOrders(moveNum);
				super.updateBatchById(systemMenuList);
			}
		}
		return "移动成功！";
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年6月16日 上午8:49:49
	 * 描述 : 设置菜单序列号(私有方法)
	 * 包名 : com.ccit.area.sales.service.system.impl
	 * 方法名 : setMenuSeq
	 *  void  
	 *  @throws
	 */
	private void setMenuSeq(SystemMenuPO systemMenu, SystemMenuPO parent) {
		if (parent != null) {
			systemMenu.setMenuSeq(parent.getMenuSeq() + systemMenu.getId() + ".");
		} else {
			systemMenu.setMenuSeq("." + systemMenu.getId() + ".");
		}
	}

}
