package meetroom.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import meetroom.domain.Event;
import meetroom.domain.User;
import meetroom.forms.EventForm;
import meetroom.services.MeetingService;
import meetroom.utils.ImageUtil;

@Controller
@RequestMapping("/")
public class MeetingController {
	private MeetingService service;
	
	public MeetingController(MeetingService service) {
		this.service = service;
	}
	
	@ModelAttribute("imgUtil")
	public ImageUtil imgUtil() {
		return new ImageUtil();
	}
	
	@GetMapping
	public String show(Model model, @AuthenticationPrincipal User user) {	
		return showPage(model, user, 1);
	} 
	
	@GetMapping("/page/{pageNumber}")
	public String showPage(Model model, @AuthenticationPrincipal User user,
			@PathVariable("pageNumber") int currentPage) {
		
		PagedListHolder<LocalDate> dateHolder = service.getDatePagedListHolder();
		dateHolder.setPage(currentPage);
		
		model.addAttribute("currUser", user);
		model.addAttribute("page", dateHolder);
		model.addAttribute("eventsMap", service.getEventsMap(currentPage));
		model.addAttribute("timeList", service.getTimeList());
		model.addAttribute("dateNow", LocalDate.now());
		model.addAttribute("service", service);
		
		return "calendar";
	}
	
}
