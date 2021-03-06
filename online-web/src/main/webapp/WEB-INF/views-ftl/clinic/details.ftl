<!DOCTYPE html>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if IE 9]>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IEedge">

    <title>${tk.title?default('')}熊猫中医医馆</title>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="keywords" content="${tk.keywords?default('')}中医教育,中医传承,中医线下教育,海口中医养生,国粹,传承,中医,中药,心承,熊猫中医">
    <meta name="description" content="熊猫中医是中医药的学习传承平台：学中医、懂中医、用中医，让中医服务于家庭、个人，让中国古代科学瑰宝为现代人类的健康保驾护航。">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="/web/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="/web/css/mylogin.css"/>
    <link rel="stylesheet" href="/web/css/componet.css"/>
    <link rel="stylesheet" href="/web/css/header.css"/>
    <link rel="stylesheet" href="/web/css/hospital_details.css?v=ipandatcm_1.2.1"/>
    <link rel="stylesheet" href="/web/css/footer.css"/>
    <link rel="stylesheet" href="/web/css/style2.css"/>

    <script src="/web/js/jquery-1.12.1.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/web/js/artTemplate.js"></script>
    <script type="text/javascript" src="/web/js/jquery.SuperSlide.2.1.1.js"></script>
    <script src="/web/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script src="/web/js/common/common.js" type="text/javascript" charset="utf-8"></script>
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
            <!--医馆信息-->
            <div class="hospital_detail_inf">
                <h3>${clinic.name}</h3>
                <div class="hospital_detail_pic clearfix" id="hospital_detail_pic">
                    <div class="successlunbo">
                        <div class="succesny">
                            <div class="control">
                                <ul class="change">
                                </ul>
                            </div>
                            <div class="thumbWrap">
                                <div class="thumbCont">
                                    <ul id="lunbo">
                                        <!-- img属性, url=url, text=描述, bigimg=大图, alt=标题  -->
                                        <!--<li>
                                            <div><img src="../images/1.jpg" url="url" bigImg="../images/1.jpg"></div>
                                        </li>
                                       -->
                                    <#list clinic.medicalHospitalPictures as picture>
                                        <li>
                                            <div><img src="${picture.picture}" url="javascript:;"
                                                      bigimg="${picture.picture}"></div>
                                        </li>
                                    </#list>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="hospital_detail_con">
                    <p>${clinic.description?default('')}</p>
                    <!--<p>新中国成立后，中医药事业得到了很大的发展，为了进一步发扬中医药的特色和优势，发挥离退休专家的余热，解决群众看病难、看专家更难的问题，留住中医的根，由南京中医药大学、江苏省中医院、省人民医院、省中西医结合医院、南大鼓楼医院、东大中大医院、南京市中医院和江苏省中医药学会部分离退休领导干部、专家，国家、省、市级名老中医于2003年7月29日恢复了江苏省国医馆。</p>-->
                <#if clinic.filds??>
                    <div id="hos_inf">
                        <p>医疗领域：
                            <#list clinic.fields as field>
                                <span>${field.name}&nbsp;</span>
                            </#list>
                        </p>
                    </div>
                </#if>

                </div>


                <div class="hospital_detail_inf_bottom">
                    <p>
                        <span class="hospital_detail_inf_bottom_tel">联系电话：${clinic.tel!''}</span>
                        <span>
                            地址：<span class="sheng">${clinic.province!''}</span> <span
                                class="shi">${clinic.city!''}</span> <span class="detail"
                                                                           title="${clinic.detailedAddress!''}">${clinic.detailedAddress!''}</span>
                        </span>
                    </p>
                </div>
            </div>

        <#if doctors.pages gt 0>
            <!--医馆名家-->
            <div class="hospital_doctor clearfix">
                <div class="hospital_top">
                    <span>医馆名家</span>
                    <#if doctors.pages gt 1>
                        <a href="${webUrl}/clinics/${clinic.id}/doctors" id="more_doc" target="_blank">
                            更多&nbsp;&gt;
                        </a>
                    </#if>
                </div>
                <ul class="doctor_inf" id="yiguan_mingjia">
                    <#list doctors.records as doctor>
                        <li>
                            <a href="${webUrl}/doctors/${doctor.id}" target="_blank"></a>
                            <img src="${doctor.headPortrait!defaultDoctorHeadImg}?imageMogr2/thumbnail/!70x70r|imageMogr2/gravity/Center/crop/70x70" alt="${doctor.name}">
                            <h5>${doctor.name}</h5>
							<#if doctor.workTime?? && doctor.workTime!='暂无' >
			                    <p>${doctor.workTime?default('')}</p>
		                    </#if>
                            <p>${doctor.province?default('')}&nbsp;${doctor.city?default('')}</p>
                        </li>
                    </#list>
                </ul>
            </div>
        </#if>
            <!--课程-->
        <#if courses?? && courses?size gt 0>
            <div class="class clearfix">
                <div class="class_top">
                    <span>课程</span>
                    <a href="${webUrl}/anchors/${accountId}/courses">
                        更多&nbsp;&gt;
                    </a>
                </div>

                <div class="class_bottom">
                    <#list courses as course>

                        <div class="class_inf">
                            <a href="${webUrl}/courses/${course.id}/info" style="color: #0C0C0C" target="_blank">
                                <div class="class_inf_pic">
                                    <img src="${course.smallImgPath}" alt="">
                                </div>
                                <div class="class_inf_bottom">
                                    <p class="class_title">${course.gradeName!''}</p>
                                    <#if course.city??><p class="class_address"><em></em>${course.city!''}</p></#if>
                                    <p class="class_pirce_person">

                                        <#if course.currentPrice?? && course.currentPrice gt 0>
                                            <span class="class_pirce">
                                            ${course.currentPrice!''}
                                        </span>
                                            熊猫币
                                        <#else>
                                            <span style="color: #F97B49">免费</span>
                                        </#if>

                                        <span class="class_person class_Studycount"><em></em>${course.learndCount!''}</span>
                                    </p>
                                </div>
                            </a>
                        </div>

                    </#list>
                </div>

            </div>
        </#if>


            <!--联系方式-->
            <!--<div class="contant_way clearfix hide">
             <h3>联系方式</h3>
                <div class="map">
                    	地图
                </div>
                <p><span>联系电话：17289929373</span><span>邮编：21003</span></p>
                <p><span>电子邮箱：13702283872@163.com</span><span>微信：387289990</span></p>
                <p>地址：<span>江苏省南京市清凉门大街168号</span></p>
       		</div>-->

        </div>


        <!--右侧-->
        <div class="main_right ">
            <!--帐号认领-->
        <#if announcement??>
            <div class="import_thing">
                <p class="title" style="font-size: 16px;">重要通知</p>
                <p>${announcement.content}</p>
                <span>${announcement.createTime?string('yyyy-MM-dd')}</span>
            </div>
        </#if>

        <#if recruits??&&(recruits?size>0)>
            <div class="employ" id="employ">
                <h3 style="font-size: 16px;font-weight: 400;">招聘信息</h3>

                <#list recruits as recruit>
                    <!--招募信息-->
                    <div class="employ_inf">
                        <h4>招聘岗位：${recruit.position}</h4>
                        <p>岗位职责：<br><span>${recruit.postDuties}</span></p>
                        <p>任职要求：<br><span>${recruit.jobRequirements}</span></p>
                    </div>
                </#list>
            </div>
        </#if>


            <!--优秀医馆-->
            <div class="good_hospital" id="good_hospital">
                <span>优秀医馆</span>
                <ul id="good_hospital_list">
                <#list recClinics as recClinic>
                    <li>
                        <a href="${webUrl}/clinics/${recClinic.id}">
                            <#if recClinic_index <= 2>
                                <em class="select">${recClinic_index+1}</em>
                            <#else>
                                <em>${recClinic_index+1}</em>
                            </#if>
                        ${recClinic.city}&nbsp;&nbsp;${recClinic.name}
                        </a>
                    </li>
                </#list>
                </ul>
            </div>


        </div>
    </div>
</div>
<#include "../footer.ftl">
</body>
<script src="/web/js/jquery.pagination.js"></script>
<script src="/web/js/placeHolder.js"></script>
<script src="/web/js/slides-1.1.1-min.js"></script>
<script type="application/javascript">
    $(function () {
        $(".hospital-tab").addClass("select");
    });
    //启动轮播图
    $('.succesny').olvSlides({
        thumb: true,
        thumbPage: true,
        thumbDirection: "Y",
        effect: 'fade'

    });
    //    init();
</script>