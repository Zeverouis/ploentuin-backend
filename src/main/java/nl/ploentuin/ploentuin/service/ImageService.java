package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.image.ImageResponseDto;
import nl.ploentuin.ploentuin.dto.image.ImageCreateDto;
import nl.ploentuin.ploentuin.dto.image.ImageUpdateDto;
import nl.ploentuin.ploentuin.model.Image;
import nl.ploentuin.ploentuin.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private ImageResponseDto toDto(Image img) {
        return new ImageResponseDto(
                img.getId(),
                img.getParentId(),
                img.getParentType(),
                img.getUserId(),
                img.getCaption(),
                img.getCreatedAt(),
                img.getUpdatedAt()
        );
    }

    public List<ImageResponseDto> createImages(int parentId, Image.ParentType parentType, int userId,
                                               ImageCreateDto dto) {
        List<ImageResponseDto> savedImages = new ArrayList<>();
        String[] captions = dto.getCaptions();

        MultipartFile[] files = dto.getImages();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];

                String caption = (captions != null && captions.length > i && captions[i] != null && !captions[i].isBlank())
                        ? captions[i].trim()
                        : null;

                try {
                    byte[] data = file.getBytes();
                    Image img = new Image(parentId, userId, caption, parentType, data);
                    imageRepository.save(img);
                    savedImages.add(toDto(img));
                } catch (IOException e) {
                    System.err.println("Failed to process uploaded file " + file.getOriginalFilename());
                }
            }
        }

        String[] urls = dto.getUrls();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                String urlStr = urls[i];
                if (urlStr == null || urlStr.isBlank()) continue;

                String caption = (captions != null && captions.length > i && captions[i] != null
                        && !captions[i].isBlank())
                        ? captions[i].trim()
                        : null;

                try (InputStream in = new URL(urlStr).openStream()) {
                    byte[] data = in.readAllBytes();
                    Image img = new Image(parentId, userId, caption, parentType, data);
                    imageRepository.save(img);
                    savedImages.add(toDto(img));
                } catch (IOException e) {
                    System.err.println("Failed to download image from URL: " + urlStr);
                }
            }
        }

        if (savedImages.isEmpty()) {
            throw new IllegalArgumentException("No valid images provided");
        }

        return savedImages;
    }

    public void deleteImage(int imageId, int userId) {
        Image img = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Afbeelding niet gevonden"));

        if (img.getUserId() != userId) {
            throw new IllegalArgumentException("Je hebt geen permissie om deze afbeelding te verwijderen");
        }

        imageRepository.delete(img);
    }

    public ImageResponseDto updateCaption(int imageId, int userId, ImageUpdateDto dto) {
        Image img = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Afbeelding niet gevonden"));

        if (img.getUserId() != userId) {
            throw new IllegalArgumentException("Je hebt geen permissie om deze afbeelding te bewerken");
        }

        img.setCaption(dto.getCaption() != null ? dto.getCaption().trim() : null);

        return toDto(imageRepository.save(img));
    }
}