package dev.hzulfuqarov.certwatch.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainNamesTest {

    @Test
    void trims_and_lowercases_the_value() {
        assertEquals("wiki.az", new DomainName("  WIKI.AZ ").value());
    }

}