package com.odeyalo.sonata.releases.service.album.upload.chain.step;

import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Artist;
import com.odeyalo.sonata.releases.entity.ImageInfo;
import com.odeyalo.sonata.releases.entity.Release;
import com.odeyalo.sonata.releases.repository.AlbumRepository;
import com.odeyalo.sonata.releases.repository.ArtistRepository;
import com.odeyalo.sonata.releases.repository.TrackRepository;
import com.odeyalo.sonata.releases.service.album.upload.UploadResult;
import com.odeyalo.sonata.releases.service.album.upload.chain.AlbumUploadingState;
import com.odeyalo.sonata.releases.service.album.upload.chain.Order;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class UncompletedAlbumPersistingStep implements AlbumUploadingStep {
    final AlbumRepository albumRepository;
    final ArtistRepository artistRepository;
    private final Logger logger = LoggerFactory.getLogger(UncompletedAlbumPersistingStep.class);

    @Override
    public StepResult upload(AlbumUploadingState state) {
        AlbumReleaseDto release = state.getAlbumReleaseDto();
        List<Artist> artists = resolveArtists(release);

        Album album = buildAlbum(release, artists);

        try {
            album = albumRepository.save(album);
            AlbumUploadingState updatedState = AlbumUploadingState.of(album, null, state.getAlbumReleaseDto(), getOrder());
            logger.info("Successful saved the album. New state is: {}", updatedState);
            return StepResult.success(updatedState);
        } catch (Exception ex) {
            logger.error("Failed to upload the album. \n Current state is: {}", state, ex);
            return StepResult.failed(ex.getMessage());
        }
    }

    @Override
    public Order getOrder() {
        return Order.UPLOAD_ALBUM;
    }

    private Album buildAlbum(AlbumReleaseDto release, List<Artist> artists) {
        Set<ImageInfo> imageInfos = new HashSet<>();
        imageInfos.add(ImageInfo.builder().url(release.getThumbnailUrl()).build());
        return Album.builder()
                .albumType(release.getAlbumType())
                .name(release.getName())
                .images(imageInfos)
                .durationMs(release.getDurationMs())
                .totalTrackCount(release.getTotalTrackCount())
                .publicId(UUID.randomUUID().toString().substring(0, 30).replaceAll("-", ""))
                .releaseDate(release.getReleaseDate())
                .releaseDatePrecision(release.getReleaseDatePrecision())
                .artists(new HashSet<>(artists))
                .releaseType(Release.ReleaseType.ALBUM)
                .build();
    }

    private List<Artist> resolveArtists(AlbumReleaseDto release) {
        List<Artist> artists = new ArrayList<>();

        for (String artistId : release.getArtistIds()) {
            Artist artist = artistRepository.findByPublicId(artistId).orElseThrow(() -> new RuntimeException("The artist id is wrong!"));
            artists.add(artist);
        }
        return artists;
    }
}
