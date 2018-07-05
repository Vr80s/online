/**
 * 
 */
var i = {assign:"#mywords"};
var o = "facebox"
var s = "//cnstatic01.e.vhall.com/static/img/arclist/";	

var c = ["[微笑]", "[撇嘴]", "[色]", "[发呆]", "[得意]", "[流泪]", "[害羞]", "[闭嘴]", 
	"[睡]", "[哭]", "[尴尬]", "[发怒]", "[调皮]", "[呲牙]", "[惊讶]", "[难过]", 
	"[酷]", "[汗]", "[抓狂]", "[吐]", "[偷笑]", "[愉快]", "[白眼]", "[傲慢]", 
	"[饥饿]", "[困]", "[惊恐]", "[流汗]", "[憨笑]", "[悠闲]", "[奋斗]", "[咒骂]",
	"[疑问]", "[嘘]", "[晕]", "[疯了]", "[衰]", "[骷髅]", "[敲打]", "[再见]", 
	"[擦汗]", "[抠鼻]", "[鼓掌]", "[糗大了]", "[坏笑]", "[左哼哼]", "[右哼哼]", 
	"[哈欠]", "[鄙视]", "[委屈]", "[快哭了]", "[阴险]", "[亲亲]", "[吓]", "[可怜]",
	"[菜刀]", "[西瓜]", "[啤酒]", "[篮球]", "[乒乓]", "[咖啡]", "[饭]", "[猪头]",
	"[玫瑰]", "[凋谢]", "[嘴唇]", "[爱心]", "[心碎]", "[蛋糕]", "[闪电]", "[炸弹]", 
	"[刀]", "[足球]", "[瓢虫]", "[便便]", "[月亮]", "[太阳]", "[礼物]", "[拥抱]", 
	"[强]", "[弱]", "[握手]", "[胜利]", "[抱拳]", "[勾引]", "[拳头]", "[差劲]", 
	"[爱你]", "[NO]", "[OK]"];


var r = false;

function a(e){
	return c[e];
}	

/*//$(this).click(function(){
//	$("#face").click();
//})
*/
$("#mywords").click(function(){
	$(".give_a1").hide();  /*这是礼物*/
	$(".give_a01").show();  /*这是表情*/
	$("#sendChat").show();  /*发送文字按钮*/
	$(".div_input").css("background","none");  /*input背景色隐藏*/
});

$("#face").click(function(e) {
    var t, n, c;
    if (!$(this).hasClass("disabled")) {
        var d = $(this).parent().find("." + o);
        if (d.length <= 0) if (r) {
            t = '<div class="' + o + ' facebox-pc mCustomScrollbar"><table border="0" cellspacing="0" cellpadding="0"><tr>';
            for (var l = 1; l <= 90; l++) n = a(l - 1), t += '<td><img width="24" src="' + s + "Expression_" + l + '@2x.png" onclick="$(\'' + i.assign + "').setCaret();$('" + i.assign + "').insertAtCaret('" + n + "');\" /></td>", l % 9 === 0 && (t += "</tr><tr>");
            t += "</tr></table></div>", $(this).parent().append(t), c = $(this).position()
        } else {
            var u = $(window).width(),
                p = "";
            t = '<div class="' + o + ' facebox-mobile" style="width:' + u + 'px;"><div class="qqFace-box" style="width:' + 5 * u + 'px">';
            for (var l = 1; l <= 5; l++) {
                t += '<div class="qqFace-mobile" style="width:' + u + 'px">';
                for (var g = 1; g <= 20; g++) {
                    var f = 20 * (l - 1) + g;
                    n = a(f - 1), n && (t += "<li onclick=\"setCaret(this);insertAtCaret('" + n + '\',this);"><img width="24" src="' + s + "Expression_" + f + '@2x.png" /></li>')
                }
                t += "<li onclick=\"$('" + i.assign + '\').deleteCaret();" ><img width="24" src="' + s + 'faceDelete@2x.png" /></li></div>', p += 1 === l ? "<a class='active'></a>" : "<a></a>"
            }
            t += "<div style='clear:both'></div></div><div class='text-center'>" + p + "</div></div>", $(this).parent().append(t), c = $(this).position(), d = $(this).parent().find("." + o), d.data("data", {
                index: 0
            });
            var h, v, y = !1,
                m = d.find(".qqFace-box").eq(0).get(0);
            d[0].addEventListener("touchstart", function(e) {
                h = e.touches[0].pageX
            }, !1), d[0].addEventListener("touchmove", function(e) {
                e.preventDefault(), v = e.touches[0].pageX - h;
                var t = $(this).data("data").index * u,
                    i = "translate3d(" + (v - t) + "px, 0, 0)";
                m.style.webkitTransform = i, m.style.mozTransform = i, m.style.transform = i, y = !0
            }, !1), d[0].addEventListener("touchend", function(e) {
                if (y) {
                    var t = $(this).data("data").index,
                        i = t * u;
                    v < -50 ? t < 4 && (t += 1, $(this).data("data", {
                        index: t
                    }), i += u) : v > 50 && t >= 1 && (t -= 1, $(this).data("data", {
                        index: t
                    }), i -= u), d.find(".text-center a").removeClass("active").eq(t).addClass("active");
                    var n = "translate3d(-" + i + "px, 0, 0)";
                    m.style.webkitTransform = n, m.style.mozTransform = n, m.style.transform = n
                }
                y = !1
            }, !1), d.on("click", "li", function(e) {
                e.stopPropagation()
            })
        }
        $(this).parent().find("." + o).toggle() ,e.stopPropagation()
    }
}), void $(document).click(function() {
    $("." + o).hide()
})


function setCaret(obj){
	
	  if (/msie/.test(navigator.userAgent.toLowerCase())) {
          var e = function() {
                  var e = $("#mywords").get(0);
                  e.caretPos = document.selection.createRange().duplicate()
              };
          $("#mywords").click(e).select(e).keyup(e)
      }
	
}

function insertAtCaret(e,obj){
	var t = $("#mywords").get(0);
    if (document.all && t.createTextRange && t.caretPos) {
        var i = t.caretPos;
        i.text = "" === i.text.charAt(i.text.length - 1) ? e + "" : e
    } else if (t.setSelectionRange) {
        var n = t.selectionStart,
            a = t.selectionEnd,
            o = t.value.substring(0, n),
            s = t.value.substring(a);
        t.value = o + e + s;
        //t.value = "123";
        var c = e.length;
        t.setSelectionRange(n + c, n + c), r ? $("#mywords").focus() : $("#mywords").blur()
    } else t.value += e
	
}


$.fn.extend({
    selectContents: function() {
        $(this).each(function(e) {
            var t, i, n, r, a = this;
            (n = a.ownerDocument) && (r = n.defaultView) && "undefined" != typeof r.getSelection && "undefined" != typeof n.createRange && (t = window.getSelection()) && "undefined" != typeof t.removeAllRanges ? (i = n.createRange(), i.selectNode(a), 0 === e && t.removeAllRanges(), t.addRange(i)) : document.body && "undefined" != typeof document.body.createTextRange && (i = document.body.createTextRange()) && (i.moveToElementText(a), i.select())
        })
    },
    setCaret: function() {
        if (/msie/.test(navigator.userAgent.toLowerCase())) {
            var e = function() {
                    var e = $(this).get(0);
                    e.caretPos = document.selection.createRange().duplicate()
                };
            $(this).click(e).select(e).keyup(e)
        }
    },
    insertAtCaret: function(e) {
        var t = $(this).get(0);
        if (document.all && t.createTextRange && t.caretPos) {
            var i = t.caretPos;
            i.text = "" === i.text.charAt(i.text.length - 1) ? e + "" : e
        } else if (t.setSelectionRange) {
            var n = t.selectionStart,
                a = t.selectionEnd,
                o = t.value.substring(0, n),
                s = t.value.substring(a);
            //t.value = o + e + s;
            t.value = "123";
            var c = e.length;
            t.setSelectionRange(n + c, n + c), r ? $(this).focus() : $(this).blur()
        } else t.value += e
    },
    deleteCaret: function() {
        var e = $(this),
            t = e.val(),
            i = /(\[[^@]{1,3}\])$/;
        t = i.test(t) ? t.replace(i, "") : t.substring(0, t.length - 1), e.val(t), e.blur()
    }
})

















