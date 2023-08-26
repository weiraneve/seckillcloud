package com.weiran.manage.utils;

import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.dto.TreeRoleMenuDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeHelper {

    public static List<PermissionMenuDTO> getSortedNodes(List<PermissionMenuDTO> dtoList) {
        convertData2Node(dtoList, (n, m) -> Objects.equals(m.getParentId(), n.getId()));
        return findRootNodes(dtoList, node -> node.getParentId() == null || node.getParentId() == 0);
    }

    public static List<TreeRoleMenuDTO> getSortedTreeNodes(List<TreeRoleMenuDTO> dtoList) {
        convertData2TreeNode(dtoList, (n, m) -> Objects.equals(m.getParentId(), n.getValue()));
        return findRootNodes(dtoList, node -> node.getParentId() == null || node.getParentId() == 0);
    }

    private static void convertData2Node(List<PermissionMenuDTO> nodes, NodeRelation<PermissionMenuDTO> checker) {
        for (int i = 0; i < nodes.size(); i++) {
            PermissionMenuDTO n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                PermissionMenuDTO m = nodes.get(j);
                if (checker.isParent(n, m)) {
                    if (n.getChildren() == null) {
                        n.setChildren(new ArrayList<>());
                    }
                    n.getChildren().add(m);
                }
            }
        }
    }

    private static void convertData2TreeNode(List<TreeRoleMenuDTO> nodes, NodeRelation<TreeRoleMenuDTO> checker) {
        for (int i = 0; i < nodes.size(); i++) {
            TreeRoleMenuDTO n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                TreeRoleMenuDTO m = nodes.get(j);
                if (checker.isParent(n, m)) {
                    if (n.getChildren() == null) {
                        n.setChildren(new ArrayList<>());
                    }
                    n.getChildren().add(m);
                }
            }
        }
    }

    private static <T> List<T> findRootNodes(List<T> nodes, NodeChecker<T> checker) {
        List<T> root = new ArrayList<>();
        for (T node : nodes) {
            if (checker.isRoot(node)) {
                root.add(node);
            }
        }
        return root;
    }

    @FunctionalInterface
    private interface NodeRelation<T> {
        boolean isParent(T node1, T node2);
    }

    @FunctionalInterface
    private interface NodeChecker<T> {
        boolean isRoot(T node);
    }
}
