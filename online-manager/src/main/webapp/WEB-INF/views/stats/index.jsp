<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/jstl_taglib.jsp" %>
<link href="/css/jquery-ui-timepicker-addon.css" type="text/css"/>
<link href="/js/layer/skin/layer.css" type="text/css"/>
<script type="text/javascript">
    try {
        var scripts = [ null, null ];
        $('.page-content-area').ace_ajax('loadScripts', scripts,function(){});
    } catch (e) {}
</script>
<script src="/js/layer/layer.js"></script>
<script src="/js/jquery-ui-timepicker-zh-CN.js" type="text/javascript"></script>
<script src="/js/echarts.min.js" type="text/javascript"></script>
<div class="page-header">
    当前位置：数据统计管理
</div>


<div class="mainrighttab tabresourse bordernone">
    <%--<div class="searchDivClass" id="searchDiv">
        <div class="profile-info-row">
            <table frame=void>
                <tr>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="date" name="startTime" id="startTime" maxlength="100"
                                   value="${startTime}">
                        </div>
                    </td>
                    <td>
                        <div class="profile-info-value searchTr">
                            <input type="date" name="endTime" id="endTime" maxlength="100"
                                   value="${endTime}">
                        </div>
                    </td>
                </tr>
            </table>
            <div>
                <input type="button" class="J-button btn-primary" value="确定"/>
            </div>
        </div>
    </div>--%>

        <div class="searchDivClass" id="searchDiv">
            <div class="profile-info-row" >
                <table frame=void >
                    <tr>
                        <td>
                            <div class="profile-info-value searchTr">
                                <input type="text" class="datetime-picker propertyValue1"  value="${startTime}" id="startTime" name="startTime" placeholder = "开始日期" style="width:130px"/>
                                <input type="hidden" value="startTime" class="propertyName"/>
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value">
                                至
                            </div>
                        </td>
                        <td>
                            <div class="profile-info-value searchTr">
                                <input type="text" class="datetime-picker propertyValue1"  value="${endTime}" id="endTime" name="endTime" placeholder = "结束日期" style="width:130px"/>
                                <input type="hidden" value="endTime" class="propertyName"/>
                            </div>
                        </td>
                        <td>
                            <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
                                    onclick="render();">
                                <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                            </button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>



</div>
<div></div>
</div>

<div class="row">
    <div class="col-xs-12" id="user-chart" style="width: 400px;height: 400px;">

    </div>
    <div class="col-xs-12" id="course-chart" style="width: 400px;height: 400px;">

    </div>
    <div class="col-xs-12" id="anchor-chart" style="width: 400px;height: 400px;">

    </div>
    <div class="col-xs-12" id="order-chart" style="width: 400px;height: 400px;">

    </div>
    <div class="col-xs-12" id="income-chart" style="width: 400px;height: 400px;">

    </div>
</div>


<script type="text/javascript" src="/js/stats/index.js?v=ipandatcm_1.3"></script>
<script type="text/javascript">
    $('.ajax-loading-overlay').hide();
    var data = {};
    var userChart = echarts.init(document.getElementById('user-chart'));
    var courseChart = echarts.init(document.getElementById('course-chart'));
    var anchorChart = echarts.init(document.getElementById('anchor-chart'));
    var orderChart = echarts.init(document.getElementById('order-chart'));
    var incomeChart = echarts.init(document.getElementById('income-chart'));
    render();


    function render() {
        var startTime = $('#startTime').val();
        var endTime = $('#endTime').val();
        if (!startTime || !endTime) {
            alertInfo("请选择时间");
            return false;
        }
        if (startTime > endTime) {
            alertInfo("开始时间不能大于结束时间");
            return false;
        }
        $.ajax({
            method: "GET",
            url: "/stats/data",
            data: {
                "startTime": new Date(startTime),
                "endTime": new Date(endTime)
            },
            async: false,
            success: function (resp) {
                data = resp.resultObject;
            }
        });
        var userX = [];
        var userSum = 0;
        for (var i = 0; i < data.userStatsData.length; i++) {
            userX[i] = data.userStatsData[i].name;
            userSum = userSum + data.userStatsData[i].value;
        }
        var courseX = [];
        var courseSum = 0;
        for (var i = 0; i < data.courseStatsData.length; i++) {
            courseX[i] = data.courseStatsData[i].name;
            courseSum = courseSum + data.courseStatsData[i].value;
        }
        var anchorX = [];
        var anchorSum = 0;
        for (var i = 0; i < data.anchorStatsData.length; i++) {
            anchorX[i] = data.anchorStatsData[i].name;
            anchorSum = anchorSum + data.anchorStatsData[i].value;
        }
        var orderX = [];
        var orderSum = 0;
        for (var i = 0; i < data.orderStatsData.length; i++) {
            orderX[i] = data.orderStatsData[i].name;
            orderSum = orderSum + data.orderStatsData[i].value;
        }
        var incomeX = [];
        var incomeSum = 0;
        for (var i = 0; i < data.incomeStatsData.length; i++) {
            incomeX[i] = data.incomeStatsData[i].name;
            incomeSum = incomeSum + data.incomeStatsData[i].value;
        }

        userChartOption = {
            title: {
                text: '用户注册来源',
                subtext: '总计:' + userSum,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: userX
            },
            noDataLoadingOption: {
                text: '暂无数据',
                effect: 'bubble',
                effectOption: {
                    effect: {
                        n: 1
                    }
                }
            },
            series: [
                {
                    name: '用户注册来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: data.userStatsData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        courseChartOption = {
            title: {
                text: '课程来源',
                subtext: '总计:' + courseSum,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: courseX
            },
            series: [
                {
                    name: '课程来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: data.courseStatsData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        anchorChartOption = {
            title: {
                text: '主播来源',
                subtext: '总计:' + anchorSum,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: anchorX
            },
            series: [
                {
                    name: '主播来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: data.anchorStatsData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        orderChartOption = {
            title: {
                text: '订单来源',
                subtext: '总计:' + orderSum,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: orderX
            },
            series: [
                {
                    name: '订单来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: data.orderStatsData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        incomeChartOption = {
            title: {
                text: '收入来源',
                subtext: '总计:' + incomeSum,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: incomeX
            },
            series: [
                {
                    name: '收入来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: data.incomeStatsData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        userChart.setOption(userChartOption);
        courseChart.setOption(courseChartOption);
        anchorChart.setOption(anchorChartOption);
        orderChart.setOption(orderChartOption);
        incomeChart.setOption(incomeChartOption);
    }

</script>
