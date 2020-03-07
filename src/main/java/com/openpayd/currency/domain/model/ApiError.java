package com.openpayd.currency.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ApiError implements Serializable {

    private List<String> errors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;


    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return status == apiError.status &&
                Objects.equals(errors, apiError.errors) &&
                Objects.equals(timestamp, apiError.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors, timestamp, status);
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errors=" + errors +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}