# Introduction

This project contains multiple examples using the jackson annotations for parsing java objects to json.

Each example contains the definition of the java class and a test where the object is converted to json and vice versa. 

# Examples

## Simple

Simple example with no annotations.

Jackson needs an empty constructor.

The methods (getters, setters, etc) and the empty constructor used by jackson can have any visibility (private, package, protected and public).

## One-way

Example that shows how to define a one way serialization.

## Ignore

Example that shows how to ignore a property during.

## Rename

Example that shows how to rename a property during serialization, deserialization or both.

**IMPORTANT** The annotation `@JsonGetter` allow us to rename a field for the serialization, it requires a `@JsonSetter` for the same field, otherwise the name is changed in bot, serialization and deserialization.

## Cyclic

Example that shows how to avoid cyclic serialization issues.

## Cyclic with owner

Example that shows how to avoid cyclic serialization issues when we are serializing/deserializing a set of objects with an owner part.

## Dynamic attributes

Example that shows how to serialize and deserialize dynamic attributes. 

## Raw value

Example that shows how to serialize objects that contains json attributes in `String` fields.

**IMPORTANT** The annotation `@JsonRawValue` is only for serialization (object to json), if we need the opposite we can use the `@JsonProperty` in a setter method.

## Root

Example that shows how to add another level in the json with the name of the root class when serializing.

**IMPORTANT** To achieve this functionality, we need to enable the options in te `ObjectMapper` instance (`WRAP_ROOT_VALUE` and `UNWRAP_ROOT_VALUE`).

## Polymorphism

Example that shows how to serialize and deserialize objects with multiple levels of polymorphism.

Multiple fields for type are not supported.

**IMPORTANT** Whe need to define the type of the parent class in both places when multiple levels are defined. 

## Flat

Example that shows how to serialize and deserialize objects with nested objects to flat json.

**IMPORTANT** The prefix and suffix don't do any capitalization to the names.

## Java Time

Example that shows how to serialize and deserialize the classes of the `java.time` library, it shows how to use the default formats and custom formats.

**IMPORTANT** We need to register the `JavaTimeModule` in the `ObjectMapper` instance. Additionally, we need to disable the property `WRITE_DATES_AS_TIMESTAMPS` to disable the conversion of the objects to milliseconds.

## Joda Time

Example that shows how to serialize and deserialize the classes of the `joda.time` library, it shows how to use the default formats and custom formats.

**IMPORTANT** We need to register the `JodaTimeModule` in the `ObjectMapper` instance. Additionally, we need to disable the property `WRITE_DATES_AS_TIMESTAMPS` to disable the conversion of the objects to milliseconds.

**VERY IMPORTANT** Note that from Java SE 8 onwards, users are asked to migrate to `java.time` (JSR-310) - a core part of the JDK which replaces [Joda-Time](http://www.joda.org/joda-time/)