package test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.dto.ImageDto;
import test.entity.Slideshow;

public interface SlideshowRepository extends JpaRepository<Slideshow, Long>  {

	@Query("""
			select new test.dto.ImageDto(i.id, i.name, i.url, i.duration)
			from Slideshow s
			join SlideshowImage si on si.slideshow = s
			join si.image i
			where s.id = :slideShowId
			order by i.duration
		   """)
	List<ImageDto> getSlideshowImages(@Param("slideShowId") long slideShowId);

}
