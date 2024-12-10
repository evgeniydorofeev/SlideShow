package test.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Slideshow extends EntityBase {
	private static final long serialVersionUID = 1L;

	@NotNull
	private String name;

	@OneToMany
	private Set<Image> images;
}
