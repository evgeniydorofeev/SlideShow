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
import test.entity.Image;
import test.repository.ImageRepository;
import test.repository.SlideshowRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
@Sql("/init.sql")
public class SlideshowIT {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private SlideshowRepository slideshowRepository;
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	public void testAddImage() {
		testRestTemplate.postForObject("/addImage", new ImageDto(null, "image0", "url0", 0L), Void.class);
		Image res = imageRepository.findById(1L).orElseThrow();
		assertNotNull(res);
		assertEquals("image0", res.getName());
		assertEquals("url0", res.getUrl());
		assertEquals(0L, res.getDuration());
	}
	
	@Test
	public void testDeleteImage() {
		testRestTemplate.delete("/deleteImage/1003");
		List<Image> list = imageRepository.findAll();
		assertEquals(2, list.size());
	}

	@Test
	public void testGetSlideshowImages() {
		ImageDto[] res = testRestTemplate.getForObject("/slideShow/1001/slideshowOrder", ImageDto[].class);
		assertEquals(2, res.length);
		assertEquals("image1", res[0].name());
		assertEquals("image2", res[1].name());	
	}

}
