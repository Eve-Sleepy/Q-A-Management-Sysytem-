package com.example.faq.dto;

import java.util.ArrayList;

public class DocSearchDto {
    private String keyword;
    private Integer productId;
    private Integer authorId;
    private Integer sortway;
    private Integer currentPage;
    private Integer perPage;
    private String[] tagGroup;
    private Integer deptBelong;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getSortway() {
        return sortway;
    }

    public void setSortway(Integer sortway) {
        this.sortway = sortway;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public String[] getTagGroup() {
        return tagGroup;
    }

    public void setTagGroup(String[] tagGroup) {
        this.tagGroup = tagGroup;
    }

    public Integer getDeptBelong() {
        return deptBelong;
    }

    public void setDeptBelong(Integer deptBelong) {
        this.deptBelong = deptBelong;
    }
}
