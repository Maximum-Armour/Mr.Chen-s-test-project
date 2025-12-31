package com.ccit.area.sales.common.swdp3.xml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ccit.area.sales.common.swdp3.widgets.ContainerWidget;
import com.ccit.area.sales.common.swdp3.widgets.Page;
import com.ccit.area.sales.common.swdp3.widgets.Script;
import com.ccit.area.sales.common.swdp3.widgets.Widget;
import com.ccit.common.utils.StringUtil;

/**
 * 
 * 描述 : Web客户端XML解析器
 * 创建人 : yn
 * 创建时间 : 2024年7月11日 下午4:43:08
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.common.swdp3.xml
 * 类名 : WebClientXmlParse
 */
@SuppressWarnings({ "all" })
public class WebClientXmlParse {
	
	/**
	 * 文件
	 */
	protected Document document = null;
	
	/**
	 * 元素<名称, 路径>
	 */
	protected static Map<String, String> elementClassMap = new HashMap<>();
	
	/**
	 * 动态新增
	 */
	static {
		if (elementClassMap.isEmpty()) {
			String classPath = "com.ccit.area.sales.common.swdp3.widgets";
			elementClassMap.put("panel", classPath + ".Panel");
			elementClassMap.put("treepanel", classPath + ".TreePanel");
			elementClassMap.put("formpanel", classPath + ".FormPanel");
			elementClassMap.put("fieldset", classPath + ".Fieldset");
			elementClassMap.put("gridpanel", classPath + ".GridPanel");
			elementClassMap.put("editgridpanel", classPath + ".EditGridPanel");
			elementClassMap.put("advanceQuery", classPath + ".FormPanel");
			elementClassMap.put("column", classPath + ".GridColumn");
			elementClassMap.put("tabpanel", classPath + ".TabPanel");
			elementClassMap.put("toolbar", classPath + ".Toolbar");
			elementClassMap.put("button", classPath + ".Button");
			elementClassMap.put("formwidget", classPath + ".Formwidget");
			elementClassMap.put("window", classPath + ".Window");
		}
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午5:18:19
	 * 构造方法名 : WebClientXmlParse()  
	 * 描述 : 
	 */
	protected WebClientXmlParse() {
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午5:18:27
	 * 构造方法名 : WebClientXmlParse(String route)
	 * 描述 : 根据路径进行解析XML
	 */
	public WebClientXmlParse(String route) throws Exception {
		SAXReader reader = new SAXReader();
		InputStream in = this.getClass().getResourceAsStream("/" + route.replace(".", "/") + ".xml");
		document = reader.read(in);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午5:03:41
	 * 描述 : 获取XML页面
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : getXmlPage
	 *  Page  
	 *  @throws
	 */
	public Page getXmlPage() throws Exception {
		List<Element> list = document.getRootElement().elements();
		Page page = new Page();
		page.clearWidgetsFromMap();
		page.setId(this.getPageId());
		for (int i = 0; i < list.size(); i++) {
			Element element = (Element) list.get(i);
			contentParse(element, page);
		}
		return page;
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午4:48:57
	 * 描述 : 获取XML页面ID（私有方法）
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : getPageId
	 *  String  
	 *  @throws
	 */
	private String getPageId() throws Exception {
		Node node = document.selectSingleNode("//page");
		return node.valueOf("@id");
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午4:52:37
	 * 描述 : 内容解析器（私有方法）
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : contentParse
	 *  void  
	 *  @throws
	 */
	private void contentParse(Element element, ContainerWidget cw) throws Exception {
		String elementName = element.getName();
		if ("script".equalsIgnoreCase(elementName)) {
			dealScript(element, cw);
		}
		if (!"script".equalsIgnoreCase(elementName)) {
			String className = elementClassMap.get(elementName);
			if (StringUtil.isNotBlank(className)) {
				if ("column".equalsIgnoreCase(elementName) 
					|| "button".equalsIgnoreCase(elementName) 
					|| "formwidget".equalsIgnoreCase(elementName)) {
					initWidget(className, element, cw);
				} else {
					initContainer(className, element, cw);
				}
			}
		}
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午5:13:22
	 * 描述 : 处理JS函数（私有方法）
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : dealScript
	 *  void  
	 *  @throws
	 */
	private void dealScript(Element element, ContainerWidget cw) throws Exception {
		Script s = new Script();
		s.setId(element.valueOf("@id"));
		s.setScriptText(element.getStringValue());
		s.setOwner(cw);
		cw.addChild(s);
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午5:09:23
	 * 描述 : 初始化容器（私有方法）
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : initContainer
	 *  void  
	 *  @throws
	 */
	private void initContainer(String className, Element element, ContainerWidget cw) throws Exception {
		ContainerWidget cwobject = (ContainerWidget) Class.forName(className).newInstance();
		String id = System.currentTimeMillis() + "" + (int) (Math.random() * 1000);
		cwobject.setId((element.valueOf("@id") == null || "".equalsIgnoreCase(element.valueOf("@id")) ? "ID" + id
				: element.valueOf("@id")));
		initAttrs(element, cwobject);
		cwobject.setOwner(cw);
		cw.addChild(cwobject);
		List<Element> list = element.elements();
		for (int i = 0; i < list.size(); i++) {
			Element e = (Element) list.get(i);
			contentParse(e, cwobject);
		}
	}

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午5:10:19
	 * 描述 : 初始化小部件（私有方法）
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : initWidget
	 *  void  
	 *  @throws
	 */
	private void initWidget(String className, Element element, ContainerWidget cw) throws Exception {
		Widget wbject = (Widget) Class.forName(className).newInstance();
		String id = System.currentTimeMillis() + "" + (int) (Math.random() * 1000);
		wbject.setId((element.valueOf("@id") == null || "".equalsIgnoreCase(element.valueOf("@id")) ? "ID" + id
				: element.valueOf("@id")));
		initAttrs(element, wbject);
		wbject.setOwner(cw);
		cw.addChild(wbject);
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 下午5:11:22
	 * 描述 : 初始化属性（私有方法）
	 * 包名 : com.ccit.area.sales.common.swdp3.xml
	 * 方法名 : initAttrs
	 *  void  
	 *  @throws
	 */
	private void initAttrs(Element element, Widget widget) throws Exception {
		List<Attribute> list = element.attributes();
		for (int i = 0; i < list.size(); i++) {
			Attribute attr = list.get(i);
			if ("id".equalsIgnoreCase(attr.getName())) {
				continue;
			}
			widget.setAttr(attr.getName(), attr.getValue());
		}
	}

}
