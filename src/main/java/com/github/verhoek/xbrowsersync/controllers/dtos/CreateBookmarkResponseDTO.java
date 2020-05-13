package com.github.verhoek.xbrowsersync.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;


@JsonDeserialize
@Data
@NoArgsConstructor
public class CreateBookmarkResponseDTO {

    @JsonProperty
    String version;

    @JsonProperty
    String id;

    @JsonProperty
    String lastUpdated;
}
