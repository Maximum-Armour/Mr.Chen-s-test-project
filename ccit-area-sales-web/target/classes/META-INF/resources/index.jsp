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
    <title>地销管理系统-导航栏</title>
    <script type="text/javascript" src="/common/js/jquery-3.5.0.min.js"></script>
	<script type="text/javascript" src="/common/js/common.js"></script>
	<script type="text/javascript">
		function gotoPage(url) {
		    window.open(url, "_blank");
		}
	</script>
	<style type="text/css">
		.wrapper {
			display: flex;
			flex-direction: column;
		}
		
		span {
			border: 6px solid #117391;
			border-bottom: none;
			border-right: none;
			border-top: none;
			padding-left: 10px;
		}
		
		button {
			width: 120px;
			height: 40px;
			line-height: 35px;
			color: #5c5c5c;
			text-align: center;
			border-radius: 20px;
			cursor: pointer;
		}
		
		.title {
			padding: 10px;
		}
		
		.menu {
			padding: 10px;
		}
	</style>
</head>
<body>
<div class="wrapper">
	<div class="title">
		<span>产品档案</span>
	</div>
	<div class="menu">
		<button onclick="gotoPage('/main/productClassList.html')">产品分类</button>
		<button onclick="gotoPage('/main/skufileList.html')">牌号档案</button>
		<button onclick="gotoPage('/main/materielList.html')">产品档案</button>
		<button onclick="gotoPage('/main/depotList.html')">仓库档案</button>
		<button onclick="gotoPage('/main/deliveryPlaceClassList.html')">提货地分类</button>
		<button onclick="gotoPage('/main/deliveryPlaceList.html')">提货地档案</button>
		<button onclick="gotoPage('/main/salesAreaList.html')">销售区域</button>
	</div>
	<div class="title">
		<span>定价及挂牌</span>
	</div>
	<div class="menu">
		<button onclick="gotoPage('/main/priceSchemeList.html')">定价方案</button>
		<button onclick="gotoPage('/main/productPriceList.html')">产品定价</button>
		<button onclick="gotoPage('/main/listedList.html')">挂牌管理</button>
	</div>
</div>
</body>
</html>