package mada.android.models.users;

import com.google.gson.annotations.SerializedName;

public class UserToken {
    @SerializedName("user")
    private User user;
    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    public UserToken() {
    }

    public UserToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
