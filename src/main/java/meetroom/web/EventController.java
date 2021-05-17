package meetroom.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import meetroom.domain.Event;
import meetroom.domain.User;
import meetroom.forms.EventForm;
import meetroom.security.UserRepositoryUserDetailsService;
import meetroom.services.MeetingService;
import meetroom.utils.DateUtil;
import meetroom.utils.EventFormValidator;

@Controller
@RequestMapping("/event")
public class EventController {
	
	private MeetingService meetingService;
	private UserRepositoryUserDetailsService userService;
	private EventFormValidator validator;
	
	public EventController(MeetingService meetingService, UserRepositoryUserDetailsService userService, 
			EventFormValidator validator) {
		this.meetingService = meetingService;
		this.userService = userService;
		this.validator = validator;
	}
	
	@ModelAttribute(name = "eventForm")
	public EventForm eventForm() {
		return new EventForm(userService);
	}
	@ModelAttribute(name = "timeList")
	public List<LocalTime> timeList() {
		return meetingService.getTimeList();
	}
	@ModelAttribute(name = "dateList")
	public List<LocalDate> dateList() {
		return meetingService.getDateListForBookig();
	}
	@ModelAttribute(name = "minuteList")
	public List<Integer> minuteList() {
		return DateUtil.getMinuteList();
	}
	@ModelAttribute(name = "currUser")
	public User currUser(@AuthenticationPrincipal User user) {
		return user;
	}
	
	@GetMapping("/{id}")
	public String eventInfo(Model model, @AuthenticationPrincipal User user,
			@PathVariable("id") long id) {
		model.addAttribute("event", meetingService.findById(id));
		return "event_info";
	}
	
	@GetMapping("/new")
	public String showEventForm(Model model, @AuthenticationPrincipal User user) {
		return "create_event";
	}
	
	@PostMapping("/new")
	public String createEvent(@ModelAttribute("eventForm") @Valid EventForm eventForm, Errors errors, @AuthenticationPrincipal User user,  Model model) {
		
		
		validator.validate(eventForm, errors);
		if (errors.hasErrors()) {
			return "create_event";
		}
		Event event = eventForm.createEvent();
		event.setUser(user);
		meetingService.save(event);
		
		return "redirect:/";
	}
}
