package test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entities.ProofOfPlay;

public interface ProofOfPlayRepository extends JpaRepository<ProofOfPlay, Long>  {
}
