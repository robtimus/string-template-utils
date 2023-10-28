/*
 * URLEncoderProcessor.java
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

import java.lang.StringTemplate.Processor;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Function;
import com.github.robtimus.lang.stringtemplate.StringTemplateProcessors;

/**
 * An implementation of {@link Processor StringTemplate.Processor} that applies {@link URLEncoder#encode(String, Charset)} to template values.
 *
 * @author Rob Spoor
 * @param <T> The process result type.
 */
public final class URLEncoderProcessor<T> implements StringTemplate.Processor<T, RuntimeException> {

    private final StringTemplate.Processor<String, RuntimeException> delegate;
    private final Function<String, T> finisher;

    private URLEncoderProcessor(Charset charset, Function<String, T> finisher) {
        this.delegate = StringTemplateProcessors.interpolateMappedAsString(s -> URLEncoder.encode(s, charset));
        this.finisher = finisher;
    }

    @Override
    public T process(StringTemplate stringTemplate) throws RuntimeException {
        String result = delegate.process(stringTemplate);
        return finisher.apply(result);
    }

    /**
     * Returns a string template processor that encodes each template value using the UTF-8 charset and returns the result as a {@link String}.
     * <p>
     * The returned string template processor is immutable.
     *
     * @return A string template processor that encodes each template value using the given charset
     */
    public static URLEncoderProcessor<String> encodeAsString() {
        return encodeAsString(StandardCharsets.UTF_8);
    }

    /**
     * Returns a string template processor that encodes each template value using a specific charset and returns the result as a {@link String}.
     * <p>
     * The returned string template processor is immutable.
     *
     * @param charset The charset to use for encoding.
     * @return A string template processor that encodes each template value using the given charset
     * @throws NullPointerException If the given charset is {@code null}.
     */
    public static URLEncoderProcessor<String> encodeAsString(Charset charset) {
        Objects.requireNonNull(charset);
        return new URLEncoderProcessor<>(charset, Function.identity());
    }

    /**
     * Returns a string template processor that encodes each template value using the UTF-8 charset and returns the result as a {@link URI}.
     * <p>
     * The returned string template processor is immutable.
     *
     * @return A string template processor that encodes each template value using the given charset
     * @see #encodeAsString()
     * @see URI#create(String)
     */
    public static URLEncoderProcessor<URI> encodeAsURI() {
        return encodeAsURI(StandardCharsets.UTF_8);
    }

    /**
     * Returns a string template processor that encodes each template value using a specific charset and returns the result as a {@link URI}.
     * <p>
     * The returned string template processor is immutable.
     *
     * @param charset The charset to use for encoding.
     * @return A string template processor that encodes each template value using the given charset
     * @throws NullPointerException If the given charset is {@code null}.
     * @see #encodeAsString(Charset)
     * @see URI#create(String)
     */
    public static URLEncoderProcessor<URI> encodeAsURI(Charset charset) {
        Objects.requireNonNull(charset);
        return new URLEncoderProcessor<>(charset, URI::create);
    }
}
