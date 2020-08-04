package cn.xhb.simpleDBMS.DTO;

/**
 * @author HaibiaoXu
 * @date Create in 17:30 2020/6/30
 * @modified By
 */
public class Request {
    private String name;
    private String param;
    private String operation;

    public Request() {
    }

    public Request(String name, String param, String operation) {
        this.name = name;
        this.param = param;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
