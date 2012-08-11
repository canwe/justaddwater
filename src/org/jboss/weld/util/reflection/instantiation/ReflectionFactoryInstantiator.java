/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and/or its affiliates, and individual
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
package org.jboss.weld.util.reflection.instantiation;

import static org.jboss.weld.logging.messages.ReflectionMessage.REFLECTIONFACTORY_INSTANTIATION_FAILED;

import java.util.logging.Logger;

import org.jboss.weld.exceptions.WeldException;

/**
 * A instantiator for sun.reflect.ReflectionFactory
 *
 * @author Nicklas Karlsson
 * @author Ales Justin
 */
public class ReflectionFactoryInstantiator implements Instantiator {

	private static final Logger log = Logger.getLogger(ReflectionFactoryInstantiator.class.getName());
	
    private void init() {
    	log.info("init: ReflectionFactoryInstantiator isn't available");
    }

    public boolean isAvailable() {
    	init();
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T> T instantiate(Class<T> clazz) {
        throw new WeldException(REFLECTIONFACTORY_INSTANTIATION_FAILED, clazz);
    }
}