/*
 * URLEncoderProcessorTest.java
 * Copyright 2023 Rob Spoor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.robtimus.lang.stringtemplate.net;

import static com.github.robtimus.lang.stringtemplate.net.URLEncoderProcessor.encodeAsString;
import static com.github.robtimus.lang.stringtemplate.net.URLEncoderProcessor.encodeAsURI;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
class URLEncoderProcessorTest {

    @Test
    void testEncodeAsUTF8String() {
        String id = "id/123";
        String url = "https://example.org?q=1";

        String result = encodeAsString().process(urlTemplate(id, url));

        String expected = "https://host/path/" + encode(id, UTF_8) + "?url=" + encode(url, UTF_8);

        assertEquals(expected, result);
        assertThat(result, not(containsString(id)));
        assertThat(result, not(containsString(url)));
    }

    @Test
    void testEncodeAsAsciiString() {
        String id = "id/123";
        String url = "https://example.org?q=1";

        String result = encodeAsString(US_ASCII).process(urlTemplate(id, url));

        String expected = "https://host/path/" + encode(id, US_ASCII) + "?url=" + encode(url, US_ASCII);

        assertEquals(expected, result);
        assertThat(result, not(containsString(id)));
        assertThat(result, not(containsString(url)));
    }

    @Test
    void testEncodeAsUTF8URI() {
        String id = "id/123";
        String url = "https://example.org?q=1";

        URI result = encodeAsURI().process(urlTemplate(id, url));

        URI expected = URI.create("https://host/path/" + encode(id, UTF_8) + "?url=" + encode(url, UTF_8));

        assertEquals(expected, result);
        assertThat(result.getRawPath(), not(containsString(id)));
        assertThat(result.getRawQuery(), not(containsString(url)));
        assertThat(result.getRawQuery(), not(containsString("?")));
    }

    @Test
    void testEncodeAsAsciiURI() {
        String id = "id/123";
        String url = "https://example.org?q=1";

        URI result = encodeAsURI(US_ASCII).process(urlTemplate(id, url));

        URI expected = URI.create("https://host/path/" + encode(id, US_ASCII) + "?url=" + encode(url, US_ASCII));

        assertEquals(expected, result);
        assertThat(result.getRawPath(), not(containsString(id)));
        assertThat(result.getRawQuery(), not(containsString(url)));
        assertThat(result.getRawQuery(), not(containsString("?")));
    }

    private StringTemplate urlTemplate(String id, String url) {
        // Both Eclipse and Checkstyle don't properly support string template yet, so use the factory method to create the template
        //return StringTemplate.RAW."https://host/path/\{id}?url=\{url}";
        return StringTemplate.of(List.of("https://host/path/", "?url=", ""), List.of(id, url));
    }
}
