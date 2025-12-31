package com.ccit.area.sales.common.swdp3.widgets;

import java.util.*;

public class ContainerWidget extends Widget {

	List<Widget> childList = new ArrayList<Widget>();
	// 设置静态变量,方便查找
	public static Map<String, Widget> childMap = new HashMap<String, Widget>();

	public ContainerWidget() {

	}

	public void addChild(Widget w) throws Exception {
		childList.add(w);
		if (childMap.containsKey(w.getId())) {
		}
		childMap.put(w.getId(), w);
	}

	public void removeChild(Widget w) {
		childList.remove(w);
		childMap.remove(w.getId());
	}

	public void removeChildByID(String id) {
		Widget w = findWidgetById(id);
		if (w != null) {
			this.removeChild(w);
		}
	}

	public List<Widget> getChildList() {
		return childList;
	}

	public Widget findWidgetById(String key) {
		return childMap.get(key);
	}

	public ContainerWidget findContainerWidgetById(String key) {
		return (ContainerWidget) childMap.get(key);
	}

	public void clearWidgetsFromMap() {
		childMap.clear();
		childList.clear();
	}

}
