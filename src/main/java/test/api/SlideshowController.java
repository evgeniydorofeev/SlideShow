package test.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import test.dto.ImageDto;
import test.dto.ImageWithSlideshowDto;
import test.dto.SlideshowDto;
import test.entities.Image;
import test.entities.Slideshow;
import test.entities.SlideshowImage;
import test.events.ProofOfPlayEvent;
import test.repositories.ImageRepository;
import test.repositories.SlideshowImageRepository;
import test.repositories.SlideshowRepository;

@RestController
@RequiredArgsConstructor
public class SlideshowController {
	private final ImageRepository imageRepository;
	private final SlideshowRepository slideshowRepository;
	private final SlideshowImageRepository slideshowImageRepository;
	private final ApplicationEventPublisher eventPublisher;
	
	/**
	 * Create a new Image entity.
	 * 
	 * @param   dto the new Image data.
	 * @return  ID of the created entity. 
	 */
	@PostMapping("/addImage")
	public Long addImage(@RequestBody ImageDto dto) {
		Image image = new Image(dto.name(), dto.url(), dto.duration());
		return imageRepository.save(image).getId();
	}

	/**
	 * Remove an Image by its ID.
	 * 
	 * @param id the Image ID.
	 */
	@DeleteMapping("/deleteImage/{id}")
	public void deleteImage(@PathVariable("id") long id) {
		imageRepository.deleteById(id);
	}

	/**
	 * Create a new Slideshow entity. 
	 * 
	 * @param  dto the Slideshow data.
	 * @return the created entity ID.
	 */
	@PostMapping("/addSlideshow")
	@Transactional
	public Long addSlideshow(@RequestBody SlideshowDto dto) {
		Slideshow slideShow = slideshowRepository.save(new Slideshow(dto.name(), null));
		List<Image> images = imageRepository.findAllById(dto.imageIds());
		List<SlideshowImage> slideShowImages = new ArrayList<>();
		for (int i = 0; i < images.size(); i++) {
			slideShowImages.add(new SlideshowImage(images.get(i), slideShow, i));
		}
		slideshowImageRepository.saveAll(slideShowImages);
		return slideShow.getId();
	}

	/**
	 * Remove a Slideshow by its ID.
	 * 
	 * @param the SlideShow ID.
	 */
	@DeleteMapping("/deleteSlideshow/{id}")
	@Transactional
	public void deleteSlideshow(@PathVariable("id") long slideShowId) {
		slideshowRepository.deleteById(slideShowId);
	}

	/**
	 * Retrieve images and slideshows found images belong to based on keywords in the URL or duration.
	 * 
	 * If both duration and keyword are specified then both parameters should match. 
	 * 
	 * @param  duration in milliseconds
	 * @param  keyword keyword 
	 * @return Images that meet the specified search criteria.
	 */
	@GetMapping("/images/search")
	public List<ImageWithSlideshowDto> imageSearch(@RequestParam(name = "duration", required = false) Long duration,
			@RequestParam(name = "keyword", required = false) String keyword) {
		if (duration == null && keyword == null) {
			throw new IllegalStateException("Either duration or keyword parameters must be specified in the request");
		}
		return imageRepository.findImageWithSlideshow(duration, keyword);
	}

	/**
	 *  Get a Slideshow images ordered by duration.
	 * 
	 * @param  slideShowId the Slideshow ID.
	 * @return the Slideshow images ordered by duration.
	 */
	@GetMapping("/slideShow/{id}/slideshowOrder")
	public List<ImageDto> getSlideshowImages(@PathVariable("id") long slideShowId) {
		return imageRepository.getImagesBySlideshowId(slideShowId);
	}

	/**
	 * When an Image is being replaced to the next one, record this event as a new ProofOfPlay entity.
	 * 
	 * @param slideShowId the Slideshow ID.
	 * @param imageId the Image ID.
	 */
	@GetMapping("/slideShow/{id}/proof-of-play/{imageId}")
	public void proofOfPlay(@PathVariable("id") long slideShowId, @PathVariable("imageId") long imageId) {
		eventPublisher.publishEvent(new ProofOfPlayEvent(this, slideShowId, imageId));
	}

}
