package com.Blog.Application.Project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog.Application.Project.Model.ContactUs;
import com.Blog.Application.Project.Repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	public void saveContact(ContactUs contact) {
		contactRepository.save(contact);
	}
}
