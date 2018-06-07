package service;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class BaseService {

    public String post(String uri, String json) throws IOException {

        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postMethod = new HttpPost(uri);
        postMethod.setEntity(requestEntity);
        HttpResponse response = client.execute(postMethod);

        String reposnseJson = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

        return reposnseJson;

    }

    public String get(String uri) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getMethod = new HttpGet(uri);
        HttpResponse response = client.execute(getMethod);

        String json = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

        return json;

    }

    public String put(String uri, String json) throws IOException {

        StringEntity requestEntity = new StringEntity(
                json,
                ContentType.APPLICATION_JSON);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPut putMethod = new HttpPut(uri);
        putMethod.setEntity(requestEntity);
        HttpResponse response = client.execute(putMethod);

        String reposnseJson = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

        return reposnseJson;

    }

}
