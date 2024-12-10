package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entity.SlideshowImage;

public interface SlideshowImageRepository extends JpaRepository<SlideshowImage, Long>  {
}
