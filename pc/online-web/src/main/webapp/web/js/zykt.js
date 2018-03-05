/**
 * Created by admin on 2016/7/27.
 */
//var bath="onlineweb.ixincheng.com:58000";
/*学员故事*/
$(function () {
	function get_cookie(Name) {
	   var search = Name + "="//查询检索的值
	   var returnvalue = "";//返回值
	   if (document.cookie.length > 0) {
	     sd = document.cookie.indexOf(search);
	     if (sd!= -1) {
	        sd += search.length;
	        end = document.cookie.indexOf(";", sd);
	        if (end == -1)
	         end = document.cookie.length;
	         //unescape() 函数可对通过 escape() 编码的字符串进行解码。
	        returnvalue=unescape(document.cookie.substring(sd, end))
	      }
	   } 
	   return returnvalue;
	}
    if(get_cookie("first_login") == "1"){
		$("#oldModalBack").show();
		$("#old").show();
	}else{
		
	}
    $(".header_left .path a").each(function () {
        if ($(this).text() == "云课堂") {
            $(this).addClass("select");
        } else {
            $(this).removeClass("select");
        }
    });
    addSelectedMenu();
});

//友情链接
/*友情链接模板*/
var friendLink =
    '<h3>友情链接</h3>' +
    '{{each friend}}' +
    '<a href={{$value.url}} target="_blank">{{$value.name}}</a>' +
    '{{/each}}';
/*友情链接模板*/

RequestService("/otherlink/getOtherLink", "GET", "", function (data) {
    $("#friendLink").html(template.compile(friendLink)({friend: data.resultObject}));
});

/**
 * 获取课程难度
 */
RequestService("/menu/getAllMenu?type=1", "GET", "", function (data) {

    var _html="<dd class=\"select-all selected\"><a href=\"#\">全部</a></dd>";
    data=data.resultObject;
    for (var i=0;i<data.length;i++){
        if(data[i].name=='全部'){
            continue;
        }
    _html+='<dd sid=couseTypeId='+data[i].id+'><a href=#>'+data[i].name+'</a></dd>';
    }

    $("#couseTypeIds").html(_html);
    initXX();
},true);

//测试环境只能点击ID为1
template.helper('href', function (num) {
    if (num != "") {
        return '' + bath + '/web/courseDetail/' + num;
    } else {
        return 'javascript:;';
    }
});
var strcourse =
	'{{each item}}' +
    '<div class="course clearfix">' +
    '{{#indexHref($value.description_show,$value.free,$value.id,$value.courseType,$value.type,$value.direct_id,null,$value.coursePwd)}}'+
    '{{#hasImg($value.smallImgPath)}}' +
    '{{#online($value.multimediaType,$value.collection)}}' +
    '<div class="detail">' +
    '<p class="title" data-text="{{$value.gradeName}}" title="{{$value.gradeName}}">{{$value.gradeName}}</p>' +
    // '<p class="timeAndTeac">' +
    // '<span>{{#timeChange($value.courseLength)}}</span><i>|</i>' +
    // '<span>讲师：<span class="teacher">{{$value.name}}</span></span>' +
    // '</p>' +
    '<p class="info clearfix">' +
    '<span>' +
    '{{if $value.free == true}}' +
    '{{if $value.coursePwd == 1}}' +
    '<span class="pricefree">加密</span>' +
    '{{else}}' +
    '<span class="pricefree">免费</span>' +
    '{{/if}}' +
    '{{else}}' +
    '<span class="price">{{$value.currentPrice}}</span><span>熊猫币</span>' +
    '{{/if}}' +
    '</span>' +
    '<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learnd_count==null?0:$value.learnd_count}}</span></span>' +
//    '<span class="stuCount"><img src="/web/images/studentCount.png" alt=""/><span class="studentCou">{{$value.learnd_count}}</span></span>' +
    '</p>' +
    '</div>' +
    '</a>' +
    '</div>' +
    '{{/each}}';


//加载一、二级导航
//课程列表请求数据
firstAjax(1,"_zyxx");

function firstAjax(type,typeName) {
    RequestService("/menu/getAllMenu?type="+type, "GET", "", function (data) {
        var $container = $('#tabFirst'+typeName).empty();
        var $odiv = $('#tabSecond'+typeName).empty();

        $.each(data.resultObject, function (index, item) {
            if (index === 0) {
                $container.append('<li class="select" data-number="' + item.id + '" ><span>' + item.name + '</span></li>');
            } else if (index < 12) {
                $container.append('<li data-number="' + item.id + '"><span>' + item.name + '</span></li>');
            }
        })
        $.each(data.resultObject[0].sencodMenu, function (index, item) {
            if (index === 0) {
                $odiv.append('<li class="cur" data-number="' + item.menuId + '"  data-type="' + item.courseTypeId + '">' + item.name + '</li>');
            } else if (index < 12) {
                $odiv.append('<li data-number="' + item.menuId + '"  data-type="' + item.courseTypeId + '">' + item.name + '</li>');
            }
        });
        var param = {
    		pageSize: 12,
            pageNumber: 1
        };
        secondAjax(param,"");
    });
}

function secondAjax(param,searchStr) {
    RequestService("/course/courseList?"+searchStr, "GET", {
        pageNumber: param.pageNumber ? param.pageNumber : 1,
        pageSize: param.pageSize ? param.pageSize : 12

    }, function (data) {
     var  typeName="_zyxx"
        $("#log"+typeName+" .pages").css({"display": "none", "text-align": "right"});
        if (data.resultObject.items.length == 0) {
            $('#content'+typeName).empty();
            $("#emptyTitle").css("display", "block");
        } else {
            $("#emptyTitle").css("display", "none");

            $("#content_zyxx").html(template.compile(strcourse)({
                item: data.resultObject.items
            }));
            $(".searchPage").css("display","none");
            if (data.resultObject.totalPageCount > 1) { //分页判断
                $(".not-data").remove();
                $("#log"+typeName+" .pages").css({"display": "block", "text-align": "right"});
                $("#log"+typeName+" .pages .searchPage .allPage").text(data.resultObject.totalPageCount);
                if (data.resultObject.currentPage == 1) {
                    $("#Pagination").pagination(data.resultObject.totalPageCount, {
                        callback: function (page) {//翻页功能
                            var pageParam = {
                                pageNumber: (page + 1),
                                pageSize: "12"
                            };
                            secondAjax(pageParam,searchStr);
                        }
                    });
                }
                $(".view-content-notbodys").html("");
            } else if (data.resultObject.totalPageCount = 1 && data.resultObject.totalCount > 0) {
                $("#log"+typeName+" .pages").css({"display": "none", "text-align": "right"});
                $(".view-content-notbodys").html("");
            }
        }
        lazyCkeck();
    });
}
function addSelectedMenu(){
	$(".classroom").css("color","#2cb82c");
}