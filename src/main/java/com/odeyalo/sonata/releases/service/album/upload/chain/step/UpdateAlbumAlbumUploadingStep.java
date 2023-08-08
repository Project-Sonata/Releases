package com.odeyalo.sonata.releases.service.album.upload.chain.step;

import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Track;
import com.odeyalo.sonata.releases.repository.AlbumRepository;
import com.odeyalo.sonata.releases.service.album.upload.chain.AlbumUploadingState;
import com.odeyalo.sonata.releases.service.album.upload.chain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UpdateAlbumAlbumUploadingStep implements AlbumUploadingStep {
    private final AlbumRepository albumRepository;
    private final Logger logger = LoggerFactory.getLogger(UpdateAlbumAlbumUploadingStep.class);

    public UpdateAlbumAlbumUploadingStep(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public StepResult upload(AlbumUploadingState state) {
        Album album = state.getAlbum();
        List<Track> savedTracks = state.getTracks();

        album.setTracks(new HashSet<>(savedTracks));

        try {
            album = albumRepository.save(album);
            AlbumUploadingState updatedState = AlbumUploadingState.of(album, savedTracks, state.getAlbumReleaseDto(), getOrder());
            logger.info("Successful saved the album. New state is: {}", updatedState);
            return StepResult.success(updatedState);
        } catch (Exception ex) {
            logger.error("Failed to upload the album. \n Current state is: {}", state, ex);
            return StepResult.failed(ex.getMessage());
        }
    }

    @Override
    public Order getOrder() {
        return Order.UPDATE_ALBUM;
    }
}
