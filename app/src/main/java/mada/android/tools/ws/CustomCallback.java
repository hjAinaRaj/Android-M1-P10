package mada.android.tools.ws;

import android.view.View;

public interface CustomCallback <T>{
    public void callback(T data, View v) throws Exception;
}
