package com.odeyalo.sonata.releases.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odeyalo.sonata.releases.dto.AlbumDTO;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Track;
import com.odeyalo.sonata.releases.repository.AlbumRepository;
import com.odeyalo.sonata.releases.repository.ArtistRepository;
import com.odeyalo.sonata.releases.repository.TrackRepository;
import com.testing.faker.AlbumFaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class AlbumControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    TrackRepository trackRepository;


    Album existingAlbum;

    @BeforeEach
    void setup() {
        Album album = AlbumFaker.create().get();
        Set<Track> tracks = album.getTracks();
        album.getTracks().clear();
        artistRepository.saveAll(album.getArtists());
        Album savedAlbum = albumRepository.save(album);

        tracks.forEach(track -> {
            track.setAlbum(savedAlbum);
            trackRepository.save(track);
        });

        savedAlbum.setTracks(tracks);

        existingAlbum = albumRepository.save(savedAlbum);

    }

    @AfterEach
    void clear() {
        this.albumRepository.deleteAll();
        this.trackRepository.deleteAll();
        this.artistRepository.deleteAll();
    }

    @Test
    void shouldReturnAlbumWithValidName() throws Exception {
        MvcResult mvcResult = sendRequest();
        AlbumDTO body = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AlbumDTO.class);

        assertEquals(existingAlbum.getName(), body.getName());
    }

    @Test
    void shouldReturnAlbumWithValidDate() throws Exception {
        MvcResult mvcResult = sendRequest();
        AlbumDTO body = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AlbumDTO.class);

        assertEquals(existingAlbum.getReleaseDate(), body.getReleaseDate());
        assertEquals(existingAlbum.getReleaseDatePrecision().name().toLowerCase(), body.getReleaseDatePrecision());
    }

    @Test
    void shouldReturnAlbumWithValidAlbumType() throws Exception {
        MvcResult mvcResult = sendRequest();
        AlbumDTO body = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AlbumDTO.class);

        assertEquals(existingAlbum.getAlbumType().name().toLowerCase(), body.getAlbumType());
    }

    @Test
    void shouldReturnAlbumWithValidReleaseType() throws Exception {
        MvcResult mvcResult = sendRequest();
        AlbumDTO body = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AlbumDTO.class);

        assertEquals(existingAlbum.getReleaseType().name().toLowerCase(), body.getReleaseType());
    }

    @Test
    void shouldReturnAlbumWithValidTrackCount() throws Exception {
        MvcResult mvcResult = sendRequest();
        AlbumDTO body = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AlbumDTO.class);

        assertEquals(existingAlbum.getTotalTrackCount(), body.getTotalTrackCount());
    }

    private MvcResult sendRequest() throws Exception {
        return mockMvc.perform(get("/album/{id}", existingAlbum.getPublicId())).andReturn();
    }
}