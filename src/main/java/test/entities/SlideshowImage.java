package test.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class SlideshowImage extends EntityBase {
	
	@NotNull
	@ManyToOne
	private Image image;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@NotNull
	private Slideshow slideshow;

	@NotNull
	private Integer slideOrder;
	
}

