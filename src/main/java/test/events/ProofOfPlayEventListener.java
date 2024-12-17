package test.events;

import java.time.Instant;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import test.entities.Image;
import test.entities.ProofOfPlay;
import test.entities.Slideshow;
import test.repositories.ImageRepository;
import test.repositories.ProofOfPlayRepository;
import test.repositories.SlideshowRepository;

@Component
@RequiredArgsConstructor
public class ProofOfPlayEventListener {
	private final ImageRepository imageRepository;
	private final SlideshowRepository slideshowRepository;
	private final ProofOfPlayRepository proofOfPlayRepository;

	@EventListener
	@Transactional
	public void handleUserRegistration(ProofOfPlayEvent event) {
		Slideshow slideshow = slideshowRepository.findById(event.getSlideshowId()).get();
		Image image = imageRepository.findById(event.getImageId()).get();
		proofOfPlayRepository.save(new ProofOfPlay(Instant.now(), slideshow, image));
	}
}
