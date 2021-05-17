package meetroom.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
	
	public List<LocalDate> getDateList() {
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		for (int dayNumber = 0; dayNumber < 21; dayNumber++)
			dateList.add(DateUtil.currentMonday.minusDays(7).plusDays(dayNumber));
		return dateList;
	}
	
	public List<LocalDate> getDateListForBookig() {
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		for (int dayNumber = 0; dayNumber <= 14; dayNumber++)
			dateList.add(LocalDate.now().plusDays(dayNumber));
		return dateList;
	}
	public PagedListHolder<LocalDate> getDatePage(int currentPage) {
		PagedListHolder<LocalDate> page = new PagedListHolder<LocalDate>(getDateList());
		page.setPageSize(7);
		page.setPage(currentPage);
		return page;
	}
	public List<LocalTime> getTimeList() {
		List<LocalTime> timeList = new ArrayList<LocalTime>();
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
}
