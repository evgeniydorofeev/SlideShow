package test.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestClient;

import test.dto.ImageRecord;
import test.entity.Image;
import test.entity.Slideshow;
import test.repository.ImageRepository;
import test.repository.SlideshowRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
public class SlideshowControllerIT {

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private SlideshowRepository slideshowRepository;
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void testGetSlideshowOrder() {
		Image image = imageRepository.save(new Image("test", "test", 1L));
		slideshowRepository.save(new Slideshow("test", Set.of(image)));
		String s = testRestTemplate.getForObject("/slideShow/1/slideshowOrder", String.class);
		System.out.println(s);
		
//		@SuppressWarnings("unchecked")
//		List<ImageRecord> res = restClient.get().uri("/slideShow/1/slideshowOrder").retrieve().body(List.class);
//		System.out.println(res);
//		assertEquals(1, res.size());
	}

}
