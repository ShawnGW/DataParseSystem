<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/11/9
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>V-Test Data analysis System</title>
    <link href="<c:url value="/resources/system/css/latin.css"/>" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/system/css/normalize.css"/>" /><!--CSS RESET-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/system/css/htmleaf-demo.css"/>"/><!--演示页面样式，使用时可以不引用-->
    <!--Material Design Iconic Font-->
    <link rel="stylesheet" href="<c:url value="/resources/system/material-design/css/material-design-iconic-font.css"/>" />
    <!--jSide Menu CSS-->
    <link rel="stylesheet" href="<c:url value="/resources/system/css/jside-menu.css"/>" />
    <!--jSide Skins-->
    <link rel="stylesheet" href="<c:url value="/resources/system/css/jside-skins.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/demo-only.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/button.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/sb-admin-2.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/Font/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>">

    <link rel="stylesheet" href="<c:url value="/resources/system/css/jquery-dataTables-min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/objects.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/status-select.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/system/css/tester-select.css"/>" />

    <script src="<c:url value="/resources/system/js/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/jquery.jside.menu.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/demo-only.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>

    <script src="<c:url value="/resources/system/js/mapping.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/system/js/highcharts.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/exporting.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts-zh_CN.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/highcharts-more.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/boost.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/boost-canvas.js"/>" type="text/javascript" charset="utf-8"></script>


    <script src="<c:url value="/resources/system/js/jquery.cxselect.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/pageChange.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/jquery-dataTables-min.js"/>" type="text/javascript" charset="utf-8"></script>
    <script src="<c:url value="/resources/system/js/toStatus.js"/>" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            $(".menu-container").jSideMenu({
                jSidePosition: "position-left", //possible options position-left or position-right
                jSideSticky: true, // menubar will be fixed on top, false to set static
                jSideSkin: "default-skin", // to apply custom skin, just put its name in this string
            });
        });
    </script>
</head>
<body>
<menu class="menubar">
    <menuitem>
        <button class="menu-trigger"> </button>
    </menuitem>
    <menuitem>
        <ul class="navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw" style="color: white"></i> <i class="fa fa-caret-down" style="color: white"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li>
                        <a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                    </li>
                    <li>
                        <a href="#" onclick="contentChange()"><i class="fa fa-gear fa-fw"></i> Settings</a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="loginOff"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                    </li>
                </ul>
            </li>
        </ul>
    </menuitem>
    <menuitem class="logo">
        <a href="#1"><img src="<c:url value="/resources/system/image/logo.PNG"/>" alt="v-test" /> </a>
    </menuitem>
</menu>
<div class="menu-head">
	   <span class="layer">
	<div class="col">
	  <div class="row for-pic">
	   <div class="profile-pic">
	   <img src="<c:url value="/resources/system/image/cat.gif"/>" alt=${SYSTEM_USER.userName} />
	         </div>
	  </div>
	         <div class="row for-name">
	   <h3 title="User Name"> ${SYSTEM_USER.userName} </h3>
	        <span class="tagline">welcome you!</span>
	          </div>
	         </div> <!--//col-->
	  </span>
</div> <!--//menu-head-->
<nav class="menu-container">
    <ul class="menu-items">
        <li><span class="item-icon"><i class="zmdi zmdi-android"></i></span> <a href="#1">
            Main item one </a></li>
        <li> <span class="item-icon"> <i class="zmdi zmdi-apple"></i> </span> <a href="#1">
            Main item two </a></li>
        <li class="has-sub"> <span class="item-icon"> <i class="fa fa-bar-chart" style="color: white"></i> </span>
            <span class="dropdown-heading">Data Analysis</span>
            <ul>
                <li><a target="_blank" href="<c:url value="/Navigation/LotLineChart"/>">Lot_line</a> </li>
                <li><a href="javascripts:void(0)" onclick="testerChange()">Tester_status </a> </li>
                <li><a href="javascripts:void(0)" onclick="databaseChange()">Data_summary</a> </li>
                <li><a target="_blank" href="<c:url value="/Navigation/superposition"/>">Superposition</a> </li>
            </ul>
        </li>
        <li class="has-sub"> <span class="item-icon"> <i class="zmdi zmdi-devices"></i> </span>
            <span class="dropdown-heading">tools</span>
            <ul>
               <li><a href="<c:url value="/tools/DegreeTrans"/>">Degree Transform</a> </li>
            </ul>
        </li>
        <li> <span class="item-icon"> <i class="zmdi zmdi-keyboard"></i> </span> <a href="#1">
            Main item four </a></li>
        <li> <span class="item-icon"> <i class="fa fa-bar-chart" style="color: white"></i> </span> <a href="#1">
            Main item five </a></li>
        <li>  <span class="item-icon"><i class="fa fa-envelope fa-fw" style="color: white"></i></span> <a href="#1">
            Main item five </a></li>
        <li> <span class="item-icon"> <i class="fa fa-download" style="color: white"></i> </span> <a href="#1">
            Software Dowload </a></li>
    </ul>
</nav>
<div class="dim-overlay"></div>
<header class="intro">
    <h1>DATA ANALYSIS SYSTEM(Inner Version V1.0)</h1>
</header>
<section class="main-content">
    <div id="statusPage" style="display: none;">
        <div class="panel-heading">
            <h1>测试机状态</h1>
        </div>
        <div class="panel-body">
            <div style="width: 70%;float: left;">
                <div class="panel panel-default">
                    <div class="panel-heading class1">
                        报错的测试机
                        <button class="button button-circle button-tiny " style="float: right;" data-toggle="collapse" data-target="#demo"><i class="fa fa-minus-circle"></i></button>
                    </div>
                    <div class="panel-body" id="badTester">
                    </div>
                    <div class="panel-body collapse in" id="demo">
                        <button class="button button-caution button-primary button-pill button-small ">TTM-01</button>
                        <button class="button button-caution button-primary button-pill button-small ">TTM-02</button>
                        <button class="button button-caution button-primary button-pill button-small ">TTM-03</button>
                        <button class="button button-caution button-primary button-pill button-small ">TTM-04</button>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa"></i>正常的测试机
                        <button class="button button-circle button-tiny " style="float: right;" data-toggle="collapse" data-target="#demo2"><i class="fa fa-minus-circle"></i></button>
                    </div>
                    <div class="panel-body collapse in" id="demo2">
                    </div>
                </div>
            </div>
            <div style="width: 30%;float: right;">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-file-text"></i> RawData详细信息
                    </div>
                    <div class="panel-body">
                        <table class="table table-striped table-bordered table-hover display" style="width: 100%"  id="example3">

                        </table>
                    </div>
                    <div class="panel-footer">
                        <div id="container1" style="width:100px;height:100px"></div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <div id="dataBasePage" style="display: none;">
        <div class="panel panel-default">
            <div class="panel-heading">
                Test DataBase
            </div>
            <div class="panel-body">
                <div id="testSelect" style="width: 70%;float: left;">
                    <select class="customer_code" id="customer_code">
                    </select>
                    <select class="device" id="device">
                    </select>
                    <select class="lot_id" id="lot_id">
                    </select>
                    <select class="cp_step" id="cp_step">
                    </select>
                    <select class="wafer_no" id="wafer_no">
                    </select>
                </div>
                <div style="width: 30%;float: right;">
                    <button class="button button-raised button-highlight button-pill" id="findData" type="button">查找 <i class="fa fa-search"></i></button>
                    <button class="button button-raised button-royal button-pill" id="refresh" type="button">刷新 <i class="fa fa fa-refresh"></i></button>
                </div>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover display" style="width:100%" id="example">
                    <thead>
                    <tr>
                        <th>Details</th>
                        <%--<th>Code</th>--%>
                        <th>Device</th>
                        <th>Lot</th>
                        <th>CP</th>
                        <th>Wafer</th>
                        <th>Gross</th>
                        <th>Pass</th>
                        <th>Fail</th>
                        <th>Yield</th>
                        <th>Tester</th>
                        <th>Prober</th>
                        <th>ProberCard</th>
                        <th>StartTime</th>
                        <th>EndTime</th>
                    </tr>
                    </thead>
                </table>

            </div>
        </div>
    </div>
    <div id="content" style="display: none">
        <main>
            <article>
                <h2> Main Features </h2>
                <ul>
                    <li>Fully Responsive and Customizable.</li>
                    <li>Unlimited main items and sub items can be added. </li>
                    <li>Sticky Profile.</li>
                    <li> CSS3 and jQuery Animations Enabled.  </li>
                    <li>Material Design Iconic Fonts. </li>
                    <li> Background dim-overlay when menu open </li>
                    <li> Chrome, Safari, Firefox, Opera, IE7+, IOS, Android and windows phone supported. </li>
                    <li> User Friendly and Easy to Implement. </li>
                </ul>
                <h2> Save Time to Quickly Customize </h2>
                <p>
                    You can highly customize this navigation system without investing time in modifications of code.
                </p>
                <h3> 1. jSide Menu Skin </h3>
                <p> Choose the theme for the menu: </p>
                <div class="theme-tray">
                    <section class="pl-color">
                        <h4> Plain Color</h4>
                        <span title="Love Red" class="red"> </span>
                        <span title="Clover Green" class="green"> </span>
                        <span title="Dodger Blue" class="blue"> </span>
                        <span title="Bright Neon Pink" class="bnp"> </span>
                        <span title="Pumpkin Orange" class="orange"> </span>
                        <span title="Black" class="black"> </span>
                        <span title="Neon Pink" class="pink"> </span>
                        <span title="Golden Brown" class="gol-b"> </span>
                        <span title="Greenish Blue" class="greenish"> </span>
                        <span title="Wood" class="wood"> </span>
                    </section>
                    <section class="gr-color">
                        <h4> Gradient Color</h4>
                        <span title="Flickr" class="flickr"> </span>
                        <span title="Facebook Messenger" class="fb-messenger"> </span>
                        <span title="Moonlit Asteroid" class="moonlit"> </span>
                        <span title="Park Life" class="park-life"> </span>
                        <span title="Dance to Forget" class="d2f"> </span>
                        <span title="Man of the Steel" class="steel-man"> </span>
                        <span title="Amethyst" class="amethyst"> </span>
                        <span title="Between the Clouds" class="between-clouds"> </span>
                        <span title="Crazy Orange"class="crazy-orange"> </span>
                        <span title="Endless River" class="endless-river"> </span>
                    </section>
                </div>
                <!--//theme-tray-->
                <h3>2. Menubar Position</h3>
                <p> Choose the menubar position:</p>
                <ol>
                    <li> <input type="radio" id="set-top" name="radio" checked/> Fixed on Top </li>
                    <li> <input type="radio" name="radio" id="set-st"/> Static </li>

                </ol>
                <h3>3. Menu Container Position </h3>
                <p> Choose the suitable position for menu container. </p>
                <select class="menu-position">
                    <option> position-left </option>
                    <option> position-right </option>
                </select>

                <h2>Generated Script</h2>
                <p>
                    Copy and paste it into document ready function.
                </p>
                <pre class="prettyprint">
$(".menu-container").jSideMenu({
   <span class="j-pos">jSidePosition: "position-left",</span>
   <span class="j-sticky">jSideSticky: true, </span>
   <span class="j-skin">jSideSkin: "default-skin", </span>
  });
 </pre>
                <h2> Browsers Compatability</h2>
                <p>Best view on Google Chrome, Firefox, Opera and UC Desktop Browser</p>
                </p>
            </article>
        </main>
    </div>
</section>
<footer>
    <div class="htmleaf-container">
        <div class="related" style="font-size: 1.25em">
            Copyright &copy; 2016 - 2018 V-Test. All Rights Reserved.<em>上海伟测半导体科技有限公司 版权所有</em>
        </div>
    </div>
</footer>
<script src="<c:url value="/resources/system/js/selectToDatabase.js"/>" type="text/javascript" charset="utf-8"></script>
</body>
</html>
