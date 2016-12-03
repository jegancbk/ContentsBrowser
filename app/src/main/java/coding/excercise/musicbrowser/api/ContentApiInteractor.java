package coding.excercise.musicbrowser.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import coding.excercise.musicbrowser.utils.ContentBrowserConstants;
import coding.excercise.musicbrowser.utils.VolleySingleton;

import static android.content.ContentValues.TAG;

/**
 * Created by jegan_2 on 12/3/2016.
 */

public class ContentApiInteractor {

    private static ContentApiInteractor instance = null;

    public static ContentApiInteractor getInstance() {
        if(instance == null) {
            instance = new ContentApiInteractor();
        }
        return instance;
    }

    protected ContentApiInteractor() {
    }

    public void fetchContentData(Context context, String query, String entity,
                                        final ContentApiResponseListener listener) {
        try {
            query = URLEncoder.encode(query, "UTF-8");
            entity = URLEncoder.encode(entity, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }

        String apiUrl = ContentBrowserConstants.ITUNES_API_URL.replace("[SEARCH_TERM]", query)
                .replace("[ENTITY]", entity);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccessResponse(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailureResponse(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public interface ContentApiResponseListener {
        void onSuccessResponse(JSONObject response);
        void onFailureResponse(VolleyError error);
    }

}
