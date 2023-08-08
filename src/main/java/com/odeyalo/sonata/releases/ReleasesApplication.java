package com.odeyalo.sonata.releases;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.odeyalo.sonata.releases.repository.*;
import com.odeyalo.sonata.releases.entity.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReleasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReleasesApplication.class, args);
	}


	@Bean
	public ApplicationRunner applicationRunner(AlbumRepository albumRepository,
											   TrackRepository trackRepository,
											   ArtistRepository artistRepository) {

		return (args) -> {
			Artist artist = Artist.builder()
					.id(1L)
					.publicId("mikuuuuu")
					.artistName("odeyalooobeats")
					.build();
			Artist artist2 = Artist.builder()
					.id(2L)
					.publicId("pokpasfi")
					.artistName("BONES")
					.build();

			artistRepository.save(artist);
			artistRepository.save(artist2);

//
//			Album album = Album.builder()
//					.name("Classical")
//					.artist(artist)
//					.build();
//
//			album = albumRepository.save(album);
//
//			Track track1 = Track.builder()
//					.name("It's matter where you are")
//					.album(album)
//					.artist(artist)
//					.build();
//
//			Track track2 = Track.builder()
//					.name("Molly")
//					.album(album)
//					.artist(artist)
//					.artist(artist2)
//					.build();
//
//			track1 = trackRepository.save(track1);
//			track2 = trackRepository.save(track2);
//
//			album.addTrack(track1);
//			album.addTrack(track2);
//
//			albumRepository.save(album);
		};
	}
}
