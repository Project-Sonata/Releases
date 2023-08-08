package com.testing.faker;

import com.odeyalo.sonata.releases.entity.Artist;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;

@Accessors(chain = true)
@Data
public class ArtistFaker {
    String publicId;

    public ArtistFaker() {
        this.publicId = RandomStringUtils.random(30);
    }

    public static ArtistFaker create() {
        return new ArtistFaker();
    }

    public Artist get() {
        return Artist.builder()
                .publicId(publicId)
                .build();
    }
}
