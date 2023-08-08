package com.odeyalo.sonata.releases.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odeyalo.sonata.releases.dto.AlbumUploadReleaseDto;
import com.odeyalo.sonata.releases.dto.AlbumUploadResultDto;
import com.odeyalo.sonata.releases.entity.Album;
import com.odeyalo.sonata.releases.entity.Artist;
import com.odeyalo.sonata.releases.repository.AlbumRepository;
import com.odeyalo.sonata.releases.repository.ArtistRepository;
import com.odeyalo.sonata.releases.repository.TrackRepository;
import com.testing.faker.UploadAlbumReleaseDtoFaker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UploadAlbumReleaseControllerTest extends UploadReleaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    TrackRepository trackRepository;

    Artist artist;

    public static final String EMPTY_CONTENT = null;

    @BeforeEach
    void setup() {
        artist = this.artistRepository.save(
                Artist.builder()
                        .publicId("odeyalooo")
                        .build());
    }

    @AfterEach
    void clear() {
        this.albumRepository.deleteAll();
        this.trackRepository.deleteAll();
        this.artistRepository.deleteAll();
    }

    @Test
    void shouldReturnStatus200() throws Exception {
        ResultActions actions = prepareValidAndSend();

        actions.andExpect(status().isOk());
    }

    @Test
    void shouldReturnApplicationJsonContentType() throws Exception {
        ResultActions actions = prepareValidAndSend();

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnIdInBody() throws Exception {
        MvcResult result = prepareValidAndSend().andReturn();

        AlbumUploadResultDto body = parseBody(result, AlbumUploadResultDto.class);

        assertNotNull(body.getId());
    }


    @Test
    void shouldSaveAlbum() throws Exception {
        MvcResult result = prepareValidAndSend().andReturn();

        AlbumUploadResultDto body = parseBody(result, AlbumUploadResultDto.class);

        Optional<Album> optionalAlbum = albumRepository.findByPublicId(body.getId());

        assertTrue(optionalAlbum.isPresent(), "If album was saved, then it must be saved in database!");
    }

    @Test
    @Transactional
        // Must be better workaround for this problem. Haven't found better solution yet
    void albumShouldContainTracks() throws Exception {
        MvcResult result = prepareValidAndSend().andReturn();

        AlbumUploadResultDto body = parseBody(result, AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(body.getId()).get();

        assertNotNull(album.getTracks());
        assertTrue(album.getTracks().size() > 0, "The tracks must be saved!");
    }

    @Test
    @Transactional
    void shouldContainArtists() throws Exception {
        RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> pair = prepareValidBodyAsPair();
        AlbumUploadResultDto responseBody = parseBody(pair.mvcResult(), AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(responseBody.getId()).get();

        List<String> artistIds = pair.body().getRelease().getArtistIds();

        album.getArtists().forEach(artist -> {
            String expectedId = artist.getPublicId();
            assertTrue(artistIds.contains(expectedId), String.format("Artist with id: %s must be saved", expectedId));
        });
    }


    @Test
    @Transactional
    void shouldContainValidReleaseDate() throws Exception {
        RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> pair = prepareValidBodyAsPair();

        AlbumUploadResultDto responseBody = parseBody(pair.mvcResult(), AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(responseBody.getId()).get();

        assertEquals(pair.body.getRelease().getReleaseDate(), album.getReleaseDate(), "The release data must be equal!");
        assertEquals(pair.body.getRelease().getReleaseDatePrecision(), album.getReleaseDatePrecision(), "Release data precision must be equal!");
    }

    @Test
    void shouldContainAlbumName() throws Exception {
        RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> pair = prepareValidBodyAsPair();

        AlbumUploadResultDto responseBody = parseBody(pair.mvcResult(), AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(responseBody.getId()).get();

        assertEquals(pair.body.getRelease().getName(), album.getName());
    }

    @Test
    void shouldContainValidAlbumType() throws Exception {
        RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> pair = prepareValidBodyAsPair();

        AlbumUploadResultDto responseBody = parseBody(pair.mvcResult(), AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(responseBody.getId()).get();

        assertEquals(pair.body.getRelease().getAlbumType(), album.getAlbumType());
    }

    @Test
    void shouldContainValidTrackCount() throws Exception {
        RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> pair = prepareValidBodyAsPair();

        AlbumUploadResultDto responseBody = parseBody(pair.mvcResult(), AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(responseBody.getId()).get();

        assertEquals(pair.body.getRelease().getTotalTrackCount(), album.getTotalTrackCount());
    }

    @Test
    void durationMsMustBeValid() throws Exception {
        RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> pair = prepareValidBodyAsPair();

        AlbumUploadResultDto responseBody = parseBody(pair.mvcResult(), AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(responseBody.getId()).get();

        assertEquals(pair.body.getRelease().getDurationMs(), album.getDurationMs());
    }

    @Test
    @Disabled("THe test is disabled because requirement with images have been changed")
    void thumbnailShouldBeValid() throws Exception {
        RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> pair = prepareValidBodyAsPair();

        AlbumUploadResultDto responseBody = parseBody(pair.mvcResult(), AlbumUploadResultDto.class);

        Album album = albumRepository.findByPublicId(responseBody.getId()).get();

//        assertEquals(pair.body.getRelease().getThumbnailUrl(), album.getThumbnailUrl());
    }

    @Test
    void sendEmptyBody_andExpect400() throws Exception {
        ResultActions resultActions = sendUploadAlbumRequestAndReturn(EMPTY_CONTENT);

        resultActions.andExpect(status().isBadRequest());
    }

    private RequestBodyWithMvcResultPair<AlbumUploadReleaseDto> prepareValidBodyAsPair() throws Exception {
        AlbumUploadReleaseDto body = getValidBody();
        String content = toContent(body);

        MvcResult result = sendUploadAlbumRequestAndReturn(content).andReturn();

        return RequestBodyWithMvcResultPair.of(body, result);
    }

    private <T> T parseBody(MvcResult result, Class<T> target) throws Exception {
        String content = result.getResponse().getContentAsString();
        return objectMapper.readValue(content, target);
    }

    private ResultActions prepareValidAndSend() throws Exception {
        String jsonContent = prepareValidRequestBodyContent();
        return sendUploadAlbumRequestAndReturn(jsonContent);
    }

    private String prepareValidRequestBodyContent() throws JsonProcessingException {
        AlbumUploadReleaseDto release = getValidBody();
        return toContent(release);
    }

    private String toContent(AlbumUploadReleaseDto release) throws JsonProcessingException {
        return objectMapper.writeValueAsString(release);
    }

    private AlbumUploadReleaseDto getValidBody() {
        AlbumUploadReleaseDto release = UploadAlbumReleaseDtoFaker.createAlbumRelease().get();
        release.getRelease().setArtistIds(Collections.singletonList(artist.getPublicId()));
        return release;
    }

    private ResultActions sendUploadAlbumRequestAndReturn(String jsonContent) throws Exception {
        MockHttpServletRequestBuilder builder = post("/release/upload/album")
                .contentType(MediaType.APPLICATION_JSON);
        // Empty content can be changed in the future, so now warning should be ignored
        if (jsonContent != null && !jsonContent.equals(EMPTY_CONTENT)) {
            builder.content(jsonContent);
        }

        return mockMvc.perform(builder);
    }

    private record RequestBodyWithMvcResultPair<B>(B body, MvcResult mvcResult) {

        public static <B> RequestBodyWithMvcResultPair<B> of(B body, MvcResult mvcResult) {
            return new RequestBodyWithMvcResultPair<>(body, mvcResult);
        }
    }
}
