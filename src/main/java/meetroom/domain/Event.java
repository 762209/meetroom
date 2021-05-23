package meetroom.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="events")
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String title;
	private String description;
	@ManyToOne(targetEntity = User.class)
	@JoinColumn()
	private User user;
	@ManyToOne(targetEntity = User.class)
	private User guest;
	
	public Event(LocalDateTime startDate, LocalDateTime endDate, String title, String description) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.description = description;
	}
}
