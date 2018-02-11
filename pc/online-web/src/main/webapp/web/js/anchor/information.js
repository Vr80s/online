var workTime;
$(function(){
    //判断账户身份显示效果
//  localStorage.AccountStatus = 2;
    if(localStorage.AccountStatus == 1){
        //主播是医师的身份
        //医馆的消失
        $('.clinics').addClass('hide');
        $('.physician').removeClass('hide');

        //医师的显示
        // $('.begin_approve .physician_one').removeClass('hide');
        // $('.account_main .physician_two').removeClass('hide');

    }else if(localStorage.AccountStatus == 2){
        //主播是医馆的身份
        $('.clinics').removeClass('hide');
        $('.physician').addClass('hide');
        // //医师的消失
        // $('.begin_approve .physician_one').addClass('hide');
        // $('.account_main .physician_two').addClass('hide');
        //
        // //医馆的显示
        // $('.begin_approve .clinic_two').removeClass('hide');
        // $('.account_main .account_two').removeClass('hide');

    }
    initAuthentication();

    var anchor_details_editor = UE.getEditor('anchor_details_editor',{
        toolbars:[['source', //源代码
            'undo', //撤销
            'redo', //重做
            'bold', //加粗
            'forecolor', //字体颜色
            'backcolor', //背景色
            'indent', //首行缩进
            'removeformat',//清除格式
            'formatmatch', //格式刷
            'blockquote', //引用
            'fontfamily', //字体
            'fontsize', //字号
            'paragraph', //段落格式
            'italic', //斜体
            'underline', //下划线
            'strikethrough', //删除线
            'superscript', //上标
            'subscript', //下标
            'touppercase', //字母大写
            'tolowercase', //字母小写
            'justifyleft', //居左对齐
            'justifyright', //居右对齐
            'justifycenter', //居中对齐
            'justifyjustify',//两端对齐
            'link', //超链接
            'unlink', //取消链接
            'simpleupload', //单图上传
            // 'insertimage', //多图上传
            'emotion', //表情
            'fullscreen'
        ] ],
        elementPathEnabled:false,
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        enableAutoSave:false,
        imagePopup:false,
        maximumWords:10000       //允许的最大字符数
    });
    
    
    
    
    	
	//主播基础信息中的医师的坐诊时间数组
	var arr1 = [];

	$('#workTime  li ').click(function(){
		if($(this).hasClass('color')){
		//删除第二次选中的
			for(var i = 0 ;i < arr1.length; i++){
            	if($(this).text() == arr1[i]){
                arr1.splice(i,1)
            	}
       		}
			workTime = arr1.toString();
			$(this).removeClass('color');	
		}else{
			$(this).addClass('color');
			arr1.push($(this).text());
			workTime = arr1.toString();
		}
		console.log(workTime)
	})
	

	//选择医师列表
	$('#speech_select1').change(function(){
	var hosID = $('#speech_select1').val();
	 RequestService("/medical/hospital/getHospitalById", "get", {
        id: hosID,
    },function(data){
    	$(' #tel').val(data.resultObject.tel);
//		console.log(data)
    })
})
	
	
});

function initAuthentication (){
    RequestService("/medical/doctor/apply/getLastOne", "get", null, function(data) {
        if(data.resultObject==null)return;
        data = data.resultObject;
        $(".anchor_name").html(data.name);
        $(".anchor_idCard").html(data.cardNum);
        $(".anchor_idCard_a").attr("src",data.cardPositive);
        $(".anchor_idCard_b").attr("src",data.cardNegative);
        $(".anchor_qualification_certificate").attr("src",data.qualificationCertificate);
        $(".anchor_professional_certificate").attr("src",data.professionalCertificate);
    });
}

function savePhysicianApply(){
    var physician = getPhysicianData();
    if(verifyPhysician(physician)){
        RequestService("/medical/doctor/apply", "post", physician, function(data) {
            alert(data)
        },false);
    }
}

function getPhysicianData(){
    var data = {};
    data.name = $.trim($('.physician_name').val());
    data.cardNum = $.trim($('.physician_card').val());
    data.cardPositive = $.trim($('#cardPositiveImg img').attr('src'));
    data.cardNegative = $.trim($('#cardNegativeImg img').attr('src'));
    data.qualificationCertificate = $.trim($('#qualificationCertificateImg img').attr('src'));
    data.professionalCertificate = $.trim($('#professionalCertificateImg img').attr('src'));
    return data;
}

function verifyPhysician(data){
    var NameWarnings = $.trim($('.physician_two .name_put0').val());
    var NameCard = $.trim($('.physician_two .name_put1').val());
    var name = $.trim($('.physician_two .name_put0').val());
    var name_pass = /^[\u4E00-\u9FA5]{1,6}$/;;
    var doc_Idnum = $.trim($('#AutList .doc_Idnum').val());
    var doc_Idnum_pass = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
    var personInt = UE.getEditor('editor').getContent();

    //姓名为空
    if(data.name == ''){
        $('.warning_physician_name').removeClass('hide');
        return false;
    }else{
        $('.warning_physician_name').addClass('hide');
    }
    //身份证号为空
    if(data.cardNum == ''){
        $('.warning_physician_card').removeClass('hide');
        return false;
    }else{
        $('.warning_physician_card').addClass('hide');
    }
    //身份证号校验
    if(!isCardID(data.cardNum)){
        $('.warning_physician_card_verify').removeClass('hide');
        return false;
    }else{
        $('.warning_physician_card_verify').addClass('hide');
    }
    if(data.cardPositive == ''){
        $('.warning_cardPositiveImg').removeClass('hide');
        return false;
    }else{
        $('.warning_cardPositiveImg').addClass('hide');
    }
    if(data.cardNegative == ''){
        $('.warning_cardNegativeImg').removeClass('hide');
        return false;
    }else{
        $('.warning_cardNegativeImg').addClass('hide');
    }
    if(data.qualificationCertificate == ''){
        $('.warning_qualificationCertificateImg').removeClass('hide');
        return false;
    }else{
        $('.warning_qualificationCertificateImg').addClass('hide');
    }
    if(data.professionalCertificate == ''){
        $('.warning_professionalCertificateImg').removeClass('hide');
        return false;
    }else{
        $('.warning_professionalCertificateImg').addClass('hide');
    }
    return true;
}

function saveClinicsApply(){
    var clinics = getClinicsData();
    if(verifyClinics(clinics)){
        RequestService("/medical/doctor/apply", "post", clinics, function(data) {
            alert(data)
        },false);
    }
}

function getClinicsData(){
    var data = {};
    data.name = $.trim($('.clinics_name').val());
    data.company = $.trim($('.company').val());
    data.businessLicenseNo = $.trim($('.businessLicenseNo').val());
    data.businessLicensePicture = $.trim($('#businessLicensePictureImg img').attr('src'));
    data.licenseForPharmaceuticalTradingPicture = $.trim($('#licenseForPharmaceuticalTradingPictureImg img').attr('src'));
    return data;
}

function verifyClinics(data){

    if(data.name == ''){
        $('.warning_name').removeClass('hide');
        return false;
    }else{
        $('.warning_name').addClass('hide');
    }
    //身份证号为空
    if(data.company == ''){
        $('.warning_company').removeClass('hide');
        return false;
    }else{
        $('.warning_company').addClass('hide');
    }
    if(data.businessLicenseNo == ''){
        $('.warning_businessLicenseNo').removeClass('hide');
        return false;
    }else{
        $('.warning_businessLicenseNo').addClass('hide');
    }
    if(data.businessLicensePicture == ''){
        $('.warning_businessLicensePictureImg').removeClass('hide');
        return false;
    }else{
        $('.warning_businessLicensePictureImg').addClass('hide');
    }
    if(data.licenseForPharmaceuticalTradingPicture == ''){
        $('.warning_licenseForPharmaceuticalTradingPictureImg').removeClass('hide');
        return false;
    }else{
        $('.warning_licenseForPharmaceuticalTradingPictureImg').addClass('hide');
    }
    return true;
}

function isCardID(sId){
    var iSum=0 ;
    var info="" ;
    if(!/^\d{17}(\d|x)$/i.test(sId))
    //return "你输入的身份证长度或格式错误";
        return false;
    sId=sId.replace(/x$/i,"a");
    if(aCity[parseInt(sId.substr(0,2))]==null)
    //return "你的身份证地区非法";
        return false;
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
    var d=new Date(sBirthday.replace(/-/g,"/")) ;
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))
    //return "身份证上的出生日期非法";
        return false;
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
    if(iSum%11!=1)
    //return "你输入的身份证号非法";
        return false;
    return true;
}

function saveAnchorInfo(){
		
	//基础信息验证通过了验证医师医馆对应的信息
	if(localStorage.AccountStatus == 1){
		var anchorInfo1 = getAnchorInfo();
		if(verifyAnchorInfo(anchorInfo1)){
		 	//验证通过之后进行
		RequestService("/anchor/info", "post", anchorInfo1,function(data){
    	
//		console.log(data)
    	})
		}
	}
	
	
	
	if(localStorage.AccountStatus == 2){
		var ancHosInfo2 = getHosInfo();
		if(verifyAnchorInfo(anchorInfo2)){
		 	//验证通过之后进行
		RequestService("/anchor/info", "post", ancHosInfo2,function(data){
    	
//		console.log(data)
    	})
		}
	}
	
    }


//获取的主播是医师的信息
function getAnchorInfo(){
    var data = {};
    data.name = $(".anchor_nick_name").val();
    data.video = $("#speech_select").val();
    data.profilePhoto = $("#profilePhotoImg img").attr('src');
    data.detail = UE.getEditor('anchor_details_editor').getContent();
    data.hospitalId = $("#speech_select1").val();
    data.workTime = workTime;
    data.province = $("#demo1 #chooseProvince option:selected").text();
    data.city = $('#demo1 #chooseCity option:selected').text();
    data.detailAddress = $('#demo1 textarea').val();
    data.tel = $('#tel').val();
    return data;
}


//获取的主播是医馆的信息
function getAnchorInfo2(){
    var data = {};
    data.name = $(".anchor_nick_name").val();
    data.video = $("#speech_select").val();
    data.profilePhoto = $("#profilePhotoImg img").attr('src');
    data.detail = UE.getEditor('anchor_details_editor').getContent();
    data.province = $("#demo1 #chooseProvince option:selected").text();
    data.city = $('#demo1 #chooseCity option:selected').text();
    data.detailAddress = $('#demo1 textarea').val();
    data.tel = $('#hosTel').val();
    return data;
}




//主播是医师的信息验证
function verifyAnchorInfo(data){
    if(data.name == ''){
        $('.warning_anchor_name').removeClass('hide');
        return false;
    }else{
        $('.warning_anchor_name').addClass('hide');
    }
    
    if(data.profilePhoto == '' || data.profilePhoto == null){
        $('.warning_profileImgphoto').removeClass('hide');
        return false;
    }else{
        $('.warning_profileImgphoto').addClass('hide');
    }
    
     if(data.video == ''){
        $('.warning_anchor_Speech').removeClass('hide');
        return false;
    }else{
        $('.warning_anchor_Speech').addClass('hide');
    }
    
    if(data.detail == ''){
        $('.warning_anchor_lecturer_description').removeClass('hide');
        return false;
    }else{
        $('.warning_anchor_lecturer_description').addClass('hide');
    }
    
    
    //医师入驻的医馆名字
	if(data.hospitalId == '-1'){ 
		$('.return_warning4').removeClass('hide');
		return false;
	}else{
       $('.return_warning4').addClass('hide');
    }
	
	//坐镇的时间
	if(data.workTime == ''){
		$('.return_warning7').removeClass('hide');
		return false;
	}else{
		$('.return_warning7').addClass('hide');
	}
//	
//	//医师所在省市填写
//	if(data.province == '-1' ||  data.city == '-1'){
//		$('.return_warning6').removeClass('hide');
//		return false;
//	}else {
//		 $('.return_warning6').addClass('hide');
//	}

    return true;
}




//主播是医馆的信息验证
function verifyAnchorInfo2(data){
    if(data.name == ''){
        $('.warning_anchor_name').removeClass('hide');
        return false;
    }else{
        $('.warning_anchor_name').addClass('hide');
    }
    
    if(data.profilePhoto == '' || data.profilePhoto == null){
        $('.warning_profileImgphoto').removeClass('hide');
        return false;
    }else{
        $('.warning_profileImgphoto').addClass('hide');
    }
    
     if(data.video == ''){
        $('.warning_anchor_Speech').removeClass('hide');
        return false;
    }else{
        $('.warning_anchor_Speech').addClass('hide');
    }
    
    if(data.detail == ''){
        $('.warning_anchor_lecturer_description').removeClass('hide');
        return false;
    }else{
        $('.warning_anchor_lecturer_description').addClass('hide');
    }
    
    
    //医师入驻的医馆名字
//	if(data.hospitalId == '-1'){ 
//		$('.return_warning4').removeClass('hide');
//		return false;
//	}else{
//     $('.return_warning4').addClass('hide');
//  }
	
	//坐镇的时间
//	if(data.workTime == ''){
//		$('.return_warning7').removeClass('hide');
//		return false;
//	}else{
//		$('.return_warning7').addClass('hide');
//	}
//	
//	//医师所在省市填写
//	if(data.province == '-1' ||  data.city == '-1'){
//		$('.return_warning6').removeClass('hide');
//		return false;
//	}else {
//		 $('.return_warning6').addClass('hide');
//	}

	if(data.tel == ''){
        $('.return_warning8').removeClass('hide');
        return false;
    }else{
        $('.return_warning8').addClass('hide');
    }

    return true;
}


