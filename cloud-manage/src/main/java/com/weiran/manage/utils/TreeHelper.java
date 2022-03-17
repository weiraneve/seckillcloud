package com.weiran.manage.utils;


import com.weiran.manage.dto.admin.PermissionMenuDTO;
import com.weiran.manage.dto.admin.TreeRoleMenuDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TreeHelper {

    public static List<PermissionMenuDTO> getSortedNodes(List<PermissionMenuDTO> datas) {
        List<PermissionMenuDTO> nodes = convertData2Node(datas);
        // 拿到根节点
        return getRootNodes(nodes);
    }

    private static List<PermissionMenuDTO> convertData2Node(List<PermissionMenuDTO> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            PermissionMenuDTO n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                PermissionMenuDTO m = nodes.get(j);
                if (Objects.equals(m.getParentId(), n.getId())) {
                    if (StringUtils.isEmpty(n.getChildren())) {
                        n.setChildren(new ArrayList<>());
                    }
                    n.getChildren().add(m);
                }
            }
        }
        return nodes;
    }

    private static List<PermissionMenuDTO> getRootNodes(List<PermissionMenuDTO> nodes) {
        List<PermissionMenuDTO> root = new ArrayList<>();
        for (PermissionMenuDTO node : nodes) {
            if (node.getParentId() == null || node.getParentId() == 0) {
                root.add(node);
            }
        }
        return root;
    }

    private static void addNode(List<PermissionMenuDTO> nodes, PermissionMenuDTO node, int currentLevel) {
        nodes.add(node);
        if (node.getChildren().size() == 0) {
            return;
        }
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(nodes, node.getChildren().get(i), currentLevel + 1);
        }
    }


    public static List<TreeRoleMenuDTO> getSortedTreeNodes(List<TreeRoleMenuDTO> datas) {
        List<TreeRoleMenuDTO> nodes = convertData2TreeNode(datas);
        // 拿到根节点
        return getTreeRootNodes(nodes);
    }

    private static List<TreeRoleMenuDTO> convertData2TreeNode(List<TreeRoleMenuDTO> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            TreeRoleMenuDTO n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                TreeRoleMenuDTO m = nodes.get(j);
                if (Objects.equals(m.getParentId(), n.getValue())) {
                    if (StringUtils.isEmpty(n.getChildren())) {
                        n.setChildren(new ArrayList<>());
                    }
                    n.getChildren().add(m);
                }
            }
        }
        return nodes;
    }
    private static List<TreeRoleMenuDTO> getTreeRootNodes(List<TreeRoleMenuDTO> nodes) {
        List<TreeRoleMenuDTO> root = new ArrayList<>();
        for (TreeRoleMenuDTO node : nodes) {
            if (node.getParentId() == null || node.getParentId() == 0) {
                root.add(node);
            }
        }
        return root;
    }
}
