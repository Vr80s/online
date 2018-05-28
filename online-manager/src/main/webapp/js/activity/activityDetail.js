var activityDetailTable;
var searchJson = new Array();
$(function() {
    createDatePicker($(".profile-info-row .datetime-picker"));
 $('#add_startTime').datetimepicker({
    	
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#add_startTime" );
    		var $endDate = $('#add_endTime');
    		var endDate = $endDate.datepicker( 'getDate' );
    		if(endDate < startDate){
    			$endDate.datetimepicker('setDate', startDate - 3600*1*24*60*60*60);
    		}
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });

    $('#add_endTime').datetimepicker({
    	showSecond: true,
		changeMonth: true,
		changeYear: true,
		dateFormat:'yy-mm-dd',
		monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
		timeFormat: 'HH:mm',
    	onSelect: function( endDate ) {
    		var $startDate = $( "#add_startTime" );
    		var $endDate = $('#add_endTime');
    		var startDate = $startDate.datepicker( "getDate" );
    		if(endDate < startDate){
    			$startDate.datetimepicker('setDate', startDate + 3600*1*24*60*60*60);
    		}
    		$startDate.datetimepicker( "option", "maxDate", endDate );
    	}
    });

  
    
    
    $('#update_startTime').datetimepicker({
    	
    	showSecond: true,
    	changeMonth: true,
    	changeYear: true,
    	dateFormat:'yy-mm-dd',
    	monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
    	timeFormat: 'HH:mm',
    	onSelect: function( startDate ) {
    		var $startDate = $( "#update_startTime" );
    		var $endDate = $('#update_endTime');
    		var endDate = $endDate.datepicker( 'getDate' );
    		if(endDate < startDate){
    			$endDate.datetimepicker('setDate', startDate - 3600*1*24*60*60*60);
    		}
    		$endDate.datetimepicker( "option", "minDate", startDate );
    	}
    });
    
    $('#update_endTime').datetimepicker({
    	showSecond: true,
    	changeMonth: true,
    	changeYear: true,
    	dateFormat:'yy-mm-dd',
    	monthNamesShort: [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ],
    	timeFormat: 'HH:mm',
    	onSelect: function( endDate ) {
    		var $startDate = $( "#update_startTime" );
    		var $endDate = $('#update_endTime');
    		var startDate = $startDate.datepicker( "getDate" );
    		if(endDate < startDate){
    			$startDate.datetimepicker('setDate', startDate + 3600*1*24*60*60*60);
    		}
    		$startDate.datetimepicker( "option", "maxDate", endDate );
    	}
    });
    
    loadActivityDetailList();
});
//列表展示
function loadActivityDetailList(){
	var dataFields = [
		{title: '规则ID', "class": "center", "width": "5%","height":"68px","data": 'id', "sortable": false},
		{title: '活动ID', "class": "center", "width": "5%","height":"68px","data": 'actId', "sortable": false,"visible":false},
        {title: '活动名称', "class": "center","height":"68px","width": "20%","data": 'name', "sortable": false},
        {title: '活动落地页', "class": "center","width": "15%","data": 'url', "sortable": false,"mRender":function(data,display,row){
        	return "<div style='white-space:normal;'><a href='"+data+"' target='blank'>"+data+"</a></div>";
        }},
        {title: '创建人', "class": "center", "width": "8%","data": 'createPersonName', "sortable": false},
        
        {title: '规则生效起止时间', "class": "center", "width": "17%","data": 'ruleStartTime', "sortable": false,"mRender":function(data,display,row){
    		return row.startTime + " 至 " + row.endTime;
        }},
        {title: '订单量', "class": "center", "width": "6%","height":"68px","data": 'actOrderSum', "sortable": false},
        {title: '参与用户数', "class": "center", "width": "9%","height":"68px","data": 'actUserSum', "sortable": false},
        {title: '订单总额（元）', "class": "center", "width": "10%","height":"68px","data": 'actOrderMoneyTotal', "sortable": false},
        {title:"操作","class": "center","width":"9%","height":"34px","data":"id","sortable": false,"mRender":function (data, display, row) {
        	var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
        	'<a class="blue" href="javascript:void(-1);" title="查看" onclick="showDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
        	'<a class="blue" href="javascript:void(-1);" title="数据" onclick="previewDialogCourse(this)"><i class="ace-icon glyphicon glyphicon-stats bigger-130" ></i></a>'
        	buttons += '</div>';
            return buttons;
            }
        }
    ];
    activityDetailTable = initTables("activityDetailTable", basePath + "/activity/activityDetail/findActivityDetailList", dataFields, true, true, 1,null,searchJson,function(data){
    });
}
/**
* 日期格式化yyyy-MM-dd HH:mm:ss
*/
function FormatDate (strTime) {
	var year = strTime.getFullYear();
	var month = getFormat(strTime.getMonth()+1);
	var day = getFormat(strTime.getDate());
	var hours = getFormatHMS(strTime.getHours());
	var minutes = getFormatHMS(strTime.getMinutes());
	//var seconds = getFormatHMS(strTime.getSeconds());
	
    return year+"-"+month+"-"+day +" "+hours +":"+(minutes>=30?30:'00' ); 
}
//查看各规则数据
function showDialog(obj){
	var oo = $(obj).parent().parent().parent();
	var row = activityDetailTable.fnGetData(oo);// get datarow
	$(".autoTr").remove();
	$.ajax({
		  url:"/activity/activityDetail/getActivityDetailPreferenty",
		  type:"get",
		  data:{actId:row.id},
		  success:function(result){
			  if(result.success){
				  var str="<tr class='autoTr'><th>规则内容</th><th>订单量</th><th>用户数</th><th>订单总额（元）</th></tr>";
				  var resuts = result.resultObject;
				  var sum1 = 0;
				  var sum2 = 0;
				  var sum3 = 0;
				 for(var i=0;i<resuts.length;i++){
					 sum1=sum1 +resuts[i].actOrderSum;
					 sum2=sum2 +resuts[i].actUserSum;
					 sum3=sum3 +resuts[i].actOrderMoneyTotal;
					var pref = (resuts[i].preferenty==null?"":resuts[i].preferenty);
					str=str+"<tr class='autoTr'><td >"+resuts[i].actContent+"</td><td>"+resuts[i].actOrderSum+"</td><td>"+resuts[i].actUserSum+"</td><td>"+resuts[i].actOrderMoneyTotal+"</td></tr>"
					
				  }
				 var foot="<tr class='autoTr'><td >合计</td><td>"+sum1+"</td><td>"+sum2+"</td><td>"+sum3+"</td></tr>";
				 $("#showCoursePreferenty").append(str);
				 $("#showCoursePreferenty").append(foot);
			    
				 $("#dataTime").html(FormatDate (new Date()));
				 
			  }
		  }
	  })
	  
	  var dialog = openDialog("showActivityDetailDialog","dialogShowActivityDetailDiv","各规则数据",750,630,false,"关闭",null);
}
//查看课程详情数据
function previewDialogCourse(obj){
	var oo = $(obj).parent().parent().parent();
	var row = activityDetailTable.fnGetData(oo); // get datarow
	$(".autoTr").remove();
	$.ajax({
		  url:"/activity/activityDetail/getActivityDetailCourse",
		  type:"get",
		  data:{actId:row.id},
		  success:function(result){
			  if(result.success){
				  var str="<tr class='autoTr'><th>课程名称</th><th>销量</th><th>单价（元）</th></tr>";
				  var resuts = result.resultObject;
				
				 for(var i=0;i<resuts.length;i++){
					var pref = (resuts[i].preferenty==null?"":resuts[i].preferenty);
					str=str+"<tr class='autoTr'><td >"+resuts[i].courseName+"</td><td>"+resuts[i].saleSum+"</td><td>"+resuts[i].price+"</td></tr>"
					
				  }
				  $("#showCourseDetailPreferenty").append(str);
				 // $("#showCourseDetailPreferenty").append(foot);
				 
				  $("#updateDataTime").html(FormatDate (new Date()));
				 
			  }
		  }
	  })
	  
	  var dialog = openDialog("showCourseActivityDetailDialog","dialogShowCourseActivityDetailDiv","课程详情数据",750,630,false,"关闭",null);
}
//条件搜索
function search(){
	 
    searchButton(activityDetailTable,searchJson);
}
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
jQuery.fn.rowspan = function(colIdx) {//封装jQuery小插件用于合并相同内容单元格(列)  
    return this.each(function(){   
        var that;  
        var thatNext;//相邻单元格
        var sum=0;
        $('tr', this).each(function(row) {   
            $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {   
                if (that!=null && $(this).html() == $(that).html()) {   
                    rowspan = $(that).attr("rowSpan");
                    rowspanNext = $(thatNext).attr("rowSpan");
                    if (rowspan == undefined) {   
                        $(that).attr("rowSpan",1);   
                        rowspan = $(that).attr("rowSpan"); 
                        
                        $(thatNext).attr("rowSpan",1);   
                        rowspanNext = $(that).attr("rowSpan"); 
                    }   
                    rowspan = Number(rowspan)+1;   
                    $(that).attr("rowSpan",rowspan);   
                    $(this).hide(); 
                    
                    rowspanNext = Number(rowspanNext) + 1;
                    sum+=parseInt($(this).next().html())
					$(thatNext).attr("rowSpan", rowspanNext);
                   
					$(thatNext).html(sum);
					$(this).next().hide();

                } else {   
                    that = this; 
                    thatNext = $(this).next();
                    console.log(thatNext);
                    sum+=parseInt($(thatNext).html())
                }   
            });   
        });   
    });   
}   