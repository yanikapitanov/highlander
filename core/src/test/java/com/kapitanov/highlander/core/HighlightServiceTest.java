package com.kapitanov.highlander.core;

import com.kapitanov.highlander.core.domain.Highlight;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class HighlightServiceTest implements WithAssertions {
    @InjectMocks
    private HighlightService highlightService;
    @Mock
    private HighlightPersistence persistence;

    @Test
    void parseSingleHighlightWithTitle() {
        String highlight = getStringHighlights();
        Highlight first = new Highlight("Hughes, Mark", "Unstoppable: The Ultimate Biography of Max Verstappen",
                "But that prepared me very well for criticism or praise – because I just didn’t really care. " +
                        "I don’t really care if it’s negative and don’t really care if it’s positive. " +
                        "It doesn’t bring me anything, being arrogant and thinking I can walk on water. " +
                        "And being negative, I’m just going to be upset. I prefer to be in the middle and forget about both sides. " +
                        "Just focus on what you have to do and what you can control.’");
        Highlight second = new Highlight("Hughes, Mark", "Unstoppable: The Ultimate Biography of Max Verstappen",
                "It was such a fine line Max was treading, internalising the toughness without assimilating the anger.");
        Highlight third = new Highlight("Perry, Philippa",
                "The Book You Wish Your Parents Had Read (and Your Children Will Be Glad That You Did)",
                "It is common for a parent to withdraw from their child at a very similar age to when " +
                        "that parent’s parent became unavailable to them. Or a parent will want to pull away " +
                        "emotionally when their child is the same age as they were when they felt alone.");
        List<Highlight> actual = highlightService.parse(highlight);

        assertThat(actual).containsExactlyInAnyOrder(first, second, third);
    }

    private static String getStringHighlights() {
        return """
                Unstoppable: The Ultimate Biography of Max Verstappen (Hughes, Mark)
                - Your Highlight on page 83 | Location 1028-1031 | Added on Sunday, September 17, 2023 8:53:07 PM
                
                But that prepared me very well for criticism or praise – because I just didn’t really care. I don’t really care if it’s negative and don’t really care if it’s positive. It doesn’t bring me anything, being arrogant and thinking I can walk on water. And being negative, I’m just going to be upset. I prefer to be in the middle and forget about both sides. Just focus on what you have to do and what you can control.’
                ==========
                Unstoppable: The Ultimate Biography of Max Verstappen (Hughes, Mark)
                - Your Highlight on page 110 | Location 1346-1347 | Added on Wednesday, September 20, 2023 5:46:03 PM
                
                It was such a fine line Max was treading, internalising the toughness without assimilating the anger.
                ==========
                The Book You Wish Your Parents Had Read (and Your Children Will Be Glad That You Did) (Perry, Philippa)
                - Your Highlight on page 21 | Location 419-421 | Added on Tuesday, September 26, 2023 7:55:29 AM
                
                It is common for a parent to withdraw from their child at a very similar age to when that parent’s parent became unavailable to them. Or a parent will want to pull away emotionally when their child is the same age as they were when they felt alone.
                """;
    }

}