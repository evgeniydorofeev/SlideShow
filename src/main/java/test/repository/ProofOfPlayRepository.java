package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entity.ProofOfPlay;

public interface ProofOfPlayRepository extends JpaRepository<ProofOfPlay, Long>  {
}
