package test.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image extends EntityBase {
	
	@NotNull
	private String name;
	
	@NotNull
	private String url;
	
	@NotNull
	private Long duration;
}
