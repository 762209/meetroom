package meetroom.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import meetroom.utils.ImageUtil;

@Controller
@RequestMapping("/event")
public class EventController {
	
	private MeetingService meetingService;
	private UserRepositoryUserDetailsService userService;
	private EventFormValidator validator;
	private DateTimeFormatter dtf;
	
	@Autowired
	public EventController(MeetingService meetingService, UserRepositoryUserDetailsService userService, 
			EventFormValidator validator) {
		this.meetingService = meetingService;
		this.userService = userService;
		this.validator = validator;
		dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	}
	
	@ModelAttribute("imgUtil")
	public ImageUtil imgUtil() {
		return new ImageUtil();
	}
	
	@ModelAttribute(name = "eventForm")
	public EventForm eventForm() {
		return new EventForm(userService);
	}
	@ModelAttribute(name = "timeList")
	public List<LocalTime> timeList() {
		return meetingService.getTimeList();
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
	public String eventInfo(Model model, @PathVariable("id") long id) {
		model.addAttribute("event", meetingService.findById(id));
		return "event_info";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteEvent(Model model, @PathVariable("id") long id) {
		meetingService.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/new/{date}")
	public String showEventForm(Model model, @PathVariable("date") String date) {
		

		model.addAttribute("date", date);
		
		return "create_event";
	}
	
	@PostMapping("/new/{date}")
	public String createEvent(@ModelAttribute("eventForm") @Valid EventForm eventForm, Errors errors, @AuthenticationPrincipal User user,
			Model model, @PathVariable("date") String date) {
		
		eventForm.setStartDate(LocalDate.parse(date, dtf));
		eventForm.setUserService(userService);
		
		validator.validate(eventForm, errors);
		if (errors.hasErrors()) {
			return "create_event";
		}
		Event event = eventForm.createEvent();
		event.setUser(user);
		meetingService.save(event);
		
		return "redirect:/";
	}
	
	@GetMapping("/update/{id}")
	public String showUpdateForm(@ModelAttribute("eventForm") @Valid EventForm eventForm, Errors errors,
			Model model, @PathVariable("id") Long id) {
		
		Event event = meetingService.findById(id);
		eventForm.loadData(event);
		model.addAttribute("eventForm", eventForm);
		
		return "update_event";
	}
	
	@PostMapping("update/{id}")
	public String updateEvent(@ModelAttribute("eventForm") @Valid EventForm eventForm, Errors errors, @AuthenticationPrincipal User user,
			Model model, @PathVariable("id") Long id) {
		
		validator.validate(eventForm, errors);
		if (errors.hasErrors()) {
			eventForm.setId(id);
			return "update_event";
		}
		
		Event event = eventForm.updateEvent();
		event.setUser(user);
		meetingService.save(event);
		
		return "redirect:/";
	}
}
