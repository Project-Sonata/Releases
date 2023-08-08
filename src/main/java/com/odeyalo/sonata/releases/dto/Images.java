package com.odeyalo.sonata.releases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "empty")
@Builder
public final class Images {
    @JsonProperty("images")
    @Singular
    List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        this.images.add(image);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
    }

    public Image get(int index) {
        return images.get(index);
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @RequiredArgsConstructor(staticName = "of")
    @Builder
    public static final class Image {
        final String url;
        int width;
        int height;
    }
}
