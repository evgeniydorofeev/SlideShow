package test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class SlideshowImage extends EntityBase {
	
	@NotNull
	@ManyToOne
	private Image image;

	@NotNull
	private Integer slideOrder;
	
	@ManyToOne
	@NotNull
	private Slideshow slideshow;
}

