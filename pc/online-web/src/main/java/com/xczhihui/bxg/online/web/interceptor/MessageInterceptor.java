package com.xczhihui.bxg.online.web.interceptor;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.dao.ASKQuestionListDao;
import com.xczhihui.bxg.online.web.dao.AskAnswerDao;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.service.MessageService;
import com.xczhihui.bxg.online.web.vo.MessageShortVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Fudong.Sun【】
 * @Date 2017/2/5 16:03
 */
public class MessageInterceptor extends SimpleHibernateDao implements HandlerInterceptor {
    @Autowired
    private MessageService messageService;

    @Autowired
    private AskAnswerDao dao;

    @Autowired
    private CourseDao coursedao;

    @Autowired
    private ASKQuestionListDao questionListDao;

    @Value("${web.url}")
    private String weburl;
    /**
     * 在Controller方法前进行拦截
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return true;
    }

    /**
     * 在Controller方法后进行拦截
     * @param request
     * @param response
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在页面渲染完成之后进行拦截
     * @param request
     * @param response
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        BxgUser u = UserLoginUtil.getLoginUser(request);
        String uri = request.getRequestURI();
        String message;
        if("/ask/answer/addAnswer".equalsIgnoreCase(uri)){
            /** 1,提问者问题有新回答---消息提醒 */
            String question_id = request.getParameter("question_id");
            //根据id获取问题信息
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", question_id);
            String sql = "select user_id,title from oe_ask_question where id= :id";
            List<Map<String,Object>> questions = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
            Map<String,Object> question = questions.size()>0? questions.get(0) : null;
            if(question!=null) {
                String user_id = (String) question.get("user_id");
                String title = (String) question.get("title");
                //处理一下title里面的特殊字符
                title= title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\"","&quot;");
                String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                String msg_link = weburl + "/web/qusDetail/"+question_id;
                message = u.getName() + "回答了您的问题<a href=\"javascript:void(0)\" onclick=\"on_click_msg('"+msg_id+"','"+msg_link+"');\">" + title + "</a>";
                /** 屏蔽自己触发的与自己相关的消息消息 */
                if(!u.getId().equalsIgnoreCase(user_id)) {
                    messageService.addMessage(msg_id, user_id, 3, message, 0, null);
                }
                 /** 2,收藏问题有新回答---消息提醒 */
                //根据问题id查找收藏该问题的所有人
                params = new MapSqlParameterSource();
                params.addValue("question_id", question_id);
                sql = "select id,user_id from oe_ask_collection where question_id= :question_id";
                List<Map<String,Object>> collections = this.getNamedParameterJdbcTemplate().queryForList(sql,params);
                String msg_id1 = UUID.randomUUID().toString().replaceAll("-", "");
                String msg_link1 = weburl + "/web/qusDetail/"+question_id;
                message = u.getName()+"回答了您收藏的问题<a href=\"javascript:void(0)\" onclick=\"on_click_msg('"+msg_id1+"','"+msg_link1+"');\">"+title+"</a>";
                for (Map<String, Object> collection : collections) {
                    String user_id1 = (String) collection.get("user_id");
                    /** 屏蔽自己触发的与自己相关的消息消息 */
                    if(!u.getId().equalsIgnoreCase(user_id1)) {
                        messageService.addMessage(msg_id1, user_id1, 3, message, 0, null);
                    }
                }
            }else{
                throw new RuntimeException("根据回答找不到问题！");
            }
        }else if("/ask/answer/acceptAnswer".equalsIgnoreCase(uri)){
            /** 3,回答者答案被标记为最佳---消息提醒 */
            String answer_id = request.getParameter("answer_id");
            //根据answer_id查找问题id
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", answer_id);
            String sql = "select question_id,accepted,user_id from oe_ask_answer where id= :id";
            Map<String,Object> answer = this.getNamedParameterJdbcTemplate().queryForMap(sql,params);
            String question_id = (String) answer.get("question_id");
            boolean accepted = (boolean) answer.get("accepted");
            if(accepted) {//表示被标记为最佳
                //根据id获取问题信息
                params = new MapSqlParameterSource();
                params.addValue("id", question_id);
                sql = "select user_id,title from oe_ask_question where id= :id";
                List<Map<String, Object>> questions = this.getNamedParameterJdbcTemplate().queryForList(sql, params);
                Map<String, Object> question = questions.size() > 0 ? questions.get(0) : null;
                if (question != null) {
                    String user_id = (String) answer.get("user_id");
                    String title = (String) question.get("title");
                    //处理一下title里面的特殊字符
                    title= title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\"","&quot;");
                    String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                    String msg_link =  weburl + "/web/qusDetail/" + question_id;
                    message = u.getName() + "将您在问题<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">" + title + "</a>的回答采纳为最佳";
                    /** 屏蔽自己触发的与自己相关的消息消息 */
                    if(!u.getId().equalsIgnoreCase(user_id)) {
                        messageService.addMessage(msg_id, user_id, 3, message, 0, null);
                    }
                    /** 4,收藏问题有答案被标记为最佳---消息提醒 */
                    //根据问题id查找收藏该问题的所有人
                    params = new MapSqlParameterSource();
                    params.addValue("question_id", question_id);
                    sql = "select id,user_id from oe_ask_collection where question_id= :question_id";
                    List<Map<String, Object>> collections = this.getNamedParameterJdbcTemplate().queryForList(sql, params);
                    String msg_id1 = UUID.randomUUID().toString().replaceAll("-", "");
                    String msg_link1 =  weburl + "/web/qusDetail/" + question_id;
                    message = "您收藏的问题<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id1 + "','" + msg_link1 + "');\">" + title + "</a>有了最佳回答";
                    for (Map<String, Object> collection : collections) {
                        String user_id1 = (String) collection.get("user_id");
                        messageService.addMessage(msg_id1, user_id1, 3, message,0,null);
                    }
                } else {
                    throw new RuntimeException("根据回答找不到问题！");
                }
            }
        }else if("/ask/comment/addComment".equalsIgnoreCase(uri)) {
            String answer_id = request.getParameter("answer_id");
            String target_user_id = request.getParameter("target_user_id");
            //根据answer_id查找最新的评论或回复
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", answer_id);
            String sql = "select user_id,question_id from oe_ask_answer where id= :id";
            Map<String, Object> answer = this.getNamedParameterJdbcTemplate().queryForMap(sql,params);
            String user_id = (String) answer.get("user_id");
            String question_id = (String) answer.get("question_id");
            //根据id获取问题信息
            params = new MapSqlParameterSource();
            params.addValue("id", question_id);
            sql = "select user_id,title from oe_ask_question where id= :id";
            List<Map<String, Object>> questions = this.getNamedParameterJdbcTemplate().queryForList(sql, params);
            Map<String, Object> question = questions.size() > 0 ? questions.get(0) : null;
            if (question != null) {
                String title = (String) question.get("title");
                //处理一下title里面的特殊字符
                title= title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\"","&quot;");
                if (target_user_id == null) {//表示评论
                    /** 5,回答被评论---消息提醒 */
                    String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                    String msg_link =  weburl + "/web/qusDetail/" + question_id;
                    message = u.getName() + "评论了您在问题<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">"+ title +"</a>的回答";
                    /** 屏蔽自己触发的与自己相关的消息消息 */
                    if(!u.getId().equalsIgnoreCase(user_id)) {
                        messageService.addMessage(msg_id, user_id, 4, message, 0, null);
                    }
                } else {//表示回复
                    /** 6,评论被回复---消息提醒 */
                    String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                    String msg_link = weburl + "/web/qusDetail/" + question_id;
                    message = u.getName() + "在问题<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">"+ title +"</a>回复了您";
                    /** 屏蔽自己触发的与自己相关的消息消息 */
                    if(!u.getId().equalsIgnoreCase(target_user_id)) {
                        messageService.addMessage(msg_id, target_user_id, 4, message, 0, null);
                    }
                }
            } else {
                throw new RuntimeException("根据回答找不到问题！");
            }
        }else if("/bxs/article/saveAppraise".equalsIgnoreCase(uri)) {
            /** 7,文章被回复---消息提醒 */
            Integer article_id = Integer.parseInt(request.getParameter("article_id").toString());
            String target_user_id = request.getParameter("target_user_id");
            if (target_user_id != null) {//表示文章评论的回复
                //根据id获取问题信息
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("id", article_id);
                String sql = "select user_id,title from oe_bxs_article where id= :id";
                Map<String, Object> articles = this.getNamedParameterJdbcTemplate().queryForMap(sql, params);
                String title = (String) articles.get("title");
                //处理一下title里面的特殊字符
                title= title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\"","&quot;");
                String msg_id = UUID.randomUUID().toString().replaceAll("-", "");
                String msg_link =  weburl + "/web/html/forumDetail.html?articleId=" + article_id;
                message = u.getName() + "在文章<a href=\"javascript:void(0)\" onclick=\"on_click_msg('" + msg_id + "','" + msg_link + "');\">"+title+"</a>回复了您";
                /** 屏蔽自己触发的与自己相关的消息消息 */
                if(!u.getId().equalsIgnoreCase(target_user_id)) {
                    messageService.addMessage(msg_id, target_user_id, 4, message, 0, null);
                }
            }
        }else if("/online/questionlist/deleteQuestionById".equalsIgnoreCase(uri))  //管理员删除被投诉的问题
        {
            User user = (User) request.getSession().getAttribute("_adminUser_");
            if( user !=null){ //manager端管理员登录删除问题及回答信息
                Map<String, Object> question= questionListDao.findDeleteAccuseQuestion(request.getParameter("questionId"));
                if(question != null){
                    String title = (String) question.get("title");
                    //处理一下title里面的特殊字符
                    title= title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\"","&quot;");
                    //为问题被投诉者发送消息
                    String msg_id= UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
                    String content="您的问题<b>"+title+"</b>涉及"+question.get("text")+"，已被管理员删除，请遵守问答社区规则";
                    MessageShortVo messageShortVo=new MessageShortVo();
                    messageShortVo.setUser_id(question.get("user_id").toString());
                    messageShortVo.setId(msg_id);
                    messageShortVo.setContext(content);
                    messageShortVo.setCreate_person(user.getId());
                    messageShortVo.setType(0);
                    messageService.saveMessage(messageShortVo);
                    //为问题投诉者发送消息
                    msg_id= UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
                    content="您对问题<b>"+title+"</b>的投诉已被核实，问题已被管理员删除，感谢您对维护问答社区做出的贡献";
                    messageShortVo=new MessageShortVo();
                    messageShortVo.setUser_id(question.get("userId").toString());
                    messageShortVo.setId(msg_id);
                    messageShortVo.setContext(content);
                    messageShortVo.setCreate_person(user.getId());
                    messageShortVo.setType(0);
                    messageService.saveMessage(messageShortVo);
                }
            }
        }else  if ("/ask/answer/deleteAnswerById".equalsIgnoreCase(uri)) //管理员删除被投诉的回答
        {
            User user = (User) request.getSession().getAttribute("_adminUser_");
            if( user !=null){ //manager端管理员登录删除问题及回答信息
                Map<String, Object> answer= dao.findDeleteAccuseAnswer(request.getParameter("answerId"));
                if(answer != null){
                    String title = (String) answer.get("title");
                    //处理一下title里面的特殊字符
                    title= title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\"","&quot;");
                    String text = (String) answer.get("text");
                    //处理一下title里面的特殊字符
                    text= text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\"","&quot;");
                    //为被投诉的回答的回答者发消息
                    String msg_id= UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
                    String content="您在问题<b>"+title+"</b>的回答涉及"+text+"，已被管理员删除，请遵守问答社区规则";
                    MessageShortVo messageShortVo=new MessageShortVo();
                    messageShortVo.setUser_id(answer.get("user_id").toString());
                    messageShortVo.setId(msg_id);
                    messageShortVo.setContext(content);
                    messageShortVo.setCreate_person(user.getId());
                    messageShortVo.setType(0);
                    messageService.saveMessage(messageShortVo);

                    //为回答的投诉者发送消息
                    msg_id= UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
                    content="您对"+answer.get("name")+"的回答进行的投诉已被核实，回答已被管理员删除，感谢您对维护问答社区做出的贡献";
                    messageShortVo=new MessageShortVo();
                    messageShortVo.setUser_id(answer.get("userId").toString());
                    messageShortVo.setId(msg_id);
                    messageShortVo.setContext(content);
                    messageShortVo.setCreate_person(user.getId());
                    messageShortVo.setType(0);
                    messageService.saveMessage(messageShortVo);
                }
            }
        }

    }
}
