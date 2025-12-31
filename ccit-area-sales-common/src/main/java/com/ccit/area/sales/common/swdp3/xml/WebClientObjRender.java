package com.ccit.area.sales.common.swdp3.xml;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.ccit.area.sales.common.ruuner.DictionaryInitializer;
import com.ccit.area.sales.common.swdp3.widgets.Button;
import com.ccit.area.sales.common.swdp3.widgets.ContainerWidget;
import com.ccit.area.sales.common.swdp3.widgets.FormPanel;
import com.ccit.area.sales.common.swdp3.widgets.Formwidget;
import com.ccit.area.sales.common.swdp3.widgets.GridColumn;
import com.ccit.area.sales.common.swdp3.widgets.GridPanel;
import com.ccit.area.sales.common.swdp3.widgets.Panel;
import com.ccit.area.sales.common.swdp3.widgets.Script;
import com.ccit.area.sales.common.swdp3.widgets.TabPanel;
import com.ccit.area.sales.common.swdp3.widgets.Toolbar;
import com.ccit.area.sales.common.swdp3.widgets.Widget;
import com.ccit.area.sales.common.swdp3.widgets.Window;
import com.ccit.common.utils.ServletUtil;
import com.ccit.common.utils.StringUtil;

/**
 * 
 * 描述 : Web客户端对象渲染
 * 创建人 : yn
 * 创建时间 : 2024年7月11日 下午5:28:43
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.swdp3.xml
 * 类名 : WebClientObjRender
 */
public class WebClientObjRender {
	
	public static void explain(ContainerWidget cw, StringBuffer sb) throws Exception {
		List<Widget> childList = cw.getChildList();
		for (int i = 0; i < childList.size(); i++) {
			Object obj = childList.get(i);
			String objName = obj.getClass().getSimpleName();
			if ("Panel".equalsIgnoreCase(objName)) {
				dealPanel((Panel) obj, sb);
			}
			if("TreePanel".equalsIgnoreCase(objName)){
				dealTreePanel((Panel) obj, sb);
			}
			if ("TabPanel".equalsIgnoreCase(objName)) {
				dealTabPanel((TabPanel) obj, sb);
			}
			if ("GridPanel".equalsIgnoreCase(objName)) {
				dealGridPanel((GridPanel) obj, sb);
			}
			if ("Script".equalsIgnoreCase(objName)) {
				dealScript((Script) obj, sb);
			}
			if ("Button".equalsIgnoreCase(objName)) {
				dealButton((Button) obj, sb);
			}
			if ("FormPanel".equalsIgnoreCase(objName)) {
				dealFormPanel((FormPanel) obj, sb);
			}
			if("Window".equalsIgnoreCase(objName)){
				dealWindow((Window) obj, sb);
			}
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月15日 下午4:55:43
	 * 描述 : 处理Panel
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealPanel
	 *  void  
	 *  @throws
	 */
	public static void dealPanel(Panel panel, StringBuffer sb) throws Exception {
		if (panel.getChildList().size() > 0) {
			explain(panel, sb);
		}
		StringBuffer items = new StringBuffer();
		for (int i = 0; i < panel.getChildList().size(); i++) {
			Object obj = panel.getChildList().get(i);
			String objName = obj.getClass().getSimpleName();
			if (!"Toolbar".equalsIgnoreCase(objName)) {
				items.append(((Panel) obj).getId() + ",");
			}
		}
		sb.append("	var " + panel.getId() + " = Ext.create('Ext.Panel', {");
		sb.append("		id: '" + panel.getId() + "', ");
		sb.append("		items: [" + items.toString() + "], ");
		sb.append("		layout: 'fit', ");
		sb.append("		frame: false,");
		sb.append("		border: false, ");
		sb.append("		autoDestroy: true, ");
		sb.append("		deferredRender: false, ");
		sb.append(		panel.getAttrNone("autoScroll", false, false));
		sb.append("	}); ");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月18日 下午2:32:57
	 * 描述 : 处理TreePanel
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealTreePanel
	 *  void  
	 *  @throws
	 */
	public static void dealTreePanel(Panel panel, StringBuffer sb) throws Exception {
		if (panel.getChildList().size() > 0) {
			explain(panel, sb);
		}
		// 对象属性
		StringBuffer modelFields = new StringBuffer();
		// 面板列值
		StringBuffer panelColumns = new StringBuffer();
		// 工具栏按钮
		StringBuffer tbarButtons = new StringBuffer();
		for (int i = 0; i < panel.getChildList().size(); i++) {
			Object obj = panel.getChildList().get(i);
			if ("Toolbar".equals(obj.getClass().getSimpleName())) {
				dealToolbar((Toolbar) obj, tbarButtons);
			} else {
				GridColumn gc = (GridColumn) obj;
				// 执行赋值对象属性
				modelFields.append("	{" );
				modelFields.append("		name: '" + gc.getAttr("prop") + "', "); // 属性
				modelFields.append("		type: 'string', "); // 类型
				modelFields.append("	}, ");
				// 执行赋值面板列值
				panelColumns.append("	{");
				String xtype = gc.getAttr("xtype");
				if (StringUtil.isNotBlank(xtype) && "treecolumn".equals(xtype)) {
					panelColumns.append("		xtype: '" + xtype + "', ");
				}
				String flex = gc.getAttr("flex");
				if (StringUtil.isNotBlank(flex)) {
					panelColumns.append("		flex: " + flex + ", ");
				} else {
					panelColumns.append("		width: " + gc.getAttr("width", "150") + ", ");
				}
				panelColumns.append("		text: '" + gc.getAttr("label") + "', ");
				panelColumns.append("		dataIndex: '" + gc.getAttr("prop") + "', ");
				panelColumns.append("		hidden: " + gc.getAttr("hidden", "false") + " ");
				panelColumns.append("	}, ");
			}
		}
		
		sb.append("	Ext.define('" + panel.getId() + "Model', {");
		sb.append("		extend: 'Ext.data.TreeModel', ");
		sb.append("		fields: [" + modelFields.toString() + "] ");
		sb.append("	});");
		
		sb.append("	var " + panel.getId() + "Store = Ext.create('Ext.data.TreeStore', {");
		sb.append("		model: '" + panel.getId() + "Model', "); // 于对象模型进行绑定
		sb.append("		autoDestroy: true, "); // 自动销毁
		sb.append("		nodeParam : 'parentId', "); // 父级ID
		sb.append("		proxy: { "); // 请求代理
		sb.append("			type: 'ajax', "); // AJAX
		sb.append("			timeout: '120000', "); // 超时时间
		sb.append("			url: '" + panel.getAttr("url") + "', "); // 请求地址
		sb.append("			paramsAsJson: true, "); // JSON格式传参
		sb.append("			extraParams: {}, "); // 传参
		sb.append("			headers: {'Content-Type': 'application/json'}, "); // 请求头传参格式
		sb.append("			reader: {"); // 解析
		sb.append("				type: 'json', "); // 返回格式
		sb.append("				root: 'data' "); // 返回数组
		sb.append("			}, ");
		sb.append("			actionMethods: {read: 'POST'}, "); // POST请求方式
		sb.append("		}, ");
		sb.append("		actionMethods: {create: 'POST', read: 'POST', update: 'POST', destroy: 'POST'}, "); // 请求方式
		sb.append("		autoLoad: true, "); // 执行加载
		sb.append("		root: { expanded: true }, ");
		sb.append("	});");
		
		sb.append("	var " + panel.getId() + " = Ext.create('Ext.tree.Panel', {");
		sb.append("		id:'" + panel.getId() + "', ");
		sb.append("		title: '" + panel.getAttr("title", true) + "',	");
		sb.append("		useArrows: " + panel.getAttr("useArrows", "true") + ", ");
		sb.append("		rootVisible: " + panel.getAttr("rootVisible", "true") + ", ");
		sb.append("		focusOnToFront: " + panel.getAttr("focusOnToFront", "false") + ", ");
		sb.append("		columns: [ " + panelColumns.toString() + " ], ");
		sb.append("		store: " + panel.getId()+ "Store, ");
		sb.append("		tbar: [" + tbarButtons.toString() + "]");
		sb.append("	}); ");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月12日 上午10:35:41
	 * 描述 : 处理TabPanel
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealTabPanel
	 *  void  
	 *  @throws
	 */
	public static void dealTabPanel(TabPanel tabPanel, StringBuffer sb) throws Exception {
		if (tabPanel.getChildList().size() > 0) {
			explain(tabPanel, sb);
		}
		String ps = "";
		int activeTab = 0;
		boolean activeFlag = true;
		for (int i = 0; i < tabPanel.getChildList().size(); i++) {
			Panel p = (Panel) tabPanel.getChildList().get(i);
			if (i == 0) {
				ps += "" + p.getId();
			} else {
				ps += "," + p.getId();
			}
			if ("true".equalsIgnoreCase(p.getAttr("activeTab")) && activeFlag) {
				activeTab = i;
				activeFlag = false;
			}
		}
		// 创建Tab面板
		sb.append("	var " + tabPanel.getId() + " = Ext.create('Ext.tab.Panel', {");
		sb.append("		id:'" + tabPanel.getId() + "', ");
		sb.append("		frame: false, ");
		sb.append("		border: false, ");
		sb.append("		autoDestroy: true, ");
		sb.append("		deferredRender: false, ");
		sb.append("		style: 'margin: 10px;', ");
		sb.append(		tabPanel.getAttrNone("hidden", false, false));
		sb.append(		tabPanel.getAttrNone("autoScroll", false, false));
		if (ps.length() > 1) {
			sb.append("items:[" + ps + "], ");
			sb.append("activeTab:" + activeTab + ", ");
			sb.append(tabPanel.getAttrNone("title", true, false));
		}
		sb.append("	});");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月12日 下午1:44:56
	 * 描述 : 处理GridPanel
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealGridPanel
	 *  void  
	 *  @throws
	 */
	public static void dealGridPanel(GridPanel gridPanel, StringBuffer sb) throws Exception {
		// 对象属性
		StringBuffer modelFields = new StringBuffer();
		// 面板列值
		StringBuffer panelColumns = new StringBuffer();
		// 工具栏按钮
		StringBuffer tbarButtons = new StringBuffer();
		// 开始处理内容
		for (int i = 0; i < gridPanel.getChildList().size(); i++) {
			Object obj = gridPanel.getChildList().get(i);
			if ("Toolbar".equals(obj.getClass().getSimpleName())) {
				dealToolbar((Toolbar) obj, tbarButtons);
			} else {
				GridColumn gc = (GridColumn) obj;
				// 执行赋值对象属性
				modelFields.append("	{" );
				modelFields.append("		name: '" + gc.getAttr("prop") + "', "); // 属性
				modelFields.append("		type: 'string', "); // 类型
				modelFields.append("	}, ");
				// 执行赋值面板列值
				panelColumns.append("	{ ");
				String allowBlank = gc.getAttr("allowBlank", "true"); // 是否必填
				panelColumns.append("		header: '" + gc.getAttr("label") + ("false".equals(allowBlank) ? "<font color=red>*</font>" : "") + "', "); // 名称
				panelColumns.append("		dataIndex: '" + gc.getAttr("prop") + "', "); // 属性
				panelColumns.append("		width: " + gc.getAttr("width", "140") + ", "); // 宽度
				panelColumns.append("		hidden: " + gc.getAttr("hidden", "false") + ", "); // 是否隐藏
				panelColumns.append(		gc.getAttrNone("renderer", false, false)); // 超链接
				String route = gc.getAttr("route", ""); // 路由地址
				if (StringUtil.isNotBlank(route)) {
					panelColumns.append("		route: '" + route + "', ");
				}
				String comboKey = gc.getAttr("comboKey", ""); // 处理字典
				if (StringUtil.isNotBlank(comboKey)) {
					JSONObject jsonObject = (JSONObject) DictionaryInitializer.dictionaryMap.get(comboKey);
					if (jsonObject != null) {
						panelColumns.append("	renderer: function (value, metadata, record, rowIndex, colIndex, store) {" );
						panelColumns.append("	var json = " + jsonObject.toJSONString() + ";" );
						panelColumns.append("	return json[value]; " );
						panelColumns.append("	}," );
					}
				}
				panelColumns.append("		items:[{" ); // 子配置项
				panelColumns.append("			enableKeyEvents: true, "); // 开启回车键
				panelColumns.append("			listeners: { "); // 监听器
				panelColumns.append("				scope: this, ");
				panelColumns.append("				'keydown': function(t, e, eOpts) {" ); // 回车键判断
				panelColumns.append("					if (e.event.keyCode == 37 || e.event.keyCode == 39) { ");
				panelColumns.append("						e.stopPropagation(); ");
				panelColumns.append("					} ");
				panelColumns.append("					if (e.event.keyCode == 13) { ");
				panelColumns.append("						Ext.getCmp('" + gridPanel.getId() + "').plugins[0].queryButton.fireEvent('click'); ");
				panelColumns.append("					} ");
				panelColumns.append("				} ");
				panelColumns.append("			}, ");
				panelColumns.append(handleXtype(gc)); // 处理xtype类型
				panelColumns.append("			id: '" + gridPanel.getId() + "_" + gc.getAttr("prop") + "', "); // ID
				panelColumns.append("			width: " + String.valueOf((Integer.parseInt(gc.getAttr("width", "140")) - 5)) + ", "); // 宽度
				panelColumns.append("			hidden: " + gridPanel.getAttr("searchHidden", "false") + " "); // 是否隐藏搜索框
				panelColumns.append("		}],");
				String editor = gridPanel.getAttr("editor", "false"); // 编辑
				Object readOnlyStatus = ServletUtil.getRequest().getAttribute("readOnly"); // 是否只读状态
				if ("true".equals(editor) && (readOnlyStatus == null || !(Boolean) readOnlyStatus)) {
					panelColumns.append("		editor: { ");
					panelColumns.append("			id: 'editor_" + gridPanel.getId() + "_" + gc.getAttr("prop") + "', "); // ID
					panelColumns.append("			xtype: '" + gc.getAttr("xtype") + "',  ");
					panelColumns.append(handleXtype(gc));
					String readOnly = gc.getAttr("readOnly", "false");
					if ("true".equals(readOnly)) {
						panelColumns.append("			readOnly: true, ");
						panelColumns.append("			fieldClass: 'formreadonly', ");
						panelColumns.append("			fieldStyle: 'background:#F0F0F0', ");
					} else {
						if ("trigger".equalsIgnoreCase(gc.getAttr("xtype"))) {
							panelColumns.append("		editable: false, ");
							panelColumns.append("		triggers: { ");
							panelColumns.append("			clear: { ");
							panelColumns.append("				cls: Ext.baseCSSPrefix + 'form-clear-trigger', ");
							panelColumns.append("				handler: function() { ");
							panelColumns.append("					Ext.getCmp('editor_" + gridPanel.getId() + "_" + gc.getAttr("prop") + "').setValue(''); ");
							panelColumns.append("				} ");
							panelColumns.append("			}, ");
							panelColumns.append("			search: { ");
							panelColumns.append("				cls: Ext.baseCSSPrefix + 'form-search-trigger', ");
							panelColumns.append("				handler: ");
							panelColumns.append(					gc.getAttr("onTriggerClick", "Ext.emptyFn") );
							panelColumns.append(" 			} ");
							panelColumns.append("		},");
							panelColumns.append("		hasSearch : false, ");
							panelColumns.append("		emptyText: '请选择', ");
						} else if ("combo".equalsIgnoreCase(gc.getAttr("xtype"))) {
							panelColumns.append("		editable: false, ");
							panelColumns.append("		emptyText: '请选择', ");
						} else {
							panelColumns.append("		emptyText: '请输入" + gc.getAttr("label") + "', ");
						}
					}
					if ("false".equalsIgnoreCase(allowBlank)) {
						panelColumns.append("		allowBlank: " + allowBlank + ", ");
						panelColumns.append("		blankText: '" + gc.getAttr("label") + "不能为空!', ");
					}
					String regex = gc.getAttr("regex", "");
					if (StringUtil.isNotBlank(regex)) {
						panelColumns.append("		regex: " + regex + ", ");
						panelColumns.append("		regexText: '" + gc.getAttr("regexText", "") + "', ");
					}
					panelColumns.append("		} ");
				}
				panelColumns.append("	}, ");
			}
		}
		// 创建对象模型
		sb.append("	Ext.define('" + gridPanel.getId() + "Model', {");
		sb.append("		extend: 'Ext.data.Model', ");
		sb.append("		fields: [" + modelFields.toString() + "] ");
		sb.append("	});");
		// 创建JSON存储于对象模型进行绑定
		sb.append("	var " + gridPanel.getId() + "Store = Ext.create('Ext.data.JsonStore', {");
		sb.append("		model: '" + gridPanel.getId() + "Model', "); // 于对象模型进行绑定
		sb.append("		autoDestroy: true, "); // 自动销毁
		sb.append("		pageSize: 50, "); // 页码
		sb.append("		proxy: { "); // 请求代理
		sb.append("			type: 'rest', "); // RESTful
		sb.append("			timeout: '120000', "); // 超时时间
		sb.append("			url: '" + gridPanel.getAttr("url") + "', "); // 请求地址
		sb.append("			paramsAsJson: true, "); // JSON格式传参
		String param = (String) ServletUtil.getRequest().getAttribute("param");
		if (Objects.isNull(param)) {
			param = "";
		}
		String panelParam = gridPanel.getAttr("param", "");
		if (StringUtil.isNotBlank(panelParam) && !panelParam.contains("{") && !panelParam.contains("}")) {
			for (String key : panelParam.split(",")) {
				String value = (String) ServletUtil.getRequest().getAttribute(key);
				if (!Objects.isNull(value)) {
					param = key + ":" + "'" + value + "',";
				} else {
					param = key + ": '0',";
				}
			}
			param = param.substring(0, param.length() - 1);
		} else if (panelParam.contains("{") && panelParam.contains("}")) {
			param = panelParam.replace("{", "").replace("}", "");
		}
		sb.append("			extraParams: { param: {" + param + "} }, "); // 传参
		sb.append("			headers: {'Content-Type': 'application/json'}, "); // 请求头传参格式
		sb.append("			reader: {"); // 解析
		sb.append("				type: 'json', "); // 返回格式
		sb.append("				rootProperty: 'data.records', "); // 返回数组
		sb.append("				totalProperty: 'data.total', "); // 返回条数
		sb.append("			}, ");
		sb.append("			actionMethods: {read: 'POST'}, "); // POST请求方式
		sb.append("			limitParam: 'size', "); // 修改分页默认值“limit”名称改成“size”
		sb.append("			startParam: 'page', "); // 修改分页默认值“start”名称改成“page”
		sb.append("		}, ");
		sb.append("		actionMethods: {create: 'POST', read: 'POST', update: 'POST', destroy: 'POST'}, "); // 请求方式
		sb.append("		autoLoad: true, "); // 执行加载
		sb.append("		listeners: {"); // 监听器
		sb.append("			'beforeload': function(store, options) { "); // 加载之前
		sb.append("				var newPage = (store.lastOptions.start / store.lastOptions.limit); "); // 处理新页码
		sb.append("				var newParams = {page: (newPage == 0 ? 1 : (newPage + 1))}; "); // 新参数
		sb.append("		        Ext.apply(store.proxy.extraParams, newParams); "); // 赋值
		sb.append("			}");
		sb.append("		} ");
		sb.append("	});");
		// 创建面板
		sb.append("	var " + gridPanel.getId() + " = Ext.create('Ext.grid.Panel', {");
		sb.append("		id: '" + gridPanel.getId() + "', "); // 标识
		sb.append(		gridPanel.getAttrNone("title", true, false) ); // 标题
		sb.append(		gridPanel.getAttrNone("autoScroll", false, false) ); // 滚动
		sb.append("		minHeight: " + gridPanel.getAttr("minHeight", "500") + ", "); // 最小高度
		String flex = gridPanel.getAttr("flex", "0"); // 容器填充
		if (Integer.parseInt(flex) > 0) {
			sb.append("		flex: " + flex + ", ");
		}
		String style = gridPanel.getAttr("style", ""); // 样式
		if (StringUtil.isNotBlank(style)) {
			sb.append("		style: '" + style + "', ");
		}
		sb.append("		store: " + gridPanel.getId() + "Store, "); // 数据共享
		sb.append("		columns: [ "); // 列值
		sb.append("			{xtype: 'rownumberer', width:50, align: 'center', header: '序号' }, ");
		sb.append(			panelColumns.toString() );
		sb.append("		],");
		sb.append("		bbar: [ "); // 分页
		sb.append("			{xtype: 'pagingtoolbarcustom', store: " + gridPanel.getId() + "Store, ");
		sb.append("			displayMsg: '显示 {0} - {1} 条，共计 {2} 条', emptyMsg: '没有数据', beforePageText: '当前页', ");
		sb.append("			afterPageText: '共{0}页', displayInfo: true, plugins: Ext.create('Ext.ux.ComboPageSize', {})} ");
		sb.append("		], ");
		Object readOnly = ServletUtil.getRequest().getAttribute("readOnly");
		if (readOnly != null && (Boolean) readOnly) {
			tbarButtons.delete(0, tbarButtons.length());;
		}
		sb.append("		tbar: Ext.create('Ext.toolbar.Toolbar', {"); // 工具栏
		sb.append("			id: '" + gridPanel.getId() + "Tbar', items: [ " + tbarButtons.toString() + " ] ");
		sb.append("		}), ");
		sb.append("		plugins: ['searchHeaderPlugin' "); // 插件
		String editor = gridPanel.getAttr("editor", "false"); // 编辑
		Object readOnlyStatus = ServletUtil.getRequest().getAttribute("readOnly"); // 是否只读状态
		if ("true".equals(editor) && (readOnlyStatus == null || !(Boolean) readOnlyStatus)) {
			sb.append("			,Ext.create('Ext.grid.plugin.RowEditing', { ");
			sb.append("				clicksToMoveEditor: 1, ");
			sb.append("				autoCancel: false, ");
			sb.append("				saveBtnText: '保存',  ");
			sb.append("				cancelBtnText: '取消', tipAnchor: 't', ");
			sb.append("			}) ");
		}
		sb.append("		], "); // 插件
		sb.append("		operateArray: [], "); // 操作数组用于存储临时添加数据
		String mode = gridPanel.getAttr("mode", ""); // 模式，默认为多选框
		if (StringUtil.isNotBlank(mode)) { // single 单选
			mode = ", mode: '" + mode + "'";
		}
		sb.append("		selModel: Ext.create('Ext.selection.CheckboxModel', {width: 100 " + mode + "}), ");
		sb.append("	});");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年8月14日 下午4:38:08
	 * 描述 : 处理xtype类型，如combo、datefield等
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : getXtypeContent
	 *  String  
	 *  @throws
	 */
	public static String handleXtype(GridColumn gc) throws Exception {
		StringBuffer sb = new StringBuffer();
		String xtype = gc.getAttr("xtype");
		if ("combo".equals(xtype)) {
			sb.append("			xtype: 'combobox', ");
			sb.append("			store: Ext.create('Ext.data.Store', { ");
			sb.append("				fields: ['dictValue', 'dictName'], "); // 属性
			sb.append("				autoDestroy: true, "); // 自动销毁
			sb.append("				proxy: { "); // 请求代理
			sb.append("					type: 'ajax', "); // AJAX
			sb.append("					timeout: '120000', "); // 超时时间
			sb.append("					url: '/webapi/system/dict/getChildrenList?dictCode=" + gc.getAttr("comboKey", true) + "', "); // 请求地址
			sb.append("					reader: { "); // 解析
			sb.append("						type: 'json', "); // 返回格式
			sb.append("						root: 'data' "); // 返回数组
			sb.append("					}, ");
			sb.append("					actionMethods: {read: 'GET'}, "); // GET请求方式
			sb.append("				}, ");
			sb.append("				actionMethods: {create: 'GET', read: 'GET', update: 'GET', destroy: 'GET'}, "); // 请求方式
			sb.append("				autoLoad: true, "); // 执行加载
			sb.append("			}),");
			sb.append("			displayField: 'dictName', ");
			sb.append("			valueField: 'dictValue', ");
		} else if ("datefield".equals(xtype)) {
			sb.append("			xtype: '" + xtype + "', ");
			sb.append("			format: '" + gc.getAttr("format", "Y-m-d") + "', "); // 时间格式
		} else {
			sb.append("			xtype: '" + xtype + "', ");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月13日 下午2:35:26
	 * 描述 : 处理Toolbar
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealToolbar
	 *  void  
	 *  @throws
	 */
	public static void dealToolbar(Toolbar toolbar, StringBuffer tbarButtons) throws Exception {
		for (int i = 0; i < toolbar.getChildList().size(); i++) {
			Object obj = toolbar.getChildList().get(i);
			String objName = obj.getClass().getSimpleName();
			if ("Button".equalsIgnoreCase(objName)) {
				Button button = (Button) obj;
				tbarButtons.append("	{ ");
				tbarButtons.append("		id: '" + button.getId() + "', ");
				tbarButtons.append("		text: '<font size=2>" + button.getAttr("text", true) + "</font>', ");
				tbarButtons.append("		tooltip: '" + button.getAttr("text", true) + "', ");
				tbarButtons.append(			button.getAttrNone("iconCls", true, false) );
				tbarButtons.append(			button.getAttrNone("hidden", false, false) );
				tbarButtons.append("		handler: " + button.getAttr("handler", true) + " ");
				tbarButtons.append("	}, ");
			}
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月13日 下午3:14:16
	 * 描述 : 处理Script
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealScript
	 *  void  
	 *  @throws
	 */
	private static void dealScript(Script script, StringBuffer sb) {
		sb.append(script.getScriptText() + "\n");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月13日 下午4:41:47
	 * 描述 : 处理Window
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealWindow
	 *  void  
	 *  @throws
	 */
	public static void dealWindow(Window window, StringBuffer sb) throws Exception {
		StringBuffer items = new StringBuffer();
		StringBuffer buttons = new StringBuffer();
		if (window.getChildList().size() > 0) {
			explain(window, sb);
			List<Widget> childList = window.getChildList();
			for (int i = 0; i < childList.size(); i++) {
				String objName = childList.get(i).getClass().getSimpleName();
				if ("button".equalsIgnoreCase(objName)) {
					Object readOnly = ServletUtil.getRequest().getAttribute("readOnly");
					if (readOnly == null || !(Boolean) readOnly) {
						buttons.append(childList.get(i).getId() + ",");
					}
				} else {
					items.append(childList.get(i).getId() + ",");
				}
			}
		}
		// 前缀标题
		String prefixTitle = (String) ServletUtil.getRequest().getAttribute("prefixTitle");
		prefixTitle = Objects.isNull(prefixTitle) ? "" : prefixTitle;
		// 后缀标题
		String suffixTitle = (String) ServletUtil.getRequest().getAttribute("suffixTitle");
		suffixTitle = Objects.isNull(suffixTitle) ? "" : suffixTitle;
		// 创建弹窗
		sb.append("	var " + window.getId() + " = Ext.create('Ext.window.Window', {");
		sb.append("		id: '" + window.getId() + "', ");
		sb.append("		title: '" + prefixTitle + window.getAttr("title", true) + suffixTitle + "',	");
		sb.append("		modal: true, ");
		sb.append("		stateful: true, ");
		sb.append("		autoDestroy: true, ");
		sb.append("		monitorResize: true, ");
		sb.append(		window.getAttrNone("bodyStyle", true, false) );
		sb.append(		window.getAttrNone("autoScroll", false, false) );
		sb.append(		window.getAttrNone("header", false, false) );
		sb.append(		window.getAttrNone("maximizable", false, false) );
		sb.append("		closable: '" + window.getAttr("closeAction", "hide") + "', ");
		String layout = window.getAttr("layout", "fit");
		// 内部元素布局方式{vbox absolute accordion anchor border card column fit form table}
		if ("vbox".equals(layout)) { // 垂直布局
			sb.append("		layout: { ");
			sb.append("			type: '" + layout + "', ");
			sb.append("			align: 'stretch' ");
			sb.append("		}, ");
		} else {
			sb.append("		layout: '" + layout + "', ");
		}
		String width = window.getAttr("width");
		if (StringUtil.isBlank(width)) {
			sb.append("		width: crrWidth, ");
		} else {
			sb.append("		width: " + width + ", ");
		}
		String height = window.getAttr("height");
		if (StringUtil.isBlank(height)) {
			sb.append("		height: crrHeight, ");
		} else {
			sb.append("		height: " + height + ", ");
		}
		sb.append("		buttons:[" + buttons.toString() + " {scale: 'small', iconCls: 'closeButton', text: '<font size=2>关闭</font>', ");
		sb.append("		handler: function() { "+ window.getId() + ".close();}}], ");
		sb.append("		items:[" + items + "]");
		sb.append("	});");
		// 打开弹窗
		sb.append(window.getId() + ".show();");
		// 是否开启调试大小
		String resize = window.getAttr("resize", "true");
		if ("true".equals(resize)) {
			sb.append(window.getId() + ".setPosition(0, 0);");
			sb.append(window.getId() + ".fitContainer();");
			// 监听弹窗高度宽度实现自适应
			sb.append("	Ext.EventManager.onWindowResize(function() {");
			sb.append("		" + window.getId() + ".setWidth(Ext.getBody().getWidth()); ");
			sb.append("		" + window.getId() + ".setHeight(document.documentElement.offsetHeight); ");
			sb.append("	}, " + window.getId() + ");");
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月13日 下午8:30:57
	 * 描述 : 处理FormPanel
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealFormPanel
	 *  void  
	 *  @throws
	 */
	public static void dealFormPanel(FormPanel formPanel, StringBuffer sb) throws Exception {
		sb.append("	var " + formPanel.getId() + " = Ext.create('Ext.form.Panel', {");
		sb.append("		id: '" + formPanel.getId() + "' ,");
		sb.append("		border: false, ");
		sb.append("		bodyPadding: 5,	");
		sb.append("		autoDestroy: true, ");
		sb.append("		defaultType: 'textfield', ");
		sb.append("		autoScroll: " + formPanel.getAttr("autoScroll", "true") + ", ");
		sb.append(		formPanel.getAttrNone("title", true, false) );
		sb.append(		formPanel.getAttrNone("header", false, false) );
		sb.append(		formPanel.getAttrNone("hidden", false, false) );
		sb.append(		formPanel.getAttrNone("collapsed", false, false) );
		sb.append(		formPanel.getAttrNone("bodyStyle", true, false) );
		String height = formPanel.getAttr("height", "0");
		if (Integer.parseInt(height) > 0) {
			sb.append("		height: " + height + ", ");
		}
		sb.append(		formPanel.getAttrNone("bodyStyle", true, false) );
		sb.append("		layout: { ");
		sb.append("			type: 'table', ");
		sb.append("			columns: " + formPanel.getAttr("columns", true) + " ");
		sb.append("		}, ");
		StringBuffer widgets = new StringBuffer();
		List<Widget> childList = formPanel.getChildList();
		for (int i = 0; i < childList.size(); i++) {
			Formwidget fwidget = (Formwidget) childList.get(i);
			dealFormWidget(formPanel.getId(), fwidget, widgets);
		}
		sb.append("		items: [" + widgets.toString() + "], ");
		sb.append("		listeners: { ");
		sb.append("			afterrender: function(panel) { ");
		String method = formPanel.getAttr("method", false);
		if (StringUtil.isNotBlank(method)) {
			Object id = ServletUtil.getRequest().getAttribute("id");
			if (id != null) {
				sb.append("		get('" + method.replace("#{id}", id.toString()) + "', function(res) {");
				sb.append("			var form = panel.getForm(); ");
				sb.append("			if (form != null && form != undefined) { ");
				sb.append("				form.setValues(res.data); ");
				sb.append("			} else { ");
				sb.append("				Ext.MessageBox.alert('提示', '回显失败，请刷新浏览器重新操作！'); ");
				sb.append("			}");
				sb.append("		});");
			}
		}
		sb.append("			} ");
		sb.append("		} ");
		sb.append("	}); ");
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月13日 下午8:40:07
	 * 描述 : 处理FormWidget
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealFormwidget
	 *  void  
	 *  @throws
	 */
	public static void dealFormWidget(String formPanelId, Formwidget formWidget, StringBuffer widgets) throws Exception {
		String xtype = formWidget.getAttr("xtype");
		if ("filesetBegin".equalsIgnoreCase(xtype)) {
			widgets.append("	{ ");
			widgets.append("		id:'" + UUID.randomUUID() + "', ");
			widgets.append("		xtype: 'fieldsetCux', ");
			widgets.append("		margin: '5px 10px', ");
			widgets.append("		autoHeight : true, ");
			widgets.append("		frame: true, ");
			widgets.append("		border: 1, ");
			widgets.append("		title: '" + formWidget.getAttr("title", "基本信息") + "', ");
			widgets.append("		layout: {");
			widgets.append("			type: 'table', ");
			widgets.append("			columns: " + formWidget.getAttr("columns", true) + ", ");
			widgets.append("		},");
			widgets.append("		items :[ ");
		}
		if ("filesetEnd".equalsIgnoreCase(xtype)) {
			widgets.append("	]}, ");
		}
		if (!"filesetBegin".equalsIgnoreCase(xtype) && !"filesetEnd".equalsIgnoreCase(xtype)) {
			// 获取只读状态
			Object readOnlyObj = ServletUtil.getRequest().getAttribute("readOnly");
			// 转成Boolean
			Boolean readOnly = readOnlyObj != null && (Boolean) readOnlyObj ? true : false;
			// 是否必填
			String allowBlank = formWidget.getAttr("allowBlank", "false");
			// 只读时，关闭校验选项
			allowBlank = "displayfield".equals(xtype) || readOnly ? "true" : allowBlank;
			// 红色标题
			String redLabel = ("false".equals(allowBlank) ? "<font color=red>*</font>" : "");
			widgets.append("	{ ");
			widgets.append("		fieldLabel: '" + redLabel + formWidget.getAttr("fieldLabel", true) + "', ");
			widgets.append("		name: '" + formWidget.getAttr("fieldProp", true) + "', ");
			widgets.append("		autoDestroy: true, ");
			widgets.append("		labelAlign: 'right',");
			widgets.append(			formWidget.getAttrNone("readOnly", true, false) );
			widgets.append("		width: " + formWidget.getAttr("width", "300") + ", ");
			widgets.append("		allowBlank: " + allowBlank + ", ");
			widgets.append("		hidden: " + formWidget.getAttr("hidden", "false") + ", ");
			if ("false".equalsIgnoreCase(allowBlank)) {
				widgets.append("	blankText: '此处不能为空!', ");
			}
			if ("true".equalsIgnoreCase(formWidget.getAttr("readOnly"))) {
				widgets.append("	fieldClass: 'formreadonly', ");
				widgets.append("	fieldStyle: 'background:#F0F0F0', ");
			}
			if (readOnly && !"displayfield".equalsIgnoreCase(xtype)) {
				widgets.append("	readOnly: true, ");
				widgets.append("	fieldClass: 'formreadonly', ");
				widgets.append("	fieldStyle: 'background:#F0F0F0', ");
			}
			if ("textfield".equalsIgnoreCase(xtype) || "trigger".equalsIgnoreCase(xtype)) {
				widgets.append("		xtype: 'cuxtextfield', ");
				String emptyText = formWidget.getAttr("emptyText");
				String emptyTextFlag = formWidget.getAttr("emptyTextFlag", "true");
				if (StringUtil.isNotBlank(emptyText) && !readOnly) {
					widgets.append("		emptyText: '" + emptyText + "', ");
				} else if ("true".equals(emptyTextFlag) && !readOnly) {
					widgets.append("		emptyText: '请输入" + formWidget.getAttr("fieldLabel", true) + "', ");
				}
			} else if ("datefield".equalsIgnoreCase(xtype)) {
				widgets.append("		xtype: 'datefield', ");
				widgets.append("		format: '" + formWidget.getAttr("format", "Y-m-d") + "', ");
				if (!readOnly) {
					widgets.append("		emptyText: '请选择" + formWidget.getAttr("fieldLabel", true) + "', ");
				}
			} else if ("datetimefield".equalsIgnoreCase(xtype)) {
				widgets.append("		xtype: 'datetimefield', ");
				widgets.append("		format: '" + formWidget.getAttr("format", "Y-m-d H:i:s") + "', ");
				if (!readOnly) {
					widgets.append("		emptyText: '请选择" + formWidget.getAttr("fieldLabel", true) + "', ");
				}
			} else if ("combo".equalsIgnoreCase(xtype)) {
				widgets.append("		editable: false, ");
				if (!readOnly) {
					widgets.append("		emptyText: '请选择', ");
				}
				widgets.append("		xtype: 'combobox', ");
				widgets.append("		store: Ext.create('Ext.data.Store', { ");
				widgets.append("			fields: ['dictValue', 'dictName'], "); // 属性
				widgets.append("			autoDestroy: true, "); // 自动销毁
				widgets.append("			proxy: { "); // 请求代理
				widgets.append("				type: 'ajax', "); // AJAX
				widgets.append("				timeout: '120000', "); // 超时时间
				widgets.append("				url: '/webapi/system/dict/getChildrenList?dictCode=" + formWidget.getAttr("key", true) + "', "); // 请求地址
				widgets.append("				reader: { "); // 解析
				widgets.append("					type: 'json', "); // 返回格式
				widgets.append("					root: 'data' "); // 返回数组
				widgets.append("				}, ");
				widgets.append("				actionMethods: {read: 'GET'}, "); // GET请求方式
				widgets.append("			}, ");
				widgets.append("			actionMethods: {create: 'GET', read: 'GET', update: 'GET', destroy: 'GET'}, "); // 请求方式
				widgets.append("			autoLoad: true, "); // 执行加载
				widgets.append("		}),");
				widgets.append("		displayField: 'dictName', ");
				widgets.append("		valueField: 'dictValue', ");
				String value = formWidget.getAttr("value", "");
				if (StringUtil.isNotBlank(value)) {
					widgets.append("		value: '" + value + "', ");
				}
			} else {
				widgets.append("		xtype: '" + xtype + "', ");
			}
			if ("trigger".equalsIgnoreCase(xtype)) {
				widgets.append("		editable: false, ");
				widgets.append("		triggers: { ");
				widgets.append("			clear: { ");
				widgets.append("				cls: Ext.baseCSSPrefix + 'form-clear-trigger', ");
				widgets.append("				handler: function() { ");
				widgets.append("					Ext.getCmp('" + formPanelId + "').getForm().findField('" + formWidget.getAttr("fieldProp", true) + "').setValue(''); ");
				String clearKeys = formWidget.getAttr("clearKeys", "");
				if (StringUtil.isNotBlank(clearKeys)) {
					String[] clearKeyArray = clearKeys.split(",");
					for (String key : clearKeyArray) {
						widgets.append("					Ext.getCmp('" + formPanelId + "').getForm().findField('" + key + "').setValue(''); ");
					}
				}
				widgets.append("				} ");
				widgets.append("			}, ");
				widgets.append("			search: { ");
				widgets.append("				cls: Ext.baseCSSPrefix + 'form-search-trigger', ");
				widgets.append("				handler: ");
				widgets.append(					formWidget.getAttr("onTriggerClick", "Ext.emptyFn") );
				widgets.append(" 			} ");
				widgets.append("		},");
				widgets.append("		hasSearch : false, ");
				if (!readOnly) {
					widgets.append("		emptyText: '请选择', ");
				}
			}
			if ("treecombo".equalsIgnoreCase(xtype)) {
				widgets.append("		store: Ext.create('Ext.data.TreeStore', { ");
				widgets.append("			autoDestroy: true, "); // 自动销毁
				widgets.append("			nodeParam : 'parentId', "); // 父级ID
				widgets.append("			proxy: { "); // 请求代理
				widgets.append("				type: 'ajax', "); // AJAX
				widgets.append("				timeout: '120000', "); // 超时时间
				widgets.append("				url: '" + formWidget.getAttr("url") + "', "); // 请求地址
				widgets.append("				paramsAsJson: true, "); // JSON格式传参
				widgets.append("				extraParams: {}, "); // 传参
				widgets.append("				headers: {'Content-Type': 'application/json'}, "); // 请求头传参格式
				widgets.append("				reader: { "); // 解析
				widgets.append("					type: 'json', "); // 返回格式
				widgets.append("					root: 'data' "); // 返回数组
				widgets.append("				}, ");
				widgets.append("				actionMethods: {read: 'POST'}, "); // POST请求方式
				widgets.append("				extractResponseData: function(response) { ");
				widgets.append("					var res = Ext.JSON.decode(response.responseText);");
				widgets.append("					var firstRoot = " + formWidget.getAttr("firstRoot") + ";");
				widgets.append("					if ('root' == response.request.jsonData.parentId && firstRoot != null) { res.data.unshift(firstRoot); }");
				widgets.append("					response.responseText = Ext.JSON.encode(res).replaceAll('" + formWidget.getAttr("text") + "', 'text'); ");
				widgets.append("					return response; ");
				widgets.append("				} ");
				widgets.append("			}, ");
				widgets.append("			actionMethods: {create: 'POST', read: 'POST', update: 'POST', destroy: 'POST'}, "); // 请求方式
				widgets.append("			autoLoad: true, "); // 执行加载
				widgets.append("			root: { expanded: true }, ");
				widgets.append("		}),");
				// 空提示信息
				if (!readOnly) {
					widgets.append("		emptyText: '请选择', ");
				}
				// 回调方法
				String callback = formWidget.getAttr("callback", "");
				if (StringUtil.isNotEmpty(callback)) {
					widgets.append("		callback: " + callback + ", ");
				}
				// 开展方法
				String expands = formWidget.getAttr("expands", "");
				if (StringUtil.isNotEmpty(expands)) {
					widgets.append("		expands: " + expands + ", ");
				}
			}
			widgets.append("	}, ");
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月13日 下午10:10:16
	 * 描述 : 处理Button
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealButton
	 *  void  
	 *  @throws
	 */
	private static void dealButton(Button button, StringBuffer sb) throws Exception {
		Object readOnly = ServletUtil.getRequest().getAttribute("readOnly");
		if (readOnly != null && (Boolean) readOnly) {
			return;
		}
		sb.append("	var " + button.getId() + " = Ext.create('Ext.Button', {");
		sb.append("		scale: 'small', ");
		sb.append("		id: '" + button.getId() + "', ");
		sb.append("		text: '<font size=2>" + button.getAttr("text", true) + "</font>', ");
		sb.append(		button.getAttrNone("iconCls", true, false) );
		sb.append(		button.getAttrNone("hidden", false, false) );
		sb.append("		handler: " + button.getAttr("handler", true) + ", ");
		String callback = button.getAttr("callback", "");
		if (StringUtil.isNotBlank(callback)) {
			sb.append("		callback: '" + ServletUtil.getRequest().getAttribute(callback) + "' ");
		}
		sb.append("	}); ");
	}
	
}
