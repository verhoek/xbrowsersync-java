package com.github.verhoek.xbrowsersync.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@JsonDeserialize
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookmarkRequestDTO {
    @JsonProperty
    @NonNull
    String version;
}
