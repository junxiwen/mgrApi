package com.ys.mgr.form.response;

import com.ys.mgr.form.request.MyRequestForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * Date: 2017/12/8 14:05
 */
@Data
@EqualsAndHashCode
public class PageData<T> implements Serializable {
    // 当前页码
    private Integer current;

    // 数据总数
    private Long total;

    // 每页大小
    private Integer pageSize;

    // 数据
    private List<T> data;

    private Integer totalPage;

    //扩展数据
    private Map<String,Object> otherData;

    public PageData(MyRequestForm myRequestForm) {
        this.current = myRequestForm.getReqPage();
        this.pageSize = myRequestForm.getReqPageSize();
    }

    public Integer getTotalPage() {
        if(total<1){
            return 0;
        }
        if(total % pageSize == 0){
            return (int)(total/pageSize);
        }else{
            return (int)(total/pageSize)+1;
        }
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
