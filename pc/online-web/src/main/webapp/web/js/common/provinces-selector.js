/*省市区插件 20180508-yuxin  iProvincesSelect*/
/*
使用说明：
初始化：$("xxx").iProvincesSelect("init",data),$("xxx")为下拉框所属容器,
		data为下拉框对应数据
		缺省值{ province: '北京市', city: '北京市', district: '朝阳区' }
取值：$("xxx").iProvincesSelect("val");
*/
$.fn.iProvincesSelect = function(option, data) {
    var iProvincesSelect = {
        init : function (that,data){
        	if(data==null){
        		data={
                    province: '北京市',
                    city: '北京市',
                    district: '朝阳区'
				}
			}
            var provinceStr = new StringBuffer();
            $.each(provinces,
                function(i, val) {
                    provinceStr.append("<option value='" + val.text + "'>" + val.text + "</option>");
                }
            );
            that.find(".province").html(provinceStr.toString());

			this.setVal(that,data);
            this.initEvent(that);
        },
        setProvince :function(that,province){

        	that.find(".province").val(province);

            var cityStr = new StringBuffer();
            $.each(provinces, function(i, val) {
                if(val.text == province){
                    $.each(val.children, function(i, val) {
                        cityStr.append("<option value='" + val.text + "'>" + val.text + "</option>");
                    });
                }
            });
            that.find(".city").html(cityStr.toString());
		},
        setCity :function(that,city){
        	that.find(".city").val(city);

            var province = that.find(".province").val();
            var districtStr = new StringBuffer();
            $.each(provinces, function(i, val) {
                if(val.text == province){
                    $.each(val.children, function(i, val) {
                        if(val.text == city) {
                            $.each(val.children, function(i, val) {
                                districtStr.append("<option value='" + val.text + "'>" + val.text + "</option>");
                            });
                        }
                    });
                }
            });
            that.find(".district").html(districtStr.toString());
		},
        setDistrict :function(that,district){
        	that.find(".district").val(district);
		},
        initEvent : function (that) {
            that.find(".province").change(function() {
                iProvincesSelect.setProvince(that,that.find(".province").val())
                iProvincesSelect.setCity(that,that.find(".city").val())
            });

            that.find(".city").change(function() {
                iProvincesSelect.setCity(that,that.find(".city").val())
            });

            that.find(".district").change(function() {
                iProvincesSelect.setDistrict(that,that.find(".district").val())
            });
        },
        getVal : function (that) {
            var pcd = {};
        	pcd.province = that.find(".province").val();
        	pcd.city = that.find(".city").val();
        	pcd.district = that.find(".district").val();
            return pcd;
        },
        setVal : function (that,data) {
            this.setProvince(that,data.province);
            this.setCity(that,data.city);
            this.setDistrict(that,data.district);
        }
    }

    if(option=="init"){
        iProvincesSelect.init(this,data);
    }else if(option=="val"){
        if(data==null){
            return iProvincesSelect.getVal(this);
        }else{
            iProvincesSelect.setVal(this,data);
        }
    }
}
/*下拉框插件 20180508-yuxin*/

function StringBuffer(str) {
    var arr = [];
    str = str || "";
    var size = 0; // 存放数组大小
    arr.push(str);
    // 追加字符串
    this.append = function(str1) {
        arr.push(str1);
        return this;
    };
    // 返回字符串
    this.toString = function() {
        return arr.join("");
    };
    // 清空
    this.clear = function(key) {
        size = 0;
        arr = [];
    };
    // 返回数组大小
    this.size = function() {
        return size;
    };
    // 返回数组
    this.toArray = function() {
        return buffer;
    };
    // 倒序返回字符串
    this.doReverse = function() {
        var str = buffer.join('');
        str = str.split('');
        return str.reverse().join('');
    };
}