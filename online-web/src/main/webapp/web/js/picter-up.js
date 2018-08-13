function columnUpdown(form, wrapPicter) {
    $.ajax({
        type: 'post',
        url: "/medical/common/upload",
        data: form,
        cache: false,
        processData: false,
        contentType: false,
    }).success(function (data) {
        $('.' + wrapPicter + '').html('<img src="' + data.resultObject + '" >');
    });

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
        var form = new FormData();
        form.append("image", this.files[0]);
		var reader = new FileReader();
		reader.onload = function(e) {
			columnUpdown(form, wrapPicter);
		}
		reader.readAsDataURL(this.files[0])
	});
}
	