<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>宜家链-挖矿</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(function() {
            $("#mineing").html("点击挖矿").css("color","red");
            $("#myButton4").click(function(){

                var params={};
                var data = $("#data").val();
                if(data==""){
                    alert("区块链存储数" +
                        "据不能为空...");
                    return;
                }
                $("#mineing").html("挖矿中...").css("color","red");
                params.data=data;
                $.ajax({
                    type : "get",
                    timeout:1000000000,
                    url : "/echain/mineBlock",
                    dataType : "json",
                    data : params,
                    async:true,
                    error : function() {
                        alert("加载错误");
                    },
                    success : function(data) {
                        var txt;
                        //展现返回的表格数据
                        txt+=
                            '<tr>'+
                            '	<td style="color:#528BFF;">'+ JSON.stringify(data)+'</td>'+
                            '</tr>';
                        $("#block_table").append(txt);
                        $("#mineing").html("挖矿完成....,点击继续挖矿").css("color","green");
                    }

                });

            });
        });
    </script>
    <script type="text/javascript" src="canvas-particle.js"></script>
    <style>
        body{background-color: #2D2D2D}
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
            </div>
        </nav>
        <h2 style="color:#528BFF;">测试挖矿</h2>
        <p style="color:#528BFF;">区块data:</p>

        <form role="form">
            <div class="form-group">
                <label id="name" style="color:#528BFF;">请填入区块内容</label>
                <textarea class="form-control" id="data" rows="3"></textarea>
            </div>
            <button type="button"  class="btn btn-primary" id="myButton4" style="color:#528BFF;">挖矿
            </button>
            <span id="mineing"></span>
        </form>

        <table class="table table-condensed" style="word-break:break-all; word-wrap:break-all;">
            <thead>
            <tr>
                <th style="color:#528BFF;">区块链</th>
            </tr>
            </thead>
            <tbody  id="block_table">

            </tbody>
        </table>
    </div>
</div>
</body>
</html>
