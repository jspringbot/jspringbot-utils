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

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UtilTest {

    @Test
    public void testComputePercentage() throws Exception {
        ComputePercentage computePercentage = new ComputePercentage();
        computePercentage.execute(new Object[]{"90","100"});
        computePercentage.execute(new Object[]{"50","100"});
        computePercentage.execute(new Object[]{"0","100"});
    }

    @Test
    public void testPercentageShouldBe() throws Exception {
        PercentageShouldBeWithinThreshold percentageShouldBe = new PercentageShouldBeWithinThreshold();
        percentageShouldBe.execute(new Object[]{"50","52","2"});
        percentageShouldBe.execute(new Object[]{"50","48","2"});
    }

    @Test
    public void testFoundSessionToken() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/session-token.json");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();

        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String fileAsString = sb.toString();
        String regex="(([A-Z0-9]){8}\\-([A-Z0-9]){4}\\-([A-Z0-9]){4}\\-([A-Z0-9]){4}\\-([A-Z0-9]){12})";
        System.out.println("Contents : " + fileAsString);

        Object[] objects = new Object[] {fileAsString,regex};

        AssertNoMatchLines assertNoMatchLines = new AssertNoMatchLines();
        try {
            assertNoMatchLines.execute(objects);

        } catch (Exception e) {
            System.out.println("Test passed. Session token found");
        }
    }

    @Test
    public void testCountSessionToken() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/session-token.json");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();

        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String fileAsString = sb.toString();
        String regex="(([A-Z0-9]){8}\\-([A-Z0-9]){4}\\-([A-Z0-9]){4}\\-([A-Z0-9]){4}\\-([A-Z0-9]){12})";
        System.out.println("Contents : " + fileAsString);

        Object[] objects = new Object[] {fileAsString,regex};

        CountMatchedLines countMatchedLines = new CountMatchedLines();
        countMatchedLines.execute(objects);
    }

    @Test
    public void testAssertNoMatchLines() {
        AssertNoMatchLines assertNoMatchLines = new AssertNoMatchLines();

        String fileAsString = "test\n"+
            "test123";
        String regex="[0-9]{3}";

        Object[] objects = new Object[] {fileAsString,regex};

        try {
            assertNoMatchLines.execute(objects);
        } catch (Exception e) {
            System.out.println("Test passed. 123 found in string.");
        }

    }

}
