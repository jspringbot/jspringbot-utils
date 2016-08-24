package org.jspringbot.keyword.url;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class URLHelper {

    private URL url;
    private List<NameValuePair> parameterList;
    private Map<String, List<String>> parameters;

    public void parse(String urlString) throws MalformedURLException {
        url = new URL(urlString);
        parameterList = URLEncodedUtils.parse(url.getQuery(), Charset.forName("utf-8"));

        parameters = new HashMap<>(parameterList.size());
        for(NameValuePair nv : parameterList) {
            List<String> values = parameters.get(nv.getName());
            if(values == null) {
                values = new ArrayList<>();
                parameters.put(nv.getName(), values);
            }

            values.add(nv.getName());
        }
    }

    public void assertHost(String host) {
        assertThat(url.getHost(), is(host));
    }

    public void assertPort(int port) {
        assertThat(url.getPort(), is(port));
    }

    public void assertProtocol(String protocol) {
        assertThat(url.getProtocol(), is(protocol));
    }

    public void assertParameterCount(int count) {
        assertThat(parameterList.size(), is(count));
    }

    public void assertParameter(String name, String value) {
        List<String> values = parameters.get(name);

        if(CollectionUtils.isEmpty(values)) {
            throw new IllegalArgumentException("No parameter with name " + name);
        }

        assertThat(values.contains(value), is(true));
    }
}
