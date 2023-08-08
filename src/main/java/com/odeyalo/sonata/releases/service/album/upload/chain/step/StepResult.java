package com.odeyalo.sonata.releases.service.album.upload.chain.step;

import com.odeyalo.sonata.releases.service.album.upload.chain.AlbumUploadingState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class StepResult {
    boolean success;
    AlbumUploadingState state;
    String errorReason;

    public static StepResult success(AlbumUploadingState state) {
        return of(true, state, null);
    }

    public static StepResult failed(String reason) {
        return of(true, null, reason);
    }
}
