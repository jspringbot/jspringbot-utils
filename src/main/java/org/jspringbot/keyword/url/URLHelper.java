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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class URLHelper {

    private URL url;
    private List<NameValuePair> parameterList;
    private Map<String, List<String>> parameters;

    public void parse(String urlString) throws MalformedURLException {
        url = new URL(urlString);
        parameterList = URLEncodedUtils.parse(url.getQuery(), Charset.forName("utf-8"));
        System.out.println("ParameterList = " + parameterList);

        parameters = new HashMap<>(parameterList.size());
        for(NameValuePair nv : parameterList) {
            List<String> values = parameters.get(nv.getName());
            if(values == null) {
                values = new ArrayList<>();
                values.add(nv.getValue());

                parameters.put(nv.getName(), values);
                System.out.println(" - keyvaluepair " + nv.getName() + " - "+ values);
            } else {

                values.add(nv.getValue());
                parameters.put(nv.getName(), values);

                System.out.println(" - keyvaluepair " + nv.getName() + " - "+ values);

            }
        }
        System.out.println("\n" + "parameters" + " " + parameters);
    }

    public void assertHost(String host) {
        assertEquals(url.getHost(), host);
    }

    public void assertPort(int port) {

        assertEquals(url.getPort(), port);
    }

    public void assertProtocol(String protocol) {
        assertEquals(url.getProtocol(), protocol);
    }

    public void assertPath(String path) {
        assertEquals(url.getPath(), path);
    }

    public void assertParameterCount(int count) {
        assertEquals(parameterList.size(), count);
    }

    public void assertParameterKeyExist(String name) {

        assertEquals(parameters.containsKey(name), true);
    }

    public void assertParameter(String name, String value) {
        List<String> values = parameters.get(name);

        if(CollectionUtils.isEmpty(values)) {
            throw new IllegalArgumentException("No parameter with name " + name);
        }

        System.out.println(" ==== name param: " + name + " value param: " + value + " parameter values: " + parameters.get(name) + " parameter exist: " + values.contains(value));

        assertEquals(values.contains(value), true);
    }
}
