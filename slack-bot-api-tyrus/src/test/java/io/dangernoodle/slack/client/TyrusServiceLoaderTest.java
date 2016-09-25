package io.dangernoodle.slack.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


@RunWith(value = JUnitPlatform.class)
public class TyrusServiceLoaderTest
{
    private Iterator<SlackProviderFactory> iterator;

    private ServiceLoader<SlackProviderFactory> pfLoader;

    @Test
    public void testServiceLoading()
    {
        givenAServiceLoader();
        whenLoadFactories();
        thenTyrusFactoryReturned();
        thenNoOtherFactoriesFound();
    }

    private void givenAServiceLoader()
    {
        pfLoader = ServiceLoader.load(SlackProviderFactory.class);
    }

    private void thenNoOtherFactoriesFound()
    {
        assertFalse(iterator.hasNext());
    }

    private void thenTyrusFactoryReturned()
    {
        SlackProviderFactory factory = iterator.next();

        assertNotNull(factory);
        assertThat(factory, instanceOf(TyrusSlackProviderFactory.class));
    }

    private void whenLoadFactories()
    {
        iterator = pfLoader.iterator();
    }
}
