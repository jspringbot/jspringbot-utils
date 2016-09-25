package org.jspringbot.keyword.url;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Validate.isTrue(StringUtils.equals(url.getHost(), host));
    }

    public void assertPort(int port) {
        Validate.isTrue(url.getPort() == port);
    }

    public void assertProtocol(String protocol) {
        Validate.isTrue(StringUtils.equals(url.getProtocol(), protocol));
    }

    public void assertPath(String path) {
        Validate.isTrue(StringUtils.equals(url.getPath(), path));
    }

    public void assertParameterCount(int count) {
        Validate.isTrue(parameterList.size() == count);
    }

    public void assertParameterKeyExist(String name) {

        Validate.isTrue(parameters.containsKey(name));
    }

    public void assertParameter(String name, String value) {
        List<String> values = parameters.get(name);

        if(CollectionUtils.isEmpty(values)) {
            throw new IllegalArgumentException("No parameter with name " + name);
        }

        System.out.println(" ==== name param: " + name + " value param: " + value + " parameter values: " + parameters.get(name) + " parameter exist: " + values.contains(value));

        Validate.isTrue(values.contains(value));
    }
}
