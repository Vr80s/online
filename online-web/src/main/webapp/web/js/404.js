window.onload = function() {
	if($(".first-page").css("display") == "block") {
		$(".first-page").hide();
		$(".other-page").show();
	}
	$("footer .content .friendly-link").show();
	RequestService("/otherlink/getOtherLink", "GET", "", function(data) {
		$(".link-content").empty();
		$(".friendly-link").css("display", "none");
		/*createOtherLink(data.resultObject);*/
		layer.closeAll('loading');
	});
	
}