package com.github.verhoek.xbrowsersync.controllers.mappers;

import com.github.verhoek.xbrowsersync.controllers.dtos.*;
import com.github.verhoek.xbrowsersync.models.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    // To DTO
    CreateBookmarkResponseDTO bookmarkToCreateBookmarkDTO(Bookmark bookmark);
    UpdateBookmarkResponseDTO bookmarkToUpdateBookmarkDTO(Bookmark bookmark);
    LastUpdatedBookmarkResponseDTO bookmarkToLastUpdatedBookmarkDTO(Bookmark bookmark);
    VersionBookmarkResponseDTO bookmarkToVersionBookmarkDTO(Bookmark bookmark);
    @Mapping(source="data", target="bookmarks")
    BookmarkResponseDTO bookmarkToBookmarkResponseDTO(Bookmark bookmark);

    // From DTO
    Bookmark createBookmarkDTOToBookmark(CreateBookmarkRequestDTO createBookmarkRequestDTO);
    @Mapping(source="bookmarks", target="data")
    Bookmark updateBookmarkDTOToBookmark(UpdateBookmarkRequestDTO updateBookmarkRequestDTO);


    default String mapInstantToString(Instant instant) {
        return instant.toString();
    }
    default Instant mapStringToInstant(String datetime) { return Instant.parse(datetime); }
}
