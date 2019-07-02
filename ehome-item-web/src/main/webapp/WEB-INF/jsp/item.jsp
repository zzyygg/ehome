<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${item.title }</title>
    <script>
        var _SF_CFG = {
            www_url:'http://www.e3mall.cn',
            m_url:'http://m.e3mall.cn',
            productId:${item.id},
            number: 1600229585,
            minBuy: 1,
            maxBuy: 99,
            presell:false,
            presellId:0,
            parentId: 0,
            commentPage: 1,
            sdPage: 1,
            oneCategoryId: 6,
            twoCategoryId: 7321,
            threeCategoryId: 7331,
            sfprice: 0,
            price:0,
            warehouse: 0,
            homeurl: 'http://home.e3mall.cn',
            staticurl: 'http://i.e3mall.cn/html',
            passporturl: 'https://passport.e3mall.cn',
            businessModel: 3,
            commentType : 0
        };
    </script>

    <script src="/js/jquery-1.5.1.min.js?v=20160713" type="text/javascript"></script>
    <script src="/js/jquery.alerts.js?v=20160713" type="text/javascript"></script>
    <script src="/js/common.js?v=20160713" type="text/javascript"></script>
    <script src="/js/cart.js?v=20160713" type="text/javascript"></script>
    <script src="/js/cloud-zoom.1.0.2.min.js?v=20160713" type="text/javascript"></script>
    <script src="/js/jquery.thickbox.js?v=20160713" type="text/javascript"></script>
    <script src="/js/goods.js?v=20160713" type="text/javascript"></script>
    <script src="/js/NewVersion.js?v=20160713" type="text/javascript"></script>
    <script src="/js/png.js?v=20160713" type="text/javascript"></script>
    <script src="/js/qiangGouPro.js?v=20160713" type="text/javascript"></script>
    <script src="/js/jquery.cookie.js?v=20160713" type="text/javascript"></script>
    <script src='/js/jquery.lazyload.js?v=20160713' type='text/javascript'></script>
    <script type="text/javascript" src="/js/jquery.qrcode.js?v=20160713"></script>
    <script type="text/javascript" src="/js/qrcode.js?v=20160713"></script>
    <script type="text/javascript" src="/js/cookie.js?v=20160416222"></script>
    <script type="text/javascript" src="/js/shadow.js?v=20160416"></script>
    <script src="/js/product.js?v=20160713" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/css/base_w1200.css?v=20160713">
    <link rel="stylesheet" type="text/css" href="/css/product.css?v=20160713">
    <link rel="stylesheet" type="text/css" href="/css/jquery.alerts.css?v=20160713" />
    <link rel="stylesheet" type="text/css" href="/css/common.css?v=20160713" />
    
    <script type="javascript">
        $(function () {
            $("#bt1").onclick(function () {
                alert("h啊哈");
                var
                alert(${list})
            });
        });
    </script>
</head>
<body >
<!-- header start -->
<jsp:include page="commons/header.jsp" />
<!-- header end -->
<div class="pWrap">
    <div class="productIntro">
        <div class="pItems">
            <div class="pItemsMain">
                <div class="pItemsName">
                    <div class="cm">
                        <h1 id="base_name-sf">${item.title }</h1><br></br>
                        <strong id="adword-sf" title="" class="">${item.sellPoint }</strong>
                    </div>
                </div>
                <div class="pItemsPrice" id="price-sf">
                    <div class="priceBox">
                        <span class="dt">售价：</span><span class="rmb">￥</span>
                        <strong class="price"><fmt:formatNumber groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" value="${item.price / 100 }"/></strong>
                    </div>
                    <div class="boxWb"></div><div id="productStamp" class="productStamp_1"></div></div>
                <div class="clear"></div>
                <div id="presell-info-sf" class="pItemBook" style="display:none"></div>
                <div class="pItemsPromo" id="promotion-sf" style="display:none"></div>
                <div class="pItemsStock">
                    <div class="dt">送至：</div>
                    <div class="dd">
                        <div id="regionSf"><div class="" id="store-selector">
                            <div class="text"><div title="北京">北京</div><b></b></div>
                            <div onclick="$('#store-selector').removeClass('hover')" class="close"></div>
                        </div>
                        </div>
                    </div>
                    <span class="clear"></span>
                </div>
                <div class="pItemsChoose">
                    <div class="chooseType">
                        <ul id="fatherson-sf"></ul>
                        <span class="clear"></span>
                    </div>
                    <div class="chooseBtns" id="buy-btn-sf">
                        <div class="pAmount">
                            <span><input type="text" id="number_${item.id}" class="text" value=""></span>
                            <span>
                                <a href="javascript:    ;" id="add-sell-num" class="p-add">+</a>
                                <a href="javascript:;" id="reduce-sell-num" class="p-reduce disable">-</a>
                            </span>
                        </div>
                        <div class="pBtn" id="cart-add-btn-sf">
                            <a onclick="cartAdd(${item.id}, 0, 1, 0, 1, this);"><b></b>加入购物车</a>
                        </div>
                        <div class="pBtn" id="cart-add-btn-bc" style="background-color: red;color: red">
                            <a id="bc"  href="http://localhost:8100/echain/searchItem?itemID=${item.id}" target="_blank">查看区块信息</a>
                        </div>
                        <div class="pBtn quickBuy" style="display: none;" id="quickBuy" title="快速下单，直达填写订单页面"><a onclick="oneKeyBuy(${item.id}, 0, 1, 0, 1, this);">一键购买</a></div>
                        <div class=""><div id=""><br><em></em></div>
                        </div>
                        <span class="clear"></span>
                    </div>
                    <div class="chooseBtns" id="buy-nogood-sf" style="display:none">
                        <div class="pBtn"><span class="noShip">已售完</span></div>
                        <div class="pBtn preBtn" id="arrival_notice"><a href="javascript:void(0);" onclick="Goods.arrivalNotice(this);">到货通知</a></div>
                        <div class="pBtn quickBuy disable"><a href="javascript:void(0);">一键购买</a></div>

                        <span class="clear"></span>
                    </div>
                    <div class="chooseBtns" id="buy-canntsend-sf" style="display:none">
                        <div class="pBtn"><span class="noShip">无法送达</span></div>
                        <span class="clear"></span>
                    </div>
                    <div class="finalBuy" id="finalbuy-sf" style="display:none"></div>
                </div>
            </div>
            <div class="pView">
                <div id="pView">
                    <div id="zoom-jpg" class="jqzoom">
                        <img alt="" width="330" height="330" src="${item.images[0] }" jqimg="${item.images[0] }"/>
                    </div>
                    <div id="pic-list">
                        <a href="javascript:void(0);" class="btn-control disabled" id="btn-forward"><b></b></a>
                        <a href="javascript:void(0);" class="btn-control disabled" id="btn-backward"><b></b></a>

                        <div class="pic-items" style="position: absolute; width: 50px; height: 300px; overflow: hidden;">
                            <ul style="position: absolute; left: 0px; top: 0px; height: 240px;">
                                <c:forEach items="${item.images }" var="image">
                                    <li style="float: left;"><img title="${item.title } " alt="${item.title }" src="${image }"></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <span class="clear"></span>
    </div>
</div><div class="pWrap">
    <div class="main-box">
        <div id="package"></div>
        <div class="pDetail">
            <ul class="pTab">
                <li class="curr" pcont-target="div-detail"><a title="商品详情" href="javascript:void(0);">商品介绍</a></li>
            </ul>
            <div id="add-cart-r-btn-sf" class="p-btn"><a href="javascript:void(0);" data_url="http://p02.e3mall.cn/2016/1600229585/thumb_1600229585_1_1.jpg" pid="${item.id}">加入购物车</a></div>
        </div>
        <div class="clear" id="flow-layer-sf"></div>
        <div class="pCont cpjs_box" id="div-detail">
            <div
                    style="background-color: #f5f5f5; padding: 4px 20px 4px 0; line-height: 20px; overflow: hidden; zoom: 1;">
            </div>
            <div class="product_info">${itemDesc.itemDesc }</div>
        </div>
    </div>
</div>
</body>
</html>