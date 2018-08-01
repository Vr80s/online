package com.xczhihui.common.util.bean;

import java.util.HashMap;

import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.IStringUtil;

/**
 * 微吼聊天列表放回的数据
 * @author yangxuan
 *
 */
public class VhallMessageParamsVo {

    
    private String channel_id; // 
    private String type; //
    private String pos; //
    private String limit; //
    private String start_time; //
    private String end_time; //
    
    public  HashMap<String, String> bulidMap() throws Exception{
        
        HashMap<String, String> params = new HashMap<String, String>();
        if(IStringUtil.isNotBlank(this.channel_id)) {
            params.put("channel_id", this.channel_id);
        }else {
            throw new Exception("频道id不能为空");
        }
        
        if(IStringUtil.isNotBlank(this.type)) {
            params.put("type", this.type);
        }
        
        if(IStringUtil.isNotBlank(this.pos)) {
            params.put("pos", this.pos);
        }
        
        if(IStringUtil.isNotBlank(this.limit)) {
            params.put("limit", this.limit);
        }
        
        if(IStringUtil.isNotBlank(this.start_time)) {
            try {
                DateUtil.parseDate(this.start_time, "yyyy/MM/dd");
            } catch (Exception e) {
                throw new Exception("开始时间格式有误");
            }
            params.put("start_time", this.start_time);
        }else {
            throw new Exception("开始时间不能为空");
        }
        
        if(IStringUtil.isNotBlank(this.end_time)) {
            params.put("end_time", this.end_time);
        }
        return params;
    }
    
    
    public String getChannel_id() {
        return channel_id;
    }
    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPos() {
        return pos;
    }
    public void setPos(String pos) {
        this.pos = pos;
    }
    public String getLimit() {
        return limit;
    }
    public void setLimit(String limit) {
        this.limit = limit;
    }
    public String getStart_time() {
        return start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
  
    
    
}
