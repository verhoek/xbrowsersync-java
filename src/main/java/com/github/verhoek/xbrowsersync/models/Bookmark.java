package com.github.verhoek.xbrowsersync.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;



@Entity
@Data
@Table(name = "bookmark")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {
    String version;

    @Id
    String id;

    String data;

    Instant lastUpdated;

    public Bookmark(String version) {
        id = UUID.randomUUID().toString().replace("-", "");
        lastUpdated = Instant.now();
        this.version = version;
    }

    public boolean copyBookmarkData(Bookmark newBookmark) {
        if (lastUpdated != null && newBookmark.lastUpdated.isBefore(lastUpdated)) {
            return false;
        }

        data = newBookmark.data;
        lastUpdated = Instant.now();
        return true;
    }
}
