/*
 * Copyright 2014 Ranjan Kumar
 *
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
 */
package com.restfiddle.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.restfiddle.constant.StatusType;

public class User extends NamedEntity implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @Length(max = 255)
    private String password;

    @Column(nullable = false)
    @Length(max = 255)
    private String email;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Tenant tenant;

    public String getEmail() {
	return email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getUsername() {
	return super.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
	return StatusType.ACTIVE.toString().equals(super.getStatus());
    }

    @Override
    public boolean isAccountNonLocked() {
	return StatusType.ACTIVE.toString().equals(super.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return StatusType.ACTIVE.toString().equals(super.getStatus());
    }

    @Override
    public boolean isEnabled() {
	return StatusType.ACTIVE.toString().equals(super.getStatus());
    }

    @Override
    public String toString() {
	return "User [email=" + email + "]";
    }

    public Team getTeam() {
	return team;
    }

    public void setTeam(Team team) {
	this.team = team;
    }

    public Tenant getTenant() {
	return tenant;
    }

    public void setTenant(Tenant tenant) {
	this.tenant = tenant;
    }

}
