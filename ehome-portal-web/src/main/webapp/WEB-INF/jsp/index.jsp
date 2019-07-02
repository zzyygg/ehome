<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>宜家商城</title>
    <link rel="stylesheet" type="text/css" href="/css/base_w1200.css?v=2016071333">
    <link rel="stylesheet" type="text/css" href="/css/index.css?v=2016071312">
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/global_index.js"></script>
    <style id="style-1-cropbar-clipper">
        .en-markup-crop-options {
            top: 18px !important;
            left: 50% !important;
            margin-left: -100px !important;
            width: 200px !important;
            border: 2px rgba(255, 255, 255, .38) solid !important;
            border-radius: 4px !important;
        }

        .en-markup-crop-options div div:first-of-type {
            margin-left: 0px !important;
        }
    </style>
</head>
<body>


<!-- header start -->
<jsp:include page="commons/header.jsp"/>
<!-- header end -->
<!----row1------->
<div class="slide_show" id="slide_show">
    <div class="indexW">
        <div id="index_slide" class="slide_wrap">
            <ol>
                <c:forEach items="${ad1List}" var="node" varStatus="status">
                    <li>
                        <a name="sfbest_hp_hp_focus_${status.index }" class="fore_pic trackref" href="${node.url }"
                           target="_blank">
                            <img id="lunbo_1" alt="${node.title }" src="${node.pic }">
                        </a>
                    </li>
                </c:forEach>
            </ol>
        </div>
    </div>
</div>
<!----row1 end------->
</div>
</body>
</html>