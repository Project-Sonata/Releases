package com.testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;
import com.odeyalo.sonata.releases.dto.ReleaseDto;
import com.odeyalo.sonata.releases.dto.UploadTrackDto;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Release;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Accessors(chain = true)
@Data
public class AlbumReleaseDtoFaker {
    String name;
    List<String> genres;
    List<String> artists;
    Album.AlbumType albumType;
    LocalDate releaseDate;
    List<UploadTrackDto> tracks;
    String thumbnailFilename;
    int totalTrackCount;

    final AtomicInteger currentTrackIndex = new AtomicInteger();
    final Faker faker = new Faker();

    public AlbumReleaseDtoFaker() {
        this.name = faker.music().chord();
        this.genres = generateRandomGenres(3);
        this.artists = generateRandomArtistIds(3);
        this.albumType = faker.options().option(Album.AlbumType.class);
        this.releaseDate = faker.date().future(365, TimeUnit.DAYS).toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        this.tracks = generateRandomTracks(5);
        this.thumbnailFilename = faker.file().fileName("", null, "jpg", "");
        this.totalTrackCount = tracks.size();
    }

    public static AlbumReleaseDtoFaker create() {
        return new AlbumReleaseDtoFaker();
    }

    public AlbumReleaseDto get() {
        return AlbumReleaseDto.builder()
                .name(name)
                .genres(genres)
                .artistIds(artists)
                .albumType(albumType)
                .type(ReleaseDto.ReleaseType.ALBUM)
                .releaseDate(releaseDate)
                .tracks(tracks)
                .releaseDatePrecision(Release.ReleaseDatePrecision.DAY)
                .thumbnailUrl(thumbnailFilename)
                .totalTrackCount(totalTrackCount)
                .build();
    }


    private List<String> generateRandomGenres(int maxCount) {
        int count = faker.random().nextInt(1, maxCount);
        return generateListWithSupplier(count, () -> faker.music().genre());
    }

    private List<String> generateRandomArtistIds(int maxCount) {
        int count = faker.random().nextInt(1, maxCount);
        return generateListWithSupplier(count, () -> faker.random().hex(12));
    }

    private List<UploadTrackDto> generateRandomTracks(int maxCount) {
        int count = faker.random().nextInt(1, maxCount);
        return generateListWithSupplier(count, () -> UploadTrackDtoFaker.withIndex(currentTrackIndex.getAndIncrement()).get());
    }

    private <T> List<T> generateListWithSupplier(int count, Supplier<T> supplier) {
        List<T> target = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            target.add(supplier.get());
        }
        return target;
    }
}
