package yte.thebackend.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.common.dto.AutocompleteItem;
import yte.thebackend.common.service.AutocompleteService;
import yte.thebackend.course.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutocompleteController {

    private final AutocompleteService autocompleteService;

    private final RoomService roomService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/auto-lecturer/{like}")
    public List<AutocompleteItem> getLecturersByUsernameLike(@PathVariable String like) {
        return autocompleteService.getLecturersByUsernameLike(like).stream()
                .map(AutocompleteItem::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAuthority('LECTURER')")
    @GetMapping("/auto-assistant/{like}")
    public List<AutocompleteItem> getAssistantsByUsernameLike(@PathVariable String like) {
        return autocompleteService.getAssistantsByUsernameLike(like).stream()
                .map(AutocompleteItem::fromEntity)
                .toList();
    }

    @GetMapping("/auto-room/{like}")
    public List<AutocompleteItem> getRoomsByNameLike(@PathVariable String like) {
        return roomService.getRoomsByNameLike(like).stream()
                .map(AutocompleteItem::fromEntity)
                .toList();
    }
}
