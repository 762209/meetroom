package meetroom.forms;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import org.springframework.stereotype.Component;

import lombok.Data;
import meetroom.domain.Event;
import meetroom.security.UserRepositoryUserDetailsService;

@Data
@Component
public class EventForm {
	private UserRepositoryUserDetailsService userService;
	
	private long id;
	private String title;
	private String description;
	private String guestUsername;
	private LocalDate startDate;
	private LocalTime startTime;
	private LocalTime durationHours;
	private int durationMinutes;
	
	public EventForm(UserRepositoryUserDetailsService userService) {
		this.userService = userService;
	}

	public Event createEvent() {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
		Event event = new Event(startDateTime, startDateTime.plusHours(durationHours.getHour()).withMinute(durationMinutes), title, description);
		event.setGuest(userService.loadUserByUsername(guestUsername));
		return event;
	}

	public Event updateEvent() {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
		Event event = new Event(startDateTime, startDateTime.plusHours(durationHours.getHour()).withMinute(durationMinutes), title, description);
		event.setGuest(userService.loadUserByUsername(guestUsername));
		event.setId(id);
		return event;
	}

	public void loadData(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.description = event.getDescription();
		this.guestUsername = event.getGuest().getUsername();
		this.startDate = event.getStartDate().toLocalDate();
	}
	
	

}
