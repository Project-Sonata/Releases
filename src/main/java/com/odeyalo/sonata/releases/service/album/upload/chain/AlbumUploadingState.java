package com.odeyalo.sonata.releases.service.album.upload.chain;

import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Track;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class AlbumUploadingState {
    // Null if album is not uploaded yet
    Album album;
    // Null if tracks are not uploaded yet
    List<Track> tracks;
    AlbumReleaseDto albumReleaseDto;
    Order previousStep;

    public static AlbumUploadingState initialState(AlbumReleaseDto body) {
        return of(null, null, body, null);
    }
}
