package mada.android.commons;

import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class YoutubeWebClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        return true;
    }
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.d("Error", "--------------------------sdssss");
        Log.d("Error", error.toString());
    }
}
