package test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entities.Slideshow;

public interface SlideshowRepository extends JpaRepository<Slideshow, Long>  {
}
