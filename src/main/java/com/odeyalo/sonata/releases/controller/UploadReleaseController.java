package com.odeyalo.sonata.releases.controller;

import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;
import com.odeyalo.sonata.releases.dto.AlbumUploadReleaseDto;
import com.odeyalo.sonata.releases.dto.AlbumUploadResultDto;
import com.odeyalo.sonata.releases.service.album.upload.AlbumReleaseUploader;
import com.odeyalo.sonata.releases.service.album.upload.UploadResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/release/upload")
public class UploadReleaseController {
    final AlbumReleaseUploader uploader;

    public UploadReleaseController(AlbumReleaseUploader uploader) {
        this.uploader = uploader;
    }

    @PostMapping("/album")
    public ResponseEntity<?> uploadAlbum(@RequestBody AlbumUploadReleaseDto body) {
        UploadResult result = uploader.uploadAlbum(body.getRelease());
        if (result.isSuccess()) {
            AlbumUploadResultDto response = AlbumUploadResultDto.of(result.getId());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(result.getExceptionReason());
    }
}
