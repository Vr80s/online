$(function () {

    $('.bbs-tab').addClass("select");
    var list = 1;
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
				//分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".post_pages").removeClass("hide");
		            $(".post_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#Pagination_post").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:list-1,
		                callback: function (page) {
		                    //翻页功能
                            post(page + 1);
		                }
		            });
				}
				else {
					$(".post_pages").addClass("hide");
				}
        });
    }

    function reply(list) {
        RequestService("/bbs/myReplies", 'GET', list, function (data) {
            var replyTemplate = template.compile(replyTpl, {'escape' : false})({
                replies: data.resultObject.records
            });
            $("#reply_table").html(replyTemplate);
		//分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".reply_pages").removeClass("hide");
		            $(".reply_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#Pagination_reply").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:list-1,
		                callback: function (page) {
		                    //翻页功能
                            reply(page + 1);
		                }
		            });
				}
				else {
					$(".reply_pages").addClass("hide");
				}

        });
    }

    post(list);

    $('.J-post').on('click', function () {
//      list.page = 1;
        post(list);
        $('.J-reply').removeClass('post-active');
        $(this).addClass('post-active');
        $('.content-post').removeClass('contro');
        $('.content-reply').addClass('contro');
    });

    $('.J-reply').on('click', function () {
//      list.page = 1;
        reply(list);
        $('.J-post').removeClass('post-active');
        $(this).addClass('post-active');
        $('.content-post').addClass('contro');
        $('.content-reply').removeClass('contro');
    })
});