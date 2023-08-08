package com.testing.faker;

import com.odeyalo.sonata.releases.dto.AlbumReleaseDto;
import com.odeyalo.sonata.releases.dto.UploadTrackDto;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Artist;
import com.odeyalo.sonata.releases.entity.Release;
import com.odeyalo.sonata.releases.entity.Track;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Accessors(chain = true)
@Data
public class AlbumFaker {
    Album album;

    public AlbumFaker() {
        AlbumReleaseDto releaseDto = AlbumReleaseDtoFaker.create().get();
        Artist artist = ArtistFaker.create().get();
        this.album = toAlbum(releaseDto, List.of(artist));
        HashSet<Track> tracks = getTracks();
        this.album.setTracks(tracks);
    }

    private HashSet<Track> getTracks() {
        HashSet<Track> tracks = new HashSet<>();
        Track track1 = TrackFaker.withAlbum(album, 0).get();
        Track track2 = TrackFaker.withAlbum(album, 1).get();
        tracks.add(track1);
        tracks.add(track2);
        return tracks;
    }

    public static AlbumFaker create() {
        return new AlbumFaker();
    }

    public Album get() {
        return album;
    }

    private static Album toAlbum(AlbumReleaseDto release, List<Artist> artists) {
        return Album.builder()
                .albumType(release.getAlbumType())
                .name(release.getName())
                .thumbnailUrl(release.getThumbnailUrl())
                .durationMs(release.getDurationMs())
                .totalTrackCount(release.getTotalTrackCount())
                .publicId(UUID.randomUUID().toString().substring(0, 30).replaceAll("-", ""))
                .releaseDate(release.getReleaseDate())
                .releaseDatePrecision(release.getReleaseDatePrecision())
                .artists(new HashSet<>(artists))
                .releaseType(Release.ReleaseType.ALBUM)
                .build();
    }
}
