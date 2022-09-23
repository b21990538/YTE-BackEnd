package yte.thebackend.room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import yte.thebackend.common.response.MessageResponse;
import yte.thebackend.course.dto.CourseAddRequest;
import yte.thebackend.room.dto.RoomAddRequest;
import yte.thebackend.room.dto.RoomResponse;
import yte.thebackend.room.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<RoomResponse> getRooms() {
        return roomService.getRooms()
                .map(RoomResponse::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public MessageResponse addRoom(@RequestBody @Valid RoomAddRequest roomAddRequest) {
        return roomService.addRoom(roomAddRequest.toEntity());
    }

    @GetMapping("/{id}")
    public RoomResponse getRoom(@PathVariable Long id) {
        return RoomResponse.fromEntity(roomService.getRoomById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public MessageResponse updateRoom(@RequestBody @Valid RoomAddRequest roomAddRequest,
                                        @PathVariable Long id) {
        return roomService.updateRoom(id, roomAddRequest.toEntity());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public MessageResponse deleteRoom(@PathVariable Long id) {
        return roomService.deleteRoom(id);
    }
}
