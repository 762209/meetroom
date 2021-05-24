package meetroom.db;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import meetroom.data.EventRepository;
import meetroom.data.UserRepository;
import meetroom.domain.Event;
import meetroom.domain.Photo;
import meetroom.domain.User;

@Component
public class Data {
	@Autowired
	private EventRepository eventRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init() throws IOException {
		byte[] img1 = Files.readAllBytes(new ClassPathResource("/static/images/korjic.jpg").getFile().toPath());
		byte[] img2 = Files.readAllBytes(new ClassPathResource("/static/images/zeliboba.jpg").getFile().toPath());
		byte[] img3 = Files.readAllBytes(new ClassPathResource("/static/images/eliaz.jpg").getFile().toPath());
		
		User user1 = new User("alexei.stratonov", passwordEncoder.encode("123"), "Alexei", "Stratonov");
		user1.setPhoto(new Photo(img1));
		User user2 = new User("alexei.filipov", passwordEncoder.encode("123"), "Alexei", "Filipov");
		user2.setPhoto(new Photo(img2));
		User user3 = new User("dmitrii.kuznetsov", passwordEncoder.encode("123"), "Dmitrii", "Kuznetsov");
		user3.setPhoto(new Photo(img3));
		
		userRepo.save(user1); userRepo.save(user2); userRepo.save(user3);
		
		LocalDateTime start = LocalDateTime.now();
		LocalDateTime end = LocalDateTime.now().plusHours(3);
		
		Event event = new Event(start, end, "Главная", "Description");
		event.setGuest(user2);
		event.setUser(user1);
		eventRepo.save(event);
		
		Event event2 = new Event(start.plusDays(2), end.plusDays(2), "Главная", "Description");
		event2.setGuest(user3);
		event2.setUser(user2);
		eventRepo.save(event2);
	}
}
