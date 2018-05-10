function columnUpdown(baseurl, wrapPicter) {
		RequestService("/medical/common/upload", "post", {
			image: baseurl,
		}, function(data) {
			$('.' + wrapPicter + '').html('<img src="' + data.resultObject + '" >');
		})
	}


function pieterUp(downId,wrapPicter){
	$('#'+downId+'').on('change', function() {		
		if(this.files[0].size > 2097152) {
			$('#tip').text('上传图片不能大于2M');
			$('#tip').toggle();
			setTimeout(function() {
				$('#tip').toggle();
			}, 2000)
//			showTip("上传图片不能大于2M");
			return false;
		}
		if(!(this.files[0].type.indexOf('image') == 0 && this.files[0].type && /\.(?:jpg|png|gif)$/.test(this.files[0].name))) {
			$('#tip').text('图片格式不正确');
			$('#tip').toggle();
			setTimeout(function() {
				$('#tip').toggle();
			}, 2000)
			return false;
		}
		var reader = new FileReader();
		reader.onload = function(e) {
			columnUpdown(reader.result, wrapPicter);
		}
		reader.readAsDataURL(this.files[0])
	});
}
	