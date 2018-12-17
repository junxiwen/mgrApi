package com.ys.mgr.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fsj on 2017/5/26.
 */
public class TreeNode {
    private Integer id;
    private String title;
    private Boolean checked;
    private Boolean expand;
    private Boolean selected = false;
    private Boolean disabled = false;
    private Boolean disableCheckbox = false;
    private Integer parentId;
    private String url;
    private String icon;
    private List<TreeNode> children = new ArrayList<>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getChecked() {
        return checked == null ? false : checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getExpand() {
        if ((this.parentId == null || this.parentId < 10) && !this.children.isEmpty()) {
            return true;
        }
        return expand == null ? false : expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Boolean getSelected() {
        return selected == null ? false : selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getDisabled() {
        return disabled == null ? false : disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDisableCheckbox() {
        return disableCheckbox == null ? false : disableCheckbox;
    }

    public void setDisableCheckbox(Boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    @JsonIgnore
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", checked=" + checked +
                ", expand=" + expand +
                ", selected=" + selected +
                ", disabled=" + disabled +
                ", disableCheckbox=" + disableCheckbox +
                ", parentId=" + parentId +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }
}
