package coding.excercise.musicbrowser.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import coding.excercise.musicbrowser.ContentListFragment;
import coding.excercise.musicbrowser.R;
import coding.excercise.musicbrowser.models.Content;

/**
 * Created by jegan_2 on 12/1/2016.
 */

public class ContentsRecyclerViewAdapter extends
        RecyclerView.Adapter<ContentsRecyclerViewAdapter.ContentViewHolder> {

    private Context mContext;
    private List<Content> mContentList;

    private ContentListFragment.OnItemClickListener onItemClickListener;

    public ContentListFragment.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(ContentListFragment.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

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
        final Content content = mContentList.get(position);
        holder.title.setText(content.getTrackName());
        float price = content.getTrackPrice();
        holder.price.setText(String.format(mContext.getResources().getString(R.string.price_text),
                price));

        if (content.getArtworkUrl100() != null) {
            Picasso.with(mContext).load(content.getArtworkUrl100())
                    .error(R.drawable.ic_image_not_available)
                    .placeholder(R.drawable.ic_image_not_available)
                    .fit()
                    .into(holder.imageView);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(content);
            }
        };

        holder.imageView.setOnClickListener(listener);
        holder.title.setOnClickListener(listener);
        holder.price.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != mContentList ? mContentList.size() : 0);
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView title;
        protected TextView price;

        public ContentViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.price = (TextView) itemView.findViewById(R.id.price);

        }
    }
}
