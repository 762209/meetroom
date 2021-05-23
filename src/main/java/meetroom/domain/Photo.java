package meetroom.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="photos")
public class Photo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private byte[] photo;
	
	public Photo(byte[] photo) {
		this.photo = photo;
	}

}
