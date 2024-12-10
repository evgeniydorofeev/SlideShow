package test.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class EntityBase {
	
	@Id
	@GeneratedValue
	private Long id;
	
}
