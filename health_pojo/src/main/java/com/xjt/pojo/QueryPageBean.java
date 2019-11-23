package com.xjt.pojo;
import java.io.Serializable;

/**
 * @Description:
 * @author: 向某人
 * @date:2019/11/10 15:43
 */
public class QueryPageBean implements Serializable {
    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件

    @Override
    public String toString() {
        return "QueryPageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", queryString='" + queryString + '\'' +
                '}';
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
