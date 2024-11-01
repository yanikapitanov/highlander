package com.kapitanov.highlander;

import com.kapitanov.highlander.core.HighlightService;
import com.kapitanov.model.HighlightResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    void createFromFile(MultipartFile file) throws IOException {
        highlightService.parse(new String(file.getBytes()));
    }
}


