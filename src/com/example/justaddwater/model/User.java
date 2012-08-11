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
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

/**
 * @author armhold
 */
@Entity
@Table(name = "users")
public class User implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;

    @Basic
    private String email;

    @Basic
    private String password;

    @Basic
    private Date accountCreationDate;

    @Basic
    private AuthenticationType authenticationType;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OneTimeLogin> oneTimeLogins = new ArrayList<OneTimeLogin>();

    public User()
    {
    }

    public Key getId()
    {
        return id;
    }

    public void setId(Key id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getAccountCreationDate()
    {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate)
    {
        this.accountCreationDate = accountCreationDate;
    }

    public void setAuthenticationType(AuthenticationType authenticationType)
    {
        this.authenticationType = authenticationType;
    }

    public AuthenticationType getAuthenticationType()
    {
        return authenticationType;
    }

	public List<OneTimeLogin> getOneTimeLogins() {
		return oneTimeLogins;
	}

	public void setOneTimeLogins(List<OneTimeLogin> oneTimeLogins) {
		this.oneTimeLogins = oneTimeLogins;
	}
}
