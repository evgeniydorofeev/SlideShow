package test.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import test.dto.ImageRecord;
import test.repository.ImageRepository;

@RestController
public class SlideshowController {

	@Autowired
	private ImageRepository imageRepository;

	@PostMapping("/addImage")
	public void addImage() {
	}

	@DeleteMapping("/deleteImage/{id}")
	public void deleteImage() {
	}

	@PostMapping("/addSlideshow")
	public void addSlideshow() {
	}

	@DeleteMapping("/deleteSlideshow/{id}")
	public void deleteSlideshow() {
	}

	@GetMapping("/images/search")
	public void imageSearch() {
	}

	@GetMapping("/slideShow/{id}/slideshowOrder")
	public List<ImageRecord> getSlideshows(@PathVariable("id") long slideShowId) {
		return imageRepository.findImageBySlideshowId(slideShowId);
	}

	@GetMapping("/slideShow/{id}/proof-of-play/{imageId}")
	public void proofOfPlay() {
	}

}
