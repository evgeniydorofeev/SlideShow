package test.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import test.dto.ImageDto;
import test.dto.SlideshowDto;
import test.entity.Image;
import test.repository.ImageRepository;
import test.repository.SlideshowRepository;

@RestController
public class SlideshowController {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private SlideshowRepository slideshowRepository;

	@PostMapping("/addImage")
	public void addImage(@RequestBody ImageDto dto) {
		imageRepository.save(new Image(dto.name(), dto.url(), dto.duration()));
	}

	@DeleteMapping("/deleteImage/{id}")
	public void deleteImage(@PathVariable("id") long id) {
		imageRepository.deleteById(id);
	}

	@PostMapping("/addSlideshow")
	public void addSlideshow(SlideshowDto dto) {
//		slideshowRepository
	}

	@DeleteMapping("/deleteSlideshow/{id}")
	public void deleteSlideshow() {
	}

	@GetMapping("/images/search")
	public void imageSearch() {
	}

	@GetMapping("/slideShow/{id}/slideshowOrder")
	public List<ImageDto> getSlideshowImages(@PathVariable("id") long slideShowId) {
		return slideshowRepository.getSlideshowImages(slideShowId);
	}

	@GetMapping("/slideShow/{id}/proof-of-play/{imageId}")
	public void proofOfPlay() {
	}

}
