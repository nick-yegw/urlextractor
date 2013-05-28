import static junit.framework.Assert.*;
import org.junit.Test;

import java.net.URL;

public class TestExtractor {

    @Test
    public void testGetURLEncode() throws Exception {
        URL testURL = new URL("http://www.163.com");
        String encode = Extractor.getURLEncode(testURL);
        assertNotNull(encode);
        assertTrue(!encode.equals(""));
    }
}
