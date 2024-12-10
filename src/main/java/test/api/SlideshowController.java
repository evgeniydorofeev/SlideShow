package test.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import test.dto.ImageDto;
import test.dto.SlideshowDto;
import test.entity.Image;
import test.entity.Slideshow;
import test.entity.SlideshowImage;
import test.repository.ImageRepository;
import test.repository.SlideshowImageRepository;
import test.repository.SlideshowRepository;

@RestController
public class SlideshowController {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private SlideshowRepository slideshowRepository;
	
	@Autowired
	private SlideshowImageRepository slideshowImageRepository;

	@PostMapping("/addImage")
	public Long addImage(@RequestBody ImageDto dto) {
		Image image = new Image(dto.name(), dto.url(), dto.duration());
		return imageRepository.save(image).getId();
	}

	@DeleteMapping("/deleteImage/{id}")
	public void deleteImage(@PathVariable("id") long id) {
		imageRepository.deleteById(id);
	}

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

	@DeleteMapping("/deleteSlideshow/{id}")
	@Transactional
	public void deleteSlideshow(@PathVariable("id") long slideShowId) {
		slideshowRepository.deleteById(slideShowId);
	}

	@GetMapping("/images/search")
	public void imageSearch() {
	}

	@GetMapping("/slideShow/{id}/slideshowOrder")
	public List<ImageDto> getSlideshowImages(@PathVariable("id") long slideShowId) {
		return imageRepository.getImagesBySlideshowId(slideShowId);
	}

	@GetMapping("/slideShow/{id}/proof-of-play/{imageId}")
	public void proofOfPlay() {
	}

}
