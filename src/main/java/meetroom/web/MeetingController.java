package meetroom.web;

import java.time.LocalDateTime;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import meetroom.domain.User;
import meetroom.services.MeetingService;

@Controller
@RequestMapping("/")
public class MeetingController {
	private MeetingService service;
	
	public MeetingController(MeetingService service) {
		this.service = service;
	}
	
	@GetMapping
	public String show(Model model, @AuthenticationPrincipal User user) {	
		return showPage(model, user, 1);
	} 
	
	@GetMapping("/page/{pageNumber}")
	public String showPage(Model model, @AuthenticationPrincipal User user,
			@PathVariable("pageNumber") int currentPage) {
		
		model.addAttribute("currUser", user);
		model.addAttribute("page", service.getDatePage(currentPage));
		model.addAttribute("timeList", service.getTimeList());
		model.addAttribute("dateTime", LocalDateTime.now());
		model.addAttribute("service", service);
		
		
		return "calendar";
	}
	
}
