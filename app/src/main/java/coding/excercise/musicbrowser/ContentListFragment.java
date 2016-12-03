package coding.excercise.musicbrowser;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import coding.excercise.musicbrowser.adapters.ContentsRecyclerViewAdapter;
import coding.excercise.musicbrowser.models.Content;
import coding.excercise.musicbrowser.utils.ContentBrowserConstants;
import coding.excercise.musicbrowser.utils.ContentSearchUtils;
import coding.excercise.musicbrowser.utils.VolleySingleton;


/**
 * Content List fragment
 */
public class ContentListFragment extends Fragment {

    private static final String ARG_QUERY = "query";
    private static final String ARG_ENTITY = "entity";
    private final String TAG = this.getClass().getSimpleName();

    private String query;
    private String entity;
    private List<Content> contentsList = new ArrayList<>();

    private LinearLayout progressBarLayout;
    private ContentListInteractionListener mActivityInteractor;
    private RecyclerView mRecyclerView;
    private ContentsRecyclerViewAdapter contentsAdapter;

    public ContentListFragment() {
    }

    /**
     * factory method to create new instance method of Content list fragment
     *
     * @param query  search query string
     * @param entity selected entity
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

        View view = inflater.inflate(R.layout.fragment_content_list, container, false);
        progressBarLayout = (LinearLayout) view.findViewById(R.id.progress_bar);
        progressBarLayout.setVisibility(View.VISIBLE);
        progressBarLayout.bringToFront();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.contents_recycler_view);
        if (getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        fetchContentData();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ContentListInteractionListener) {
            mActivityInteractor = (ContentListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ContentListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityInteractor = null;
    }

    public interface ContentListInteractionListener {
        void loadContentDetailsFragment(Content contentItem);
    }

    public void fetchContentData() {

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
                        String contentJsonResponse = response.toString();
                        Log.d("Response json", contentJsonResponse);

                        progressBarLayout.setVisibility(View.GONE);

                        buildRecyclerView(contentJsonResponse);

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

    private void buildRecyclerView(String contentJsonResponse) {

        contentsList = ContentSearchUtils.processJsonResponse(contentJsonResponse);
        if (contentsList == null) {
            return;
        }
        contentsAdapter = new ContentsRecyclerViewAdapter(getContext(), contentsList);
        contentsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Content contentItem) {
                if (mActivityInteractor != null) {
                    mActivityInteractor.loadContentDetailsFragment(contentItem);
                }
            }
        });
        mRecyclerView.setAdapter(contentsAdapter);
    }

    public interface OnItemClickListener {
        void onItemClick(Content contentItem);
    }
}
