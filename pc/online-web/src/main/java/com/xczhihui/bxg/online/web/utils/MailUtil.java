package com.xczhihui.bxg.online.web.utils;

public class MailUtil {
	
	public static String getEmailContent(String codeUrl) {
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>");
		builder.append("<html lang=\"en\">");
		builder.append("<head>");
		builder.append("    <meta charset=\"UTF-8\">");
		builder.append("    <title>Title</title>");
		builder.append("    <style type=\"text/css\">");
		builder.append("    	@charset \"UTF-8\";@keyframes arrowAnimotion{ 0% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  25% {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"  30% {\n" +
				"    transform: translateY(40px);\n" +
				"  }\n" +
				"  55% {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"  60% {\n" +
				"    transform: translateY(40px);\n" +
				"  }\n" +
				"  85% {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"  90% {\n" +
				"    transform: translateY(40px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-moz-keyframes arrowAnimotion {\n" +
				"  from {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  to {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"}\n" +
				"@-webkit-keyframes arrowAnimotion {\n" +
				"  0% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: translateY(30px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-o-keyframes arrowAnimotion {\n" +
				"  from {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  to {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"}\n" +
				"@-ms-keyframes arrowAnimotion {\n" +
				"  from {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  to {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"}\n" +
				"/*titleChange动画*/\n" +
				"@keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-moz-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-webkit-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-o-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-ms-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"/*wordsChange动画*/\n" +
				"@keyframes wordsChange {\n" +
				"  0% {\n" +
				"    transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-moz-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -moz-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -moz-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-webkit-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -webkit-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -webkit-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-o-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -o-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -o-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-ms-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -ms-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -ms-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"body html {\n" +
				"  margin: 0;\n" +
				"  padding: 0;\n" +
				"  font-family: \"Microsoft Yahei\";\n" +
				"  font-size: 18px;\n" +
				"}\n" +
				".page-email {\n" +
				"  width: 602px;\n" +
				"  height: 463px;\n" +
				"  border: 1px solid #eeeeee;\n" +
				"  margin: 0px auto;\n" +
				"  -moz-border-radius: 3px;\n" +
				"  /* Firefox */\n" +
				"  -webkit-border-radius: 3px;\n" +
				"  /* Safari 和 Chrome */\n" +
				"  border-radius: 3px;\n" +
				"  /* Opera 10.5+, 以及使用了IE-CSS3的IE浏览器 */\n" +
				"  padding: 0 25px;\n" +
				"  position: relative;\n" +
				"}\n" +
				".page-email .header {\n" +
				"  border-bottom: 1px solid #eee;\n" +
				"  margin-top: 5px;\n" +
				"}\n" +
				".page-email .header p {\n" +
				"  color: #2cb82c;\n" +
				"  width: 100%;\n" +
				"  text-align: center;\n" +
				"  margin: 0px;\n" +
				"}\n" +
				".page-email .header .p1 {\n" +
				"  font-size: 28px;\n" +
				"}\n" +
				".page-email .header .p2 {\n" +
				"  font-size: 12px;\n" +
				"  margin-bottom: 20px;\n" +
				"}\n" +
				".page-email .article .text {\n" +
				"  width: 100%;\n" +
				"  text-align: left;\n" +
				"  font-size: 14px;\n" +
				"  padding: 30px 0px;\n" +
				"  font-weight: 600;\n" +
				"  margin-bottom: 4px;\n" +
				"  color: #2CB82C;\n" +
				"  margin-top: 8px;\n" +
				"}\n" +
				".page-email .article .link {\n" +
				"  line-height: 25px;\n" +
				"  font-size: 14px;\n" +
				"  padding-bottom: 10px;\n" +
				"  margin: 0;\n" +
				"}\n" +
				".page-email .article .link .tip {\n" +
				"  padding-bottom: 10px;\n" +
				"  display: block;\n" +
				"  color: #666;\n" +
				"  font-size: 14px;\n" +
				"}\n" +
				".page-email .article .link a {\n" +
				"  text-decoration: none;\n" +
				"  font-size: 12px;\n" +
				"  color: #55b9e6;\n" +
				"}\n" +
				".page-email .article .footer {\n" +
				"  font-size: 12px;\n" +
				"  color: #999;\n" +
				"  margin-top: 10px;\n" +
				"}\n" +
				".page-email .article .addr {\n" +
				"  font-size: 12px;\n" +
				"  color: #999;\n" +
				"  text-align: center;\n" +
				"  position: absolute;\n" +
				"  bottom: 10px;\n" +
				"  left: 50%;\n" +
				"  margin-left: -123px;\n" +
				"}");
		builder.append("    </style>");
		builder.append("</head>");
		builder.append("<body>");
		builder.append("<div class=\"page-email\">");
		builder.append("    <div class=\"header\">");
		builder.append("        <p class=\"p1\">熊猫中医</p>");
		builder.append("        <p class=\"p2\">ixincheng.com</p>");
		builder.append("    </div>");
		builder.append("    <div class=\"article\">");
		builder.append("        <p class=\"text\">");
		builder.append("            亲爱的用户");
		builder.append("        </p>");
		builder.append("        <p class=\"link\">");
		builder.append("            <span class=\"tip\">点击下面链接，完成重置密码。</span>");
		builder.append("            <a href=\""+codeUrl+"\">"+codeUrl+"</a>");
		builder.append("        </p>");
		builder.append("        <p class=\"footer\">");
		builder.append("            (若无法点击，请复制到浏览器)");
		builder.append("        </p>");
		builder.append("        <p class=\"addr\">");
		builder.append("            海南省海口市美兰区演丰镇瑶城一路D栋心承智慧大厦");
		builder.append("        </p>");
		builder.append("    </div>");
		builder.append("</div>");
		builder.append("</body>");
		builder.append("</html>");
		return builder.toString();
	}


	public static String getRegisterEmailContent(String codeUrl) {
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>");
		builder.append("<html lang=\"en\">");
		builder.append("<head>");
		builder.append("    <meta charset=\"UTF-8\">");
		builder.append("    <title>Title</title>");
		builder.append("    <style type=\"text/css\">");
		builder.append("    	@charset \"UTF-8\";@keyframes arrowAnimotion{ 0% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  25% {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"  30% {\n" +
				"    transform: translateY(40px);\n" +
				"  }\n" +
				"  55% {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"  60% {\n" +
				"    transform: translateY(40px);\n" +
				"  }\n" +
				"  85% {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"  90% {\n" +
				"    transform: translateY(40px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-moz-keyframes arrowAnimotion {\n" +
				"  from {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  to {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"}\n" +
				"@-webkit-keyframes arrowAnimotion {\n" +
				"  0% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: translateY(30px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-o-keyframes arrowAnimotion {\n" +
				"  from {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  to {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"}\n" +
				"@-ms-keyframes arrowAnimotion {\n" +
				"  from {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"  to {\n" +
				"    transform: translateY(50px);\n" +
				"  }\n" +
				"}\n" +
				"/*titleChange动画*/\n" +
				"@keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-moz-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-webkit-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-o-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"@-ms-keyframes titleChange {\n" +
				"  0% {\n" +
				"    transform: scale(0, 0);\n" +
				"    opacity: 0;\n" +
				"  }\n" +
				"  50% {\n" +
				"    transform: scale(0.5, 0.5);\n" +
				"    opacity: 0.5;\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: scale(1, 1);\n" +
				"    opacity: 1;\n" +
				"  }\n" +
				"}\n" +
				"/*wordsChange动画*/\n" +
				"@keyframes wordsChange {\n" +
				"  0% {\n" +
				"    transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-moz-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -moz-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -moz-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-webkit-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -webkit-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -webkit-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-o-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -o-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -o-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"@-ms-keyframes wordsChange {\n" +
				"  0% {\n" +
				"    -ms-transform: translateY(3400px);\n" +
				"  }\n" +
				"  100% {\n" +
				"    -ms-transform: translateY(0px);\n" +
				"  }\n" +
				"}\n" +
				"body html {\n" +
				"  margin: 0;\n" +
				"  padding: 0;\n" +
				"  font-family: \"Microsoft Yahei\";\n" +
				"  font-size: 18px;\n" +
				"}\n" +
				".page-email {\n" +
				"  width: 602px;\n" +
				"  height: 463px;\n" +
				"  border: 1px solid #eeeeee;\n" +
				"  margin: 0px auto;\n" +
				"  -moz-border-radius: 3px;\n" +
				"  /* Firefox */\n" +
				"  -webkit-border-radius: 3px;\n" +
				"  /* Safari 和 Chrome */\n" +
				"  border-radius: 3px;\n" +
				"  /* Opera 10.5+, 以及使用了IE-CSS3的IE浏览器 */\n" +
				"  padding: 0 25px;\n" +
				"  position: relative;\n" +
				"}\n" +
				".page-email .header {\n" +
				"  border-bottom: 1px solid #eee;\n" +
				"  margin-top: 5px;\n" +
				"}\n" +
				".page-email .header p {\n" +
				"  color: #2cb82c;\n" +
				"  width: 100%;\n" +
				"  text-align: center;\n" +
				"  margin: 0px;\n" +
				"}\n" +
				".page-email .header .p1 {\n" +
				"  font-size: 28px;\n" +
				"}\n" +
				".page-email .header .p2 {\n" +
				"  font-size: 12px;\n" +
				"  margin-bottom: 20px;\n" +
				"}\n" +
				".page-email .article .text {\n" +
				"  width: 100%;\n" +
				"  text-align: left;\n" +
				"  font-size: 14px;\n" +
				"  padding: 30px 0px;\n" +
				"  font-weight: 600;\n" +
				"  margin-bottom: 4px;\n" +
				"  color: #2CB82C;\n" +
				"  margin-top: 8px;\n" +
				"}\n" +
				".page-email .article .link {\n" +
				"  line-height: 25px;\n" +
				"  font-size: 14px;\n" +
				"  padding-bottom: 10px;\n" +
				"  margin: 0;\n" +
				"}\n" +
				".page-email .article .link .tip {\n" +
				"  padding-bottom: 10px;\n" +
				"  display: block;\n" +
				"  color: #666;\n" +
				"  font-size: 14px;\n" +
				"}\n" +
				".page-email .article .link a {\n" +
				"  text-decoration: none;\n" +
				"  font-size: 12px;\n" +
				"  color: #55b9e6;\n" +
				"}\n" +
				".page-email .article .footer {\n" +
				"  font-size: 12px;\n" +
				"  color: #999;\n" +
				"  margin-top: 10px;\n" +
				"}\n" +
				".page-email .article .addr {\n" +
				"  font-size: 12px;\n" +
				"  color: #999;\n" +
				"  text-align: center;\n" +
				"  position: absolute;\n" +
				"  bottom: 10px;\n" +
				"  left: 50%;\n" +
				"  margin-left: -123px;\n" +
				"}");
		builder.append("    </style>");
		builder.append("</head>");
		builder.append("<body>");
		builder.append("<div class=\"page-email\">");
		builder.append("    <div class=\"header\">");
		builder.append("        <p class=\"p1\">熊猫中医</p>");
		builder.append("        <p class=\"p2\">ixincheng.com</p>");
		builder.append("    </div>");
		builder.append("    <div class=\"article\">");
		builder.append("        <p class=\"text\">");
		builder.append("            亲爱的用户，欢迎加入熊猫中医!");
		builder.append("        </p>");
		builder.append("        <p class=\"link\">");
		builder.append("            <span class=\"tip\">点击下面链接，踏上成长之路。</span>");
		builder.append("            <a href=\""+codeUrl+"\">"+codeUrl+"</a>");
		builder.append("        </p>");
		builder.append("        <p class=\"footer\">");
		builder.append("            (若无法点击，请复制到浏览器)");
		builder.append("        </p>");
		builder.append("        <p class=\"addr\">");
		builder.append("            海南省海口市美兰区演丰镇瑶城一路D栋心承智慧大厦");
		builder.append("        </p>");
		builder.append("    </div>");
		builder.append("</div>");
		builder.append("</body>");
		builder.append("</html>");
		return builder.toString();
	}
}
