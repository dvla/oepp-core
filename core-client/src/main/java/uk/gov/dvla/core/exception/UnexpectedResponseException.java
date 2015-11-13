package uk.gov.dvla.core.exception;

public class UnexpectedResponseException extends RuntimeException {

    private Integer responseStatus;
    private String responseBody;

    public UnexpectedResponseException(Integer responseStatus, String responseBody, Throwable cause) {
        super(cause);
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
