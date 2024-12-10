package test.listener;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import test.entity.Image;
import test.entity.ProofOfPlay;
import test.entity.Slideshow;
import test.events.ProofOfPlayEvent;
import test.repository.ImageRepository;
import test.repository.ProofOfPlayRepository;
import test.repository.SlideshowRepository;

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
