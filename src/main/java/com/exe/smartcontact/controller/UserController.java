package com.exe.smartcontact.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.exe.smartcontact.dao.ContactRepository;
import com.exe.smartcontact.dao.UserRepository;
import com.exe.smartcontact.entities.Contact;
import com.exe.smartcontact.entities.User;
import com.exe.smartcontact.helper.Message;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	ContactRepository conrepo;
	
	
//	@Transactional
	@RequestMapping("/index")
	public String dashboard(Model m , Principal principal, HttpSession session) {
		String name = principal.getName();
		
		User user = userrepo.findByName(name);
		System.out.println("USER is:"+user);
		
    	m.addAttribute("user",user);
    	
    	if (session.getAttribute("message") == null) {
            session.setAttribute("message", new Message("Default Message", "info"));
        }
	
    	
		return "normal/dashboard";
	}
	
	@ModelAttribute
	public void commondata(Model m , Principal p) {
	String name = p.getName();
		
		User user = userrepo.findByName(name);
		System.out.println("USER is:"+user);
		
    	m.addAttribute("user",user);
	
	}
	
	@GetMapping("/add-contact")
	public String getcontactdetail(Model m) {
		m.addAttribute("title","contact-page");
		m.addAttribute("contact",new Contact());
	   
		return "normal/add-contact";
	}	
	
//	@PostMapping("/process-contact")
//	public String getcontact(@ModelAttribute Contact contact, Principal p,HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile file) {
//		
//		try {
//			String name = p.getName();
//			
//			User user = this.userrepo.findByName(name);
//			
//			if(file.isEmpty()) {
//				System.out.println("file is empty");
//			}else {
//				contact.setImage(file.getOriginalFilename());
//				File saveFile = new ClassPathResource("static/img").getFile();
//				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
//				
//				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//				
//				System.out.println("image is uploaded");
//				
//				
//				
//				redirectAttributes.addFlashAttribute("message", new Message("success", "Successfully added!!"));
//				return "redirect:/user/add-contact";  // Redirect instead of returning the same page
//	}
//			
//			user.getContact().add(contact);
//			
//			this.userrepo.save(user);
//
//			session.setAttribute("message", new Message("success", "Successfully added!!"));
//			
//			 System.out.println("DATA"+contact);
//		} catch (Exception e) {
//			e.printStackTrace();
//			session.setAttribute( "message", new Message("danger" , "something went wrong !! try again.. ")); 
//		}
//		
//		return "normal/add-contact";
//	}
	
//	@PostMapping("/process-contact")
//	public String getcontact(@ModelAttribute Contact contact, 
//	                         @RequestParam("image") MultipartFile file, 
//	                         Principal p, 
//	                         HttpSession session, 
//	                         RedirectAttributes redirectAttributes) {
//	    
//	    try {
//	        // Get logged-in user
//	        String name = p.getName();
//	        User user = this.userrepo.findByName(name);
//
//	        // File upload handling
//	        if (!file.isEmpty()) {
//	            contact.setImage(file.getOriginalFilename()); // Set image name
//	            
//	            // Get path for saving image
//	            File saveFile = new ClassPathResource("static/img").getFile();
//	            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
//	            
//	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//	            
//	            System.out.println("Image uploaded successfully!");
//	        } else {
//	            contact.setImage("default.png"); // Set a default image if none is uploaded
//	            System.out.println("File is empty. Using default image.");
//	        }
//
//	        // Associate contact with user & save
//	        user.getContact().add(contact);
//	        this.userrepo.save(user);
//
//	        System.out.println("DATA: " + contact);
//
//	        // Success message using RedirectAttributes
//	        redirectAttributes.addFlashAttribute("message", new Message("success", "Successfully added!!"));
//	        return "redirect:/user/add-contact";  // Redirect to prevent form resubmission
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        // Error message in session (as redirectAttributes won't work here)
//	        session.setAttribute("message", new Message("danger", "Something went wrong! Try again."));
//	        return "normal/add-contact";  // Return the same page in case of error
//	    }
//		
//		return "normal/add-contact";
//	}
//
	
	@PostMapping("/process-contact")
	public String getcontact(@ModelAttribute Contact contact,
	                         Principal principal,
	                         HttpSession session,
	                         RedirectAttributes redirectAttributes) {
	    try {
	        String username = principal.getName();
	        User user = this.userrepo.findByName(username);

	        if (user == null) {
	            redirectAttributes.addFlashAttribute("message", new Message("danger", "User not found!"));
	            return "redirect:/user/add-contact";
	        }
	        
	        contact.setUser(user);
	        user.getContact().add(contact);	
	        this.conrepo.save(contact);
	        
	        // Handling File Upload
//	        if (!file.isEmpty()) {
//	            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//	            File saveFile = new ClassPathResource("static/img").getFile();
//	            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + fileName);
//
//	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//	            System.out.println("Image uploaded successfully: " + fileName);
//
//	            // Store the image name and path
//	            contact.setImage(fileName);
//	            contact.setPath("/img/" + fileName);  // Store relative path
//	        } else {
//	            contact.setImage("default.png");
//	            contact.setPath("/img/default.png");
//	        }
//
//	        // Associate contact with user and save
//	        contact.setUser(user);
//	        user.getContact().add(contact);
//	        this.userrepo.save(user);

//	        redirectAttributes.addFlashAttribute("message", new Message("success", "Contact added successfully!"));
	        session.setAttribute("message", new Message("success", "Contact added successfully!"));
		      
	        return "redirect:/user/add-contact";

	    } catch (Exception e) {
	        e.printStackTrace();
//	        redirectAttributes.addFlashAttribute("message", new Message("danger", "Something went wrong! Try again."));
	        session.setAttribute("message", new Message("danger", "Something went wrong! Try again."));
		       
	        
	        return "redirect:/user/add-contact";
	    }
	}

	@GetMapping("/show-contact/{page}")
	public String showProcess(@PathVariable("page") Integer page, Model m, Principal p) {
		
		m.addAttribute("title","show user contact");
		
//		String userName = p.getName();
//		
//		User user = this.userrepo.findByName(userName);
//		
//		List<Contact> contact = user.getContact();
		
		String userName = p.getName();
		User user = this.userrepo.findByName(userName);
		
     org.springframework.data.domain.Pageable pageable =  PageRequest.of(page, 5);
   	Page<Contact> contact  = this.conrepo.findContactByUser(user.getId(),pageable);
   	
   	
//		List<Contact> contact = Arrays.asList(
//		        new Contact(1, "John Doe", "john@example.com", "9876543210"),
//		        new Contact(2, "Jane Smith", "jane@example.com", "1234567890")
//		    );

   	
   	
        System.out.println("Contacts fetched: " + contact);
    	
    	m.addAttribute( "contact" , contact);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPage",contact.getTotalPages());
		return "normal/show-contact";
	}


	@GetMapping("/show-contact")
	public String redirectToFirstPage() {
	    return "redirect:/user/show-contact/0";
	}

	@RequestMapping("/{cId}/contact")
	public String showDetail(@PathVariable("cId") Integer cId, Model m, Principal p) {
		
		
	Optional<Contact> contactopt =	this.conrepo.findById(cId);
	
	
	Contact contact = contactopt.get();
	
	String username = p.getName();
	User user = this.userrepo.findByName(username);
	
	if(user.getId() == contact.getUser().getId()) {

		m.addAttribute("contact",contact);
	}
	
	
	
		return "normal/contact_detail";
	}
	
	@RequestMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, Model m, HttpSession s) {
		
		Optional<Contact> contactopt =	this.conrepo.findById(cId);
		Contact contact = contactopt.get();
		
		contact.setUser(null);
		
		this.conrepo.delete(contact);
		
		s.setAttribute("msg", new Message("contact deleted successfuly","success"));
		
		return "redirect:/user/show-contact/0";
	}
	
	@PostMapping("/update-contact/{cId}")
	public String updateForm(@PathVariable("cId") Integer cId , Model m) {
		m.addAttribute("title","Update Contact");
		
		Optional<Contact> contactopt =	this.conrepo.findById(cId);
		Contact contact = contactopt.get();
	

		m.addAttribute("contact",contact);
		
		return "normal/update_contact";
		}
	
	@PostMapping("/process-update")
	public String processUpdate(@ModelAttribute Contact contact, Principal principal, RedirectAttributes redirectAttributes) {
	    
	    // Get user details and verify ownership if needed
	    
	    // Save updated contact
	    conrepo.save(contact);

	    redirectAttributes.addFlashAttribute("message", "Contact updated successfully!");

	    return "redirect:/user/show-contact/0";
	}


	
	
}
