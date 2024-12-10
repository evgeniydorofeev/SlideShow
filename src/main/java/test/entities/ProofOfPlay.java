package test.entities;

import java.time.Instant;

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
public class ProofOfPlay extends EntityBase {

	@NotNull
	private Instant createdDate;

	@ManyToOne
	@NotNull
	private Slideshow slideshow;

	@ManyToOne
	@NotNull
	private Image image;
}
