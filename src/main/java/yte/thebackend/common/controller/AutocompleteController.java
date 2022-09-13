package yte.thebackend.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import yte.thebackend.common.dto.AutocompleteItem;
import yte.thebackend.common.service.AutocompleteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutocompleteController {

    private final AutocompleteService autocompleteService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/auto-lecturer/{like}")
    public List<AutocompleteItem> getLecturers(@PathVariable String like) {
        return autocompleteService.getLecturers(like).stream()
                .map(AutocompleteItem::fromEntity)
                .toList();
    }

    @PreAuthorize("hasAuthority('LECTURER')")
    @GetMapping("/auto-assistant/{like}")
    public List<AutocompleteItem> getAssistants(@PathVariable String like) {
        return autocompleteService.getAssistants(like).stream()
                .map(AutocompleteItem::fromEntity)
                .toList();
    }
}
