package br.org.facc10.images.repositories;

import br.org.facc10.images.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
