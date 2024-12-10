package test.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
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

	@NotNull
	private String name;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "slideshow")
	private List<SlideshowImage> slideshowImages;
}
