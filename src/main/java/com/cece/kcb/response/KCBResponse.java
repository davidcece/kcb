package com.cece.kcb.response;

import lombok.Data;

@Data
public class KCBResponse<T> {
    private boolean success;
    private String message;
    private T data;


    public KCBResponse(T data){
        this.success = true;
        this.message = "Success";
        this.data = data;
    }

    public KCBResponse(String message){
        this.success = true;
        this.message = message;
        this.data = null;
    }

    public KCBResponse(Exception e){
        this.success = false;
        this.message = "Error: " + (e.getMessage() != null ? e.getMessage() : "Unknown error");
        this.data = null;
    }
}
