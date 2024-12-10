package test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.dto.ImageRecord;
import test.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	@Query("""
			select new test.dto.ImageRecord(i.id, i.name, i.url, i.duration)
			from Slideshow s join s.images i
			where s.id = :slideShowId
			order by i.duration
		   """)
	List<ImageRecord> findSlideshowImages(@Param("slideShowId") long slideShowId);
}
