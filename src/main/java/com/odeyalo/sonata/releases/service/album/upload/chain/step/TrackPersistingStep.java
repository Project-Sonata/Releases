package com.odeyalo.sonata.releases.service.album.upload.chain.step;

import com.odeyalo.sonata.releases.dto.UploadTrackDto;
import com.odeyalo.sonata.releases.entity.Track;
import com.odeyalo.sonata.releases.repository.TrackRepository;
import com.odeyalo.sonata.releases.service.album.upload.chain.AlbumUploadingState;
import com.odeyalo.sonata.releases.service.album.upload.chain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TrackPersistingStep implements AlbumUploadingStep {
    private final TrackRepository trackRepository;
    private final Logger logger = LoggerFactory.getLogger(TrackPersistingStep.class);

    public TrackPersistingStep(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public StepResult upload(AlbumUploadingState state) {
        List<UploadTrackDto> tracksDto = state.getAlbumReleaseDto().getTracks();

        List<Track> tracks = new ArrayList<>();

        for (UploadTrackDto trackDto : tracksDto) {
            Track track = Track.builder()
                    .publicId(UUID.randomUUID().toString().substring(0, 30).replaceAll("-", ""))
                    .name(trackDto.getName())
                    .order(Track.Order.of(trackDto.getDiscNumber(), trackDto.getIndex()))
                    .album(state.getAlbum())
                    .artists(state.getAlbum().getArtists()) // Should be changed. Artists can be different for each track
                    .build();
            tracks.add(track);
        }
        try {
            List<Track> savedTracks = trackRepository.saveAll(tracks);
            AlbumUploadingState updatedState = AlbumUploadingState.of(state.getAlbum(), savedTracks, state.getAlbumReleaseDto(), getOrder());
            logger.info("Saved tracks. Current state is: {}", updatedState);
            return StepResult.success(updatedState);
        } catch (Exception ex) {
            logger.error("Failed to upload the album. Current state is: {}", state, ex);
            return StepResult.failed(ex.getMessage());
        }
    }

    @Override
    public Order getOrder() {
        return Order.UPLOAD_TRACKS;
    }
}
