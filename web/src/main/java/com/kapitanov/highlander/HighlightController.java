package com.kapitanov.highlander;

import com.kapitanov.highlander.core.HighlightPersistence;
import com.kapitanov.highlander.core.HighlightService;
import com.kapitanov.model.HighlightResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class HighlightController {

    private final HighlightService highlightService;

    HighlightController(HighlightService highlightService) {
        this.highlightService = highlightService;
    }

    @PostMapping("/highlight")
    void create(@RequestBody HighlightResource resource) {
        highlightService.save(resource.toHighlight());
    }

    @PostMapping("/highlight/file")
    void createFromFile(@RequestBody String file) {
        highlightService.saveHighlightsFromFile(file);
    }

    @GetMapping("/highlight/random")
    List<HighlightResource> fetchFiveRandomHighlights() {
        return highlightService.findFiveRandomHighlights()
                .stream()
                .map(HighlightResource::from)
                .toList();
    }
}


