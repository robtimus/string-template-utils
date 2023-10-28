/*
 * StringTemplateUtils.java
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

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * A utility class for string templates.
 *
 * @author Rob Spoor
 */
public final class StringTemplateUtils {

    private static final Function<Object, String> TO_STRING_MAPPER = String::valueOf;

    private StringTemplateUtils() {
    }

    /**
     * Returns the string interpolation of the fragments and values for a {@link StringTemplate}.
     * This method is similar to {@link StringTemplate#interpolate()}, except it doesn't use {@link String#valueOf(Object)} but instead the provided
     * mapper function.
     *
     * @param template The string template to interpolate.
     * @param mapper The function to apply to each template value.
     * @return The string interpolation of the given string template.
     * @throws NullPointerException If the given string template or function is {@code null}.
     */
    public static String interpolateMapped(StringTemplate template, Function<Object, String> mapper) {
        Objects.requireNonNull(template);
        Objects.requireNonNull(mapper);

        List<String> fragments = template.fragments();
        List<Object> values = template.values();

        String[] strings = new String[fragments.size() + values.size()];
        int index = 0;
        Iterator<String> fragmentsIterator = fragments.iterator();
        Iterator<Object> valuesIterator = values.iterator();
        while (valuesIterator.hasNext()) {
            strings[index++] = fragmentsIterator.next();
            strings[index++] = mapper.apply(valuesIterator.next());
        }
        strings[index] = fragmentsIterator.next();

        return String.join("", strings); //$NON-NLS-1$
    }

    /**
     * Returns the string interpolation of the fragments and values for a {@link StringTemplate}.
     * This method is a specialized case of {@link #interpolateMapped(StringTemplate, Function)} where each template value is first turned into a
     * string using {@link String#valueOf(Object)}, and then the provided mapper function is applied. That makes it easier to use functions that take
     * {@link String} as argument.
     * <p>
     * This method also similar to {@link StringTemplate#interpolate()}, except it applies the provided mapper function to each template value after
     * applying {@link String#valueOf(Object)}.
     *
     * @param template The string template to interpolate.
     * @param mapper The function to apply to each template value.
     * @return The string interpolation of the given string template.
     * @throws NullPointerException If the given string template or function is {@code null}.
     */
    public static String interpolateMappedAsString(StringTemplate template, Function<? super String, String> mapper) {
        return interpolateMapped(template, TO_STRING_MAPPER.andThen(mapper));
    }
}
