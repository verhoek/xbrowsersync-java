package com.github.verhoek.xbrowsersync.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;


@JsonDeserialize
@Data
public class ServerInfo {
    String location;

    @JsonProperty
    Integer maxSyncSize;

    String message;

    @JsonProperty
    int status;

    @JsonProperty
    String version;

    public ServerInfo(String version) {
        status = 1;
        this.version = version;
        maxSyncSize = 3000000;
    }
}
