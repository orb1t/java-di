package org.osgl.inject.provider;

/*-
 * #%L
 * OSGL Genie
 * %%
 * Copyright (C) 2017 OSGL (Open Source General Library)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.osgl.util.C;

import javax.inject.Provider;
import java.util.List;

/**
 * Inject {@link List} and {@link org.osgl.util.C.List} using
 * {@link org.osgl.util.DelegatingList} implementation.
 */
public class OsglListProvider implements Provider<C.List<?>> {

    public static final OsglListProvider INSTANCE = new OsglListProvider();

    @Override
    public C.List<?> get() {
        return C.newList();
    }

}
