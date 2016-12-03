package coding.excercise.musicbrowser;

import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import coding.excercise.musicbrowser.models.Content;

import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Created by jegan_2 on 12/3/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ContentDetailsTest extends TestCase {

    private SearchActivity activity;
    private Content contentModel;
    private ContentDetailsFragment contentDetailsFragment;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(SearchActivity.class);
        contentModel = ServiceUtils.getContentModel();
        contentDetailsFragment = ContentDetailsFragment.newInstance(contentModel);
        startFragment(contentDetailsFragment, SearchActivity.class);
    }

    @Test
    public void startContentDetailsFragment_validateFragmentLoad() {
        FragmentActivity contentActivity = contentDetailsFragment.getActivity();
        assertEquals("Content details Fragment Launch validation failed",
                contentActivity instanceof SearchActivity, true);
    }

    @Test
    public void startContentDetailsFragment_shouldMatchContentTitle() {

        TextView title = (TextView) contentDetailsFragment.getView().findViewById(R.id.title);

        assertEquals("Validation of Content details fragment failed", title.getText().toString(),
                contentModel.getTrackName());
    }

    @Test
    public void startContentDetailsFragment_shouldMatchContentPrice() {

        TextView title = (TextView) contentDetailsFragment.getView().findViewById(R.id.price);

        assertEquals("Validation of Content details fragment failed", title.getText().toString(),
                "$" + contentModel.getTrackPrice());
    }
}
