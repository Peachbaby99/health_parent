package com.xjt.pojo;
import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/10 15:42
 */
public class PageResult implements Serializable {
    private Long total;//总记录数
    private List rows;//当前页结果

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
