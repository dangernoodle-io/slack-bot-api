package io.dangernoodle.slack.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ObjectSidekick<T>
{
    private final String className;
    private final T object;
    private Map<String, Function<T, Object>> properties;

    public ObjectSidekick(T object)
    {
        this.object = object;
        this.className = object.getClass().getSimpleName();

        // ordered...
        this.properties = new LinkedHashMap<>();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || object.getClass() != obj.getClass())
        {
            return false;
        }

        @SuppressWarnings("unchecked")
        T other = (T) obj;
        return properties.values()
                         .stream()
                         .map(property -> areEqual(property, object, other))
                         .allMatch(x -> x == true);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(properties.values().stream().toArray(Object[]::new));
    }

    @Override
    public String toString()
    {
        return toString(Collections.emptyMap());
    }

    public String toString(Map<String, Function<T, Object>> additional)
    {
        Map<String, Function<T, Object>> copy = new LinkedHashMap<>(properties);
        copy.putAll(additional);

        return copy.entrySet()
                   .stream()
                   .map(this::buildPropertyToString)
                   .collect(Collectors.joining(", ", String.format("%s [", className), "]"));
    }

    public ObjectSidekick<T> with(String name, Function<T, Object> property)
    {
        properties.put(name, property);
        return this;
    }

    private boolean areEqual(Function<T, Object> function, T obj1, T obj2)
    {
        return Objects.equals(function.apply(obj1), function.apply(obj2));
    }

    private String buildPropertyToString(Map.Entry<String, Function<T, Object>> entry)
    {
        Object value = entry.getValue().apply(object);
        return String.format("%s=%s", entry.getKey(), (value == null) ? "null" : value.toString());
    }
}
