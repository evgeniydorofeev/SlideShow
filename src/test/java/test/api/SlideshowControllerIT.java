package test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import lombok.SneakyThrows;
import test.dto.ImageDto;
import test.dto.ImageWithSlideshowDto;
import test.dto.SlideshowDto;
import test.entity.Image;
import test.entity.ProofOfPlay;
import test.entity.Slideshow;
import test.entity.SlideshowImage;
import test.repository.ImageRepository;
import test.repository.ProofOfPlayRepository;
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
	private ProofOfPlayRepository proofOfPlayRepository;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void testAddImage() {
		long id = testRestTemplate.postForObject("/addImage", new ImageDto(null, "image4", "url4", 1L), Long.class);
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
		long id = testRestTemplate.postForObject("/addSlideshow", new SlideshowDto(null, "slideshow2", List.of(1001L)),
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
	
	@ParameterizedTest
	@MethodSource("testImageSearchParams")
	public void testImageSearch(String query, Long imageId, String imageName, Long duration, Long slideshowId, String slideShowName) {
		ImageWithSlideshowDto[] dtos = testRestTemplate.getForObject("/images/search?" + query, ImageWithSlideshowDto[].class);
		assertEquals(1, dtos.length);
		assertEquals(imageId, dtos[0].id());
		assertEquals(imageName, dtos[0].name());
		assertEquals(duration, dtos[0].duration());
		assertEquals(slideshowId, dtos[0].slideshowId());
		assertEquals(slideShowName, dtos[0].slideshowName());
  	}
	
	private static Stream<Arguments> testImageSearchParams() {
	    return Stream.of(
	    		Arguments.of("duration=1", 1001L, "image1", 1L, 1001L, "slideshow1"),
	    		Arguments.of("keyword=url1", 1001L, "image1", 1L, 1001L, "slideshow1")
	    );
	}
	@Test
	public void testGetSlideshowImages() {
		ImageDto[] dtos = testRestTemplate.getForObject("/slideShow/1001/slideshowOrder", ImageDto[].class);
		assertEquals(2, dtos.length);
		assertEquals("image1", dtos[0].name());
		assertEquals("image2", dtos[1].name());
	}
	
	@Test
	@SneakyThrows
	public void testProofOfPlay() {
		testRestTemplate.getForObject("/slideShow/1001/proof-of-play/1001", Void.class);
		Thread.sleep(Duration.ofSeconds(1));
		List<ProofOfPlay> entities = proofOfPlayRepository.findAll();
		assertEquals(1, entities.size());
	}

}
