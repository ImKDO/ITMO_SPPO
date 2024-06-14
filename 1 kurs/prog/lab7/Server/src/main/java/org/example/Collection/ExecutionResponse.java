package org.example.Collection;


public class ExecutionResponse {
    private boolean exitCode;
    private String maessage;
    private String responseObj;

    public ExecutionResponse(boolean code, String s) {
        exitCode = code;
        maessage = s;
    }

    public ExecutionResponse(String s) {
        this(true, s);
    }


    public boolean getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return maessage;
    }

    public String toString() {
        return String.valueOf(exitCode) + ";" + maessage + ";" +
                (responseObj == null ? "null" : responseObj);
    }
}
