package test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class SlideshowOrder extends EntityBase {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Integer showOrder;

	@NotNull
	private Image image;
	
	@ManyToOne
	@NotNull
	private Slideshow slideshow;
}

