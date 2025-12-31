Ext.define('adQueryListModel', {
	autoDestroy: true,
	extend: 'Ext.data.Model',
	fields: [{
		name: 'sysid'
	}, {
		name: 'pageid'
	}, {
		name: 'name'
	}, {
		name: 'col'
	}, {
		name: 'operation'
	}, {
		name: 'value'
	}, {
		name: 'orderby'
	}]
});
Ext.define('Ext.grid.plugin.SearchHeaderPlugin', {
	extend: 'Ext.plugin.Abstract',
	requires: ['Ext.grid.*'],
	alias: 'plugin.searchHeaderPlugin',
	pluginId: 'searchHeaderPlugin',
	disabled: false,
	config: {
		dynamic: false
	},
	init: function(grid) {
		me = this;
		me.grid = grid;
		me.toolbar = Ext.getCmp(grid.id + 'Tbar');
		this.initBar();
		me.isexpand = false;
		this.aqWin = null;
		this.pageMap = new Map();
	},
	destroy: function() {
		delete this.grid;
		delete this.quickButton;
		delete this.queryButton;
		delete this.adQueryButton;
		delete this.exportModuleButton;
		delete this.importModuleButton;
		delete this.showFiltersButton;
		delete this.exportExcelButton;
		delete this.showFiltersButton;
		delete this.clearFiltersButton;
		delete this.dataAddButton;
		delete this.dataSumButton;
		delete this.printButton;
		delete this.aqWin;
		delete this.aqCombox;
		delete this.adQueryList;
		delete this.toolbar;
		delete this.pageMap;
		delete this.exportExcelModuleButton;

	},

	// 初始化按钮
	initBar: function() {
		if (this.toolbar) {
			this.toolbar.add(this.initQueryButton());
//			this.toolbar.add(this.initExportExcelButton());
//			if ('choose' == this.grid.op) {
//				this.toolbar.add(this.initQueryButton());
//			}
//			if ('read' == this.grid.op) {
//				this.toolbar.add(this.initQueryButton());
//			}
//			if ('export' == this.grid.op) {
//				this.toolbar.add(this.initExportExcelButton());
//			}
//			if ('exportModule' == this.grid.op) {
//				this.toolbar.add(this.initQueryButton());
//				this.toolbar.add(this.initExportModuleExcelButton());
//				this.toolbar.add(this.initImportModuleButton());
//			}
//			if ('import' == this.grid.op) {
//				this.toolbar.add(this.initImportModuleButton());
//				this.toolbar.add(this.initExportModuleButton());
//			}
//			if ('mdmEdit' == this.grid.op) {
//				this.toolbar.add(this.initImportModuleButton());
//				this.toolbar.add(this.initQueryButton());
//			}
//			if ('mdmAudit' == this.grid.op) {
//				this.toolbar.add(this.initQueryButton());
//			}
		}
	},
	// 汇总Field
	initSumField: function() {
		this.sumField = Ext.create('Ext.form.Field', {
			fieldLabel: '总数量：',
			id: this.grid.id + 'Text',
			labelAlign: 'right',
			readOnly: true,
			labelSeparator: '',
			width: 200,
			xtype: "tbfill"
		});
		return this.sumField;
	},
	// 初始化快捷查询按钮
	initQuickButton: function() {
		this.quickButton = Ext.create('Ext.Button', {
			text: '查询',
			tooltip: '查询',
			grid: this.grid,
			iconCls: 'quickqueryButton'
		});
		this.quickButton.on('click', function(btn, event) {
			btn.setTooltip(this.isexpand ? '查询' : '查询');
			btn.setText(this.isexpand ? '查询' : '查询');
			// 设置是否显示
			for (i = 0; i < this.grid.columns.length; i++) {
				var c = this.grid.columns[i];
				if (c.dataIndex && !c.hidden) {
					var cf = this.grid.id + 'AA' + c.dataIndex
						.replace('$', 'AA');
					var kf = Ext.getCmp(cf);
					kf.setHidden(this.isexpand);
				}
			}
			this.isexpand = (this.isexpand ? false : true);
		});
		return this.quickButton;
	},
	// 初始化查询按钮
	initQueryButton: function() {
		this.queryButton = Ext.create('Ext.Button', {
			text: '查询',
			tooltip: '查询',
			grid: this.grid,
			iconCls: 'searchButton'
		});
		this.queryButton.on('click', function(btn, event) {
			var params = this.grid.getStore().proxy.extraParams.param;
			for (i = 0; i < this.grid.columns.length; i++) {
				var column = this.grid.columns[i];
				if (column.dataIndex) {
					if (column.dataIndex && !column.hidden) {
						var columnCmp = Ext.getCmp(this.grid.getId() + "_" + column.dataIndex);
						params[column.dataIndex] = columnCmp.getValue();
					}
				}
			}
			this.grid.getStore().proxy.extraParams.param = params;
			this.grid.getStore().load();
		});
		return this.queryButton;
	},

	// 初始化高级查询按钮
	initAdqueryButton: function() {
		this.adQueryButton = Ext.create('Ext.Button', {
			text: '高级查询',
			tooltip: '高级查询',
			gridid: this.grid.id,
			plugin: this,
			iconCls: 'adqueryButton',
			handler: function() {
				this.plugin.showWin();
			}
		});
		return this.adQueryButton;
	},

	// 初始化模板导出按钮
	initExportModuleButton: function() {
		this.exportModuleButton = Ext.create('Ext.Button', {
			text: '模板导出',
			tooltip: '模板导出',
			gridid: this.grid.id,
			iconCls: 'downData',
			plugin: this,
			handler: function() {
				this.plugin.exportModule();
			}
		});
		return this.exportModuleButton;
	},

	// 初始化模板导入按钮
	initImportModuleButton: function() {
		this.importModuleButton = Ext.create('Ext.Button', {
			text: '数据导入',
			tooltip: '数据导入',
			plugin: this,
			iconCls: 'uploadData',
			handler: function() {
				this.plugin.importModule();
			}
		});
		return this.importModuleButton;
	},

	// 初始化数据导出按钮
	initExportExcelButton: function() {
		this.exportExcelButton = Ext.create('Ext.Button', {
			text: '数据导出',
			tooltip: '数据导出(Excel)',
			plugin: this,
			iconCls: 'excleicon',
			handler: function() {
				this.plugin.exportExcel();
			}
		});
		return this.exportExcelButton;
	},

	// 初始化数据模板导出按钮
	initExportModuleExcelButton: function() {
		this.exportExcelModuleButton = Ext.create('Ext.Button', {
			text: '数据模板导出',
			tooltip: '数据模板导出(Excel)',
			plugin: this,
			iconCls: 'excleicon',
			handler: function() {
				this.plugin.exportModuleExcel();
			}
		});
		return this.exportExcelModuleButton;
	},

	// 初始化过滤按钮
	initShowFiltersButton: function() {
		this.showFiltersButton = Ext.create('Ext.Button', {
			text: '显示过滤条件',
			tooltip: '显示过滤条件',
			plugin: this,
			iconCls: 'conf',
			handler: function() {
				if (this.plugin.pageMap.size() > 0) {
					var v = this.plugin.pageMap.values();
					var commitStr = "-1";
					for (var i = 0; i < v.length; i++) {
						commitStr += v[i];
					}
					var paramStr = '系统编码:' + commitStr;
					Ext.Msg.alert('过滤数据', msg);
				} else {
					this.plugin.showFilters();
				}
			}
		});
		return this.showFiltersButton;
	},

	// 初始化过滤按钮
	initClearFiltersButton: function() {
		this.clearFiltersButton = Ext.create('Ext.Button', {
			text: '清空过滤条件',
			tooltip: '清空过滤条件',
			plugin: this,
			iconCls: 'adqueryButton',
			handler: function() {
				this.plugin.grid.filters.clearFilters();
				this.plugin.pageMap.clear();
				// console.log(this.plugin.isexpand);
				if (!this.plugin.isexpand) {
					for (i = 0; i < this.plugin.grid.columns
						.length; i++) {
						var c = this.plugin.grid.columns[
							i];
						if (c.dataIndex && !c.hidden) {
							var cf = this.plugin.grid.id +
								'AA' + c.dataIndex.replace(
									'$', 'AA');
							var kf = Ext.getCmp(cf);
							kf.setValue('');
						}
					}
				}
			}
		});
		return this.clearFiltersButton;
	},
	// 初始化数据记忆按钮
	initDataAddButton: function() {
		this.dataAddButton = Ext.create('Ext.Button', {
			text: '记忆数据',
			tooltip: '记忆数据',
			plugin: this,
			iconCls: 'filteradd',
			handler: function() {
				// 替换数据
				if (this.plugin.pageMap.containsKey(
					this.plugin.grid.getStore()
					.currentPage)) {
					this.plugin.pageMap.remove(this.plugin
						.grid.getStore()
						.currentPage);
				}
				var records = this.plugin.grid.getSelectionModel()
					.getSelection();
				var commitStr = "";
				for (i = 0; i < records.length; i++) {
					commitStr += "," + records[i].get(
						'sysid$number') + "";
				}
				// console.log(commitStr);
				this.plugin.pageMap.put(this.plugin.grid
					.getStore()
					.currentPage, commitStr);
			}
		});
		return this.dataAddButton;
	},
	// 初始化记忆汇总按钮
	initDataSumButton: function() {
		this.dataSumButton = Ext.create('Ext.Button', {
			text: '记忆汇总',
			tooltip: '记忆汇总',
			plugin: this,
			iconCls: 'filtersum',
			handler: function() {
				var v = this.plugin.pageMap.values();
				var commitStr = "-1";
				for (var i = 0; i < v.length; i++) {
					// console.log(v[i]);
					commitStr += v[i];
				}
				// console.log(commitStr);loadPage
				// store.loadPage(s.page,{params:{page:s.page,start:s.start,limit:s.limit,otherParam:s.otherParam}});
				var paramStr = '{"sysid$number$in":"' +
					commitStr + '"}';
				this.plugin.grid.getStore()
					.loadPage(1, {
						params: {
							page: 1,
							start: 0,
							limit: 50,
							otherParam: paramStr
						}
					});
			}
		});
		return this.dataSumButton;
	},
	initPrintButton: function() {
		this.printButton = Ext.create('Ext.Button', {
			text: '打印',
			tooltip: '打印',
			plugin: this,
			iconCls: 'print',
			handler: function() {
				var records = this.plugin.grid.getSelectionModel()
					.getSelection();
				for (var i = 0; i < records.length; i++) {
					var sysid = records[i].get(
						'sysid$number');
					var tables = this.plugin.grid.tables;
					window.open('printAction.html?s=' +
						encodeURIComponent(this.plugin
							.grid.id + ',' + sysid +
							',' + this.plugin.grid.tables +
							',' + this.plugin.grid.op
						), '打印',
						'height=600, width=1024, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no'
					);
				}
			}
		});
		return this.printButton;
	},

	// 显示高级查询窗口
	showWin: function() {
		var aqgridID = this.grid.id;
		adQueryNameValue = '默认';
		var a = this.grid;
		var aqdata = [];
		var aqOpData = [];
		for (i = 0; i < a.columns.length; i++) {
			var c = a.columns[i];
			if (c.dataIndex.indexOf('$') > -1) {
				aqdata.push({
					id: c.text,
					name: c.text,
					code: c.dataIndex
				});
			}
		}

		if (!this.aqWin) {

			var adQueryListstore = Ext.create('Ext.data.JsonStore', {
				model: 'adQueryListModel',
				autoDestroy: true,
				remoteFilter: false,
				remoteSort: false,
				pageSize: 50,
				proxy: {
					type: 'ajax',
					url: 'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=queryList',
					reader: {
						type: 'json',
						rootProperty: 'root',
						totalProperty: 'total'
					}
				},
				autoLoad: false
			});
			adQueryListstore.load({
				params: {
					start: 0,
					limit: 50,
					pageid: aqgridID,
					name: encodeURIComponent(
						adQueryNameValue)
				}
			});

			var aqComboxStore = Ext.create('Ext.data.JsonStore', {
				autoDestroy: true,
				fields: [{
					name: 'id'
				}, {
					name: 'name'
				}],
				autoDestroy: true,
				remoteFilter: false,
				remoteSort: false,
				pageSize: 50,
				proxy: {
					type: 'ajax',
					url: 'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=queryNames',
					reader: {
						type: 'json',
						rootProperty: 'root',
						totalProperty: 'total'
					}
				},
				autoLoad: false
			});
			aqComboxStore.load({
				params: {
					start: 0,
					limit: 50,
					pageid: aqgridID
				}
			});

			this.aqCombox = Ext.create('Ext.form.ComboBox', {
				plugin: this,
				autoDestroy: true,
				ids: 'adQueryName',
				fieldLabel: '查询名',
				labelAlign: 'right',
				labelWidth: 80,
				width: 200,
				allowBlank: false,
				store: aqComboxStore,
				displayField: 'name',
				valueField: 'name',
				queryMode: 'local',
				ids: 'changeSelectName',
				listeners: {
					scope: this,
					'change': function(combo, newValue,
						oldValue, eOpts) {
						adQueryNameValue = newValue;
						combo.plugin.adQueryList.getStore()
							.load({
								params: {
									start: 0,
									limit: 50,
									pageid: combo.plugin
										.grid.id,
									name: encodeURIComponent(
										adQueryNameValue
									)
								}
							});
					}
				},
				value: '默认',
				allowBlank: false,
				xtype: 'combobox'
			});

			this.adQueryList = Ext.create('Ext.grid.Panel', {
				autoDestroy: true,
				store: adQueryListstore,
				columns: [{
						header: '系统编码',
						width: 120,
						dataIndex: 'sysid',
						hidden: true,
						editor: {
							xtype: 'displayfield'
						}
					}, {
						header: '页面ID',
						width: 120,
						dataIndex: 'pageid',
						hidden: true,
						editor: {
							xtype: 'displayfield'
						}
					},
					{
						header: '查询名',
						width: 120,
						dataIndex: 'name',
						hidden: true,
						editor: {
							xtype: 'displayfield'
						}
					},
					{
						header: '字段变量',
						width: 120,
						dataIndex: 'colname',
						hidden: true,
						editor: {
							xtype: 'displayfield'
						}
					},
					{
						header: '字段名',
						width: 120,
						dataIndex: 'col',
						hidden: false,
						editor: {
							autoDestroy: true,
							width: 300,
							editable: false,
							allowBlank: false,
							store: Ext.create(
								'Ext.data.Store', {
									fields: [{
										name: 'id'
									}, {
										name: 'name'
									}, {
										name: 'code'
									}],
									autoDestroy: true,
									data: aqdata
								}),
							displayField: 'name',
							valueField: 'name',
							queryMode: 'local',
							xtype: 'combobox'
						}
					},
					{
						header: '条件',
						width: 120,
						dataIndex: 'operation',
						hidden: false,
						editor: {
							autoDestroy: true,
							width: 200,
							editable: false,
							allowBlank: false,
							store: Ext.create(
								'Ext.data.Store', {
									fields: [{
										name: 'id'
									}, {
										name: 'name'
									}],
									data: [{
										id: '等于',
										name: '等于'
									}, {
										id: '大于',
										name: '大于'
									}, {
										id: '大于等于',
										name: '大于等于'
									}, {
										id: '小于',
										name: '小于'
									}, {
										id: '小于等于',
										name: '小于等于'
									}, {
										id: '包含',
										name: '包含'
									}, {
										id: '左包含',
										name: '左包含'
									}, {
										id: '右包含',
										name: '右包含'
									}],
									autoDestroy: true
								}),
							displayField: 'name',
							valueField: 'name',
							queryMode: 'local',
							xtype: 'combobox'
						}
					},
					{
						header: '值',
						width: 120,
						dataIndex: 'value',
						hidden: false,
						editor: {
							xtype: 'textfield'
						}
					}, {
						header: '排序',
						width: 120,
						dataIndex: 'orderby',
						hidden: false,
						editor: {
							xtype: 'numberfield'
						}
					}, {
						xtype: 'actioncolumn',
						width: 30,
						sortable: false,
						menuDisabled: true,
						items: [{
							icon: 'ext6/examples/classic/shared/icons/fam/delete.gif',
							tooltip: '删除',
							scope: this,
							handler: function(
								grid,
								rowIndex) {
								this.adQueryList
									.getStore()
									.removeAt(
										rowIndex
									);
							}
						}]
					}
				],
				forceFit: false,
				height: 285,
				selType: 'rowmodel',
				viewConfig: {
					forceFit: false
				},
				stripeRows: true,
				split: true,
				tbar: [this.aqCombox,
					{
						autoDestroy: true,
						ids: 'adQueryListQuery',
						plugin: this,
						iconCls: 'quickqueryButton',
						text: '查询',
						tooltip: '查询',
						handler: this.dealEvent
					}, {
						autoDestroy: true,
						ids: 'adQueryListSave',
						plugin: this,
						iconCls: 'saveButton',
						text: '保存查询',
						tooltip: '保存查询',
						handler: this.dealEvent
					}, {
						autoDestroy: true,
						ids: 'adQueryListDel',
						plugin: this,
						iconCls: 'deleteButton',
						text: '删除查询',
						tooltip: '删除查询',
						handler: this.dealEvent
					}, {
						autoDestroy: true,
						ids: 'adQueryListAddItem',
						plugin: this,
						iconCls: 'newButton',
						text: '新增查询项',
						tooltip: '新增查询项',
						handler: this.dealEvent
					}
				],
				plugins: [Ext.create(
					'Ext.grid.plugin.CellEditing', {
						autoDestroy: true,
						clicksToEdit: 1,
						listeners: {
							scope: this,
							'beforeedit': function(
								editor, context,
								eOpts) {
								// console.dir(context);
								// console.log('fff===='+context.record.get('col'));
								if (context.field ==
									'operation'
								) {
									for (i = 0; i <
										aqdata.length; i++
									) {
										if (
											aqdata[
												i
											].name ==
											context
											.record
											.get(
												'col'
											)) {
											context
												.record
												.set(
													'colname',
													aqdata[
														i
													]
													.code
												);
										}
									}
									// console.log('fff11===='+context.record.get('colname'));
									// b=this.adQueryList.columns[5].getEditor().getStore();
									// console.dir(b);
									// console.log('before removeAll');
									// b.removeAll();
									// console.dir(b);
									// console.log('removeAll');
									// b.add({id: '等于',name: '等于'},{id: '大于',name: '大于'},{id: '大于等于',name:
									// '大于等于'},{id: '小于',name: '小于'},{id: '小于等于',name: '小于等于'});
									// if(context.record.get('colname').indexOf('string')>-1){b.add({id:
									// '等于',name: '等于'},{id: '包含',name: '包含'},{id: '左包含',name:
									// '左包含'},{id: '右包含',name: '右包含'});}
									// else{b.add({id: '等于',name: '等于'},{id: '大于',name: '大于'},{id:
									// '大于等于',name: '大于等于'},{id: '小于',name: '小于'},{id: '小于等于',name:
									// '小于等于'});}
								}

							}
						}
					})]
			});

			this.aqWin = Ext.create('widget.window', {
				title: '高级查询',
				header: {
					titlePosition: 2,
					titleAlign: 'center'
				},
				closable: true,
				closeAction: 'hide',
				maximizable: true,
				shadow: true,
				width: 900,
				minWidth: 350,
				height: 350,
				tools: [{
					type: 'pin'
				}],
				items: [this.adQueryList]
			});
			this.aqWin.show();
		} else {
			this.aqWin.show();
			adQueryNameValue = '默认';
			this.aqCombox.setValue(adQueryNameValue);
			this.aqCombox.getStore()
				.load({
					params: {
						start: 0,
						limit: 50,
						pageid: aqgridID
					}
				});
			this.adQueryList.getStore()
				.load({
					params: {
						start: 0,
						limit: 50,
						pageid: aqgridID,
						name: encodeURIComponent(
							adQueryNameValue)
					}
				});

		}

	},
	// 处理按钮事件
	dealEvent: function(t) {
		// 新增查询项
		if (t.ids == 'adQueryListAddItem') {
			// console.dir(t.plugin);
			var rec = Ext.create('adQueryListModel', {
				pageid: t.plugin.grid.id,
				name: adQueryNameValue,
				col: '',
				operation: '包含',
				value: '',
				orderby: t.plugin.adQueryList.getStore()
					.getCount(),
				active: true
			});

			rowEditing = t.plugin.adQueryList.plugins[0];
			rowEditing.cancelEdit();
			t.plugin.adQueryList.store.insert(0, rec);
			rowEditing.startEdit(0, 0);
		}
		// 查询
		if (t.ids == 'adQueryListQuery') {
			var a = t.plugin.adQueryList.getStore();
			var param = '{';
			for (i = 0; i < a.getCount(); i++) {
				var r = a.getAt(i);
				var v = r.get('colname');
				var o = r.get('operation');
				// console.log('o='+o);
				var k = r.get('value');
				if ('等于' == o) {
					o = '$dengyu';
				}
				if ('大于' == o) {
					o = '$dayu';
				}
				if ('大于等于' == o) {
					o = '$dayudengyu';
				}
				if ('小于' == o) {
					o = '$xiaoyu';
				}
				if ('小于等于' == o) {
					o = '$xiaoyudengyu';
				}
				if ('包含' == o) {
					o = '$alllike';
				}
				if ('右包含' == o) {
					o = '$rightlike';
				}
				if ('左包含' == o) {
					o = '$leftlike';
				}
				if (o != undefined && v != undefined && k !=
					undefined && k != '' && o != '' && v != '') {
					param += '"' + v + o + '":"' +
						encodeURIComponent(k) + '",';
				}
			}
			if (param.length > 3) {
				param = param.substring(0, param.length - 1);
			}
			param += '}';

			var g = t.plugin.grid.getStore();
			g.load({
				params: {
					start: 0,
					limit: 50,
					otherParam: param
				}
			});
		}
		// 保存查询
		if (t.ids == 'adQueryListSave') {
			// console.log('adQueryListSave..........');
			// 增加默认值
			var a = t.plugin.adQueryList.getStore();
			var aqgridID = t.plugin.grid.id;
			for (i = 0; i < a.data.length; i++) {
				var d = a.data.items[i].data;
				d.name = adQueryNameValue;
				d.pageid = t.plugin.grid.id;
			}
			var json = form2Json([{
				table: 't_system_query',
				detail: eg2form2(t.plugin.adQueryList,
					't_system_query')
			}]);
			submitForm(
				'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=deleteAdvanceQuery&pageid=' +
				t.plugin.grid.id, encodeURIComponent(
					adQueryNameValue));
			syncSubmitForm(
				'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=saveAdvanceQuery',
				json);
			t.plugin.adQueryList.getStore()
				.load({
					params: {
						start: 0,
						limit: 50,
						pageid: aqgridID,
						name: encodeURIComponent(
							adQueryNameValue)
					}
				});
			var fr = t.plugin.aqCombox.getStore()
				.findRecord("id", adQueryNameValue);
			if (!fr) { // 新增
				t.plugin.aqCombox.getStore()
					.add({
						id: adQueryNameValue
					}, {
						name: adQueryNameValue
					});
			}

		}
		// 删除查询
		if (t.ids == 'adQueryListDel') {
			submitForm(
				'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=deleteAdvanceQuery&pageid=' +
				t.plugin.grid.id, encodeURIComponent(
					adQueryNameValue));
			t.plugin.adQueryList.getStore()
				.load({
					params: {
						start: 0,
						limit: 50,
						pageid: t.plugin.grid.id,
						name: encodeURIComponent('默认')
					}
				});
			t.plugin.aqCombox.getStore()
				.load({
					params: {
						start: 0,
						limit: 50,
						pageid: t.plugin.grid.id
					}
				});
		}

	},


	// 显示查询条件
	showFilters: function() {
		var a = this.grid;
		var msg = "";
		var columnData = [];
		for (i = 0; i < a.columns.length; i++) {
			var c = a.columns[i];
			if (c.dataIndex.indexOf('$') > -1) {
				columnData.push({
					name: c.text,
					code: c.dataIndex
				});
			}
		}

		var b = a.getStore()
			.getFilters();
		b.each(function(filter) {
			for (i = 0; i < columnData.length; i++) {
				var d = columnData[i];
				if (d.code == filter.serialize()
					.property) {
					// console.log(d.name+','+filter.serialize().operator+','+filter.serialize().value);
					if (filter.serialize()
						.operator == 'lt') {
						msg += d.name + ' [小于] ' + filter.serialize()
							.value;
					}
					if (filter.serialize()
						.operator == 'gt') {
						msg += d.name + ' [大于] ' + filter.serialize()
							.value;
					}
					if (filter.serialize()
						.operator == 'eg') {
						msg += d.name + ' [等于] ' + filter.serialize()
							.value;
					}
					if (filter.serialize()
						.operator == 'like') {
						msg += d.name + ' [包含] ' + filter.serialize()
							.value;
					}
					msg += '<br/>';
				}
			}
		});
		Ext.Msg.alert('过滤数据', msg);
	},
	// 导出模板
	exportModule: function() {
		var tname = this.grid.tables;
		if (tname != '' && tname != undefined) {
			window.open(
				'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=exportModule&t=' +
				this.stringToHex(tname), 'exportModule', '');
		}
	},
	// 导入模板
	importModule: function() {
		tname = this.grid.tables;
		var rgrid = this.grid;
		var uploadfp = new Ext.FormPanel({
			fileUpload: true,
			width: 500,
			frame: true,
			grid: this.grid,
			autoHeight: true,
			bodyStyle: 'padding: 10px 10px 0 10px;',
			labelWidth: 50,
			defaults: {
				anchor: '95%',
				allowBlank: false,
				msgTarget: 'side'
			},
			items: [{
				xtype: 'fileuploadfield',
				id: 'form-file',
				emptyText: '选择上传文件',
				fieldLabel: '文件',
				name: 'filePath',
				buttonText: '选择...'
			}],
			buttons: [{
				text: '保存',
				handler: function() {
					if (uploadfp.getForm()
						.isValid()) {
						uploadfp.getForm()
							.submit({
								url: 'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=importModule',
								waitMsg: '正在上传中...',
								success: function(
									fp,
									o) {
									Ext
										.Msg
										.alert(
											'提示',
											o
											.result
											.file
										);
									rgrid
										.getStore()
										.reload();
								}
							});
					}
				}
			}, {
				text: '重置',
				handler: function() {
					uploadfp.getForm()
						.reset();
				}
			}]
		});

		var winUpload = new Ext.Window({
			title: '导入数据',
			layout: 'fit',
			width: 400,
			height: 180,
			plain: true,
			items: [uploadfp],
			buttons: [{
				text: '关闭',
				handler: function() {
					winUpload.close();
				}
			}]
		});
		winUpload.show();
	},
	// 将字符转成16进制
	stringToHex: function(str) {
		var val = "";
		for (var i = 0; i < str.length; i++) {
			val += str.charCodeAt(i)
				.toString(16);
		}
		return val;
	},
	serializeParams: function(obj) {
		var str = [];
		for (var p in obj)
			if (obj.hasOwnProperty(p)) {
				str.push(encodeURIComponent(p) + "=" +
					encodeURIComponent(obj[p]));
			}
		return str.join("&");
	},
	exportExcel: function() {
		var a = this.grid.getStore();
		var url = a.proxy.url;
		var params = a.proxy.extraParams;
		if (params) {
			// var appFile = a.proxy.extraParams.appFile;
			// var methodName = a.proxy.extraParams.methodName;
			// url = url+"?appFile="+appFile+"&methodName="+methodName+"&";
			if (url.indexOf("MainAction.action?") > -1) {
				if (url == 'MainAction.action?') {
					url = url + this.serializeParams(params) + "&";
				} else {
					url = url + "&" + this.serializeParams(params) +
						"&";
				}
			} else {
				url = url + "?" + this.serializeParams(params) +
					"&";
			}
		} else {
			url = url + "?";
		}
		var b = this.grid;
		var q = '';
		for (i = 0; i < b.columns.length; i++) {
			var c = b.columns[i];
			if ((s = c.dataIndex.indexOf('$')) > -1 && !c.hidden) {
				q += c.dataIndex.substring(0, s) + ',' + c.text +
					';';
			}
		}
		// q=toUTF8(q);
		if (a.lastOptions.params) {
			window.open(url + 'toExcel=true&otherParam=' +
				encodeURIComponent(a.lastOptions.params.otherParam) +
				'&title=' + encodeURIComponent(
					encodeURIComponent(q)), 'exportExcel', '');
		} else {
			if (a.lastOptions.filters) {
				var filters = a.getFilters()
					.items;
				vfilters = '[';
				for (i = 0; i < filters.length; i++) {
					if (i > 0) {
						vfilters += ',';
					}
					vfilters += '{"property":"' + filters[0]._property +
						'","operator":"' + filters[0]._operator +
						'","value":"' + encodeURIComponent(filters[
							0]._value) + '"}';
				}
				vfilters += ']';
				window.open(url + 'toExcel=true&filter=' +
					encodeURIComponent(vfilters) + '&title=' +
					encodeURIComponent(encodeURIComponent(q)),
					'exportExcel', '');
			} else {
				window.open(url + 'toExcel=true&title=' +
					encodeURIComponent(encodeURIComponent(q)),
					'exportExcel', '');
			}
		}
	},
	exportModuleExcel: function() {
		var a = this.grid.getStore();
		var url = a.proxy.url;
		var params = a.proxy.extraParams;
		if (params) {
			// var appFile = a.proxy.extraParams.appFile;
			// var methodName = a.proxy.extraParams.methodName;
			// url = url+"?appFile="+appFile+"&methodName="+methodName+"&";
			if (url.indexOf("MainAction.action?") > -1) {
				if (url == 'MainAction.action?') {
					url = url + this.serializeParams(params) + "&";
				} else {
					url = url + "&" + this.serializeParams(params) +
						"&";
				}
			} else {
				url = url + "?" + this.serializeParams(params) +
					"&";
			}
		} else {
			url = url + "?";
		}
		var b = this.grid;
		var t = b.tables;
		var q = '';
		for (i = 0; i < b.columns.length; i++) {
			var c = b.columns[i];
			if ((s = c.dataIndex.indexOf('$')) > -1 && !c.hidden) {
				q += "'" + c.dataIndex.substring(0, s) + "',";
			}
		}
		// q=toUTF8(q);
		if (q != '') {
			if (q.indexOf("sysid") > -1 || q.indexOf("SYSID") > -1) {
				q = q.substring(0, q.length - 1);
			} else {
				q += "'sysid'";
			}
		}
		if (a.lastOptions.params) {
			window.open(url + '&toExcelModule=true&otherParam=' +
				encodeURIComponent(a.lastOptions.params.otherParam) +
				'&title=' + encodeURIComponent(
					encodeURIComponent(q)) + '&t=' + this.stringToHex(
					t), 'exportExcel', '');
		} else {
			if (a.lastOptions.filters) {
				var filters = a.getFilters()
					.items;
				vfilters = '[';
				for (i = 0; i < filters.length; i++) {
					if (i > 0) {
						vfilters += ',';
					}
					vfilters += '{"property":"' + filters[0]._property +
						'","operator":"' + filters[0]._operator +
						'","value":"' + encodeURIComponent(filters[
							0]._value) + '"}';
				}
				vfilters += ']';
				window.open(url + '&toExcelModule=true&filter=' +
					encodeURIComponent(vfilters) + '&title=' +
					encodeURIComponent(encodeURIComponent(q)) +
					'&t=' + this.stringToHex(t), 'exportExcel',
					'');
			} else {
				window.open(url + '&toExcelModule=true&title=' +
					encodeURIComponent(encodeURIComponent(q)) +
					'&t=' + this.stringToHex(t), 'exportExcel',
					'');
			}
		}
	}
});
Ext.define("Ext.grid.plugin.EditGridPlugin", {
	extend: "Ext.plugin.Abstract",
	requires: ["Ext.grid.*"],
	alias: "plugin.editGridPlugin",
	pluginId: "editGridPlugin",
	disabled: false,
	config: {
		dynamic: false
	},
	init: function(grid) {
		me = this;
		me.grid = grid;
		me.toolbar = Ext.getCmp('tbar' + grid.id);
		this.initBar();
		me.isexpand = false;
	},
	destroy: function() {
		// console.log('destroy');
		delete this.grid;
		delete this.queryButton;
		delete this.adQueryButton;
		delete this.exportModuleButton;
		delete this.importModuleButton;
		delete this.exportExcelButton;
		delete this.exportExcelModuleButton;

	},

	// 初始化按钮
	initBar: function() {
		if (this.toolbar) {
			if ('query' == this.grid.op) {
				this.toolbar.add(this.initQueryButton());
				this.toolbar.add(this.initExportExcelButton());
			}
			if ('choose' == this.grid.op) {
				this.toolbar.add(this.initQueryButton());
			}
			if ('export' == this.grid.op) {
				this.toolbar.add(this.initExportExcelButton());
			}
			if ('exportModule' == this.grid.op) {
				this.toolbar.add(this.initQueryButton());
				this.toolbar.add(this.initExportModuleExcelButton());
				this.toolbar.add(this.initImportModuleButton());
			}
			if ('import' == this.grid.op) {
				this.toolbar.add(this.initImportModuleButton());
				this.toolbar.add(this.initExportModuleButton());
			}

		}
	},
	// 初始化查询按钮
	initQueryButton: function() {
		this.queryButton = Ext.create('Ext.Button', {
			text: '查询',
			tooltip: '查询',
			grid: this.grid,
			iconCls: 'search'
		});
		this.queryButton.on('click', function(btn, event) {
			var paramStr = '{';
			var k = 0;
			for (i = 0; i < this.grid.columns.length; i++) {
				var c = this.grid.columns[i];

				if (c.dataIndex) {
					var cf = this.grid.id + 'AA' + c.dataIndex
						.replace('$', 'AA');
					var kf = Ext.getCmp(cf);
					if (typeof(kf.getValue()) == 'number' ||
						kf.getValue()) {
						var tmp = cf.split('AA');
						var v = kf.getValue();
						if ((v + '')
							.indexOf("UTC") > -1 || (v + '')
							.indexOf("GMT") > -1) {
							v = v.DateToString("full");
						} else {
							v = encodeURIComponent(v);
						}
						// console.log(cf+','+tmp[0]+','+tmp[1]+','+tmp[2]);
						var op = tmp[2] == 'string' ?
							'alllike' : 'alllike';
						if (k == 0) {
							paramStr += '"' + tmp[1] + '$' +
								tmp[2] + '$' + op + '":"' +
								v + '"';
						} else {
							paramStr += ',"' + tmp[1] + '$' +
								tmp[2] + '$' + op + '":"' +
								v + '"';
						}
						k++;
					}
				}
			}
			paramStr += '}';
			// console.log(paramStr);
			this.grid.getStore()
				.load({
					params: {
						start: 0,
						limit: 50,
						otherParam: paramStr
					}
				});
			this.grid.getStore()
				.lastOptions.params.otherParam = paramStr;
		});
		return this.queryButton;
	},
	// 初始化数据导出按钮
	initExportExcelButton: function() {
		this.exportExcelButton = Ext.create('Ext.Button', {
			text: '数据导出',
			tooltip: '数据导出(Excel)',
			plugin: this,
			iconCls: 'excleicon',
			handler: function() {
				this.plugin.exportExcel();
			}
		});
		return this.exportExcelButton;
	},
	// 初始化数据模板导出按钮
	initExportModuleExcelButton: function() {
		this.exportExcelModuleButton = Ext.create('Ext.Button', {
			text: '数据模板导出',
			tooltip: '数据模板导出(Excel)',
			plugin: this,
			iconCls: 'excleicon',
			handler: function() {
				this.plugin.exportModuleExcel();
			}
		});
		return this.exportExcelModuleButton;
	},
	// 初始化模板导入按钮
	initImportModuleButton: function() {
		this.importModuleButton = Ext.create('Ext.Button', {
			text: '数据导入',
			tooltip: '数据导入',
			plugin: this,
			iconCls: 'uploadData',
			handler: function() {
				this.plugin.importModule();
			}
		});
		return this.importModuleButton;
	},
	// 初始化模板导出按钮
	initExportModuleButton: function() {
		this.exportModuleButton = Ext.create('Ext.Button', {
			text: '模板导出',
			tooltip: '模板导出',
			gridid: this.grid.id,
			iconCls: 'downData',
			plugin: this,
			handler: function() {
				this.plugin.exportModule();
			}
		});
		return this.exportModuleButton;
	},
	// 导出模板
	exportModule: function() {
		var tname = this.grid.tables;
		if (tname != '' && tname != undefined) {
			window.open(
				'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=exportModule&t=' +
				this.stringToHex(tname), 'exportModule', '');
		}
	},
	// 导入模板
	importModule: function() {
		tname = this.grid.tables;
		var rgrid = this.grid;
		var uploadfp = new Ext.FormPanel({
			fileUpload: true,
			width: 500,
			frame: true,
			grid: this.grid,
			autoHeight: true,
			bodyStyle: 'padding: 10px 10px 0 10px;',
			labelWidth: 50,
			defaults: {
				anchor: '95%',
				allowBlank: false,
				msgTarget: 'side'
			},
			items: [{
				xtype: 'fileuploadfield',
				id: 'form-file',
				emptyText: '选择上传文件',
				fieldLabel: '文件',
				name: 'filePath',
				buttonText: '选择...'
			}],
			buttons: [{
				text: '保存',
				handler: function() {
					if (uploadfp.getForm()
						.isValid()) {
						uploadfp.getForm()
							.submit({
								url: 'MainAction.action?appFile=com.swdp3.core.common.Common&methodName=importModule',
								waitMsg: '正在上传中...',
								success: function(
									fp,
									o) {
									Ext
										.Msg
										.alert(
											'提示',
											o
											.result
											.file
										);
									rgrid
										.getStore()
										.reload();
								}
							});
					}
				}
			}, {
				text: '重置',
				handler: function() {
					uploadfp.getForm()
						.reset();
				}
			}]
		});

		var winUpload = new Ext.Window({
			title: '导入数据',
			layout: 'fit',
			width: 400,
			height: 180,
			plain: true,
			items: [uploadfp],
			buttons: [{
				text: '关闭',
				handler: function() {
					winUpload.close();
				}
			}]
		});
		winUpload.show();
	},
	// 将字符转成16进制
	stringToHex: function(str) {
		var val = "";
		for (var i = 0; i < str.length; i++) {
			val += str.charCodeAt(i)
				.toString(16);
		}
		return val;
	},
	exportExcel: function() {
		var a = this.grid.getStore();
		var url = a.proxy.url;
		var params = a.proxy.extraParams;
		if (params) {
			var appFile = a.proxy.extraParams.appFile;
			var methodName = a.proxy.extraParams.methodName;
			url = url + "?appFile=" + appFile + "&methodName=" +
				methodName + "&";
		} else {
			url = url + "?";
		}
		var b = this.grid;
		var q = '';
		for (i = 0; i < b.columns.length; i++) {
			var c = b.columns[i];
			if ((s = c.dataIndex.indexOf('$')) > -1 && !c.hidden) {
				q += c.dataIndex.substring(0, s) + ',' + c.text +
					';';
			}
		}
		// q=toUTF8(q);
		if (a.lastOptions.params) {
			window.open(url + 'toExcel=true&otherParam=' +
				encodeURIComponent(a.lastOptions.params.otherParam) +
				'&title=' + encodeURIComponent(
					encodeURIComponent(q)), 'exportExcel', '');
		} else {
			if (a.lastOptions.filters) {
				var filters = a.getFilters()
					.items;
				vfilters = '[';
				for (i = 0; i < filters.length; i++) {
					if (i > 0) {
						vfilters += ',';
					}
					vfilters += '{"property":"' + filters[0]._property +
						'","operator":"' + filters[0]._operator +
						'","value":"' + encodeURIComponent(filters[
							0]._value) + '"}';
				}
				vfilters += ']';
				window.open(url + 'toExcel=true&filter=' +
					encodeURIComponent(vfilters) + '&title=' +
					encodeURIComponent(encodeURIComponent(q)),
					'exportExcel', '');
			} else {
				window.open(url + 'toExcel=true&title=' +
					encodeURIComponent(encodeURIComponent(q)),
					'exportExcel', '');
			}
		}
	},
	exportModuleExcel: function() {
		var a = this.grid.getStore();
		var url = a.proxy.url;
		var params = a.proxy.extraParams;
		if (params) {
			var appFile = a.proxy.extraParams.appFile;
			var methodName = a.proxy.extraParams.methodName;
			url = url + "?appFile=" + appFile + "&methodName=" +
				methodName + "&";
		} else {
			url = url + "?";
		}
		var b = this.grid;
		var t = b.tables;
		var q = '';
		for (i = 0; i < b.columns.length; i++) {
			var c = b.columns[i];
			if ((s = c.dataIndex.indexOf('$')) > -1 && !c.hidden) {
				q += "'" + c.dataIndex.substring(0, s) + "',";
			}
		}
		// q=toUTF8(q);
		if (q != '') {
			if (q.indexOf("sysid") > -1 || q.indexOf("SYSID") > -1) {
				q = q.substring(0, q.length - 1);
			} else {
				q += "'sysid'";
			}
		}
		if (a.lastOptions.params) {
			window.open(url + '&toExcelModule=true&otherParam=' +
				encodeURIComponent(a.lastOptions.params.otherParam) +
				'&title=' + encodeURIComponent(
					encodeURIComponent(q)) + '&t=' + this.stringToHex(
					t), 'exportExcel', '');
		} else {
			if (a.lastOptions.filters) {
				var filters = a.getFilters()
					.items;
				vfilters = '[';
				for (i = 0; i < filters.length; i++) {
					if (i > 0) {
						vfilters += ',';
					}
					vfilters += '{"property":"' + filters[0]._property +
						'","operator":"' + filters[0]._operator +
						'","value":"' + encodeURIComponent(filters[
							0]._value) + '"}';
				}
				vfilters += ']';
				window.open(url + '&toExcelModule=true&filter=' +
					encodeURIComponent(vfilters) + '&title=' +
					encodeURIComponent(encodeURIComponent(q)) +
					'&t=' + this.stringToHex(t), 'exportExcel',
					'');
			} else {
				window.open(url + '&toExcelModule=true&title=' +
					encodeURIComponent(encodeURIComponent(q)) +
					'&t=' + this.stringToHex(t), 'exportExcel',
					'');
			}
		}
	}
});
Ext.define("Ext.toolbar.PagingCustom", {
	extend: "Ext.toolbar.Toolbar",
	xtype: "pagingtoolbarcustom",
	alternateClassName: "Ext.PagingToolbarCustom",
	requires: ["Ext.toolbar.TextItem", "Ext.form.field.Number"],
	mixins: ["Ext.util.StoreHolder"],
	displayInfo: false,
	prependButtons: false,
	displayMsg: "Displaying {0} - {1} of {2}",
	emptyMsg: "No data to display",
	beforePageText: "Page",
	afterPageText: "of {0}",
	firstText: "第一页",
	prevText: "上一页",
	nextText: "下一页",
	lastText: "最后一页",
	refreshText: "刷新",
	inputItemWidth: 40,
	getPagingItems: function() {
		var me = this,
			inputListeners = {
				scope: me,
				blur: me.onPagingBlur
			};
		inputListeners[Ext.supports.SpecialKeyDownRepeat ?
			"keydown" : "keypress"] = me.onPagingKeyDown;
		return [{
				itemId: "first",
				tooltip: me.firstText,
				overflowText: me.firstText,
				iconCls: Ext.baseCSSPrefix + "tbar-page-first",
				disabled: true,
				handler: me.moveFirst,
				scope: me
			},
			{
				itemId: "prev",
				tooltip: me.prevText,
				overflowText: me.prevText,
				iconCls: Ext.baseCSSPrefix + "tbar-page-prev",
				disabled: true,
				handler: me.movePrevious,
				scope: me
			},
			"-", me.beforePageText, {
				xtype: "numberfield",
				itemId: "inputItem",
				name: "inputItem",
				cls: Ext.baseCSSPrefix + "tbar-page-number",
				allowDecimals: false,
				minValue: 1,
				hideTrigger: true,
				enableKeyEvents: true,
				keyNavEnabled: false,
				selectOnFocus: true,
				submitValue: false,
				isFormField: false,
				width: me.inputItemWidth,
				margin: "-1 2 3 2",
				listeners: inputListeners
			},
			{
				xtype: "tbtext",
				itemId: "afterTextItem",
				text: Ext.String.format(me.afterPageText, 1)
			},
			"-", {
				itemId: "next",
				tooltip: me.nextText,
				overflowText: me.nextText,
				iconCls: Ext.baseCSSPrefix + "tbar-page-next",
				disabled: true,
				handler: me.moveNext,
				scope: me
			},
			{
				itemId: "last",
				tooltip: me.lastText,
				overflowText: me.lastText,
				iconCls: Ext.baseCSSPrefix + "tbar-page-last",
				disabled: true,
				handler: me.moveLast,
				scope: me
			},
			"-", {
				itemId: "refresh",
				tooltip: me.refreshText,
				overflowText: me.refreshText,
				iconCls: Ext.baseCSSPrefix + "tbar-loading",
				disabled: me.store.isLoading(),
				handler: me.doRefresh,
				scope: me
			}
		]
	},
	initComponent: function() {
		var me = this,
			userItems = me.items || me.buttons || [],
			pagingItems;
		me.bindStore(me.store || "ext-empty-store", true);
		pagingItems = me.getPagingItems();
		if (me.prependButtons) {
			me.items = userItems.concat(pagingItems)
		} else {
			me.items = pagingItems.concat(userItems)
		}
		delete me.buttons;
		if (me.displayInfo) {
			me.items.push("->");
			me.items.push({
				xtype: "tbtext",
				itemId: "displayItem"
			})
		}
		me.callParent()
	},
	beforeRender: function() {
		var me = this;
		me.callParent(arguments);
		if (!me.store.isLoading()) {
			me.calledFromRender = true;
			me.onLoad();
			delete me.calledFromRender
		}
	},
	updateInfo: function() {
		var me = this,
			displayItem = me.child("#displayItem"),
			store = me.store,
			pageData = me.getPageData(),
			count,
			msg;
		if (displayItem) {
			count = store.getCount();
			if (count === 0) {
				msg = me.emptyMsg
			} else {
				msg = Ext.String.format(me.displayMsg, pageData.fromRecord,
					pageData.toRecord, pageData.total)
			}
			displayItem.setText(msg)
		}
	},
	onLoad: function() {
		var me = this,
			pageData, currPage, pageCount, afterText, count,
			isEmpty, item;
		var me = this,
			store = me.store;
		count = me.store.getCount();
		isEmpty = count === 0;
		if (!isEmpty) {
			pageData = me.getPageData();
			currPage = pageData.currentPage;
			pageCount = pageData.pageCount;
			if (currPage > pageCount) {
				if (store.lastOptions.params) {
					var s = store.lastOptions.params;
					s.start = pageCount * store.getPageSize();
					s.page = pageCount;
					s.limit = store.getPageSize();
					store.loadPage(s.page, {
						params: {
							page: s.page,
							start: s.start,
							limit: s.limit,
							otherParam: s.otherParam
						}
					})
				} else {
					me.store.loadPage(pageCount)
				}
				return
			}
			afterText = Ext.String.format(me.afterPageText, isNaN(
				pageCount) ? 1 : pageCount)
		} else {
			currPage = 0;
			pageCount = 0;
			afterText = Ext.String.format(me.afterPageText, 0)
		}
		Ext.suspendLayouts();
		item = me.child("#afterTextItem");
		if (item) {
			item.setText(afterText)
		}
		item = me.getInputItem();
		if (item) {
			item.setDisabled(isEmpty)
				.setValue(currPage)
		}
		me.setChildDisabled("#first", currPage === 1 || isEmpty);
		me.setChildDisabled("#prev", currPage === 1 || isEmpty);
		me.setChildDisabled("#next", currPage === pageCount ||
			isEmpty);
		me.setChildDisabled("#last", currPage === pageCount ||
			isEmpty);
		me.setChildDisabled("#refresh", false);
		me.updateInfo();
		Ext.resumeLayouts(true);
		if (!me.calledFromRender) {
			me.fireEvent("change", me, pageData)
		}
	},
	setChildDisabled: function(selector, disabled) {
		var item = this.child(selector);
		if (item) {
			item.setDisabled(disabled)
		}
	},
	getPageData: function() {
		var store = this.store,
			totalCount = store.getTotalCount();
		return {
			total: totalCount,
			currentPage: store.currentPage,
			pageCount: Math.ceil(totalCount / store.pageSize),
			fromRecord: ((store.currentPage - 1) * store.pageSize) +
				1,
			toRecord: Math.min(store.currentPage * store.pageSize,
				totalCount)
		}
	},
	onLoadError: function() {
		this.setChildDisabled("#refresh", false)
	},
	getInputItem: function() {
		return this.child("#inputItem")
	},
	readPageFromInput: function(pageData) {
		var inputItem = this.getInputItem(),
			pageNum = false,
			v;
		if (inputItem) {
			v = inputItem.getValue();
			pageNum = parseInt(v, 10);
			if (!v || isNaN(pageNum)) {
				inputItem.setValue(pageData.currentPage);
				return false
			}
		}
		return pageNum
	},
	onPagingBlur: function(e) {
		var inputItem = this.getInputItem(),
			curPage;
		if (inputItem) {
			curPage = this.getPageData()
				.currentPage;
			inputItem.setValue(curPage)
		}
	},
	onPagingKeyDown: function(field, e) {
		this.processKeyEvent(field, e)
	},
	processKeyEvent: function(field, e) {
		var me = this,
			k = e.getKey(),
			pageData = me.getPageData(),
			increment = e.shiftKey ? 10 : 1,
			pageNum;
		if (k == e.RETURN) {
			e.stopEvent();
			pageNum = me.readPageFromInput(pageData);
			if (pageNum !== false) {
				pageNum = Math.min(Math.max(1, pageNum), pageData.pageCount);
				if (pageNum !== pageData.currentPage && me.fireEvent(
					"beforechange", me, pageNum) !== false) {
					me.store.loadPage(pageNum)
				}
			}
		} else {
			if (k == e.HOME || k == e.END) {
				e.stopEvent();
				pageNum = k == e.HOME ? 1 : pageData.pageCount;
				field.setValue(pageNum)
			} else {
				if (k == e.UP || k == e.PAGE_UP || k == e.DOWN || k ==
					e.PAGE_DOWN) {
					e.stopEvent();
					pageNum = me.readPageFromInput(pageData);
					if (pageNum) {
						if (k == e.DOWN || k == e.PAGE_DOWN) {
							increment *= -1
						}
						pageNum += increment;
						if (pageNum >= 1 && pageNum <= pageData.pageCount) {
							field.setValue(pageNum)
						}
					}
				}
			}
		}
	},
	beforeLoad: function() {
		this.setChildDisabled("#refresh", true)
	},
	moveFirst: function() {
		var me = this,
			store = me.store;
		if (this.fireEvent("beforechange", this, 1) !== false) {
			if (store.lastOptions.params) {
				var s = store.lastOptions.params;
				s.start = 0;
				s.page = 1;
				s.limit = store.getPageSize();
				store.loadPage(s.page, {
					params: {
						page: s.page,
						start: s.start,
						limit: s.limit,
						otherParam: s.otherParam
					}
				})
			} else {
				this.store.loadPage(1)
			}
			return true
		}
		return false
	},
	movePrevious: function() {
		var me = this,
			store = me.store,
			prev = store.currentPage - 1;
		if (prev > 0) {
			if (me.fireEvent("beforechange", me, prev) !== false) {
				if (store.lastOptions.params) {
					var s = store.lastOptions.params;
					s.start = (store.currentPage - 1) * store.getPageSize();
					s.page = store.currentPage - 1;
					s.limit = store.getPageSize();
					if (prev == 1) {
						s.start = 0;
					}
					store.loadPage(s.page, {
						params: {
							page: s.page,
							start: s.start,
							limit: s.limit,
							otherParam: s.otherParam
						}
					})
				} else {
					store.previousPage()
				}
				return true
			}
		}
		return false
	},
	moveNext: function() {
		var me = this,
			store = me.store,
			total = me.getPageData()
			.pageCount,
			next = store.currentPage + 1;
		if (next <= total) {
			if (me.fireEvent("beforechange", me, next) !== false) {
				if (store.lastOptions.params) {
					var s = store.lastOptions.params;
					s.start = store.currentPage * store.getPageSize();
					s.page = store.currentPage + 1;
					s.limit = store.getPageSize();
					store.loadPage(s.page, {
						params: {
							page: s.page,
							start: s.start,
							limit: s.limit,
							otherParam: s.otherParam
						}
					})
				} else {
					store.nextPage()
				}
				return true
			}
		}
		return false
	},
	moveLast: function() {
		var me = this,
			store = me.store,
			last = me.getPageData()
			.pageCount;
		if (me.fireEvent("beforechange", me, last) !== false) {
			if (store.lastOptions.params) {
				var s = store.lastOptions.params;
				s.start = (last - 1) * store.getPageSize();
				s.limit = store.getPageSize();
				s.page = last;
				store.loadPage(s.page, {
					params: {
						page: s.page,
						start: s.start,
						limit: s.limit,
						otherParam: s.otherParam
					}
				})
			} else {
				me.store.loadPage(last)
			}
			return true
		}
		return false
	},
	// 刷新
	doRefresh: function() {
		var me = this,
			store = me.store,
			current = store.currentPage;
		if (me.fireEvent("beforechange", me, current) !== false) {
			if (store.lastOptions.params) {
				var s = store.lastOptions.params;
				// 2018-3-1 修改：查詢后，刷新錯誤
				// s.start = current * store.getPageSize();
				if ((current * store.getPageSize()) == store.getPageSize()) {
					s.start = current * store.getPageSize() - store
						.getPageSize();
				} else {
					s.start = current * store.getPageSize();
				}
				s.limit = store.getPageSize();
				s.page = current;
				store.loadPage(s.page, {
					params: {
						page: s.page,
						start: s.start,
						limit: s.limit,
						otherParam: s.otherParam
					}
				})
			} else {
				store.loadPage(current)
			}
			return true
		}
		return false
	},
	getStoreListeners: function() {
		return {
			beforeload: this.beforeLoad,
			load: this.onLoad,
			exception: this.onLoadError
		}
	},
	onDestroy: function() {
		this.bindStore(null);
		this.callParent()
	}
});
Ext.define("Ext.exporter.File", {
		singleton: true
	},
	function(File) {
		var navigator = window.navigator,
			saveAs = window.saveAs || (typeof navigator !== "undefined" &&
				navigator.msSaveOrOpenBlob && navigator.msSaveOrOpenBlob.bind(
					navigator)) || (function(view) {
				if (typeof navigator !== "undefined" && /MSIE [1-9]\./.test(
					navigator.userAgent)) {
					return
				}
				var doc = view.document,
					get_URL = function() {
						return view.URL || view.webkitURL || view
					},
					save_link = doc.createElementNS(
						"http://www.w3.org/1999/xhtml", "a"),
					can_use_save_link = !view.externalHost &&
					"download" in save_link,
					click = function(node) {
						var event = doc.createEvent("MouseEvents");
						event.initMouseEvent("click", true, false, view,
							0, 0, 0, 0, 0, false, false, false,
							false, 0, null);
						node.dispatchEvent(event)
					},
					webkit_req_fs = view.webkitRequestFileSystem,
					req_fs = view.requestFileSystem || webkit_req_fs ||
					view.mozRequestFileSystem,
					throw_outside = function(ex) {
						(view.setImmediate || view.setTimeout)(function() {
								throw ex
							},
							0)
					},
					force_saveable_type = "application/octet-stream",
					fs_min_size = 0,
					deletion_queue = [],
					process_deletion_queue = function() {
						var i = deletion_queue.length;
						while (i--) {
							var file = deletion_queue[i];
							if (typeof file === "string") {
								get_URL()
									.revokeObjectURL(file)
							} else {
								file.remove()
							}
						}
						deletion_queue.length = 0
					},
					dispatch = function(filesaver, event_types, event) {
						event_types = [].concat(event_types);
						var i = event_types.length;
						while (i--) {
							var listener = filesaver["on" + event_types[
								i]];
							if (typeof listener === "function") {
								try {
									listener.call(filesaver, event ||
										filesaver)
								} catch (ex) {
									throw_outside(ex)
								}
							}
						}
					},
					FileSaver = function(blob, name) {
						var filesaver = this,
							type = blob.type,
							blob_changed = false,
							object_url, target_view, get_object_url =
							function() {
								var object_url = get_URL()
									.createObjectURL(blob);
								deletion_queue.push(object_url);
								return object_url
							},
							dispatch_all = function() {
								dispatch(filesaver,
									"writestart progress write writeend"
									.split(" "))
							},
							fs_error = function() {
								if (blob_changed || !object_url) {
									object_url = get_object_url(blob)
								}
								if (target_view) {
									target_view.location.href =
										object_url
								} else {
									window.open(object_url, "_blank")
								}
								filesaver.readyState = filesaver.DONE;
								dispatch_all()
							},
							abortable = function(func) {
								return function() {
									if (filesaver.readyState !==
										filesaver.DONE) {
										return func.apply(this,
											arguments)
									}
								}
							},
							create_if_not_found = {
								create: true,
								exclusive: false
							},
							slice;
						filesaver.readyState = filesaver.INIT;
						if (!name) {
							name = "download"
						}
						if (can_use_save_link) {
							object_url = get_object_url(blob);
							save_link.href = object_url;
							save_link.download = name;
							click(save_link);
							filesaver.readyState = filesaver.DONE;
							dispatch_all();
							return
						}
						if (view.chrome && type && type !==
							force_saveable_type) {
							slice = blob.slice || blob.webkitSlice;
							blob = slice.call(blob, 0, blob.size,
								force_saveable_type);
							blob_changed = true
						}
						if (webkit_req_fs && name !== "download") {
							name += ".download"
						}
						if (type === force_saveable_type ||
							webkit_req_fs) {
							target_view = view
						}
						if (!req_fs) {
							fs_error();
							return
						}
						fs_min_size += blob.size;
						req_fs(view.TEMPORARY, fs_min_size, abortable(
							function(fs) {
								fs.root.getDirectory("saved",
									create_if_not_found,
									abortable(function(dir) {
										var save =
											function() {
												dir.getFile(
													name,
													create_if_not_found,
													abortable(
														function(
															file
														) {
															file
																.createWriter(
																	abortable(
																		function(
																			writer
																		) {
																			writer
																				.onwriteend =
																				function(
																					event
																				) {
																					target_view
																						.location
																						.href =
																						file
																						.toURL();
																					deletion_queue
																						.push(
																							file
																						);
																					filesaver
																						.readyState =
																						filesaver
																						.DONE;
																					dispatch
																						(
																							filesaver,
																							"writeend",
																							event
																						)
																				};
																			writer
																				.onerror =
																				function() {
																					var
																						error =
																						writer
																						.error;
																					if (
																						error
																						.code !==
																						error
																						.ABORT_ERR
																					) {
																						fs_error
																							()
																					}
																				};
																			"writestart progress write abort"
																			.split
																				(
																					" "
																				)
																				.forEach(
																					function(
																						event
																					) {
																						writer
																							[
																								"on" +
																								event
																							] =
																							filesaver[
																								"on" +
																								event
																							]
																					}
																				);
																			writer
																				.write(
																					blob
																				);
																			filesaver
																				.abort =
																				function() {
																					writer
																						.abort();
																					filesaver
																						.readyState =
																						filesaver
																						.DONE
																				};
																			filesaver
																				.readyState =
																				filesaver
																				.WRITING
																		}
																	),
																	fs_error
																)
														}
													),
													fs_error
												)
											};
										dir.getFile(
											name, {
												create: false
											},
											abortable(
												function(
													file
												) {
													file
														.remove();
													save
														()
												}),
											abortable(
												function(
													ex
												) {
													if (
														ex
														.code ===
														ex
														.NOT_FOUND_ERR
													) {
														save
															()
													} else {
														fs_error
															()
													}
												}))
									}), fs_error)
							}), fs_error)
					},
					FS_proto = FileSaver.prototype,
					saveAs = function(blob, name) {
						return new FileSaver(blob, name)
					};
				FS_proto.abort = function() {
					var filesaver = this;
					filesaver.readyState = filesaver.DONE;
					dispatch(filesaver, "abort")
				};
				FS_proto.readyState = FS_proto.INIT = 0;
				FS_proto.WRITING = 1;
				FS_proto.DONE = 2;
				FS_proto.error = FS_proto.onwritestart = FS_proto.onprogress =
					FS_proto.onwrite = FS_proto.onabort = FS_proto.onerror =
					FS_proto.onwriteend = null;
				view.addEventListener("unload", process_deletion_queue,
					false);
				saveAs.unload = function() {
					process_deletion_queue();
					view.removeEventListener("unload",
						process_deletion_queue, false)
				};
				return saveAs
			}(typeof self !== "undefined" && self || typeof window !==
				"undefined" && window || this.content));
		if (typeof module !== "undefined" && module !== null) {
			module.exports = saveAs
		} else {
			if ((typeof define !== "undefined" && define !== null) && (
				define.amd != null)) {
				define([],
					function() {
						return saveAs
					})
			}
		}
		var saveTextAs = window.saveTextAs || (function(textContent,
			fileName, charset) {
			fileName = fileName || "download.txt";
			charset = charset || "utf-8";
			textContent = (textContent || "")
				.replace(/\r?\n/g, "\r\n");
			if (saveAs && Blob) {
				var blob = new Blob([textContent], {
					type: "text/plain;charset=" + charset
				});
				saveAs(blob, fileName);
				return true
			} else {
				var saveTxtWindow = window.frames.saveTxtWindow;
				if (!saveTxtWindow) {
					saveTxtWindow = document.createElement("iframe");
					saveTxtWindow.id = "saveTxtWindow";
					saveTxtWindow.style.display = "none";
					document.body.insertBefore(saveTxtWindow, null);
					saveTxtWindow = window.frames.saveTxtWindow;
					if (!saveTxtWindow) {
						saveTxtWindow = window.open("", "_temp",
							"width=100,height=100");
						if (!saveTxtWindow) {
							window.alert(
								"Sorry, download file could not be created."
							);
							return false
						}
					}
				}
				var doc = saveTxtWindow.document;
				doc.open("text/html", "replace");
				doc.charset = charset;
				if (Ext.String.endsWith(fileName, ".htm", true) ||
					Ext.String.endsWith(fileName, ".html", true)) {
					doc.close();
					doc.body.innerHTML = "\r\n" + textContent +
						"\r\n"
				} else {
					if (!Ext.String.endsWith(fileName, ".txt", true)) {
						fileName += ".txt"
					}
					doc.write(textContent);
					doc.close()
				}
				var retValue = doc.execCommand("SaveAs", null,
					fileName);
				saveTxtWindow.close();
				return retValue
			}
		});
		File.saveAs = saveTextAs
	});
Ext.define("Ext.exporter.Base", {
	mixins: ["Ext.mixin.Factoryable"],
	alias: "exporter.base",
	requires: ["Ext.exporter.File"],
	config: {
		data: null,
		showSummary: true,
		title: "",
		author: "Sencha",
		fileName: "export.txt",
		charset: "UTF-8"
	},
	constructor: function(config) {
		this.initConfig(config || {});
		return this.callParent(arguments)
	},
	getContent: Ext.identityFn,
	saveAs: function() {
		Ext.exporter.File.saveAs(this.getContent(), this.getFileName(),
			this.getCharset())
	},
	getColumnCount: function(columns) {
		var s = 0;
		if (!columns) {
			return s
		}
		for (var i = 0; i < columns.length; i++) {
			if (!columns[i].columns) {
				s += 1
			} else {
				s += this.getColumnCount(columns[i].columns)
			}
		}
		return s
	},
	applyData: function(data) {
		if (Ext.isObject(data)) {
			data.columns = data.columns || [];
			this.fixColumns(data.columns, this.getColDepth(data.columns,
				-1))
		} else {
			data = {}
		}
		data.groups = data.groups || [];
		return data
	},
	getColDepth: function(columns, level) {
		var m = 0;
		if (!columns) {
			return level
		}
		for (var i = 0; i < columns.length; i++) {
			columns[i].level = level + 1;
			m = Math.max(m, this.getColDepth(columns[i].columns,
				level + 1))
		}
		return m
	},
	fixColumns: function(columns, depth) {
		var col;
		if (!columns) {
			return
		}
		for (var i = 0; i < columns.length; i++) {
			col = columns[i];
			if (!col.columns && depth > col.level) {
				col.columns = [];
				col.columns.push({
					text: "",
					level: col.level + 1
				})
			}
			this.fixColumns(col.columns, depth)
		}
	}
});
Ext.define("Ext.exporter.file.Base", {
	requires: ["Ext.XTemplate", "Ext.util.Collection"],
	config: {
		id: ""
	},
	tpl: null,
	constructor: function(config) {
		var me = this;
		me.initConfig(config || {});
		return me.callParent(arguments)
	},
	applyId: function(data, id) {
		if (Ext.isEmpty(id)) {
			id = Ext.id()
		}
		if (!Ext.isEmpty(data)) {
			id = data
		}
		return id
	},
	checkCollection: function(data, dataCollection, className) {
		if (!dataCollection) {
			dataCollection = this.constructCollection(className)
		}
		if (data) {
			dataCollection.add(data)
		}
		return dataCollection
	},
	constructCollection: function(className) {
		return new Ext.util.Collection({
			decoder: this.getCollectionDecoder(className)
		})
	},
	getCollectionDecoder: function(className) {
		return function(config) {
			return Ext.create(className, config || {})
		}
	},
	render: function() {
		return this.tpl ? Ext.XTemplate.getTpl(this, "tpl")
			.apply(this.getRenderData()) : ""
	},
	getRenderData: function() {
		return this.getConfig()
	}
});
Ext.define("Ext.exporter.file.excel.Cell", {
	extend: "Ext.exporter.file.Base",
	config: {
		dataType: "String",
		formula: null,
		index: null,
		styleId: null,
		mergeAcross: null,
		mergeDown: null,
		value: ""
	},
	tpl: ["               <Cell",
		'<tpl if="this.exists(index)"> ss:Index="{index}"</tpl>',
		'<tpl if="this.exists(styleId)"> ss:StyleID="{styleId}"</tpl>',
		'<tpl if="this.exists(mergeAcross)"> ss:MergeAcross="{mergeAcross}"</tpl>',
		'<tpl if="this.exists(mergeDown)"> ss:MergeDown="{mergeDown}"</tpl>',
		'<tpl if="this.exists(formula)"> ss:Formula="{formula}"</tpl>',
		">\n",
		'                   <Data ss:Type="{dataType}">{[this.formatValue(values.value)]}</Data>\n',
		"               </Cell>\n", {
			exists: function(value) {
				return !Ext.isEmpty(value)
			},
			formatValue: function(value) {
				var format = Ext.util.Format;
				return (value instanceof Date ? Ext.Date.format(
					value, "Y-m-d\\TH:i:s.u") : format.htmlEncode(
					format.htmlDecode(value)))
			}
		}
	],
	render: function() {
		var me = this,
			v = me.getValue();
		if (v instanceof Date) {
			me.setDataType("DateTime")
		} else {
			if (Ext.isNumeric(v)) {
				me.setDataType("Number")
			} else {
				me.setDataType("String")
			}
		}
		return me.callParent(arguments)
	}
});
Ext.define("Ext.exporter.file.excel.Column", {
	extend: "Ext.exporter.file.Base",
	config: {
		autoFitWidth: false,
		caption: null,
		hidden: null,
		index: null,
		span: null,
		styleId: null,
		width: null
	},
	tpl: ["<Column",
		'<tpl if="this.exists(index)"> ss:Index="{index}"</tpl>',
		'<tpl if="this.exists(caption)"> c:Caption="{caption}"</tpl>',
		'<tpl if="this.exists(styleId)"> ss:StyleID="{styleId}"</tpl>',
		'<tpl if="this.exists(hidden)"> ss:Hidden="{hidden}"</tpl>',
		'<tpl if="this.exists(span)"> ss:Span="{span}"</tpl>',
		'<tpl if="this.exists(width)"> ss:Width="{width}"</tpl>',
		'<tpl if="this.exists(autoFitWidth)"> ss:AutoFitWidth="{autoFitWidth:this.toNumber}"</tpl>',
		"/>\n", {
			exists: function(value) {
				return !Ext.isEmpty(value)
			},
			toNumber: function(value) {
				return Number(Boolean(value))
			}
		}
	]
});
Ext.define("Ext.exporter.file.excel.Row", {
	extend: "Ext.exporter.file.Base",
	config: {
		autoFitHeight: false,
		caption: null,
		cells: [],
		height: null,
		index: null,
		span: null,
		styleId: null
	},
	tpl: ["           <Row",
		'<tpl if="this.exists(index)"> ss:Index="{index}"</tpl>',
		'<tpl if="this.exists(caption)"> c:Caption="{caption}"</tpl>',
		'<tpl if="this.exists(autoFitHeight)"> ss:AutoFitHeight="{autoFitHeight:this.toNumber}"</tpl>',
		'<tpl if="this.exists(span)"> ss:Span="{span}"</tpl>',
		'<tpl if="this.exists(height)"> ss:Height="{height}"</tpl>',
		'<tpl if="this.exists(styleId)"> ss:StyleID="{styleId}"</tpl>',
		">\n", '<tpl for="cells">{[values.render()]}</tpl>',
		"           </Row>\n", {
			exists: function(value) {
				return !Ext.isEmpty(value)
			},
			toNumber: function(value) {
				return Number(Boolean(value))
			}
		}
	],
	destroy: function() {
		this.getCells()
			.destroy();
		return this.callParent(arguments)
	},
	applyCells: function(data, dataCollection) {
		return this.checkCollection(data, dataCollection,
			"Ext.exporter.file.excel.Cell")
	},
	addCell: function(config) {
		return this.getCells()
			.add(config || {})
	},
	getCell: function(id) {
		return this.getCells()
			.get(id)
	},
	getRenderData: function() {
		return Ext.apply(this.callParent(arguments), {
			cells: this.getCells()
				.getRange()
		})
	}
});
Ext.define("Ext.exporter.file.excel.Style", {
	extend: "Ext.exporter.file.Base",
	config: {
		parentId: null,
		name: null,
		protection: null,
		alignment: null,
		font: null,
		interior: null,
		format: null,
		borders: null
	},
	statics: {
		checks: {
			alignment: {
				Horizontal: ["Automatic", "Left", "Center", "Right",
					"Fill", "Justify", "CenterAcrossSelection",
					"Distributed", "JustifyDistributed"
				],
				Indent: null,
				ReadingOrder: ["LeftToRight", "RightToLeft", "Context"],
				Rotate: null,
				ShrinkToFit: [true, false],
				Vertical: ["Automatic", "Top", "Bottom", "Center",
					"Justify", "Distributed", "JustifyDistributed"
				],
				VerticalText: [true, false],
				WrapText: [true, false]
			},
			font: {
				Bold: [true, false],
				CharSet: null,
				Color: null,
				FontName: null,
				Family: ["Automatic", "Decorative", "Modern", "Roman",
					"Script", "Swiss"
				],
				Italic: [true, false],
				Outline: [true, false],
				Shadow: [true, false],
				Size: null,
				StrikeThrough: [true, false],
				Underline: ["None", "Single", "Double",
					"SingleAccounting", "DoubleAccounting"
				],
				VerticalAlign: ["None", "Subscript", "Superscript"]
			},
			border: {
				Position: ["Left", "Top", "Right", "Bottom",
					"DiagonalLeft", "DiagonalRight"
				],
				Color: null,
				LineStyle: ["None", "Continuous", "Dash", "Dot",
					"DashDot", "DashDotDot", "SlantDashDot",
					"Double"
				],
				Weight: [0, 1, 2, 3]
			},
			interior: {
				Color: null,
				Pattern: ["None", "Solid", "Gray75", "Gray50", "Gray25",
					"Gray125", "Gray0625", "HorzStripe",
					"VertStripe", "ReverseDiagStripe", "DiagStripe",
					"DiagCross", "ThickDiagCross", "ThinHorzStripe",
					"ThinVertStripe", "ThinReverseDiagStripe",
					"ThinDiagStripe", "ThinHorzCross",
					"ThinDiagCross"
				],
				PatternColor: null
			},
			protection: {
				Protected: [true, false],
				HideFormula: [true, false]
			}
		}
	},
	tpl: ['       <Style ss:ID="{id}"',
		'<tpl if="this.exists(parentId)"> ss:Parent="{parentId}"</tpl>',
		'<tpl if="this.exists(name)"> ss:Name="{name}"</tpl>',
		">\n",
		'<tpl if="this.exists(alignment)">           <Alignment{[this.getAttributes(values.alignment, "alignment")]}/>\n</tpl>',
		'<tpl if="this.exists(borders)">', "           <Borders>\n",
		'<tpl for="borders">               <Border{[this.getAttributes(values, "border")]}/>\n</tpl>',
		"           </Borders>\n", "</tpl>",
		'<tpl if="this.exists(font)">           <Font{[this.getAttributes(values.font, "font")]}/>\n</tpl>',
		'<tpl if="this.exists(interior)">           <Interior{[this.getAttributes(values.interior, "interior")]}/>\n</tpl>',
		'<tpl if="this.exists(format)">           <NumberFormat ss:Format="{format}"/>\n</tpl>',
		'<tpl if="this.exists(protection)">           <Protection{[this.getAttributes(values.protection, "protection")]}/>\n</tpl>',
		"       </Style>\n", {
			exists: function(value) {
				return !Ext.isEmpty(value)
			},
			getAttributes: function(obj, checkName) {
				var template = ' ss:{0}="{1}"',
					checks = this.owner.self.checks,
					keys = Ext.Object.getKeys(obj || {}),
					len = keys.length,
					s = "",
					i,
					arr,
					key;
				if (checks[checkName]) {
					for (i = 0; i < len; i++) {
						key = keys[i];
						arr = checks[checkName][key];
						if (Ext.isEmpty(arr) || Ext.Array.indexOf(
							arr, obj[key]) >= 0) {
							s += Ext.String.format(template,
								key, Ext.isBoolean(obj[key]) ?
								Number(obj[key]) : obj[key]
							)
						} else {
							Ext.raise(Ext.String.format(
								"Invalid key (%0) or value (%1) provided for Style!",
								key, obj[key]))
						}
					}
				}
				return s
			}
		}
	]
});
Ext.define("Ext.exporter.file.excel.Table", {
	extend: "Ext.exporter.file.Base",
	config: {
		expandedColumnCount: null,
		expandedRowCount: null,
		fullColumns: 1,
		fullRows: 1,
		defaultColumnWidth: 48,
		defaultRowHeight: 12.75,
		styleId: null,
		leftCell: 1,
		topCell: 1,
		columns: [],
		rows: []
	},
	tpl: [
		'       <Table x:FullColumns="{fullColumns}" x:FullRows="{fullRows}"',
		'<tpl if="this.exists(expandedRowCount)"> ss:ExpandedRowCount="{expandedRowCount}"</tpl>',
		'<tpl if="this.exists(expandedColumnCount)"> ss:ExpandedColumnCount="{expandedColumnCount}"</tpl>',
		'<tpl if="this.exists(defaultRowHeight)"> ss:DefaultRowHeight="{defaultRowHeight}"</tpl>',
		'<tpl if="this.exists(defaultColumnWidth)"> ss:DefaultColumnWidth="{defaultColumnWidth}"</tpl>',
		'<tpl if="this.exists(leftCell)"> ss:LeftCell="{leftCell}"</tpl>',
		'<tpl if="this.exists(topCell)"> ss:TopCell="{topCell}"</tpl>',
		'<tpl if="this.exists(styleId)"> ss:StyleID="{styleId}"</tpl>',
		">\n", '<tpl for="columns">{[values.render()]}</tpl>',
		'<tpl if="this.exists(rows)">',
		'<tpl for="rows">{[values.render()]}</tpl>',
		'<tpl else>         <Row ss:AutoFitHeight="0"/>\n</tpl>',
		"       </Table>\n", {
			exists: function(value) {
				return !Ext.isEmpty(value)
			}
		}
	],
	destroy: function() {
		this.getColumns()
			.destroy();
		this.getRows()
			.destroy();
		return this.callParent(arguments)
	},
	applyColumns: function(data, dataCollection) {
		return this.checkCollection(data, dataCollection,
			"Ext.exporter.file.excel.Column")
	},
	applyRows: function(data, dataCollection) {
		return this.checkCollection(data, dataCollection,
			"Ext.exporter.file.excel.Row")
	},
	addColumn: function(config) {
		return this.getColumns()
			.add(config || {})
	},
	getColumn: function(id) {
		return this.getColumns()
			.get(id)
	},
	addRow: function(config) {
		return this.getRows()
			.add(config || {})
	},
	getRow: function(id) {
		return this.getRows()
			.get(id)
	},
	getRenderData: function() {
		return Ext.apply(this.callParent(arguments), {
			columns: this.getColumns()
				.getRange(),
			rows: this.getRows()
				.getRange()
		})
	}
});
Ext.define("Ext.exporter.file.excel.Worksheet", {
	extend: "Ext.exporter.file.Base",
	config: {
		name: "Sheet",
		protection: null,
		rightToLeft: null,
		showGridLines: true,
		tables: []
	},
	tpl: ['   <Worksheet ss:Name="{name:htmlEncode}"',
		'<tpl if="this.exists(protection)"> ss:Protected="{protection:this.toNumber}"</tpl>',
		'<tpl if="this.exists(rightToLeft)"> ss:RightToLeft="{rightToLeft:this.toNumber}"</tpl>',
		">\n", '<tpl for="tables">{[values.render()]}</tpl>',
		'       <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">\n',
		"          <PageSetup>\n",
		'              <Layout x:CenterHorizontal="1" x:Orientation="Portrait" />\n',
		'              <Header x:Margin="0.3" />\n',
		'              <Footer x:Margin="0.3" x:Data="Page &amp;P of &amp;N" />\n',
		'              <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75" />\n',
		"          </PageSetup>\n", "          <FitToPage />\n",
		"          <Print>\n",
		"              <PrintErrors>Blank</PrintErrors>\n",
		"              <FitWidth>1</FitWidth>\n",
		"              <FitHeight>32767</FitHeight>\n",
		"              <ValidPrinterInfo />\n",
		"              <VerticalResolution>600</VerticalResolution>\n",
		"          </Print>\n", "          <Selected />\n",
		'<tpl if="!showGridLines">',
		"          <DoNotDisplayGridlines />\n", "</tpl>",
		"          <ProtectObjects>False</ProtectObjects>\n",
		"          <ProtectScenarios>False</ProtectScenarios>\n",
		"      </WorksheetOptions>\n", "   </Worksheet>\n", {
			exists: function(value) {
				return !Ext.isEmpty(value)
			},
			toNumber: function(value) {
				return Number(Boolean(value))
			}
		}
	],
	destroy: function() {
		this.getTables()
			.destroy();
		return this.callParent(arguments)
	},
	applyTables: function(data, dataCollection) {
		return this.checkCollection(data, dataCollection,
			"Ext.exporter.file.excel.Table")
	},
	addTable: function(config) {
		return this.getTables()
			.add(config || {})
	},
	getTable: function(id) {
		return this.getTables()
			.get(id)
	},
	applyName: function(value) {
		return Ext.String.ellipsis(String(value), 31)
	},
	getRenderData: function() {
		return Ext.apply(this.callParent(arguments), {
			tables: this.getTables()
				.getRange()
		})
	}
});
Ext.define("Ext.exporter.file.excel.Workbook", {
	extend: "Ext.exporter.file.Base",
	requires: ["Ext.exporter.file.excel.Worksheet",
		"Ext.exporter.file.excel.Table",
		"Ext.exporter.file.excel.Style",
		"Ext.exporter.file.excel.Row",
		"Ext.exporter.file.excel.Column",
		"Ext.exporter.file.excel.Cell"
	],
	config: {
		title: "Workbook",
		author: "Sencha",
		windowHeight: 9000,
		windowWidth: 50000,
		protectStructure: false,
		protectWindows: false,
		styles: [],
		worksheets: []
	},
	tpl: ['<?xml version="1.0" encoding="utf-8"?>\n',
		'<?mso-application progid="Excel.Sheet"?>\n', "<Workbook ",
		'xmlns="urn:schemas-microsoft-com:office:spreadsheet" ',
		'xmlns:o="urn:schemas-microsoft-com:office:office" ',
		'xmlns:x="urn:schemas-microsoft-com:office:excel" ',
		'xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" ',
		'xmlns:html="http://www.w3.org/TR/REC-html40">\n',
		'   <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">\n',
		"       <Title>{title:htmlEncode}</Title>\n",
		"       <Author>{author:htmlEncode}</Author>\n",
		"       <Created>{createdAt}</Created>\n",
		"   </DocumentProperties>\n",
		'   <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">\n',
		"       <WindowHeight>{windowHeight}</WindowHeight>\n",
		"       <WindowWidth>{windowWidth}</WindowWidth>\n",
		"       <ProtectStructure>{protectStructure}</ProtectStructure>\n",
		"       <ProtectWindows>{protectWindows}</ProtectWindows>\n",
		"   </ExcelWorkbook>\n", "   <Styles>\n",
		'<tpl for="styles">{[values.render()]}</tpl>',
		"   </Styles>\n",
		'<tpl for="worksheets">{[values.render()]}</tpl>',
		"</Workbook>"
	],
	destroy: function() {
		this.getStyles()
			.destroy();
		this.getWorksheets()
			.destroy();
		return this.callParent(arguments)
	},
	getRenderData: function() {
		return Ext.apply(this.callParent(arguments), {
			worksheets: this.getWorksheets()
				.getRange(),
			styles: this.getStyles()
				.getRange()
		})
	},
	applyStyles: function(data, dataCollection) {
		return this.checkCollection(data, dataCollection,
			"Ext.exporter.file.excel.Style")
	},
	applyWorksheets: function(data, dataCollection) {
		return this.checkCollection(data, dataCollection,
			"Ext.exporter.file.excel.Worksheet")
	},
	addStyle: function(config) {
		return this.getStyles()
			.add(config || {})
	},
	getStyle: function(id) {
		return this.getStyles()
			.get(id)
	},
	addWorksheet: function(config) {
		return this.getWorksheets()
			.add(config || {})
	},
	getWorksheet: function(id) {
		return this.getWorksheets()
			.get(id)
	}
});
Ext.define("Ext.exporter.Excel", {
	extend: "Ext.exporter.Base",
	alias: "exporter.excel",
	requires: ["Ext.exporter.file.excel.Workbook"],
	config: {
		windowHeight: 9000,
		windowWidth: 50000,
		protectStructure: false,
		protectWindows: false,
		defaultStyle: {
			alignment: {
				Vertical: "Top"
			},
			font: {
				FontName: "Calibri",
				Family: "Swiss",
				Size: 11,
				Color: "#000000"
			}
		},
		titleStyle: {
			name: "Title",
			alignment: {
				Horizontal: "Center",
				Vertical: "Center"
			},
			font: {
				FontName: "Cambria",
				Family: "Swiss",
				Size: 18,
				Color: "#1F497D"
			}
		},
		groupHeaderStyle: {
			name: "Group Header",
			borders: [{
				Position: "Bottom",
				LineStyle: "Continuous",
				Weight: 1,
				Color: "#4F81BD"
			}]
		},
		groupFooterStyle: {
			name: "Total Footer",
			borders: [{
				Position: "Top",
				LineStyle: "Continuous",
				Weight: 1,
				Color: "#4F81BD"
			}]
		},
		tableHeaderStyle: {
			name: "Heading 1",
			alignment: {
				Horizontal: "Center",
				Vertical: "Center"
			},
			borders: [{
				Position: "Bottom",
				LineStyle: "Continuous",
				Weight: 1,
				Color: "#4F81BD"
			}],
			font: {
				FontName: "Calibri",
				Family: "Swiss",
				Size: 11,
				Color: "#1F497D"
			}
		}
	},
	fileName: "export.xml",
	destroy: function() {
		Ext.destroyMembers(this, "workbook", "table");
		this.workbook = this.table = null;
		return this.callParent(arguments)
	},
	applyDefaultStyle: function(newValue) {
		return Ext.applyIf({
				id: "Default",
				name: "Normal"
			},
			newValue || {})
	},
	getContent: function() {
		var me = this,
			config = this.getConfig(),
			data = config.data,
			colMerge;
		me.workbook = Ext.create("Ext.exporter.file.excel.Workbook", {
			title: config.title,
			author: config.author,
			windowHeight: config.windowHeight,
			windowWidth: config.windowWidth,
			protectStructure: config.protectStructure,
			protectWindows: config.protectWindows
		});
		me.table = me.workbook.addWorksheet({
				name: config.title
			})
			.addTable();
		me.workbook.addStyle(config.defaultStyle);
		me.tableHeaderStyleId = me.workbook.addStyle(config.tableHeaderStyle)
			.getId();
		me.groupHeaderStyleId = me.workbook.addStyle(config.groupHeaderStyle)
			.getId();
		me.groupFooterStyleId = me.workbook.addStyle(config.groupFooterStyle)
			.getId();
		colMerge = me.getColumnCount(data.columns);
		me.addTitle(config, colMerge);
		me.buildHeader();
		me.buildRows(colMerge);
		return me.workbook.render()
	},
	addTitle: function(config, colMerge) {
		if (!Ext.isEmpty(config.title)) {
			this.table.addRow({
					autoFitHeight: 1,
					height: 22.5,
					styleId: this.workbook.addStyle(config.titleStyle)
						.getId()
				})
				.addCell({
					mergeAcross: colMerge - 1,
					value: config.title
				})
		}
	},
	buildRows: function(colMerge) {
		var me = this,
			data = me.getData(),
			groups = Ext.isDefined(data.groups) ? data.groups : Ext
			.Array.from(data),
			row;
		me.buildSummaryRows(groups, colMerge, 1);
		if (me.getShowSummary() !== false && Ext.isDefined(data.groups) &&
			data.summary && data.summary.length > 0) {
			row = me.table.addRow({
				styleId: me.groupFooterStyleId
			});
			for (var j = 0; j < data.summary.length; j++) {
				row.addCell({
					value: data.summary[j]
				})
			}
		}
	},
	buildSummaryRows: function(groups, colMerge, level) {
		var me = this,
			showSummary = me.getShowSummary(),
			g,
			row,
			styleH,
			styleF;
		if (!groups) {
			return
		}
		styleH = me.workbook.addStyle({
			parentId: me.groupHeaderStyleId,
			alignment: {
				Horizontal: "Left",
				Indent: level - 1
			}
		});
		styleF = me.workbook.addStyle({
			parentId: me.groupFooterStyleId,
			alignment: {
				Horizontal: "Left",
				Indent: level - 1
			}
		});
		for (var i = 0; i < groups.length; i++) {
			g = groups[i];
			if (showSummary !== false && !Ext.isEmpty(g.text)) {
				me.table.addRow({
						styleId: me.groupHeaderStyleId
					})
					.addCell({
						mergeAcross: colMerge - 1,
						value: g.text,
						styleId: styleH.getId()
					})
			}
			me.buildSummaryRows(g.groups, colMerge, level + 1);
			me.buildGroupRows(g.rows);
			if (showSummary !== false && g.summary && g.summary.length >
				0) {
				row = me.table.addRow({
					styleId: me.groupFooterStyleId
				});
				for (var j = 0; j < g.summary.length; j++) {
					row.addCell({
						value: g.summary[j],
						styleId: (j === 0 ? styleF.getId() :
							null)
					})
				}
			}
		}
	},
	buildGroupRows: function(lines) {
		var l, row, i, j;
		if (!lines) {
			return
		}
		for (i = 0; i < lines.length; i++) {
			row = this.table.addRow();
			l = lines[i];
			for (j = 0; j < l.length; j++) {
				row.addCell({
					value: l[j]
				})
			}
		}
	},
	buildHeader: function() {
		var me = this,
			ret = {},
			keys, row, i, j, len, lenCells;
		me.buildHeaderRows(me.getData()
			.columns, ret);
		keys = Ext.Object.getKeys(ret);
		len = keys.length;
		for (i = 0; i < len; i++) {
			row = me.table.addRow({
				height: 20.25,
				autoFitHeight: 1,
				styleId: me.tableHeaderStyleId
			});
			lenCells = ret[keys[i]].length;
			for (j = 0; j < lenCells; j++) {
				row.addCell(ret[keys[i]][j])
			}
		}
	},
	buildHeaderRows: function(columns, result) {
		var col, count, s;
		if (!columns) {
			return
		}
		for (var i = 0; i < columns.length; i++) {
			col = columns[i];
			count = this.getColumnCount(col.columns);
			result["s" + col.level] = result["s" + col.level] || [];
			s = {
				value: this.sanitizeHtml(col.text)
			};
			if (count > 1) {
				Ext.apply(s, {
					mergeAcross: count - 1
				})
			}
			result["s" + col.level].push(s);
			this.buildHeaderRows(col.columns, result)
		}
	},
	sanitizeHtml: function(value) {
		value = String(value)
			.replace("<br>", " ");
		value = value.replace("<br/>", " ");
		return value.replace(/<\/?[^>]+>/gi, "")
	}
});
Ext.define("Ext.grid.plugin.Exporter", {
	alias: ["plugin.gridexporter"],
	extend: "Ext.AbstractPlugin",
	requires: ["Ext.exporter.Excel"],
	lockableScope: "top",
	init: function(grid) {
		var me = this;
		grid.saveDocumentAs = Ext.bind(me.saveDocumentAs, me);
		grid.getDocumentData = Ext.bind(me.getDocumentData, me);
		me.grid = grid;
		return me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		me.grid.saveDocumentAs = me.grid.getDocumentData = me.grid =
			null;
		return me.callParent(arguments)
	},
	saveDocumentAs: function(config) {
		var exporter;
		if (this.disabled) {
			return
		}
		exporter = this.getExporter.apply(this, arguments);
		exporter.saveAs();
		Ext.destroy(exporter)
	},
	getDocumentData: function(config) {
		var exporter, ret;
		if (this.disabled) {
			return
		}
		exporter = this.getExporter.apply(this, arguments);
		ret = exporter.getContent();
		Ext.destroy(exporter);
		return ret
	},
	getExporter: function(config) {
		return Ext.Factory.exporter(Ext.apply({
				type: "excel",
				data: this.prepareData()
			},
			config || {}))
	},
	prepareData: function() {
		var me = this,
			grid = me.grid,
			headers, group;
		group = me.extractGroups(grid.getColumnManager()
			.getColumns());
		if (grid.lockedGrid) {
			headers = Ext.Array.merge(me.getColumnHeaders(grid.lockedGrid
				.headerCt.items), me.getColumnHeaders(grid.normalGrid
				.headerCt.items))
		} else {
			headers = me.getColumnHeaders(grid.headerCt.items)
		}
		return {
			columns: headers,
			groups: [group]
		}
	},
	getColumnHeaders: function(columns) {
		var cols = [],
			i,
			obj,
			col;
		for (i = 0; i < columns.length; i++) {
			col = columns.get(i);
			if (!col.ignoreExport) {
				obj = {
					text: col.text
				};
				if (col.isGroupHeader) {
					obj.columns = this.getColumnHeaders(col.items);
					if (obj.columns.length === 0) {
						obj = null
					}
				}
				if (obj) {
					cols.push(obj)
				}
			}
		}
		return cols
	},
	extractGroups: function(columns) {
		var store = this.grid.getStore(),
			len = store.getCount(),
			lenCols = columns.length,
			group = {
				rows: []
			},
			i,
			j,
			record,
			row,
			col,
			useRenderer,
			v;
		for (i = 0; i < len; i++) {
			record = store.getAt(i);
			row = [];
			for (j = 0; j < lenCols; j++) {
				col = columns[j];
				if (!col.ignoreExport) {
					useRenderer = !Ext.isEmpty(col.initialConfig.formatter) &&
						Ext.isEmpty(col.formatter);
					v = record.get(col.dataIndex) || "";
					row.push(useRenderer ? col.renderer(v) : v)
				}
			}
			group.rows.push(row)
		}
		return group
	}
});
Ext.define("Ext.pivot.filter.Base", {
	alternateClassName: ["Mz.aggregate.filter.Abstract"],
	alias: "pivotfilter.base",
	mixins: ["Ext.mixin.Factoryable"],
	operator: null,
	value: null,
	caseSensitive: true,
	parent: null,
	constructor: function(config) {
		Ext.apply(this, config || {});
		return this.callParent(arguments)
	},
	destroy: function() {
		this.parent = null;
		return this.callParent(arguments)
	},
	serialize: function() {
		var me = this;
		return Ext.apply({
				type: me.type,
				operator: me.operator,
				value: me.value,
				caseSensitive: me.caseSensitive
			},
			me.getSerialArgs() || {})
	},
	getSerialArgs: Ext.emptyFn,
	isMatch: function(value) {
		var me = this,
			sorter = Ext.pivot.matrix.Base.prototype.naturalSort,
			v = me.value,
			ret, retFrom, retTo, from, to;
		v = (Ext.isArray(v) ? v[0] : v) || "";
		ret = (me.caseSensitive ? sorter(value || "", v) : sorter(
			String(value || "")
			.toLowerCase(), String(v)
			.toLowerCase()));
		if (me.operator == "=") {
			return (ret === 0)
		}
		if (me.operator == "!=") {
			return (ret !== 0)
		}
		if (me.operator == ">") {
			return (ret > 0)
		}
		if (me.operator == ">=") {
			return (ret >= 0)
		}
		if (me.operator == "<") {
			return (ret < 0)
		}
		if (me.operator == "<=") {
			return (ret <= 0)
		}
		v = me.value;
		from = (Ext.isArray(v) ? v[0] : v) || "";
		to = (Ext.isArray(v) ? v[1] : v) || "";
		retFrom = (me.caseSensitive ? sorter(String(value || "")
			.toLowerCase(), String(from)
			.toLowerCase()) : sorter(value || "", from));
		retTo = (me.caseSensitive ? sorter(String(value || "")
			.toLowerCase(), String(to)
			.toLowerCase()) : sorter(value || "", to));
		if (me.operator == "between") {
			return (retFrom >= 0 && retTo <= 0)
		}
		if (me.operator == "not between") {
			return !(retFrom >= 0 && retTo <= 0)
		}
		return true
	}
});
Ext.define("Ext.pivot.filter.Label", {
	alternateClassName: ["Mz.aggregate.filter.Label"],
	extend: "Ext.pivot.filter.Base",
	alias: "pivotfilter.label",
	isMatch: function(value) {
		var me = this,
			v;
		if (me.operator == "begins") {
			return Ext.String.startsWith(String(value || ""),
				String(me.value || ""), !me.caseSensitive)
		}
		if (me.operator == "not begins") {
			return !Ext.String.startsWith(String(value || ""),
				String(me.value || ""), !me.caseSensitive)
		}
		if (me.operator == "ends") {
			return Ext.String.endsWith(String(value || ""), String(
				me.value || ""), !me.caseSensitive)
		}
		if (me.operator == "not ends") {
			return !Ext.String.endsWith(String(value || ""), String(
				me.value || ""), !me.caseSensitive)
		}
		if (me.operator == "contains") {
			return me.stringContains(String(value || ""), String(me
				.value || ""), !me.caseSensitive)
		}
		if (me.operator == "not contains") {
			return !me.stringContains(String(value || ""), String(
				me.value || ""), !me.caseSensitive)
		}
		if (me.operator == "in") {
			return me.foundInArray(me.value)
		}
		if (me.operator == "not in") {
			return !me.foundInArray(me.value)
		}
		return me.callParent(arguments)
	},
	foundInArray: function(item) {
		var values = Ext.Array.from(this.value),
			len = values.length,
			found = false,
			i;
		if (this.caseSensitive) {
			return Ext.Array.indexOf(values, item) >= 0
		} else {
			for (i = 0; i < len; i++) {
				found = found || (String(item)
					.toLowerCase() == String(values[i])
					.toLowerCase());
				if (found) {
					break
				}
			}
			return found
		}
	},
	stringContains: function(s, start, ignoreCase) {
		var result = (start.length <= s.length);
		if (result) {
			if (ignoreCase) {
				s = s.toLowerCase();
				start = start.toLowerCase()
			}
			result = (s.lastIndexOf(start) >= 0)
		}
		return result
	}
});
Ext.define("Ext.pivot.filter.Value", {
	alternateClassName: ["Mz.aggregate.filter.Value"],
	extend: "Ext.pivot.filter.Base",
	alias: "pivotfilter.value",
	dimensionId: "",
	topType: "items",
	topOrder: "top",
	topSort: true,
	isTopFilter: false,
	constructor: function() {
		var me = this,
			ret = me.callParent(arguments);
		if (Ext.isEmpty(me.dimensionId)) {
			Ext.raise("dimensionId is mandatory on Value filters")
		} else {
			if (!me.parent.matrix.aggregate.getByKey(me.dimensionId)) {
				Ext.raise(
					"There is no aggregate dimension that matches the dimensionId provided"
				)
			}
		}
		me.dimension = me.parent.matrix.aggregate.getByKey(me.dimensionId);
		me.isTopFilter = (me.operator === "top10");
		return ret
	},
	destroy: function() {
		this.dimension = null;
		return this.callParent(arguments)
	},
	isMatch: function(value) {
		var me = this,
			temp = me.value,
			match = me.callParent(arguments);
		if (!match) {
			me.value = me.dimension.renderer(Ext.isNumeric(temp) ?
				parseFloat(temp) : temp);
			match = me.callParent([me.dimension.renderer(Ext.isNumeric(
				value) ? parseFloat(value) : value)]);
			me.value = temp
		}
		return match
	},
	getSerialArgs: function() {
		return {
			dimensionId: this.dimensionId,
			topType: this.topType,
			topOrder: this.topOrder
		}
	},
	applyFilter: function(axis, treeItems) {
		var me = this,
			items = me.topSort ? treeItems : Ext.Array.clone(
				treeItems),
			ret = [];
		if (treeItems.length == 0) {
			return ret
		}
		me.sortItemsByGrandTotal(axis, items);
		switch (me.topType) {
			case "items":
				ret = me.extractTop10Items(items);
				break;
			case "sum":
				ret = me.extractTop10Sum(items);
				break;
			case "percent":
				ret = me.extractTop10Percent(axis, items);
				break
		}
		if (!me.topSort) {
			items.length = 0
		}
		return ret
	},
	extractTop10Items: function(items) {
		var me = this,
			uniqueValues = [],
			i;
		for (i = 0; i < items.length; i++) {
			if (uniqueValues.indexOf(items[i]["tempVar"]) < 0) {
				uniqueValues.push(items[i]["tempVar"]);
				if (uniqueValues.length > me.value || (me.value < i +
					1 && i > 0)) {
					break
				}
			}
		}
		return Ext.Array.slice(items, i)
	},
	extractTop10Sum: function(items) {
		var me = this,
			sum = 0,
			i;
		for (i = 0; i < items.length; i++) {
			sum += items[i]["tempVar"];
			if (sum >= me.value) {
				break
			}
		}
		return Ext.Array.slice(items, i + 1)
	},
	extractTop10Percent: function(axis, items) {
		var me = this,
			sum = 0,
			keys = items[0].key.split(axis.matrix.keysSeparator),
			i,
			leftKey,
			topKey,
			parentKey,
			result,
			grandTotal;
		keys.length--;
		parentKey = (keys.length > 0 ? keys.join(axis.matrix.keysSeparator) :
			axis.matrix.grandTotalKey);
		leftKey = (axis.isLeftAxis ? parentKey : axis.matrix.grandTotalKey);
		topKey = (axis.isLeftAxis ? axis.matrix.grandTotalKey :
			parentKey);
		result = axis.matrix.results.get(leftKey, topKey);
		grandTotal = (result ? result.getValue(me.dimensionId) : 0);
		for (i = 0; i < items.length; i++) {
			sum += items[i]["tempVar"];
			if ((sum * 100 / grandTotal) >= me.value) {
				break
			}
		}
		return Ext.Array.slice(items, i + 1)
	},
	sortItemsByGrandTotal: function(axis, items) {
		var me = this,
			leftKey, topKey, result, i;
		for (i = 0; i < items.length; i++) {
			leftKey = (axis.isLeftAxis ? items[i].key : axis.matrix
				.grandTotalKey);
			topKey = (axis.isLeftAxis ? axis.matrix.grandTotalKey :
				items[i].key);
			result = axis.matrix.results.get(leftKey, topKey);
			items[i]["tempVar"] = (result ? result.getValue(me.dimensionId) :
				0)
		}
		Ext.Array.sort(items,
			function(a, b) {
				var result = axis.matrix.naturalSort(a[
					"tempVar"], b["tempVar"]);
				if (result < 0 && me.topOrder === "top") {
					return 1
				}
				if (result > 0 && me.topOrder === "top") {
					return -1
				}
				return result
			})
	}
});
Ext.define("Ext.pivot.MixedCollection", {
	extend: "Ext.util.MixedCollection",
	alternateClassName: ["Mz.aggregate.MixedCollection"],
	removeAt: function(index) {
		Ext.destroy(this.callParent(arguments))
	},
	clear: function() {
		Ext.destroy(this.items);
		this.callParent(arguments)
	},
	removeAll: function() {
		Ext.destroy(this.items);
		this.callParent(arguments)
	},
	destroy: function() {
		this.clear()
	}
});
Ext.define("Ext.pivot.Aggregators", {
	alternateClassName: ["Mz.aggregate.Aggregators"],
	singleton: true,
	sum: function(records, measure, matrix, rowGroupKey, colGroupKey) {
		var length = records.length,
			total = 0,
			i;
		for (i = 0; i < length; i++) {
			total += Ext.Number.from(records[i].get(measure), 0)
		}
		return total
	},
	avg: function(records, measure, matrix, rowGroupKey, colGroupKey) {
		var length = records.length,
			total = 0,
			i;
		for (i = 0; i < length; i++) {
			total += Ext.Number.from(records[i].get(measure), 0)
		}
		return length > 0 ? (total / length) : 0
	},
	min: function(records, measure, matrix, rowGroupKey, colGroupKey) {
		var data = [],
			length = records.length,
			i,
			v;
		for (i = 0; i < length; i++) {
			data.push(records[i].get(measure))
		}
		v = Ext.Array.min(data);
		return v
	},
	max: function(records, measure, matrix, rowGroupKey, colGroupKey) {
		var data = [],
			length = records.length,
			i;
		for (i = 0; i < length; i++) {
			data.push(records[i].get(measure))
		}
		v = Ext.Array.max(data);
		return v
	},
	count: function(records, measure, matrix, rowGroupKey, colGroupKey) {
		return records.length
	},
	groupSumPercentage: function(records, measure, matrix, rowGroupKey,
		colGroupKey) {
		var sumFn = Ext.pivot.Aggregators.sum,
			length = records.length,
			result, resultParent, sum = 0,
			sumParent = 0,
			keys = rowGroupKey.split(matrix.keysSeparator);
		if (length == 0) {
			return 0
		}
		keys.pop();
		keys = keys.join(matrix.keysSeparator);
		if (Ext.isEmpty(keys)) {
			keys = matrix.grandTotalKey
		}
		result = matrix.results.get(rowGroupKey, colGroupKey);
		if (result) {
			sum = result.getValue("groupSum");
			if (!Ext.isDefined(sum)) {
				sum = result.calculateByFn("groupSum", measure,
					sumFn)
			}
		}
		resultParent = matrix.results.get(keys, colGroupKey);
		if (resultParent) {
			sumParent = resultParent.getValue("groupSum");
			if (!Ext.isDefined(sumParent)) {
				sumParent = resultParent.calculateByFn("groupSum",
					measure, sumFn)
			}
		}
		return (sumParent > 0 && sum > 0) ? sum / sumParent * 100 :
			0
	},
	groupCountPercentage: function(records, measure, matrix,
		rowGroupKey, colGroupKey) {
		var countFn = Ext.pivot.Aggregators.count,
			length = records.length,
			result, resultParent, sum = 0,
			sumParent = 0,
			keys = rowGroupKey.split(matrix.keysSeparator);
		if (length == 0) {
			return 0
		}
		keys.pop();
		keys = keys.join(matrix.keysSeparator);
		if (Ext.isEmpty(keys)) {
			keys = matrix.grandTotalKey
		}
		result = matrix.results.get(rowGroupKey, colGroupKey);
		if (result) {
			sum = result.getValue("groupCount");
			if (!Ext.isDefined(sum)) {
				sum = result.calculateByFn("groupCount", measure,
					countFn)
			}
		}
		resultParent = matrix.results.get(keys, colGroupKey);
		if (resultParent) {
			sumParent = resultParent.getValue("groupCount");
			if (!Ext.isDefined(sumParent)) {
				sumParent = resultParent.calculateByFn("groupCount",
					measure, countFn)
			}
		}
		return (sumParent > 0 && sum > 0) ? sum / sumParent * 100 :
			0
	},
	variance: function(records, measure, matrix, rowGroupKey,
		colGroupKey) {
		var me = Ext.pivot.Aggregators,
			length = records.length,
			avg = me.avg.apply(me, arguments),
			total = 0,
			i;
		if (avg > 0) {
			for (i = 0; i < length; i++) {
				total += Math.pow(Ext.Number.from(records[i].get(
					measure), 0) - avg, 2)
			}
		}
		return (total > 0 && length > 1) ? (total / (length - 1)) :
			0
	},
	varianceP: function(records, measure, matrix, rowGroupKey,
		colGroupKey) {
		var me = Ext.pivot.Aggregators,
			length = records.length,
			avg = me.avg.apply(me, arguments),
			total = 0,
			i;
		if (avg > 0) {
			for (i = 0; i < length; i++) {
				total += Math.pow(Ext.Number.from(records[i].get(
					measure), 0) - avg, 2)
			}
		}
		return (total > 0 && length > 0) ? (total / length) : 0
	},
	stdDev: function(records, measure, matrix, rowGroupKey, colGroupKey) {
		var me = Ext.pivot.Aggregators,
			v = me.variance.apply(me, arguments);
		return v > 0 ? Math.sqrt(v) : 0
	},
	stdDevP: function(records, measure, matrix, rowGroupKey,
		colGroupKey) {
		var me = Ext.pivot.Aggregators,
			v = me.varianceP.apply(me, arguments);
		return v > 0 ? Math.sqrt(v) : 0
	}
});
Ext.define("Ext.pivot.axis.Item", {
	alternateClassName: ["Mz.aggregate.axis.Item"],
	level: 0,
	key: "",
	value: "",
	sortValue: "",
	name: "",
	dimensionId: "",
	dimension: null,
	children: null,
	record: null,
	axis: null,
	data: null,
	expanded: false,
	constructor: function(config) {
		var me = this;
		Ext.apply(me, config || {});
		if (Ext.isEmpty(me.sortValue)) {
			me.sortValue = me.value
		}
		me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		Ext.destroy(me.children);
		me.axis = me.data = me.dimension = me.record = me.children =
			null;
		me.callParent(arguments)
	},
	getTextTotal: function() {
		var me = this,
			groupHeaderTpl = Ext.XTemplate.getTpl(me.axis.matrix,
				"textTotalTpl");
		return groupHeaderTpl.apply({
			groupField: me.dimension.dataIndex,
			columnName: me.dimension.dataIndex,
			name: me.name,
			rows: me.children || []
		})
	},
	expand: function(includeChildren) {
		var me = this;
		me.expanded = true;
		if (includeChildren === true) {
			me.expandCollapseChildrenTree(me, true)
		}
		me.axis.matrix.fireEvent("groupexpand", me.axis.matrix, (me
			.axis.isLeftAxis ? "row" : "col"), me)
	},
	collapse: function(includeChildren) {
		var me = this;
		me.expanded = false;
		if (includeChildren === true) {
			me.expandCollapseChildrenTree(me, false)
		}
		me.axis.matrix.fireEvent("groupcollapse", me.axis.matrix, (
			me.axis.isLeftAxis ? "row" : "col"), me)
	},
	expandCollapseChildrenTree: function(item, state) {
		var me = this,
			i;
		item.expanded = state;
		if (Ext.isArray(me.children)) {
			for (i = 0; i < me.children.length; i++) {
				me.expandCollapseChildrenTree(me.children[i], state)
			}
		}
	}
});
Ext.define("Ext.pivot.dimension.Item", {
	alternateClassName: ["Mz.aggregate.dimension.Item"],
	requires: ["Ext.pivot.MixedCollection", "Ext.pivot.filter.Label",
		"Ext.pivot.filter.Value"
	],
	header: "",
	dataIndex: "",
	sortIndex: "",
	width: 100,
	flex: 0,
	align: "left",
	sortable: true,
	direction: "ASC",
	sorterFn: null,
	caseSensitiveSort: true,
	filter: null,
	renderer: null,
	grouperFn: null,
	blankText: "(blank)",
	showZeroAsBlank: false,
	aggregator: "sum",
	isAggregate: false,
	id: "",
	values: null,
	matrix: null,
	constructor: function(config) {
		var me = this,
			aggregators = Ext.pivot.Aggregators;
		me.initialConfig = config || {};
		if (config.isAggregate === true && Ext.isEmpty(config.align)) {
			config.align = "left"
		}
		Ext.apply(me, config || {});
		if (Ext.isEmpty(me.id)) {
			me.id = Ext.id()
		}
		if (me.isAggregate) {
			if (Ext.isEmpty(me.dataIndex) && Ext.isDefined(me.measure)) {
				me.dataIndex = me.measure;
				delete me.measure
			}
			if (Ext.isEmpty(me.aggregator)) {
				me.aggregator = "sum"
			}
			if (Ext.isString(me.aggregator) && Ext.isFunction(
				aggregators[me.aggregator])) {
				me.aggregatorFn = Ext.bind(aggregators[me.aggregator],
					aggregators)
			} else {
				if (Ext.isFunction(me.aggregator)) {
					me.aggregatorFn = me.aggregator
				}
			}
			me.filter = false
		} else {
			if (Ext.isObject(me.filter)) {
				Ext.applyIf(me.filter, {
					type: "label",
					parent: me
				});
				me.filter = Ext.Factory.pivotfilter(me.filter)
			} else {
				me.filter = false
			}
		}
		if (!Ext.isFunction(me.grouperFn)) {
			me.grouperFn = me.defaultGrouperFn
		}
		if (me.sortable && !me.sorterFn) {
			me.sorterFn = me.defaultSorterFn
		}
		if (Ext.isEmpty(me.sortIndex)) {
			me.sortIndex = me.dataIndex
		}
		if (!me.renderer) {
			me.renderer = me.getDefaultFormatRenderer(me.isAggregate ?
				"0,000.00" : "")
		} else {
			if (Ext.isString(me.renderer)) {
				me.renderer = me.getDefaultFormatRenderer(me.renderer)
			}
		}
		me.values = Ext.create("Ext.pivot.MixedCollection");
		me.values.getKey = function(item) {
			return item.value
		};
		me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		Ext.destroyMembers(me, "values", "filter");
		me.matrix = me.values = me.filter = null
	},
	serialize: function() {
		var me = this;
		return {
			id: me.id,
			header: me.header,
			dataIndex: me.dataIndex,
			sortIndex: me.sortIndex,
			width: me.width,
			flex: me.flex,
			align: me.align,
			sortable: me.sortable,
			direction: me.direction,
			caseSensitiveSort: me.caseSensitiveSort,
			filter: me.filter ? me.filter.serialize() : null,
			aggregator: Ext.isString(me.aggregator) ? me.aggregator :
				"sum",
			showZeroAsBlank: me.showZeroAsBlank
		}
	},
	addValue: function(value, display) {
		if (!this.values.getByKey(value)) {
			this.values.add({
				value: value,
				display: display
			})
		}
	},
	getValues: function() {
		return this.values
	},
	getId: function() {
		return this.id
	},
	defaultSorterFn: function(o1, o2) {
		var me = this,
			s1 = o1.sortValue,
			s2 = o2.sortValue,
			result;
		if (s1 instanceof Date) {
			s1 = s1.getTime()
		}
		if (s2 instanceof Date) {
			s2 = s2.getTime()
		}
		if (!me.caseSensitiveSort) {
			s1 = String(s1)
				.toUpperCase();
			s2 = String(s2)
				.toUpperCase()
		}
		result = Ext.pivot.matrix.Base.prototype.naturalSort(s1, s2);
		if (result < 0 && me.direction === "DESC") {
			return 1
		}
		if (result > 0 && me.direction === "DESC") {
			return -1
		}
		return result
	},
	getDefaultFormatRenderer: function(format) {
		var me = this;
		return function(v) {
			var positive;
			if (Ext.isEmpty(format)) {
				return v
			}
			if (Ext.isFunction(format)) {
				return format.apply(me, arguments)
			}
			if (!Ext.isNumber(v)) {
				return v
			}
			if (me.isAggregate && v === 0 && me.showZeroAsBlank) {
				return ""
			}
			positive = (v >= 0);
			v = Math.abs(v);
			v = Ext.util.Format.number(v, format);
			return positive ? v : "-" + v
		}
	},
	defaultGrouperFn: function(record) {
		return record.get(this.dataIndex)
	}
});
Ext.define("Ext.pivot.axis.Base", {
	alternateClassName: ["Mz.aggregate.axis.Abstract"],
	alias: "pivotaxis.base",
	mixins: ["Ext.mixin.Factoryable"],
	requires: ["Ext.pivot.MixedCollection", "Ext.pivot.dimension.Item",
		"Ext.pivot.axis.Item"
	],
	dimensions: null,
	matrix: null,
	items: null,
	tree: null,
	levels: 0,
	isLeftAxis: false,
	constructor: function(config) {
		var me = this,
			i, sorter;
		if (!config || !config.matrix) {
			Ext.log("Wrong initialization of the axis!");
			return
		}
		me.isLeftAxis = config.isLeftAxis || me.isLeftAxis;
		me.matrix = config.matrix;
		me.tree = [];
		me.dimensions = Ext.create("Ext.pivot.MixedCollection");
		me.dimensions.getKey = function(item) {
			return item.getId()
		};
		me.items = Ext.create("Ext.pivot.MixedCollection");
		me.items.getKey = function(item) {
			return item.key
		};
		Ext.Array.each(Ext.Array.from(config.dimensions || []), me.addDimension,
			me)
	},
	destroy: function() {
		var me = this;
		Ext.destroyMembers(me, "dimensions", "items", "tree");
		me.matrix = me.dimensions = me.items = me.tree = null
	},
	addDimension: function(config) {
		if (config) {
			this.dimensions.add(Ext.create(
				"Ext.pivot.dimension.Item", Ext.apply({
						matrix: this.matrix
					},
					config)))
		}
	},
	addItem: function(item) {
		var me = this;
		if (!Ext.isObject(item) || Ext.isEmpty(item.key) || Ext.isEmpty(
			item.value) || Ext.isEmpty(item.dimensionId)) {
			return false
		}
		item.key = String(item.key);
		item.dimension = me.dimensions.getByKey(item.dimensionId);
		item.name = item.name || item.dimension.renderer(item.value);
		item.dimension.addValue(item.value, item.name);
		item.axis = me;
		if (!me.items.getByKey(item.key) && item.dimension) {
			me.items.add(Ext.create("Ext.pivot.axis.Item", item));
			return true
		}
		return false
	},
	clear: function() {
		this.items.clear();
		this.tree = null
	},
	getTree: function() {
		if (!this.tree) {
			this.buildTree()
		}
		return this.tree
	},
	findTreeElement: function(attribute, value) {
		var tree = arguments[2] || this.tree || [],
			level = arguments[3] || 1,
			obj = null;
		var filter = Ext.Array.filter(tree,
			function(item, index, all) {
				return Ext.isDate(value) ? Ext.Date.isEqual(
					item[attribute], value) : item[
					attribute] === value
			},
			this);
		if (filter.length > 0) {
			return {
				level: level,
				node: filter[0]
			}
		}
		Ext.Array.each(tree,
			function(item, index, all) {
				if (item.children) {
					obj = this.findTreeElement(attribute, value,
						item.children, level + 1);
					if (obj) {
						return false
					}
				}
			},
			this);
		return obj
	},
	buildTree: function() {
		var me = this;
		me.tree = [];
		me.items.each(me.addItemToTree, me);
		me.sortTree()
	},
	addItemToTree: function(item) {
		var me = this,
			keys = String(item.key)
			.split(me.matrix.keysSeparator),
			parentKey = "",
			el;
		keys = Ext.Array.slice(keys, 0, keys.length - 1);
		parentKey = keys.join(me.matrix.keysSeparator);
		el = me.findTreeElement("key", parentKey);
		if (el) {
			item.level = el.level;
			item.data = Ext.clone(el.node.data || {});
			el.node.children = el.node.children || [];
			el.node.children.push(item)
		} else {
			item.level = 0;
			item.data = {};
			me.tree.push(item)
		}
		item.data[item.dimension.getId()] = item.name;
		me.levels = Math.max(me.levels, item.level)
	},
	sortTree: function() {
		var tree = arguments[0] || this.tree,
			dimension;
		if (tree.length > 0) {
			dimension = tree[0].dimension
		}
		if (dimension && dimension.sortable === true) {
			Ext.Array.sort(tree,
				function(a, b) {
					return dimension.sorterFn(a, b)
				})
		}
		Ext.Array.each(tree,
			function(item) {
				if (item.children) {
					this.sortTree(item.children)
				}
			},
			this)
	},
	sortTreeByField: function(field, direction) {
		var me = this,
			sorted = false,
			dimension;
		if (field == me.matrix.compactViewKey) {
			sorted = me.sortTreeByDimension(me.tree, me.dimensions.getRange(),
				direction);
			me.dimensions.each(function(item) {
				item.direction = direction
			})
		} else {
			direction = direction || "ASC";
			dimension = me.dimensions.getByKey(field);
			if (dimension) {
				sorted = me.sortTreeByDimension(me.tree, dimension,
					direction);
				dimension.direction = direction
			} else {
				sorted = me.sortTreeByRecords(me.tree, field,
					direction)
			}
		}
		return sorted
	},
	sortTreeByDimension: function(tree, dimension, direction) {
		var sorted = false,
			dimensions = Ext.Array.from(dimension),
			aDimension,
			len,
			i,
			temp;
		tree = tree || [];
		len = tree.length;
		if (len > 0) {
			aDimension = tree[0].dimension
		}
		if (Ext.Array.indexOf(dimensions, aDimension) >= 0) {
			if (aDimension.sortable) {
				temp = aDimension.direction;
				aDimension.direction = direction;
				Ext.Array.sort(tree, Ext.bind(aDimension.sorterFn,
					aDimension));
				aDimension.direction = temp
			}
			sorted = aDimension.sortable
		}
		for (i = 0; i < len; i++) {
			sorted = this.sortTreeByDimension(tree[i].children,
				dimension, direction) || sorted
		}
		return sorted
	},
	sortTreeByRecords: function(tree, field, direction) {
		var i, len;
		tree = tree || [];
		len = tree.length;
		if (len <= 0) {
			return false
		}
		if (tree[0].record) {
			this.sortTreeRecords(tree, field, direction)
		} else {
			this.sortTreeLeaves(tree, field, direction)
		}
		for (i = 0; i < len; i++) {
			this.sortTreeByRecords(tree[i].children, field,
				direction)
		}
		return true
	},
	sortTreeRecords: function(tree, field, direction) {
		var sortFn = this.matrix.naturalSort;
		direction = direction || "ASC";
		Ext.Array.sort(tree || [],
			function(a, b) {
				var result, o1 = a.record,
					o2 = b.record;
				if (!(o1 && o1.isModel && o2 && o2.isModel)) {
					return 0
				}
				result = sortFn(o1.get(field) || "", o2.get(
					field) || "");
				if (result < 0 && direction === "DESC") {
					return 1
				}
				if (result > 0 && direction === "DESC") {
					return -1
				}
				return result
			})
	},
	sortTreeLeaves: function(tree, field, direction) {
		var sortFn = this.matrix.naturalSort,
			results = this.matrix.results,
			matrixModel = this.matrix.model,
			idx = Ext.Array.indexOf(Ext.Array.pluck(matrixModel,
				"name"), field),
			col,
			agg;
		if (idx < 0) {
			return false
		}
		col = matrixModel[idx]["col"];
		agg = matrixModel[idx]["agg"];
		direction = direction || "ASC";
		Ext.Array.sort(tree || [],
			function(a, b) {
				var result, o1, o2;
				o1 = results.get(a.key, col);
				if (o1) {
					o1 = o1.getValue(agg)
				} else {
					o1 = 0
				}
				o2 = results.get(b.key, col);
				if (o2) {
					o2 = o2.getValue(agg)
				} else {
					o2 = 0
				}
				result = sortFn(o1, o2);
				if (result < 0 && direction === "DESC") {
					return 1
				}
				if (result > 0 && direction === "DESC") {
					return -1
				}
				return result
			})
	}
});
Ext.define("Ext.pivot.axis.Local", {
	alternateClassName: ["Mz.aggregate.axis.Local"],
	extend: "Ext.pivot.axis.Base",
	alias: "pivotaxis.local",
	processRecord: function(record) {
		var me = this,
			items = [],
			parentKey = "",
			filterOk = true,
			dimCount = me.dimensions.getCount(),
			groupValue,
			groupKey,
			dimension,
			i;
		for (i = 0; i < dimCount; i++) {
			dimension = me.dimensions.getAt(i);
			groupValue = dimension.grouperFn(record);
			groupKey = parentKey ? parentKey + me.matrix.keysSeparator :
				"";
			groupValue = Ext.isEmpty(groupValue) ? dimension.blankText :
				groupValue;
			groupKey += me.matrix.getKey(groupValue);
			if (dimension.filter instanceof Ext.pivot.filter.Label) {
				filterOk = dimension.filter.isMatch(groupValue)
			}
			if (!filterOk) {
				break
			}
			items.push({
				value: groupValue,
				sortValue: record.get(dimension.sortIndex),
				key: groupKey,
				dimensionId: dimension.getId()
			});
			parentKey = groupKey
		}
		if (filterOk) {
			return items
		} else {
			return null
		}
	},
	buildTree: function() {
		this.callParent(arguments);
		this.filterTree()
	},
	filterTree: function() {
		var me = this,
			length = me.dimensions.getCount(),
			hasFilters = false,
			i;
		for (i = 0; i < length; i++) {
			hasFilters = hasFilters || (me.dimensions.getAt(i)
				.filter instanceof Ext.pivot.filter.Value)
		}
		if (!hasFilters) {
			return
		}
		me.matrix.filterApplied = true;
		me.filterTreeItems(me.tree)
	},
	filterTreeItems: function(items) {
		var me = this,
			filter, i, filteredItems;
		if (!items || !Ext.isArray(items) || items.length <= 0) {
			return
		}
		filter = items[0].dimension.filter;
		if (filter && (filter instanceof Ext.pivot.filter.Value)) {
			if (filter.isTopFilter) {
				filteredItems = filter.applyFilter(me, items) || []
			} else {
				filteredItems = Ext.Array.filter(items, me.canRemoveItem,
					me)
			}
			me.removeRecordsFromResults(filteredItems);
			me.removeItemsFromArray(items, filteredItems);
			for (i = 0; i < filteredItems.length; i++) {
				me.removeTreeChildren(filteredItems[i])
			}
		}
		for (i = 0; i < items.length; i++) {
			if (items[i].children) {
				me.filterTreeItems(items[i].children);
				if (items[i].children.length === 0) {
					me.items.remove(items[i]);
					Ext.Array.erase(items, i, 1);
					i--
				}
			}
		}
	},
	removeTreeChildren: function(item) {
		var i, len;
		if (item.children) {
			len = item.children.length;
			for (i = 0; i < len; i++) {
				this.removeTreeChildren(item.children[i])
			}
		}
		this.items.remove(item)
	},
	canRemoveItem: function(item) {
		var me = this,
			leftKey = (me.isLeftAxis ? item.key : me.matrix.grandTotalKey),
			topKey = (me.isLeftAxis ? me.matrix.grandTotalKey :
				item.key),
			result = me.matrix.results.get(leftKey, topKey),
			filter = item.dimension.filter;
		return (result ? !filter.isMatch(result.getValue(filter.dimensionId)) :
			false)
	},
	removeItemsFromArray: function(source, toDelete) {
		for (var i = 0; i < source.length; i++) {
			if (Ext.Array.indexOf(toDelete, source[i]) >= 0) {
				Ext.Array.erase(source, i, 1);
				i--
			}
		}
	},
	removeRecordsFromResults: function(items) {
		for (var i = 0; i < items.length; i++) {
			this.removeRecordsByItem(items[i])
		}
	},
	removeRecordsByItem: function(item) {
		var me = this,
			keys, i, results, result, toRemove;
		if (item.children) {
			me.removeRecordsFromResults(item.children)
		}
		if (me.isLeftAxis) {
			toRemove = me.matrix.results.get(item.key, me.matrix.grandTotalKey);
			results = me.matrix.results.getByLeftKey(me.matrix.grandTotalKey)
		} else {
			toRemove = me.matrix.results.get(me.matrix.grandTotalKey,
				item.key);
			results = me.matrix.results.getByTopKey(me.matrix.grandTotalKey)
		}
		if (!toRemove) {
			return
		}
		for (i = 0; i < results.length; i++) {
			me.removeItemsFromArray(results[i].records, toRemove.records)
		}
		keys = item.key.split(me.matrix.keysSeparator);
		keys.length = keys.length - 1;
		while (keys.length > 0) {
			if (me.isLeftAxis) {
				results = me.matrix.results.getByLeftKey(keys.join(
					me.matrix.keysSeparator))
			} else {
				results = me.matrix.results.getByTopKey(keys.join(
					me.matrix.keysSeparator))
			}
			for (i = 0; i < results.length; i++) {
				me.removeItemsFromArray(results[i].records,
					toRemove.records)
			}
			keys.length = keys.length - 1
		}
	}
});
Ext.define("Ext.pivot.result.Base", {
	alias: "pivotresult.base",
	mixins: ["Ext.mixin.Factoryable"],
	leftKey: "",
	topKey: "",
	dirty: false,
	values: null,
	matrix: null,
	constructor: function(config) {
		var me = this;
		Ext.apply(me, config || {});
		me.values = {};
		return me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		me.matrix = me.values = null;
		me.leftAxisItem = me.topAxisItem = null;
		return me.callParent(arguments)
	},
	calculate: Ext.emptyFn,
	calculateByFn: Ext.emptyFn,
	addValue: function(dimensionId, value) {
		this.values[dimensionId] = value
	},
	getValue: function(dimensionId) {
		return this.values[dimensionId]
	},
	getLeftAxisItem: function() {
		return this.matrix.leftAxis.items.getByKey(this.leftKey)
	},
	getTopAxisItem: function() {
		return this.matrix.topAxis.items.getByKey(this.topKey)
	}
});
Ext.define("Ext.pivot.result.Local", {
	extend: "Ext.pivot.result.Base",
	alias: "pivotresult.local",
	alternateClassName: ["Mz.aggregate.matrix.Result"],
	records: null,
	constructor: function(config) {
		this.records = [];
		return this.callParent(arguments)
	},
	destroy: function() {
		this.records.length = 0;
		this.records = null;
		return this.callParent(arguments)
	},
	calculate: function() {
		var me = this,
			i, dimension, length = me.matrix.aggregate.getCount();
		for (i = 0; i < length; i++) {
			dimension = me.matrix.aggregate.getAt(i);
			me.addValue(dimension.getId(), dimension.aggregatorFn(
				me.records, dimension.dataIndex, me.matrix,
				me.leftKey, me.topKey))
		}
	},
	calculateByFn: function(key, dataIndex, aggFn) {
		var me = this,
			v = aggFn(me.records, dataIndex, me.matrix, me.leftKey,
				me.topKey);
		me.addValue(key, v);
		return v
	},
	addRecord: function(record) {
		this.records.push(record)
	}
});
Ext.define("Ext.pivot.result.Collection", {
	alternateClassName: ["Mz.aggregate.matrix.Results"],
	requires: ["Ext.pivot.MixedCollection", "Ext.pivot.result.Base"],
	resultType: "base",
	items: null,
	matrix: null,
	constructor: function(config) {
		var me = this;
		Ext.apply(me, config || {});
		me.items = Ext.create("Ext.pivot.MixedCollection");
		me.items.getKey = function(obj) {
			return obj.leftKey + "/" + obj.topKey
		};
		return me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		Ext.destroy(me.items);
		me.matrix = me.items = null;
		me.callParent(arguments)
	},
	clear: function() {
		this.items.clear()
	},
	add: function(leftKey, topKey) {
		var obj = this.get(leftKey, topKey);
		if (!obj) {
			obj = this.items.add(Ext.Factory.pivotresult({
				type: this.resultType,
				leftKey: leftKey,
				topKey: topKey,
				matrix: this.matrix
			}))
		}
		return obj
	},
	get: function(leftKey, topKey) {
		return this.items.getByKey(leftKey + "/" + topKey)
	},
	getByLeftKey: function(leftKey) {
		var col = this.items.filterBy(function(item, key) {
			var keys = String(key)
				.split("/");
			return (leftKey == keys[0])
		});
		return col.getRange()
	},
	getByTopKey: function(topKey) {
		var col = this.items.filterBy(function(item, key) {
			var keys = String(key)
				.split("/");
			return (keys.length > 1 && topKey == keys[1])
		});
		return col.getRange()
	},
	calculate: function() {
		this.items.each(function(item) {
			item.calculate()
		})
	}
});
Ext.define("Ext.pivot.matrix.Base", {
	alternateClassName: ["Mz.aggregate.matrix.Abstract"],
	extend: "Ext.util.Observable",
	alias: "pivotmatrix.base",
	mixins: ["Ext.mixin.Factoryable"],
	requires: ["Ext.util.DelayedTask", "Ext.data.ArrayStore",
		"Ext.XTemplate", "Ext.pivot.Aggregators",
		"Ext.pivot.MixedCollection", "Ext.pivot.axis.Base",
		"Ext.pivot.dimension.Item", "Ext.pivot.result.Collection"
	],
	resultType: "base",
	leftAxisType: "base",
	topAxisType: "base",
	textRowLabels: "Row labels",
	textTotalTpl: "合计({name})",
	textGrandTotalTpl: "Grand total",
	keysSeparator: "#_#",
	grandTotalKey: "grandtotal",
	compactViewKey: "_compactview_",
	viewLayoutType: "outline",
	rowSubTotalsPosition: "first",
	rowGrandTotalsPosition: "first",
	colSubTotalsPosition: "first",
	colGrandTotalsPosition: "first",
	showZeroAsBlank: false,
	leftAxis: null,
	topAxis: null,
	aggregate: null,
	results: null,
	pivotStore: null,
	isDestroyed: false,
	constructor: function(config) {
		var ret = this.callParent(arguments);
		this.initialize(true, config);
		return ret
	},
	destroy: function() {
		var me = this;
		me.delayedTask.cancel();
		me.delayedTask = null;
		if (Ext.isFunction(me.onDestroy)) {
			me.onDestroy()
		}
		Ext.destroy(me.results, me.leftAxis, me.topAxis, me.aggregate,
			me.pivotStore);
		me.results = me.leftAxis = me.topAxis = me.aggregate = me.pivotStore =
			null;
		if (Ext.isArray(me.columns)) {
			me.columns.length = 0
		}
		if (Ext.isArray(me.model)) {
			me.model.length = 0
		}
		if (Ext.isArray(me.totals)) {
			me.totals.length = 0
		}
		me.columns = me.model = me.totals = me.keysMap = null;
		me.isDestroyed = true;
		me.callParent(arguments)
	},
	getKey: function(value) {
		var me = this;
		me.keysMap = me.keysMap || {};
		if (!Ext.isDefined(me.keysMap[value])) {
			me.keysMap[value] = Ext.id()
		}
		return me.keysMap[value]
	},
	naturalSort: (function() {
		var re =
			/(^([+\-]?(?:\d*)(?:\.\d*)?(?:[eE][+\-]?\d+)?)?$|^0x[\da-fA-F]+$|\d+)/g,
			sre = /^\s+|\s+$/g,
			snre = /\s+/g,
			dre =
			/(^([\w ]+,?[\w ]+)?[\w ]+,?[\w ]+\d+:\d+(:\d+)?[\w ]?|^\d{1,4}[\/\-]\d{1,4}[\/\-]\d{1,4}|^\w+, \w+ \d+, \d{4})/,
			hre = /^0x[0-9a-f]+$/i,
			ore = /^0/,
			normChunk = function(s, l) {
				s = s || "";
				return (!s.match(ore) || l == 1) && parseFloat(
						s) || s.replace(snre, " ")
					.replace(sre, "") || 0
			};
		return function(a, b) {
			var x = String(a instanceof Date ? a.getTime() :
					(a || ""))
				.replace(sre, ""),
				y = String(b instanceof Date ? b.getTime() :
					(b || ""))
				.replace(sre, ""),
				xN = x.replace(re, "\0$1\0")
				.replace(/\0$/, "")
				.replace(/^\0/, "")
				.split("\0"),
				yN = y.replace(re, "\0$1\0")
				.replace(/\0$/, "")
				.replace(/^\0/, "")
				.split("\0"),
				xD = parseInt(x.match(hre), 16) || (xN.length !==
					1 && Date.parse(x)),
				yD = parseInt(y.match(hre), 16) || xD && y.match(
					dre) && Date.parse(y) || null,
				oFxNcL,
				oFyNcL;
			if (yD) {
				if (xD < yD) {
					return -1
				} else {
					if (xD > yD) {
						return 1
					}
				}
			}
			for (var cLoc = 0,
				xNl = xN.length,
				yNl = yN.length,
				numS = Math.max(xNl, yNl); cLoc < numS; cLoc++) {
				oFxNcL = normChunk(xN[cLoc], xNl);
				oFyNcL = normChunk(yN[cLoc], yNl);
				if (isNaN(oFxNcL) !== isNaN(oFyNcL)) {
					return (isNaN(oFxNcL)) ? 1 : -1
				} else {
					if (typeof oFxNcL !== typeof oFyNcL) {
						oFxNcL += "";
						oFyNcL += ""
					}
				}
				if (oFxNcL < oFyNcL) {
					return -1
				}
				if (oFxNcL > oFyNcL) {
					return 1
				}
			}
			return 0
		}
	}()),
	initialize: function(firstTime, config) {
		var me = this,
			props = ["viewLayoutType", "rowSubTotalsPosition",
				"rowGrandTotalsPosition", "colSubTotalsPosition",
				"colGrandTotalsPosition", "showZeroAsBlank"
			],
			i;
		me.initResults();
		me.initAggregates(config.aggregate || []);
		me.initAxis(config.leftAxis || [], config.topAxis || []);
		for (i = 0; i < props.length; i++) {
			if (config.hasOwnProperty(props[i])) {
				me[props[i]] = config[props[i]]
			}
		}
		me.totals = [];
		me.keysMap = null;
		if (firstTime) {
			me.pivotStore = Ext.create("Ext.data.ArrayStore", {
				autoDestroy: false,
				fields: []
			});
			me.delayedTask = new Ext.util.DelayedTask(me.startProcess,
				me);
			if (Ext.isFunction(me.onInitialize)) {
				me.onInitialize()
			}
		}
	},
	onInitialize: Ext.emptyFn,
	onDestroy: Ext.emptyFn,
	reconfigure: function(config) {
		var me = this,
			config = Ext.clone(config || {});
		me.initialize(false, config);
		me.clearData();
		if (Ext.isFunction(me.onReconfigure)) {
			me.onReconfigure(config)
		}
		me.delayedTask.delay(5)
	},
	onReconfigure: Ext.emptyFn,
	initResults: function() {
		Ext.destroy(this.results);
		this.results = Ext.create("Ext.pivot.result.Collection", {
			resultType: this.resultType,
			matrix: this
		})
	},
	initAggregates: function(aggregates) {
		var me = this,
			i, item;
		Ext.destroy(me.aggregate);
		me.aggregate = Ext.create("Ext.pivot.MixedCollection");
		me.aggregate.getKey = function(item) {
			return item.getId()
		};
		if (Ext.isEmpty(aggregates)) {
			return
		}
		aggregates = Ext.Array.from(aggregates);
		for (i = 0; i < aggregates.length; i++) {
			item = aggregates[i];
			Ext.applyIf(item, {
				isAggregate: true,
				align: "right",
				showZeroAsBlank: me.showZeroAsBlank
			});
			me.aggregate.add(Ext.create("Ext.pivot.dimension.Item",
				item))
		}
	},
	initAxis: function(leftAxis, topAxis) {
		var me = this;
		leftAxis = Ext.Array.from(leftAxis || []);
		topAxis = Ext.Array.from(topAxis || []);
		Ext.destroy(me.leftAxis);
		me.leftAxis = Ext.Factory.pivotaxis({
			type: me.leftAxisType,
			matrix: me,
			dimensions: leftAxis,
			isLeftAxis: true
		});
		Ext.destroy(me.topAxis);
		me.topAxis = Ext.Factory.pivotaxis({
			type: me.topAxisType,
			matrix: me,
			dimensions: topAxis,
			isLeftAxis: false
		})
	},
	clearData: function() {
		var me = this;
		me.fireEvent("cleardata", me);
		me.leftAxis.clear();
		me.topAxis.clear();
		me.results.clear();
		if (Ext.isArray(me.columns)) {
			me.columns.length = 0
		}
		if (Ext.isArray(me.model)) {
			me.model.length = 0
		}
		me.totals = [];
		me.keysMap = null;
		if (me.pivotStore) {
			me.pivotStore.removeAll(true)
		}
	},
	startProcess: Ext.emptyFn,
	endProcess: function() {
		var me = this;
		me.leftAxis.getTree();
		me.topAxis.getTree();
		me.buildModelAndColumns();
		me.buildPivotStore();
		if (Ext.isFunction(me.onBuildStore)) {
			me.onBuildStore(me.pivotStore)
		}
		me.fireEvent("storebuilt", me, me.pivotStore);
		me.fireEvent("done")
	},
	onBuildModel: Ext.emptyFn,
	onBuildColumns: Ext.emptyFn,
	onBuildRecord: Ext.emptyFn,
	onBuildTotals: Ext.emptyFn,
	onBuildStore: Ext.emptyFn,
	buildModelAndColumns: function() {
		var me = this;
		me.model = [{
			name: "id",
			type: "string"
		}];
		me.buildColumnHeaders(false)
	},
	buildColumnHeaders: function(disableChangeModel) {
		var me = this;
		me.internalCounter = 0;
		me.columns = [];
		if (me.viewLayoutType == "compact") {
			me.generateCompactLeftAxis(disableChangeModel)
		} else {
			me.leftAxis.dimensions.each(function(item) {
					this.parseLeftAxisDimension(item,
						disableChangeModel)
				},
				me)
		}
		if (me.colGrandTotalsPosition == "first") {
			me.columns.push(me.parseAggregateForColumn(null, {
					text: me.textGrandTotalTpl,
					grandTotal: true
				},
				disableChangeModel))
		}
		Ext.Array.each(me.topAxis.getTree(),
			function(item) {
				this.parseTopAxisItem(item, disableChangeModel)
			},
			me);
		if (me.colGrandTotalsPosition == "last") {
			me.columns.push(me.parseAggregateForColumn(null, {
					text: me.textGrandTotalTpl,
					grandTotal: true
				},
				disableChangeModel))
		}
		if (!disableChangeModel) {
			if (Ext.isFunction(me.onBuildModel)) {
				me.onBuildModel(me.model)
			}
			me.fireEvent("modelbuilt", me, me.model)
		}
		if (Ext.isFunction(me.onBuildColumns)) {
			me.onBuildColumns(me.columns)
		}
		me.fireEvent("columnsbuilt", me, me.columns)
	},
	parseLeftAxisDimension: function(dimension, disableChangeModel) {
		if (!disableChangeModel) {
			this.model.push({
				name: dimension.getId(),
				type: "string"
			})
		}
		this.columns.push({
			dataIndex: dimension.getId(),
			text: dimension.header,
			dimension: dimension,
			leftAxis: true
		})
	},
	generateCompactLeftAxis: function(disableChangeModel) {
		var me = this;
		if (!disableChangeModel) {
			me.model.push({
				name: me.compactViewKey,
				type: "string"
			})
		}
		me.columns.push({
			dataIndex: me.compactViewKey,
			text: me.textRowLabels,
			leftAxis: true,
			width: 200
		})
	},
	parseTopAxisItem: function(item, disableChangeModel) {
		var me = this,
			columns = [],
			retColumns = [],
			o1,
			o2,
			doAdd = false;
		if (!item.children) {
			columns = me.parseAggregateForColumn(item, null,
				disableChangeModel);
			if (item.level === 0) {
				me.columns.push(columns)
			} else {
				return columns
			}
		} else {
			if (me.colSubTotalsPosition == "first") {
				o2 = me.addColSummary(item, disableChangeModel,
					true);
				if (o2) {
					retColumns.push(o2)
				}
			}
			Ext.Array.each(item.children,
				function(child) {
					var ret = me.parseTopAxisItem(child,
						disableChangeModel);
					if (Ext.isArray(ret)) {
						columns = Ext.Array.merge(columns, ret)
					} else {
						columns.push(ret)
					}
				});
			if (item.expanded || !disableChangeModel) {
				o1 = {
					text: item.name,
					columns: columns,
					key: item.key,
					xcollapsible: item.expanded,
					xexpanded: item.expanded,
					xexpandable: true
				};
				if (item.level === 0) {
					me.columns.push(o1)
				}
				retColumns.push(o1)
			}
			if (me.colSubTotalsPosition == "last") {
				o2 = me.addColSummary(item, disableChangeModel,
					true);
				if (o2) {
					retColumns.push(o2)
				}
			}
			if (me.colSubTotalsPosition == "none") {
				o2 = me.addColSummary(item, disableChangeModel,
					false);
				if (o2) {
					retColumns.push(o2)
				}
			}
			return retColumns
		}
	},
	addColSummary: function(item, disableChangeModel, addColumns) {
		var me = this,
			o2, doAdd = false;
		o2 = me.parseAggregateForColumn(item, {
				text: item.expanded ? item.getTextTotal() : item
					.name,
				subTotal: true
			},
			disableChangeModel);
		if (addColumns) {
			doAdd = true
		} else {
			doAdd = !item.expanded
		}
		if (doAdd) {
			if (item.level === 0) {
				me.columns.push(o2)
			}
			Ext.apply(o2, {
				key: item.key,
				xcollapsible: !item.expanded,
				xexpanded: item.expanded,
				xexpandable: !item.expanded
			});
			return o2
		}
	},
	parseAggregateForColumn: function(item, config, disableChangeModel) {
		var me = this,
			columns = [],
			column = {};
		me.aggregate.each(function(agg) {
			me.internalCounter++;
			if (!disableChangeModel) {
				me.model.push({
					name: "c" + me.internalCounter,
					type: "auto",
					defaultValue: undefined,
					useNull: true,
					col: item ? item.key : me.grandTotalKey,
					agg: agg.getId()
				})
			}
			columns.push({
				dataIndex: "c" + me.internalCounter,
				text: agg.header,
				topAxis: true,
				subTotal: (config ? config.subTotal ===
					true : false),
				grandTotal: (config ? config.grandTotal ===
					true : false),
				dimension: agg
			})
		});
		if (columns.length == 0 && me.aggregate.getCount() == 0) {
			me.internalCounter++;
			column = Ext.apply({
					text: item ? item.name : "",
					dataIndex: "c" + me.internalCounter
				},
				config || {})
		} else {
			if (columns.length == 1) {
				column = Ext.applyIf({
						text: item ? item.name : ""
					},
					columns[0]);
				Ext.apply(column, config || {});
				if (config && config.grandTotal && me.aggregate.getCount() ==
					1) {
					column.text = me.aggregate.getAt(0)
						.header || config.text
				}
			} else {
				column = Ext.apply({
						text: item ? item.name : "",
						columns: columns
					},
					config || {})
			}
		}
		return column
	},
	buildPivotStore: function() {
		var me = this;
		if (Ext.isFunction(me.pivotStore.model.setFields)) {
			me.pivotStore.model.setFields(me.model)
		} else {
			me.pivotStore.model.replaceFields(me.model, true)
		}
		me.pivotStore.removeAll(true);
		Ext.Array.each(me.leftAxis.getTree(), me.addRecordToPivotStore,
			me);
		me.addGrandTotalsToPivotStore()
	},
	addGrandTotalsToPivotStore: function() {
		var me = this,
			totals = [];
		totals.push({
			title: me.textGrandTotalTpl,
			values: me.preparePivotStoreRecordData({
				key: me.grandTotalKey
			})
		});
		if (Ext.isFunction(me.onBuildTotals)) {
			me.onBuildTotals(totals)
		}
		me.fireEvent("buildtotals", me, totals);
		Ext.Array.forEach(totals,
			function(t) {
				if (Ext.isObject(t) && Ext.isObject(t.values)) {
					me.totals.push({
						title: t.title || "",
						record: me.pivotStore.add(t.values)[
							0]
					})
				}
			})
	},
	addRecordToPivotStore: function(item) {
		var me = this,
			record;
		if (!item.children) {
			record = me.pivotStore.add(me.preparePivotStoreRecordData(
				item));
			item.record = record[0];
			if (Ext.isFunction(me.onBuildRecord)) {
				me.onBuildRecord(record[0])
			}
			me.fireEvent("recordbuilt", me, record[0])
		} else {
			Ext.Array.each(item.children,
				function(child) {
					me.addRecordToPivotStore(child)
				})
		}
	},
	preparePivotStoreRecordData: function(group) {
		var me = this,
			data = {};
		data["id"] = group.key;
		Ext.apply(data, group.data || {});
		Ext.Array.each(me.model,
			function(field) {
				var result;
				if (field.col && field.agg) {
					result = me.results.get(group.key, field.col);
					if (result) {
						data[field.name] = result.getValue(
							field.agg)
					}
				}
			});
		if (me.viewLayoutType == "compact") {
			data[me.compactViewKey] = group.name
		}
		return data
	},
	getColumns: function() {
		return this.model
	},
	getColumnHeaders: function() {
		var me = this;
		if (!me.model) {
			me.buildModelAndColumns()
		} else {
			me.buildColumnHeaders(true)
		}
		return me.columns
	},
	isGroupRow: function(key) {
		var obj = this.leftAxis.findTreeElement("key", key);
		if (!obj) {
			return false
		}
		return (obj.node.children && obj.nodel.children.length == 0) ?
			0 : obj.level
	},
	isGroupCol: function(key) {
		var obj = this.topAxis.findTreeElement("key", key);
		if (!obj) {
			return false
		}
		return (obj.node.children && obj.node.children.length == 0) ?
			0 : obj.level
	},
	deprecated: {
		"6.0": {
			properties: {
				mztype: "type",
				mztypeLeftAxis: "leftAxisType",
				mztypeTopAxis: "topAxisType"
			}
		}
	}
});
Ext.define("Ext.pivot.matrix.Local", {
	alternateClassName: ["Mz.aggregate.matrix.Local"],
	extend: "Ext.pivot.matrix.Base",
	alias: "pivotmatrix.local",
	requires: ["Ext.pivot.matrix.Base", "Ext.pivot.axis.Local",
		"Ext.pivot.result.Local"
	],
	resultType: "local",
	leftAxisType: "local",
	topAxisType: "local",
	store: null,
	recordsPerJob: 1000,
	timeBetweenJobs: 2,
	onInitialize: function() {
		var me = this;
		me.localDelayedTask = new Ext.util.DelayedTask(me.delayedProcess,
			me);
		me.newRecordsDelayedTask = new Ext.util.DelayedTask(me.onOriginalStoreAddDelayed,
			me);
		me.updateRecordsDelayedTask = new Ext.util.DelayedTask(me.onOriginalStoreUpdateDelayed,
			me);
		me.callParent(arguments)
	},
	onReconfigure: function(config) {
		var me = this,
			store, newStore;
		if (config.store) {
			newStore = config.store
		} else {
			if (me.store) {
				if (me.store.isStore && !me.storeListeners) {
					store = me.store
				} else {
					newStore = me.store
				}
			}
		}
		if (newStore) {
			store = Ext.getStore(newStore || "");
			if (Ext.isEmpty(store) && Ext.isString(newStore)) {
				store = Ext.create(newStore)
			}
		}
		if (store && store.isStore) {
			Ext.destroy(me.storeListeners);
			if (me.store && me.store.autoDestroy && store != me.store) {
				Ext.destroy(me.store)
			}
			me.store = store;
			me.storeListeners = me.store.on({
				refresh: me.startProcess,
				beforeload: me.onOriginalStoreBeforeLoad,
				add: me.onOriginalStoreAdd,
				update: me.onOriginalStoreUpdate,
				remove: me.onOriginalStoreRemove,
				clear: me.startProcess,
				scope: me,
				destroyable: true
			})
		}
		me.callParent(arguments)
	},
	onDestroy: function() {
		var me = this;
		me.localDelayedTask.cancel();
		me.localDelayedTask = null;
		me.newRecordsDelayedTask.cancel();
		me.newRecordsDelayedTask = null;
		me.updateRecordsDelayedTask.cancel();
		me.updateRecordsDelayedTask = null;
		if (Ext.isArray(me.records)) {
			me.records.length = 0
		}
		me.records = null;
		Ext.destroy(me.storeListeners);
		if (me.store && me.store.isStore && me.store.autoDestroy) {
			Ext.destroy(me.store)
		}
		me.store = me.storeListeners = null;
		me.callParent(arguments)
	},
	onOriginalStoreBeforeLoad: function(store) {
		this.fireEvent("start", this)
	},
	onOriginalStoreAdd: function(store, records) {
		var me = this;
		me.newRecords = me.newRecords || [];
		me.newRecords = Ext.Array.merge(me.newRecords, Ext.Array.from(
			records));
		me.newRecordsDelayedTask.delay(100)
	},
	onOriginalStoreAddDelayed: function() {
		var me = this,
			i, records;
		records = Ext.Array.from(me.newRecords || []);
		for (i = 0; i < records.length; i++) {
			me.processRecord(records[i], i, records.length)
		}
		me.newRecords = [];
		me.leftAxis.tree = null;
		me.leftAxis.buildTree();
		me.topAxis.tree = null;
		me.topAxis.buildTree();
		me.recalculateResults(me.store, records)
	},
	onOriginalStoreUpdate: function(store, records) {
		var me = this;
		me.updateRecords = me.updateRecords || [];
		me.updateRecords = Ext.Array.merge(me.updateRecords, Ext.Array
			.from(records));
		me.updateRecordsDelayedTask.delay(100)
	},
	onOriginalStoreUpdateDelayed: function() {
		var me = this;
		me.recalculateResults(me.store, me.updateRecords);
		me.updateRecords.length = 0
	},
	onOriginalStoreRemove: function(store, record, index, isMove) {
		if (isMove) {
			return
		}
		this.startProcess()
	},
	isReallyDirty: function(store, records) {
		var found = true;
		records = Ext.Array.from(records);
		this.leftAxis.dimensions.each(function(dimension) {
			Ext.Array.forEach(records,
				function(record) {
					found = (record && record.isModel &&
						dimension.values.containsKey(
							record.get(dimension.dataIndex)
						));
					return found
				});
			return found
		});
		return !found
	},
	recalculateResults: function(store, records) {
		var me = this;
		if (me.isReallyDirty(store, records)) {
			me.startProcess();
			return
		}
		me.fireEvent("beforeupdate", me);
		me.results.calculate();
		Ext.Array.each(me.leftAxis.getTree(), me.updateRecordToPivotStore,
			me);
		me.updateGrandTotalsToPivotStore();
		me.fireEvent("afterupdate", me)
	},
	updateGrandTotalsToPivotStore: function() {
		var me = this,
			totals = [],
			i;
		if (me.totals.length <= 0) {
			return
		}
		totals.push({
			title: me.textGrandTotalTpl,
			values: me.preparePivotStoreRecordData({
				key: me.grandTotalKey
			})
		});
		if (Ext.isFunction(me.onBuildTotals)) {
			me.onBuildTotals(totals)
		}
		me.fireEvent("buildtotals", me, totals);
		if (me.totals.length === totals.length) {
			for (i = 0; i < me.totals.length; i++) {
				if (Ext.isObject(totals[i]) && Ext.isObject(totals[
					i].values) && (me.totals[i].record instanceof Ext
					.data.Model)) {
					delete(totals[i].values.id);
					me.totals[i].record.set(totals[i].values)
				}
			}
		}
	},
	updateRecordToPivotStore: function(item) {
		if (!item.children) {
			if (item.record) {
				item.record.set(this.preparePivotStoreRecordData(
					item))
			}
		} else {
			Ext.Array.each(item.children,
				function(child) {
					this.updateRecordToPivotStore(child)
				},
				this)
		}
	},
	startProcess: function() {
		var me = this;
		if (!me.store || (me.store && !me.store.isStore) || me.isDestroyed) {
			return
		}
		me.clearData();
		me.localDelayedTask.delay(50)
	},
	delayedProcess: function() {
		var me = this;
		me.fireEvent("start", me);
		me.records = me.store.getRange();
		if (me.records.length == 0) {
			me.endProcess();
			return
		}
		me.statusInProgress = false;
		me.processRecords(0)
	},
	processRecords: function(position) {
		var me = this,
			i = position,
			totalLength;
		if (me.isDestroyed) {
			return
		}
		totalLength = me.records.length;
		me.statusInProgress = true;
		while (i < totalLength && i < position + me.recordsPerJob &&
			me.statusInProgress) {
			me.processRecord(me.records[i], i, totalLength);
			i++
		}
		if (i >= totalLength) {
			me.statusInProgress = false;
			me.results.calculate();
			me.leftAxis.buildTree();
			me.topAxis.buildTree();
			if (me.filterApplied) {
				me.results.calculate()
			}
			me.records = null;
			me.endProcess();
			return
		}
		if (me.statusInProgress && totalLength > 0) {
			Ext.defer(me.processRecords, me.timeBetweenJobs, me, [i])
		}
	},
	processRecord: function(record, index, length) {
		var me = this,
			grandTotalKey = me.grandTotalKey,
			leftItems, topItems, i, j;
		leftItems = me.leftAxis.processRecord(record);
		topItems = me.topAxis.processRecord(record);
		if (leftItems && topItems) {
			me.results.add(grandTotalKey, grandTotalKey)
				.addRecord(record);
			for (i = 0; i < topItems.length; i++) {
				me.topAxis.addItem(topItems[i]);
				me.results.add(grandTotalKey, topItems[i].key)
					.addRecord(record)
			}
			for (i = 0; i < leftItems.length; i++) {
				me.leftAxis.addItem(leftItems[i]);
				me.results.add(leftItems[i].key, grandTotalKey)
					.addRecord(record);
				for (j = 0; j < topItems.length; j++) {
					me.results.add(leftItems[i].key, topItems[j].key)
						.addRecord(record)
				}
			}
		}
		me.fireEvent("progress", me, index + 1, length)
	},
	getRecordsByRowGroup: function(key) {
		var results = this.results.getByLeftKey(key),
			length = results.length,
			records = [],
			i;
		for (i = 0; i < length; i++) {
			records = Ext.Array.merge(records, results[i].records ||
				[])
		}
		return records
	},
	getRecordsByColGroup: function(key) {
		var results = this.results.getByTopKey(key),
			length = results.length,
			records = [],
			i;
		for (i = 0; i < length; i++) {
			records = Ext.Array.merge(records, results[i].records ||
				[])
		}
		return records
	},
	getRecordsByGroups: function(rowKey, colKey) {
		var result = this.results.get(rowKey, colKey);
		return (result ? result.records || [] : [])
	}
});
Ext.define("Ext.pivot.matrix.Remote", {
	alternateClassName: ["Mz.aggregate.matrix.Remote"],
	extend: "Ext.pivot.matrix.Base",
	alias: "pivotmatrix.remote",
	url: "",
	timeout: 3000,
	onBeforeRequest: Ext.emptyFn,
	onRequestException: Ext.emptyFn,
	onInitialize: function() {
		var me = this;
		me.remoteDelayedTask = new Ext.util.DelayedTask(me.delayedProcess,
			me);
		me.callParent(arguments)
	},
	startProcess: function() {
		var me = this;
		if (Ext.isEmpty(me.url)) {
			return
		}
		me.clearData();
		me.fireEvent("start", me);
		me.statusInProgress = false;
		me.remoteDelayedTask.delay(5)
	},
	delayedProcess: function() {
		var me = this,
			leftAxis = [],
			topAxis = [],
			aggregate = [],
			ret,
			params;
		me.leftAxis.dimensions.each(function(item) {
			leftAxis.push(item.serialize())
		});
		me.topAxis.dimensions.each(function(item) {
			topAxis.push(item.serialize())
		});
		me.aggregate.each(function(item) {
			aggregate.push(item.serialize())
		});
		params = {
			keysSeparator: me.keysSeparator,
			grandTotalKey: me.grandTotalKey,
			leftAxis: leftAxis,
			topAxis: topAxis,
			aggregate: aggregate
		};
		ret = me.fireEvent("beforerequest", me, params);
		if (ret !== false) {
			if (Ext.isFunction(me.onBeforeRequest)) {
				ret = me.onBeforeRequest(params)
			}
		}
		if (ret === false) {
			me.endProcess()
		} else {
			Ext.Ajax.request({
				url: me.url,
				timeout: me.timeout,
				jsonData: params,
				callback: me.processRemoteResults,
				scope: me
			})
		}
	},
	processRemoteResults: function(options, success, response) {
		var me = this,
			exception = !success,
			data = Ext.JSON.decode(response.responseText, true);
		if (success) {
			exception = (!data || !data["success"])
		}
		if (exception) {
			me.fireEvent("requestexception", me, response);
			if (Ext.isFunction(me.onRequestException)) {
				me.onRequestException(response)
			}
			me.endProcess();
			return
		}
		Ext.Array.each(Ext.Array.from(data.leftAxis || []),
			function(item) {
				if (Ext.isObject(item)) {
					me.leftAxis.addItem(item)
				}
			});
		Ext.Array.each(Ext.Array.from(data.topAxis || []),
			function(item) {
				if (Ext.isObject(item)) {
					me.topAxis.addItem(item)
				}
			});
		Ext.Array.each(Ext.Array.from(data.results || []),
			function(item) {
				if (Ext.isObject(item)) {
					var result = me.results.add(item.leftKey ||
						"", item.topKey || "");
					Ext.Object.each(item.values || {},
						result.addValue, result)
				}
			});
		me.endProcess()
	}
});
Ext.define("Ext.pivot.feature.PivotStore", {
	constructor: function(config) {
		Ext.apply(this, config);
		this.bindStore(config.store)
	},
	destroy: function() {
		var me = this;
		Ext.destroy(me.storeListeners);
		me.store = me.matrix = me.pivotFeature = null;
		me.storeInfo = me.storeListeners = me.store = null;
		me.callParent(arguments)
	},
	bindStore: function(store) {
		var me = this;
		if (me.store) {
			Ext.destroy(me.storeListeners);
			me.store = null
		}
		if (store) {
			me.storeListeners = store.on({
				pivotstoreremodel: me.processStore,
				scope: me,
				destroyable: true
			});
			me.store = store
		}
	},
	processStore: function() {
		if (!this.matrix) {
			return
		}
		var me = this,
			fn = me["processGroup" + Ext.String.capitalize(me.matrix
				.viewLayoutType)],
			fields = me.matrix.getColumns(),
			outputFn;
		me.store.model.replaceFields(fields, true);
		me.store.removeAll(true);
		me.store.suspendEvents(false);
		me.storeInfo = {};
		if (!Ext.isFunction(fn)) {
			fn = me.processGroupOutline
		}
		outputFn = Ext.Function.bind(fn, me);
		if (me.matrix.rowGrandTotalsPosition == "first") {
			me.processGrandTotal()
		}
		Ext.Array.each(me.matrix.leftAxis.getTree(),
			function(group, index, all) {
				me.store.add(outputFn({
					group: group,
					previousExpanded: (index > 0 ?
						all[index - 1].expanded :
						false)
				}))
			},
			me);
		if (me.matrix.rowGrandTotalsPosition == "last") {
			me.processGrandTotal()
		}
		me.store.resumeEvents();
		me.store.fireEvent("refresh", me.store)
	},
	processGroup: function(config) {
		var me = this,
			fn = me["processGroup" + Ext.String.capitalize(me.matrix
				.viewLayoutType)],
			outputFn;
		if (!Ext.isFunction(fn)) {
			fn = me.processGroupOutline
		}
		outputFn = Ext.Function.bind(fn, me);
		return outputFn(config)
	},
	createGridStoreRecord: function(values) {
		var me = this,
			data = me.matrix.preparePivotStoreRecordData(values ||
				{}),
			record;
		data.id = "";
		record = new me.store.model(data);
		if (Ext.isEmpty(values)) {
			Ext.Object.each(data,
				function(field) {
					if (field != "id") {
						record.set(field, null)
					}
				});
			record.commit()
		}
		record.isPlaceholder = true;
		return record
	},
	processGrandTotal: function() {
		var me = this,
			found = false,
			group = {
				key: me.matrix.grandTotalKey
			};
		Ext.Array.forEach(me.matrix.totals || [],
			function(total) {
				var record = total.record,
					i = me.matrix.leftAxis.dimensions.getCount();
				if (!(record instanceof Ext.data.Model)) {
					return
				}
				me.storeInfo[record.internalId] = {
					leftKey: group.key,
					rowStyle: "",
					rowClasses: [me.pivotFeature.gridMaster
						.clsGrandTotal, me.pivotFeature
						.summaryDataCls
					],
					rendererParams: {}
				};
				me.matrix.leftAxis.dimensions.each(function(
					column, index) {
					var key;
					if (me.matrix.viewLayoutType ==
						"compact" || index === 0) {
						if (me.matrix.viewLayoutType ==
							"compact") {
							key = me.matrix.compactViewKey;
							i = 1
						} else {
							key = column.getId()
						}
						record.set(key, total.title);
						record.commit(false, [key]);
						me.storeInfo[record.internalId]
							.rendererParams[key] = {
								fn: "groupOutlineRenderer",
								group: group,
								colspan: i,
								hidden: false,
								subtotalRow: true
							};
						found = true
					} else {
						me.storeInfo[record.internalId]
							.rendererParams[column.getId()] = {
								fn: "groupOutlineRenderer",
								group: group,
								colspan: 0,
								hidden: found,
								subtotalRow: true
							};
						i--
					}
					me.storeInfo[record.internalId].rendererParams[
						"topaxis"] = {
						fn: "topAxisRenderer"
					}
				});
				me.store.add(record)
			})
	},
	processGroupOutline: function(config) {
		var me = this,
			group = config["group"],
			results = [];
		if (group.record) {
			me.processRecordOutline({
				results: results,
				group: group
			})
		} else {
			me.processGroupOutlineWithChildren({
				results: results,
				group: group,
				previousExpanded: config.previousExpanded
			})
		}
		return results
	},
	processGroupOutlineWithChildren: function(config) {
		var me = this,
			group = config["group"],
			previousExpanded = config["previousExpanded"],
			hasSummaryData = false,
			record,
			i;
		if (!group.expanded || (group.expanded && me.matrix.rowSubTotalsPosition ==
			"first")) {
			hasSummaryData = true;
			record = me.createGridStoreRecord(group)
		} else {
			if (me.matrix.rowSubTotalsPosition == "last" || me.matrix
				.rowSubTotalsPosition == "none") {
				record = me.createGridStoreRecord();
				record.set(group.dimension.getId(), group.name)
			}
		}
		record.commit();
		me.processGroupHeaderRecordOutline({
			results: config.results,
			group: group,
			record: record,
			previousExpanded: previousExpanded,
			hasSummaryData: hasSummaryData
		});
		if (group.expanded) {
			if (group.children) {
				for (i = 0; i < group.children.length; i++) {
					if (group.children[i]["children"]) {
						me.processGroupOutlineWithChildren({
							results: config.results,
							group: group.children[i]
						})
					} else {
						me.processRecordOutline({
							results: config.results,
							group: group.children[i]
						})
					}
				}
			}
			if (me.matrix.rowSubTotalsPosition == "last") {
				record = me.createGridStoreRecord(group);
				record.set(group.dimension.getId(), group.getTextTotal());
				record.commit();
				me.processGroupHeaderRecordOutline({
					results: config.results,
					group: group,
					record: record,
					previousExpanded: previousExpanded,
					subtotalRow: true,
					hasSummaryData: true
				})
			}
		}
	},
	processGroupHeaderRecordOutline: function(config) {
		var me = this,
			group = config["group"],
			record = config["record"],
			previousExpanded = config["previousExpanded"],
			subtotalRow = config["subtotalRow"],
			hasSummaryData = config["hasSummaryData"],
			i = me.matrix.leftAxis.dimensions.getCount(),
			found = false;
		me.storeInfo[record.internalId] = {
			leftKey: group.key,
			rowStyle: "",
			rowClasses: [me.pivotFeature.gridMaster.clsGroupTotal,
				hasSummaryData ? me.pivotFeature.summaryDataCls :
				""
			],
			rendererParams: {}
		};
		me.matrix.leftAxis.dimensions.each(function(column, index) {
			if (column.getId() == group.dimension.getId()) {
				me.storeInfo[record.internalId].rendererParams[
					column.getId()] = {
					fn: "groupOutlineRenderer",
					group: group,
					colspan: i,
					hidden: false,
					previousExpanded: previousExpanded,
					subtotalRow: subtotalRow
				};
				found = true
			} else {
				me.storeInfo[record.internalId].rendererParams[
					column.getId()] = {
					fn: "groupOutlineRenderer",
					group: group,
					colspan: 0,
					hidden: found,
					previousExpanded: previousExpanded,
					subtotalRow: subtotalRow
				};
				i--
			}
		});
		me.storeInfo[record.internalId].rendererParams["topaxis"] = {
			fn: (hasSummaryData ? "topAxisRenderer" :
				"topAxisNoRenderer")
		};
		config.results.push(record)
	},
	processRecordOutline: function(config) {
		var me = this,
			group = config["group"],
			found = false,
			record = group.record;
		me.storeInfo[record.internalId] = {
			leftKey: group.key,
			rowStyle: "",
			rowClasses: [me.pivotFeature.rowCls, me.pivotFeature
				.summaryDataCls
			],
			rendererParams: {}
		};
		me.matrix.leftAxis.dimensions.each(function(column, index) {
			if (column.getId() == group.dimension.getId()) {
				found = true
			}
			me.storeInfo[record.internalId].rendererParams[
				column.getId()] = {
				fn: "recordOutlineRenderer",
				group: group,
				hidden: !found
			}
		});
		me.storeInfo[record.internalId].rendererParams["topaxis"] = {
			fn: "topAxisRenderer"
		};
		config.results.push(record)
	},
	processGroupCompact: function(config) {
		var me = this,
			group = config["group"],
			previousExpanded = config["previousExpanded"],
			results = [];
		if (group.record) {
			me.processRecordCompact({
				results: results,
				group: group
			})
		} else {
			me.processGroupCompactWithChildren({
				results: results,
				group: group,
				previousExpanded: previousExpanded
			})
		}
		return results
	},
	processGroupCompactWithChildren: function(config) {
		var me = this,
			group = config["group"],
			previousExpanded = config["previousExpanded"],
			hasSummaryData = false,
			record,
			i;
		if (!group.expanded || (group.expanded && me.matrix.rowSubTotalsPosition ==
			"first")) {
			hasSummaryData = true;
			record = me.createGridStoreRecord(group)
		} else {
			if (me.matrix.rowSubTotalsPosition == "last" || me.matrix
				.rowSubTotalsPosition == "none") {
				record = me.createGridStoreRecord();
				record.set(me.matrix.compactViewKey, group.name)
			}
		}
		record.commit();
		me.processGroupHeaderRecordCompact({
			results: config.results,
			group: group,
			record: record,
			previousExpanded: previousExpanded,
			hasSummaryData: hasSummaryData
		});
		if (group.expanded) {
			if (group.children) {
				for (i = 0; i < group.children.length; i++) {
					if (group.children[i]["children"]) {
						me.processGroupCompactWithChildren({
							results: config.results,
							group: group.children[i]
						})
					} else {
						me.processRecordCompact({
							results: config.results,
							group: group.children[i]
						})
					}
				}
			}
			if (me.matrix.rowSubTotalsPosition == "last") {
				record = me.createGridStoreRecord(group);
				record.set(me.matrix.compactViewKey, group.getTextTotal());
				record.commit();
				me.processGroupHeaderRecordCompact({
					results: config.results,
					group: group,
					record: record,
					previousExpanded: previousExpanded,
					subtotalRow: true,
					hasSummaryData: true
				})
			}
		}
	},
	processGroupHeaderRecordCompact: function(config) {
		var me = this,
			group = config["group"],
			record = config["record"],
			previousExpanded = config["previousExpanded"],
			subtotalRow = config["subtotalRow"],
			hasSummaryData = config["hasSummaryData"],
			i = me.matrix.leftAxis.dimensions.getCount(),
			found = false;
		me.storeInfo[record.internalId] = {
			leftKey: group.key,
			rowStyle: "",
			rowClasses: [me.pivotFeature.gridMaster.clsGroupTotal,
				hasSummaryData ? me.pivotFeature.summaryDataCls :
				""
			],
			rendererParams: {}
		};
		me.storeInfo[record.internalId].rendererParams[me.matrix.compactViewKey] = {
			fn: "groupCompactRenderer",
			group: group,
			colspan: 0,
			previousExpanded: previousExpanded,
			subtotalRow: subtotalRow
		};
		me.storeInfo[record.internalId].rendererParams["topaxis"] = {
			fn: (hasSummaryData ? "topAxisRenderer" :
				"topAxisNoRenderer")
		};
		config.results.push(record)
	},
	processRecordCompact: function(config) {
		var me = this,
			group = config["group"],
			found = false,
			record = me.createGridStoreRecord(group);
		me.storeInfo[record.internalId] = {
			leftKey: group.key,
			rowStyle: "",
			rowClasses: [me.pivotFeature.rowCls, me.pivotFeature
				.summaryDataCls
			],
			rendererParams: {}
		};
		me.storeInfo[record.internalId].rendererParams[me.matrix.compactViewKey] = {
			fn: "recordCompactRenderer",
			group: group
		};
		me.storeInfo[record.internalId].rendererParams["topaxis"] = {
			fn: "topAxisRenderer"
		};
		config.results.push(record)
	},
	doExpandCollapse: function(key, oldRecord) {
		var me = this,
			gridMaster = me.pivotFeature.gridMaster,
			group;
		group = me.matrix.leftAxis.findTreeElement("key", key);
		if (!group) {
			return
		}
		me.doExpandCollapseInternal(group, oldRecord);
		gridMaster.fireEvent((group.node.expanded ?
				"pivotgroupexpand" : "pivotgroupcollapse"),
			gridMaster, "row", group.node)
	},
	doExpandCollapseInternal: function(group, oldRecord) {
		var me = this,
			items, oldItems, startIdx, len;
		oldItems = me.processGroup({
			group: group.node,
			previousExpanded: false
		});
		group.node.expanded = !group.node.expanded;
		items = me.processGroup({
			group: group.node,
			previousExpanded: false
		});
		if (items.length && (startIdx = me.store.indexOf(oldRecord)) !==
			-1) {
			me.store.suspendEvents();
			if (group.node.expanded) {
				me.store.remove(me.store.getAt(startIdx));
				me.store.insert(startIdx, items);
				oldItems = [oldRecord]
			} else {
				len = oldItems.length;
				oldItems = me.store.getRange(startIdx, startIdx +
					len - 1);
				me.store.remove(oldItems);
				me.store.insert(startIdx, items)
			}
			me.removeStoreInfoData(oldItems);
			me.store.resumeEvents();
			me.store.fireEvent("replace", me.store, startIdx,
				oldItems, items)
		}
	},
	removeStoreInfoData: function(records) {
		Ext.Array.each(records,
			function(record) {
				if (this.storeInfo[record.internalId]) {
					delete this.storeInfo[record.internalId]
				}
			},
			this)
	}
});
Ext.define("Ext.pivot.feature.PivotEvents", {
	alternateClassName: ["Mz.pivot.feature.PivotEvents"],
	extend: "Ext.grid.feature.Feature",
	alias: "feature.pivotevents",
	requires: ["Ext.pivot.feature.PivotStore"],
	eventPrefix: "pivotcell",
	eventSelector: "." + Ext.baseCSSPrefix + "grid-cell",
	summaryDataCls: Ext.baseCSSPrefix + "pivot-summary-data",
	summaryDataSelector: "." + Ext.baseCSSPrefix + "pivot-summary-data",
	cellSelector: "." + Ext.baseCSSPrefix + "grid-cell",
	groupHeaderCls: Ext.baseCSSPrefix + "pivot-grid-group-header",
	groupHeaderCollapsibleCls: Ext.baseCSSPrefix +
		"pivot-grid-group-header-collapsible",
	summaryRowCls: Ext.baseCSSPrefix + "pivot-grid-group-total",
	summaryRowSelector: "." + Ext.baseCSSPrefix +
		"pivot-grid-group-total",
	grandSummaryRowCls: Ext.baseCSSPrefix + "pivot-grid-grand-total",
	grandSummaryRowSelector: "." + Ext.baseCSSPrefix +
		"pivot-grid-grand-total",
	init: function(grid) {
		var me = this,
			view = me.view,
			lockPartner;
		me.initEventsListeners();
		me.summaryRowSelector = "." + me.summaryRowCls;
		me.grandSummaryRowSelector = "." + me.grandSummaryRowCls;
		me.callParent(arguments);
		lockPartner = me.lockingPartner;
		if (lockPartner && lockPartner.dataSource) {
			me.dataSource = lockPartner.dataSource
		} else {
			me.dataSource = new Ext.pivot.feature.PivotStore({
				store: me.grid.store,
				pivotFeature: me
			})
		}
	},
	destroy: function() {
		var me = this;
		me.destroyEventsListeners();
		Ext.destroy(me.dataSource);
		me.view = me.grid = me.gridMaster = me.matrix = me.dataSource =
			null;
		me.callParent(arguments)
	},
	initEventsListeners: function() {
		var me = this;
		me.eventsViewListeners = me.view.on(Ext.apply({
				scope: me,
				destroyable: true
			},
			me.getViewListeners() || {}));
		me.gridListeners = me.grid.on(Ext.apply({
				scope: me,
				destroyable: true
			},
			me.getGridListeners() || {}))
	},
	getViewListeners: function() {
		var me = this,
			listeners = {
				afterrender: me.onViewAfterRender
			};
		listeners[me.eventPrefix + "click"] = me.onCellEvent;
		listeners[me.eventPrefix + "dblclick"] = me.onCellEvent;
		listeners[me.eventPrefix + "contextmenu"] = me.onCellEvent;
		return listeners
	},
	getGridListeners: Ext.emptyFn,
	destroyEventsListeners: function() {
		Ext.destroyMembers(this, "eventsViewListeners",
			"gridListeners");
		this.eventsViewListeners = this.gridListeners = null
	},
	onViewAfterRender: function() {
		var me = this;
		me.gridMaster = me.view.up("pivotgrid");
		me.matrix = me.gridMaster.getMatrix();
		me.dataSource.matrix = me.matrix
	},
	getRowId: function(record) {
		return this.view.id + "-record-" + record.internalId
	},
	getRecord: function(row) {
		return this.view.getRecord(row)
	},
	onCellEvent: function(view, tdCell, e) {
		var me = this,
			row = Ext.fly(tdCell)
			.findParent(me.summaryDataSelector) || Ext.fly(tdCell)
			.findParent(me.summaryRowSelector),
			record = me.getRecord(row),
			params = {
				grid: me.gridMaster,
				view: me.view,
				cellEl: tdCell
			},
			colIndex,
			ret,
			eventName,
			column,
			colDef,
			leftKey,
			topKey;
		if (!row || !record) {
			return false
		}
		leftKey = me.dataSource.storeInfo[record.internalId].leftKey;
		row = Ext.fly(row);
		if (row.hasCls(me.grandSummaryRowCls)) {
			eventName = "pivottotal"
		} else {
			if (row.hasCls(me.summaryRowCls)) {
				eventName = "pivotgroup"
			} else {
				if (row.hasCls(me.summaryDataCls)) {
					eventName = "pivotitem"
				}
			}
		}
		colIndex = Ext.getDom(tdCell)
			.getAttribute("data-columnid");
		column = me.getColumnHeaderById(colIndex);
		Ext.apply(params, {
			columnId: colIndex,
			column: column,
			leftKey: leftKey
		});
		if (Ext.fly(tdCell)
			.hasCls(me.groupHeaderCls)) {} else {
			if (column) {
				eventName += "cell";
				colDef = me.getTopAxisGroupByDataIndex(column.dataIndex);
				if (colDef) {
					topKey = colDef.col;
					Ext.apply(params, {
						topKey: topKey,
						dimensionId: colDef.agg
					})
				}
			}
		}
		ret = me.gridMaster.fireEvent(eventName + e.type, params, e);
		if (ret !== false && e.type == "click" && Ext.fly(tdCell)
			.hasCls(me.groupHeaderCollapsibleCls)) {
			me.dataSource.doExpandCollapse(leftKey, record);
			if (!me.view.bufferedRenderer && Ext.fly(me.getRowId(
				record))) {
				Ext.fly(me.getRowId(record))
					.scrollIntoView(me.view.el, false, false)
			}
		}
		return false
	},
	getColumnHeaderById: function(columnId) {
		var columns = this.view.getGridColumns(),
			i;
		for (i = 0; i < columns.length; i++) {
			if (columns[i].id === columnId) {
				return columns[i]
			}
		}
	},
	getTopAxisGroupByDataIndex: function(dataIndex) {
		var columns = this.gridMaster.matrix.getColumns(),
			i;
		for (i = 0; i < columns.length; i++) {
			if (columns[i].name === dataIndex) {
				return columns[i]
			}
		}
	}
});
Ext.define("Ext.pivot.feature.PivotView", {
	extend: "Ext.pivot.feature.PivotEvents",
	alias: "feature.pivotview",
	groupTitleCls: Ext.baseCSSPrefix + "pivot-grid-group-title",
	groupHeaderCollapsedCls: Ext.baseCSSPrefix +
		"pivot-grid-group-header-collapsed",
	tableCls: Ext.baseCSSPrefix + "grid-table",
	rowCls: Ext.baseCSSPrefix + "grid-row",
	dirtyCls: Ext.baseCSSPrefix + "grid-dirty-cell",
	outlineCellHiddenCls: Ext.baseCSSPrefix +
		"pivot-grid-outline-cell-hidden",
	outlineCellGroupExpandedCls: Ext.baseCSSPrefix +
		"pivot-grid-outline-cell-previous-expanded",
	compactGroupHeaderCls: Ext.baseCSSPrefix +
		"pivot-grid-group-header-compact",
	compactLayoutPadding: 25,
	outerTpl: ["{%", "var me = this.pivotViewFeature;",
		"if (!(me.disabled)) {", "me.setup();", "}",
		"this.nextTpl.applyOut(values, out, parent);", "%}", {
			priority: 200
		}
	],
	rowTpl: ["{%", "var me = this.pivotViewFeature;",
		"me.setupRowData(values.record, values.rowIndex, values);",
		"values.view.renderColumnSizer(values, out);",
		"this.nextTpl.applyOut(values, out, parent);",
		"me.resetRenderers();", "%}", {
			priority: 200,
			syncRowHeights: function(firstRow, secondRow) {
				var firstHeight, secondHeight;
				firstRow = Ext.fly(firstRow, "syncDest");
				if (firstRow) {
					firstHeight = firstRow.offsetHeight
				}
				secondRow = Ext.fly(secondRow, "sycSrc");
				if (secondRow) {
					secondHeight = secondRow.offsetHeight
				}
				if (firstRow && secondRow) {
					if (firstHeight > secondHeight) {
						Ext.fly(secondRow)
							.setHeight(firstHeight)
					} else {
						if (secondHeight > firstHeight) {
							Ext.fly(firstRow)
								.setHeight(secondHeight)
						}
					}
				}
			}
		}
	],
	cellTpl: ["{%", 'values.hideCell = values.tdAttr == "hidden";\n',
		"%}", '<tpl if="!hideCell">',
		'<td class="{tdCls}" role="{cellRole}" {tdAttr} {cellAttr:attributes}',
		' style="width:{column.cellWidth}px;<tpl if="tdStyle">{tdStyle}</tpl>"',
		' tabindex="-1" data-columnid="{[values.column.getItemId()]}">',
		'<div {unselectableAttr} class="' + Ext.baseCSSPrefix +
		'grid-cell-inner {innerCls}" ',
		'style="text-align:{align};<tpl if="style">{style}</tpl>" ',
		"{cellInnerAttr:attributes}>{value}</div>", "</td>",
		"</tpl>", {
			priority: 0
		}
	],
	rtlCellTpl: ["{%", 'values.hideCell = values.tdAttr == "hidden";\n',
		"%}", '<tpl if="!hideCell">',
		'<td class="{tdCls}" role="{cellRole}" {tdAttr} {cellAttr:attributes}',
		' style="width:{column.cellWidth}px;<tpl if="tdStyle">{tdStyle}</tpl>"',
		' tabindex="-1" data-columnid="{[values.column.getItemId()]}">',
		'<div {unselectableAttr} class="' + Ext.baseCSSPrefix +
		'grid-cell-inner {innerCls}" ',
		'style="text-align:{align};<tpl if="style">{style}</tpl>" ',
		"{cellInnerAttr:attributes}>{value}</div>", "</td>",
		"</tpl>", {
			priority: 200,
			rtlAlign: {
				right: "left",
				left: "right",
				center: "center"
			},
			getAlign: function(align) {
				return this.rtlAlign[align]
			}
		}
	],
	init: function(grid) {
		var me = this,
			view = me.view;
		me.callParent(arguments);
		view.addTpl(Ext.XTemplate.getTpl(me, "outerTpl"))
			.pivotViewFeature = me;
		view.addRowTpl(Ext.XTemplate.getTpl(me, "rowTpl"))
			.pivotViewFeature = me;
		view.preserveScrollOnRefresh = true;
		if (view.bufferedRenderer) {
			view.bufferedRenderer.variableRowHeight = true
		} else {
			grid.variableRowHeight = view.variableRowHeight = true
		}
	},
	destroy: function() {
		this.columns = null;
		this.callParent(arguments)
	},
	setup: function() {
		this.columns = this.view.getGridColumns()
	},
	isRTL: function() {
		var me = this,
			grid = me.gridMaster || me.grid;
		if (Ext.isFunction(grid.isLocalRtl)) {
			return grid.isLocalRtl()
		}
		return false
	},
	getGridListeners: function() {
		var me = this;
		return Ext.apply(me.callParent(arguments) || {}, {
			beforerender: me.onBeforeGridRendered
		})
	},
	onBeforeGridRendered: function(grid) {
		var me = this;
		if (me.isRTL()) {
			me.view.addCellTpl(Ext.XTemplate.getTpl(me,
				"rtlCellTpl"))
		} else {
			me.view.addCellTpl(Ext.XTemplate.getTpl(me, "cellTpl"))
		}
	},
	vetoEvent: function(record, row, rowIndex, e) {
		if (e.type !== "mouseover" && e.type !== "mouseout" && e.type !==
			"mouseenter" && e.type !== "mouseleave" && e.getTarget(
				this.eventSelector)) {
			return false
		}
	},
	setupRowData: function(record, idx, rowValues) {
		var storeInfo = this.dataSource.storeInfo[record.internalId],
			rendererParams = storeInfo ? storeInfo.rendererParams :
			{};
		rowValues.rowClasses.length = 0;
		Ext.Array.insert(rowValues.rowClasses, 0, storeInfo ?
			storeInfo.rowClasses : []);
		this.setRenderers(rendererParams)
	},
	setRenderers: function(rendererParams) {
		Ext.Array.each(this.columns,
			function(column) {
				if (Ext.isDefined(rendererParams[column.dataIndex])) {
					column.savedRenderer = column.renderer;
					column.renderer = this[rendererParams[
						column.dataIndex].fn](Ext.apply({
							renderer: column.savedRenderer
						},
						rendererParams[column.dataIndex]
					))
				} else {
					if (Ext.isDefined(rendererParams["topaxis"])) {
						column.savedRenderer = column.renderer;
						column.renderer = this[rendererParams[
							"topaxis"].fn](Ext.apply({
								renderer: column.savedRenderer
							},
							rendererParams[column.dataIndex]
						))
					}
				}
			},
			this)
	},
	resetRenderers: function() {
		Ext.Array.each(this.columns,
			function(column) {
				if (Ext.isDefined(column.savedRenderer)) {
					column.renderer = column.savedRenderer;
					delete column.savedRenderer
				}
			})
	},
	groupOutlineRenderer: function(config) {
		var me = this,
			prevRenderer = config["renderer"],
			group = config["group"],
			colspan = config["colspan"],
			hidden = config["hidden"],
			previousExpanded = config["previousExpanded"],
			subtotalRow = config["subtotalRow"];
		return function(value, metaData, record, rowIndex, colIndex,
			store, view) {
			if (Ext.isFunction(prevRenderer)) {
				value = prevRenderer.apply(this, arguments)
			}
			value = me.encodeValue(value, group);
			if (colspan > 0) {
				metaData.tdAttr = 'colspan = "' + colspan + '"';
				metaData.tdCls = me.groupHeaderCls;
				if (!subtotalRow) {
					metaData.tdCls += " " + me.groupHeaderCollapsibleCls;
					if (!group.expanded) {
						metaData.tdCls += " " + me.groupHeaderCollapsedCls
					}
					if (previousExpanded) {
						metaData.tdCls += " " + me.outlineCellGroupExpandedCls
					}
				}
				return '<div class="' + me.groupTitleCls + '">' +
					value + "</div>"
			}
			if (hidden) {
				metaData.tdAttr = "hidden"
			}
			metaData.tdCls = me.outlineCellHiddenCls;
			return ""
		}
	},
	recordOutlineRenderer: function(config) {
		var me = this,
			prevRenderer = config["renderer"],
			group = config["group"],
			hidden = config["hidden"];
		return function(value, metaData, record, rowIndex, colIndex,
			store, view) {
			if (Ext.isFunction(prevRenderer)) {
				value = prevRenderer.apply(this, arguments)
			}
			value = me.encodeValue(value, group);
			if (hidden) {
				metaData.tdCls = me.outlineCellHiddenCls;
				return ""
			}
			metaData.tdCls = me.groupHeaderCls + " " + me.groupTitleCls;
			return value
		}
	},
	groupCompactRenderer: function(config) {
		var me = this,
			prevRenderer = config["renderer"],
			group = config["group"],
			colspan = config["colspan"],
			previousExpanded = config["previousExpanded"],
			subtotalRow = config["subtotalRow"];
		return function(value, metaData, record, rowIndex, colIndex,
			store, view) {
			if (Ext.isFunction(prevRenderer)) {
				value = prevRenderer.apply(this, arguments)
			}
			value = me.encodeValue(value, group);
			if (group.level > 0) {
				metaData.style = (me.isRTL() ? "margin-right: " :
					"margin-left: ") + (me.compactLayoutPadding *
					group.level) + "px;"
			}
			metaData.tdCls = me.groupHeaderCls + " " + me.compactGroupHeaderCls;
			if (!subtotalRow) {
				metaData.tdCls += " " + me.groupHeaderCollapsibleCls;
				if (!group.expanded) {
					metaData.tdCls += " " + me.groupHeaderCollapsedCls
				}
				if (previousExpanded) {
					metaData.tdCls += " " + me.outlineCellGroupExpandedCls
				}
			}
			return '<div class="' + me.groupTitleCls + '">' +
				value + "</div>"
		}
	},
	recordCompactRenderer: function(config) {
		var me = this,
			prevRenderer = config["renderer"],
			group = config["group"];
		return function(value, metaData, record, rowIndex, colIndex,
			store, view) {
			if (Ext.isFunction(prevRenderer)) {
				value = prevRenderer.apply(this, arguments)
			}
			value = me.encodeValue(value, group);
			if (group.level > 0) {
				metaData.style = (me.isRTL() ? "margin-right: " :
					"margin-left: ") + (me.compactLayoutPadding *
					group.level) + "px;"
			}
			metaData.tdCls = me.groupHeaderCls + " " + me.groupTitleCls +
				" " + me.compactGroupHeaderCls;
			return value
		}
	},
	topAxisNoRenderer: function(config) {
		return function(value, metaData, record, rowIndex, colIndex,
			store, view) {
			return ""
		}
	},
	topAxisRenderer: function(config) {
		var me = this,
			prevRenderer = config["renderer"];
		return function(value, metaData, record, rowIndex, colIndex,
			store, view) {
			var hideValue = (value === 0 && me.gridMaster.showZeroAsBlank);
			if (Ext.isFunction(prevRenderer)) {
				value = prevRenderer.apply(this, arguments)
			}
			return hideValue ? "" : value
		}
	},
	encodeValue: function(value, group) {
		return value
	}
});
Ext.define("Ext.pivot.Grid", {
	extend: "Ext.grid.Panel",
	alternateClassName: ["Mz.pivot.Grid", "Mz.pivot.Table"],
	xtype: ["pivotgrid", "mzpivotgrid"],
	requires: ["Ext.pivot.matrix.Local", "Ext.pivot.matrix.Remote",
		"Ext.pivot.feature.PivotView", "Ext.util.DelayedTask",
		"Ext.data.ArrayStore"
	],
	subGridXType: "gridpanel",
	matrixConfig: null,
	enableLoadMask: true,
	enableLocking: false,
	enableColumnSort: true,
	columnLines: true,
	viewLayoutType: "outline",
	rowSubTotalsPosition: "first",
	rowGrandTotalsPosition: "last",
	colSubTotalsPosition: "last",
	colGrandTotalsPosition: "last",
	textTotalTpl: "合计 ({name})",
	textGrandTotalTpl: "Grand total",
	leftAxis: null,
	topAxis: null,
	aggregate: null,
	clsGroupTotal: Ext.baseCSSPrefix + "pivot-grid-group-total",
	clsGrandTotal: Ext.baseCSSPrefix + "pivot-grid-grand-total",
	startRowGroupsCollapsed: true,
	startColGroupsCollapsed: true,
	showZeroAsBlank: false,
	stateEvents: ["pivotgroupexpand", "pivotgroupcollapse", "pivotdone"],
	isPivotGrid: true,
	initComponent: function() {
		var me = this;
		me.columns = [];
		me.preInitialize();
		me.callParent(arguments);
		me.postInitialize()
	},
	preInitialize: function() {
		var me = this;
		me.features = [{
			id: "group",
			ftype: "pivotview",
			summaryRowCls: me.clsGroupTotal,
			grandSummaryRowCls: me.clsGrandTotal
		}];
		me.addCls(Ext.baseCSSPrefix + "pivot-grid");
		if (me.store) {
			me.originalStore = me.store
		}
		me.store = Ext.create("Ext.data.ArrayStore", {
			fields: []
		});
		me.enableColumnMove = false;
		me.delayedTask = new Ext.util.DelayedTask(me.refreshView,
			me)
	},
	postInitialize: function() {
		var me = this,
			matrixConfig = {},
			headerListener = {
				headerclick: me.onHeaderClick,
				scope: me,
				destroyable: true
			};
		if (me.enableLocking) {
			me.lockedHeaderCtListeners = me.getView()
				.lockedView.getHeaderCt()
				.on(headerListener);
			me.headerCtListeners = me.getView()
				.normalView.getHeaderCt()
				.on(headerListener)
		} else {
			me.headerCtListeners = me.getView()
				.getHeaderCt()
				.on(headerListener)
		}
		Ext.apply(matrixConfig, {
			leftAxis: me.leftAxis,
			topAxis: me.topAxis,
			aggregate: me.aggregate,
			showZeroAsBlank: me.showZeroAsBlank,
			textTotalTpl: me.textTotalTpl,
			textGrandTotalTpl: me.textGrandTotalTpl,
			viewLayoutType: me.viewLayoutType,
			rowSubTotalsPosition: me.rowSubTotalsPosition,
			rowGrandTotalsPosition: me.rowGrandTotalsPosition,
			colSubTotalsPosition: me.colSubTotalsPosition,
			colGrandTotalsPosition: me.colGrandTotalsPosition
		});
		Ext.applyIf(matrixConfig, me.matrixConfig || {});
		Ext.applyIf(matrixConfig, {
			type: "local"
		});
		if (matrixConfig.type == "local" && me.originalStore) {
			Ext.applyIf(matrixConfig, {
				store: me.originalStore
			})
		}
		me.matrix = Ext.Factory.pivotmatrix(matrixConfig);
		me.matrixListeners = me.matrix.on({
			cleardata: me.onMatrixClearData,
			start: me.onMatrixProcessStart,
			progress: me.onMatrixProcessProgress,
			done: me.onMatrixDataReady,
			beforeupdate: me.onMatrixBeforeUpdate,
			afterupdate: me.onMatrixAfterUpdate,
			scope: me,
			destroyable: true
		});
		me.matrixRelayedListeners = me.relayEvents(me.matrix, [
			"start", "progress", "done", "modelbuilt",
			"columnsbuilt", "recordbuilt", "buildtotals",
			"storebuilt", "beforerequest",
			"requestexception"
		], "pivot")
	},
	destroy: function() {
		var me = this;
		me.delayedTask.cancel();
		Ext.destroy(me.matrixRelayedListeners, me.matrixListeners,
			me.headerCtListeners, me.lockedHeaderCtListeners);
		Ext.destroy(me.matrix, me.delayedTask, me.originalStore);
		me.matrixRelayedListeners = me.matrixListeners = me.headerCtListeners =
			me.lockedHeaderCtListeners = null;
		me.matrix = me.delayedTask = me.originalStore = null;
		me.callParent(arguments);
		Ext.destroy(me.store);
		me.store = null
	},
	afterRender: function() {
		this.reconfigurePivot();
		this.callParent(arguments)
	},
	refreshView: function() {
		var me = this,
			columns;
		if (me.scheduledReconfigure === true) {
			me.scheduledReconfigure = false;
			columns = me.getMatrix()
				.getColumnHeaders();
			me.preparePivotColumns(columns);
			me.restorePivotColumnsState(columns);
			me.reconfigure(undefined, columns)
		}
		me.store.fireEvent("pivotstoreremodel", me)
	},
	onMatrixClearData: function() {
		var me = this;
		me.store.removeAll(true);
		if (!me.expandedItemsState) {
			me.lastColumnsState = null
		}
		me.sortedColumn = null
	},
	onMatrixProcessStart: function() {
		if (this.enableLoadMask) {
			this.setLoading(true)
		}
	},
	onMatrixProcessProgress: function(matrix, index, length) {
		var me = this,
			percent = ((index || 0.1) * 100) / (length || 0.1),
			pEl;
		if (me.loadMask) {
			if (me.loadMask.msgTextEl) {
				pEl = me.loadMask.msgTextEl
			} else {
				if (me.loadMask.msgEl) {
					pEl = me.loadMask.msgEl
				}
			}
			if (pEl) {
				pEl.update(Ext.util.Format.number(percent, "0") +
					"%")
			}
		}
	},
	onMatrixBeforeUpdate: function() {
		this.store.suspendEvents()
	},
	onMatrixAfterUpdate: function() {
		var me = this;
		me.store.resumeEvents();
		me.store.fireEvent("pivotstoreremodel")
	},
	onMatrixDataReady: function() {
		var me = this,
			cols = me.matrix.getColumnHeaders(),
			stateApplied = false;
		if (me.enableLoadMask) {
			me.setLoading(false)
		}
		if (me.expandedItemsState) {
			me.matrix.leftAxis.items.each(function(item) {
				if (Ext.Array.indexOf(me.expandedItemsState[
					"rows"], item.key) >= 0) {
					item.expanded = true;
					stateApplied = true
				}
			});
			me.matrix.topAxis.items.each(function(item) {
				if (Ext.Array.indexOf(me.expandedItemsState[
					"cols"], item.key) >= 0) {
					item.expanded = true;
					stateApplied = true
				}
			});
			if (stateApplied) {
				cols = me.matrix.getColumnHeaders();
				delete me.expandedItemsState
			}
		} else {
			me.doExpandCollapseTree(me.matrix.leftAxis.getTree(), !
				me.startRowGroupsCollapsed);
			me.doExpandCollapseTree(me.matrix.topAxis.getTree(), !
				me.startColGroupsCollapsed);
			cols = me.matrix.getColumnHeaders()
		}
		me.preparePivotColumns(cols);
		me.restorePivotColumnsState(cols);
		me.reconfigure(undefined, cols);
		if (!Ext.isEmpty(me.sortedColumn)) {
			me.matrix.leftAxis.sortTreeByField(me.sortedColumn.dataIndex,
				me.sortedColumn.direction)
		}
		me.store.fireEvent("pivotstoreremodel", me);
		if (!Ext.isEmpty(me.sortedColumn)) {
			me.updateColumnSortState(me.sortedColumn.dataIndex, me.sortedColumn
				.direction)
		}
	},
	preparePivotColumns: function(columns) {
		var me = this,
			defaultColConfig = {
				menuDisabled: true,
				sortable: false,
				lockable: false
			},
			colCount = columns.length,
			i,
			column;
		for (i = 0; i < colCount; i++) {
			column = columns[i];
			column.cls = column.cls || "";
			Ext.apply(column, defaultColConfig);
			if (column.leftAxis) {
				column.locked = me.enableLocking
			}
			if (column.subTotal) {
				column.cls = column.tdCls = me.clsGroupTotal
			}
			if (column.grandTotal) {
				column.cls = column.tdCls = me.clsGrandTotal
			}
			if (!column.xexpanded) {
				column.cls += " " + Ext.baseCSSPrefix +
					"grid-row-collapsed"
			}
			if (column.xcollapsible) {
				column.text = Ext.String.format('<span class="' +
					Ext.baseCSSPrefix +
					'grid-row-expander" style="padding-left: 13px">{0}</span>',
					column.text)
			}
			if (Ext.isEmpty(column.columns)) {
				if (column.dimension) {
					column.renderer = column.dimension ? column.dimension
						.renderer : false;
					column.align = column.dimension.align;
					if (column.dimension.flex > 0) {
						column.flex = column.flex || column.dimension
							.flex
					} else {
						column.width = column.width || column.dimension
							.width
					}
				}
			} else {
				me.preparePivotColumns(column.columns)
			}
		}
	},
	reconfigurePivot: function(config) {
		var me = this,
			props = Ext.clone(me.getStateProperties()),
			i;
		props.push("startRowGroupsCollapsed",
			"startColGroupsCollapsed", "showZeroAsBlank");
		config = config || {};
		for (i = 0; i < props.length; i++) {
			if (!config.hasOwnProperty(props[i])) {
				if (me[props[i]]) {
					config[props[i]] = me[props[i]]
				}
			} else {
				me[props[i]] = config[props[i]]
			}
		}
		me.getMatrix()
			.reconfigure(config)
	},
	getMatrix: function() {
		return this.matrix
	},
	doExpandCollapseTree: function(tree, expanded) {
		var i;
		for (i = 0; i < tree.length; i++) {
			tree[i].expanded = expanded;
			if (tree[i].children) {
				this.doExpandCollapseTree(tree[i].children,
					expanded)
			}
		}
	},
	doExpandCollapse: function(type, groupId, state, includeChildren) {
		var me = this,
			item;
		if (!me.matrix) {
			return
		}
		item = (type == "row" ? me.matrix.leftAxis : me.matrix.topAxis)[
			"findTreeElement"]("key", groupId);
		if (!item) {
			return
		}
		state = Ext.isDefined(state) ? state : !item.node.expanded;
		if (includeChildren === true) {
			me.doExpandCollapseTree([item.node], state)
		} else {
			item.node.expanded = state
		}
		if (type == "col") {
			me.scheduledReconfigure = true
		}
		me.refreshView();
		me.fireEvent((item.node.expanded ? "pivotgroupexpand" :
			"pivotgroupcollapse"), me, type, item.node)
	},
	expandRow: function(leftAxisItemKey, includeChildren) {
		this.doExpandCollapse("row", leftAxisItemKey, true,
			includeChildren)
	},
	collapseRow: function(leftAxisItemKey, includeChildren) {
		this.doExpandCollapse("row", leftAxisItemKey, false,
			includeChildren)
	},
	expandCol: function(topAxisItemKey, includeChildren) {
		this.doExpandCollapse("col", topAxisItemKey, true,
			includeChildren)
	},
	collapseCol: function(topAxisItemKey, includeChildren) {
		this.doExpandCollapse("col", topAxisItemKey, false,
			includeChildren)
	},
	expandAll: function() {
		var me = this;
		me.expandAllColumns();
		me.expandAllRows()
	},
	expandAllRows: function() {
		var me = this;
		if (!me.getMatrix()) {
			return
		}
		me.doExpandCollapseTree(me.getMatrix()
			.leftAxis.getTree(), true);
		me.delayedTask.delay(10)
	},
	expandAllColumns: function() {
		var me = this;
		if (!me.getMatrix()) {
			return
		}
		me.doExpandCollapseTree(me.getMatrix()
			.topAxis.getTree(), true);
		me.scheduledReconfigure = true;
		me.delayedTask.delay(10)
	},
	collapseAll: function() {
		var me = this;
		me.collapseAllRows();
		me.collapseAllColumns()
	},
	collapseAllRows: function() {
		var me = this;
		if (!me.getMatrix()) {
			return
		}
		me.doExpandCollapseTree(me.getMatrix()
			.leftAxis.getTree(), false);
		me.delayedTask.delay(10)
	},
	collapseAllColumns: function() {
		var me = this;
		if (!me.getMatrix()) {
			return
		}
		me.doExpandCollapseTree(me.getMatrix()
			.topAxis.getTree(), false);
		me.scheduledReconfigure = true;
		me.delayedTask.delay(10)
	},
	setStore: function(store) {
		this.reconfigurePivot({
			store: store
		})
	},
	getStore: function() {
		var me = this,
			matrix = me.getMatrix();
		return ((matrix instanceof Ext.pivot.matrix.Local) ? matrix
			.store : me.originalStore) || me.store
	},
	getPivotStore: function() {
		return this.store
	},
	getTopAxisItem: function(column) {
		var me = this,
			matrix = me.getMatrix(),
			columns = matrix.getColumns(),
			key,
			i;
		if (!column) {
			return null
		}
		for (i = 0; i < columns.length; i++) {
			if (columns[i].name === column.dataIndex) {
				key = columns[i].col;
				break
			}
		}
		return Ext.isEmpty(key) ? null : matrix.topAxis.items.getByKey(
			key)
	},
	getLeftAxisItem: function(record) {
		var me = this,
			view = me.getView(),
			info,
			feature;
		if (!record) {
			return null
		}
		view = view.normalView || view;
		feature = view.getFeature("group");
		if (!feature) {
			return null
		}
		info = feature.dataSource.storeInfo[record.internalId];
		return info ? me.getMatrix()
			.leftAxis.items.getByKey(info.leftKey) : null
	},
	onHeaderClick: function(ct, column, e) {
		var me = this,
			columns, el, sortState = (column.sortState ? (column.sortState ==
				"ASC" ? "DESC" : "ASC") : "ASC");
		if (!me.enableColumnSort) {
			return
		}
		if (!column.xexpandable) {
			if (e) {
				e.stopEvent()
			}
			if ((column.leftAxis || column.topAxis) && !Ext.isEmpty(
				column.dataIndex)) {
				if (me.getMatrix()
					.leftAxis.sortTreeByField(column.dataIndex,
						sortState)) {
					me.refreshView();
					me.updateColumnSortState(column, sortState)
				}
			}
			return false
		}
		me.doExpandCollapse("col", column.key);
		if (e) {
			e.stopEvent()
		}
	},
	updateColumnSortState: function(column, sortState) {
		if (Ext.isString(column)) {
			column = this.down('[dataIndex="' + column + '"]')
		}
		if (!column) {
			return
		}
		column.setSortState(new Ext.util.Sorter({
			direction: sortState,
			property: "dummy"
		}));
		column.sortState = sortState;
		this.sortedColumn = {
			dataIndex: column.dataIndex,
			direction: sortState
		}
	},
	getStateProperties: function() {
		return ["viewLayoutType", "rowSubTotalsPosition",
			"rowGrandTotalsPosition", "colSubTotalsPosition",
			"colGrandTotalsPosition", "aggregate", "leftAxis",
			"topAxis", "enableColumnSort", "sortedColumn"
		]
	},
	applyState: function(state) {
		var me = this,
			props = me.getStateProperties(),
			i;
		for (i = 0; i < props.length; i++) {
			if (state[props[i]]) {
				me[props[i]] = state[props[i]]
			}
		}
		if (state["expandedItems"]) {
			me.expandedItemsState = state["expandedItems"]
		}
		me.lastColumnsState = state["pivotcolumns"] || {};
		if (me.rendered) {
			me.reconfigurePivot()
		}
	},
	getState: function() {
		var me = this,
			state = {},
			props = me.getStateProperties(),
			i;
		for (i = 0; i < props.length; i++) {
			state[props[i]] = me[props[i]]
		}
		state["expandedItems"] = {
			cols: [],
			rows: []
		};
		me.matrix.leftAxis.items.each(function(item) {
			if (item.expanded) {
				state["expandedItems"]["rows"].push(item.key)
			}
		});
		me.matrix.topAxis.items.each(function(item) {
			if (item.expanded) {
				state["expandedItems"]["cols"].push(item.key)
			}
		});
		me.matrix.leftAxis.dimensions.each(function(item, index) {
			state["leftAxis"][index]["id"] = item.getId()
		});
		state["pivotcolumns"] = me.getPivotColumnsState();
		return state
	},
	getPivotColumnsState: function() {
		var me = this,
			i, cols;
		if (!me.lastColumnsState) {
			cols = me.getDataIndexColumns(me.getMatrix()
				.getColumnHeaders());
			me.lastColumnsState = {};
			for (i = 0; i < cols.length; i++) {
				if (cols[i].dataIndex) {
					me.lastColumnsState[cols[i].dataIndex] = {
						width: cols[i].width,
						flex: cols[i].flex || 0
					}
				}
			}
		}
		cols = me.getView()
			.getGridColumns();
		for (i = 0; i < cols.length; i++) {
			if (cols[i].dataIndex) {
				me.lastColumnsState[cols[i].dataIndex] = {
					width: cols[i].rendered ? cols[i].getWidth() :
						cols[i].width,
					flex: cols[i].flex || 0
				}
			}
		}
		return me.lastColumnsState
	},
	getDataIndexColumns: function(columns) {
		var cols = [],
			i;
		for (i = 0; i < columns.length; i++) {
			if (columns[i].dataIndex) {
				cols.push(columns[i].dataIndex)
			} else {
				if (Ext.isArray(columns[i].columns)) {
					cols = Ext.Array.merge(cols, this.getDataIndexColumns(
						columns[i].columns))
				}
			}
		}
		return cols
	},
	restorePivotColumnsState: function(columns) {
		this.parsePivotColumnsState(this.getPivotColumnsState(),
			columns)
	},
	parsePivotColumnsState: function(state, columns) {
		var item, i;
		if (!columns) {
			return
		}
		for (i = 0; i < columns.length; i++) {
			item = state[columns[i].dataIndex];
			if (item) {
				if (item.flex) {
					columns[i].flex = item.flex
				} else {
					if (item.width) {
						columns[i].width = item.width
					}
				}
			}
			this.parsePivotColumnsState(state, columns[i].columns)
		}
	}
});
Ext.define("Ext.pivot.plugin.configurator.FilterLabelWindow", {
	extend: "Ext.window.Window",
	requires: ["Ext.form.Panel", "Ext.form.FieldContainer",
		"Ext.form.field.Text", "Ext.form.field.Hidden",
		"Ext.form.field.ComboBox", "Ext.layout.container.HBox"
	],
	modal: true,
	closeAction: "destroy",
	titleText: "Label filter ({0})",
	fieldText: "Show items for which the label",
	caseSensitiveText: "Case sensitive",
	initComponent: function() {
		var me = this,
			items = [];
		items = me.filterFields || [];
		items.push({
			xtype: "combo",
			editable: false,
			queryMode: "local",
			valueField: "value",
			store: me.store,
			name: "operator",
			listeners: {
				change: function(combo, newValue) {
					var me = this,
						hidden = me.isOperatorBetween(
							newValue);
					me.down("#fValue")
						.setVisible(!hidden);
					me.down("#fValue")
						.allowBlank = hidden;
					me.down("#fFrom")
						.setVisible(hidden);
					me.down("#fFrom")
						.allowBlank = !hidden;
					me.down("#fTo")
						.setVisible(hidden);
					me.down("#fTo")
						.allowBlank = !hidden
				},
				scope: me
			}
		}, {
			itemId: "fValue",
			xtype: "textfield",
			margin: "0 0 0 5",
			name: "value"
		}, {
			itemId: "fFrom",
			xtype: "textfield",
			margin: "0 0 0 5",
			name: "from"
		}, {
			itemId: "fTo",
			xtype: "textfield",
			margin: "0 0 0 5",
			name: "to"
		});
		Ext.apply(me, {
			title: Ext.String.format(me.titleText, me.title),
			layout: "fit",
			items: [{
				xtype: "form",
				bodyPadding: 5,
				items: [{
						xtype: "hidden",
						name: "type"
					},
					{
						xtype: "fieldcontainer",
						labelSeparator: "",
						fieldLabel: me.fieldText,
						labelAlign: "top",
						layout: {
							type: "hbox",
							align: "stretch"
						},
						defaults: {
							allowBlank: false,
							flex: 1
						},
						items: items
					},
					{
						xtype: "checkbox",
						boxLabel: me.caseSensitiveText,
						name: "caseSensitive"
					}
				]
			}],
			buttons: [{
					text: Ext.Msg.buttonText.ok,
					handler: me.applyFilter,
					scope: me
				},
				{
					text: Ext.Msg.buttonText.cancel,
					handler: me.cancelFilter,
					scope: me
				}
			]
		});
		me.callParent(arguments)
	},
	applyFilter: function() {
		var form = this.down("form")
			.getForm(),
			filter;
		if (form.isValid()) {
			filter = form.getValues();
			if (this.isOperatorBetween(filter.operator)) {
				filter.value = [filter.from, filter.to]
			}
			delete(filter.from);
			delete(filter.to);
			filter.caseSensitive = (filter.caseSensitive === "on");
			filter.topSort = (filter.topSort === "on");
			this.fireEvent("filter", this, filter)
		}
	},
	cancelFilter: function() {
		this.close()
	},
	isOperatorBetween: function(operator) {
		return Ext.Array.indexOf(["between", "not between"],
			operator) >= 0
	}
});
Ext.define("Ext.pivot.plugin.configurator.FilterValueWindow", {
	extend: "Ext.pivot.plugin.configurator.FilterLabelWindow",
	titleText: "Value filter ({0})",
	fieldText: "Show items for which",
	initComponent: function() {
		var me = this;
		me.filterFields = [{
			xtype: "combo",
			editable: false,
			queryMode: "local",
			valueField: "value",
			store: me.storeAgg,
			name: "dimensionId"
		}];
		me.callParent(arguments)
	}
});
Ext.define("Ext.pivot.plugin.configurator.FilterTopWindow", {
	extend: "Ext.window.Window",
	requires: ["Ext.form.Panel", "Ext.form.FieldContainer",
		"Ext.form.field.Text", "Ext.form.field.Hidden",
		"Ext.form.field.ComboBox", "Ext.layout.container.HBox"
	],
	modal: true,
	closeAction: "destroy",
	titleText: "Top 10 filter ({0})",
	fieldText: "Show",
	sortResultsText: "Sort results",
	initComponent: function() {
		var me = this,
			items = [];
		items.push({
			xtype: "combo",
			editable: false,
			queryMode: "local",
			valueField: "value",
			store: me.storeTopOrder,
			name: "topOrder"
		}, {
			xtype: "textfield",
			margin: "0 0 0 5",
			name: "value"
		}, {
			xtype: "combo",
			margin: "0 0 0 5",
			editable: false,
			queryMode: "local",
			valueField: "value",
			store: me.storeTopType,
			name: "topType"
		}, {
			xtype: "combo",
			margin: "0 0 0 5",
			editable: false,
			queryMode: "local",
			valueField: "value",
			store: me.storeAgg,
			name: "dimensionId"
		});
		Ext.apply(me, {
			title: Ext.String.format(me.titleText, me.title),
			layout: "fit",
			items: [{
				xtype: "form",
				bodyPadding: 5,
				defaults: {
					allowBlank: false
				},
				items: [{
						xtype: "hidden",
						name: "type"
					},
					{
						xtype: "hidden",
						name: "operator"
					},
					{
						xtype: "fieldcontainer",
						labelSeparator: "",
						fieldLabel: me.fieldText,
						labelAlign: "top",
						layout: {
							type: "hbox",
							align: "stretch"
						},
						defaults: {
							flex: 1,
							allowBlank: false
						},
						items: items
					},
					{
						xtype: "checkbox",
						boxLabel: me.sortResultsText,
						name: "topSort"
					}
				]
			}],
			buttons: [{
					text: Ext.Msg.buttonText.ok,
					handler: me.applyFilter,
					scope: me
				},
				{
					text: Ext.Msg.buttonText.cancel,
					handler: me.cancelFilter,
					scope: me
				}
			]
		});
		me.callParent(arguments)
	},
	applyFilter: function() {
		var form = this.down("form")
			.getForm();
		if (form.isValid()) {
			this.fireEvent("filter", this, form.getValues())
		}
	},
	cancelFilter: function() {
		this.close()
	}
});
Ext.define("Ext.pivot.plugin.configurator.Column", {
	extend: "Ext.Component",
	requires: ["Ext.menu.Menu",
		"Ext.pivot.plugin.configurator.FilterLabelWindow",
		"Ext.pivot.plugin.configurator.FilterValueWindow",
		"Ext.pivot.plugin.configurator.FilterTopWindow"
	],
	alias: "widget.pivotconfigcolumn",
	childEls: ["textCol", "filterCol", "sortCol"],
	renderTpl: '<div id="{id}-configCol" class="' + Ext.baseCSSPrefix +
		'pivot-grid-config-column-inner">' +
		'<tpl if="isCustomizable">' + '<span id={id}-customCol class="' +
		Ext.baseCSSPrefix + "pivot-grid-config-column-btn-customize " +
		Ext.baseCSSPrefix + "border-box " + Ext.baseCSSPrefix +
		"pivot-grid-config-column-btn " + Ext.baseCSSPrefix +
		'pivot-grid-config-column-btn-image"></span>' + "</tpl>" +
		'<span id={id}-sortCol data-ref="sortCol" class="' + Ext.baseCSSPrefix +
		"border-box " + Ext.baseCSSPrefix +
		'pivot-grid-config-column-btn"></span>' +
		'<span id={id}-filterCol data-ref="filterCol" class="' + Ext.baseCSSPrefix +
		"border-box " + Ext.baseCSSPrefix +
		'pivot-grid-config-column-btn"></span>' +
		'<span id="{id}-textCol" data-ref="textCol" data-qtip="{header}{aggregator}" class="' +
		Ext.baseCSSPrefix + "pivot-grid-config-column-text " + Ext.baseCSSPrefix +
		"column-header-text " + Ext.baseCSSPrefix + 'border-box">' +
		"{header}{aggregator}" + "</span>" + "</div>",
	header: "&#160;",
	isCustomizable: false,
	dimension: null,
	isAgg: false,
	sumText: "Sum",
	avgText: "Avg",
	countText: "Count",
	minText: "Min",
	maxText: "Max",
	groupSumPercentageText: "Group sum percentage",
	groupCountPercentageText: "Group count percentage",
	varText: "Var",
	varPText: "Varp",
	stdDevText: "StdDev",
	stdDevPText: "StdDevp",
	sortAscText: "Sort A to Z",
	sortDescText: "Sort Z to A",
	sortClearText: "Disable sorting",
	clearFilterText: 'Clear filter from "{0}"',
	labelFiltersText: "Label filters",
	valueFiltersText: "Value filters",
	equalsText: "Equals...",
	doesNotEqualText: "Does not equal...",
	beginsWithText: "Begins with...",
	doesNotBeginWithText: "Does not begin with...",
	endsWithText: "Ends with...",
	doesNotEndWithText: "Does not end with...",
	containsText: "Contains...",
	doesNotContainText: "Does not contain...",
	greaterThanText: "Greater than...",
	greaterThanOrEqualToText: "Greater than or equal to...",
	lessThanText: "Less than...",
	lessThanOrEqualToText: "Less than or equal to...",
	betweenText: "Between...",
	notBetweenText: "Not between...",
	top10Text: "Top 10...",
	equalsLText: "equals",
	doesNotEqualLText: "does not equal",
	beginsWithLText: "begins with",
	doesNotBeginWithLText: "does not begin with",
	endsWithLText: "ends with",
	doesNotEndWithLText: "does not end with",
	containsLText: "contains",
	doesNotContainLText: "does not contain",
	greaterThanLText: "is greater than",
	greaterThanOrEqualToLText: "is greater than or equal to",
	lessThanLText: "is less than",
	lessThanOrEqualToLText: "is less than or equal to",
	betweenLText: "is between",
	notBetweenLText: "is not between",
	top10LText: "Top 10...",
	topOrderTopText: "Top",
	topOrderBottomText: "Bottom",
	topTypeItemsText: "Items",
	topTypePercentText: "Percent",
	topTypeSumText: "Sum",
	baseCls: Ext.baseCSSPrefix + "pivot-grid-config-column",
	btnIconCls: Ext.baseCSSPrefix +
		"pivot-grid-config-column-btn-image",
	setFilterIconCls: Ext.baseCSSPrefix +
		"pivot-grid-config-column-btn-filter-set",
	clearFilterIconCls: Ext.baseCSSPrefix +
		"pivot-grid-config-column-btn-filter-clear",
	ascSortIconCls: Ext.baseCSSPrefix +
		"pivot-grid-config-column-btn-sort-asc",
	descSortIconCls: Ext.baseCSSPrefix +
		"pivot-grid-config-column-btn-sort-desc",
	clearSortIconCls: Ext.baseCSSPrefix +
		"pivot-grid-config-column-btn-sort-clear",
	destroy: function() {
		var me = this;
		Ext.destroyMembers(me, "relayers", "menu");
		me.dimension = me.relayers = me.menu = null;
		me.callParent(arguments)
	},
	initRenderData: function() {
		var me = this;
		return Ext.apply(me.callParent(arguments), {
			header: me.dimension.header,
			aggregator: me.isAgg ? " (" + me.dimension.aggregator +
				")" : "",
			dimension: me.dimension,
			isCustomizable: me.isCustomizable
		})
	},
	afterRender: function() {
		var me = this;
		me.callParent();
		if (me.isCustomizable) {
			if (!me.isAgg && (!Ext.isDefined(me.dimension.sortable) ||
				me.dimension.sortable)) {
				me.addSortCls(me.dimension.direction)
			}
			if (me.dimension.filter) {
				me.addFilterCls()
			}
			me.mon(me.getTargetEl(), {
				scope: me,
				click: me.handleColClick
			})
		}
	},
	handleColClick: function(e, t) {
		var me = this;
		if (me.isAgg) {
			me.showAggMenu();
			e.stopEvent()
		} else {
			me.showColMenu()
		}
	},
	handleMenuClick: function(item, e) {
		var me = this,
			method;
		me.dimension.aggregator = item.aggregator;
		if (me.textCol) {
			method = me.textCol.setHtml ? "setHtml" : "setHTML";
			me.textCol[method](me.header + " (" + me.dimension.aggregator +
				")")
		}
		me.ownerCt.updateLayout();
		me.fireEvent("configchange")
	},
	addSortCls: function(direction) {
		var me = this;
		if (!me.sortCol) {
			return
		}
		if (direction === "ASC" || !direction) {
			me.sortCol.addCls(me.ascSortIconCls);
			me.sortCol.removeCls(me.descSortIconCls)
		} else {
			me.sortCol.addCls(me.descSortIconCls);
			me.sortCol.removeCls(me.ascSortIconCls)
		}
		me.sortCol.addCls(me.btnIconCls)
	},
	removeSortCls: function(direction) {
		var me = this;
		if (!me.sortCol) {
			return
		}
		if (direction === "ASC") {
			me.sortCol.removeCls(me.ascSortIconCls)
		} else {
			me.sortCol.removeCls(me.descSortIconCls)
		}
		me.sortCol.removeCls(me.btnIconCls)
	},
	addFilterCls: function() {
		var me = this;
		if (me.filterCol && !me.filterCol.hasCls(me.setFilterIconCls)) {
			me.filterCol.addCls(me.setFilterIconCls);
			me.filterCol.addCls(me.btnIconCls)
		}
	},
	removeFilterCls: function() {
		var me = this;
		if (me.filterCol) {
			me.filterCol.removeCls(me.setFilterIconCls);
			me.filterCol.removeCls(me.btnIconCls)
		}
	},
	serialize: function() {
		var me = this;
		return Ext.applyIf({
				idColumn: me.id
			},
			me.initialConfig)
	},
	showAggMenu: function() {
		var me = this,
			aggregator = me.dimension.aggregator;
		Ext.destroy(me.menu);
		me.menu = Ext.create("Ext.menu.Menu", {
			floating: true,
			defaults: {
				handler: me.handleMenuClick,
				scope: me,
				xtype: "menucheckitem",
				group: "aggregator"
			},
			items: [{
					text: me.sumText,
					aggregator: "sum",
					checked: aggregator == "sum"
				},
				{
					text: me.avgText,
					aggregator: "avg",
					checked: aggregator == "avg"
				},
				{
					text: me.countText,
					aggregator: "count",
					checked: aggregator == "count"
				},
				{
					text: me.maxText,
					aggregator: "max",
					checked: aggregator == "max"
				},
				{
					text: me.minText,
					aggregator: "min",
					checked: aggregator == "min"
				},
				{
					text: me.groupSumPercentageText,
					aggregator: "groupSumPercentage",
					checked: aggregator ==
						"groupSumPercentage"
				},
				{
					text: me.groupCountPercentageText,
					aggregator: "groupCountPercentage",
					checked: aggregator ==
						"groupCountPercentage"
				},
				{
					text: me.stdDevText,
					aggregator: "stdDev",
					checked: aggregator == "stdDev"
				},
				{
					text: me.stdDevPText,
					aggregator: "stdDevP",
					checked: aggregator == "stdDevP"
				},
				{
					text: me.varText,
					aggregator: "variance",
					checked: aggregator == "variance"
				},
				{
					text: me.varPText,
					aggregator: "varianceP",
					checked: aggregator == "varianceP"
				}
			]
		});
		me.menu.showBy(me)
	},
	showColMenu: function() {
		var me = this,
			items = [],
			labelItems,
			valueItems,
			commonItems,
			i,
			filter = me.dimension.filter;
		Ext.destroy(me.menu);
		items.push({
			text: me.sortAscText,
			direction: "ASC",
			iconCls: me.ascSortIconCls,
			handler: me.sortMe
		}, {
			text: me.sortDescText,
			direction: "DESC",
			iconCls: me.descSortIconCls,
			handler: me.sortMe
		}, {
			text: me.sortClearText,
			direction: "",
			disabled: me.dimension.sortable === false,
			iconCls: me.clearSortIconCls,
			handler: me.sortMe
		}, {
			xtype: "menuseparator"
		});
		commonItems = [{
				text: me.equalsText,
				operator: "="
			},
			{
				text: me.doesNotEqualText,
				operator: "!="
			},
			{
				xtype: "menuseparator"
			},
			{
				text: me.greaterThanText,
				operator: ">"
			},
			{
				text: me.greaterThanOrEqualToText,
				operator: ">="
			},
			{
				text: me.lessThanText,
				operator: "<"
			},
			{
				text: me.lessThanOrEqualToText,
				operator: "<="
			},
			{
				xtype: "menuseparator"
			},
			{
				text: me.betweenText,
				operator: "between"
			},
			{
				text: me.notBetweenText,
				operator: "not between"
			}
		];
		labelItems = Ext.clone(commonItems);
		Ext.Array.insert(labelItems, 3, [{
				text: me.beginsWithText,
				operator: "begins"
			},
			{
				text: me.doesNotBeginWithText,
				operator: "not begins"
			},
			{
				text: me.endsWithText,
				operator: "ends"
			},
			{
				text: me.doesNotEndWithText,
				operator: "not ends"
			},
			{
				xtype: "menuseparator"
			},
			{
				text: me.containsText,
				operator: "contains"
			},
			{
				text: me.doesNotContainText,
				operator: "not contains"
			},
			{
				xtype: "menuseparator"
			}
		]);
		for (i = 0; i < labelItems.length; i++) {
			labelItems[i]["checked"] = (filter && filter.type ==
				"label" && filter.operator == labelItems[i].operator
			)
		}
		valueItems = Ext.clone(commonItems);
		valueItems.push({
			xtype: "menuseparator"
		}, {
			text: me.top10Text,
			operator: "top10"
		});
		for (i = 0; i < valueItems.length; i++) {
			valueItems[i]["checked"] = (filter && filter.type ==
				"value" && filter.operator == valueItems[i].operator
			)
		}
		items.push({
			text: Ext.String.format(me.clearFilterText, me.header),
			iconCls: me.clearFilterIconCls,
			disabled: !filter,
			handler: me.onRemoveFilter
		}, {
			text: me.labelFiltersText,
			menu: {
				defaults: {
					handler: me.onShowFilter,
					scope: me,
					xtype: "menucheckitem",
					group: "filterlabel",
					type: "label"
				},
				items: labelItems
			}
		}, {
			text: me.valueFiltersText,
			menu: {
				defaults: {
					handler: me.onShowFilter,
					scope: me,
					xtype: "menucheckitem",
					group: "filtervalue",
					type: "value"
				},
				items: valueItems
			}
		});
		me.menu = Ext.create("Ext.menu.Menu", {
			floating: true,
			defaults: {
				scope: me
			},
			items: items
		});
		me.menu.showBy(me)
	},
	sortMe: function(btn) {
		var me = this;
		if (Ext.isEmpty(btn.direction)) {
			me.dimension.sortable = false;
			me.removeSortCls(me.dimension.direction)
		} else {
			me.dimension.sortable = true;
			me.addSortCls(btn.direction);
			me.dimension.direction = btn.direction
		}
		me.fireEvent("sortchange", me, btn.direction)
	},
	onShowFilter: function(btn) {
		var me = this,
			win, winClass, winCfg = {},
			data, dataAgg, filter = me.dimension.filter,
			values = {
				type: btn.type,
				operator: btn.operator,
				value: (filter ? filter.value : ""),
				from: (filter ? (Ext.isArray(filter.value) ? filter
					.value[0] : "") : ""),
				to: (filter ? (Ext.isArray(filter.value) ? filter.value[
					1] : "") : ""),
				caseSensitive: (filter ? filter.caseSensitive :
					false),
				topSort: (filter ? filter.topSort : false)
			};
		dataAgg = [];
		Ext.each(me.ownerCt.aggregateDimensions,
			function(field) {
				dataAgg.push([field.header, field.id])
			});
		if (btn.type == "label" || (btn.type == "value" && btn.operator !=
			"top10")) {
			data = [
				[me.equalsLText, "="],
				[me.doesNotEqualLText, "!="],
				[me.greaterThanLText, ">"],
				[me.greaterThanOrEqualToLText, ">="],
				[me.lessThanLText, "<"],
				[me.lessThanOrEqualToLText, "<="],
				[me.betweenLText, "between"],
				[me.notBetweenLText, "not between"]
			];
			if (btn.type == "label") {
				Ext.Array.insert(data, 3, [
					[me.beginsWithLText, "begins"],
					[me.doesNotBeginWithLText, "not begins"],
					[me.endsWithLText, "ends"],
					[me.doesNotEndWithLText, "not ends"],
					[me.containsLText, "contains"],
					[me.doesNotContainLText, "not contains"]
				]);
				winClass =
					"Ext.pivot.plugin.configurator.FilterLabelWindow"
			} else {
				winClass =
					"Ext.pivot.plugin.configurator.FilterValueWindow";
				Ext.apply(values, {
					dimensionId: (filter ? filter.dimensionId :
						"")
				});
				winCfg.storeAgg = Ext.create("Ext.data.ArrayStore", {
					fields: ["text", "value"],
					data: dataAgg
				})
			}
			winCfg.store = Ext.create("Ext.data.ArrayStore", {
				fields: ["text", "value"],
				data: data
			})
		} else {
			winClass =
				"Ext.pivot.plugin.configurator.FilterTopWindow";
			data = [];
			Ext.apply(winCfg, {
				storeTopOrder: Ext.create(
					"Ext.data.ArrayStore", {
						fields: ["text", "value"],
						data: [
							[me.topOrderTopText,
								"top"
							],
							[me.topOrderBottomText,
								"bottom"
							]
						]
					}),
				storeTopType: Ext.create(
					"Ext.data.ArrayStore", {
						fields: ["text", "value"],
						data: [
							[me.topTypeItemsText,
								"items"
							],
							[me.topTypePercentText,
								"percent"
							],
							[me.topTypeSumText,
								"sum"
							]
						]
					}),
				storeAgg: Ext.create("Ext.data.ArrayStore", {
					fields: ["text", "value"],
					data: dataAgg
				})
			});
			Ext.apply(values, {
				operator: "top10",
				dimensionId: (filter ? filter.dimensionId :
					""),
				topType: (filter ? filter.topType : "items"),
				topOrder: (filter ? filter.topOrder : "top")
			})
		}
		win = Ext.create(winClass, Ext.apply(winCfg || {}, {
			title: me.header,
			listeners: {
				filter: me.onApplyFilter,
				scope: me
			}
		}));
		win.down("form")
			.getForm()
			.setValues(values);
		win.show()
	},
	onApplyFilter: function(win, filter) {
		var me = this;
		filter.caseSensitive = (filter.caseSensitive === "on");
		filter.topSort = (filter.topSort === "on");
		win.close();
		me.addFilterCls();
		me.dimension.filter = filter;
		me.fireEvent("filterchange", me, filter)
	},
	onRemoveFilter: function() {
		var me = this;
		me.removeFilterCls();
		me.dimension.filter = null;
		me.fireEvent("filterchange", me, null)
	}
});
Ext.define("Ext.pivot.plugin.configurator.DragZone", {
	extend: "Ext.dd.DragZone",
	configColumnSelector: "." + Ext.baseCSSPrefix +
		"pivot-grid-config-column",
	configColumnInnerSelector: "." + Ext.baseCSSPrefix +
		"pivot-grid-config-column-inner",
	maxProxyWidth: 120,
	dragging: false,
	constructor: function(panel) {
		this.panel = panel;
		this.ddGroup = this.getDDGroup();
		this.callParent([panel.el])
	},
	getDDGroup: function() {
		return "configurator-" + this.panel.up("gridpanel")
			.id
	},
	getDragData: function(e) {
		if (e.getTarget(this.configColumnInnerSelector)) {
			var header = e.getTarget(this.configColumnSelector),
				headerCmp,
				ddel;
			if (header) {
				headerCmp = Ext.getCmp(header.id);
				if (!this.panel.dragging) {
					ddel = document.createElement("div");
					ddel.innerHTML = headerCmp.header;
					return {
						ddel: ddel,
						header: headerCmp
					}
				}
			}
		}
		return false
	},
	onBeforeDrag: function() {
		return !(this.panel.dragging || this.disabled)
	},
	onInitDrag: function() {
		this.panel.dragging = true;
		this.callParent(arguments)
	},
	onDragDrop: function() {
		if (!this.dragData.dropLocation) {
			this.panel.dragging = false;
			this.callParent(arguments);
			return
		}
		var dropCol = this.dragData.dropLocation.header,
			dragCol = this.dragData.header,
			pos = -1;
		if (dropCol instanceof Ext.grid.column.Column) {
			dropCol.show();
			pos = this.panel.items.findIndex("idColumn", dragCol.id);
			this.panel.remove(this.panel.items.getAt(pos));
			this.panel.notifyGroupChange()
		}
		this.panel.dragging = false;
		this.callParent(arguments)
	},
	afterRepair: function() {
		this.callParent();
		this.panel.dragging = false
	},
	getRepairXY: function() {
		return this.dragData.header.el.getXY()
	},
	disable: function() {
		this.disabled = true
	},
	enable: function() {
		this.disabled = false
	}
});
Ext.define("Ext.pivot.plugin.configurator.DropZone", {
	extend: "Ext.dd.DropZone",
	proxyOffsets: [-4, -9],
	configPanelCls: Ext.baseCSSPrefix +
		"pivot-grid-config-container-body",
	configColumnCls: Ext.baseCSSPrefix + "pivot-grid-config-column",
	constructor: function(panel) {
		this.panel = panel;
		this.ddGroup = this.getDDGroup();
		this.callParent([panel.id])
	},
	disable: function() {
		this.disabled = true
	},
	enable: function() {
		this.disabled = false
	},
	getDDGroup: function() {
		return "configurator-" + this.panel.up("gridpanel")
			.id
	},
	getTargetFromEvent: function(e) {
		return e.getTarget("." + this.configColumnCls) || e.getTarget(
			"." + this.configPanelCls)
	},
	getTopIndicator: function() {
		if (!this.topIndicator) {
			this.self.prototype.topIndicator = Ext.DomHelper.append(
				Ext.getBody(), {
					cls: "col-move-top " + Ext.baseCSSPrefix +
						"col-move-top",
					html: "&#160;"
				},
				true);
			this.self.prototype.indicatorXOffset = Math.floor((this
				.topIndicator.dom.offsetWidth + 1) / 2)
		}
		return this.topIndicator
	},
	getBottomIndicator: function() {
		if (!this.bottomIndicator) {
			this.self.prototype.bottomIndicator = Ext.DomHelper.append(
				Ext.getBody(), {
					cls: "col-move-bottom " + Ext.baseCSSPrefix +
						"col-move-bottom",
					html: "&#160;"
				},
				true)
		}
		return this.bottomIndicator
	},
	getLocation: function(e, t) {
		var x = e.getXY()[0],
			target = Ext.getCmp(t.id),
			region,
			pos;
		if (target instanceof Ext.pivot.plugin.configurator.Container) {
			if (target.items.getCount() > 0) {
				region = Ext.fly(target.items.last()
						.el)
					.getRegion()
			} else {
				region = new Ext.util.Region(0, 1000000, 0, 0)
			}
		} else {
			region = Ext.fly(t)
				.getRegion()
		}
		if ((region.right - x) <= (region.right - region.left) / 2) {
			pos = "after"
		} else {
			pos = "before"
		}
		return {
			pos: pos,
			header: Ext.getCmp(t.id),
			node: t
		}
	},
	positionIndicator: function(data, node, e) {
		var me = this,
			dragHeader = data.header,
			dropLocation = me.getLocation(e, node),
			targetHeader = dropLocation.header,
			pos = dropLocation.pos,
			nextHd,
			prevHd,
			topIndicator,
			bottomIndicator,
			topAnchor,
			bottomAnchor,
			topXY,
			bottomXY,
			headerCtEl,
			minX,
			maxX,
			allDropZones,
			ln,
			i,
			dropZone;
		if (targetHeader === me.lastTargetHeader && pos === me.lastDropPos) {
			return
		}
		nextHd = dragHeader.nextSibling("gridcolumn:not([hidden])");
		prevHd = dragHeader.previousSibling(
			"gridcolumn:not([hidden])");
		me.lastTargetHeader = targetHeader;
		me.lastDropPos = pos;
		data.dropLocation = dropLocation;
		if ((dragHeader !== targetHeader) && ((pos === "before" &&
			nextHd !== targetHeader) || (pos === "after" &&
			prevHd !== targetHeader)) && !targetHeader.isDescendantOf(
			dragHeader)) {
			allDropZones = Ext.dd.DragDropManager.getRelated(me);
			ln = allDropZones.length;
			i = 0;
			for (; i < ln; i++) {
				dropZone = allDropZones[i];
				if (dropZone !== me && dropZone.invalidateDrop) {
					dropZone.invalidateDrop()
				}
			}
			me.valid = true;
			topIndicator = me.getTopIndicator();
			bottomIndicator = me.getBottomIndicator();
			if (pos === "before") {
				topAnchor = "bc-tl";
				bottomAnchor = "tc-bl"
			} else {
				topAnchor = "bc-tr";
				bottomAnchor = "tc-br"
			}
			if (targetHeader instanceof Ext.pivot.plugin.configurator
				.Container && targetHeader.items.getCount() > 0) {
				topXY = topIndicator.getAlignToXY(targetHeader.items
					.last()
					.el, topAnchor);
				bottomXY = bottomIndicator.getAlignToXY(
					targetHeader.items.last()
					.el, bottomAnchor)
			} else {
				topXY = topIndicator.getAlignToXY(targetHeader.el,
					topAnchor);
				bottomXY = bottomIndicator.getAlignToXY(
					targetHeader.el, bottomAnchor)
			}
			headerCtEl = me.panel.el;
			minX = headerCtEl.getX() - me.indicatorXOffset;
			maxX = headerCtEl.getX() + headerCtEl.getWidth();
			topXY[0] = Ext.Number.constrain(topXY[0], minX, maxX);
			bottomXY[0] = Ext.Number.constrain(bottomXY[0], minX,
				maxX);
			topIndicator.setXY(topXY);
			bottomIndicator.setXY(bottomXY);
			topIndicator.show();
			bottomIndicator.show()
		} else {
			me.invalidateDrop()
		}
	},
	invalidateDrop: function() {
		this.valid = false;
		this.hideIndicators()
	},
	onNodeOver: function(node, dragZone, e, data) {
		var me = this,
			from = data.header,
			doPosition, to, fromPanel, toPanel;
		doPosition = true;
		if (data.header.el.dom === node) {
			doPosition = false
		}
		if (doPosition) {
			me.positionIndicator(data, node, e)
		} else {
			me.valid = false
		}
		return me.valid ? me.dropAllowed : me.dropNotAllowed
	},
	hideIndicators: function() {
		var me = this;
		me.getTopIndicator()
			.hide();
		me.getBottomIndicator()
			.hide();
		me.lastTargetHeader = me.lastDropPos = null
	},
	onNodeOut: function() {
		this.hideIndicators()
	},
	onNodeDrop: function(node, dragZone, e, data) {
		var me = this,
			dragColumn = data.header,
			dropLocation = data.dropLocation,
			newCol, pos, newPos;
		if (me.valid && dropLocation) {
			if (dragZone.id != me.panel.id) {
				pos = me.panel.getColumnPosition(dropLocation.header,
					dropLocation.pos);
				newCol = dragColumn.serialize();
				if (!me.panel.isAgg) {
					dragZone.panel.remove(dragColumn)
				}
				me.panel.addColumn(newCol.dimension, pos, true)
			} else {
				me.panel.moveColumn(dragColumn.id, dropLocation.header instanceof Ext
					.pivot.plugin.configurator.Container ?
					dropLocation.header.items.last()
					.id : dropLocation.header.id, dropLocation.pos
				)
			}
		}
	}
});
Ext.define("Ext.pivot.plugin.configurator.Container", {
	extend: "Ext.panel.Panel",
	requires: ["Ext.pivot.plugin.configurator.Column",
		"Ext.pivot.plugin.configurator.DragZone",
		"Ext.pivot.plugin.configurator.DropZone"
	],
	alias: "widget.pivotconfigcontainer",
	childEls: ["innerCt", "targetEl"],
	handleSorting: false,
	handleFiltering: false,
	position: "top",
	isAgg: false,
	border: false,
	dragDropText: "Drop Column Fields Here",
	cls: Ext.baseCSSPrefix + "pivot-grid-config-container-body",
	dockedTopRightCls: Ext.baseCSSPrefix +
		"pivot-grid-config-container-body-tr",
	dockedBottomLeftCls: Ext.baseCSSPrefix +
		"pivot-grid-config-container-body-bl",
	hintTextCls: Ext.baseCSSPrefix + "pivot-grid-config-container-hint",
	initComponent: function() {
		var me = this;
		if (me.position == "top" || me.position == "bottom") {
			Ext.apply(me, {
				style: "overflow:hidden",
				layout: "column",
				height: "auto"
			})
		} else {
			Ext.apply(me, {
				layout: {
					type: "vbox",
					align: "stretch"
				}
			})
		}
		if (me.position == "top" || me.position == "right") {
			me.cls += " " + me.dockedTopRightCls
		} else {
			me.cls += " " + me.dockedBottomLeftCls
		}
		me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		Ext.destroyMembers(me, "dragZone", "dropZone", "relayers",
			"targetEl");
		me.dragZone = me.dropZone = me.relayers = me.targetEl =
			null;
		me.callParent()
	},
	enable: function() {
		var me = this;
		if (me.dragZone) {
			me.dragZone.enable()
		}
		if (me.dropZone) {
			me.dropZone.enable()
		}
	},
	disable: function() {
		var me = this;
		if (me.dragZone) {
			me.dragZone.disable()
		}
		if (me.dropZone) {
			me.dropZone.disable()
		}
	},
	afterRender: function() {
		var me = this;
		me.callParent();
		me.dragZone = new Ext.pivot.plugin.configurator.DragZone(me);
		me.dropZone = new Ext.pivot.plugin.configurator.DropZone(me);
		me.mon(me, "afterlayout", me.showGroupByText, me)
	},
	addColumn: function(config, pos, notify) {
		var me = this,
			cfg = {
				xtype: "pivotconfigcolumn"
			},
			itemFound = me.items.findIndex("dimensionId", new RegExp(
				"^" + config.id + "$", "i")) >= 0,
			newCol;
		if (!me.isAgg) {
			if (itemFound) {
				if (notify === true) {
					me.notifyGroupChange()
				}
				return
			}
		} else {
			if (itemFound) {
				config.id = Ext.id()
			}
		}
		if (me.items.getCount() == 0) {
			me.hideGroupByText()
		}
		Ext.apply(cfg, {
			dimension: config,
			dimensionId: config.id,
			header: config.header,
			isCustomizable: me.isCustomizable,
			isAgg: me.isAgg
		});
		if (me.isAgg) {
			config.aggregator = config.aggregator || "sum"
		}
		if (pos != -1) {
			newCol = me.insert(pos, cfg)
		} else {
			newCol = me.add(cfg)
		}
		me.updateColumnIndexes();
		newCol.relayers = me.relayEvents(newCol, ["sortchange",
			"filterchange", "configchange"
		]);
		if (notify === true) {
			me.notifyGroupChange()
		}
	},
	getColumnPosition: function(column, position) {
		var me = this,
			pos;
		if (column instanceof Ext.pivot.plugin.configurator.Column) {
			pos = me.items.findIndex("id", column.id);
			pos = (position === "before") ? pos : pos + 1
		} else {
			pos = -1
		}
		return pos
	},
	moveColumn: function(idFrom, idTo, position) {
		var me = this,
			pos = me.items.findIndex("id", idFrom),
			newPos = me.items.findIndex("id", idTo);
		if (pos != newPos) {
			if (newPos > pos) {
				newPos = (position === "before") ? Math.max(newPos -
					1, 0) : newPos
			} else {
				newPos = (position === "before") ? newPos : newPos +
					1
			}
			me.move(pos, newPos);
			me.updateColumnIndexes();
			me.notifyGroupChange()
		}
	},
	updateColumnIndexes: function() {
		this.items.each(function(item, index, all) {
			item.index = index
		})
	},
	notifyGroupChange: function() {
		this.fireEvent("configchange")
	},
	showGroupByText: function() {
		var me = this;
		if (me.items.getCount() === 0) {
			me.innerCt.setHeight(me.minHeight);
			if (me.targetEl) {
				me.targetEl.setHtml('<div class="' + me.hintTextCls +
					'">' + me.dragDropText + "</div>")
			} else {
				me.targetEl = me.innerCt.createChild()
			}
		}
	},
	hideGroupByText: function() {
		if (this.targetEl) {
			this.targetEl.setHtml("")
		}
	}
});
Ext.define("Ext.pivot.plugin.configurator.Panel", {
	extend: "Ext.panel.Panel",
	requires: ["Ext.pivot.plugin.configurator.Container"],
	alias: "widget.pivotconfigpanel",
	dock: "right",
	weight: 50,
	grid: null,
	fields: [],
	refreshDelay: 1000,
	defaultMinHeight: 70,
	defaultMinWidth: 250,
	header: false,
	title: "Configurator",
	collapsible: true,
	collapseMode: "placeholder",
	panelAllFieldsText: "Drop Unused Fields Here",
	panelAllFieldsTitle: "All fields",
	panelTopFieldsText: "Drop Column Fields Here",
	panelTopFieldsTitle: "Column labels",
	panelLeftFieldsText: "Drop Row Fields Here",
	panelLeftFieldsTitle: "Row labels",
	panelAggFieldsText: "Drop Agg Fields Here",
	panelAggFieldsTitle: "Values",
	headerContainerCls: Ext.baseCSSPrefix +
		"pivot-grid-config-container-header",
	initComponent: function() {
		var me = this,
			listeners = {
				configchange: me.onConfigChanged,
				sortchange: me.onSortChanged,
				filterchange: me.onFilterChanged,
				scope: me,
				destroyable: true
			};
		Ext.apply(me, Ext.Array.indexOf(["top", "bottom"], me.dock) >=
			0 ? me.getHorizontalConfig() : me.getVerticalConfig()
		);
		me.callParent(arguments);
		me.fieldsCt = me.down("#fieldsCt");
		me.fieldsTopCt = me.down("#fieldsTopCt");
		me.fieldsLeftCt = me.down("#fieldsLeftCt");
		me.fieldsAggCt = me.down("#fieldsAggCt");
		me.fieldsCtListeners = me.fieldsCt.on(listeners);
		me.fieldsLeftCtListeners = me.fieldsLeftCt.on(listeners);
		me.fieldsTopCtListeners = me.fieldsTopCt.on(listeners);
		me.fieldsAggCtListeners = me.fieldsAggCt.on(listeners);
		me.fieldsExtracted = false;
		me.gridListeners = me.grid.on({
			pivotdone: me.initPivotFields,
			scope: me,
			destroyable: true
		});
		me.task = new Ext.util.DelayedTask(function() {
			me.grid.reconfigurePivot({
				topAxis: me.getFieldsFromContainer(
					me.fieldsTopCt),
				leftAxis: me.getFieldsFromContainer(
					me.fieldsLeftCt),
				aggregate: me.getFieldsFromContainer(
					me.fieldsAggCt)
			})
		})
	},
	destroy: function() {
		var me = this;
		delete(me.grid);
		Ext.destroy(me.relayers, me.fieldsCtListeners, me.fieldsLeftCtListeners,
			me.fieldsTopCtListeners, me.fieldsAggCtListeners,
			me.gridListeners);
		me.callParent()
	},
	enable: function() {
		var me = this;
		if (me.fieldsCt) {
			me.fieldsCt.enable();
			me.fieldsTopCt.enable();
			me.fieldsLeftCt.enable();
			me.fieldsAggCt.enable();
			me.initPivotFields()
		}
		me.show()
	},
	disable: function() {
		var me = this;
		if (me.fieldsCt) {
			me.fieldsCt.disable();
			me.fieldsTopCt.disable();
			me.fieldsLeftCt.disable();
			me.fieldsAggCt.disable()
		}
		me.hide()
	},
	getPanelConfigHeader: function(config) {
		return Ext.apply({
				xtype: "header",
				baseCls: Ext.baseCSSPrefix + "panel-header",
				cls: this.headerContainerCls,
				border: 1,
				width: 100
			},
			config || {})
	},
	getHorizontalConfig: function() {
		var me = this;
		return {
			minHeight: me.defaultMinHeight,
			headerPosition: me.dock == "top" ? "bottom" : "top",
			collapseDirection: me.dock,
			defaults: {
				xtype: "container",
				layout: {
					type: "hbox",
					align: "stretchmax"
				},
				minHeight: me.defaultMinHeight / 3
			},
			items: [{
					items: [me.getPanelConfigHeader({
						title: me.panelAllFieldsTitle,
						tools: me.collapsible ? [{
							type: me.dock ==
								"top" ?
								"up" : "down",
							handler: me.collapseMe,
							scope: me
						}] : []
					}), {
						itemId: "fieldsCt",
						xtype: "pivotconfigcontainer",
						isCustomizable: false,
						dragDropText: me.panelAllFieldsText,
						position: me.dock,
						flex: 1
					}]
				},
				{
					items: [me.getPanelConfigHeader({
						title: me.panelAggFieldsTitle
					}), {
						itemId: "fieldsAggCt",
						xtype: "pivotconfigcontainer",
						isCustomizable: true,
						isAgg: true,
						dragDropText: me.panelAggFieldsText,
						position: me.dock,
						flex: 1
					}]
				},
				{
					defaults: {
						xtype: "pivotconfigcontainer",
						minHeight: me.defaultMinHeight / 3,
						position: me.dock
					},
					items: [me.getPanelConfigHeader({
							title: me.panelLeftFieldsTitle
						}), {
							itemId: "fieldsLeftCt",
							pivotField: "leftAxis",
							isCustomizable: true,
							dragDropText: me.panelLeftFieldsText,
							flex: 1
						},
						me.getPanelConfigHeader({
							title: me.panelTopFieldsTitle
						}), {
							itemId: "fieldsTopCt",
							pivotField: "topAxis",
							isCustomizable: true,
							dragDropText: me.panelTopFieldsText,
							flex: 1
						}
					]
				}
			]
		}
	},
	getVerticalConfig: function() {
		var me = this;
		return {
			layout: {
				type: "hbox",
				align: "stretch"
			},
			minWidth: me.defaultMinWidth,
			headerPosition: me.dock == "right" ? "left" : "right",
			collapseDirection: me.dock,
			defaults: {
				flex: 1
			},
			items: [{
					itemId: "fieldsCt",
					xtype: "pivotconfigcontainer",
					position: me.dock,
					title: me.panelAllFieldsTitle,
					isCustomizable: false,
					dragDropText: me.panelAllFieldsText,
					autoScroll: true,
					header: {
						cls: me.headerContainerCls
					},
					tools: me.collapsible ? [{
						type: me.dock,
						handler: me.collapseMe,
						scope: me
					}] : []
				},
				{
					xtype: "container",
					defaults: {
						xtype: "pivotconfigcontainer",
						flex: 1,
						autoScroll: true,
						position: me.dock,
						header: {
							cls: me.headerContainerCls
						}
					},
					layout: {
						type: "vbox",
						align: "stretch"
					},
					items: [{
							itemId: "fieldsAggCt",
							title: me.panelAggFieldsTitle,
							isCustomizable: true,
							isAgg: true,
							dragDropText: me.panelAggFieldsText
						},
						{
							itemId: "fieldsLeftCt",
							title: me.panelLeftFieldsTitle,
							pivotField: "leftAxis",
							isCustomizable: true,
							dragDropText: me.panelLeftFieldsText
						},
						{
							itemId: "fieldsTopCt",
							title: me.panelTopFieldsTitle,
							pivotField: "topAxis",
							isCustomizable: true,
							dragDropText: me.panelTopFieldsText
						}
					]
				}
			]
		}
	},
	onConfigChanged: function() {
		var me = this,
			topAxis = [],
			leftAxis = [],
			agg = [];
		if (me.disabled) {
			return
		}
		if (me.grid.fireEvent("configchange", me, {
			topAxis: me.getFieldsFromContainer(me.fieldsTopCt),
			leftAxis: me.getFieldsFromContainer(me.fieldsLeftCt),
			aggregate: me.getFieldsFromContainer(me.fieldsAggCt)
		}) !== false) {
			me.task.delay(me.refreshDelay)
		}
	},
	collapseMe: function() {
		this.collapse(this.dock)
	},
	getFieldsFromContainer: function(ct, excludeWidth) {
		var fields = [];
		ct.items.each(function(item) {
			fields.push(item.dimension)
		});
		return fields
	},
	onSortChanged: function(column, direction) {
		var me = this,
			fields;
		if (me.disabled) {
			return
		}
		fields = me.grid[column.ownerCt.pivotField];
		Ext.each(fields,
			function(field) {
				if (field.dataIndex == column.dataIndex) {
					field.direction = direction;
					return false
				}
			});
		me.task.delay(me.refreshDelay)
	},
	onFilterChanged: function(column, filter) {
		var me = this,
			fields;
		if (me.disabled) {
			return
		}
		me.task.delay(me.refreshDelay)
	},
	initPivotFields: function() {
		var me = this,
			store = me.grid.getStore(),
			model = store ? store.model : null,
			fieldsTop,
			fieldsLeft,
			fieldsAgg,
			cFields;
		if (model != me.lastModel) {
			Ext.destroy(me.lastFields);
			delete(me.lastFields);
			me.lastModel = model
		}
		if (!me.lastFields) {
			me.lastFields = me.fetchAllFieldConfigurations()
		}
		cFields = me.lastFields.clone();
		me.fieldsCt.removeAll();
		me.fieldsTopCt.removeAll();
		me.fieldsLeftCt.removeAll();
		me.fieldsAggCt.removeAll();
		fieldsTop = me.getConfigFields(me.grid.topAxis);
		fieldsLeft = me.getConfigFields(me.grid.leftAxis);
		fieldsAgg = me.getConfigFields(me.grid.aggregate);
		Ext.each(Ext.Array.merge(fieldsTop, fieldsLeft),
			function(item) {
				var i, found = false;
				if (item.filter && item.filter.dimensionId) {
					for (i = 0; i < fieldsAgg.length; i++) {
						if (fieldsAgg[i].id == item.filter.dimensionId) {
							found = true;
							break
						}
					}
					if (!found) {
						delete item.filter
					}
				}
				cFields.removeAtKey(item.header);
				me.mergeFieldConfig(item)
			});
		Ext.each(fieldsAgg, me.mergeFieldConfig, me);
		Ext.suspendLayouts();
		me.addFieldsToConfigurator(cFields.getRange(), me.fieldsCt);
		me.addFieldsToConfigurator(fieldsTop, me.fieldsTopCt);
		me.addFieldsToConfigurator(fieldsLeft, me.fieldsLeftCt);
		me.addFieldsToConfigurator(fieldsAgg, me.fieldsAggCt);
		me.fieldsTopCt.aggregateDimensions = fieldsAgg;
		me.fieldsLeftCt.aggregateDimensions = fieldsAgg;
		Ext.resumeLayouts(true)
	},
	mergeFieldConfig: function(item) {
		var el = this.lastFields.getByKey(item.header),
			id;
		if (el) {
			id = el.id;
			Ext.apply(el, item);
			el.id = id
		}
	},
	fetchAllFieldConfigurations: function() {
		var me = this,
			store = me.grid.getStore(),
			fields = store ? store.model.getFields() : [],
			allFields = [],
			lastFields;
		lastFields = Ext.create("Ext.util.MixedCollection");
		lastFields.getKey = function(el) {
			return el.header
		};
		if (me.fields.length > 0) {
			allFields = me.fields
		} else {
			Ext.each(fields,
				function(field) {
					allFields.push({
						header: Ext.String.capitalize(
							field.name),
						dataIndex: field.name,
						direction: field.sortDir
					})
				})
		}
		Ext.each(allFields,
			function(field) {
				field.id = field.id || Ext.id()
			});
		lastFields.addAll(allFields);
		return lastFields
	},
	addFieldsToConfigurator: function(fields, fieldsCt) {
		Ext.each(fields,
			function(item, index, len) {
				fieldsCt.addColumn(item, -1)
			})
	},
	getConfigFields: function(dimension) {
		var me = this,
			fields = [];
		Ext.each(dimension,
			function(obj) {
				var field = Ext.clone(obj);
				field.id = field.id || Ext.id();
				if (!me.lastFields.getByKey(field.header)) {
					me.lastFields.add(field)
				}
				fields.push(field)
			});
		return fields
	},
	placeholderCollapse: function(direction, animate) {
		var me = this,
			ownerCt = me.ownerCt,
			collapseDir = direction || me.collapseDirection,
			floatCls = Ext.panel.Panel.floatCls,
			placeholder = me.getPlaceholder(collapseDir),
			slideInDirection;
		me.isCollapsingOrExpanding = 1;
		me.setHiddenState(true);
		me.collapsed = collapseDir;
		if (placeholder.rendered) {
			if (placeholder.el.dom.parentNode !== me.el.dom.parentNode) {
				me.el.dom.parentNode.insertBefore(placeholder.el.dom,
					me.el.dom)
			}
			placeholder.hidden = false;
			placeholder.setHiddenState(false);
			placeholder.el.show();
			ownerCt.updateLayout()
		} else {
			if (me.dock) {
				placeholder.dock = me.dock;
				ownerCt.addDocked(placeholder)
			} else {
				ownerCt.insert(ownerCt.items.indexOf(me),
					placeholder)
			}
		}
		if (me.rendered) {
			if (Ext.ComponentManager.getActiveComponent() === me.collapseTool) {
				me.focusPlaceholderExpandTool = true
			}
			me.el.setVisibilityMode(me.placeholderCollapseHideMode);
			if (animate) {
				me.el.addCls(floatCls);
				placeholder.el.hide();
				slideInDirection = me.convertCollapseDir(
					collapseDir);
				me.el.slideOut(slideInDirection, {
					preserveScroll: true,
					duration: Ext.Number.from(animate, Ext.fx
						.Anim.prototype.duration),
					listeners: {
						scope: me,
						afteranimate: function() {
							var me = this;
							me.el.removeCls(floatCls);
							me.placeholder.el.show()
								.setStyle("display",
									"none")
								.slideIn(
									slideInDirection, {
										easing: "linear",
										duration: 100,
										listeners: {
											afteranimate: me
												.doPlaceholderCollapse,
											scope: me
										}
									})
						}
					}
				})
			} else {
				me.el.hide();
				me.doPlaceholderCollapse()
			}
		} else {
			me.isCollapsingOrExpanding = 0;
			if (!me.preventCollapseFire) {
				me.fireEvent("collapse", me)
			}
		}
		return me
	},
	placeholderExpand: function(animate) {
		var me = this,
			collapseDir = me.collapsed,
			expandTool = me.placeholder.expandTool,
			floatCls = Ext.panel.Panel.floatCls,
			center = me.ownerLayout ? me.ownerLayout.centerRegion :
			null,
			finalPos,
			floatedPos;
		if (Ext.Component.layoutSuspendCount) {
			animate = false
		}
		if (me.floatedFromCollapse) {
			floatedPos = me.getPosition(true);
			me.slideOutFloatedPanelBegin();
			me.slideOutFloatedPanelEnd();
			me.floated = false
		}
		if (Ext.ComponentManager.getActiveComponent() ===
			expandTool) {
			me.focusHeaderCollapseTool = true;
			expandTool._ariaRole = expandTool.ariaEl.dom.getAttribute(
				"role");
			expandTool._ariaLabel = expandTool.ariaEl.dom.getAttribute(
				"aria-label");
			expandTool.ariaEl.dom.setAttribute("role",
				"presentation");
			expandTool.ariaEl.dom.removeAttribute("aria-label")
		}
		if (animate) {
			Ext.suspendLayouts();
			me.placeholder.hide();
			me.el.show();
			me.collapsed = false;
			me.setHiddenState(false);
			if (center && !floatedPos) {
				center.hidden = true
			}
			Ext.resumeLayouts(true);
			if (center) {
				center.hidden = false
			}
			me.el.addCls(floatCls);
			me.isCollapsingOrExpanding = 2;
			if (floatedPos) {
				finalPos = me.getXY();
				me.setLocalXY(floatedPos[0], floatedPos[1]);
				me.setXY([finalPos[0], finalPos[1]], {
					duration: Ext.Number.from(animate, Ext.fx
						.Anim.prototype.duration),
					listeners: {
						scope: me,
						afteranimate: function() {
							var me = this;
							me.el.removeCls(floatCls);
							me.isCollapsingOrExpanding =
								0;
							me.fireEvent("expand", me)
						}
					}
				})
			} else {
				me.el.hide();
				me.placeholder.el.show();
				me.placeholder.hidden = false;
				me.setHiddenState(false);
				me.el.slideIn(me.convertCollapseDir(collapseDir), {
					preserveScroll: true,
					duration: Ext.Number.from(animate, Ext.fx
						.Anim.prototype.duration),
					listeners: {
						afteranimate: me.doPlaceholderExpand,
						scope: me
					}
				})
			}
		} else {
			me.floated = me.collapsed = false;
			me.doPlaceholderExpand(true)
		}
		return me
	}
});
Ext.define("Ext.pivot.plugin.Configurator", {
	alternateClassName: ["Mz.pivot.plugin.Configurator"],
	extend: "Ext.AbstractPlugin",
	requires: ["Ext.util.DelayedTask", "Ext.menu.Menu",
		"Ext.menu.CheckItem", "Ext.pivot.plugin.configurator.Panel"
	],
	alias: ["plugin.pivotconfigurator", "plugin.mzconfigurator"],
	fields: [],
	refreshDelay: 300,
	dock: "right",
	collapsible: true,
	lockableScope: "top",
	init: function(grid) {
		var me = this;
		if (!grid.isPivotGrid) {
			Ext.raise(
				"This plugin is only compatible with Ext.pivot.Grid"
			)
		}
		me.pivot = grid;
		me.fields = Ext.Array.from(me.fields);
		me.pivotListeners = me.pivot.on({
			beforerender: me.onBeforeGridRendered,
			afterrender: me.onAfterGridRendered,
			single: true,
			scope: me,
			destroyable: true
		});
		me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		Ext.destroyMembers(me, "configCt", "pivotListeners");
		me.pivot = me.fields = me.pivotListeners = me.configCt =
			null;
		me.callParent(arguments)
	},
	enable: function() {
		var me = this;
		me.disabled = false;
		if (me.configCt) {
			me.configCt.enable()
		}
		me.pivot.fireEvent("showconfigpanel", me.configCt)
	},
	disable: function() {
		var me = this;
		me.disabled = true;
		if (me.configCt) {
			me.configCt.disable()
		}
		me.pivot.fireEvent("hideconfigpanel", me.configCt)
	},
	onBeforeGridRendered: function() {
		this.setDock(this.dock)
	},
	onAfterGridRendered: function() {
		if (this.disabled === true) {
			this.disable()
		} else {
			this.enable()
		}
	},
	setDock: function(position) {
		var me = this,
			exists = Ext.isDefined(me.configCt);
		Ext.destroy(me.configCt);
		me.configCt = me.pivot.addDocked({
			xtype: "pivotconfigpanel",
			dock: position || me.dock,
			grid: me.pivot,
			fields: me.fields,
			refreshDelay: me.refreshDelay,
			collapsible: me.collapsible
		})[0];
		if (exists) {
			me.configCt.initPivotFields()
		}
	}
});
Ext.define("Ext.pivot.plugin.DrillDown", {
	alternateClassName: ["Mz.pivot.plugin.DrillDown"],
	alias: ["plugin.pivotdrilldown", "plugin.mzdrilldown"],
	extend: "Ext.AbstractPlugin",
	requires: ["Ext.pivot.Grid", "Ext.window.Window",
		"Ext.data.proxy.Memory", "Ext.data.Store",
		"Ext.toolbar.Paging"
	],
	mixins: {
		observable: "Ext.util.Observable"
	},
	columns: [],
	width: 400,
	height: 300,
	textWindow: "Drill down window",
	lockableScope: "top",
	init: function(grid) {
		var me = this;
		if (!grid.isPivotGrid) {
			Ext.raise(
				"This plugin is only compatible with Ext.pivot.Grid"
			)
		}
		me.pivot = grid;
		me.pivotListeners = me.pivot.on({
			pivotitemcelldblclick: me.runPlugin,
			pivotgroupcelldblclick: me.runPlugin,
			pivottotalcelldblclick: me.runPlugin,
			scope: me,
			destroyable: true
		});
		me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		Ext.destroyMembers(me, "view", "pivotListeners");
		me.pivot = me.view = me.pivotListeners = me.store = null;
		me.callParent(arguments)
	},
	showView: function(records) {
		var me = this,
			fields = me.pivot.getMatrix()
			.store.model.getFields(),
			columns = me.columns,
			store;
		if (!me.view) {
			store = Ext.create("Ext.data.Store", {
				pageSize: 25,
				remoteSort: true,
				fields: Ext.clone(fields),
				proxy: {
					type: "memory",
					reader: {
						type: "array"
					},
					enablePaging: true
				}
			});
			if (columns.length === 0) {
				Ext.Array.each(fields,
					function(value, index, all) {
						columns.push({
							header: Ext.String.capitalize(
								value.name),
							dataIndex: value.name
						})
					})
			}
			me.view = Ext.create("Ext.window.Window", {
				title: me.textWindow,
				width: me.width,
				height: me.height,
				layout: "fit",
				modal: true,
				closeAction: "hide",
				items: [{
					xtype: "grid",
					border: false,
					viewConfig: {
						loadMask: false
					},
					columns: columns,
					store: store,
					dockedItems: [{
						itemId: "idPager",
						xtype: "pagingtoolbar",
						store: store,
						dock: "bottom",
						displayInfo: true
					}]
				}]
			});
			me.store = store
		}
		me.store.getProxy()
			.data = records;
		me.store.load();
		me.view.show();
		me.view.down("#idPager")
			.moveFirst()
	},
	runPlugin: function(params, e, eOpts) {
		var me = this,
			matrix = me.pivot.getMatrix(),
			result;
		if (me.disabled) {
			return
		}
		if (params.topKey) {
			result = matrix.results.get(params.leftKey, params.topKey);
			if (result) {
				me.showView(result.records)
			}
		}
	}
});
Ext.define("Ext.pivot.plugin.Exporter", {
	alternateClassName: ["Mz.pivot.plugin.ExcelExport"],
	alias: ["plugin.pivotexporter", "plugin.mzexcelexport"],
	extend: "Ext.AbstractPlugin",
	requires: ["Ext.exporter.Excel"],
	lockableScope: "top",
	init: function(grid) {
		var me = this;
		if (!grid.isPivotGrid) {
			Ext.raise(
				"This plugin is only compatible with Ext.pivot.Grid"
			)
		}
		grid.saveDocumentAs = Ext.bind(me.saveDocumentAs, me);
		grid.getDocumentData = Ext.bind(me.getDocumentData, me);
		me.pivot = grid;
		return me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		me.pivot.saveDocumentAs = me.pivot.getDocumentData = me.pivot =
			me.matrix = null;
		return me.callParent(arguments)
	},
	saveDocumentAs: function(config) {
		var exporter;
		if (this.disabled) {
			return
		}
		exporter = this.getExporter.apply(this, arguments);
		exporter.saveAs();
		Ext.destroy(exporter)
	},
	getDocumentData: function(config) {
		var exporter, ret;
		if (this.disabled) {
			return
		}
		exporter = this.getExporter.apply(this, arguments);
		ret = exporter.getContent();
		Ext.destroy(exporter);
		return ret
	},
	getExporter: function(config) {
		var me = this;
		config = config || {};
		me.matrix = me.pivot.getMatrix();
		me.onlyExpandedNodes = config.onlyExpandedNodes;
		delete(config.onlyExpandedNodes);
		return Ext.Factory.exporter(Ext.apply({
				type: "excel",
				data: me.prepareData()
			},
			config))
	},
	prepareData: function() {
		var me = this,
			matrix = me.matrix,
			group, columns, headers, record, i, dataIndexes;
		if (!me.onlyExpandedNodes) {
			me.setColumnsExpanded(matrix.topAxis.getTree(), true)
		}
		columns = Ext.clone(matrix.getColumnHeaders());
		headers = me.getColumnHeaders(columns);
		dataIndexes = me.getDataIndexColumns(columns);
		if (!me.onlyExpandedNodes) {
			me.setColumnsExpanded(matrix.topAxis.getTree())
		}
		group = me.extractGroups(matrix.leftAxis.getTree(),
			dataIndexes);
		Ext.apply(group, {
			summary: [],
			text: ""
		});
		group.summary.push(matrix.textGrandTotalTpl);
		record = matrix.preparePivotStoreRecordData({
			key: matrix.grandTotalKey
		});
		for (i = 1; i < dataIndexes.length; i++) {
			group.summary.push(record[dataIndexes[i]] || "")
		}
		return {
			columns: headers,
			groups: [group]
		}
	},
	setColumnsExpanded: function(items, expanded) {
		for (var i = 0; i < items.length; i++) {
			if (Ext.isDefined(expanded)) {
				items[i].backupExpanded = items[i].expanded;
				items[i].expanded = expanded
			} else {
				items[i].expanded = items[i].backupExpanded;
				items[i].backupExpanded = null
			}
			if (items[i].children) {
				this.setColumnsExpanded(items[i].children, expanded)
			}
		}
	},
	getColumnHeaders: function(columns) {
		var cols = [],
			i,
			obj;
		for (i = 0; i < columns.length; i++) {
			obj = {
				text: columns[i].text
			};
			if (columns[i].columns) {
				obj.columns = this.getColumnHeaders(columns[i].columns)
			}
			cols.push(obj)
		}
		return cols
	},
	getDataIndexColumns: function(columns) {
		var cols = [],
			i;
		for (i = 0; i < columns.length; i++) {
			if (columns[i].dataIndex) {
				cols.push(columns[i].dataIndex)
			} else {
				if (Ext.isArray(columns[i].columns)) {
					cols = Ext.Array.merge(cols, this.getDataIndexColumns(
						columns[i].columns))
				}
			}
		}
		return cols
	},
	extractGroups: function(items, columns) {
		var me = this,
			group = {},
			i, j, doExtract, item, row, record;
		for (i = 0; i < items.length; i++) {
			item = items[i];
			if (item.record) {
				group.rows = group.rows || [];
				row = [];
				for (j = 0; j < columns.length; j++) {
					row.push(item.record.get(columns[j]) || "")
				}
				group.rows.push(row)
			} else {
				if (item.children) {
					group.groups = group.groups || [];
					row = {};
					doExtract = me.onlyExpandedNodes ? item.expanded :
						true;
					if (doExtract) {
						row = me.extractGroups(item.children,
							columns)
					}
					Ext.apply(row, {
						summary: [],
						text: item.name
					});
					row.summary.push(item.getTextTotal());
					record = me.matrix.preparePivotStoreRecordData(
						item);
					for (j = 1; j < columns.length; j++) {
						row.summary.push(record[columns[j]] || "")
					}
					group.groups.push(row)
				}
			}
		}
		return group
	}
});
Ext.define("Ext.pivot.plugin.RangeEditor", {
	alternateClassName: ["Mz.pivot.plugin.RangeEditor"],
	alias: ["plugin.pivotrangeeditor", "plugin.mzrangeeditor"],
	extend: "Ext.AbstractPlugin",
	requires: ["Ext.pivot.Grid", "Ext.window.Window",
		"Ext.form.field.Text", "Ext.form.field.Number",
		"Ext.form.field.ComboBox", "Ext.form.field.Display",
		"Ext.button.Button", "Ext.data.Store"
	],
	mixins: {
		observable: "Ext.util.Observable"
	},
	width: null,
	height: null,
	textWindowTitle: "Range editor",
	textFieldValue: "Value",
	textFieldEdit: "Field",
	textFieldType: "Type",
	textButtonOk: "Ok",
	textButtonCancel: "Cancel",
	textTypePercentage: "Percentage",
	textTypeIncrement: "Increment",
	textTypeOverwrite: "Overwrite",
	textTypeUniformly: "Uniformly",
	onBeforeRecordsUpdate: Ext.emptyFn,
	onAfterRecordsUpdate: Ext.emptyFn,
	lockableScope: "top",
	init: function(grid) {
		var me = this;
		if (!grid.isPivotGrid) {
			Ext.raise(
				"This plugin is only compatible with Ext.pivot.Grid"
			)
		}
		me.pivot = grid;
		me.pivotListeners = me.pivot.on({
			pivotitemcelldblclick: me.runPlugin,
			pivotgroupcelldblclick: me.runPlugin,
			pivottotalcelldblclick: me.runPlugin,
			scope: me,
			destroyable: true
		});
		me.callParent(arguments)
	},
	destroy: function() {
		var me = this;
		Ext.destroyMembers(me, "view", "pivotListeners");
		me.pivot = me.view = me.pivotListeners = me.currentRecord =
			me.currentCol = me.currentResult = null;
		me.callParent(arguments)
	},
	runPlugin: function(params, e, eOpts) {
		var me = this,
			matrix = me.pivot.getMatrix(),
			dataIndex;
		if (me.disabled) {
			return
		}
		if (params.topKey) {
			me.initEditorWindow();
			me.currentResult = matrix.results.get(params.leftKey,
				params.topKey);
			if (me.currentResult) {
				me.currentCol = params.column;
				dataIndex = me.currentCol.dimension.getId();
				me.view.down("form")
					.getForm()
					.setValues({
						field: me.currentCol.dimension.header ||
							me.currentCol["text"] || me.currentCol
							.dimension.dataIndex,
						value: me.currentResult.getValue(
							dataIndex),
						type: "uniformly"
					});
				me.view.show()
			}
		}
	},
	updateRecords: function() {
		var me = this,
			result = me.currentResult,
			colDef = me.currentCol,
			agg = colDef.dimension.getId(),
			dataIndex = colDef.dimension.dataIndex,
			values = me.view.down("form")
			.getForm()
			.getValues(),
			records,
			remainder = 0;
		records = result.records;
		if (me.onBeforeRecordsUpdate(me.pivot, colDef, records,
			values.value, result.getValue(agg)) === false) {
			return
		}
		me.view.getEl()
			.mask();
		values.value = parseFloat(values.value);
		Ext.defer(function() {
				Ext.Array.each(records,
					function(item) {
						var currValue = item.get(dataIndex),
							newValue,
							v;
						switch (values.type) {
							case "percentage":
								v = Math.floor(currValue *
									values.value / 100);
								break;
							case "increment":
								v = currValue + values.value;
								break;
							case "overwrite":
								v = values.value;
								break;
							case "uniformly":
								newValue = (1 / records.length *
										values.value) +
									remainder;
								v = Math.floor(newValue);
								remainder += (newValue - v);
								break
						}
						if (currValue != v) {
							item.set(dataIndex, v)
						}
					});
				me.onAfterRecordsUpdate(me.pivot, colDef,
					records, values.value, result.getValue(
						agg));
				me.view.getEl()
					.unmask();
				me.view.close()
			},
			10)
	},
	initEditorWindow: function() {
		var me = this;
		if (!me.view) {
			me.view = Ext.create("Ext.window.Window", {
				title: me.textWindowTitle,
				width: me.width,
				height: me.height,
				layout: "fit",
				modal: true,
				closeAction: "hide",
				items: [{
					xtype: "form",
					padding: 5,
					border: false,
					defaults: {
						anchor: "100%"
					},
					items: [{
							fieldLabel: me.textFieldEdit,
							xtype: "displayfield",
							name: "field"
						},
						{
							fieldLabel: me.textFieldType,
							xtype: "combo",
							name: "type",
							queryMode: "local",
							valueField: "id",
							displayField: "text",
							editable: false,
							store: Ext.create(
								"Ext.data.Store", {
									fields: [
										"id",
										"text"
									],
									data: [{
											"id": "percentage",
											"text": me
												.textTypePercentage
										},
										{
											"id": "increment",
											"text": me
												.textTypeIncrement
										},
										{
											"id": "overwrite",
											"text": me
												.textTypeOverwrite
										},
										{
											"id": "uniformly",
											"text": me
												.textTypeUniformly
										}
									]
								})
						},
						{
							fieldLabel: me.textFieldValue,
							xtype: "numberfield",
							name: "value"
						}
					]
				}],
				buttons: [{
						text: me.textButtonOk,
						handler: me.updateRecords,
						scope: me
					},
					{
						text: me.textButtonCancel,
						handler: function() {
							this.view.close()
						},
						scope: me
					}
				]
			})
		}
	}
});
Ext.define('Ext.form.FieldSetCux', {
	extend: 'Ext.container.Container',
	mixins: {
		fieldAncestor: 'Ext.form.FieldAncestor'
	},
	alias: 'widget.fieldsetCux',
	uses: ['Ext.form.field.Checkbox', 'Ext.panel.Tool',
		'Ext.layout.container.Anchor',
		'Ext.layout.component.FieldSet'
	],
	checkboxUI: 'default',
	collapsed: false,
	toggleOnTitleClick: true,
	baseCls: Ext.baseCSSPrefix + 'fieldset',
	layout: 'anchor',
	descriptionText: '{0} field set',
	expandText: 'Expand field set',
	componentLayout: 'fieldset',
	ariaRole: 'group',
	focusable: false,
	// combo:false,
	autoEl: 'fieldset',
	childEls: [
		'body'
	],
	renderTpl: [
		'{%this.renderLegend(out,values);%}',
		'<div id="{id}-body" data-ref="body" class="{baseCls}-body {baseCls}-body-{ui} {bodyTargetCls}" ',
		'role="presentation"<tpl if="bodyStyle"> style="{bodyStyle}"</tpl>>',
		'{%this.renderContainer(out,values);%}',
		'</div>'
	],
	stateEvents: ['collapse', 'expand'],
	maskOnDisable: false,
	beforeDestroy: function() {
		var me = this,
			legend = me.legend;

		if (legend) {
			// get rid of the ownerCt since it's not a proper item
			delete legend.ownerCt;
			legend.destroy();
			me.legend = null;
		}
		me.callParent();
	},

	initComponent: function() {
		var me = this,
			baseCls = me.baseCls;
		if (me.ariaRole && !me.ariaLabel) {
			me.ariaLabel = Ext.String.formatEncode(me.descriptionText,
				me.title || '');
		}
		me.ariaRenderAttributes = me.ariaRenderAttributes || {};
		me.ariaRenderAttributes['aria-expanded'] = !me.collapsed;
		me.initFieldAncestor();
		me.callParent();
		me.layout.managePadding = me.layout.manageOverflow = false;
		if (me.collapsed) {
			me.addCls(baseCls + '-collapsed');
			me.collapse();
		}
		if (me.title || me.checkboxToggle || me.collapsible) {
			me.addTitleClasses();
			me.legend = me.createLegendCt();
		}
		me.initMonitor();
	},
	initRenderData: function() {
		var me = this,
			data = me.callParent();

		data.bodyTargetCls = me.bodyTargetCls;
		me.protoBody.writeTo(data);
		delete me.protoBody;

		return data;
	},

	getState: function() {
		var state = this.callParent();

		state = this.addPropertyToState(state, 'collapsed');

		return state;
	},

	afterCollapse: Ext.emptyFn,
	afterExpand: Ext.emptyFn,

	collapsedHorizontal: function() {
		return true;
	},

	collapsedVertical: function() {
		return true;
	},

	createLegendCt: function() {
		var me = this,
			items = [],
			legendCfg = {
				baseCls: me.baseCls + '-header',
				layout: 'container',
				ui: me.ui,
				id: me.id + '-legend',
				autoEl: 'legend',
				ariaRole: null,
				items: items,
				ownerCt: me,
				shrinkWrap: true,
				ownerLayout: me.componentLayout
			},
			legend;

		// Checkbox
		if (me.checkboxToggle) {
			items.push(me.createCheckboxCmp());
		} else if (me.collapsible) {
			// Toggle button
			items.push(me.createToggleCmp());
		}

		// Title
		items.push(me.createTitleCmp());
		if (me.combo) {
			items.push(me.createComboCmp());
		}

		legend = new Ext.container.Container(legendCfg);

		legend.collapseImmune = true;
		legend.getInherited()
			.collapseImmune = true;

		return legend;
	},
	createTitleCmp: function() {
		var me = this,
			cfg = {
				html: me.title,
				ui: me.ui,
				cls: me.baseCls + '-header-text',
				id: me.id + '-legendTitle',
				ariaRole: 'presentation'
			};

		if (me.collapsible && me.toggleOnTitleClick) {
			cfg.listeners = {
				click: {
					element: 'el',
					scope: me,
					fn: me.toggle
				}
			};
			cfg.cls += ' ' + me.baseCls +
				'-header-text-collapsible';
		}

		me.titleCmp = new Ext.Component(cfg);

		return me.titleCmp;
	},
	createCheckboxCmp: function() {
		var me = this,
			suffix = '-checkbox',
			cls = me.baseCls + '-header' + suffix,
			checkboxCmp;
		cls += ' ' + cls + '-' + me.ui;
		me.checkboxCmp = checkboxCmp = new Ext.form.field.Checkbox({
			hideEmptyLabel: true,
			name: me.checkboxName || me.id + suffix,
			cls: cls,
			id: me.id + '-legendChk',
			ui: me.checkboxUI,
			checked: !me.collapsed,
			msgTarget: 'none',
			listeners: {
				change: me.onCheckChange,
				scope: me
			},
			ariaLabel: me.expandText
		});

		return checkboxCmp;
	},
	createComboCmp: function() {
		var me = this,
			comboCmp;
		var states = Ext.create('Ext.data.Store', {
			fields: ['id', 'name'],
			data: eval(me.combodata)
		});
		var vname = me.id + '-combo';
		console.log(me.unflag);
		if (me.unflag) { // modify by lvgang 2016.8.13 多标准
			vname = me.unflag;
		}
		me.comboCmp = comboCmp = Ext.create('Ext.form.ComboBox', {
			id: me.id + '-combo',
			// name:me.id + '-combo',
			name: vname,
			hideEmptyLabel: true,
			store: states,
			editable: false,
			queryMode: 'local',
			displayField: 'name',
			valueField: 'id',
			listeners: {
				select: me.comboSelect,
				scope: me
			}
		});

		return comboCmp;
	},
	comboSelect: function(c, r, e) {
		var s = c.getStore();
		for (var i = 0; i < s.getCount(); i++) {
			Ext.getCmp(s.getAt(i)
					.get('id'))
				.setHidden(true);
		}
		Ext.getCmp(r.get('id'))
			.setHidden(false);
		Ext.getCmp(r.get('id'))
			.comboCmp.setValue(r.get('id'));
	},
	createToggleCmp: function() {
		var me = this,
			toggleCmp;

		me.toggleCmp = toggleCmp = new Ext.panel.Tool({
			cacheHeight: false,
			cls: me.baseCls + '-header-tool-' + me.ui,
			type: 'toggle',
			handler: me.toggle,
			id: me.id + '-legendToggle',
			scope: me,
			ariaRole: 'checkbox',
			ariaLabel: me.expandText,
			ariaRenderAttributes: {
				'aria-checked': !me.collapsed
			}
		});

		return toggleCmp;
	},
	doRenderLegend: function(out, renderData) {
		var me = renderData.$comp,
			legend = me.legend,
			tree;
		if (legend) {
			legend.ownerLayout.configureItem(legend);
			tree = legend.getRenderTree();
			Ext.DomHelper.generateMarkup(tree, out);
		}
	},
	getCollapsed: function() {
		return this.collapsed ? 'top' : false;
	},
	getCollapsedDockedItems: function() {
		var legend = this.legend;

		return legend ? [legend] : [];
	},
	setTitle: function(title) {
		var me = this,
			legend = me.legend;

		me.title = title;
		me.ariaLabel = Ext.String.formatEncode(me.descriptionText,
			title || '');

		if (me.rendered) {
			if (!legend) {
				me.legend = legend = me.createLegendCt();
				me.addTitleClasses();
				legend.ownerLayout.configureItem(legend);
				legend.render(me.el, 0);
			}
			me.titleCmp.update(title);

			me.ariaEl.dom.setAttribute('aria-label', me.ariaLabel);
		} else if (legend) {
			me.titleCmp.update(title);
		} else {
			me.addTitleClasses();
			me.legend = me.createLegendCt();
		}
		return me;
	},

	addTitleClasses: function() {
		var me = this,
			title = me.title,
			baseCls = me.baseCls;
		if (title) {
			me.addCls(baseCls + '-with-title');
		}

		if (title || me.checkboxToggle || me.collapsible) {
			me.addCls(baseCls + '-with-legend');
		}
	},
	expand: function() {
		return this.setExpanded(true);
	},
	collapse: function() {
		return this.setExpanded(false);
	},
	setExpanded: function(expanded) {
		var me = this,
			checkboxCmp = me.checkboxCmp,
			toggleCmp = me.toggleCmp,
			operation = expanded ? 'expand' : 'collapse';

		if (!me.rendered || me.fireEvent('before' + operation, me) !==
			false) {
			expanded = !!expanded;

			if (checkboxCmp) {
				checkboxCmp.setValue(expanded);
			} else if (toggleCmp && toggleCmp.ariaEl.dom) {
				toggleCmp.ariaEl.dom.setAttribute('aria-checked',
					expanded);
			}

			if (expanded) {
				me.removeCls(me.baseCls + '-collapsed');
			} else {
				me.addCls(me.baseCls + '-collapsed');
			}

			if (me.ariaEl.dom) {
				me.ariaEl.dom.setAttribute('aria-expanded', !!
					expanded);
			}

			me.collapsed = !expanded;
			if (expanded) {
				delete me.getInherited()
					.collapsed;
			} else {
				me.getInherited()
					.collapsed = true;
			}
			if (me.rendered) {
				me.updateLayout({
					isRoot: false
				});
				me.fireEvent(operation, me);
			}
		}
		return me;
	},

	getRefItems: function(deep) {
		var refItems = this.callParent(arguments),
			legend = this.legend;
		if (legend) {
			refItems.unshift(legend);
			if (deep) {
				refItems.unshift.apply(refItems, legend.getRefItems(
					true));
			}
		}
		return refItems;
	},
	toggle: function() {
		this.setExpanded(!!this.collapsed);
	},

	privates: {
		applyTargetCls: function(targetCls) {
			this.bodyTargetCls = targetCls;
		},

		finishRender: function() {
			var legend = this.legend;

			this.callParent();

			if (legend) {
				legend.finishRender();
			}
		},

		getProtoBody: function() {
			var me = this,
				body = me.protoBody;

			if (!body) {
				me.protoBody = body = new Ext.util.ProtoElement({
					styleProp: 'bodyStyle',
					styleIsText: true
				});
			}

			return body;
		},

		getDefaultContentTarget: function() {
			return this.body;
		},

		getTargetEl: function() {
			return this.body || this.frameBody || this.el;
		},

		initPadding: function(targetEl) {
			var me = this,
				body = me.getProtoBody(),
				padding = me.padding,
				bodyPadding;

			if (padding !== undefined) {
				if (Ext.isIE8) {
					padding = me.parseBox(padding);
					bodyPadding = Ext.Element.parseBox(0);
					bodyPadding.top = padding.top;
					padding.top = 0;
					body.setStyle('padding', me.unitizeBox(
						bodyPadding));
				}

				targetEl.setStyle('padding', me.unitizeBox(padding));
			}
		},

		/**
		 * @private Handle changes in the checkbox checked state.
		 */
		onCheckChange: function(cmp, checked) {
			this.setExpanded(checked);
		},

		setupRenderTpl: function(renderTpl) {
			this.callParent(arguments);

			renderTpl.renderLegend = this.doRenderLegend;
		}
	}
});

var errors = "";
Ext.form.Basic.prototype.isValidCux = function() {
	errors = "";
	var me = this,
		invalid;
	Ext.suspendLayouts();
	invalid = me.getFields()
		.filterBy(function(field) {
			if (!field.validate()) {
				errors += field.fieldLabel.replace(
						"<font color=red>*</font>", "") +
					':<font color=red>' + field.getErrors() +
					'</font><br/>';
			}
			return !field.validate();
		});
	Ext.resumeLayouts(true);
	if ("" != errors) {
		Ext.MessageBox.alert('提示信息:', errors);
	}
	// modify by lvg ,2016.8.13 激活tab
	if (me.owner && invalid.length > 0) {
		if (me.owner.ownerCt) {
			if ('tabpanel' == me.owner.ownerCt.getXType()) {
				me.owner.ownerCt.setActiveTab(me.owner);
			}
		}
	}
	return invalid.length < 1;
};
Ext.define('Ext.form.field.CuxText', {
	extend: 'Ext.form.field.Base',
	alias: 'widget.cuxtextfield',
	requires: [
		'Ext.form.field.VTypes',
		'Ext.form.trigger.Trigger',
		'Ext.util.TextMetrics'
	],
	alternateClassName: ['Ext.form.CuxTextField', 'Ext.form.CuxText'],

	config: {
		hideTrigger: false,
		triggers: undefined
	},

	renderConfig: {
		editable: true
	},
	growMin: 30,
	growMax: 800,
	growAppend: 'W',
	allowBlank: true,
	validateBlank: false,
	allowOnlyWhitespace: true,
	minLength: 0,
	maxLength: Number.MAX_VALUE,
	minLengthText: 'The minimum length for this field is {0}',
	maxLengthText: 'The maximum length for this field is {0}',
	blankText: 'This field is required',
	regexText: '',
	emptyText: '',
	emptyCls: Ext.baseCSSPrefix + 'form-empty-field',
	requiredCls: Ext.baseCSSPrefix + 'form-required-field',
	valueContainsPlaceholder: false,
	ariaRole: 'textbox',
	repeatTriggerClick: false,
	triggerWrapCls: Ext.baseCSSPrefix + 'form-trigger-wrap',

	triggerWrapFocusCls: Ext.baseCSSPrefix + 'form-trigger-wrap-focus',
	triggerWrapInvalidCls: Ext.baseCSSPrefix +
		'form-trigger-wrap-invalid',

	fieldBodyCls: Ext.baseCSSPrefix + 'form-text-field-body',
	inputWrapCls: Ext.baseCSSPrefix + 'form-text-wrap',

	inputWrapFocusCls: Ext.baseCSSPrefix + 'form-text-wrap-focus',
	inputWrapInvalidCls: Ext.baseCSSPrefix + 'form-text-wrap-invalid',
	growCls: Ext.baseCSSPrefix + 'form-text-grow',
	enableMouseEvents: false,
	needArrowKeys: true,

	squashMouseUp: {
		mouseup: function(e) {
			this.inputEl.dom.select();
		},
		translate: false,
		single: true,
		preventDefault: true
	},

	childEls: [
		'triggerWrap',
		'inputWrap'
	],

	preSubTpl: [
		'<div id="{cmpId}-triggerWrap" data-ref="triggerWrap"',
		' role="presentation" class="{triggerWrapCls} {triggerWrapCls}-{ui}">',
		'<div id={cmpId}-inputWrap data-ref="inputWrap"',
		' role="presentation" class="{inputWrapCls} {inputWrapCls}-{ui}">'
	],

	postSubTpl: [
		'</div>', // end inputWrap
		'<tpl for="triggers">{[values.renderTrigger(parent)]}</tpl>',
		'</div>' // end triggerWrap
	],

	initComponent: function() {
		var me = this,
			emptyCls = me.emptyCls;

		if (me.allowOnlyWhitespace === false) {
			me.allowBlank = false;
		}
		if (me.size) {
			me.defaultBodyWidth = me.size * 6.5 + 20;
		}

		if (!me.onTrigger1Click) {
			me.onTrigger1Click = me.onTriggerClick;
		}

		me.callParent();

		if (me.readOnly) {
			me.setReadOnly(me.readOnly);
		}
		me.fieldFocusCls = me.baseCls + '-focus';
		me.emptyUICls = emptyCls + ' ' + emptyCls + '-' + me.ui;
		me.addStateEvents('change');
	},

	initEvents: function() {
		var me = this,
			el = me.inputEl;

		me.callParent();
		if (me.maskRe || (me.vtype && me.disableKeyFilter !== true &&
			(me.maskRe = Ext.form.field.VTypes[me.vtype +
				'Mask']))) {
			me.mon(el, 'keypress', me.filterKeys, me);
		}

		if (me.enableKeyEvents) {
			me.mon(el, {
				scope: me,
				mousedown: me.onMouseDown,
				keyup: me.onKeyUp,
				keydown: me.onKeyDown,
				keypress: me.onKeyPress
			});
		}
		if (me.enableMouseEvents) {
			me.mon(el, {
				scope: me,
				mousedown: me.onMouseDown
			});
		}
	},
	isEqual: function(value1, value2) {
		return this.isEqualAsString(value1, value2);
	},
	onChange: function(newVal, oldVal) {
		this.callParent(arguments);
		this.autoSize();
	},

	getSubTplData: function(fieldData) {
		var me = this,
			value = me.getRawValue(),
			isEmpty = me.emptyText && value.length < 1,
			maxLength = me.maxLength,
			placeholder, data, inputElAttr;
		if (me.enforceMaxLength) {
			if (maxLength === Number.MAX_VALUE) {
				maxLength = undefined;
			}
		} else {
			maxLength = undefined;
		}

		if (isEmpty) {
			if (Ext.supports.Placeholder) {
				placeholder = me.emptyText;
			} else {
				value = me.emptyText;
				me.valueContainsPlaceholder = true;
			}
		}

		data = Ext.apply(me.callParent([fieldData]), {
			triggerWrapCls: me.triggerWrapCls,
			inputWrapCls: me.inputWrapCls,
			triggers: me.orderedTriggers,
			maxLength: maxLength,
			readOnly: !me.editable || me.readOnly,
			placeholder: placeholder,
			value: value,
			fieldCls: me.fieldCls + ((isEmpty && (
					placeholder || value)) ? ' ' + me.emptyUICls :
				'') + (me.allowBlank ? '' : ' ' + me.requiredCls)
		});

		inputElAttr = data.inputElAriaAttributes;

		if (inputElAttr) {
			inputElAttr['aria-required'] = !me.allowBlank;
		}

		return data;
	},

	onRender: function() {
		var me = this,
			triggers = me.getTriggers(),
			elements = [],
			id, triggerEl;

		if (Ext.supports.FixedTableWidthBug) {
			me.el._needsTableWidthFix = true;
		}

		me.callParent();

		if (triggers) {
			this.invokeTriggers('onFieldRender');
			for (id in triggers) {
				elements.push(triggers[id].el);
			}

			triggerEl = me.triggerEl = me.triggerCell = new Ext.CompositeElement(
				elements, true);
		}
		me.inputCell = me.inputWrap;
	},

	afterRender: function() {
		var me = this;

		me.autoSize();
		me.callParent();
		me.invokeTriggers('afterFieldRender');
	},

	onMouseDown: function() {
		if (!this.hasFocus) {
			this.squashMouseUp.scope = this;
			Ext.getDoc()
				.on(this.squashMouseUp);
		}
	},

	applyTriggers: function(triggers) {
		var me = this,
			hideAllTriggers = me.getHideTrigger(),
			readOnly = me.readOnly,
			orderedTriggers = me.orderedTriggers = [],
			repeatTriggerClick = me.repeatTriggerClick,
			id, triggerCfg, trigger, triggerCls, i;

		if (!triggers) {
			triggers = {};

			if (me.triggerCls && !me.trigger1Cls) {
				me.trigger1Cls = me.triggerCls;
			}
			for (i = 1; triggerCls = me['trigger' + i + 'Cls']; i++) { // jshint
				// ignore:line
				triggers['trigger' + i] = {
					cls: triggerCls,
					extraCls: Ext.baseCSSPrefix +
						'trigger-index-' + i,
					handler: 'onTrigger' + i + 'Click',
					compat4Mode: true,
					scope: me
				};
			}
		}

		for (id in triggers) {
			if (triggers.hasOwnProperty(id)) {
				triggerCfg = triggers[id];
				triggerCfg.field = me;
				triggerCfg.id = id;
				if ((readOnly && triggerCfg.hideOnReadOnly !==
					false) || (hideAllTriggers && triggerCfg.hidden !==
					false)) {
					triggerCfg.hidden = true;
				}
				if (repeatTriggerClick && (triggerCfg.repeatClick !==
					false)) {
					triggerCfg.repeatClick = true;
				}

				trigger = triggers[id] = Ext.form.trigger.Trigger.create(
					triggerCfg);
				orderedTriggers.push(trigger);
			}
		}

		Ext.Array.sort(orderedTriggers, Ext.form.trigger.Trigger.weightComparator);

		return triggers;
	},

	invokeTriggers: function(methodName, args) {
		var me = this,
			triggers = me.getTriggers(),
			id, trigger;

		if (triggers) {
			for (id in triggers) {
				if (triggers.hasOwnProperty(id)) {
					trigger = triggers[id];
					// IE8 needs "|| []" if args is undefined
					trigger[methodName].apply(trigger, args || []);
				}
			}
		}
	},

	getTrigger: function(id) {
		return this.getTriggers()[id];
	},

	updateHideTrigger: function(hideTrigger) {
		this.invokeTriggers(hideTrigger ? 'hide' : 'show');
	},

	updateEditable: function(editable, oldEditable) {
		this.setReadOnlyAttr(!editable || this.readOnly);
	},

	setReadOnly: function(readOnly) {
		var me = this,
			triggers = me.getTriggers(),
			hideTriggers = me.getHideTrigger(),
			trigger,
			id;

		readOnly = !!readOnly;

		me.callParent([readOnly]);
		if (me.rendered) {
			me.setReadOnlyAttr(readOnly || !me.editable);
		}

		if (triggers) {
			for (id in triggers) {
				trigger = triggers[id];
				if (trigger.hideOnReadOnly === true || (trigger.hideOnReadOnly !==
					false && !hideTriggers)) {
					trigger.setVisible(!readOnly);
				}
			}
		}
	},

	/**
	 * @private Sets the readonly attribute of the input element
	 */
	setReadOnlyAttr: function(readOnly) {
		var me = this,
			readOnlyName = 'readonly',
			inputEl = me.inputEl.dom;

		if (readOnly) {
			inputEl.setAttribute(readOnlyName, readOnlyName);
		} else {
			inputEl.removeAttribute(readOnlyName);
		}

		if (me.ariaRole) {
			me.ariaEl.dom.setAttribute('aria-readonly', !!readOnly);
		}
	},

	processRawValue: function(value) {
		var me = this,
			stripRe = me.stripCharsRe,
			mod, newValue, lastValue;

		if (stripRe) {
			if (!stripRe.global) {
				mod = 'g';
				mod += (stripRe.ignoreCase) ? 'i' : '';
				mod += (stripRe.multiline) ? 'm' : '';
				stripRe = new RegExp(stripRe.source, mod);
			}

			newValue = value.replace(stripRe, '');
			if (newValue !== value) {
				me.setRawValue(newValue);
				if (me.lastValue === value) {
					me.lastValue = newValue;
				}
				value = newValue;
			}
		}
		return value;
	},

	onDisable: function() {
		this.callParent();
		if (Ext.isIE) {
			this.inputEl.dom.unselectable = 'on';
		}
	},

	onEnable: function() {
		this.callParent();
		if (Ext.isIE) {
			this.inputEl.dom.unselectable = '';
		}
	},
	onMouseDown: function(e) {
		this.fireEvent('mousedown', this, e);
	},
	onKeyDown: function(e) {
		this.fireEvent('keydown', this, e);
	},

	onKeyUp: function(e) {
		this.fireEvent('keyup', this, e);
	},

	onKeyPress: function(e) {
		this.fireEvent('keypress', this, e);
	},

	reset: function() {
		this.callParent();
		this.applyEmptyText();
	},

	applyEmptyText: function() {
		var me = this,
			emptyText = me.emptyText,
			isEmpty;

		if (me.rendered && emptyText) {
			isEmpty = me.getRawValue()
				.length < 1 && !me.hasFocus;

			if (Ext.supports.Placeholder) {
				me.inputEl.dom.placeholder = emptyText;
			} else if (isEmpty) {
				me.setRawValue(emptyText);
				me.valueContainsPlaceholder = true;
			}

			// all browsers need this because of a styling issue with chrome +
			// placeholders.
			// the text isnt vertically aligned when empty (and using the
			// placeholder)
			if (isEmpty) {
				me.inputEl.addCls(me.emptyUICls);
			} else {
				me.inputEl.removeCls(me.emptyUICls);
			}

			me.autoSize();
		}
	},

	getEmptyText: function() {
		return this.emptyText;
	},

	setEmptyText: function(value) {
		var me = this,
			inputEl = me.inputEl,
			inputDom = inputEl && inputEl.dom,
			emptyText = value || '';

		if (value) {
			me.emptyText = emptyText;
			me.applyEmptyText();
		} else if (inputDom) {
			if (Ext.supports.Placeholder) {
				inputDom.removeAttribute('placeholder');
			} else {
				if (inputDom.value !== me.getRawValue()) {
					// only way these are !== is if emptyText is in the
					// dom.value
					inputDom.value = '';
					inputEl.removeCls(me.emptyUICls);
				}
			}
			// value is null so it cannot be the input value:
			me.valueContainsPlaceholder = false;
		}
		// This has to be added at the end because getRawValue depends on
		// the emptyText value to return an empty string or not in legacy
		// browsers.
		me.emptyText = emptyText;
		return me;
	},

	afterFirstLayout: function() {
		this.callParent();
		if (Ext.isIE && this.disabled) {
			var el = this.inputEl;
			if (el) {
				el.dom.unselectable = 'on';
			}
		}
	},

	/**
	 * @private
	 */
	toggleInvalidCls: function(hasError) {
		var method = hasError ? 'addCls' : 'removeCls';

		this.callParent();

		this.triggerWrap[method](this.triggerWrapInvalidCls);
		this.inputWrap[method](this.inputWrapInvalidCls);
	},

	beforeFocus: function() {
		var me = this,
			inputEl = me.inputEl,
			emptyText = me.emptyText,
			isEmpty;

		me.callParent(arguments);
		if ((emptyText && !Ext.supports.Placeholder) && (inputEl.dom
			.value === me.emptyText && me.valueContainsPlaceholder
		)) {
			me.setRawValue('');
			isEmpty = true;
			inputEl.removeCls(me.emptyUICls);
			me.valueContainsPlaceholder = false;
		} else if (Ext.supports.Placeholder) {
			inputEl.removeCls(me.emptyUICls);
		}
	},

	onFocus: function(e) {
		var me = this;

		me.callParent(arguments);
		if (me.selectOnFocus) {
			me.inputEl.dom.select();
		}

		if (me.emptyText) {
			me.autoSize();
		}

		me.addCls(me.fieldFocusCls);
		me.triggerWrap.addCls(me.triggerWrapFocusCls);
		me.inputWrap.addCls(me.inputWrapFocusCls);
		me.invokeTriggers('onFieldFocus', [e]);
	},

	/**
	 * @private
	 */
	onBlur: function(e) {
		var me = this;

		me.callParent(arguments);

		me.removeCls(me.fieldFocusCls);
		me.triggerWrap.removeCls(me.triggerWrapFocusCls);
		me.inputWrap.removeCls(me.inputWrapFocusCls);
		me.invokeTriggers('onFieldBlur', [e]);
	},

	completeEdit: function(e) {
		this.callParent([e]);
		this.applyEmptyText();
	},

	/**
	 * @private
	 */
	filterKeys: function(e) {
		if ((e.ctrlKey && !e.altKey) || e.isSpecialKey()) {
			return;
		}
		var charCode = String.fromCharCode(e.getCharCode());
		if (!this.maskRe.test(charCode)) {
			e.stopEvent();
		}
	},

	getState: function() {
		return this.addPropertyToState(this.callParent(), 'value');
	},

	applyState: function(state) {
		this.callParent(arguments);
		if (state.hasOwnProperty('value')) {
			this.setValue(state.value);
		}
	},

	getRawValue: function() {
		var me = this,
			v = me.callParent();
		if (v === me.emptyText && me.valueContainsPlaceholder) {
			v = '';
		}
		return v;
	},

	setValue: function(value) {
		var me = this,
			inputEl = me.inputEl;

		if (inputEl && me.emptyText && !Ext.isEmpty(value)) {
			inputEl.removeCls(me.emptyUICls);
			me.valueContainsPlaceholder = false;
		}

		me.callParent(arguments);

		me.applyEmptyText();
		return me;
	},

	getErrors: function(value) {
		value = arguments.length ? (value == null ? '' : value) :
			this.processRawValue(this.getRawValue());

		var me = this,
			errors = me.callParent([value]),
			validator = me.validator,
			vtype = me.vtype,
			vtypes = Ext.form.field.VTypes,
			regex = me.regex,
			format = Ext.String.format,
			msg, trimmed, isBlank;

		if (Ext.isFunction(validator)) {
			msg = validator.call(me, value);
			if (msg !== true) {
				errors.push(msg);
			}
		}

		trimmed = me.allowOnlyWhitespace ? value : Ext.String.trim(
			value);

		if (trimmed.length < 1 || (value === me.emptyText && me.valueContainsPlaceholder)) {
			if (!me.allowBlank) {
				errors.push(me.blankText);
			}
			// If we are not configured to validate blank values, there cannot
			// be any additional errors
			if (!me.validateBlank) {
				return errors;
			}
			isBlank = true;
		}
		if (!isBlank && value.length < me.minLength) {
			errors.push(format(me.minLengthText, me.minLength));
		}

		if (value.length > me.maxLength) {
			errors.push(format(me.maxLengthText, me.maxLength));
		}

		if (vtype) {
			if (!vtypes[vtype](value, me)) {
				errors.push(me.vtypeText || vtypes[vtype + 'Text']);
			}
		}

		if (regex && !regex.test(value)) {
			errors.push(me.regexText || me.invalidText);
		}

		return errors;
	},
	selectText: function(start, end) {
		var me = this,
			el = me.inputEl.dom,
			v = el.value,
			len = v.length,
			range;

		if (len > 0) {
			start = start === undefined ? 0 : Math.min(start, len);
			end = end === undefined ? len : Math.min(end, len);

			if (el.setSelectionRange) {
				el.setSelectionRange(start, end);
			} else if (el.createTextRange) {
				range = el.createTextRange();
				range.moveStart('character', start);
				range.moveEnd('character', end - len);
				range.select();
			}
		}
	},
	getGrowWidth: function() {
		return this.inputEl.dom.value;
	},
	autoSize: function() {
		var me = this,
			triggers, triggerId, triggerWidth, inputEl, width,
			value;

		if (me.grow && me.rendered && me.getSizeModel()
			.width.auto) {
			inputEl = me.inputEl;
			triggers = me.getTriggers();
			triggerWidth = 0;

			value = Ext.util.Format.htmlEncode(
				me.getGrowWidth() || (me.hasFocus ? '' : me.emptyText) ||
				''
			);
			value += me.growAppend;

			for (triggerId in triggers) {
				triggerWidth += triggers[triggerId].el.getWidth();
			}

			width = inputEl.getTextWidth(value) + triggerWidth +
				// The element that has the border depends on theme - inputWrap
				// (classic)
				// or triggerWrap (neptune)
				me.inputWrap.getBorderWidth('lr') + me.triggerWrap.getBorderWidth(
					'lr');

			width = Math.min(Math.max(width, me.growMin), me.growMax);

			me.bodyEl.setWidth(width);

			me.updateLayout();

			me.fireEvent('autosize', me, width);
		}
	},

	onDestroy: function() {
		var me = this;

		me.invokeTriggers('destroy');
		Ext.destroy(me.triggerRepeater);

		me.callParent();
	},

	onTriggerClick: Ext.emptyFn,

	privates: {
		/**
		 * @private
		 */
		getTdType: function() {
			return 'textfield';
		}
	},

	deprecated: {
		5: {
			methods: {
				getTriggerWidth: function() {
					var triggers = this.getTriggers(),
						width = 0,
						id;
					if (triggers && this.rendered) {
						for (id in triggers) {
							if (triggers.hasOwnProperty(id)) {
								width += triggers[id].el.getWidth();
							}
						}
					}

					return width;
				}
			}
		}
	}

});

function querySelect(gird) {
	var paramStr = '{';
	var k = 0;
	for (i = 0; i < gird.columns.length; i++) {
		var c = gird.columns[i];

		if (c.dataIndex) {
			var cf = gird.id + 'AA' + c.dataIndex.replace('$', 'AA');
			var kf = Ext.getCmp(cf);
			if (typeof(kf.getValue()) == 'number' || kf.getValue()) {
				var tmp = cf.split('AA');
				var v = kf.getValue();
				if ((v + '')
					.indexOf("UTC") > -1 || (v + '')
					.indexOf("GMT") > -1) {
					v = v.DateToString("full");
				} else {
					v = encodeURIComponent(v);
				}
				// console.log(cf+','+tmp[0]+','+tmp[1]+','+tmp[2]);
				var op = tmp[2] == 'string' ? 'alllike' : 'alllike';
				if (k == 0) {
					paramStr += '"' + tmp[1] + '$' + tmp[2] + '$' + op + '":"' +
						v + '"';
				} else {
					paramStr += ',"' + tmp[1] + '$' + tmp[2] + '$' + op + '":"' +
						v + '"';
				}
				k++;
			}
		}
	}
	paramStr += '}';
	// console.log(paramStr);
	gird.getStore()
		.load({
			params: {
				start: 0,
				limit: 50,
				otherParam: paramStr
			}
		});
	gird.getStore()
		.lastOptions.params.otherParam = paramStr;
}