var Msg = {
	_SUCCESS : "success",
	_FAIL : "fail",
	_MSGHTML : '<div class="msg_pop"><span class="label"></span></div>',
	_MSGPOPHTML : '<div class="msg_pop msg_pop1"><span class="label"></span></div>',
	success : function(a) {
		alert(a);
		this._prompt(a, this._SUCCESS)
	},
	fail : function(a) {
		this._prompt(a, this._FAIL)
	},
	inspectErrMsg : function() {
		var a = $.trim($("#errMsg").val());
		a && this.fail(a)
	},
	_prompt : function(a, b) {
		var c = "label-success";
		this._FAIL === b && (c = "label-warning"), this._showMsg(a, c)
	},
	_showMsg : function(a, b) {
		$(".msg_pop span").length
				|| (window != top ? $("body").append(this._MSGPOPHTML) : $(
						"body").append(this._MSGHTML)), $(".msg_pop span")
				.empty().removeClass().append(a), $(".msg_pop").show(), $(
				".msg_pop span").addClass("label " + b);
		var c = $(".msg_pop").width();
		$(".msg_pop").css("margin-left", -c / 2 + "px"), setTimeout(function() {
			$(".msg_pop").fadeOut()
		}, 1500)
	}
};