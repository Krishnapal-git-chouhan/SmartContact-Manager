package com.exe.smartcontact.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.exe.smartcontact.entities.User;

public class CustomUserDetails implements UserDetails{

	@Autowired
	User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		
		SimpleGrantedAuthority simple = new SimpleGrantedAuthority(user.getRole());
		
		return List.of(simple);
	}

	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	

	 
	
}
