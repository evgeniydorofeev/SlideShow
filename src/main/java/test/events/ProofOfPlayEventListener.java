package test.events;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import test.entities.Image;
import test.entities.ProofOfPlay;
import test.entities.Slideshow;
import test.repositories.ImageRepository;
import test.repositories.ProofOfPlayRepository;
import test.repositories.SlideshowRepository;

@Component
public class ProofOfPlayEventListener {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private SlideshowRepository slideshowRepository;

	@Autowired
	private ProofOfPlayRepository proofOfPlayRepository;

	@EventListener
	@Transactional
	public void handleUserRegistration(ProofOfPlayEvent event) {
		Slideshow slideshow = slideshowRepository.findById(event.getSlideshowId()).orElseThrow();
		Image image = imageRepository.findById(event.getImageId()).orElseThrow();
		proofOfPlayRepository.save(new ProofOfPlay(Instant.now(), slideshow, image));
	}
}
