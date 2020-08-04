package cn.xhb.simpleDBMS.DTO;

/**
 * @author HaibiaoXu
 * @date Create in 17:30 2020/6/30
 * @modified By
 */
public class Response<T> {
    private String name;
    private T data;
    private String msg;
    private String operation;

    public Response() {
        this.name = null;
        this.data = null;
        this.msg = null;
        this.operation = null;
    }

    public Response(String name, T data, String msg, String operation) {
        this.name = name;
        this.data = data;
        this.msg = msg;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
