package org.mybatis.generator.custom.config;

/**
 * @author lzj
 * @date 2018/1/17
 */
public class SearchCondition {

    //表字段名
    private String column;
    //java字段名
    private String property;
    //操作符
    private String operation;
    //AND | OR
    private String isAnd;
    //值1
    private String value;
    //值2 , between时使用
    private String secondValue;


    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
    }

    public String getIsAnd() {
        return isAnd;
    }

    public void setIsAnd(String isAnd) {
        this.isAnd = isAnd;
    }
}
