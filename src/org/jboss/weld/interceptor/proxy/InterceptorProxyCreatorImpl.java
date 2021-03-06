/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.weld.interceptor.proxy;

import java.lang.reflect.Constructor;
import java.util.logging.Logger;

import javassist.util.proxy.MethodHandler;

import org.jboss.weld.interceptor.spi.context.InvocationContextFactory;
import org.jboss.weld.interceptor.spi.instance.InterceptorInstantiator;
import org.jboss.weld.interceptor.spi.metadata.ClassMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class InterceptorProxyCreatorImpl implements InterceptorProxyCreator {
	
	private static final Logger log = Logger.getLogger(InterceptorProxyCreatorImpl.class.getName());

    private final InvocationContextFactory invocationContextFactory;
    private final InterceptionModel<ClassMetadata<?>, ?> interceptionModel;

    private final InterceptorInstantiator<?, ?> interceptorInstantiator;

    public InterceptorProxyCreatorImpl(InterceptorInstantiator<?, ?> interceptorInstantiator, InvocationContextFactory invocationContextFactory, InterceptionModel<ClassMetadata<?>, ?> interceptionModel) {
        this.interceptorInstantiator = interceptorInstantiator;
        this.invocationContextFactory = invocationContextFactory;
        this.interceptionModel = interceptionModel;
    }

    public <T> MethodHandler createSubclassingMethodHandler(Object targetInstance, ClassMetadata<T> proxyClass) {
        return new InterceptorMethodHandler(targetInstance, proxyClass, interceptionModel, interceptorInstantiator, invocationContextFactory);
    }

    private <T> Constructor<T> getNoArgConstructor(Class<T> clazz) {
    	log.info("getNoArgConstructor start. clazz = " + clazz.getName());
        Constructor<T> constructor;
        try {
            constructor = clazz.getConstructor(new Class[]{});
            log.info("No args constructor found");
        } catch (NoSuchMethodException e) {
        	log.info("No args constructor NOT found");
            return null;
        }
        log.info("getNoArgConstructor end.");
        return constructor;
    }

}

