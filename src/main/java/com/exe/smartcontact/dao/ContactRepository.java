package com.exe.smartcontact.dao;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exe.smartcontact.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("select c from Contact as c where c.user.id =:userId")
	public Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pePageable );
	
}
