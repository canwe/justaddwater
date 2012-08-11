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
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

/**
 * supports "forgotten password login"
 *
 * @author George Armhold armhold@gmail.com
 */
@Entity
@Table(name = "onetimelogin")
public class OneTimeLogin implements Serializable
{
    // valid for 24 hours
    public static final long VALID_FOR_MILLIS = 1000 * 60 * 60 * 24;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Key token;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    public Key getToken()
    {
        return token;
    }

    public void setToken(Key token)
    {
        this.token = token;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public boolean isExpired()
    {
        return ! isNotExpired();
    }

    public boolean isNotExpired()
    {
        return new Date().getTime() - creationDate.getTime() < VALID_FOR_MILLIS;
    }

}
