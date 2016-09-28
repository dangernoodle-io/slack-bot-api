package io.dangernoodle.slack.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.dangernoodle.slack.client.SlackHttpDelegate;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Implementation of <code>SlackHttpDelegate</code> using <code>okhttp3</code>
 *
 * @since 0.1.0
 */
public class OkHttpDelegate implements SlackHttpDelegate
{
    public static final MediaType MEDIA_TYPE_BINARY = MediaType.parse("application/octet-stream");

    private final OkHttpClient httpClient;

    public OkHttpDelegate()
    {
        this.httpClient = createOkHttpClient();
    }

    @Override
    public String get(String url) throws IOException
    {
        return execute(createBuilder(url));
    }

    @Override
    public String post(String url, Map<String, Object> formData) throws IOException
    {
        FormBody.Builder builder = new FormBody.Builder();
        formData.forEach((k, v) -> builder.add(k, v.toString()));

        return execute(createBuilder(url).post(builder.build()));
    }

    @Override
    public String upload(String url, File file, String filename, Map<String, Object> formData) throws IOException
    {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        formData.forEach((k, v) -> builder.addFormDataPart(k, v.toString()));
        builder.addFormDataPart("file", filename, RequestBody.create(MEDIA_TYPE_BINARY, file));

        return execute(createBuilder(url).post(builder.build()));
    }

    OkHttpClient createOkHttpClient()
    {
        return new OkHttpClient.Builder().build();
    }

    private Request.Builder createBuilder(String url)
    {
        return new Request.Builder().url(url);
    }

    private String execute(Request.Builder builder) throws IOException
    {
        Response response = httpClient.newCall(builder.build()).execute();

        if (!response.isSuccessful())
        {
            throw new SlackHttpException(response.code(), response.message());
        }

        return response.body().string();
    }
}
