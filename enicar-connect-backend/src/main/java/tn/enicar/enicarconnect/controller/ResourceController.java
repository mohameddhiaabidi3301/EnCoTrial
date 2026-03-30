package tn.enicar.enicarconnect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.enicar.enicarconnect.dto.ResourceDTO;
import tn.enicar.enicarconnect.model.ResourceFile;
import tn.enicar.enicarconnect.model.User;
import tn.enicar.enicarconnect.service.FileStorageService;
import tn.enicar.enicarconnect.service.ResourceService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResourceController {

    private final ResourceService resourceService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<ResourceDTO>> getAllResources(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(resourceService.getAllResources(currentUser.getId()));
    }

    // L'upload utilise @RequestParam ou @RequestPart pour parser le FormData du
    // frontend
    @PostMapping
    public ResponseEntity<ResourceDTO> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("icon") String icon,
            @RequestParam("size") String size,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(resourceService.uploadResource(
                file, title, category, icon, size, currentUser.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        resourceService.deleteResource(id, currentUser.getId());
        return ResponseEntity.noContent().build();
    }

    // Téléchargement sécurisé (bien qu'ouverte à tout utilisateur connecté)
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        ResourceFile fileEntity = resourceService.getResourceEntity(id);

        try {
            Path filePath = fileStorageService.getFilePath(fileEntity.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                // Détecter automatiquement le content-type si possible, format octet par défaut
                // pour Download
                String contentType = "application/octet-stream";
                // L'en-tête "attachment" force le navigateur à télécharger
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + fileEntity.getTitle() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
