package meetroom.utils;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import meetroom.data.EventRepository;
import meetroom.data.UserRepository;
import meetroom.forms.EventForm;

@Component
public class EventFormValidator implements Validator {
	private EventRepository eventRepo;
	private UserRepository userRepo;
	
	@Autowired
	public EventFormValidator(EventRepository eventRepo, UserRepository userRepo) {
		this.eventRepo = eventRepo;
		this.userRepo = userRepo;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return EventForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "title", null, "Поле не может быть пустым");
		ValidationUtils.rejectIfEmpty(errors, "description", null, "Поле не может быть пустым");
		EventForm eventForm =(EventForm) target;
		if (eventForm.getDurationHours().getHour() == 0 && eventForm.getDurationMinutes() < 30) {
			errors.rejectValue("durationMinutes", null, "Продолжительность бронирования не может быть меньше 30 минут");
		}
		if (!userRepo.existsByUsername(eventForm.getGuestUsername())) {
			errors.rejectValue("guestUsername", null, "Пользователь не найден");
		}
		LocalDateTime start = LocalDateTime.of(eventForm.getStartDate(), eventForm.getStartTime());
		LocalDateTime end = start.plusHours(eventForm.getDurationHours().getHour()).withMinute(eventForm.getDurationMinutes());
		if (eventRepo.getEventBetweenDates(start, end) != null){
			errors.rejectValue("durationMinutes", null, "На данное время уже существует резерв");
		}
		if (start.isBefore(LocalDateTime.now())) {
			errors.rejectValue("startTime", null, "Нельзя бронировать раньше настоящего времени");
		}
	}
}
