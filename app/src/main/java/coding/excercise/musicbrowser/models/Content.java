package coding.excercise.musicbrowser.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jegan_2 on 12/1/2016.
 */

public class Content implements Parcelable {

    private String trackName;
    private String trackCensoredName;
    private String artworkUrl100;
    private String collectionName;
    private String collectionCensoredName;
    private float trackPrice;

    protected Content(Parcel in) {
        trackName = in.readString();
        trackCensoredName = in.readString();
        artworkUrl100 = in.readString();
        collectionName = in.readString();
        collectionCensoredName = in.readString();
        trackPrice = in.readInt();
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    public Content() {

    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackCensoredName() {
        return trackCensoredName;
    }

    public void setTrackCensoredName(String trackCensoredName) {
        this.trackCensoredName = trackCensoredName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionCensoredName() {
        return collectionCensoredName;
    }

    public void setCollectionCensoredName(String collectionCensoredName) {
        this.collectionCensoredName = collectionCensoredName;
    }

    public float getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(int trackPrice) {
        this.trackPrice = trackPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(trackName);
        parcel.writeString(trackCensoredName);
        parcel.writeString(artworkUrl100);
        parcel.writeString(collectionName);
        parcel.writeString(collectionCensoredName);
        parcel.writeFloat(trackPrice);
    }
}
