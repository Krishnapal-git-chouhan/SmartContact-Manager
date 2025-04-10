package com.exe.smartcontact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.exe.smartcontact.dao.UserRepository;
import com.exe.smartcontact.entities.User;
import jakarta.transaction.Transactional;

public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userrepo;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		User user = userrepo.findByName(username);
		
		
		if(user == null) {
			throw new UsernameNotFoundException("could not found user !!");
			
		}
		
		CustomUserDetails custom = new CustomUserDetails(user);
		
		return custom;
	}
	
	

}
