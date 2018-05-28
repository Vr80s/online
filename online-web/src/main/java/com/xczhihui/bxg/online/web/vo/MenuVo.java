package com.xczhihui.bxg.online.web.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.xczhihui.bxg.online.common.domain.Menu;

import javax.persistence.Column;
import java.util.List;

/**
 * 菜单web端调用的结果封装类
 * @author Rongcai Kang
 */
public class MenuVo extends OnlineBaseVo {

    /**
     * 学科id号
     */
    private Integer id;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 菜单编号
     */
    private Integer number;

    /**
     * 菜单类型
     */
    private Integer type;

    private List<ScoreTypeVo> sencodMenu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<ScoreTypeVo> getSencodMenu() {
        return sencodMenu;
    }

    public void setSencodMenu(List<ScoreTypeVo> sencodMenu) {
        this.sencodMenu = sencodMenu;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
