/*
 *  Copyright (C) 2023 ~ 2035 the original authors WL4G (James Wong).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.wl4g.myproject.config;

import com.wl4g.myproject.meter.MeterEventHandler;
import com.wl4g.myproject.meter.MyProjectMeter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * The {@link MyProjectAutoConfiguration}
 *
 * @author James Wong
 * @since v1.0
 **/
public class MyProjectAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "x-ops")
    public MyProjectProperties xOpsProperties() {
        return new MyProjectProperties();
    }

    @Bean
    public MyProjectMeter xOpsMeter(PrometheusMeterRegistry meterRegistry,
                                    @Value("${spring.application.name}") String appName,
                                    @Value("${server.port}") Integer port) {
        return new MyProjectMeter(meterRegistry, appName, port);
    }

    @Bean
    public MeterEventHandler meterEventHandler(MyProjectMeter meter) {
        return new MeterEventHandler(meter);
    }


}

