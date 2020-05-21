package com.everywhere.dev.Repository;

import com.everywhere.dev.model.EverywhereRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<EverywhereRooms,UUID> {

    @Query(value = "Select * from everywhere_rooms", nativeQuery = true)
    List<EverywhereRooms> findAllRooms();

    @Query(value = "Select * from everywhere_rooms where userid = ?1", nativeQuery = true)
    List<EverywhereRooms> findAllRoomsByUserId(UUID userId);

    @Query(value = "select userid from everywhere_rooms where roomid = ?1", nativeQuery = true)
    UUID findUserIdByRoomId(UUID room_id);
}
