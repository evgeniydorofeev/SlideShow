package test.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityBase {
	
	@Id
	@GeneratedValue
	private Long id;
	
}
