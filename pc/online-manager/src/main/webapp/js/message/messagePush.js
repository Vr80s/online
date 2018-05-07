var banner2Table;
var banner2Form;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
	nowTime=show();
    $("#addMobileSearch").val(1);
    $("#updateMobileSearch").val(1);
	debugger
    searchJson.push('{"tempMatchType":1,"propertyName":"search_type","propertyValue1":"1","tempType":Integer}');
    loadBanner2List();
    createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
    initUserListModule()



});
function initUserListModule() {
    ajaxRequest("message/messagePush/userlist", null, function (res) {
        if(res.resultObject.length>0){
            for(var i=0;i<res.resultObject.length;i++){
                var user=res.resultObject[i];
                if(user!=null){
                    $("#message_user_select").append("<option value='"+user.id+"'>"+user.name+"</option>");
                }
            }
        }
        $('.selectpicker').selectpicker({
            noneSelectedText: "请选择要发送消息的用户",
            actionsBox: true
        });
        $('.selectpicker').selectpicker('val', '');


    });
}


/*$('.selectpicker').on('change', function(){
    var userId = $(this).find("option:selected").val();
    alert(userId);
    var userName=$(this).find("option:selected").text();
    if(userId!="-1"&&userName!="全部"){
        $(this).selectpicker('val', userId);
       /!* $('#message_questionTable').DataTable().ajax.url(basePath+"/message/load/user/"+userId+"/messages");
        $('#message_questionTable').DataTable().ajax.reload();*!/
        message_user_select_id=userId;
        message_user_select_text=userName;
    }else{
       /!* $('#message_questionTable').DataTable().ajax.url(basePath+"/message/load/messages");
        $('#message_questionTable').DataTable().ajax.reload();*!/
        message_user_select_id=userId;
        message_user_select_text=userName;
    }
});*/
//列表展示
function loadBanner2List(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
		{ "title": checkbox,"class":"center","width":"5%","height":"68px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
		    return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
		}},
        {title: '序号', "class": "center", "width": "5%","height":"68px","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '推送内容', "class": "center","height":"68px","data": 'context', "sortable": false},
        { "title": "推送时间", "class":"center","width":"12%","sortable":false,"data": 'pushTime' },
		{ "title": "状态", "class":"center","width": "8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
			return row.status=="1"?"成功":"草稿";
			}
		},
        {title: '推送用户', "class": "center","width": "12%","height":"68px","data": 'name', "pushCount": false},
        {title: '推送次数', "class": "center","width": "8%","height":"68px","data": 'name', "pushCount": false},
        {title: '打开次数', "class": "center","width": "8%","height":"68px","data": 'name', "pushCount": false},
        {title:"操作","class": "center","width":"10%","height":"34px","data":"id","sortable": false,"mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons">';
	   			if(row.pushCount==0) {
					buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);">立即推送</i></a> ';
				}else{
					buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);">再次推送</a> ';
				};
				buttons += "</div>";
                return buttons;
            }
        }
    ];

    banner2Table = initTables("messagePushTable", basePath + "/message/messagePush/messageslist", dataFields, true, true, 2,null,searchJson,function(data){
        
    	var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize +1;//页码

		var countPage;
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
		
		$("[name='upa']").each(function(index){
			if(index == 0){
				$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
		});
		$("[name='downa']").each(function(index){
			if(index == $("[name='downa']").size()-1){
				$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
		});
    });

    banner2Form = $("#addBanner2-form").validate({
        messages: {
        	name: {
				required:"请输入搜索关键字！",
            }
        }
    });
    
    banner2FormEdit = $("#updateBanner2-form").validate({
        messages: {
        	name: {
				required:"请输入搜索关键字！",
            }
        }
    });

}

 //条件搜索
 function search(){
     searchButton(banner2Table,searchJson);
     searchJson.pop();
     searchJson.pop();

}

//新增框
 $(".add_bx").click(function(){
     $('.selectpicker').selectpicker('deselectAll');
     //$("#selectpicker").selectpicker('refresh');
     banner2Form.resetForm();
 	var dialog = openDialog("addBanner2Dialog","dialogAddBanner2Div","新建推送",780,580,true,"确定",function(){

        /*var str = $("#message_user_select option").map(function(){return $(this).val();}).get().join(", ")
        alert(str);*/
        var str = $("#message_user_select").val();
        alert(str);
        $("#addSearchUsers").val(str);
        if($("#addBanner2-form").valid()){
 			 mask();
 			 $("#addBanner2-form").attr("action", basePath+"/message/messagePush/save");
 	            $("#addBanner2-form").ajaxSubmit(function(data){
                    data = getJsonData(data);
 	                unmask();
 	                if(data.success){
 	                    $("#addBanner2Dialog").dialog("close");
 	                    layer.msg(data.errorMessage);
 	                    freshTable(banner2Table);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	               $("html").eq(0).css("overflow","scroll");
 	         });
 		}
 	});
 });




/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var row = banner2Table.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/mobile/search/updateStatus",{"id":row.id},function(data){
		layer.msg(data.resultObject);
		freshTable(banner2Table);
	});
};


 /**
  * 批量逻辑删除
  * 
  */
$(".dele_bx").click(function(){
 	deleteAll(basePath+"/mobile/search/deletes",banner2Table);
});



//获取当前时间
function show(){
	   var mydate = new Date();
	   var str = "" + mydate.getFullYear() + "-";
	   str += (mydate.getMonth()+1) + "-";
	   str += mydate.getDate() ;
	   return str;
	  }

//字符串转成Time(dateDiff)所需方法
function stringToTime(string) {
   var f = string.split(' ', 2);
   var d = (f[0] ? f[0] : '').split('-', 3);
   var t = (f[1] ? f[1] : '').split(':', 3);
   return (new Date(
  parseInt(d[0], 10) || null,
  (parseInt(d[1], 10) || 1) - 1,
parseInt(d[2], 10) || null,
parseInt(t[0], 10) || null,
parseInt(t[1], 10) || null,
parseInt(t[2], 10) || null
)).getTime();
}

function dateDiff(date1, date2) {
    var type1 = typeof date1, type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();

    return (date2 - date1) / (1000 * 60 * 60 * 24); //结果是秒
}