package com.gupao.dal.dao;

public class Test2 {
    private Integer id;

    private Integer idTest;

    private String testName;

    private String testVr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTest() {
        return idTest;
    }

    public void setIdTest(Integer idTest) {
        this.idTest = idTest;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName == null ? null : testName.trim();
    }

    public String getTestVr() {
        return testVr;
    }

    public void setTestVr(String testVr) {
        this.testVr = testVr == null ? null : testVr.trim();
    }
}