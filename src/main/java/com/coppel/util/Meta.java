package com.coppel.util;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Meta
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Meta {

    private String status;

    private int statusCode;

    private String timestamp;

    @JsonInclude(value = Include.NON_NULL)
    private String devMessage;

    @JsonInclude(value = Include.NON_NULL)
    private String message;

    public Meta(String transactionID, String status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now().toString();
    }

    public Meta(String status, int statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

}
