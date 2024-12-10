package test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.dto.ImageDto;
import test.entity.Image;

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
}
