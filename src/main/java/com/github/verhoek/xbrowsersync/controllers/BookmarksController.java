package com.github.verhoek.xbrowsersync.controllers;

import com.github.verhoek.xbrowsersync.controllers.dtos.CreateBookmarkRequestDTO;
import com.github.verhoek.xbrowsersync.controllers.dtos.UpdateBookmarkRequestDTO;
import com.github.verhoek.xbrowsersync.controllers.mappers.BookmarkMapper;
import com.github.verhoek.xbrowsersync.models.Bookmark;
import com.github.verhoek.xbrowsersync.repositories.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RefreshScope
@Slf4j
public class BookmarksController {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkMapper bookmarkMapper;


    @Value("${xbs.creation.enabled:false}")
    private Boolean allowBookmarkCreation;

    @PostMapping("/bookmarks")
    public ResponseEntity<?> createBookmark(@RequestBody CreateBookmarkRequestDTO createBookmarkRequestDTO) {

        if (!allowBookmarkCreation) {
            log.info("Request to create bookmarks but disabled.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var bookmark = new Bookmark(createBookmarkRequestDTO.getVersion());

        bookmarkRepository.save(bookmark);

        return new ResponseEntity<>(bookmarkMapper.bookmarkToCreateBookmarkDTO(bookmark), HttpStatus.OK);
    }

    @PutMapping("/bookmarks/{syncId}")
    public ResponseEntity<?> updateBookmark(@PathVariable String syncId,
                                            @RequestBody UpdateBookmarkRequestDTO updateBookmarkRequestDTO)
                                            throws ResponseStatusException {

        var bookmark = bookmarkRepository.findById(syncId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookmarks with " + syncId + " not found.")
        );

        if (bookmark.copyBookmarkData(bookmarkMapper.updateBookmarkDTOToBookmark(updateBookmarkRequestDTO))) {
            bookmarkRepository.save(bookmark);
        }

        log.info("{} - updated bookmarks data with last updated: {}.", syncId, bookmark.getLastUpdated());

        return new ResponseEntity<>(bookmarkMapper.bookmarkToUpdateBookmarkDTO(bookmark), HttpStatus.OK);
    }

    @GetMapping("/bookmarks/{syncId}")
    public ResponseEntity<?> bookmark(@PathVariable String syncId) throws ResponseStatusException {

        var bookmark = bookmarkRepository.findById(syncId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookmarks with " + syncId + " not found.")
        );

        log.info("{} - requested bookmarks data.", syncId);
        return new ResponseEntity<>(bookmarkMapper.bookmarkToBookmarkResponseDTO(bookmark), HttpStatus.OK);
    }

    @GetMapping("/bookmarks/{syncId}/lastUpdated")
    public ResponseEntity<?> lastUpdatedBookmark(@PathVariable String syncId) throws ResponseStatusException {

        var bookmark = bookmarkRepository.findById(syncId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookmarks with " + syncId + " not found.")
        );

        log.info("{} - requested lastupdated {}.", syncId, bookmark.getLastUpdated());
        return new ResponseEntity<>(bookmarkMapper.bookmarkToLastUpdatedBookmarkDTO(bookmark), HttpStatus.OK);
    }

    @GetMapping("/bookmarks/{syncId}/version")
    public ResponseEntity<?> versionBookmark(@PathVariable String syncId) throws ResponseStatusException {

        var bookmark = bookmarkRepository.findById(syncId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookmarks with " + syncId + " not found.")
        );

        return new ResponseEntity<>(bookmarkMapper.bookmarkToVersionBookmarkDTO(bookmark), HttpStatus.OK);
    }
}
