package com.ys.mgr.form.request;

import com.ys.mgr.util.MyStringUtils;
import lombok.*;
import net.miidi.fsj.util.sjp.util.MyJdbcUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Date: 2017/12/8 13:44
 */
@Data
@NoArgsConstructor // 自动生成没有参数的构造函数
@AllArgsConstructor // 自动生成全部参数的构造函数
@EqualsAndHashCode
@ToString
public class MyRequestForm implements Serializable {
    private Integer reqPage = 1; // 当前页码
    private Integer reqPageSize = 10;// 每页大小
    // 暂时只支持单个字段排序
    private String reqOrderName; // 排序的字段名称，是JavaBean的字段名称，不是数据库的列名
    private String reqOrderType = "DESC"; // 排序方式 asc ／ desc

    private Date reqDateStart;
    private Date reqDateEnd;
    private Map<String, Object> reqParam = new HashMap<>(0); //其他请求参数

    /**
     * 偏移量，初始记录行的偏移量是 0(而不是 1)
     *
     * @return 偏移量
     */
    public long getOffset() throws Exception {
        return ((reqPage == null || reqPage < 1) ? 0 : (reqPage - 1))
                * ((reqPageSize == null || reqPageSize < 1) ? 10 : reqPageSize);
    }


    /**
     * 排序的sql片段，如：order by id desc
     *
     * @return 排序的sql片段
     */
    public String getOrderSql(Class clazz) throws Exception {
        if (MyStringUtils.isBlank(reqOrderName)){
            reqOrderName = "id";
        }
        String orderType = "ASC";
        if ("DESC".equalsIgnoreCase(reqOrderType)) {
            orderType = "DESC";
        }
        return " ORDER BY " + MyJdbcUtils.getColumnName(clazz, reqOrderName) + " " + orderType + " ";
    }

    /**
     * 分页的sql片段，如：limit 0, 10
     *
     * @return 分页的sql片段
     */
    public String getLimitSql() throws Exception {
        return " LIMIT " + getOffset() + ", " + ((reqPageSize == null || reqPageSize < 1) ? 10 : reqPageSize) + " ";
    }
}
