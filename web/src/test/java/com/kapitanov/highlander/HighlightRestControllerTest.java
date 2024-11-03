package com.kapitanov.highlander;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapitanov.highlander.core.HighlightService;
import com.kapitanov.highlander.core.domain.Highlight;
import com.kapitanov.highlander.model.HighlightResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HighlightRestController.class)
@ContextConfiguration(classes = HighlightRestController.class)
class HighlightRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HighlightService service;

    @Test
    void missingBodyForCreatingHighlightReturnsError() throws Exception {
        mockMvc.perform(post("/api/highlights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void emptyBodyForCreatingHighlightReturnsError() throws Exception {
        mockMvc.perform(post("/api/highlights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @ParameterizedTest
    @MethodSource("createInvalidHighlightResources")
    void missingValuesForCreatingHighlightReturnsError(HighlightResource highlightResource) throws Exception {
        mockMvc.perform(post("/api/highlights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(highlightResource)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void createHighlightSuccessfully() throws Exception {
        HighlightResource highlightResource = new HighlightResource("auth", "title", "quote");
        mockMvc.perform(post("/api/highlights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(highlightResource)))
                .andExpect(status().isOk());
        Highlight expected = new Highlight("auth", "title", "quote");

        verify(service).save(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "''",
            "'       '"
    })
    void emptyBodyForCreatingHighlightsFromFileReturnsError(String body) throws Exception {
        mockMvc.perform(post("/api/highlights/file")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void createHighlightsFromFileSuccessfully() throws Exception {
        String body = """
                this is my test
                """;
        mockMvc.perform(post("/api/highlights/file")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(body))
                .andExpect(status().isOk());

        verify(service).saveHighlightsFromFile(body);
    }

    public static Stream<Arguments> createInvalidHighlightResources() {
        return Stream.of(
                Arguments.of(new HighlightResource(null, null, null)),
                Arguments.of(new HighlightResource("", "", "")),
                Arguments.of(new HighlightResource("Me", "", null)),
                Arguments.of(new HighlightResource("Me", "You", "")),
                Arguments.of(new HighlightResource("", "You", "see"))
        );
    }
}