package mada.android.tools.ws;

import com.google.gson.annotations.SerializedName;

public class ErrorModel {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
