<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/web/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/web/css/live-room/live-room.css"/>
</head>
<body>
<input type="hidden" id="J_roomId" value="${roomId}"/>
<input type="hidden" id="J_channelId" value="${channelId}"/>
<input type="hidden" id="J_curDocId" value=""/>
<input type="hidden" id="J_accountId" value="${accountId}"/>
<input type="hidden" id="J_appId" value="${appId}"/>
<input type="hidden" id="J_token" value="${token}"/>
<input type="hidden" id="J_nickname" value="${anchor.name}"/>
<input type="hidden" id="J_headImg" value="${anchor.smallHeadPhoto}"/>
<script>
    var courseId = "${courseId}";
    var collectionId = "";
    var courseName = "${courseName}";
    var	smallImgPath = "${courseImg}";
    var	description = "";
</script>

<div class="background-ask hide"></div>
<!--设备提示-->
<div class="noll-equipment hide">
    <p class="equipment-title">设备提示</p>
    <p class="equipment-content">未检测到直播设备，请连接摄像头或麦克风后开播</p>
    <p class="equipment-close">我知道了</p>
</div>
<!--文档弹窗-->
<div class="modal-content modal-document hide">
    <div class="modal-top">
        <p>文档管理</p>
        <img src="/web/images/collection-close.png" class="J-close-doc" alt="关闭"/>
    </div>
    <div class="document-file">
        <ul class="file-list">
            <li class="doc-title J-doc-title">
                <div class="doc-name text-center">文档名称</div>
                <div class="doc-time text-center">上传日期</div>
                <div class="doc-progress text-center">进度</div>
                <div class="doc-operation text-center">操作</div>
            </li>
            <!--图片-->
        <#list documents as document>
            <li class="doc-title hover-delect" data-did="${document.documentId}" data-page="${document.page!'1'}">
                <div class="doc-name doc-photo">${document.documentName}</div>
                <div class="doc-time text-center">${document.createTime?string('yyyy-MM-dd HH:mm:ss')}</div>
                <#if document.transStatus?? && document.transStatus == 1>
                    <div class="doc-progress text-center J-doc-item-text-${document.documentId}">转换成功</div>
                    <div class="doc-operation text-center J-doc-operation J-operation-${document.documentId}">
                        <p>演示</p>
                    </div>
                </#if>
                <#if document.transStatus?? && document.transStatus == 2>
                    <div class="doc-progress text-center J-doc-item-text-${document.documentId}">转换失败</div>
                </#if>
                <#if document.transStatus == 0>
                    <div class="doc-progress text-center J-doc-item-text-${document.documentId}">等待转换</div>
                </#if>
                <div class="delect-img hide J-doc-delete"></div>
            </li>
        </#list>
        </ul>
        <div class="null-document <#if (documents?size gt 0)>hide</#if>">
            <img src="/web/images/live-room/null-document.png" alt="无文件"/>
            <p>您还没有上传文档，快来上传吧~</p>
        </div>
    </div>
    <button type="button" class="document-upload">上传</button>

    <form action="/vhallyun/documentId" method="post" id="submitFile">
        <input type="file" id="file-input" name="document" enctype='multipart/form-data'
               style="visibility: hidden;display: inline-block;width: 2px;position: fixed;left: -1000px;" accept=".xls,.doc,.txt,.pdf,.png, .jpeg, .gif, .jpg,.pptx,.xlsx,.docx"/>
    </form>
</div>
<!--文档弹窗结束-->
<!--设置弹窗-->
<div class="modal-content setup-modal hide">
    <div class="comment-setup">
        <span>摄像头</span>
        <select name="" class="J-cameras">
        </select>
    </div>
    <div class="comment-setup">
        <span>麦克风</span>
        <select name="" class="J-mics">
        </select>
    </div>
    <#--<div class="comment-setup">-->
        <#--<span>自定义尺寸:</span>-->
        <#--<span>宽</span>-->
        <#--<input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="width-my J-setup-width" value="800" maxlength="4" />-->
        <#--<span>x高</span>-->
        <#--<input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="height-my J-setup-height" value="450" maxlength="4" />-->
        <#--<p class="control-size hide" style="color: red; margin-left: 78px;"></p>-->
    <#--</div>-->
    <div class="comment-setup">
        <button class="sure" type="button" id="J_confirm_edit">确认</button>
        <button class="cancel" type="button">取消</button>
    </div>
</div>
<!--设置结束-->
<div class="container-fluid" style="height: 100%;">
    <div class="row" style="height: 100%;">
        <!--视频区-->
        <div class="col-md-8 comment-padding video-width" style="height: 100%;">
            <div class="content-video">
                <!--分享模块-->
                <div class="share-main =">
                    <div class="select-document text-center">
                        <ul>
                            <li class="document-modal">
                                <img src="/web/images/document-page.png"/>
                                <p>文档</p>
                            </li>
                        </ul>
                    </div>
                    <div class="share-icon text-center">
                        <p>分享到</p>
                        <ul>
                            <li class="share-weixin">
                                <img src="/web/images/video2/weixin.png"/>
                                <div class="weixin-wrap">
                                    <div id="qrcodeCanvas1"></div>
                                </div>
                                <div class="slider-sanjiao">
                                    <img src="/web/images/float_sanjiao.png"/>
                                </div>
                            </li>
                            <li class="share-weixin">
                                <img src="/web/images/video2/quan.png"/>
                                <div class="weixin-wrap">
                                    <div id="qrcodeCanvas2"></div>
                                </div>
                                <div class="slider-sanjiao">
                                    <img src="/web/images/float_sanjiao.png"/>
                                </div>
                            </li>
                            <li id="weibo_share"><img src="/web/images/video2/weibo.png"/>
                                <a href="javascript:void(0)"
                                    target="_blank"></a></li>
                            <li id="qq_share"><img src="/web/images/video2/Qq.png"/>
                                <a href="javascript:void(0)"
                                   id="qq_share" target="_blank"></a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="document-main text-center">
                    <p>文档区</p>
                </div>
                <!--工具模块-->
                <div class="tool-main">
                    <ul class="select-tool">
                        <!--画笔-->
                        <li>
                            <div class="show-img">
                                <img src="/web/images/live-room/huabi.png" alt="画笔"/>
                                <img class="select-img" src="/web/images/live-room/xiaosanjiao.png" alt="下拉选择"/>
                            </div>
                            <div class="tip-tool hide">
                                <span>画笔</span>
                            </div>
                            <div class="select-huabi hide">
                                <p class="comment-bg other-one" data-size="24">
                                    <span></span>
                                </p>
                                <p class="comment-bg other-two" data-size="20">
                                    <span></span>
                                </p>
                                <p class="comment-bg other-three" data-size="16">
                                    <span></span>
                                </p>
                                <p class="comment-bg other-four" data-size="12">
                                    <span></span>
                                </p>
                                <p class="comment-bg other-five" data-size="8">
                                    <span></span>
                                </p>
                                <p class="comment-bg other-six" data-size="4">
                                    <span></span>
                                </p>
                            </div>

                        </li>
                        <!--颜色-->
                        <li class="remove-color">
                            <div class="show-img">
                                <img class="sure-color" src="/web/images/live-room/color5.png" alt="颜色"/>
                                <img class="select-img" src="/web/images/live-room/xiaosanjiao.png" alt="下拉选择"/>
                            </div>
                            <div class="tip-tool hide">
                                <span>颜色</span>
                            </div>
                            <div class="select-huabi hide">
                                <p class="comment-bg comment-color other-one" data-color="#d900ae">
                                    <img src="/web/images/live-room/color1.png"/>
                                </p>
                                <p class="comment-bg comment-color other-two" data-color="#0063db">
                                    <img src="/web/images/live-room/color6.png"/>
                                </p>
                                <p class="comment-bg comment-color other-three" data-color="#00c900">
                                    <img src="/web/images/live-room/color4.png"/>
                                </p>
                                <p class="comment-bg comment-color other-four" data-color="#ffc800">
                                    <img src="/web/images/live-room/color3.png"/>
                                </p>
                                <p class="comment-bg comment-color other-five" data-color="#ff0000">
                                    <img src="/web/images/live-room/color2.png"/>
                                </p>
                                <p class="comment-bg comment-color other-six" data-color="#000000">
                                    <img src="/web/images/live-room/color5.png"/>
                                </p>
                            </div>

                        </li>
                        <!--橡皮擦-->
                        <li>
                            <div class="show-img J-eraser">
                                <img src="/web/images/live-room/xiangpi.png" alt="橡皮擦"/>
                            </div>
                            <div class="tip-tool hide">
                                <span>橡皮擦</span>
                            </div>
                        </li>
                        <!--清空-->
                        <li>
                            <div class="show-img J-clear">
                                <img src="/web/images/live-room/qingkong.png" alt="清空"/>
                            </div>
                            <div class="tip-tool hide">
                                <span>清空</span>
                            </div>
                        </li>
                    </ul>
                </div>
                <!--文档模块-->
                <div class="video-main">
                    <div id="J_doc_main" class="document-content"></div>
                    <div class="select-document-wrap" style="width: 100px">

                        <div class="modal-list">
                            <ul class="J-thumImg">
                            </ul>
                        </div>
                        <div class="icon-right J-icon-right"></div>
                    </div>
                    <div class="icon-left hide"></div>
                    <!--pages分页-->
                    <div class="pages hide">
                        <p class="jump-pages">
                            <span class="prev J-doc-prev">
                                <img src="/web/images/live-room/left.png" alt="上一页"/>
                            </span>
                            <span class="now-page">1</span>
                            <span class="line">/</span>
                            <span class="all-pages">1</span>
                            <span class="next J-doc-next">
                                <img src="/web/images/live-room/right.png" alt="下一页"/>
                            </span>
                        </p>
                    </div>
                </div>
                <!--开始播放按钮模块-->
                <div class="play-main text-center">
                    <ul class="select-operation text-center">
                        <li><p class="play-time">00:00</p></li>
                        <li>
                            <button class="start-play" type="button" id="J_play" data-status="0">开始直播</button>
                        </li>
                    <#--<li>-->
                    <#--<img src="/web/images/video-set.png" id="J_stop_play"/>-->
                    <#--</li>-->
                        <li class="set-up">
                            <img src="/web/images/video-set2.png"/>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!--学员区-->
        <div class="col-md-2 comment-padding" style="height: 100%;">
            <div class="content-student">
                <!--存放视频-->
                <div style="height: 232px;background-color: #0C0000">
                    <div class="video-wrap" id="J_video_main">
                    </div>
                </div>
                <!--刷新学员-->
                <div class="refresh-student text-center">
                    <span>成员列表<span class="J-person-num"></span></span>
                    <img src="/web/images/refresh.png" alt="刷新" class="J-refresh-list"/>
                </div>
                <!--学员列表-->
                <div class="student-list">
                    <ul>
                        <li>
                            <div class="head-portrait z">
                                <img src="${anchor.smallHeadPhoto!''}" alt="头像"/>
                            </div>
                            <span class="student-name z">${anchor.name!''}</span>
                            <span class="student-status z">主播</span>
                        </li>
                        <#--<li>-->
                            <#--<div class="head-portrait z">-->
                                <#--<img src="/web/images/reset_head.png" alt="头像"/>-->
                            <#--</div>-->
                            <#--<span class="student-name z">我是超人</span>-->
                            <#--<span class="select-ban y">-->
                                <#--<img src="/web/images/live-room/say-icon.png" alt="选择禁言" title="禁言"/>-->
                            <#--</span>-->
                        <#--</li>-->
                        <#--<li>-->
                            <#--<div class="head-portrait z">-->
                                <#--<img src="/web/images/reset_head.png" alt="头像"/>-->
                            <#--</div>-->
                            <#--<span class="student-name z">我是超人</span>-->
                            <#--<span class="select-ban y">-->
                                <#--<img src="/web/images/live-room/say-icon.png" alt="选择禁言" title="禁言"/>-->
                            <#--</span>-->
                        <#--</li>-->
                        <#--<li>-->
                            <#--<div class="head-portrait z">-->
                                <#--<img src="/web/images/reset_head.png" alt="头像"/>-->
                            <#--</div>-->
                            <#--<span class="student-name z">我是超人</span>-->
                        <#--</li>-->
                    </ul>

                </div>
            </div>
        </div>
        <!--聊天区-->
        <div class="col-md-2 comment-padding" style="height: 100%;">
            <div class="content-chat">
                <div class="chat-top">
                    <span>聊天区域</span>
                </div>
                <!--聊天区-->
                <div class="chat-personal">
                    <ul id="J_message_list">
                    </ul>
                    <!--表情筛选-->
                    <div class="expression-select hide z">
                        <ul class="expression-list">

                        </ul>
                    </div>
                </div>
                <!--发布弹幕区域-->
                <div class="barrage-up">
                    <div class="expression-limit">
                        <!--表情-->
                        <div class="expression-img z">
                            <img src="/web/images/live-room/img-face.png" alt="表情包"/>
                        </div>
                        <!--输入框-->
                        <div class="input-main z">
                            <input type="text" value="" placeholder="来点儿神聊..." id="J_message_text"/>
                            <button type="button" id="J_message_send">发送</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="/web/js/jquery-1.12.1.js"></script>
<script src="/web/js/jquery.form.min.js"></script>
<script src="/web/js/bootstrap.min.js"></script>
<script src="https://static.vhallyun.com/jssdk/vhall-jssdk-doc-1.0.0.js?v=201806271657"></script>
<script src="https://static.vhallyun.com/jssdk/vhall-jssdk-live-stream-1.0.0.js?v=201806271657"></script>
<script src="https://static.vhallyun.com/jssdk/vhall-jssdk-chat-1.0.0.js?v=201806271657"></script>
<script src="https://static.vhallyun.com/jssdk/vhall-jssdk-base-1.0.0.js?v=201806271657"></script>
<script src="/web/js/live-room/live-room.js"></script>
<script src="/web/js/common_msg.js"></script>
<script src="/web/js/utf.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/jquery.qrcode.js" type="text/javascript" charset="utf-8"></script>
<script src="/web/js/common/video_share.js"></script>
</body>
</html>
