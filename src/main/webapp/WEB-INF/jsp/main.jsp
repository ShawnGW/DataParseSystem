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
                    <i class="fa fa-envelope fa-fw" style="color: white"></i> <i class="fa fa-caret-down" style="color: white"></i>
                </a>
                <ul class="dropdown-menu dropdown-messages">
                    <li>
                        <a href="#">
                            <div>
                                <strong>John Smith</strong>
                                <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                            </div>
                            <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <strong>John Smith</strong>
                                <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                            </div>
                            <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <strong>John Smith</strong>
                                <span class="pull-right text-muted">
                                        <em>Yesterday</em>
                                    </span>
                            </div>
                            <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a class="text-center" href="#">
                            <strong>Read All Messages</strong>
                            <i class="fa fa-angle-right" style="color: white"></i>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-tasks fa-fw" style="color: white"></i> <i class="fa fa-caret-down" style="color: white"></i>
                </a>
                <ul class="dropdown-menu dropdown-tasks">
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 1</strong>
                                    <span class="pull-right text-muted" >40% Complete</span>
                                </p>
                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                        <span class="sr-only">40% Complete (success)</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 2</strong>
                                    <span class="pull-right text-muted">20% Complete</span>
                                </p>
                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                        <span class="sr-only">20% Complete</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 3</strong>
                                    <span class="pull-right text-muted">60% Complete</span>
                                </p>
                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                        <span class="sr-only">60% Complete (warning)</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 4</strong>
                                    <span class="pull-right text-muted">80% Complete</span>
                                </p>
                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                        <span class="sr-only">80% Complete (danger)</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a class="text-center" href="#">
                            <strong>See All Tasks</strong>
                            <i class="fa fa-angle-right" style="color: white"></i>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-bell fa-fw" style="color: white"></i> <i class="fa fa-caret-down" style="color: white"></i>
                </a>
                <ul class="dropdown-menu dropdown-alerts">
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-comment fa-fw"></i> New Comment
                                <span class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                <span class="pull-right text-muted small">12 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-envelope fa-fw"></i> Message Sent
                                <span class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-tasks fa-fw"></i> New Task
                                <span class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                <span class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a class="text-center" href="#">
                            <strong>See All Alerts</strong>
                            <i class="fa fa-angle-right"></i>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw" style="color: white"></i> <i class="fa fa-caret-down" style="color: white"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li>
                        <a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
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
	   <img src="<c:url value="/resources/system/image/me.jpg"/>" alt=${SYSTEM_USER.userName} />
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
                <li><a href="#2">Wafer_map</a> </li>
                <li><a href="#2">Lot_line</a> </li>
                <li><a href="#2">Tester_status </a> </li>
                <li><a href="#2">Data_summary</a> </li>
                <li><a href="#2">Superposition</a> </li>
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
<div id="content">
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
<footer>
    <div class="htmleaf-container">
        <div class="related" style="font-size: 1.25em">
            Copyright &copy; 2016 - 2018 V-Test. All Rights Reserved.<em>上海伟测半导体科技有限公司 版权所有</em>
        </div>
    </div>
</footer>
<script>
    $(document).ready(function(){
        $(".menu-container").jSideMenu({
            jSidePosition: "position-left", //possible options position-left or position-right
            jSideSticky: true, // menubar will be fixed on top, false to set static
            jSideSkin: "default-skin", // to apply custom skin, just put its name in this string
        });
    });
</script>
</body>
</html>
