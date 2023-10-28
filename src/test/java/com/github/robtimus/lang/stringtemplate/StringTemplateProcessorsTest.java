/*
 * StringTemplateProcessorsTest.java
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

package com.github.robtimus.lang.stringtemplate;

import static com.github.robtimus.lang.stringtemplate.StringTemplateProcessors.interpolateMapped;
import static com.github.robtimus.lang.stringtemplate.StringTemplateProcessors.interpolateMappedAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
class StringTemplateProcessorsTest {

    @Test
    void testInterpolateMapped() {
        StringTemplate.Processor<String, RuntimeException> processor = interpolateMapped(o -> o.toString().toUpperCase());

        testToUpperCase(processor);
    }

    @Test
    void testInterpolateMappedAsString() {
        StringTemplate.Processor<String, RuntimeException> processor = interpolateMappedAsString(String::toUpperCase);

        testToUpperCase(processor);
    }

    private void testToUpperCase(StringTemplate.Processor<String, RuntimeException> processor) {
        String name = "robtimus";
        int x = 13;

        // Both Eclipse and Checkstyle don't properly support string template yet, so use the factory method to create the template
        //String result = processor."name: \{name}, x: \{x}";
        String result = processor.process(StringTemplate.of(List.of("name: ", ", x: ", ""), List.of(name, x)));

        String expected = "name: ROBTIMUS, x: 13";

        assertEquals(expected, result);
    }
}
