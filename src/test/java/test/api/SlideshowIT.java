package test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
@Sql("/scripts/init.sql")
public class SlideshowIT {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private SlideshowRepository slideshowRepository;
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	public void testAddImage() {
		testRestTemplate.postForObject("/addImage", new ImageDto(null, "name", "url", 1L), Void.class);
		List<Image> list = imageRepository.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@Sql("/scripts/testGetSlideshowImages.sql")
	public void testGetSlideshowImages() {
		ImageDto[] res = testRestTemplate.getForObject("/slideShow/1/slideshowOrder", ImageDto[].class);
		assertEquals(1, res.length);
		assertEquals("name1", res[0].name());
		assertEquals("url1", res[0].url());
		assertEquals(1L, res[0].duration());
	}

}
