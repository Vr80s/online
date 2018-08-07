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
<div class="background-ask hide"></div>
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
            <li class="doc-title hover-delect" data-did="${document.documentId}">
                <div class="doc-name doc-photo">${document.documentName}</div>
                <div class="doc-time text-center">${document.createTime?string('yyyy-MM-dd HH:mm:ss')}</div>
                <#if document.transStatus?? && document.transStatus == 1>
                    <div class="doc-progress text-center J-doc-item-text-${document.documentId}">转化成功</div>
                    <div class="doc-operation text-center J-doc-operation J-operation-${document.documentId}">
                        <p>演示</p>
                    </div>
                </#if>
                <#if document.transStatus?? && document.transStatus == 2>
                    <div class="doc-progress text-center J-doc-item-text-${document.documentId}">转换失败</div>
                </#if>
                <#if document.transStatus == 0>
                    <div class="doc-progress text-center J-doc-item-text-${document.documentId}">转换中</div>
                </#if>
                <div class="delect-img hide J-doc-delete"></div>
            </li>
        </#list>
        </ul>
        <div class="null-document hide">
            <img src="/web/images/live-room/null-document.png" alt="无文件"/>
            <p>您还没有上传文档，快来上传吧~</p>
        </div>
    </div>
    <button type="button" class="document-upload" onclick="$('#file-input').click()">上传</button>

    <form action="/vhallyun/documentId" method="post" id="submitFile">
        <input type="file" id="file-input" name="document"
               style="visibility: hidden;display: inline-block;width: 2px;position: fixed;left: -1000px;"/>
    </form>
</div>
<!--文档弹窗结束-->
<div class="container-fluid" style="height: 100%;">
    <div class="row" style="height: 100%;">
        <!--视频区-->
        <div class="col-md-8 comment-padding" style="height: 100%;">
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
                            <li><img src="/web/images/video2/weixin.png"/></li>
                            <li><img src="/web/images/video2/quan.png"/></li>
                            <li><img src="/web/images/video2/weibo.png"/></li>
                            <li><img src="/web/images/video2/Qq.png"/></li>
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
                                <p class="comment-bg comment-color other-one" data-color="#ff0000">
                                    <img src="/web/images/live-room/color1.png"/>
                                </p>
                                <p class="comment-bg comment-color other-two" data-color="#00ff00">
                                    <img src="/web/images/live-room/color6.png"/>
                                </p>
                                <p class="comment-bg comment-color other-three" data-color="#0000ff">
                                    <img src="/web/images/live-room/color4.png"/>
                                </p>
                                <p class="comment-bg comment-color other-four" data-color="#000000">
                                    <img src="/web/images/live-room/color3.png"/>
                                </p>
                                <p class="comment-bg comment-color other-five" data-color="#000000">
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
                <!--视频模块-->
                <div class="video-main" id="J_doc_main">

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
                        <li>
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
                <div style="height: 232px">
                    <div class="video-wrap" id="J_video_main">
                    </div>
                </div>
                <!--刷新学员-->
                <div class="refresh-student text-center">
                    <span>学员列表</span>
                    <img src="/web/images/refresh.png" alt="刷新"/>
                </div>
                <!--学员列表-->
                <div class="student-list">
                    <ul>
                        <li>
                            <div class="head-portrait z">
                                <img src="/web/images/reset_head.png" alt="头像"/>
                            </div>
                            <span class="student-name z">我是超人</span>
                            <span class="student-status z">主播</span>
                        </li>
                        <li>
                            <div class="head-portrait z">
                                <img src="/web/images/reset_head.png" alt="头像"/>
                            </div>
                            <span class="student-name z">我是超人</span>
                        </li>
                        <li>
                            <div class="head-portrait z">
                                <img src="/web/images/reset_head.png" alt="头像"/>
                            </div>
                            <span class="student-name z">我是超人</span>
                        </li>
                        <li>
                            <div class="head-portrait z">
                                <img src="/web/images/reset_head.png" alt="头像"/>
                            </div>
                            <span class="student-name z">我是超人</span>
                        </li>
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
                        <!--普通聊天-->
                        <li>
                            <span class="chat-name">明天会更好:</span>
                            <span class="chat-content">这是评这是评论的内容这是评论的内容论的内容</span>
                        </li>
                        <#--<!--主播聊天&ndash;&gt;-->
                        <#--<li>-->
                            <#--<span class="chat-status">主播</span>-->
                            <#--<span class="chat-name">霸位:</span>-->
                            <#--<span class="chat-content">回家多学习</span>-->
                        <#--</li>-->
                        <#--<!--自己发言&ndash;&gt;-->
                        <#--<li>-->
                            <#--<span class="chat-oneselft">霸位:</span>-->
                            <#--<span class="chat-content">大家好</span>-->
                        <#--</li>-->
                        <#--<!--进入直播间&ndash;&gt;-->
                        <#--<li>-->
                            <#--<span class="chat-name">霸位:</span>-->
                            <#--<span class="chat-content">进入直播间</span>-->
                        <#--</li>-->
                        <#--<!--赠送礼物&ndash;&gt;-->
                        <#--<li>-->
                            <#--<span class="chat-name">霸位:</span>-->
                            <#--<span class="chat-content">送给主播</span>-->
                            <#--<span class="chat-gift">一个锦旗</span>-->
                        <#--</li>-->

                    </ul>
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
</body>
</html>
