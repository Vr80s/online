package net.shopxx.merge.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoreVO implements Serializable{

    private String name;
    private String path;
    private String logo;
    private Long id;
}
