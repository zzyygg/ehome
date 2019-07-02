<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>添加节点</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(function() {
            $("#mineing").html("点击添加节点").css("color","red");
            $("#myButton4").click(function(){

                var params={};
                var data = $("#data").val();
                if(data==""){
                    alert("区块链存储数据不能为空...");
                    return;
                }
                // if(!isValidIP(data)){
                //     alert("请填写正确的ip地址...");
                //     return;
                // }
                data="ws://"+data
                $("#mineing").html("节点添加中...").css("color","red");
                params.peer=data;
                $.ajax({
                    type : "get",
                    url : "/echain/addSocket",
                    dataType : "json",
                    data : params,
                    async:true,
                    error : function() {
                        alert("加载错误");
                    },
                    success : function(data) {
                        $("#mineing").html("已经添加").css("color","green");
                    }

                });

            });
        });
        function isValidIP(ip) {
            var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
            return reg.test(ip);
        }
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

        <h2  style="color:#528BFF;">测试挖矿</h2>
        <p  style="color:#528BFF;">区块data:</p>

        <form role="form">
            <div class="form-group"  style="color:#528BFF;">
                <label id="name"  style="color:#528BFF;">请填入ip</label>
                ws://<input id="data"></input>
            </div>
            <button type="button"  class="btn btn-primary" id="myButton4" >添加节点
            </button>
            <span id="mineing"></span>
        </form>

    </div>id
</div>
</body>
</html><head>
    <title>Title</title>
</head>
<body>

</body>
</html>
