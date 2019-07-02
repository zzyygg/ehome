var E3MALL = {
	checkLogin : function(){
		var _ticket = $.cookie("managerToken");
		if(!_ticket){
			location.href = "http://localhost:8081/login";
			return ;
		}
		$.ajax({
			url : "http://localhost:8081/manager/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				alert(data.data.managername);
				if(data.status == 200){
					var managername = data.data.managername;
					var html = "管理员："+managername + ",欢迎您！<a id='logout' href=\"http://localhost:8081/login\" class=\"link-logout\">[退出]</a>";
					$(".loginInfo").html(html);
				}
				else{
					location.href = "http://localhost:8081/login";
					return ;
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	E3MALL.checkLogin();
});