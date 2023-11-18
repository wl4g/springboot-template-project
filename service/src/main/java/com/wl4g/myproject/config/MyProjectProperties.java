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

import com.wl4g.infra.common.lang.Assert2;
import com.wl4g.myproject.exception.MyProjectException;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.wl4g.infra.common.lang.Assert2.isTrueOf;
import static com.wl4g.infra.common.lang.Assert2.notEmptyOf;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * The {@link MyProjectProperties}
 *
 * @author James Wong
 * @since v1.0
 **/
@Slf4j
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class MyProjectProperties implements InitializingBean {
    private @Default DefinitionProperties definitions = new DefinitionProperties();
    private @Default List<WorkingProperties> workings = new ArrayList<>(1);

    @Override
    public void afterPropertiesSet() {
        try {
            validate();
        } catch (Throwable ex) {
            throw new MyProjectException("Failed to validate myproject properties", ex);
        }
    }

    private void validate() {
        requireNonNull(definitions, "definitions must not be null");
        //notEmptyOf(workings, "workings");
        //this.definitions.validate();
        //this.workings.forEach(WorkingProperties::validate);
    }

    // ----- Definitions configuration. -----

    @Getter
    @Setter
    @SuperBuilder
    @ToString
    @NoArgsConstructor
    public static class DefinitionProperties {
        private @Default List<Component1Properties> myComponents = new ArrayList<>(2);

        public void validate() {
            notEmptyOf(myComponents, "myComponents");
            // Check for component1s name duplicate.
            isTrueOf(myComponents.size() == new HashSet<>(myComponents.stream()
                    .map(Component1Properties::getName).collect(toList())).size(), "myComponents name duplicate");
        }
    }

    @Getter
    @Setter
    @SuperBuilder
    @ToString
    @NoArgsConstructor
    public static class Component1Properties {
        private String name;
        private String baseUri;
    }

    // ----- Platform environment configuration. -----

    @Getter
    @Setter
    @SuperBuilder
    @ToString
    @NoArgsConstructor
    public static class WorkingProperties {
        private String name;

        public void validate() {
            Assert2.hasTextOf(name, "name");
        }
    }

}
