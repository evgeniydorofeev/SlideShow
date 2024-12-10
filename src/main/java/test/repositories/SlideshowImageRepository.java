package test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entities.SlideshowImage;

public interface SlideshowImageRepository extends JpaRepository<SlideshowImage, Long>  {
}
