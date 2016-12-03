package coding.excercise.musicbrowser;

import android.support.v4.app.FragmentActivity;

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
public class ContentListTest extends TestCase {
    private SearchActivity activity;
    private Content contentModel;
    private ContentListFragment contentListFragment;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(SearchActivity.class);
        contentListFragment = ContentListFragment.newInstance("Jack Johnson", "music");
        startFragment(contentListFragment, SearchActivity.class);
    }

    @Test
    public void startContentListFragment_validateFragmentLoad() {
        FragmentActivity contentActivity = contentListFragment.getActivity();
        assertEquals("Content List Fragment Launch validation failed",
                contentActivity instanceof SearchActivity, true);
        assertNotNull("Content List Fragment Launch validation with view failed",
                contentListFragment.getView().findViewById(R.id.contents_recycler_view));
    }


}
