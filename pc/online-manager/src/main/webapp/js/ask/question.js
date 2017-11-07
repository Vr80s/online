var gradeTable;
var gradeForm;
var gradeteacherform;
var searchJson = new Array();
var courseArray=new Array();
var titleArray= new Array();
$(function() {
    loadQuerstionList();
    //下线时间 时间控件
    createDatePicker($("#startTime"));
    createDatePicker($("#stopTime"));
    
    $.ajax({  
        type:'get',      
        url:basePath+'/ask/question/getMenuList',  
        dataType:'json',  
        success:function(data){
        	//获取到顶级的ul
        	var selectAll = $(".select-type-ul");
        	var htmlStr = "";
        	//遍历添加li
	        for (var i = 0; i < data.length; i++) {  
		        	
	        		htmlStr +=  "<li data-id='"+data[i].id+"' data-type=\"0\"> " +
					        	"	<span class='flag' data-id='"+data[i].id+"' data-type=\"0\" title='"+data[i].name+"'>"+data[i].name+"</span> " +
					        	"	<div class=\"select-type-ul-dv\" name='none1'> " +
					        	"			<div class=\"select-type-ul-dv-d\" name='none1'>"+data[i].name+"</div> " + 
					        	"			<div class=\"select-type-ul-dv-p\"> ";
	        		for(var j = 0; j < data[i].listTagVo.length; j ++)
	        		{
	        			var listTagName = "";
			        	if(data[i].listTagVo[j].name.length >10){
			        		listTagName = data[i].listTagVo[j].name.substring(0,10)+"...";
			        	}else{
			        		listTagName = data[i].listTagVo[j].name;
			        	}
			        	
		        		htmlStr +=  " <a class=\"menuItem\" data-id=\""+data[i].listTagVo[j].name+"\" title='"+data[i].listTagVo[j].name+
		        		"' data-type=\"1\" href='javascript:void(0)')'>"+listTagName+"</a> ";
	        		}
	        		htmlStr +=  "			</div> " +
					        	"	</div> " +
					        	"</li> ";
	            
	        }
	       
	        selectAll.html(htmlStr);
	        
	        $(".select-type .select-type-ul li:first-child").find(".select-type-ul-dv").css("display","none");
	        $(".select-type .select-type-ul li:first-child").css({"display": "block", "background": "0px center"});
	        $(".select-type-ul li:first-child").css("display","none");
        }  
    });
   
});

//问题列表展示
function loadQuerstionList(){
    var dataFields = [
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '问题标题', "class": "center","data": 'title', "sortable": false,"mRender":function(data,display,row){
        	titleArray[row.id]=data.replace(/[\r\n]/g,"");
        	return "<span class='titleSpan' id='"+row.id+"'></span>";//data.replace(/[\r\n]/g,"");
        }},
        {title: '所属学科', "class": "center", "width": "8%","data": 'mentName', "sortable": false},
        {title: '所属标签', "class": "center", "width": "8%","data": 'tags', "sortable": false},
        {title: '回答数', "class": "center", "width": "6%","data": 'answerSum', "sortable": false},
        {title: '浏览数', "class": "center", "width": "6%","data": 'browseSum', "sortable": false},
        {title: '提问用户', "class": "center", "width": "8%","data": 'createPersonName', "sortable": false},
        {title: '用户来源', "class": "center", "width": "8%","data": 'origin', "sortable": false,"mRender":function(data,display,row){
        	var origin = type = "";
        	if(row.origin == "online"){
        		return "熊猫中医"
        	}else if(row.origin == "dual"){
        		origin = "双元";
        	}else if(row.origin == "bxg"){
        		origin = "院校";
        	}else if(row.origin == "ask"){
        		origin = "问答精灵";
        	}
        	
        	if(row.type == "0"){
        		type = "用户";
        	}else if(row.type == "1"){
        		type = "学生";
        	}else if(row.type == "2"){
        		type = "老师";
        	}

            return origin + type;
        }},
        {title: '提问日期', "class": "center", "width": "8%","data": 'createTime', "sortable": false},
        {title: '问题状态', "class": "center", "width": "8%", "data": 'status', "sortable": false,"mRender":function(data,display,row){
            return data==2 ? '已解决':'未解决'
        }},
        {
            "sortable": false,"data":"id","class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {


                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                             '<a class="blue" href="javascript:void(-1);" title="查看详情" onclick="showQuestionPage(\''+row.id+'\')"><i class="ace-icon glyphicon glyphicon-list-alt bigger-130"></i></a>';
                return buttons;

            }
        }
    ];

    questionTable = initTables("questionTable", basePath + "/ask/question/findQuestionList", dataFields, true, true, 1,null,searchJson,function(data){
    	$(".titleSpan").each(function(index){
    		$(this).text(titleArray[$(this).attr("id")]);
    	});
    	//赋值完成后清空
    	titleArray.length = 0;
    });
}

 //条件搜索
 function search(orders){
	 
     var startTime=$("#startTime").val(); //开始时间
     var stopTime=$("#stopTime").val(); //结束时间
     var status=$("#status").val();
     var title=$("#title").val();
     var name=$("#name").val();
     var menuTitle = $(".menu-title");
     if(startTime != "" || stopTime != "") {
         
    	 if (startTime != "" && stopTime != "" && startTime > stopTime) {
             alertInfo("开始日期不能大于结束日期");
             return;
         }
         searchJson.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
         searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
     }
     
     if(status != ""){
    	 searchJson.push('{"tempMatchType":"8","propertyName":"status","propertyValue1":"' + status + '","tempType":"String"}');
     }
     
     if(title!=null&&title!=""){
         searchJson.push('{"tempMatchType":"9","propertyName":"title","propertyValue1":"' + title + '","tempType":"String"}');
     }
     
     if(name!=null&&name!=""){
         searchJson.push('{"tempMatchType":"10","propertyName":"name","propertyValue1":"' + name + '","tempType":"String"}');
     }
     if(orders == 1){
    	 searchJson.push('{"tempMatchType":"11","propertyName":"answerSum","propertyValue1":"1","tempType":"String"}');
     }
     if(orders == 2){
    	 searchJson.push('{"tempMatchType":"11","propertyName":"browseSum","propertyValue1":"1","tempType":"String"}');
     }
     if(menuTitle.attr("data-type") == "0"){//如果数据类型为0的时候
	     searchJson.push('{"tempMatchType":"13","propertyName":"mentId","propertyValue1":"'+menuTitle.attr("data-id")+'","tempType":"String"}');
     }
     if(menuTitle.attr("data-type") == "1"){//标签名称
    	 searchJson.push('{"tempMatchType":"12","propertyName":"tags","propertyValue1":"'+menuTitle.attr("data-id")+'","tempType":"String"}');
     }
     searchButton(questionTable,searchJson);
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
 }

 //删除页面
 function showQuestionPage(questionId){
	 var newTab=window.open('about:blank');
	 debugger;
	 newTab.location.href = weburl+"/web/jumpMethod/"+questionId+"/1/"+mname;
	
}

//全部分类下拉框相关
var menuId = "", type = "", urlParam = {
	menuId : menuId,
	type : type
};

$(".select-type-ul").click(
		function(evt) {
			var target = $(evt.target);
			
			if(target.attr("name") == "none1")
			{
				return false;
			}

			if (target.text() == "全部分类") {
				$(".select-type-ul li:first-child").css("display", "none");
			} else {
				$(".select-type-ul li:first-child").css("display", "block");
			}

			$(".select-type-ul li").each(function(i) {
				if ($(this).find("span").text() == "全部分类") {
					$(this).css({
						background : "0",

					}).attr("data-id", "").find(".select-type-ul-dv").remove();
				}

			});

			if (target.hasClass("menuItem")) {
				$(".select-type-ul").css("display", "none");
				$(".select-type-ul-dv").css("display", "none");
				setTimeout(function() {
					$(".select-type-ul").removeAttr("style");
					$(".select-type-ul-dv").removeAttr("style");
					search();
				}, 100);
				urlParam.menuId = target.attr("data-id");
				if (urlParam.menuId != null) {
					urlParam.type = "";
					$(".select-type .menu-title").text(target.text()).attr(
							"data-id", target.attr("data-id")).attr(
							"data-type", target.attr("data-type")).attr(
							"title", target.text().trim().substr(0,7).trim());
				}
			} else {
				$(".select-type-ul").css("display", "none");
				setTimeout(function() {
					$(".select-type-ul").removeAttr("style");
					search();
				}, 100);
				target = target;
				if (target.attr("data-id") == undefined) {
					urlParam.menuId = ""
				} else {
					urlParam.menuId = target.attr("data-id");
				}
				if (urlParam.menuId != null && urlParam.menuId != "") {
					urlParam.type = "";
					if (target.find("span").length == 0) {
						var parentMenuName = target.text();
					} else {
						var parentMenuName = target.find("span").text();
					}
					if (parentMenuName == "") {
						$(".select-type-ul li:first-child").css("display",
								"none");
						// return false;
						parentMenuName = target.text();
						$(".select-type .menu-title").text(parentMenuName)
								.attr("data-id", target.data("id")).attr(
										"data-type", target.attr("data-type"))
								.attr("title", target.text().trim().substr(0,7).trim());
					} else {
						$(".select-type .menu-title").text(parentMenuName)
								.attr("data-id", target.data("id")).attr(
										"data-type", target.attr("data-type"))
								.attr("title", target.text().trim().substr(0,7).trim());
					}
				}
			}
		});