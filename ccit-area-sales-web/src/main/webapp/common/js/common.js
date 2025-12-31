/**
 * 统一【get】请求方式
 * @param url 请求地址
 * @param data 参数
 * @param successfn 成功返回值
 * @returns
 */
function get(url, successfn) {
	Ext.Msg.wait("请求正在提交中, 请等候...", "请求", "...");
	Ext.Ajax.request({
		url: url,
		method: "get",
		headers: {
			"Content-Type" : "application/json",
			// "accessToken" : $.cookie("accessToken")
		},
		success: function(response) {
			Ext.Msg.hide();
			var res = Ext.util.JSON.decode(response.responseText);
			if ("200" != res.code) {
				Ext.MessageBox.alert("提示", res.msg);
			} else {
				successfn(res);
			}
		},
		failure: function() {
			Ext.Msg.hide();
			Ext.MessageBox.alert("警告", "服务端连接出现故障，请您稍后重试访问！");
		}
	});
};

/**
 * 统一【post】请求方式
 * @param url 请求地址
 * @param data 参数
 * @param successfn 成功返回值
 * @returns
 */
function post(url, data, successfn) {
	Ext.Msg.wait("请求正在提交中, 请等候...", "请求", "...");
	Ext.Ajax.request({
		url: url,
		method: "post",
		dataType: "json",
		params: JSON.stringify(data),
		headers: {
			"Content-Type" : "application/json",
			// "accessToken" : $.cookie("accessToken")
		},
		success: function(response) {
			Ext.Msg.hide();
			var res = Ext.util.JSON.decode(response.responseText);
			if ("200" != res.code) {
				Ext.MessageBox.alert("提示", res.msg);
			} else {
				successfn(res);
			}
		},
		failure: function() {
			Ext.Msg.hide();
			Ext.MessageBox.alert("警告", "服务端连接出现故障，请您稍后重试访问！");
		}
	});
};

/**
 * 统一【delete】请求方式
 * @param url 请求地址
 * @param data 参数
 * @param successfn 成功返回值
 * @returns
 */
function deleted(url, data, successfn) {
	Ext.Msg.wait("请求正在提交中, 请等候...", "请求", "...");
	Ext.Ajax.request({
		url: url,
		method: "delete",
		dataType: "json",
		params: JSON.stringify(data),
		headers: {
			"Content-Type" : "application/json",
			// "accessToken" : $.cookie("accessToken")
		},
		success: function(response) {
			Ext.Msg.hide();
			var res = Ext.util.JSON.decode(response.responseText);
			if ("200" != res.code) {
				Ext.MessageBox.alert("提示", res.msg);
			} else {
				successfn(res);
			}
		},
		failure: function() {
			Ext.Msg.hide();
			Ext.MessageBox.alert("警告", "服务端连接出现故障，请您稍后重试访问！");
		}
	});
};

/**
 * 处理路由
 * @param code 编码
 * @param data 其他参数，必须JSON格式，如{name: 'x'}
 * @param successfn 成功返回值
 * @returns
 */
function handleRoute(code, data, successfn) {
	Ext.Msg.wait("请求正在提交中, 请等候...", "请求", "...");
	Ext.Ajax.request({
		url: "/getContent.html",
		method: "post",
		params: {code: code, data: JSON.stringify(data)},
		headers: {
			// "accessToken" : $.cookie("accessToken")
		},
		success: function(response) {
			Ext.Msg.hide();
			var res = Ext.util.JSON.decode(response.responseText);
			if ("200" != res.code) {
				Ext.MessageBox.alert("提示", res.msg);
			} else if (successfn != undefined) {
				successfn(res);
			} else {
				window.eval(res.data);
			}
		},
		failure: function() {
			Ext.Msg.hide();
			Ext.MessageBox.alert("警告", "服务端连接出现故障，请您稍后重试访问！");
		}
	});
};

/**
 * 显示连接
 * @param value 值
 * @returns
 */
function linkurl(value, metadata, record) {
	if (value == null || value == undefined) {
		return "";
	} else {
		return "<u style='cursor:pointer;' onclick=handleLinkUrl('"
				+ metadata.column.route + "','" + record.id + "'," + true
				+ ")>" + value + "</u>";
	}
};

/**
 * 处理连接事件
 * @param url 地址
 * @param id 主键
 * @param readOnly 是否只读
 * @returns
 */
function handleLinkUrl(url, id, readOnly) {
	var data = {
		id: id,
		readOnly: readOnly,
		suffixTitle: "详情"
	}
	handleRoute(url, data);
};

/**
 * 处理删除确认弹窗
 * @param msg 提示语
 * @param records 数组
 * @param url 地址
 * @param store 共享
 * @param virtualArray 虚拟数组
 * @returns
 */
function handleDelConfirm(url, records, panel, msg) {
	Ext.Msg.show({
		title: "提示",
		message: msg != undefined ? msg : ("确定要删除" + records.length + "条数据码？"),
		buttons: Ext.Msg.OKCANCEL,
		icon: Ext.Msg.QUESTION,
		fn: function(btn) {
			if (btn === 'ok') {
    		    Ext.Msg.wait("请求正在提交中, 请等候...", "请求", "...");
    		    var ids = [];
    		    var array = panel.operateArray;
    		    var arrayId = panel.operateArray.map(obj => obj.id);
    		    for (var i = 0; i < records.length; i++) {
    		    	if (array.length > 0 && arrayId.includes(records[i].data.id)) {
    		    		array.splice(array.findIndex(obj => obj.id === records[i].data.id), 1);
    		    	} else {
    		    		ids.push(records[i].data.id);
    		    	}
				}
    		    Ext.Msg.hide();
    		    if (ids.length > 0) {
    		    	deleted(url, {ids: ids}, function(res) {
    		    		Ext.MessageBox.alert("提示", res.msg);
    		    		panel.getStore().load();
    		    	});
    		    } else {
    		    	Ext.MessageBox.alert("提示", "删除成功");
    		    	panel.getStore().load();
    		    }
			}
		}
	});
};

/**
 * 存储数组数据
 * @param panel 面板
 * @param data 数据
 * @returns
 */
function setArrayData(panel, data) {
	for (var i = 0; i < data.length; i++) {
		var targetObject = data[i];
		if (!panel.operateArray.includes(targetObject)) {
			panel.operateArray.push(targetObject);
		}
	}
};

/**
 * 获取数组中的属性
 * @param arrayName 数组名称
 * @param attribute 数组属性
 * @returns
 */
function getArrayAttribute(panel, attribute) {
	return panel.operateArray.map(obj => obj[attribute]).join(',');
};

/**
 * 判断值不能为空
 * @param value 值
 * @returns
 */
function isNull(value) {
	if ("" == value || null == value || "" == value.trim()) {
		return true;
	}
	return false;
};
