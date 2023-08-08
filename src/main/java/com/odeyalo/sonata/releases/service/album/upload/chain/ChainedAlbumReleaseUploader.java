package com.odeyalo.sonata.releases.service.album.upload.chain;

import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;
import com.odeyalo.sonata.releases.service.album.upload.AlbumReleaseUploader;
import com.odeyalo.sonata.releases.service.album.upload.UploadResult;
import com.odeyalo.sonata.releases.service.album.upload.chain.step.AlbumUploadingStep;
import com.odeyalo.sonata.releases.service.album.upload.chain.step.StepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ChainedAlbumReleaseUploader implements AlbumReleaseUploader {
    private final List<AlbumUploadingStep> steps;
    private final Logger logger = LoggerFactory.getLogger(ChainedAlbumReleaseUploader.class);

    public ChainedAlbumReleaseUploader(List<AlbumUploadingStep> steps) {
        Collections.sort(steps, (a, b) -> a.getOrder().getIndex() - b.getOrder().getIndex());
        this.steps = steps;
        this.logger.info("Initialized steps with: {}", steps);
    }

    @Override
    public UploadResult uploadAlbum(AlbumReleaseDto body) {
        AlbumUploadingState state = AlbumUploadingState.initialState(body);
        for (AlbumUploadingStep step : steps) {
            StepResult upload = step.upload(state);
            if (!upload.isSuccess()) {
                return UploadResult.failed(upload.getErrorReason());
            }
            state = upload.getState();
        }
        return UploadResult.success(state.getAlbum().getPublicId());
    }
}
