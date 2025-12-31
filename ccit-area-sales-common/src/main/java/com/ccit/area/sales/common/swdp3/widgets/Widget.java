package com.ccit.area.sales.common.swdp3.widgets;

import java.util.*;

public class Widget {
	private String id = null;
	private String name = null;
	private Widget owner = null;
	private Map<String, String> attrMap = new HashMap<String, String>();

	public String getId() {
		return id;
	}

	public Widget setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Widget setName(String name) {
		this.name = name;
		return this;
	}

	public Widget setAttr(String key, String value) {
		attrMap.put(key.toLowerCase(), value);
		return this;
	}

	public String getAttr(String key) {
		return attrMap.get(key.toLowerCase());
	}

	public String getAttr(String key, boolean check) throws Exception {
		if (attrMap.get(key.toLowerCase()) == null && check) {
			throw new Exception("[" + this.getId() + "." + key + "] 不能为空");
		}
		return attrMap.get(key.toLowerCase());
	}

	public String getAttr(String key, String defaultValue) {
		if (attrMap.get(key.toLowerCase()) == null) {
			return defaultValue;
		} else {
			return attrMap.get(key.toLowerCase());
		}
	}

	public String getAttrNone(String key, boolean isHaveQuot, boolean end) {
		String quot = "";
		if (isHaveQuot) {
			quot = "\"";
		}
		if (attrMap.get(key.toLowerCase()) == null) {
			return "";
		} else {
			if (end) {
				return key + ":" + quot + attrMap.get(key.toLowerCase()) + quot;
			} else {
				return key + ":" + quot + attrMap.get(key.toLowerCase()) + quot + ",";
			}
		}
	}

	public Widget getOwner() {
		return owner;
	}

	public Widget setOwner(Widget owner) {
		this.owner = owner;
		return this;
	}

}
