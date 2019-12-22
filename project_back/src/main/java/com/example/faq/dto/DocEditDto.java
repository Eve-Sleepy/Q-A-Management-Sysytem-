package com.example.faq.dto;

import org.springframework.stereotype.Service;

@Service
public class DocEditDto {
    private Integer docId;
    private String title;
    private Integer productId;
    private String edition;
    private Integer deptBelong;
    private String[] tagGroup;
    private String content;
    private Integer operation;
    private String operationTime;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Integer getDeptBelong() {
        return deptBelong;
    }

    public void setDeptBelong(Integer deptBelong) {
        this.deptBelong = deptBelong;
    }

    public String[] getTagGroup() {
        return tagGroup;
    }

    public void setTagGroup(String[] tagGroup) {
        this.tagGroup = tagGroup;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }
}
