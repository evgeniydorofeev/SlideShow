package test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.dto.ImageDto;
import test.dto.ImageWithSlideshowDto;
import test.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	@Query("""
			select new test.dto.ImageDto(i.id, i.name, i.url, i.duration)
			from Image i
			join SlideshowImage si on si.image = i
			join Slideshow s on s = si.slideshow
			where s.id = :slideShowId
			order by i.duration
		   """)
	List<ImageDto> getImagesBySlideshowId(@Param("slideShowId") long slideShowId);

	@Query("""
			select new test.dto.ImageWithSlideshowDto(i.id, i.name, i.url, i.duration, s.id, s.name)
			from Image i
			join SlideshowImage si on si.image = i
			join fetch Slideshow s on s = si.slideshow
			where
			(:duration is null or i.duration = :duration)
			and
			(:keyword is null or i.url like %:keyword%)
		   """)
	List<ImageWithSlideshowDto> findImageWithSlideshow(Long duration, String keyword);
}
