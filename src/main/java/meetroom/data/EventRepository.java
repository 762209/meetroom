package meetroom.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import meetroom.domain.Event;
@Repository
public interface EventRepository  extends CrudRepository<Event, Long> {
	List<Event> findAll();

	@Query(value = "from Event where startDate between :startDate AND :endDate AND endDate between :startDate AND :endDate AND id != :id")
	Event getEventBetweenDates(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate, @Param("id")Long id);
	
	@Query(value = "from Event where startDate between :startDate AND :endDate AND endDate between :startDate AND :endDate")
	Event getEventBetweenDates(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
	
	@Query(value = "from Event where startDate <= :date AND endDate > :date")
	Event getEventBetweenDates(@Param("date")LocalDateTime date);
}