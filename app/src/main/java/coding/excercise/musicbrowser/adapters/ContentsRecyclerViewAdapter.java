package coding.excercise.musicbrowser.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import coding.excercise.musicbrowser.R;
import coding.excercise.musicbrowser.models.Content;
import coding.excercise.musicbrowser.utils.VolleySingleton;

/**
 * Created by jegan_2 on 12/1/2016.
 */

public class ContentsRecyclerViewAdapter extends RecyclerView.Adapter<ContentsRecyclerViewAdapter.ContentViewHolder> {

    private Context mContext;
    private List<Content> mContentList;
    private ImageLoader mImageLoader;


    public ContentsRecyclerViewAdapter(Context mContext, List<Content> contentList) {
        this.mContext = mContext;
        this.mContentList = contentList;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_row, null);
        ContentViewHolder viewHolder = new ContentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        Content content = mContentList.get(position);
        holder.title.setText(content.getTrackName());
        float price = content.getTrackPrice();
        holder.price.setText("$" + price);

        if(content.getArtworkUrl100() != null) {
            mImageLoader = VolleySingleton.getInstance(mContext).getImageLoader();
            holder.imageView.setImageUrl(content.getArtworkUrl100(), mImageLoader);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mContentList ? mContentList.size() : 0);
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        protected NetworkImageView imageView;
        protected TextView title;
        protected TextView price;
        public ContentViewHolder(View itemView) {
            super(itemView);
            this.imageView = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.price = (TextView) itemView.findViewById(R.id.price);

        }
    }
}
