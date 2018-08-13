
	//上传图片方法
	function upPic(iptName,boxName){
		//医师-医馆封面上传图片调用的接口
		function picUpdown(form, imgname) {
            $.ajax({
                type: 'post',
                url: "/medical/common/upload",
                data: form,
                cache: false,
                processData: false,
                contentType: false,
            }).success(function (data) {
                $('.' + imgname + '').html('<img src="' + data.resultObject + '" >');
            });
		}
	
		//医馆封面上传
		$(iptName).on('change', function() {
            var form = new FormData();
            form.append("image", this.files[0]);
			var reader = new FileReader();
			reader.onload = function(e) {
				picUpdown(form, boxName);
			}
			reader.readAsDataURL(this.files[0])
		})
	}
				

