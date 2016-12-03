package coding.excercise.musicbrowser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import coding.excercise.musicbrowser.models.Content;


/**
 * Content Details fragment
 */
public class ContentDetailsFragment extends Fragment {

    private static final String ARG_CONTENT = "content_item";

    private Content mContentItem;

    public ContentDetailsFragment() {
    }

    /**
     * factory method to create new instance of Contentdetails fragment
     *
     * @param contentItem
     * @return A new instance of fragment ContentDetailsFragment.
     */
    public static ContentDetailsFragment newInstance(Content contentItem) {
        ContentDetailsFragment fragment = new ContentDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONTENT, contentItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentItem = getArguments().getParcelable(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_details, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail);
        if (mContentItem.getArtworkUrl100() != null) {
            Picasso.with(getContext()).load(mContentItem.getArtworkUrl100())
                    .error(R.drawable.ic_image_not_available)
                    .placeholder(R.drawable.ic_image_not_available)
                    .fit()
                    .into(imageView);
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mContentItem.getTrackName());

        String collectionName = mContentItem.getCollectionName();
        TextView collection = (TextView) view.findViewById(R.id.collection);
        collection.setText(collectionName);

        if(collectionName == null) {
            collection.setVisibility(View.GONE);
        }

        TextView price = (TextView) view.findViewById(R.id.price);
        price.setText(String.format(getResources().getString(R.string.price_text),
                mContentItem.getTrackPrice()));

        return view;
    }

}
