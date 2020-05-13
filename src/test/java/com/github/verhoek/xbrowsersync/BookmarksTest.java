package com.github.verhoek.xbrowsersync;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.verhoek.xbrowsersync.controllers.dtos.CreateBookmarkRequestDTO;
import com.github.verhoek.xbrowsersync.controllers.dtos.CreateBookmarkResponseDTO;
import com.github.verhoek.xbrowsersync.controllers.dtos.UpdateBookmarkRequestDTO;
import com.github.verhoek.xbrowsersync.controllers.dtos.UpdateBookmarkResponseDTO;
import com.github.verhoek.xbrowsersync.models.Bookmark;
import com.github.verhoek.xbrowsersync.repositories.BookmarkRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.awt.print.Book;
import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@DataJpaTest
//@WebMvcTest(BookmarksController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookmarksTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Rollback
    public void testCreateBookmark() throws Exception {

        var createBookmarkRequestDTO = CreateBookmarkRequestDTO.builder().version("0.1.2").build();

        var payload = objectMapper.writeValueAsString(createBookmarkRequestDTO);

        var result = mvc.perform(MockMvcRequestBuilders.post("/bookmarks")
                .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isOk()).andReturn();

        var responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                                                 CreateBookmarkResponseDTO.class);

        assertTrue(bookmarkRepository.findById(responseDTO.getId()).isPresent());
    }

    @Test
    @Rollback
    public void testUpdateBookmark() throws Exception {
        var bookmark = createBookmark();
        var bookmarkData = "bla";
        var updateBookmarkRequestDTO = UpdateBookmarkRequestDTO.builder()
                                        .bookmarks(bookmarkData)
                                        .lastUpdated(Instant.now().toString()).build();
        var payload = objectMapper.writeValueAsString(updateBookmarkRequestDTO);


        var result = mvc.perform(MockMvcRequestBuilders.put("/bookmarks/{syncId}", bookmark.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk()).andReturn();
        var responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(),
                UpdateBookmarkResponseDTO.class);
        var updatedBookmark = bookmarkRepository.findById(bookmark.getId()).orElseThrow();


        assertEquals(bookmarkData, updatedBookmark.getData());
    }


    private Bookmark createBookmark() {
        var bookmark = Bookmark.builder()
                .id(UUID.randomUUID().toString())
                .version("0.0.1")
                .lastUpdated(Instant.now().minusSeconds(200))
                .build();

        bookmarkRepository.save(bookmark);
        return bookmark;
    }
}
