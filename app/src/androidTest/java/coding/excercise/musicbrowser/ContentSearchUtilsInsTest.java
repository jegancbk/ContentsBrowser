package coding.excercise.musicbrowser;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import coding.excercise.musicbrowser.utils.ContentSearchUtils;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jegan_2 on 12/2/2016.
 */

public class ContentSearchUtilsInsTest {

    @Test
    public void testNetworkConnection() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Boolean actual = ContentSearchUtils.isNetworkAvailable(appContext);
        Boolean expected = true;

        assertEquals("Internet connection", expected, actual);
    }
}
