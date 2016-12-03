package coding.excercise.musicbrowser.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.util.List;

import coding.excercise.musicbrowser.models.Content;
import coding.excercise.musicbrowser.models.ContentResponse;

/**
 * Created by jegan_2 on 12/2/2016.
 */

public class ContentSearchUtils {

    public static Boolean isValidSearchQuery(String query) {
        boolean hasNonAlpha = query.matches("^.*[^a-zA-Z0-9 ].*$");
        return !hasNonAlpha;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static List<Content> processJsonResponse(String contentJsonResponse) {
        if (contentJsonResponse == null || contentJsonResponse.trim().equals("")) {
            return null;
        }
        Gson gson = new Gson();
        ContentResponse contentResponse = gson.fromJson(contentJsonResponse, ContentResponse.class);
        return contentResponse.results;
    }
}
