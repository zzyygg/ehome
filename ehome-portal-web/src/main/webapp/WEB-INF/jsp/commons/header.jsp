<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!--shortcut start-->
<jsp:include page="shortcut.jsp"/>
<!--shortcut end-->
<div id="header">
    <div class="header_inner">
        <div class="logo">
            <div class="logo-text">
                <%--<img src="/images/html/logo_word.jpg">--%>
            </div>
        </div>
        <div class="index_promo"></div>
        <div class="search">
            <form action="http://localhost:8085/search.html" id="searchForm" name="query" method="GET">
                <input type="text" class="text keyword ac_input" name="keyword" id="keyword" value=""
                       style="color: rgb(153, 153, 153);"
                       onkeydown="javascript:if(event.keyCode==13) search_keys('searchForm');" autocomplete="off">
                <input type="button" value="" class="submit" onclick="search_keys('searchForm')">
            </form>
        </div>
        <div class="shopingcar" id="topCart">
            <s class="setCart"></s><a href="http://localhost:8089/cart/cart.html" class="t" rel="nofollow">我的购物车</a><b
                id="cartNum">0</b>
            <span class="outline"></span>
            <span class="blank"></span>
        </div>
    </div>
</div>