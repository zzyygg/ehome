<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ehome-blockchain</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(function() {
            $("#searchButton").click(function(){
                var itemID={};
                var data = $("#searchInput").val();
                alert(data)

                if(data==""){
                    alert("请输入要搜索的商品ID" );
                    return;
                }
                params.data=data;
                alert(data);
                $.ajax({
                    type : "get",
                    timeout:1000000000,
                    url : "/echain/searchBlocks",
                    dataType : "json",
                    data : params,
                    async:true,
                    error : function() {
                        alert("加载错误")
                    },
                    success : function(data) {
                        $("#block_table").empty();
                        console.log(data)
                        var txt;
                        for(var i =0;i<data.length;i++){
                            var block = data[i];
                            //展现返回的表格数据
                            txt+=
                                '<tr>'+
                                    '<td style="color:#528BFF;">'
                                    + 'index:'+block.index+'<br>'
                                    + 'timestamp:'+block.timestamp+'<br>'
                                    + 'previousHash:'+block.previousHash+'<br>'
                                    + 'data:'+block.data+'<br>'
                                    + 'hash:'+block.hash+'<br>'
                                    + 'nonce:'+block.nonce+'<br>'+
                                    '</td>'+
                                '</tr>';
                            txt = txt.replace("undefined", "");
                        }
                        $("#block_table").append(txt);
                        $("#hide-after").html("数据加载完毕");

                    }

                });

            });
        });
    </script>
    <script type="text/javascript" src="canvas-particle.js"></script>
    <style>
        /*body{background-color: #2D2D2D}*/
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
        <table class="table table-condensed" style="word-break:break-all">
            <thead>
            <tr>
                <th style="color:#528BFF;">区块链</th>
            </tr>
            </thead>
            <tbody style="color:#528BFF;"  id="block_table">

            </tbody>
        </table>
    </div>
</div>
</body>
</html>