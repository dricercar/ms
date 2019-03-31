package doubleone.mobilesearch.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "返回结果")
public class Payload<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态码")
    private String code = "200";
    @ApiModelProperty("描述性原因")
    private String msg = "OK";
    @ApiModelProperty("业务数据")
    private T payload;

    public Payload() {
    }

    /**
     * @return the payload
     */
    public T getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(T payload) {
        this.payload = payload;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Payload(T payload) {
        this.setPayload(payload);
    }

    public Payload(T payload, String code, String msg) {
        this.setPayload(payload);
        this.setCode(code);
        this.setMsg(msg);
    }

}