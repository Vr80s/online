<div class="footerDT">
    <footer>
        <div class="content">
            <div class="content-item footer-bodys">
                <div class="content-item content-footer-link about-us">
                    <ul class="gate">
                        <li data-id="first" data-url="/web/html/aboutUs.html">关于我们<span>|</span></li>
                        <li data-id="three" data-url="/web/html/aboutUs.html">联系我们<span>|</span></li>
                        <li data-id="four" data-url="/web/html/aboutUs.html" class="noline">常见问题<span>|</span></li>
                        <li data-id="five" data-url="/copyright.html" class="noline">版权声明</li>
                    </ul>
                </div>
                <div class="trademark"><a href="http://www.miibeian.gov.cn/" target="_blank" class="ml14" style="color: white;">琼ICP备17002789号-2 </a>Copyright &copy; 2017-2018 熊猫中医 All Rights Reserved<span style="margin-right:5px;"></span><iframe src="http://aic.hainan.gov.cn:880/lz.ashx?vie=076144A08548ACE3DB843B1F0B84B9CF453FD165F150EE1CEAB6C9AC6FC81FDE8391B314E13A8C70D0B220C7176DE164" allowtransparency="true" scrolling="no" style="overflow:hidden;width:94px;height:32px;margin-right:10px;position: absolute;top: -13px;" frameborder="0"></iframe></div>
            </div>
        </div>
    </footer>
</div>

<script type="text/javascript">
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?7d139bd69b722f04598cb7d0ed34b77b";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
    //左侧导航栏点击添加缓存
$(".gate li").click(function () {
    var dataId = $(this).attr("data-id");
    window.sessionStorage.aboutus = $(this).attr("data-id");
    top.document.location.href = $(this).attr("data-url");
});
</script>