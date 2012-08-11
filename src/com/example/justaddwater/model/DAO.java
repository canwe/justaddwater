/*
 *  Copyright 2012 George Armhold
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package com.example.justaddwater.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;

import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author George Armhold armhold@gmail.com
 */
@ApplicationScoped
public class DAO implements Serializable
{
	
	@Inject
    Logger log;
	
	@Inject
	EntityManagerFactory emf;
    
    public DAO() {
	}

    public User findUserByEmail(String email)
    {
    	log.info("findUserByEmail " + email);
        if (Strings.isEmpty(email)) return null;
        
        EntityManager em = getEntityManager();

        Query query = em.createQuery("select u from User u where u.email= :email");
        query.setParameter("email", email);
        List<User> results = query.getResultList();
        
        log.info("findUserByEmail: results size = " + results.size());
        
        User u = DAO.getOneFromCollection(results);
        
        em.close();
        em = null;
                        
        return u;
    }

    public OneTimeLogin findOneTimeLoginByToken(String token)
    {
        if (Strings.isEmpty(token)) return null;

        EntityManager em = getEntityManager();
        Query query = em.createQuery("select otl from OneTimeLogin otl where otl.token = :token");
        query.setParameter("token", KeyFactory.stringToKey(token));
        List<OneTimeLogin> results = query.getResultList();
        OneTimeLogin otl = DAO.getOneFromCollection(results);
        log.info("findOneTimeLoginByToken: user = " + otl.getUser().getEmail());
        em.close();
        em = null;
        return otl;
    }
    
    public static <T> T getOneFromCollection(List list) {
		return (list == null || list.isEmpty() || list.size() == 0) ? null : (T) list.get(0);
	}
	
	//wraps collection returned from object manager, 
	//so we'll not meet "Object Manager has been closed" exception
	public static <T> List<T> wrap(List<T> list) {
		return list != null ? new ArrayList<T>(list) : new ArrayList<T>();
	}
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
