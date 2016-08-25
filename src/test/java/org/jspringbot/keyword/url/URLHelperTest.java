/*
 * Copyright (c) 2012. JSpringBot. All Rights Reserved.
 *
 * See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The JSpringBot licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jspringbot.keyword.url;

import org.jspringbot.keyword.string.ReplaceVariable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test for {@link ReplaceVariable}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-utils.xml"})
public class URLHelperTest {

    @Autowired
    public URLHelper urlHelper;

    @Test
    public void testURLHelper() throws Exception {
        urlHelper.parse("http://jspringbot.org/search.html?a=0&b=hello&c=world&d=goodbye&e=https://myhomepage.com&a=1");
        urlHelper.assertProtocol("http");
        urlHelper.assertHost("jspringbot.org");
        urlHelper.assertPath("/search.html");
        urlHelper.assertPort(-1);
        urlHelper.assertParameterCount(6);
        urlHelper.assertParameterKeyExist("d");
        urlHelper.assertParameter("a","0");
        urlHelper.assertParameter("a","1");
        urlHelper.assertParameter("b","hello");
        urlHelper.assertParameter("c","world");
        urlHelper.assertParameter("e","https://myhomepage.com");
    }

}
