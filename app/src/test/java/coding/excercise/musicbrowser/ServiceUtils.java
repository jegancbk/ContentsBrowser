package coding.excercise.musicbrowser;

import java.util.List;

import coding.excercise.musicbrowser.models.Content;
import coding.excercise.musicbrowser.utils.ContentSearchUtils;

/**
 * Created by jegan_2 on 12/3/2016.
 */

class ServiceUtils {

    static final String test_payload = "{\n" +
            "\t\"resultCount\": 50,\n" +
            "\t\"results\": [{\n" +
            "\t\t\"wrapperType\": \"track\",\n" +
            "\t\t\"kind\": \"song\",\n" +
            "\t\t\"artistId\": 909253,\n" +
            "\t\t\"collectionId\": 879273552,\n" +
            "\t\t\"trackId\": 879273565,\n" +
            "\t\t\"artistName\": \"Jack Johnson\",\n" +
            "\t\t\"collectionName\": \"In Between Dreams\",\n" +
            "\t\t\"trackName\": \"Better Together\",\n" +
            "\t\t\"collectionCensoredName\": \"In Between Dreams\",\n" +
            "\t\t\"trackCensoredName\": \"Better Together\",\n" +
            "\t\t\"artistViewUrl\": \"https://itunes.apple.com/us/artist/jack-johnson/id909253?uo=4\",\n" +
            "\t\t\"collectionViewUrl\": \"https://itunes.apple.com/us/album/better-together/id879273552?i=879273565&uo=4\",\n" +
            "\t\t\"trackViewUrl\": \"https://itunes.apple.com/us/album/better-together/id879273552?i=879273565&uo=4\",\n" +
            "\t\t\"previewUrl\": \"http://a25.phobos.apple.com/us/r30/Music6/v4/13/22/67/1322678b-e40d-fb4d-8d9b-3268fe03b000/mzaf_8818596367816221008.plus.aac.p.m4a\",\n" +
            "\t\t\"artworkUrl30\": \"http://is3.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/30x30bb.jpg\",\n" +
            "\t\t\"artworkUrl60\": \"http://is3.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/60x60bb.jpg\",\n" +
            "\t\t\"artworkUrl100\": \"http://is3.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg\",\n" +
            "\t\t\"collectionPrice\": 10.99,\n" +
            "\t\t\"trackPrice\": 1.29,\n" +
            "\t\t\"releaseDate\": \"2005-03-01T08:00:00Z\",\n" +
            "\t\t\"collectionExplicitness\": \"notExplicit\",\n" +
            "\t\t\"trackExplicitness\": \"notExplicit\",\n" +
            "\t\t\"discCount\": 1,\n" +
            "\t\t\"discNumber\": 1,\n" +
            "\t\t\"trackCount\": 15,\n" +
            "\t\t\"trackNumber\": 1,\n" +
            "\t\t\"trackTimeMillis\": 207679,\n" +
            "\t\t\"country\": \"USA\",\n" +
            "\t\t\"currency\": \"USD\",\n" +
            "\t\t\"primaryGenreName\": \"Rock\",\n" +
            "\t\t\"isStreamable\": true\n" +
            "\t}, {\n" +
            "\t\t\"wrapperType\": \"track\",\n" +
            "\t\t\"kind\": \"song\",\n" +
            "\t\t\"artistId\": 909253,\n" +
            "\t\t\"collectionId\": 879273552,\n" +
            "\t\t\"trackId\": 879273569,\n" +
            "\t\t\"artistName\": \"Jack Johnson\",\n" +
            "\t\t\"collectionName\": \"In Between Dreams\",\n" +
            "\t\t\"trackName\": \"Banana Pancakes\",\n" +
            "\t\t\"collectionCensoredName\": \"In Between Dreams\",\n" +
            "\t\t\"trackCensoredName\": \"Banana Pancakes\",\n" +
            "\t\t\"artistViewUrl\": \"https://itunes.apple.com/us/artist/jack-johnson/id909253?uo=4\",\n" +
            "\t\t\"collectionViewUrl\": \"https://itunes.apple.com/us/album/banana-pancakes/id879273552?i=879273569&uo=4\",\n" +
            "\t\t\"trackViewUrl\": \"https://itunes.apple.com/us/album/banana-pancakes/id879273552?i=879273569&uo=4\",\n" +
            "\t\t\"previewUrl\": \"http://a1762.phobos.apple.com/us/r30/Music/v4/08/d9/c5/08d9c56d-73e5-be1c-1eda-071a48284440/mzaf_8565025008024189274.plus.aac.p.m4a\",\n" +
            "\t\t\"artworkUrl30\": \"http://is3.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/30x30bb.jpg\",\n" +
            "\t\t\"artworkUrl60\": \"http://is3.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/60x60bb.jpg\",\n" +
            "\t\t\"artworkUrl100\": \"http://is3.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg\",\n" +
            "\t\t\"collectionPrice\": 10.99,\n" +
            "\t\t\"trackPrice\": 1.29,\n" +
            "\t\t\"releaseDate\": \"2005-03-01T08:00:00Z\",\n" +
            "\t\t\"collectionExplicitness\": \"notExplicit\",\n" +
            "\t\t\"trackExplicitness\": \"notExplicit\",\n" +
            "\t\t\"discCount\": 1,\n" +
            "\t\t\"discNumber\": 1,\n" +
            "\t\t\"trackCount\": 15,\n" +
            "\t\t\"trackNumber\": 3,\n" +
            "\t\t\"trackTimeMillis\": 191854,\n" +
            "\t\t\"country\": \"USA\",\n" +
            "\t\t\"currency\": \"USD\",\n" +
            "\t\t\"primaryGenreName\": \"Rock\",\n" +
            "\t\t\"isStreamable\": true\n" +
            "\t}]\n" +
            "}";

    static Content getContentModel() {
        List<Content> contentList = ContentSearchUtils.processJsonResponse(ServiceUtils.test_payload);
        return contentList.get(0);
    }
}
