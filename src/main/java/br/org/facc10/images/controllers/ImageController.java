package br.org.facc10.images.controllers;

import br.org.facc10.images.entities.Image;
import br.org.facc10.images.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("uploads").resolve(filename);
            Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Ou o tipo MIME correto
                        .body(resource);
            } else {
                throw new RuntimeException("Imagem não encontrada");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao buscar imagem", e);
        }
    }

    // Endpoint para fazer upload de imagem
    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            Image uploadedImage = imageService.uploadImage(file);
            return ResponseEntity.ok(uploadedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint para excluir uma imagem
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        try {
            imageService.deleteImage(id);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint para obter todas as imagens
    @GetMapping
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    // Endpoint para atualizar a ordem de exibição da imagem
    @PutMapping("/update-order")
    public ResponseEntity<Void> updateOrder(@RequestParam Long id, @RequestParam int newOrder) {
        imageService.updateDisplayOrder(id, newOrder);
        return ResponseEntity.ok().build();
    }
}