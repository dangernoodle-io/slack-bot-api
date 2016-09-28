package io.dangernoodle.slack.objects.web;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import io.dangernoodle.slack.SlackJsonTestFiles;
import io.dangernoodle.slack.utils.GsonTransformer;

public abstract class AbstractSerializationTest
{
    protected static final GsonTransformer transformer = new GsonTransformer();

    protected SlackJsonTestFiles jsonFile;

    protected Object toSerialize;

    private String serialized;

    @Test
    public void testSerialization() throws Exception
    {
        givenAJsonTestFile();
        givenAnObjectToSerialize();
        whenSerialize();
        thenJsonMatches();
    }

    protected abstract void givenAJsonTestFile();

    protected abstract void givenAnObjectToSerialize();

    private void thenJsonMatches() throws JSONException
    {
        JSONAssert.assertEquals(jsonFile.loadJson(), serialized, false);
    }

    private void whenSerialize()
    {
        serialized = transformer.serialize(toSerialize);
    }
}
