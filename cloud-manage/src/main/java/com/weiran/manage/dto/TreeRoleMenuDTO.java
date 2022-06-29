package com.weiran.manage.dto;

import lombok.Data;

import java.util.List;


@Data
public class TreeRoleMenuDTO {

    private Integer value;

    private Integer parentId;

    private Integer level;

    private String key;

    /**
     * 名称
     */
    private String title;

    public List<TreeRoleMenuDTO> children = null;


}
