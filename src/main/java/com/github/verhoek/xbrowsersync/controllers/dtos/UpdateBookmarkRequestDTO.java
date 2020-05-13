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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookmarkRequestDTO {
    @JsonProperty
    @NonNull
    String bookmarks;

    @JsonProperty
    @NonNull
    String lastUpdated;
}
