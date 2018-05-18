/*省市区插件 20180508-yuxin  iProvincesSelect*/
/*
使用说明：
初始化：$("xxx").iProvincesSelect("init",data),$("xxx")为下拉框所属容器,
		data为下拉框对应数据
		缺省值{ province: '北京市', city: '北京市', district: '朝阳区' }
取值：$("xxx").iProvincesSelect("val");


例如：{value : "110000",
	text : "北京市",}
falg : true 时，对应的值的为value。数字
        false时，对应的值为text。汉字

*/
$.fn.iProvincesSelect = function(option, data,falg) {
	
    var iProvincesSelect = {
        init : function (that,data){
        	if(data==null){
        		if(falg){
        			data={
                            province: '110000',
                            city: '110100',
                            district: '110105'
        			}
        		}else{
        			data={
                            province: '北京市',
                            city: '市辖区',
                            district: '朝阳区'
        			}
        		}
			}
            var provinceStr = new StringBuffer();
            $.each(provinces,
                function(i, val) {
                    provinceStr.append("<option value='" + (falg ? val.value : val.text) + "'>" + val.text + "</option>");
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
            	
            	var falgVal = (falg ? val.value : val.text);
                if(falgVal == province){
                    $.each(val.children, function(i, val) {
                        cityStr.append("<option value='" + (falg ? val.value : val.text)  + "'>" + val.text + "</option>");
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
            	var falgValProvince = (falg ? val.value : val.text);
                if(falgValProvince == province){
                    $.each(val.children, function(i, val) {
                    	var falgValCity = (falg ? val.value : val.text);
                        if(falgValCity == city) {
                            $.each(val.children, function(i, val) {
                                districtStr.append("<option value='" + (falg ? val.value : val.text) + "'>" + val.text + "</option>");
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
        	
        	pcd.provinceText = that.find(".province option:selected").text();
        	pcd.cityText = that.find(".city option:selected").text();
        	pcd.districtText = that.find(".district option:selected").text();
        	
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