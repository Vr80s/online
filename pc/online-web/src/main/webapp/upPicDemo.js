
	//上传图片方法
	function upPic(iptName,boxName){
		//医师-医馆封面上传图片调用的接口
		function picUpdown(baseurl, imgname) {
			RequestService("/medical/common/upload", "post", {
				image: baseurl,
			}, function(data) {
				console.log(data);
				$('.' + imgname + '').html('<img src="' + data.resultObject + '" >');
			})
		}
	
		//医馆封面上传
		$(iptName).on('change', function() {
			var reader = new FileReader();
			reader.onload = function(e) {
				picUpdown(reader.result, boxName);
			}
			reader.readAsDataURL(this.files[0])
		})
	}
				

