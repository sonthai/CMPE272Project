package camel.rest.domain;


import java.util.List;
import java.util.Map;

public class ResponseMessage {
    private String message;
    private int errorCode;
    private List<Map<String, Object>> dataResponse;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<Map<String, Object>> getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(List<Map<String, Object>> dataResponse) {
        this.dataResponse = dataResponse;
    }
}
