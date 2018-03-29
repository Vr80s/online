<!-- 导入自定义ftl -->
<#import "../page.ftl" as cast/>
<!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>${tk.title?default('')}熊猫中医名医</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="${tk.keywords?default('')}中医教育,中医传承,中医线下教育，海口中医养生，国粹，传承，中医，中药，心承，熊猫中医">
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/doctor_list.css"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/css/ftl-page.css"/>
    <link rel="stylesheet" href="/web/font/iconfont.css"/>

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script type="text/javascript" src="/web/js/jquery.SuperSlide.2.1.1.js"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/ajax.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/header-top.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<header>
    <#include "../header-body.ftl">
</header>

<div class="content_box">
    <div class="content clearfix" id="main">
        <!--左侧-->
        <div class="main_left">

            <!--名医搜索列表-->
            <div class="doctor clearfix">
                <div class="doctor_search_top clearfix">
                    <h3>筛选</h3>
                    <div class="doctor_search_ipt">
                        <input type="text" placeholder="输入名字搜索医师">
                        <button id="search">搜索</button>
                    </div>

                </div>
                <div class="doctor_search_bottom">
                    <div class="doctor_search_class">
                        <!--<span>分类：</span>-->
                        <span style="padding-left: 28px;">分类：</span>
                        <ul class="clearfix" id="doctor_search_class"><li><a href="javascript:;" class="color">全部</a></li>
                        <#list doctorTypeList as doctorType>
                            <li><a href="javascript:;" data-type="${doctorType.code}">${doctorType.value}</a></li>
                        </#list>
                        </ul>
                    </div>
                    <div class="doctor_search_keshi">
                        <span style="padding-left: 28px;">科室：</span>
                        <ul class="clearfix" id="doctor_search_keshi"><li><a href="javascript:;" class="color">全部</a></li>
                        <#list departments.records as department>
                            <li><a href="javascript:;" data-id="${department.id}">${department.name}</a></li>
                        </#list>
                        </ul>
                    </div>
                    <div class="doctor_search_condition">
                        <span>筛选条件：</span>
                        <ul class="clearfix">
                            <li id="doctor_search_condition1" class="hide">分类：<div style="display: inline-block;"><span>国医大师</span></div><a href="javascript:;"></a></li>
                            <li id="doctor_search_condition2" class="hide">科室：<div style="display: inline-block;"><span>中医内科</span></div><a href="javascript:;"></a></li>
                            <li id="doctor_search_condition3" style="border:none;color: #999;">暂无筛选条件</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!--名医列表展示-->
            <div class="doctor_list">
                <div id="search_num" style="height: 60px;line-height: 60px;border-bottom: 1px solid #f0f0f0;">共找到${doctors.total}位名医</div>
                <ul id="doctor_list">
                    <!--<li class="clearfix">
                        <div class="doctor_pic">
                            <img src="../images/doctor_detail/touxiang.png" alt="" />
                        </div>
                        <div class="doctor_inf">
                            <h4>施小墨&nbsp;&nbsp;胡庆余堂国医馆&nbsp;&nbsp; 广东&nbsp;广州</h4>
                            <span></span>
                            <p>科室： <span>中医内科/儿科/肿瘤科</span></p>
                            <p>擅长：<span>中西医结合治肿瘤、脾胃病、血液病、睡眠障碍、糖尿病、内科杂病等。</span> </p>

                        </div>
                    </li>-->
                <#list doctors.records as doctor>
                    <li class="clearfix">
                        <a href="/doctors/${doctor.id}" id="${doctor.id}"></a>
                        <div class="doctor_pic">
                            <img src="${doctor.headPortrait}" alt="">
                        </div>
                        <div class="doctor_inf">
                            <h4>${doctor.name}&nbsp;&nbsp;&nbsp;&nbsp;
                            ${doctor.province?default('')}&nbsp;${doctor.city?default('')}
                            </h4>
                            <span>${doctor.title?default('暂无')}</span>
                            <!--<p>科室： <span>中医内科/儿科/肿瘤科</span></p>-->
                        </div>
                    </li>
                </#list>
                </ul>
                <!-- 使用该标签 -->
                <@cast.page pageNo=doctors.current totalPage=doctors.pages showPages=5 callUrl="getHostipalList"/>
            </div>


        </div>


        <!--右侧-->
        <div class="main_right">

            <!--名医推荐-->
            <div class="about_doctor">
                <h3>名医推荐</h3>
                <ul class="about_doctor_list clearfix" id="doc_rec">
                    <#--<li>-->
                        <#--<a href="/web/html/practitionerDetails.html?Id=8356a18d548e400d8e8b9b8aaa9d03db"></a>-->
                        <#--<span class="about_doctor_pic">-->
            <#--<img src="http://attachment-center.ixincheng.com:38080/data/picture/online/2018/01/30/10/496ed54d9282454d957035460ee63dd6.jpg" alt="暂无图片">-->
        <#--</span>-->
                        <#--<p>林超岱</p>-->

                    <#--</li>-->

                    <#list recDoctors as doctor>
                        <li>
                            <a href="/doctors/${doctor.id}"></a>
                            <span class="about_doctor_pic">
                                <img src="${doctor.headPortrait}" alt="暂无图片">
                            </span>
                            <p>${doctor.name}</p>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
</div>

<#include "../footer.ftl">
</body>
<script src="/web/js/placeHolder.js"></script>
<script type="application/javascript">
    document.onkeydown = function(e){
        if(!e){
            e = window.event;
        }
        if((e.keyCode || e.which) == 13){
            document.getElementById("search").click();
        }
    }
    $(function(){
        $(".doctor-tab").addClass("select");

        //名医分类筛选效果
        $('#doctor_search_class').on('click','a',function(){
            $('#doctor_search_class li a ').removeClass('color');
            $(this).addClass('color');
            //筛选条件部分变化
            $('#doctor_search_condition3').addClass('hide')
            $('#doctor_search_condition1').removeClass('hide')
            $('#doctor_search_condition1 span').text($(this).text());
            $('#doctor_search_condition1 span').attr('data-type',$(this).attr('data-type'))
            if($(this).text() == '全部'){
                $('#doctor_search_condition1').addClass('hide');
                if($('#doctor_search_condition2').hasClass('hide')){
                    $('#doctor_search_condition3').removeClass('hide')
                }
            }
            //触发搜索功能
            $('.doctor_search_ipt > button').click()
        })

        //名医科室筛选效果
        $('#doctor_search_keshi').on('click','a',function(){
            $('#doctor_search_keshi li a ').removeClass('color');
            $(this).addClass('color');
            //筛选条件部分变化
            $('#doctor_search_condition3').addClass('hide')
            $('#doctor_search_condition2').removeClass('hide')
            $('#doctor_search_condition2 span').text($(this).text());
            $('#doctor_search_condition2 span').attr('data-id',$(this).attr('data-id'))
            if($(this).text() == '全部'){
                $('#doctor_search_condition2').addClass('hide');
                if($('#doctor_search_condition1').hasClass('hide')){
                    $('#doctor_search_condition3').removeClass('hide')
                }
            }
            //触发搜索功能
            $('.doctor_search_ipt > button').click()
        })

        //分类筛选条件删除效果
        $('#doctor_search_condition1 a').click(function(){
            $(this).parent().addClass('hide');
            $('#doctor_search_class li a ').removeClass('color');
            $('#doctor_search_class li:first-child a ').addClass('color');
            if($('#doctor_search_condition2').hasClass('hide')){
                $('#doctor_search_condition3').removeClass('hide')
            }
            $('.doctor_search_ipt > button').click()
        })

        //可是筛选条件删除效果
        $('#doctor_search_condition2 a').click(function(){
            $(this).parent().addClass('hide');
            $('#doctor_search_keshi li a ').removeClass('color');
            $('#doctor_search_keshi li:first-child a').addClass('color');
            if($('#doctor_search_condition1').hasClass('hide')){
                $('#doctor_search_condition3').removeClass('hide')
            }
            $('.doctor_search_ipt > button').click()
        })
    });

    //获取url中参数值的方法
    function getQueryString(name) {
        var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return (r[2]);
        }
        return null;
    }
    //初始化请求信息
    var searchParams;
    searchParams={};
    searchParams.hospitalId = getQueryString('hospitalId')?getQueryString('hospitalId'):null;
    searchParams.name =getQueryString("name")?decodeURI(getQueryString("name")):'';
    searchParams.type =getQueryString('type')?getQueryString('type'):null;
    searchParams.departmentId=getQueryString('departmentId')?getQueryString('departmentId'):null;
    searchParams.current=getQueryString('current')?getQueryString('current'):1;

    function getParams(){
        var params="current="+searchParams.current;
        if(searchParams.hospitalId){
            params+="&hospitalId="+searchParams.hospitalId;
        }
        if(searchParams.name){
            params+="&name="+searchParams.name;
        }
        if(searchParams.type){
            params+="&type="+searchParams.type;
        }
        if(searchParams.departmentId){
            params+="&departmentId="+searchParams.departmentId;
        }
        return params;
    }

    function getHostipalList(current){
        searchParams.current=1;
        if(current!=null)searchParams.current=current;
        searchParams.name =$('.doctor_search_ipt > input').val();
        if($('#doctor_search_condition2').hasClass('hide')){
            searchParams.departmentId = '';
        }else{
            searchParams.departmentId = $('#doctor_search_condition2 span').attr('data-id');
            searchParams.departmentText = $('#doctor_search_condition2 span').html();
        }

        if($('#doctor_search_condition1').hasClass('hide')){
            searchParams.type = '';
        }else{
            searchParams.type = $('#doctor_search_condition1 span').attr('data-type');
            searchParams.typeText = $('#doctor_search_condition1 span').html();
        }
        var params = getParams();
        window.location.href="/doctors/list?"+params;
    }

    $(function(){
        //	渲染到所搜栏中
        if(searchParams.name){
            $('.doctor_search_ipt > input').val(searchParams.name);
        }
        typeClick(searchParams.type);
        departmentClick(searchParams.departmentId);

        //搜索功能
        $('.doctor_search_ipt > button').click(function(e){
            getHostipalList();
        });

        //分类
        function typeClick(type){
            if(type==null)return;
            $('#doctor_search_class li a ').removeClass('color');
            $('#doctor_search_class li a[data-type = '+type+']').addClass('color');
            //筛选条件部分变化
            $('#doctor_search_condition3').addClass('hide')
            $('#doctor_search_condition1').removeClass('hide')
            $('#doctor_search_condition1 span').text( 	$('#doctor_search_class li a[data-type = '+type+']').text());
            $('#doctor_search_condition1 span').attr('data-type', 	$('#doctor_search_class li a[data-type = '+type+']').attr('data-type'))
            if( $('#doctor_search_class li a[data-type = '+type+']').text() == '全部'){
                $('#doctor_search_condition1').addClass('hide');
                if($('#doctor_search_condition2').hasClass('hide')){
                    $('#doctor_search_condition3').removeClass('hide')
                }
            }
        }

        //科室
        function departmentClick(departmentId){
            if(departmentId==null)return;
            $('#doctor_search_keshi li a ').removeClass('color');
            $('#doctor_search_keshi li a[data-id='+departmentId+']').addClass('color');
            //筛选条件部分变化
            $('#doctor_search_condition3').addClass('hide')
            $('#doctor_search_condition2').removeClass('hide')
            $('#doctor_search_condition2 span').text($('#doctor_search_keshi li a[data-id='+departmentId+']').text());
            $('#doctor_search_condition2 span').attr('data-id',	departmentId)
            if($(this).text() == '全部'){
                $('#doctor_search_condition2').addClass('hide');
                if($('#doctor_search_condition1').hasClass('hide')){
                    $('#doctor_search_condition3').removeClass('hide')
                }
            }
        }
    });

</script>