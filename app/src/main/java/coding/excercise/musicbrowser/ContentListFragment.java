package coding.excercise.musicbrowser;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import coding.excercise.musicbrowser.adapters.ContentsRecyclerViewAdapter;
import coding.excercise.musicbrowser.models.Content;
import coding.excercise.musicbrowser.models.ContentResponse;
import coding.excercise.musicbrowser.utils.ContentBrowserConstants;
import coding.excercise.musicbrowser.utils.VolleySingleton;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContentListInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUERY = "query";
    private static final String ARG_ENTITY = "entity";
    private final String TAG = this.getClass().getSimpleName();

    private String query;
    private String entity;
    private List<Content> contentsList = new ArrayList<>();

    private LinearLayout progressBarLayout;
    private ContentListInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ContentsRecyclerViewAdapter contentsAdapter;
    public ContentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param query Parameter 1.
     * @param entity Parameter 2.
     * @return A new instance of fragment ContentListFragment.
     */
    public static ContentListFragment newInstance(String query, String entity) {
        ContentListFragment fragment = new ContentListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        args.putString(ARG_ENTITY, entity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(ARG_QUERY);
            entity = getArguments().getString(ARG_ENTITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content_list, container, false);
        progressBarLayout = (LinearLayout) view.findViewById(R.id.progress_bar);
        progressBarLayout.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.contents_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchContentData();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContentListInteractionListener) {
            mListener = (ContentListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ContentListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ContentListInteractionListener {
        void fetchContentList(Uri uri);
    }

    public void fetchContentData(){

        try {
            query = URLEncoder.encode(query, "UTF-8");
            entity = URLEncoder.encode(entity, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG,e.getMessage());
        }

        String apiUrl = ContentBrowserConstants.ITUNES_API_URL.replace("[SEARCH_TERM]", query)
                .replace("[ENTITY]", entity);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String contentJsonResponse = response.toString();
                        Log.d("Response json", contentJsonResponse);

                        progressBarLayout.setVisibility(View.GONE);

                        Gson gson = new Gson();
                        ContentResponse contentResponse = gson.fromJson(contentJsonResponse, ContentResponse.class);
                        contentsList = contentResponse.results;

                        contentsAdapter = new ContentsRecyclerViewAdapter(getContext(), contentsList);
                        mRecyclerView.setAdapter(contentsAdapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("Api error response", error.getMessage());
                        progressBarLayout.setVisibility(View.GONE);
                    }
                });

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
    }
}
