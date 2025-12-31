<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <title>地销管理系统</title>
    <script type="text/javascript" src="/common/js/jquery-3.5.0.min.js"></script>
    <script type="text/javascript" src="/common/ext6/build/ext-all.js"></script>
	<script type="text/javascript" src="/common/ext6/build/uxplugin.js"></script>
	<script type="text/javascript" src="/common/ext6/build/classic/locale/locale-zh_CN1.js"></script>
    <script type="text/javascript" src="/common/js/common.js"></script>
    <script type="text/javascript" src="/common/js/ext6-plugins.js"></script>
	<link rel="stylesheet" type="text/css" href="/common/css/button.css" />
	<link rel="stylesheet" type="text/css" href="/common/css/common.css" />
	<link rel="stylesheet" type="text/css" href="/common/ext6/build/examples/resources/Sencha-Examples/style.css" />
	<link rel="stylesheet" type="text/css" href="/common/ext6/build/examples/kitchensink/triton-en/lib/prettify/prettify.css" />
	<link rel="stylesheet" type="text/css" href="/common/ext6/build/examples/kitchensink/triton-en/resources/ux-KitchenSink-all.css" />
	<script type="text/javascript">
		Ext.application({
		    name: "MyExtJs",
		    launch: function() {
		        Ext.create("Ext.container.Viewport", {
		        	layout: "fit",
		            items: {
		            	flex: 1,
		            	layout: "fit",
			            id: "content",
		            	renderTo: "wrapper",
		            }
		        });
		    }
		});
		Ext.onReady(function() {
			crrWidth = document.body.clientWidth;
			crrHeight = document.body.clientHeight;
			var content = Ext.getCmp("content");
			handleRoute('${code}', "", function(res) { var entrance = null; eval(res.data); content.add(entrance); });
		});
	</script>
</head>
<body>
	<div id="wrapper"></div>
</body>
</html>