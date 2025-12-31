package com.ccit.area.sales.web.controller.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ccit.area.sales.common.result.ResponseVO;
import com.ccit.area.sales.common.swdp3.xml.WebClientObjRender;
import com.ccit.area.sales.common.swdp3.xml.WebClientXmlParse;
import com.ccit.common.utils.ServletUtil;
import com.ccit.common.utils.StringUtil;

/**
 * 
 * 描述 : “JSP控制台”前端控制器
 * 创建人 : yn
 * 创建时间 : 2024年7月11日 上午9:25:41
 * 版本 : 1.0
 * 包名 : com.ccit.area.sales.web.controller.view
 * 类名 : IndexController
 */
@Controller
public class IndexController {

	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月11日 上午9:26:22
	 * 描述 : 主入口页面
	 * 包名 : com.ccit.area.sales.web.controller.view
	 * 方法名 : main
	 *  String  
	 *  @throws
	 */
	@GetMapping("/main/{code}.html")
	public String main(@PathVariable(value = "code") String code, Model model) {
		model.addAttribute("code", code);
		return "/main.jsp";
	}
	
	/**
	 * 
	 * 创建人 : yn
	 * 创建时间 : 2024年7月15日 下午4:36:48
	 * 描述 : 获取内容
	 * 包名 : com.ccit.area.sales.web.controller.view
	 * 方法名 : getContent
	 *  ResponseVO<String>  
	 *  @throws
	 */
	@ResponseBody
	@PostMapping(value = "/getContent.html")
	public ResponseVO<String> getContent(String code, String data) {
		try {
			String basePath = "com.ccit.area.sales.web.xml.";
			if ("priceSchemeList".equals(code) || "priceSchemeForm".equals(code) || 
					"priceSchemeItemForm".equals(code) || "productPriceList".equals(code) ||
					 "productPriceForm".equals(code) || "listedList".equals(code) || 
					 "listedForm".equals(code)) {
				basePath += "marketing." + code.replaceAll("List", "").replaceAll("Form", "") + ".";
			} else if (code.indexOf("List") >= 0 || code.indexOf("Form") >= 0) {
				basePath += "archives." + code.replaceAll("List", "").replaceAll("Form", "") + ".";
			} else if (code.indexOf("Choose") >= 0) {
				basePath += "choose.";
			} else {
				
			}
			/** String appFile = "";
			if ("Text".equals(menuCode)) {
				appFile = "com.ccit.area.sales.web.scm.Text";
			} else {
				appFile = "com.ccit.area.sales.web.scm.TextForm";
			} **/
			// 获取参数，并判断是否有值，如果有值并存储在当前请求属性中
			if (StringUtil.isNotBlank(data.replaceAll("\"", ""))) {
				HttpServletRequest request = ServletUtil.getRequest();
				JSONObject jsonObject = JSONObject.parseObject(data);
				for (String key : jsonObject.keySet()) {
					request.setAttribute(key, jsonObject.get(key));
				}
			}
			WebClientXmlParse wxp = new WebClientXmlParse(basePath + code);
			StringBuffer sb = new StringBuffer();
			WebClientObjRender.explain(wxp.getXmlPage(), sb);
			String result = sb.toString().replace(":[,", ":[").replace(",]", "]").replace("	", "");
			return ResponseVO.success(result.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseVO.success();
	}
	
}
