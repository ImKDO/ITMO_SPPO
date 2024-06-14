package org.example;

import java.io.Serializable;

public class Response implements Serializable {
    private final String response;
    private static final long serialVersionUID = 921223352251172958L;


    public Response(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
