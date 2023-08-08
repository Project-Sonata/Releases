package com.testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.releases.dto.UploadTrackDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class UploadTrackDtoFaker {
    String name;
    int index;
    String fileUrl;

    Faker faker = Faker.instance();

    public UploadTrackDtoFaker(int index) {
        this.name = faker.funnyName().name();
        this.index = index;
        this.fileUrl = faker.internet().url() + ".mp3";

    }

    public static UploadTrackDtoFaker withIndex(int index) {
        return new UploadTrackDtoFaker(index);
    }

    public static UploadTrackDtoFaker withIndexAndFilename(int index, String filename) {
        UploadTrackDtoFaker faker = new UploadTrackDtoFaker(index);
        faker.setFileUrl(filename);
        return faker;
    }

    public UploadTrackDto get() {
        return UploadTrackDto.builder()
                .name(name)
                .discNumber(1)
                .index(index)
                .trackUrl(fileUrl)
                .build();
    }
}
