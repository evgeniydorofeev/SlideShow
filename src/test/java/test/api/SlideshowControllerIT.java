package test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import test.dto.ImageDto;
import test.dto.SlideshowDto;
import test.entity.Image;
import test.entity.Slideshow;
import test.entity.SlideshowImage;
import test.repository.ImageRepository;
import test.repository.SlideshowImageRepository;
import test.repository.SlideshowRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
@Sql("/init.sql")
public class SlideshowControllerIT {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private SlideshowRepository slideshowRepository;

	@Autowired
	private SlideshowImageRepository slideshowImageRepository;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void testAddImage() {
		Long id = testRestTemplate.postForObject("/addImage", new ImageDto(null, "image4", "url4", 1L), Long.class);
		assertNotNull(id);
		Image image = imageRepository.findById(id).orElse(null);
		assertNotNull(image);
		assertEquals("url4", image.getUrl());
		assertEquals(1L, image.getDuration());
	}

	@Test
	public void testDeleteImage() {
		testRestTemplate.delete("/deleteImage/1003");
		List<Image> images = imageRepository.findAll();
		assertEquals(2, images.size());
		assertEquals("image1", images.get(0).getName());
		assertEquals("image2", images.get(1).getName());
	}

	@Test
	public void testAddSlideshow() {
		Long id = testRestTemplate.postForObject("/addSlideshow", new SlideshowDto(null, "slideshow2", List.of(1001L)),
				Long.class);
		Slideshow slideshow = slideshowRepository.findById(id).orElse(null);
		assertNotNull(slideshow);
		assertEquals("slideshow2", slideshow.getName());

		SlideshowImage slideshowImage = slideshowImageRepository.findAll().stream()
				.filter(e -> e.getImage().getName().equals("image1")).findAny().orElse(null);
		assertNotNull(slideshow);
		assertEquals(0, slideshowImage.getSlideOrder());
	}

	@Test
	public void testDeleteSlideshow() {
		testRestTemplate.delete("/deleteSlideshow/1001");
		List<Slideshow> slideshows = slideshowRepository.findAll();
		System.out.println(slideshows);
	}
	
	@Test
	public void testGetSlideshowImages() {
		ImageDto[] dtos = testRestTemplate.getForObject("/slideShow/1001/slideshowOrder", ImageDto[].class);
		assertEquals(2, dtos.length);
		assertEquals("image1", dtos[0].name());
		assertEquals("image2", dtos[1].name());
	}

}
