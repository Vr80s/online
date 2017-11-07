function errMsg() {
	var a = $("#errMsg").val();
	a && Msg.fail(a)
}
function uploadFinish() {
	setTimeout("window.location = '/video/list.bo'", 500)
}
function setUploadFinish(a) {
	isUploadFinish = a
}
$(document).ready(function() {
	errMsg()
});
var isUploadFinish = !0;
window.onbeforeunload = function(a) {
	return isUploadFinish ? window.event ? void 0 : null : "您的上传还未完成，是否离开当前页？"
};
$(function() {
	$("#changeUploadMethod").click(
		function() {
			return window.ProgressEvent
				&& window.FileReader
				&& window.FormData
				&& window.Blob
				&& (Blob.prototype.slice
						|| Blob.prototype.webkitSlice || Blob.prototype.mozSlice) ? void (window.location.href = "/video/upload5.bo")
				: Msg.fail("该浏览器不支持HTML5上传，请更换或升级浏览器")
		});
});