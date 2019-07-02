<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ehome-blockchain</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="canvas-particle.js"></script>
    <style>
        body{background-color: #2D2D2D}
        #search{
            padding-top: 15px;
            padding-bottom: 15px;
            position: relative;
            display: block;
            padding: 10px 15px;
            float: right;
        }
    </style>
</head>
<body>

<div class="container">
    <div id="mydiv">
        <nav class="navbar" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/echain/blocks">宜家链</a>
                </div>
                <div>
                    <ul class="nav navbar-nav">
                        <li  class="active"><a href="/echain/blocks">区块链查看</a></li>
                        <li><a href="/echain/miner">挖矿</a></li>
                        <li><a href="/echain/peer">查看节点</a></li>
                        <li><a href="/echain/addPeer">添加节点</a></li>
                    </ul>
                </div>
                <div id="search">
                    <input type="text" id="searchInput" placeholder="请输入商品ID" aria-label="请输入商品ID" aria-describedby="button-header-search" autocomplete="off">
                    <button id="searchButton">搜索</button>
                </div>
            </div>
        </nav>
        <h2 style="color:#528BFF;">区块查询</h2>
        <p style="color:#528BFF;">区块链列表:</p>
        <c:if test="${list== null || fn:length(list) == 0}"></c:if>
        <table class="table table-condensed" style="word-break:break-all">
            <thead>
            <tr>
                <th style="color:#528BFF;">区块链</th>
            </tr>
            </thead>
            <tbody style="color:#528BFF;"  id="block_table">
            <c:forEach items="${blocks}" var="block">
                <tr>
                    <td  style="color:#528BFF;">
                        <p style="float: left;">
                            <c:out value="${block}"></c:out>
                        </p>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>