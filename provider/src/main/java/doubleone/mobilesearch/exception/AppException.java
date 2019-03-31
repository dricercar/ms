package doubleone.mobilesearch.exception;
public class AppException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;

    public AppException(){
    }
    public AppException(String code, String msg) {
        super(msg);
        this.setCode(code);
        this.setMsg(msg);
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

}