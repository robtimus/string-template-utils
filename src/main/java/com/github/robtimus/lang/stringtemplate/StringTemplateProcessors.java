/*
 * StringTemplateProcessors.java
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

import java.lang.StringTemplate.Processor;
import java.util.Objects;
import java.util.function.Function;

/**
 * Generic implementations of {@link Processor StringTemplate.Processor}.
 *
 * @author Rob Spoor
 */
public final class StringTemplateProcessors {

    private StringTemplateProcessors() {
    }

    /**
     * Returns a string template processor that delegates to {@link StringTemplateUtils#interpolateMapped(StringTemplate, Function)}.
     * That makes the result of this method similar to {@link StringTemplate#STR} but with the provided mapper function applied to each template
     * value.
     * <p>
     * The returned string template processor is immutable if the provided mapper function is a pure function.
     *
     * @param mapper The function to apply to each template value.
     * @return A string template processor that delegates to {@link StringTemplateUtils#interpolateMapped(StringTemplate, Function)}.
     */
    public static StringTemplate.Processor<String, RuntimeException> interpolateMapped(Function<Object, String> mapper) {
        Objects.requireNonNull(mapper);
        return template -> StringTemplateUtils.interpolateMapped(template, mapper);
    }

    /**
     * Returns a string template processor that delegates to {@link StringTemplateUtils#interpolateMappedAsString(StringTemplate, Function)}.
     * That makes the result of this method similar to {@link StringTemplate#STR} but with the provided mapper function applied to each template value
     * after applying {@link String#valueOf(Object)}.
     * <p>
     * The returned string template processor is immutable if the provided mapper function is a pure function.
     *
     * @param mapper The function to apply to each template value.
     * @return A string template processor that delegates to {@link StringTemplateUtils#interpolateMappedAsString(StringTemplate, Function)}.
     */
    public static StringTemplate.Processor<String, RuntimeException> interpolateMappedAsString(Function<? super String, String> mapper) {
        Objects.requireNonNull(mapper);
        return template -> StringTemplateUtils.interpolateMappedAsString(template, mapper);
    }
}
