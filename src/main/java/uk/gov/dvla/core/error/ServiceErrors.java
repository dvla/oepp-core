package uk.gov.dvla.core.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ServiceErrors implements ApplicationError {
    SERVICE_ERROR("OE-500-01", "General Service Error");

    private String code;
    private String message;

    ServiceErrors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static ServiceErrors fromJson(JsonNode json) {
        for (ServiceErrors value : values()) {
            if (Objects.equals(value.getCode(), json.get("code").textValue())
                    && Objects.equals(value.getMessage(), json.get("message").textValue())) {
                return value;
            }
        }
        return null;
    }
}
