package com.testing.faker;

import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;
import com.odeyalo.sonata.releases.dto.AlbumUploadReleaseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadAlbumReleaseDtoFaker {
    AlbumReleaseDto albumReleaseDto;

    public UploadAlbumReleaseDtoFaker() {
        this.albumReleaseDto = AlbumReleaseDtoFaker.create().get();
    }

    public static UploadAlbumReleaseDtoFaker createAlbumRelease() {
        return new UploadAlbumReleaseDtoFaker();
    }

    public AlbumUploadReleaseDto get() {
        return AlbumUploadReleaseDto.builder()
                .release(albumReleaseDto)
                .build();
    }
}
