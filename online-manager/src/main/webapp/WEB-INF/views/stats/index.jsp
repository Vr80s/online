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

        <div class="searchDivClass" id="searchDiv">
            <div class="profile-info-row" >
                <table frame=void >
                    <tr>
                        <td>
                            <div class="profile-info-value searchTr">
                                <input type="text" class="datetime-picker propertyValue1" readonly="readonly" value="${startTime}" id="startTime" name="startTime" placeholder = "开始日期" style="width:130px"/>
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
                                <input type="text" class="datetime-picker propertyValue1" readonly="readonly" value="${endTime}" id="endTime" name="endTime" placeholder = "结束日期" style="width:130px"/>
                                <input type="hidden" value="endTime" class="propertyName"/>
                            </div>
                        </td>
                        <td>
                            <button id="searchBtn" type="button" class="btn btn-sm  btn-primary "
                                    onclick="clickBtn();">
                                <i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
                            </button>

                            <%--<button type="button" class="btn btn-default btn-sm" onclick="showPie()">
                                <span class="glyphicon glyphicon-plus"></span> 饼图
                            </button>
                            <button type="button" class="btn btn-default btn-sm" onclick="showLine()">
                                <span class="glyphicon glyphicon-asterisk"></span> 折线
                            </button>--%>


                        </td>
                    </tr>
                </table>
            </div>
        </div>



</div>
<div></div>
</div>
<div>
    <i class="fa fa-line-chart fa-2x" style="color: #5c7a8c;float:right;width: 30px" onclick="showLine()"></i>
    <i class="fa fa-pie-chart fa-2x" style="color: #A049EE;float:right;width: 30px" onclick="showPie()"></i></div>
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
    <div class="col-xs-12" id="user-chart-line" style="width: 650px;height: 400px;">
    </div>
    <div class="col-xs-12" id="course-chart-line" style="width: 650px;height: 400px;">
    </div>
    <div class="col-xs-12" id="anchor-chart-line" style="width: 650px;height: 400px;">
    </div>
    <div class="col-xs-12" id="order-chart-line" style="width: 650px;height: 400px;">
    </div>
    <div class="col-xs-12" id="income-chart-line" style="width: 650px;height: 400px;">
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

    var userChartLine = echarts.init(document.getElementById('user-chart-line'));
    var courseChartLine = echarts.init(document.getElementById('course-chart-line'));
    var anchorChartLine = echarts.init(document.getElementById('anchor-chart-line'));
    var orderChartLine = echarts.init(document.getElementById('order-chart-line'));
    var incomeChartLine = echarts.init(document.getElementById('income-chart-line'));
    render();
    renderLine();
    $("#user-chart-line").hide();
    $("#course-chart-line").hide();
    $("#anchor-chart-line").hide();
    $("#order-chart-line").hide();
    $("#income-chart-line").hide();


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
            async:false,
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

    function showPie() {
        $("#user-chart").show();
        $("#course-chart").show();
        $("#anchor-chart").show();
        $("#order-chart").show();
        $("#income-chart").show();
        $("#user-chart-line").hide();
        $("#course-chart-line").hide();
        $("#anchor-chart-line").hide();
        $("#order-chart-line").hide();
        $("#income-chart-line").hide();
    }
    function showLine() {
        $("#user-chart").hide();
        $("#course-chart").hide();
        $("#anchor-chart").hide();
        $("#order-chart").hide();
        $("#income-chart").hide();
        $("#user-chart-line").show();
        $("#course-chart-line").show();
        $("#anchor-chart-line").show();
        $("#order-chart-line").show();
        $("#income-chart-line").show();
    }

    //折线图
    function renderLine() {
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
            url: "/stats/dataLine",
            data: {
                "startTime": new Date(startTime),
                "endTime": new Date(endTime)
            },
            async:false,
            success: function (resp) {
                data = resp.resultObject;
            }
        });
        var createTime = [];
        //用户来源数据
        var data1 = [],data2 = [],data3 = [],data4 = [],data5 = [],data6 = [],data7 = [];
        for (var i = 0; i < data.userStatsDataLine[0].length; i++) {
            createTime[i] = data.userStatsDataLine[0][i].createTime;
            data1[i] =  data.userStatsDataLine[0][i].value;
        }
        for (var i = 0; i < data.userStatsDataLine[1].length; i++) {
            data2[i] =  data.userStatsDataLine[1][i].value;
        }
        for (var i = 0; i < data.userStatsDataLine[2].length; i++) {
            data3[i] =  data.userStatsDataLine[2][i].value;
        }
        for (var i = 0; i < data.userStatsDataLine[3].length; i++) {
            data4[i] =  data.userStatsDataLine[3][i].value;
        }
        for (var i = 0; i < data.userStatsDataLine[4].length; i++) {
            data5[i] =  data.userStatsDataLine[4][i].value;
        }
        for (var i = 0; i < data.userStatsDataLine[5].length; i++) {
            data6[i] =  data.userStatsDataLine[5][i].value;
        }
        //课程来源数据
        var courseData1 = [],courseData2 = [],courseData3 = [],courseData4 = [],courseData5 = [],courseData6 = [],courseData7 = [];
        for (var i = 0; i < data.courseStatsDataLine[0].length; i++) {
            courseData1[i] =  data.courseStatsDataLine[0][i].value;
        }
        for (var i = 0; i < data.courseStatsDataLine[1].length; i++) {
            courseData2[i] =  data.courseStatsDataLine[1][i].value;
        }
        for (var i = 0; i < data.courseStatsDataLine[2].length; i++) {
            courseData3[i] =  data.courseStatsDataLine[2][i].value;
        }
        for (var i = 0; i < data.courseStatsDataLine[3].length; i++) {
            courseData4[i] =  data.courseStatsDataLine[3][i].value;
        }
        for (var i = 0; i < data.courseStatsDataLine[4].length; i++) {
            courseData5[i] =  data.courseStatsDataLine[4][i].value;
        }
        for (var i = 0; i < data.courseStatsDataLine[5].length; i++) {
            courseData6[i] =  data.courseStatsDataLine[5][i].value;
        }

        //主播来源数据
        var anchorData1 = [],anchorData2 = [],anchorData3 = [],anchorData4 = [],anchorData5 = [],anchorData6 = [];
        for (var i = 0; i < data.anchorStatsDataLine[0].length; i++) {
            anchorData1[i] =  data.anchorStatsDataLine[0][i].value;
        }
        for (var i = 0; i < data.anchorStatsDataLine[1].length; i++) {
            anchorData2[i] =  data.anchorStatsDataLine[1][i].value;
        }
        for (var i = 0; i < data.anchorStatsDataLine[2].length; i++) {
            anchorData3[i] =  data.anchorStatsDataLine[2][i].value;
        }
        for (var i = 0; i < data.anchorStatsDataLine[3].length; i++) {
            anchorData4[i] =  data.anchorStatsDataLine[3][i].value;
        }
        for (var i = 0; i < data.anchorStatsDataLine[4].length; i++) {
            anchorData5[i] =  data.anchorStatsDataLine[4][i].value;
        }
        for (var i = 0; i < data.anchorStatsDataLine[5].length; i++) {
            anchorData6[i] =  data.anchorStatsDataLine[5][i].value;
        }
        //订单来源数据
        var orderData1 = [],orderData2 = [],orderData3 = [],orderData4 = [],orderData5 = [],orderData6 = [],orderData7 = [],orderData8 = [];
        for (var i = 0; i < data.orderStatsDataLine[0].length; i++) {
            orderData1[i] =  data.orderStatsDataLine[0][i].value;
        }
        for (var i = 0; i < data.orderStatsDataLine[1].length; i++) {
            orderData2[i] =  data.orderStatsDataLine[1][i].value;
        }
        for (var i = 0; i < data.orderStatsDataLine[2].length; i++) {
            orderData3[i] =  data.orderStatsDataLine[2][i].value;
        }
        for (var i = 0; i < data.orderStatsDataLine[3].length; i++) {
            orderData4[i] =  data.orderStatsDataLine[3][i].value;
        }
        for (var i = 0; i < data.orderStatsDataLine[4].length; i++) {
            orderData5[i] =  data.orderStatsDataLine[4][i].value;
        }
        for (var i = 0; i < data.orderStatsDataLine[5].length; i++) {
            orderData6[i] =  data.orderStatsDataLine[5][i].value;
        }
        for (var i = 0; i < data.orderStatsDataLine[6].length; i++) {
            orderData7[i] =  data.orderStatsDataLine[6][i].value;
        }
        for (var i = 0; i < data.orderStatsDataLine[7].length; i++) {
            orderData8[i] =  data.orderStatsDataLine[7][i].value;
        }

        //收入来源数据
        var incomeData1 = [],incomeData2 = [],incomeData3 = [],incomeData4 = [],incomeData5 = [],incomeData6 = [],incomeData7 = [],incomeData8 = [];
        for (var i = 0; i < data.incomeStatsDataLine[0].length; i++) {
            incomeData1[i] =  data.incomeStatsDataLine[0][i].value;
        }
        for (var i = 0; i < data.incomeStatsDataLine[1].length; i++) {
            incomeData2[i] =  data.incomeStatsDataLine[1][i].value;
        }
        for (var i = 0; i < data.incomeStatsDataLine[2].length; i++) {
            incomeData3[i] =  data.incomeStatsDataLine[2][i].value;
        }
        for (var i = 0; i < data.incomeStatsDataLine[3].length; i++) {
            incomeData4[i] =  data.incomeStatsDataLine[3][i].value;
        }
        for (var i = 0; i < data.incomeStatsDataLine[4].length; i++) {
            incomeData5[i] =  data.incomeStatsDataLine[4][i].value;
        }
        for (var i = 0; i < data.incomeStatsDataLine[5].length; i++) {
            incomeData6[i] =  data.incomeStatsDataLine[5][i].value;
        }
        for (var i = 0; i < data.incomeStatsDataLine[6].length; i++) {
            incomeData7[i] =  data.incomeStatsDataLine[6][i].value;
        }
        for (var i = 0; i < data.incomeStatsDataLine[7].length; i++) {
            incomeData8[i] =  data.incomeStatsDataLine[7][i].value;
        }


        userChartOptionLine = {
            title: {
                text: '用户注册来源'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['未知','pc','h5','android','ios','导入']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            //保存图片
            /*toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },*/
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: createTime
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:'未知',
                    type:'line',
                    stack: '总量',
                    data:data1
                },
                {
                    name:'pc',
                    type:'line',
                    stack: '总量',
                    data:data2
                },
                {
                    name:'h5',
                    type:'line',
                    stack: '总量',
                    data:data3
                },
                {
                    name:'android',
                    type:'line',
                    stack: '总量',
                    data:data4
                },
                {
                    name:'ios',
                    type:'line',
                    stack: '总量',
                    data:data5
                },
                {
                    name:'导入',
                    type:'line',
                    stack: '总量',
                    data:data6
                }
            ]
        };

        courseChartOptionLine = {
            title: {
                text: '课程来源'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['未知','pc','h5','android','ios','其他']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: createTime
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:'未知',
                    type:'line',
                    stack: '总量',
                    data:courseData1
                },
                {
                    name:'pc',
                    type:'line',
                    stack: '总量',
                    data:courseData2
                },
                {
                    name:'h5',
                    type:'line',
                    stack: '总量',
                    data:courseData3
                },
                {
                    name:'android',
                    type:'line',
                    stack: '总量',
                    data:courseData4
                },
                {
                    name:'ios',
                    type:'line',
                    stack: '总量',
                    data:courseData5
                },
                {
                    name:'其他',
                    type:'line',
                    stack: '总量',
                    data:courseData6
                }
            ]
        };
        anchorChartOptionLine = {
            title: {
                text: '主播来源'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['未知','pc','h5','android','ios','其他']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: createTime
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:'未知',
                    type:'line',
                    stack: '总量',
                    data:anchorData1
                },
                {
                    name:'pc',
                    type:'line',
                    stack: '总量',
                    data:anchorData2
                },
                {
                    name:'h5',
                    type:'line',
                    stack: '总量',
                    data:anchorData3
                },
                {
                    name:'android',
                    type:'line',
                    stack: '总量',
                    data:anchorData4
                },
                {
                    name:'ios',
                    type:'line',
                    stack: '总量',
                    data:anchorData5
                },
                {
                    name:'其他',
                    type:'line',
                    stack: '总量',
                    data:anchorData6
                }
            ]
        };
        orderChartOptionLine = {
            title: {
                text: '订单来源'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['其他','赠送','pc','h5','android','ios','线下','工作人员']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: createTime
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:'其他',
                    type:'line',
                    stack: '总量',
                    data:orderData1
                },
                {
                    name:'赠送',
                    type:'line',
                    stack: '总量',
                    data:orderData2
                },
                {
                    name:'pc',
                    type:'line',
                    stack: '总量',
                    data:orderData3
                },
                {
                    name:'h5',
                    type:'line',
                    stack: '总量',
                    data:orderData4
                },
                {
                    name:'android',
                    type:'line',
                    stack: '总量',
                    data:orderData5
                },
                {
                    name:'ios',
                    type:'line',
                    stack: '总量',
                    data:orderData6
                },
                {
                    name:'导入',
                    type:'line',
                    stack: '总量',
                    data:orderData7
                },
                {
                    name:'工作人员',
                    type:'line',
                    stack: '总量',
                    data:orderData8
                }
            ]
        };
        incomeChartOptionLine = {
            title: {
                text: '收入来源'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['其他','赠送','pc','h5','android','ios','线下','工作人员']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: createTime
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name:'其他',
                    type:'line',
                    stack: '总量',
                    data:incomeData1
                },
                {
                    name:'赠送',
                    type:'line',
                    stack: '总量',
                    data:incomeData2
                },
                {
                    name:'pc',
                    type:'line',
                    stack: '总量',
                    data:incomeData3
                },
                {
                    name:'h5',
                    type:'line',
                    stack: '总量',
                    data:incomeData4
                },
                {
                    name:'android',
                    type:'line',
                    stack: '总量',
                    data:incomeData5
                },
                {
                    name:'ios',
                    type:'line',
                    stack: '总量',
                    data:incomeData6
                },
                {
                    name:'导入',
                    type:'line',
                    stack: '总量',
                    data:orderData7
                },
                {
                    name:'工作人员',
                    type:'line',
                    stack: '总量',
                    data:orderData8
                }
            ]
        };

        userChartLine.setOption(userChartOptionLine);
        courseChartLine.setOption(courseChartOptionLine);
        anchorChartLine.setOption(anchorChartOptionLine);
        orderChartLine.setOption(orderChartOptionLine);
        incomeChartLine.setOption(incomeChartOptionLine);
    }

    function clickBtn() {
        render();
        renderLine();
    }

</script>
