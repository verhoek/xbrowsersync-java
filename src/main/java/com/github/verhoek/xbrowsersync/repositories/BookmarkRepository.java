package com.github.verhoek.xbrowsersync.repositories;

import com.github.verhoek.xbrowsersync.models.Bookmark;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface BookmarkRepository extends CrudRepository<Bookmark, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Bookmark save(Bookmark bookmark);
}
