package com.Blog.Application.Project.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Blog.Application.Project.Model.BlogPost;
import com.Blog.Application.Project.Model.ContactUs;
import com.Blog.Application.Project.Model.User;
import com.Blog.Application.Project.Repository.BlogPostRepository;
import com.Blog.Application.Project.Service.BlogPostService;
import com.Blog.Application.Project.Service.ContactService;
import com.Blog.Application.Project.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BlogController {
	@Autowired
	private BlogPostService blogPostService;
	
	@Autowired 
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/")
	 public String Home(){
		 return "home";
	 }
	
	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("post",blogPostService.findAll());
		return "index";
	}
	
	@GetMapping("/post/{id}")
	public String post(@PathVariable Long id,Model model) {
		 Optional<BlogPost> blogPostOptional = blogPostRepository.findById(id);

	        if (blogPostOptional.isPresent()) {
	            BlogPost blogPost = blogPostOptional.get();
	            model.addAttribute("post", blogPost);
	            return "post";
	        } else {
	            // Handle the case where the blog post is not found
	            return "postNotFound"; // Assuming you have a view named postNotFound.html
	        }
	    }


	
	@GetMapping("/new")
	public String newPostForm(Model model) {
		BlogPost blogPost =new BlogPost();
		model.addAttribute("post",blogPost);
		return "new";
	}
	
	@PostMapping("/save")
	public String savePost(@ModelAttribute BlogPost post) {
		blogPostService.save(post);
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable Long id) {
		blogPostService.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	 @GetMapping("/register")
		public String regiter(Model model) {
			model.addAttribute("user", new User());
			return "register";
		}
	 @PostMapping("/register")
	 public String registerUser(@ModelAttribute  User user) {
			userService.saveUser(user);
			return "redirect:/login";
		}
	
//	@RequestMapping("/update/{id}")
//	public String updateBlog(@PathVariable("id") Long id, Model model) {
//    	BlogPost blogPost = blogPostService.findById(id);
//		model.addAttribute("post",blogPost);
//		return "new";
//	}
	 @GetMapping("/update/{id}")
	    public String updateBlog(@PathVariable("id") Long id, Model model) {
	        BlogPost blogPost = blogPostService.findById(id);
	        model.addAttribute("post", blogPost);
	        return "updateForm";  // Ensure your form view name is correct
	    }

	    @PostMapping("/update/{id}")
	    public String saveUpdatedBlog(@PathVariable("id") Long id, @ModelAttribute("post") BlogPost blogPost) {
	        blogPost.setId(id);  // Ensure the ID is set correctly
	        blogPostService.save(blogPost);
	        return "redirect:/";  // Redirect to the updated blog post view
	    }
	    

	    @GetMapping("/about")
	    public String about(Model model) {
	        return "about";
	    }

	    @GetMapping("/contact")
	    public String contact(Model model, Principal principal) {
	        ContactUs contact = new ContactUs();
	        
	        // Get the current user's email from the Principal
	        String userEmail = principal.getName();
	        Optional<User> userOptional = userService.getUserByEmail(userEmail);
	        
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            contact.setName(user.getFullName());
	            contact.setEmail(user.getEmail());
	        }

	        model.addAttribute("contact", contact);
	        return "contact";
	    }

	    @PostMapping("/contact")
	    public String userContact(@ModelAttribute ContactUs contactUs) {
	        contactService.saveContact(contactUs);
	        return "redirect:/contact";
	    }
	    
	    
	    @GetMapping("/profile")
	    public String profile(Model model, Principal principal) {
//	        String userEmail = principal.getName(); // Assuming the principal holds the user's email
//	        Optional<User> userOptional = userService.getUserByEmail(userEmail);
//
//	        if (userOptional.isPresent()) {
//	            User user = userOptional.get();
//	            model.addAttribute("fullName", user.getFullName());
//	            model.addAttribute("email", user.getEmail());
//	        } else {
//	            // Handle user not found scenario
//	            return "redirect:/login"; // Or some other error page
//	        }
//
//	        return "profile";
		 String userEmail = principal.getName(); // Assuming the principal holds the user's email
		    Optional<User> userOptional = userService.getUserByEmail(userEmail);

		    if (userOptional.isPresent()) {
		        User user = userOptional.get();
		        model.addAttribute("user", user); // Add the entire user object to the model
		    } else {
		        // Handle user not found scenario
		        return "redirect:/login"; // Or some other error page
		    }

		    return "profile";
	    }
	    
	    @GetMapping("/edit-profile")
	    public String editProfile(Model model, Principal principal) {
	        String userEmail = principal.getName(); // Assuming the principal holds the user's email
	        Optional<User> userOptional = userService.getUserByEmail(userEmail);

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            model.addAttribute("user", user);
	        } else {
	            // Handle user not found scenario
	            return "redirect:/login"; // Or some other error page
	        }

	        return "edit-profile";
	    }
	    
	    private String saveUploadedFile(MultipartFile file) throws IOException {
	        String uploadDir = "uploads/";
	        Path uploadPath = Paths.get(uploadDir);

	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }

	        String fileName = file.getOriginalFilename();
	        try (var inputStream = file.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath);
	        }catch (IOException e) {
	            // Rethrow the exception to be handled in the calling method
	            throw new IOException("Failed to save uploaded file", e);
	        }

	        return fileName;
	    }

	    @PostMapping("/update-profile")
	    public String updateProfile(@ModelAttribute("user") User updatedUser,
	                                @RequestParam("profilePhoto") MultipartFile profilePhoto,
	                                Principal principal,
	                                Model model) {
	        String userEmail = principal.getName();
	        Optional<User> userOptional = userService.getUserByEmail(userEmail);

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            user.setFullName(updatedUser.getFullName());
	            user.setEmail(updatedUser.getEmail());
	            user.setAboutMe(updatedUser.getAboutMe());
	            
	            // Set the profile photo only if it's not empty
	            if (!profilePhoto.isEmpty()) {
	                try {
	                    String fileName = saveUploadedFile(profilePhoto);
	                    user.setProfilePhoto(fileName); // Store the filename in the user object
	                } catch (IOException e) {
	                    // Handle the exception
	                    e.printStackTrace();
	                    model.addAttribute("errorMessage", "Failed to upload profile photo.");
	                    return "error";
	                }
	            }

	            userService.saveUser(user);
	        } else {
	            return "redirect:/login";
	        }

	        return "redirect:/profile";
	    }

	    
	    // Add a method to handle error page rendering
	    @GetMapping("/error")
	    public String errorPage(Model model) {
	        return "error"; // Assuming you have an error.html template
	    }
	    
	    @GetMapping("/logout")
	    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	        if (authentication != null) {
	            new SecurityContextLogoutHandler().logout(request, response, authentication);
	        }
	        return "logout";
	    }



}
