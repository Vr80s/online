<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="bootstrap/assets/js/bootstrap-select.js"></script>
<link rel="stylesheet" type="text/css" href="bootstrap/assets/css/bootstrap-select.css" />



<div class="container">
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label for="message_user_select" class="col-lg-2 control-label">用户搜索:</label>
			<div class="col-lg-10">
				<select id="message_user_select" class="selectpicker form-control"  data-live-search="true" >
					<option value="-1">全部</option>
				</select>
			</div>
		</div>
	</form>
</div>

<script>
	var message_user_select_id="-1";
	var message_user_select_text="全部";
	$(document).ready(function () {

		$('.selectpicker').on('change', function(){
			var userId = $(this).find("option:selected").val();
			var userName=$(this).find("option:selected").text();
			if(userId!="-1"&&userName!="全部"){
				$(this).selectpicker('val', userId);
				/*$('#message_questionTable').DataTable().ajax.url(basePath+"/message/load/user/"+userId+"/messages");
				$('#message_questionTable').DataTable().ajax.reload();*/
				message_user_select_id=userId;
				message_user_select_text=userName;
			}else{
				/*$('#message_questionTable').DataTable().ajax.url(basePath+"/message/load/messages");
				$('#message_questionTable').DataTable().ajax.reload();*/
				message_user_select_id=userId;
				message_user_select_text=userName;
			}
		});

		ajaxRequest(basePath+"/message/userlist",{},function(res) {
			if(res.resultObject.length>0){
				for(var i=0;i<res.resultObject.length;i++){
					var user=res.resultObject[i];
					if(user!=null){
						$("#message_user_select").append("<option value='"+user.id+"'>"+user.name+"</option>");
					}
				}
			}
			$('.selectpicker').selectpicker({
				noneSelectedText: "请选择要发送消息的用户"
			});
		});
	});
</script>
