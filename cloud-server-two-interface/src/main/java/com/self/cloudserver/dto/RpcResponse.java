package com.self.cloudserver.dto;

public class RpcResponse<T> {

    private String code;

    private String msg;

    private String tip;

    private T data;

    public RpcResponse() {
        super();
    }

    public RpcResponse(String code, String msg, String tip, T data) {
        this.code = code;
        this.msg = msg;
        this.tip = tip;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success(){
        return "0000".equals(code);
    }

    public static RpcResponse ok(){
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setCode("0000");
        return rpcResponse;
    }

    public static <T> RpcResponse<T> ok(T data){
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setCode("0000");
        rpcResponse.setData(data);
        return rpcResponse;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", tip='" + tip + '\'' +
                ", data=" + data +
                '}';
    }

}
