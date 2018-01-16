

	function btn_up(){
		if($(".wrap-user-id .userName").val()==''){
			$(".error-prompt").show();
			$(".btn-up").removeAttr("onclick")
			setTimeout(function(){
				$(".error-prompt").hide();
				$(".btn-up").attr("onclick","btn_up()")
			},2000)
		}
	}
		




