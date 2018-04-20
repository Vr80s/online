//function picUpdown222(baseurl, id) {
//	RequestService("/medical/common/upload", "post", {
//		image: baseurl,
//	}, function(data) {
//		$('#' + id + '').html('<img src="' + data.resultObject +'?imageMogr2/thumbnail/260x147<'+ '" >');
//	})
//}
////$('#zhuanlan_picIpt').on('change', function() {
//
//		
//		if(this.files[0].size > 2097152) {
//			$('#tip').text('上传图片不能大于2M');
//			$('#tip').toggle();
//			setTimeout(function() {
//				$('#tip').toggle();
//			}, 2000)
//			return false;
//		}
//		if(!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
//			$('#tip').text('图片格式不正确');
//			$('#tip').toggle();
//			setTimeout(function() {
//				$('#tip').toggle();
//			}, 2000)
//			return false;
//		}
//	
//		var reader = new FileReader();
//		reader.onload = function(e) {
//			picUpdown222(reader.result, 'id');
//		}
//		reader.readAsDataURL(this.files[0])
//	}
//})