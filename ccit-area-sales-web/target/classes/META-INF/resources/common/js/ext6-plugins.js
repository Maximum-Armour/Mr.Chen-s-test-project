/**
 * 分页插件
 */
Ext.define('CustomPagingToolbar', {
	extend: 'Ext.toolbar.Paging',
	alias: 'widget.pagingtoolbarcustom',
	updatePagingInfo: function(totalCount, pageSize) {
		this.store.totalCount = totalCount;
		this.onLoad();
		var displayItem = this.child("#displayItem");
		pageSize = (pageSize < totalCount ? pageSize : totalCount);
        displayItem.setText(Ext.String.format(this.displayMsg, 1, pageSize, totalCount));
	}
});

/**
 * 下拉框插件
 */
Ext.define('Ext.ux.ComboPageSize', {
	requires: [ 'Ext.form.field.ComboBox' ],
	pageSizes: [ 50, 100, 200, 300, 500, 1000 ],
	constructor: function(config) {
		if (config) {
			Ext.apply(this, config);
		}
	},
	init: function(pbar) {
		var combo, me = this;
		combo = Ext.widget('combo', {
			width: 90,
			editable: false,
			store: me.pageSizes,
			listeners: {
				change: function(s, v) {
					pbar.store.pageSize = v;
					if (pbar.store.lastOptions && pbar.store.lastOptions.params) {
						var s = pbar.store.lastOptions.params;
						pbar.store.loadPage(1, {
							params : {
								otherParam : s.otherParam 
							}
						});
					} else {
						pbar.store.loadPage(1);
					}
				}
			}
		});
		pbar.add('-');
		pbar.add('每页显示', combo, '条');
		combo.setValue(pbar.store.pageSize);
	}
});

/**
 * 树下拉框插件
 */
Ext.define('Ext.ux.ComboBoxTree', {
	extend: 'Ext.form.field.Picker',
	alias: ['widget.treecombo'],
	config: { 
        maxPickerWidth: 200, 
        maxPickerHeight: 200, 
        minPickerHeight: 100
    },
    editable: false,
	initComponent: function() {
		var self = this;
		Ext.apply(self, {
			fieldLabel: self.fieldLabel,
			labelWidth: self.labelWidth
		});
		self.callParent();
		this.on('expand', this.onPickerExpand, this);
		this.treePanel = Ext.create('Ext.tree.Panel', {
			width: self.maxPickerWidth, 
			height: self.maxPickerHeight,
			autoScroll: true,
			floating: true,
			focusOnToFront: false,
			shadow: false,
      		useArrows: true,
			store: this.store,
			rootVisible: false,
			animate: true,
			split: true
		});
		this.treePanel.on('itemclick', function (view, record) {
        	self.setRawValue(record.get('id'));
    		self.setValue(record.get('text'));
    		var data = {
    			parentId: record.get('id'),
    			productClassNo: record.get('productClassNo')
    		}
    		self.callback(data);
    		self.collapse();
    	});
		this.treePanel.on('beforeload', function(node) {
			if (self.expands().id != null) {
				self.treePanel.getStore().proxy.extraParams = {notId: self.expands().id};
			}
		});
	},
	createPicker : function() {
		var self = this;
		self.picker = this.treePanel;
		self.picker.on({});
		return self.picker;
	},
	onPickerExpand: function(picker) {
		var self = this;
		if (self.expands().id != null) {
			this.treePanel.getStore().load({
				params: {
					notId: self.expands().id
				}
			});
		}
		var record = 0;
		if (self.expands().parentId != null) {
			record = this.treePanel.getStore().findRecord('id', self.expands().parentId);
		} else if (self.expands().productClassNo != null) {
			record = this.treePanel.getStore().findRecord('productClassNo', self.expands().productClassNo);
		}
		this.treePanel.getSelectionModel().select(record);
    },
	alignPicker : function() {
		var me = this, picker, isAbove, aboveSfx = '-above';
		if (this.isExpanded) {
			picker = me.getPicker();
			if (me.matchFieldWidth) {
				picker.setWidth(me.bodyEl.getWidth());
			}
			if (picker.isFloating()) {
				picker.alignTo(me.inputEl, "", me.pickerOffset);
				isAbove = picker.el.getY() < me.inputEl.getY();
				me.bodyEl[isAbove ? 'addCls' : 'removeCls'](me.openCls + aboveSfx);
				picker.el[isAbove ? 'addCls' : 'removeCls'](picker.baseCls + aboveSfx);
			}
		}
	}
});

/**
 * 日期格式插件
 */
Ext.define('Swdp.picker.DateTime', {  
    extend: 'Ext.picker.Date',
    alias: 'widget.datetimepicker', 
    requires: ['Ext.form.field.Number'],
    okText: '确定',
    okTip: '确定',
    renderTpl: [
	     '<div id="{id}-innerEl" data-ref="innerEl" role="presentation">',
	         '<div class="{baseCls}-header">',
	             '<div id="{id}-prevEl" data-ref="prevEl" class="{baseCls}-prev {baseCls}-arrow" role="presentation" title="{prevText}"></div>',
	             '<div id="{id}-middleBtnEl" data-ref="middleBtnEl" class="{baseCls}-month" role="heading">{%this.renderMonthBtn(values, out)%}</div>',
	             '<div id="{id}-nextEl" data-ref="nextEl" class="{baseCls}-next {baseCls}-arrow" role="presentation" title="{nextText}"></div>',
	         '</div>',
	         '<table role="grid" id="{id}-eventEl" data-ref="eventEl" class="{baseCls}-inner" cellspacing="0" tabindex="0">',
	             '<thead>',
	                 '<tr role="row">',
	                     '<tpl for="dayNames">',
	                         '<th role="columnheader" class="{parent.baseCls}-column-header" aria-label="{.}">',
	                             '<div role="presentation" class="{parent.baseCls}-column-header-inner">{.:this.firstInitial}</div>',
	                         '</th>',
	                     '</tpl>',
	                 '</tr>',
	             '</thead>',
	             '<tbody>',
	                 '<tr role="row">',
	                     '<tpl for="days">',
	                         '{#:this.isEndOfWeek}',
	                         '<td role="gridcell">',
	                             '<div hidefocus="on" class="{parent.baseCls}-date"></div>',
	                         '</td>',
	                     '</tpl>',
	                 '</tr>',
	             '</tbody>',
	         '</table>',
	         '<table id="{id}-timeEl" style="table-layout:auto;width:auto;margin:0 3px;" class="x-datepicker-inner" cellspacing="0">',  
	         '<tbody><tr>',  
	             '<td>{%this.renderHourBtn(values,out)%}</td>',  
	             '<td>{%this.renderMinuteBtn(values,out)%}</td>',  
	             '<td>{%this.renderSecondBtn(values,out)%}</td>',  
	         '</tr></tbody>',  
	     '</table>',   
	         '<tpl if="showToday">',
	             '<div id="{id}-footerEl" data-ref="footerEl" role="presentation" class="{baseCls}-footer">{%this.renderOkBtn(values, out)%}{%this.renderTodayBtn(values, out)%}{%this.renderCloseBtn(values, out)%}</div>',
	         '</tpl>',
	         '<div id="{id}-todayText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{todayText}.</div>',
	         '<div id="{id}-ariaMinText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaMinText}.</div>',
	         '<div id="{id}-ariaMaxText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaMaxText}.</div>',
	         '<div id="{id}-ariaDisabledDaysText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaDisabledDaysText}.</div>',
	         '<div id="{id}-ariaDisabledDatesText" class="' + Ext.baseCSSPrefix + 'hidden-clip">{ariaDisabledDatesText}.</div>',
	     '</div>',
	     {
            firstInitial: function(value) {
                return Ext.picker.Date.prototype.getDayInitial(value);
            },
            isEndOfWeek: function(value) {
                value--;
                var end = value % 7 === 0 && value !== 0;
                return end ? '</tr><tr role="row">' : '';
            },
            renderTodayBtn: function(values, out) {
                Ext.DomHelper.generateMarkup(values.$comp.todayBtn.getRenderTree(), out);
            },renderHourBtn: function(values, out) {  
                Ext.DomHelper.generateMarkup(values.$comp.hourBtn.getRenderTree(), out);
            },  
            renderMinuteBtn: function(values, out) {  
                Ext.DomHelper.generateMarkup(values.$comp.minuteBtn.getRenderTree(), out);  
            },  
            renderSecondBtn: function(values, out) {  
                Ext.DomHelper.generateMarkup(values.$comp.secondBtn.getRenderTree(), out);  
            },  
            renderOkBtn: function(values, out) {  
                Ext.DomHelper.generateMarkup(values.$comp.okBtn.getRenderTree(), out);  
            },
            renderCloseBtn: function(values, out) {  
                Ext.DomHelper.generateMarkup(values.$comp.closeBtn.getRenderTree(), out);  
            },
            renderMonthBtn: function(values, out) {
                Ext.DomHelper.generateMarkup(values.$comp.monthBtn.getRenderTree(), out);
            }
	     }
	],
	beforeRender: function() {
   	 var me = this;
   	 var picker = me;
   	 me.hourBtn=Ext.create('Ext.form.field.Number',{ 
            minValue:0,  
            maxValue:23,
            step:1, 
            width:100 

        });   
        me.minuteBtn=Ext.create('Ext.form.field.Number',{  
            minValue:0,  
            maxValue:59, 
            step:1,  
            width:100,  
            labelWidth:10,  
            fieldLabel:'&nbsp;'  
        });  
        me.secondBtn=Ext.create('Ext.form.field.Number',{  
            minValue:0,  
            maxValue:59,
            step:1,  
            width:100,  
            labelWidth:10,  
            fieldLabel:'&nbsp;'
        });
        me.okBtn =Ext.create('Ext.button.Button',{  
            ownerCt: me,  
            ownerLayout: me.getComponentLayout(),  
            text: me.okText,  
            tooltip: me.okTip,  
            tooltipType:'title',  
            handler:me.okHandler,
            scope: me  
        });
        me.closeBtn =Ext.create('Ext.button.Button', {  
            ownerCt: me,  
            ownerLayout: me.getComponentLayout(),  
            text: '关闭',  
            tooltip: me.okTip,  
            tooltipType:'title',  
            handler:me.closeHandler,
            scope: me  
        }); 
        me.callParent();
	},
	privates: {
		finishRenderChildren: function () {  
			var me = this;  
			me.hourBtn.finishRender(); 
			me.minuteBtn.finishRender();  
			me.secondBtn.finishRender(); 
			me.okBtn.finishRender(); 
			me.closeBtn.finishRender(); 
			me.callParent();  
		}
	}, 
	okHandler : function() {  
		var me = this,  
		btn = me.okBtn;  
		if (btn && !btn.disabled) {  
			me.setValue(this.getValue());  
			me.fireEvent('select', me, me.value);  
			me.onSelect();  
			me.pickerField.collapse1();
		}  
		return me;  
	},  
	closeHandler : function(){  
		var me = this,  
		btn = me.closeBtn;  
		if (btn && !btn.disabled) {  
			me.pickerField.collapse1();
		}  
		return me;  
	},
	setValue : function(date) {  
		var me = this;  
		var d=new Date(); 
		if(me.hourBtn.getValue()==null){me.hourBtn.setValue(d.getHours());}
		if(me.minuteBtn.getValue()==null){me.minuteBtn.setValue(d.getMinutes());}
		if(me.secondBtn.getValue()==null){me.secondBtn.setValue(0);}
		date.setHours(me.hourBtn.getValue());  
		date.setMinutes(me.minuteBtn.getValue());  
		date.setSeconds(0);  
		me.value = date;  
		me.update(me.value);  
		return me;  
	},
	onSelect: function() {
		var me = this;  
		me.callParent();
	}
});

/**
 * 格式：Y-m-d H:i:s
 * 日期格式插件
 */
Ext.define('Swdp.form.field.DateTime', {  
    extend: 'Ext.form.field.Date',  
    alias: 'widget.datetimefield',  
    requires: ['Swdp.picker.DateTime'],  
    format: "Y-m-d H:i:s",  
    altFormats: "Y-m-d H:i:s",
    collapse: function() {},
    collapse1: function() {
        var me = this;
        if (me.isExpanded && !me.destroyed && !me.destroying) {
       	 var openCls = me.openCls,
            picker = me.picker,
            aboveSfx = '-above';
            picker.hide();
            me.isExpanded = false;
            me.bodyEl.removeCls([openCls, openCls + aboveSfx]);
            picker.el.removeCls(picker.baseCls + aboveSfx);
            if (me.ariaRole) {
                me.ariaEl.dom.setAttribute('aria-expanded', false);
            }
            me.hideListeners.destroy();
            Ext.un('resize', me.alignPicker, me);
            me.fireEvent('collapse', me);
            me.onCollapse();
        }
    },
    createPicker: function() {  
        var me = this,  
        format = Ext.String.format;  
        return new Swdp.picker.DateTime({  
            pickerField: me,
            ownerCt: me.ownerCt,  
            renderTo: document.body,  
            floating: true,  
            hidden: true,  
            focusOnShow: true,  
            minDate: me.minValue,  
            maxDate: me.maxValue,  
            disabledDatesRE: me.disabledDatesRE,  
            disabledDatesText: me.disabledDatesText,  
            disabledDays: me.disabledDays,  
            disabledDaysText: me.disabledDaysText,  
            format: me.format,  
            showToday: me.showToday,  
            startDay: me.startDay,  
            minText: format(me.minText, me.formatDate(me.minValue)),  
            maxText: format(me.maxText, me.formatDate(me.maxValue)),  
            listeners: {  
                scope: me,  
                select: me.onSelect  
            },  
            keyNavConfig: {  
               esc: function() {  
                    me.collapse();  
                }  
            }  
        });  
    }
});