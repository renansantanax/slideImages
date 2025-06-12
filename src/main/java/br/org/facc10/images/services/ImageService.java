package br.org.facc10.images.services;
import br.org.facc10.images.entities.Image;
import br.org.facc10.images.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private final String UPLOAD_DIR = "uploads/";

    // Método para fazer upload de uma imagem
    public Image uploadImage(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + filename);
        if(Files.exists(path)) {
            throw new IllegalArgumentException("Já existe uma imagem com esse nome, tente outro.");
        }
        String normalizedFilePath = path.toString().replace("\\", "/");
        Files.write(path, file.getBytes());

        Image image = new Image();
        image.setFilename(filename);
        image.setFilepath(normalizedFilePath);
        image.setDisplayOrder(0); // Padrão para a ordem de exibição
        return imageRepository.save(image);
    }

    // Método para excluir uma imagem
    public void deleteImage(Long id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
        Files.delete(Paths.get(image.getFilepath())); // Deleta o arquivo físico
        imageRepository.delete(image); // Deleta o registro no banco de dados
    }

    // Método para buscar todas as imagens
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    // Método para atualizar a ordem de exibição
    public void updateDisplayOrder(Long id, int newOrder) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
        image.setDisplayOrder(newOrder);
        imageRepository.save(image);
    }
}
