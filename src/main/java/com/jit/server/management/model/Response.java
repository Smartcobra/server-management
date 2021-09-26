package com.jit.server.management.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.LocalDateTime;
import java.util.Map;

//@SuperBuilder
@JsonInclude(NON_NULL)
public class Response {
    private final LocalDateTime timeStamp;
    private final int statusCode;
    private final HttpStatus status;
    private final String reason; //optional
    private final String message;
    private final String developerMessage;  //optional
    private final Map<?, ?> data;


    public Response(ResponseBuilder builder) {
        this.timeStamp = builder.timeStamp;
        this.statusCode = builder.statusCode;
        this.status = builder.status;
        this.reason = builder.reason;
        this.message = builder.message;
        this.developerMessage = builder.developerMessage;
        this.data = builder.data;
    }


    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public Map<?, ?> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "timeStamp=" + timeStamp +
                ", statusCode=" + statusCode +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", message='" + message + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ResponseBuilder {
        private final LocalDateTime timeStamp;
        private final int statusCode;
        private final HttpStatus status;
        private final String message;
        private final Map<?, ?> data;
        private String developerMessage;
        private String reason;

        public ResponseBuilder(LocalDateTime timeStamp, int statusCode, HttpStatus status, String message, Map<?, ?> data) {
            this.timeStamp = timeStamp;
            this.statusCode = statusCode;
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public ResponseBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResponseBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Response build() {
            Response response =  new Response(this);
            return response;
        }
    }
}
