package meetroom.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import meetroom.data.EventRepository;
import meetroom.domain.Event;
import meetroom.utils.DateUtil;

@Service
public class MeetingService {
	private EventRepository repository;
	
	public MeetingService(EventRepository repository) {
		this.repository = repository;
	}
	
	public List<LocalDate> getDateList(int firstDay, int lastDay) {
		List<LocalDate> dateList = new ArrayList<LocalDate>(7);
		for (int dayNumber = firstDay; dayNumber < lastDay; dayNumber++)
			dateList.add(DateUtil.currentMonday.minusDays(7).plusDays(dayNumber));
		return dateList;
	}
	
	public HashMap<LocalTime, Event[]> getEventsMap(int pageNumber) {
		List<LocalTime> timeList = getTimeList();
		HashMap<LocalTime, Event[]> eventsMap = new LinkedHashMap<LocalTime, Event[]>(24);
		for (int i = 0; i < timeList.size(); i++) {
			LocalTime time = timeList.get(i);
			Event[] events = getDateList(0 + (7 * pageNumber), 7 + (7 * pageNumber)).stream().map(date -> repository.getEventBetweenDates(LocalDateTime.of(date, time)))
																							 .toArray(Event[]::new);
			eventsMap.put(time, events);
		}
		return eventsMap;
	}
	
	public PagedListHolder<LocalDate> getDatePagedListHolder() {
		PagedListHolder<LocalDate> page = new PagedListHolder<LocalDate>(getDateList(0, 21));
		page.setPageSize(7);
		return page;
	}
	public List<LocalTime> getTimeList() {
		List<LocalTime> timeList = new ArrayList<LocalTime>(24);
		for (int hours = 0; hours < 24; hours++)
			timeList.add(LocalTime.MIDNIGHT.plusHours(hours));
		return timeList;
	}
 	public Event getEventBetweenDates(LocalDateTime date) {
		return repository.getEventBetweenDates(date);
	}
	public Event findById(Long id) {
		return repository.findById(id).orElseThrow(NoSuchElementException::new);
	}
	public Event save(Event event) {
		return repository.save(event);
	}
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
