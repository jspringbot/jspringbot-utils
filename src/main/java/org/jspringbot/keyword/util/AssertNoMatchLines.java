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

package org.jspringbot.keyword.util;

import org.apache.commons.lang.StringUtils;
import org.jspringbot.Keyword;
import org.jspringbot.KeywordInfo;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@KeywordInfo(name = "Assert No Match Lines",
        description = "Assert string has no matching regular expression",
        parameters = {"string","regex"})
public class AssertNoMatchLines implements Keyword {

    @Override
    public Object execute(Object[] params) throws Exception {

        String content = String.valueOf(params[0]);
        String regex = String.valueOf(params[1]);

        BufferedReader reader = null;
        try {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            reader = new BufferedReader(new StringReader(content));

            List<String> foundItems = new LinkedList<String>();
            String line;
            int ctr = 0;
            while ((line = reader.readLine()) != null) {
                ctr++;
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    foundItems.add("line " + ctr + ": " + matcher.group());
                }
            }

            System.out.println("Found matches: \n" + StringUtils.join(foundItems, "\n  "));
            if (!foundItems.isEmpty()) {
                throw new IllegalStateException("Found matches: \n" + StringUtils.join(foundItems, "\n  "));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }
}