package com.odeyalo.sonata.releases.service.album.upload.chain;

public enum Order {
    UPLOAD_ALBUM(0),
    UPLOAD_TRACKS(1),
    UPDATE_ALBUM(2);

    private final int index;

    Order(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
