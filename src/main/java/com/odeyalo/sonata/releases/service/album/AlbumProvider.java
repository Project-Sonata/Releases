package com.odeyalo.sonata.releases.service.album;

import com.odeyalo.sonata.releases.dto.AlbumDTO;

/**
 * Provide the info about the album based on id
 */
public interface AlbumProvider {

    AlbumDTO findAlbum(String id);

}
