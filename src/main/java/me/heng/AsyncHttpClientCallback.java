package me.heng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * AUTHOR: wangdi
 * DATE: 20/06/2018
 * TIME: 5:14 PM
 */
public class AsyncHttpClientCallback implements FutureCallback<HttpResponse> {

    private static Logger LOG = LoggerFactory
            .getLogger(AsyncHttpClientCallback.class);

    /**
     * 请求完成后调用该函数
     */
    @Override
    public void completed(HttpResponse response) {

        LOG.warn("status:{}",response.getStatusLine().getStatusCode());
        LOG.warn("response:{}",getHttpContent(response));

        HttpClientUtils.closeQuietly(response);
    }

    /**
     * 请求取消后调用该函数
     */
    @Override
    public void cancelled() {

    }

    /**
     * 请求失败后调用该函数
     */
    @Override
    public void failed(Exception e) {

    }



    protected String getHttpContent(HttpResponse response) {

        HttpEntity entity = response.getEntity();
        String body = null;

        if (entity == null) {
            return null;
        }

        try {
            body = EntityUtils.toString(entity, "utf-8");
        } catch (ParseException e) {
            LOG.warn("the response's content inputstream is corrupt", e);
        } catch (IOException e) {

            LOG.warn("the response's content inputstream is corrupt", e);
        }
        return body;
    }
}
