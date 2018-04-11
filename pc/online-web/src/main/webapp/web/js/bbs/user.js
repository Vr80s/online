$(function () {
    $('.bbs-tab').addClass("select");
    var list = {'page': 1};
    var postTpl = "{{each posts}}" +
        "                    <tr class='mytable' style='border-bottom: 1px solid #dddddd;'>" +
        "                            <td class='mytable'><a class='postsDetails' href='/bbs/{{$value.id}}'>{{$value.title}}</a></td>" +
        "                            <td style='width: 20%;'>{{$value.labelName}}</td>" +
        "                            <td>{{$value.initTime}}</td>" +
        "                    </tr>" +
        "                {{/each}}";

    var replyTpl = "{{each replies}}" +
        "                <tr class='mytable'>" +
        "                        <td><a class='postsDetails' href='/bbs/{{$value.postId}}'>{{$value.postTitle}}</a></td>" +
        "                        <td style='width: 20%;'>{{$value.labelName}}</td>" +
        "                        <td>{{$value.initTime}}</td>" +
        "                    </tr>" +
        "                    <tr class='mytable02'>" +
        "                        <td colspan='3'>{{$value.content}}</td>" +
        "                    </tr>" +
        "            {{/each}}";

    function post(list) {
        RequestService("/bbs/myPosts", 'GET', list, function (data) {
            $("#post_table").html(template.compile(postTpl)({
                posts: data.resultObject.records
            }));
            if (data.resultObject.total > 1) {
                if (data.resultObject.current === 1) {
                    $("#Pagination").pagination(data.resultObject.pages, {
                        callback: function (page) { //翻页功能
                            list.page = (page + 1);
                            post(list);
                        }
                    });
                }
            }
        });
    }

    function reply(list) {
        RequestService("/bbs/myReplies", 'GET', list, function (data) {
            var replyTemplate = template.compile(replyTpl, {'escape' : false})({
                replies: data.resultObject.records
            });
            $("#reply_table").html(replyTemplate);
            if (data.resultObject.total > 1) {
                if (data.resultObject.current === 1) {
                    $("#Pagination").pagination(data.resultObject.pages, {
                        callback: function (page) { //翻页功能
                            list.page = (page + 1);
                            reply(list);
                        }
                    });
                }
            }
        });
    }

    post(list);

    $('.J-post').on('click', function () {
        list.page = 1;
        post(list);
        $('.J-reply').removeClass('post-active');
        $(this).addClass('post-active');
        $('.content-post').removeClass('contro');
        $('.content-reply').addClass('contro');
    });

    $('.J-reply').on('click', function () {
        list.page = 1;
        reply(list);
        $('.J-post').removeClass('post-active');
        $(this).addClass('post-active');
        $('.content-post').addClass('contro');
        $('.content-reply').removeClass('contro');
    })
});