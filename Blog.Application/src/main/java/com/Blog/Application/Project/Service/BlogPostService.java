package com.Blog.Application.Project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog.Application.Project.Model.BlogPost;
import com.Blog.Application.Project.Repository.BlogPostRepository;

@Service
public class BlogPostService {
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	public List<BlogPost> findAll(){
		return blogPostRepository.findAll();
	}
	
//	public Optional<BlogPost> findById(Long id){
//		return blogPostRepository.findById(id);
//	}
	public BlogPost findById(long id) {
		return blogPostRepository.findById(id).get();
	}
	
	public BlogPost save(BlogPost blogPost) {
		return blogPostRepository.save(blogPost);
	}
	
	public void deleteById(Long id) {
		blogPostRepository.deleteById(id);
	}

}
