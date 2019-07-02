<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>宜家链</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(function(){
            var params={};
            $.ajax({
                type : "get",
                url : "/echain/getSockets",
                dataType : "json",
                data : params,
                async:false,
                error : function() {
                    alert("加载错误");
                },
                success : function(data) {
                    $("#block_table").empty();
                    console.log(data)
                    var txt;
                    for(var i =0;i<data.length;i++){
                        console.log(data[i])
                        //展现返回的表格数据
                        txt+=
                            '<tr>'+
                               '<td style="color:#528BFF">'+ JSON.stringify(data[i])+'</td>'+
                            '</tr>';
                        txt = txt.replace("undefined", "");
                    }
                    $("#block_table").append(txt);

                }
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
        <h2 style="color:#528BFF;>节点查询</h2>
	<p style="color:#528BFF;> 节点列表:</h2>>
        <table class="table table-condensed" style="word-break:break-all">
            <thead>
               <tr>
                   <th style="color:#528BFF">区块链</th>
               </tr>
	    	</thead>
		    <tbody id="block_table">
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
