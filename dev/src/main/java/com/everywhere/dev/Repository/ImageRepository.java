package com.everywhere.dev.Repository;

import com.everywhere.dev.model.EverywhereImages;
import com.everywhere.dev.model.Image;
import com.everywhere.dev.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<EverywhereImages,UUID> {

    @Query(value = "select * from everywhere_images where roomid = ?1", nativeQuery = true)
    List<EverywhereImages> findImagesByRoomId(UUID room_id);
}
