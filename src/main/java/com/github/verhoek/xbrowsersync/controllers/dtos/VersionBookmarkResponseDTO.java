package com.github.verhoek.xbrowsersync.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonDeserialize
@Data
@NoArgsConstructor
public class VersionBookmarkResponseDTO {
    @JsonProperty
    String version;
}
