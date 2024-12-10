package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entity.Slideshow;

public interface SlideshowRepository extends JpaRepository<Slideshow, Long>  {
}
