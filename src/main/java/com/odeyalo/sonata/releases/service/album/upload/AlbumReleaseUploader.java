package com.odeyalo.sonata.releases.service.album.upload;

import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;

/**
 * Save the album
 */
public interface AlbumReleaseUploader {

    UploadResult uploadAlbum(AlbumReleaseDto body);

}
