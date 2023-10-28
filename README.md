# string-template-utils
[![Maven Central](https://img.shields.io/maven-central/v/com.github.robtimus/string-template-utils)](https://search.maven.org/artifact/com.github.robtimus/string-template-utils)
[![Build Status](https://github.com/robtimus/string-template-utils/actions/workflows/build.yml/badge.svg)](https://github.com/robtimus/string-template-utils/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.robtimus%3Astring-template-utils&metric=alert_status)](https://sonarcloud.io/summary/overall?id=com.github.robtimus%3Astring-template-utils)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.robtimus%3Astring-template-utils&metric=coverage)](https://sonarcloud.io/summary/overall?id=com.github.robtimus%3Astring-template-utils)
[![Known Vulnerabilities](https://snyk.io/test/github/robtimus/string-template-utils/badge.svg)](https://snyk.io/test/github/robtimus/string-template-utils)

A set of utility classes for working with string templates.

## StringTemplateProcessors

Class `StringTemplateProcessors` can be used to easily construct string template processors that work like the default `STR` template processor, except they apply a function to the template values.

For instance, using [Apache Commons Text](https://commons.apache.org/proper/commons-text/):

```java
StringTemplate.Processor<String, RuntimeException> JSON = StringTemplateProcessors
        .interpolateMapped(o -> StringEscapeUtils.ESCAPE_JSON.translate(String.valueOf(o)));
String json = JSON."""
    {
      "id": \{id},
      "name": "\{name}"
    }
    """;
```

Or equivalent, to prevent having to call `String.valueOf` explicitly:

```java
StringTemplate.Processor<String, RuntimeException> JSON = StringTemplateProcessors
        .interpolateMappedAsString(s -> StringEscapeUtils.ESCAPE_JSON.translate(s));
String json = JSON."""
    {
      "id": \{id},
      "name": "\{name}"
    }
    """;
```

In both cases, the `name` property will have a properly escaped value.

## URLEncoderProcessor

Class `URLEncoderProcessor` is a string template processor that uses [URLEncoder](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/net/URLEncoder.html) for encoding template values. This makes it safe to use to create URIs. For example, using a static import:

```java
URI uri = encodeAsURI()."https://host/path/\{id}?url=\{url}";
```

If `id` is `id/123` and `url` is `https://example.org?q=1`, the result will be `https://host/path/id%2F123?url=https%3A%2F%2Fexample.org%3Fq%3D1`.
