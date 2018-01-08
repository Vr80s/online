/**
 * 
 */

// px转换为rem
(function(doc, win) {
	// var docEl = doc.documentElement,
	// resizeEvt = 'orientationchange' in window ? 'orientationchange' :
	// 'resize',
	// recalc = function() {
	// var clientWidth = docEl.clientWidth;
	// if (!clientWidth) return;
	// if (clientWidth >= 640) {
	// docEl.style.fontSize = '100px';
	// } else {
	// docEl.style.fontSize = 100 * (clientWidth / 640) + 'px';
	// }
	// };

	if (!doc.addEventListener)
		return;
	/*
	 * win.addEventListener(resizeEvt, recalc, false);
	 * doc.addEventListener('DOMContentLoaded', recalc, false);
	 */
})(document, window);

/**
 * 截取url传递的参数
 * 
 * @param name
 *            传递 key 得到value
 * @returns
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
// 判断字段空值
function stringnull(zifu) {
	if (zifu == "" || zifu == null || zifu == undefined || zifu == "undefined"
			|| zifu == "null") {
		return false;
	}
	return true;

}
// ajax统一请求
function requestService(url, param, callback, ac) {
	if (ac == null)
		ac = true;// 默认异步
	mui.ajax({
		url : url,
		type : "post",
		data : param,
		async : ac,
		success : function(msg) {
			if (msg.code == 1002) { // 过期
				location.href = "/bxg/page/login/1";
			} else if (msg.code == 1003) { // 被同一用户顶掉了
				location.href = "/xcviews/html/common.html";
			} else {
				if (callback) {
					callback(msg);
				}
			}
		},
		error : function(msg) {
			// alert(msg);
		}
	});
}

function imgChange(obj1, obj2) {
	// 获取点击的文本框
	var file = document.getElementById("file");
	// 存放图片的父级元素
	var imgContainer = document.getElementsByClassName(obj1)[0];
	// 获取的图片文件
	var fileList = file.files;
	// 文本框的父级元素
	var input = document.getElementsByClassName(obj2)[0];
	var imgArr = [];
	// 遍历获取到得图片文件
	for (var i = 0; i < fileList.length; i++) {
		// alert(fileList.length)
		var imgUrl = window.URL.createObjectURL(file.files[i]);
		imgArr.push(imgUrl);
		var img = document.createElement("img");
		img.setAttribute("src", imgArr[i]);
		var imgAdd = document.createElement("div");
		imgAdd.setAttribute("class", "z_addImg");
		imgAdd.appendChild(img);
		imgContainer.appendChild(imgAdd);

		/*
		 * var input = document.createElement("input"); input = file; //<input
		 * type="file" name="files" id="file" value="" accept="image/*" multiple
		 * onchange="imgChange('z_photo','z_file');" />
		 * input.setAttribute("type", "file"); input.setAttribute("name",
		 * "files"); input.removeAttribute("onchange");
		 * input.removeAttribute("id");
		 */
		// input.setAttribute("style","display:none");
		var a = $("#file").clone();
		a.attr("id", "");
		a.attr("onchange", "");
		a.css("display", "none");
		a.attr("name", "files");
		$(imgAdd).append(a);
	}
	;
	imgRemove();
};

function imgRemove() {
	var imgList = document.getElementsByClassName("z_addImg");
	var mask = document.getElementsByClassName("z_mask")[0];
	var cancel = document.getElementsByClassName("z_cancel")[0];
	var sure = document.getElementsByClassName("z_sure")[0];
	for (var j = 0; j < imgList.length; j++) {
		imgList[j].index = j;
		imgList[j].onclick = function() {
			var t = this;
			mask.style.display = "block";
			cancel.onclick = function() {
				mask.style.display = "none";
			};
			sure.onclick = function() {
				mask.style.display = "none";
				t.style.display = "none";

				$(t).find("input").remove();
			};

		}
	}
	;
};
var falg = getQueryString("falg");
if (stringnull(falg) && falg == 2) {
	alert("举报失败");
} else if (stringnull(falg) && falg == 1) {
	alert("举报成功");
}
var label = getQueryString("label");
var courseId = sessionStorage.getItem("tipOffCourseId");
var tipOffToken = sessionStorage.getItem("tipOffToken");
alert(courseId);
alert(tipOffToken);
//if (stringnull(courseId)) {
//	requestService("/bxg/common/shareJump", {
//		courseId : courseId,
//		token : tipOffToken
//	}, function(data) {
//		if (data.success) {
//			var result = data.resultObject;
//			// gradeName,smallimg_path as smallImgPath
//			$(".complaint_details_div").html(result.gradeName);
//			$("#complaint_details_img_img").html(result.smallImgPath);
//		}
//	}, false)
//} else {
//	alert("课程信息有误");
//}
$("#courseId").val(courseId);
$("#label").val(label);
$("#token").val(tipOffToken);
// 表单验证
function checkUser() {
	if (!stringnull(courseId)) {
		alert("课程有误,请稍后重试!");
		return;
	}
	if (!stringnull(label)) {
		alert("举报标签有误!");
		return;
	}
	var content = $("#contentId").val();
	if (content.trim().length > 200) {
		alert("举报内容不能大于于200字");
		return;
	}
	if (content.trim().length <= 0) {
		alert("举报内容不能为空");
		return;
	}
	var imgLength = $("input[name=files]").length;
	if (imgLength > 4) {
		alert("上传的图片不能大于4张");
		return;
	}
	if (imgLength == 0) {
		alert("至少上传一张图片");
		return;
	}
	document.getElementById("formid").submit();
}