var scoreTypeTable;
var roleForm;
var searchCase;
var seed=1;
var searchCase = new Array();
$(function(){
	document.onkeydown=function(event){
		if(event.keyCode==13){
			return false
		}
	}
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"60px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        { "title": "课程ID", "class": "center","width":"5%","sortable": false,"data":'id' },
        { "title": "课程名称", "class":"center","width":"9%","sortable":false,"data": 'courseName' },
        { "title": "资源类型", "class":"center","width":"6%","sortable":false,"data": 'multimediaType' ,"mRender":function (data, display, row) {
                if(data == 1){
                    return "视频";
                }
                return "音频";
            }},
        { "title": "上传人", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
		{ "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
			return row.status=="1"?"已启用":"已禁用";
			}
		},
		{ "sortable": false,"data":"id","class": "center","width":"10%","title":"排序","mRender":function (data, display, row) {
			if(row.status ==1){//如果是禁用
				return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				'<a class="blue" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
			}else{
				return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
			}
			}
		}
		      		];
	scoreTypeTable = initTables("scoreTypeTable",basePath+"/essencerecommend/course/list",objData,true,true,2,null,searchCase,function(data){
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

	//下线时间 时间控件
	createDatetimePicker($('.datetime-picker'));

});

/**
 * 批量精品推荐
 *
 */
$(".add_jp").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/essencerecommend/course/updateEssenceRec",{'ids':ids.join(","),"isRec":1},function(data){
            if(!data.success){//如果失败
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(scoreTypeTable)){
                    layer.msg("推荐成功！");
                    search();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择推荐课程！","");
    }
});
/**
 * 取消精品推荐
 *
 */
$(".deletes_jp").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/essencerecommend/course/updateEssenceRec",{'ids':ids.join(","),"isRec":0},function(data){
            if(!data.success){//如果失败
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(scoreTypeTable)){
                    layer.msg("取消成功！");
                    search();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择要取消课程！","");
    }
});


/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	ajaxRequest(basePath+"/cloudClass/scoreType/updateStatus",{"id":aData.id,"status":aData.status},function(){
		freshTable(scoreTypeTable);
	});
}


/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	ajaxRequest(basePath+"/essencerecommend/course/upMove",{"id":aData.id},function(res){
		if(res.success){
			freshTable(scoreTypeTable);
		}
	});
}

/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	ajaxRequest(basePath+"/essencerecommend/course/downMove",{"id":aData.id},function(res){
		if(res.success){
			freshTable(scoreTypeTable);
		}
	});
}



function search(){

		searchCase.push('{"tempMatchType":"5","propertyName":"name","propertyValue1":null,"tempType":"String"}');

	searchButton(scoreTypeTable,searchCase);

}




