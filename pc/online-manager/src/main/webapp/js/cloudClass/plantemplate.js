var updateForm;
var courseId;
var totalDay
$(function() {
    totalDay = $("#totalDay").text();
    courseId = $("#courseId").val();
    dualPlanInfo(totalDay);//拼接学习计划模板页面内容

    $.validator.addMethod("checkDay",function(value,element){
        var totalDay_update = parseInt($("#totalDay_update").val());
        var oldDay_update = parseInt($("#oldDay_update").val());
        if(totalDay_update <= oldDay_update){
            return false;
        }else{
            return true;
        }
    },"<font color='red'>修改的授课天数只能大于当前天数！</font>");
    updateForm = $("#update-form").validate({
        messages: {
            totalDay : {
                required : "请填写学习计划模板天数"
            }
        }
    });
});

/**
 * 修改授课天数
 */
function editTotalDay() {
    var param = {"courseId":courseId};
    ajaxRequest(basePath+'/studyPlan/ifExistPlan',param,function(res) {
        if(res.resultObject) {
            // alertInfo("当前学习计划已被生成，不可修改！")
            //学习计划已生成，修改的授课天数只能大于当前天数！
            $("#totalDay_update").addClass("checkDay");
        }else{
            $("#totalDay_update").removeClass("checkDay");
        }
        updateForm.resetForm();
        openDialog("updateDialog", "updateGradeDiv", "修改", 350, 200, true, "确定", function () {

            if ($("#update-form").valid()) {
                mask();
                $("#update-form").attr("action", basePath + "/studyPlan/editStudyDay");
                $("#update-form").ajaxSubmit(function (data) {
                    unmask();
                    if (data.success) {
                        $("#updateDialog").dialog("close");
                        $("#totalDay").text(data.resultObject);
                        $("#oldDay_update").val(data.resultObject);
                        dualPlanInfo(data.resultObject);
                    } else {
                        layer.msg(data.errorMessage);
                    }
                });
            }
        });
    });
}

/**
 * 返回到课程管理页面
 */
function goBack() {
    window.location.href=basePath+'/home#cloudclass/course/index';
}

/**
 * 根据周查询学习计划详情
 * @param week 第几周
 */
function planInfo(week) {
    var param = {"week":week,"courseId":courseId};
    ajaxRequest(basePath+'/studyPlan/planInfo',param,function(res) {
        if(res.success){
            $("#planInfoBody").html("");
            console.log("success");
            var planList = res.resultObject;
            $.each(planList,function(index,obj) {
                var planInfoBody = "";
                var kPoints = obj.kPoints;
                if (kPoints.length < 1) {
                    planInfoBody += '<tr class="tr_' + index + '">';
                    planInfoBody += '<td></td>';
                    planInfoBody += '<td></td>';
                    planInfoBody += '</tr>';
                } else{
                    for (var i = 0; i < kPoints.length; i++) {
                        planInfoBody += '<tr class="tr_' + index + '">';
                        planInfoBody += '<td>' + kPoints[i].name + '</td>';
                        planInfoBody += '<td>' + kPoints[i].videoTime + '</td>';
                        planInfoBody += '</tr>';
                    }
                }
                $("#planInfoBody").append(planInfoBody);
                $(".tr_"+index).eq(0).prepend('<td rowspan="'+kPoints.length+'">第'+obj.day+'天</td>');
                $(".tr_"+index).eq(0).append('<td rowspan="'+kPoints.length+'"><a href="javascript:void(0);" class="blue" onclick="configKnowledgePoint(\''+obj.id+'\','+week+');">配置知识点</a></td>');
            });
        }
    });
}
//知识点配置
var Tree1;
function configKnowledgePoint(templateId,week) {
    console.log(week);
    var param = {"courseId":courseId};
    ajaxRequest(basePath+'/studyPlan/ifHasResource',param,function(res) {
        if (res.resultObject) {
            //知识点树
            loadTreeAll("resource", "studyPlan/getChapterTree?courseId=" + courseId + '&templateId=' + templateId);
            openDialog("configDialog", "dialogConfigDiv", "配置知识点", 400, 530, true, "保存设置", function () {
                var url = "studyPlan/savePlanKnowledge";
                var data = {"templateId": templateId, "knowledgeIds": getSelectNodes(Tree1)};
                ajaxRequest(url, data, function (result) {
                    if (result.success) {
                        $("#configDialog").dialog("close");
                        alertMsg(result.resultObject);
                        $("#weekList li").eq(week-1).click();
                    } else {
                        alertInfo(result.errorMessage);
                    }
                });
            });
        } else {
            alertInfo("当前课程暂未添加知识点资源，请完善知识点资源再进行配置！")
        }
    });
}
function loadTreeAll(objId,url,data) {
    ajaxRequest(url, data, function (zNodes) {
        Tree1 = loadTree(objId, zNodes);//初始化选择知识点
        //遍历所有的0、1级节点 如果不可以用 全部禁用
        var nodesAll = Tree1.transformToArray(Tree1.getNodes());

        for (var i = 0; i < nodesAll.length; i++) {
            var tempNode = nodesAll[i];
            if (tempNode.level == 1) {
                //查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
                var tempNodes = Tree1.getNodesByParam("chkDisabled", false, tempNode);
                if (tempNodes.length == 0) {
                    Tree1.setChkDisabled(nodesAll[i], true);
                }
            }
        }

        nodesAll = Tree1.transformToArray(Tree1.getNodes());
        for (var i = 0; i < nodesAll.length; i++) {
            var tempNode = nodesAll[i];
            if (tempNode.level == 0) {
                //查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
                var tempNodes = Tree1.getNodesByParam("chkDisabled", false, tempNode);
                if (tempNodes.length == 0) {
                    Tree1.setChkDisabled(nodesAll[i], true);
                }
            }
        }
    });
}
function loadTree(objId,zNodes,onClickTree){
    var setting = {
        view: {
            selectedMulti: true,
            expandSpeed: "normal"
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: function(event, treeId, treeNode) {
                if(!isnull(onClickTree)){
                    onClickTree(treeNode);
                }
            }
        }
    };
    return $.fn.zTree.init($("#"+objId), setting, zNodes);
}
/**
 * 简单的选择树
 * @param objId 对象的ID
 * @param url 请求的后台地址
 * @param callback 成功后的回调方法
 * @param onClickTree 左键点击方法
 * @returns
 */
function loadSimpleTree(objId,url,callback,onClickTree){
    var setting = {
        view: {
            selectedMulti: true,
            expandSpeed: "normal"
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "s" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        async: {
            enable: true,
            url:url,
            autoParam:["id"]
        },
        callback: {
            onClick: function(event, treeId, treeNode) {
                if(!isnull(onClickTree)){
                    onClickTree(treeNode);
                }
            }
        }
    };
    return $.fn.zTree.init($("#"+objId), setting);
}
/**
 * 获取选择的节点树
 */
function getSelectNodes(obj){
    var objIds = new Array();
    var nodes = obj.getCheckedNodes();
    if(nodes.length>0){
        for(var i=0;i<nodes.length;i++){
            var id = nodes[i].id;
            objIds.push(id);
        }
    }
    return objIds.join(",");
}

/**
 * 拼接学习计划页面信息
 * @param totalDay
 */
function dualPlanInfo(totalDay){
    $("#weekList").html("");
    var totalWeek;
    if(totalDay%7==0){
        totalWeek=parseInt(totalDay/7);
    }else{
        totalWeek=parseInt(totalDay/7)+1
    }
    var lis = "";
    for(var i=1;i<totalWeek+1;i++){
        if(i==1){
            lis += '<li id="'+i+'" class="cli"><a href="#"><span>第'+i+'周</span></a></li>';
        }else{
            lis += '<li id="'+i+'"><a href="#"><span>第'+i+'周</span></a></li>';
        }
    }
    $("#weekList").append(lis);

    //切换周展示学习计划详情
    $("#weekList li").on("click",function(){
        $(this).siblings().each(function () {
            $(this).removeClass("cli")
        });
        $(this).addClass("cli");
        var week = $(this).attr("id");
        planInfo(week);
    });
    $("#weekList li").eq(0).click();
}

