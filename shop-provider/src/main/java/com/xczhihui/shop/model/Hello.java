package com.xczhihui.shop.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
@TableName("users")
public class Hello extends Model<Hello> {

    private static final long serialVersionUID = 1L;

    private int id;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
