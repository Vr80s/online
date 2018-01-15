package com.xczh.consumer.market.wxpay.entity;

import java.util.Date;

//WX red pack definition；
public class SendRedPack {

	private String rp_id; 
    private String nonce_str;// 随机字符串
    private String sign;// 签名
    private String mch_billno;// 商户订单号
    private String mch_id;// 商户号
    private String wxappid;// 公众账号
    private String send_name;// 商户名称
    private String re_openid;// 用户
    private int total_amount;// 付款金额
    private int total_num;// 红包发放总人数
    private String wishing;// 红包祝福语
    private String client_ip;// Ip地址
    private String act_name;// 活动名称
    private String remark;// 备注
    private String sub_mch_id;
    private String nick_name;
    private int min_value;
    private int max_value;
    private String logo_imgurl;
    private String share_content;
    private String share_url;
    private String share_imgurl;
    private Date create_time = new Date();
    
    public String getRp_id() {
		return rp_id;
	}

	public void setRp_id(String rp_id) {
		this.rp_id = rp_id;
	}

	public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public int getMin_value() {
		return min_value;
	}

	public void setMin_value(int min_value) {
		this.min_value = min_value;
	}

	public int getMax_value() {
		return max_value;
	}

	public void setMax_value(int max_value) {
		this.max_value = max_value;
	}

	public String getLogo_imgurl() {
		return logo_imgurl;
	}

	public void setLogo_imgurl(String logo_imgurl) {
		this.logo_imgurl = logo_imgurl;
	}

	public String getShare_content() {
		return share_content;
	}

	public void setShare_content(String share_content) {
		this.share_content = share_content;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public String getShare_imgurl() {
		return share_imgurl;
	}

	public void setShare_imgurl(String share_imgurl) {
		this.share_imgurl = share_imgurl;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	private String return_code;
	private String result_code;
	private String return_msg;
		
	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getResult_code() {
		return result_code;
	}
	
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}	
	
}

