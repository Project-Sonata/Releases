package com.odeyalo.sonata.releases.service.album.upload.chain.step;

import com.odeyalo.sonata.releases.service.album.upload.UploadResult;
import com.odeyalo.sonata.releases.service.album.upload.chain.AlbumUploadingState;
import com.odeyalo.sonata.releases.service.album.upload.chain.Order;

public interface AlbumUploadingStep {

    StepResult upload(AlbumUploadingState state);

    Order getOrder();
}
