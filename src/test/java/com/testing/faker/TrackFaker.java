package com.testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Artist;
import com.odeyalo.sonata.releases.entity.Track;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.Set;

public class TrackFaker {
    String publicId;
    String name;
    Album album;
    Set<Artist> artists;
    long durationMs;
    String previewUrl;
    Track.Order order;

    final Faker faker = Faker.instance();

    protected TrackFaker(int index) {
        this(null , index);
    }

    public TrackFaker(Album album, int index) {
        this.publicId = RandomStringUtils.random(30);
        this.name = faker.music().key();
        this.album = album;
        this.artists = new HashSet<>();
        initializeArtists();
        this.durationMs = faker.random().nextInt(1000);
        this.previewUrl = faker.internet().url();
        this.order = Track.Order.of(1, index);
    }

    public Track get() {
        return Track.builder()
                .publicId(publicId)
                .name(name)
                .album(album)
                .previewUrl(previewUrl)
                .artists(artists)
                .order(order)
                .build();
    }

    private void initializeArtists() {
        Artist artist = ArtistFaker.create().get();
        this.artists.add(artist);
    }

    public static TrackFaker withAlbum(Album album, int index) {
        return new TrackFaker(album, index);
    }

    public static TrackFaker withoutAlbum(int index) {
        return new TrackFaker(index);
    }
}
