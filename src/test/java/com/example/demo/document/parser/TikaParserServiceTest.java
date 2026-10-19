package com.example.demo.document.parser;

import org.apache.tika.Tika;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TikaParserServiceTest {

    private TikaParserService tikaParserService;

    @BeforeEach
    void setUp() {
        tikaParserService = new TikaParserService(new Tika());
    }

    @Test
    void testParseNullInputStream() {
        String result = tikaParserService.parse(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseEmptyInputStream() {
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
        String result = tikaParserService.parse(emptyStream);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseRealPdfFixture() throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream("/fixtures/sample_resume.pdf")) {
            assertNotNull(inputStream, "Test fixture sample_resume.pdf not found in classpath!");
            
            String result = tikaParserService.parse(inputStream);
            
            assertNotNull(result);
            assertFalse(result.trim().isEmpty(), "Extracted text should not be empty");
            
            String lowerResult = result.toLowerCase();
            assertTrue(lowerResult.contains("john doe"), "Extracted text should contain the fixture's applicant name");
            assertTrue(lowerResult.contains("professional experience"), "Extracted text should contain the experience heading");
        }
    }
}
