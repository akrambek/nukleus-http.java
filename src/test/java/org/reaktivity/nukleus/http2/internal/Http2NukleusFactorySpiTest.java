/**
 * Copyright 2016-2018 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.nukleus.http2.internal;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.reaktivity.nukleus.Configuration;
import org.reaktivity.nukleus.Nukleus;
import org.reaktivity.nukleus.NukleusBuilder;
import org.reaktivity.nukleus.NukleusFactory;
import org.reaktivity.nukleus.stream.StreamFactoryBuilder;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.reaktivity.nukleus.route.RouteKind.SERVER;

public class Http2NukleusFactorySpiTest
{
    private NukleusBuilder builder;

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery()
    {
        {
            builder = mock(NukleusBuilder.class, "builder");
        }
    };

    @Test
    public void shouldCreateHttp2Nukleus()
    {
        context.checking(new Expectations()
        {
            {
                oneOf(builder).streamFactory(with(SERVER), with(any(StreamFactoryBuilder.class)));
            }
        });

        NukleusFactory factory = NukleusFactory.instantiate();
        Properties properties = new Properties();
        properties.setProperty(Configuration.DIRECTORY_PROPERTY_NAME, "target/nukleus-tests");
        Configuration config = new Configuration(properties);
        Nukleus nukleus = factory.create("http2", config, builder);
        assertThat(nukleus, instanceOf(Nukleus.class));
    }

}
