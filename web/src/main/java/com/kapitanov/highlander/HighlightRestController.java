package com.kapitanov.highlander;

import com.kapitanov.highlander.core.HighlightService;
import com.kapitanov.highlander.model.HighlightResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class HighlightRestController {

    private final HighlightService highlightService;

    HighlightRestController(HighlightService highlightService) {
        this.highlightService = highlightService;
    }

    @PostMapping("/highlights")
    void create(@RequestBody @NotNull @Validated HighlightResource resource) {
        highlightService.save(resource.toHighlight());
    }

    @PostMapping("/highlights/file")
    void createFromFile(@RequestBody @NotBlank String file) {
        highlightService.saveHighlightsFromFile(file);
    }

    @GetMapping("/highlights/random")
    List<HighlightResource> fetchFiveRandomHighlights() {
        return highlightService.findFiveRandomHighlights()
                .stream()
                .map(HighlightResource::from)
                .toList();
    }
}


