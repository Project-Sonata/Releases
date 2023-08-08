package com.odeyalo.sonata.releases.service.album;

import com.odeyalo.sonata.releases.dto.*;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Artist;
import com.odeyalo.sonata.releases.entity.Track;
import com.odeyalo.sonata.releases.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RepositoryDelegateAlbumProvider implements AlbumProvider {
    private final AlbumRepository albumRepository;

    public RepositoryDelegateAlbumProvider(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public AlbumDTO findAlbum(String id) {
        return albumRepository.findByPublicId(id)
                .map(this::toDto)
                .orElse(null);
    }

    public AlbumDTO toDto(Album album) {
        List<Images.Image> images = album.getImages().stream().map(image -> Images.Image.of(image.getUrl(), image.getWidth(), image.getHeight())).toList();
        Set<SimplifiedTrack> tracks = album.getTracks().stream().map(RepositoryDelegateAlbumProvider::toSimplifiedTrack).collect(Collectors.toSet());
        Set<ArtistDTO> artists = album.getArtists().stream().map(RepositoryDelegateAlbumProvider::toArtistDto).collect(Collectors.toSet());

        return AlbumDTO.builder()
                .name(album.getName())
                .artists(artists)
                .id(album.getPublicId())
                .releaseDate(album.getReleaseDate())
                .releaseDatePrecision(album.getReleaseDatePrecision().name().toLowerCase())
                .releaseType(album.getReleaseType().name().toLowerCase())
                .albumType(album.getAlbumType().name().toLowerCase())
                .tracks(tracks)
                .images(Images.builder()
                        .images(images)
                        .build())
                .totalTrackCount(album.getTotalTrackCount())
                .durationMs(album.getDurationMs())
                .build();
    }

    private static ArtistDTO toArtistDto(Artist artist) {
        return ArtistDTO.builder()
                .id(artist.getPublicId())
                .href("https://api.sonata.com/v1/artist/" + artist.getPublicId()) // TODO: CHANGE IT
                .build();
    }

    private static SimplifiedTrack toSimplifiedTrack(Track track) {
        return SimplifiedTrack.builder()
                .id(track.getPublicId())
                .name(track.getName())
                .href("https://api.sonata.com/v1/track/" + track.getPublicId())
                .artists(track.getArtists().stream().map(RepositoryDelegateAlbumProvider::toSimplifiedArtist).collect(Collectors.toSet()))
                .trackNumber(track.getOrder().getIndex())
                .discNumber(track.getOrder().getDiscNumber())
                .isPlayable(true)
                .build();
    }

    private static SimplifiedArtist toSimplifiedArtist(Artist artist) {
        return SimplifiedArtist.builder()
                .id(artist.getPublicId())
                .href("https://api.sonata.com/v1/artist/" + artist.getPublicId())
                .build();
    }
}
