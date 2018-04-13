//出现黑色提示弹窗方法

var msgTip='<div id="blackTip" style="display: none;padding: 8px 32px;'+
			'background-color: #fff;border-radius: 4px;background-color: #000;'+
			'opacity: .5;position: fixed;color: #fff;top: 50%;left: 50%;transform: translate(-50%,-50%);'+
			'z-index: 1000;">保存失败</div>';
function showTip(contant,fn){
	$("body").append(msgTip);
    $('#blackTip').text(contant).show();
    setTimeout(function(){
        $('#blackTip').text(contant).hide();
        if(fn!=null)fn();
    },2000)
}	

