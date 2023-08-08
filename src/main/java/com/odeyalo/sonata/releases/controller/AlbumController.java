package com.odeyalo.sonata.releases.controller;

import com.odeyalo.sonata.releases.dto.AlbumDTO;
import com.odeyalo.sonata.releases.dto.ExceptionMessage;
import com.odeyalo.sonata.releases.service.album.AlbumProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final AlbumProvider albumProvider;

    public AlbumController(AlbumProvider albumProvider) {
        this.albumProvider = albumProvider;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        AlbumDTO dto = albumProvider.findAlbum(id);
        if (dto == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.of("The id is wrong!"));
        }
        return ResponseEntity.ok(dto);
    }
}
