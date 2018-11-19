<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/11/9
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登陆</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/system/css/normalize.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/system/css/default.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/system/css/styles.css"/>">
    <%--<script src="<c:url value="/resources/system/js/jquery-1.11.0.min.js"/>" type="text/javascript" charset="utf-8"></script>--%>
</head>
<body background-image="/">
<div class="htmleaf-container">
    <header class="htmleaf-header"><h1>上海伟测半导体科技有限公司<span></span></h1>
    </header>
    <div class="login-wrap">
        <div class="login-html">
            <input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">登录</label>
            <input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">注册</label>
            <div class="login-form">
                <div class="sign-in-htm">
                    <form action="<c:url value="/system/validate"/>" method="post">
                        <div class="group">
                            <label for="user" class="label">用户名</label>
                            <input id="user" type="text" class="input" name="userName">
                        </div>
                        <div class="group">
                            <label for="pass" class="label">密码</label>
                            <input id="pass" type="password" class="input" data-type="password" name="password">
                        </div>
                        <div class="group">
                            <input id="check" type="checkbox" class="check" checked>
                            <label for="check"><span class="icon"></span> 保持登录</label>
                        </div>
                        <div class="group">
                            <input type="submit" class="button" value="登录">
                        </div>
                    </form>
                    <div class="hr"></div>
                    <div class="foot-lnk">
                        <a href="#forgot">忘记密码?</a>
                    </div>
                </div>
                <div class="sign-up-htm">
                    <form method="post" action="<c:url value="/system/registerUser"/>">
                        <div class="group">
                            <label for="username" class="label">用户</label>
                            <input id="username" type="text" class="input" name="userName">
                        </div>
                        <div class="group">
                            <label for="password" class="label">密码</label>
                            <input id="password" type="password" class="input" data-type="password" name="password">
                        </div>
                        <div class="group">
                            <label for="validatePassword" class="label">确认密码</label>
                            <input id="validatePassword" type="password" class="input" data-type="password">
                        </div>
                        <div class="group">
                            <label for="email" class="label">邮箱地址</label>
                            <input id="email" type="text" class="input" name="email">
                        </div>
                        <div class="group">
                            <label for="group" class="label">组别</label>
                            <select id="group" class="vselect" name="vGroup">
                                <option value="" selected>*</option>
                                <option value="IT">IT</option>
                                <option value="OP">OP</option>
                                <option value="PE">PE</option>
                                <option value="FIN">FIN</option>
                                <option value="FAC">FAC</option>
                                <option value="PTE">PTE</option>
                            </select>
                        </div>
                        <div class="group">
                            <input type="submit" class="button" value="注册">
                        </div>
                        <div class="hr"></div>
                        <div class="foot-lnk">
                            <label for="tab-1"/>已经注册?</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<script type="text/javascript">--%>
    <%--$(function () {--%>

    <%--})--%>
<%--</script>--%>
</body>
</html>
