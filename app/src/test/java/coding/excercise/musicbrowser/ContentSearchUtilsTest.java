package coding.excercise.musicbrowser;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.List;

import coding.excercise.musicbrowser.models.Content;
import coding.excercise.musicbrowser.utils.ContentSearchUtils;

/**
 * Created by jegan_2 on 12/2/2016.
 */

public class ContentSearchUtilsTest extends TestCase {

    @Test
    public void testValidateSearchQueryWithSpecialCharacter() {
        Boolean actual = ContentSearchUtils.isValidSearchQuery("sadf!lsdf");
        Boolean expected = false;

        assertEquals("Validate search query with a special character failed", expected, actual);

    }

    @Test
    public void testValidateSearchQueryWithValidValue() {
        Boolean actual = ContentSearchUtils.isValidSearchQuery("Jack Johnson");
        Boolean expected = true;

        assertEquals("Validate search query with actual value failed", expected, actual);

    }

    @Test
    public void testValidPayLoad() {
        List<Content> contentList = ContentSearchUtils.processJsonResponse(ServiceUtils.test_payload);
        String actual = contentList.get(0).getTrackName();
        String expected = "Better Together";
        assertEquals("Validate valid payload failed", expected, actual);
    }

    @Test
    public void testInvalidPayLoad2() {
        List<Content> contentList = ContentSearchUtils.processJsonResponse(" ");

        String expected = null;
        assertEquals("Validate valid payload failed", expected, contentList);
    }
}
