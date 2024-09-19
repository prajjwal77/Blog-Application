package com.Blog.Application.Project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Blog.Application.Project.Model.ContactUs;

@Repository
public interface ContactRepository  extends JpaRepository<ContactUs, Long>{

}
