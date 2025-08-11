package com.id25.backend;

import com.id25.backend.formatting.NameFormatter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class NameFormatterTest {


    @Test
    public void TestAllCaps()
    {
        var actual = NameFormatter.formatName("KRISTOFFER KJÆR");
        assertEquals("Kristoffer Kjær",actual);
    }

    @Test
    public void TestHiphen()
    {
        var actual = NameFormatter.formatName("KRISTOFFER-KJÆR");
        assertEquals("Kristoffer-Kjær",actual);
    }

    @Test
    public void TestNoCaps()
    {
        var actual = NameFormatter.formatName("kristoffer kjær");
        assertEquals("Kristoffer Kjær",actual);
    }

    @Test
    public void TestManyNames()
    {
        var actual = NameFormatter.formatName("kristoffer reimer overvad kjær");
        assertEquals("Kristoffer Reimer Overvad Kjær",actual);
    }
}
