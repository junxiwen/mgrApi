package com.ys.mgr.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fsj on 2017/5/27.
 */
public class PutSysResourceForm {
    private List<Integer> selectedResourceIds = new ArrayList<>();

    public List<Integer> getSelectedResourceIds() {
        return selectedResourceIds;
    }

    public void setSelectedResourceIds(List<Integer> selectedResourceIds) {
        this.selectedResourceIds = selectedResourceIds;
    }
}
