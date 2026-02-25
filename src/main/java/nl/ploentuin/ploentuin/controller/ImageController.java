package nl.ploentuin.ploentuin.controller;

import nl.ploentuin.ploentuin.model.Image;
import nl.ploentuin.ploentuin.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<byte[]> serveImage(@PathVariable int id) {
        Image img = imageService.getImageEntity(id);
        byte[] data = img.getData();

        if (data == null || data.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header("Content-Length", String.valueOf(data.length))
                .body(data);
    }
}