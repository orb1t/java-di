package org.osgl.inject;

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

import org.osgl.$;

import javax.annotation.PostConstruct;
import javax.inject.Provider;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Invoke bean's {@link javax.annotation.PostConstruct post construct methods}
 */
class PostConstructorInvoker<T> implements Provider<T> {

    private Provider<T> realProvider;
    private Method postConstructor;

    private PostConstructorInvoker(Provider<T> realProvider, Method postConstructor) {
        this.realProvider = realProvider;
        this.postConstructor = postConstructor;
    }

    @Override
    public T get() {
        T t = realProvider.get();
        $.invokeVirtual(t, postConstructor);
        return t;
    }

    static <T> Provider<T> decorate(BeanSpec spec, Provider<T> realProvider, Genie genie) {
        if (realProvider instanceof PostConstructorInvoker) {
            return realProvider;
        }

        Method postConstructor = findPostConstructor(spec.rawType());
        return null == postConstructor ? realProvider : new PostConstructorInvoker<T>(realProvider, postConstructor);
    }

    private static Method findPostConstructor(Class<?> type) {
        for (Method m : type.getDeclaredMethods()) {
            if (Modifier.isStatic(m.getModifiers()) || Void.TYPE != m.getReturnType()) {
                continue;
            }
            if (m.isAnnotationPresent(PostConstruct.class)) {
                m.setAccessible(true);
                return m;
            }
        }
        Class<?> parent = type.getSuperclass();
        if (null != parent && Object.class != parent) {
            return findPostConstructor(parent);
        }
        return null;
    }


}
