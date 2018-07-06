package com.wetcher;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AntPathMatcherTests {
    
    private AntPathMatcher pathMatcher;
    
    @Test
    public void combine() {
        // ...
        assertEquals("/hotels", pathMatcher.combine("/hotels", null));
        assertEquals("/hotels/booking", pathMatcher.combine("/hotels/*", "/booking"));
        assertEquals("/hotels/**/booking", pathMatcher.combine("/hotels/**", "booking"));
        assertEquals("/hotels/**/booking", pathMatcher.combine("/hotels/**", "/booking"));
        assertEquals("/hotels/booking", pathMatcher.combine("/hotels", "/booking"));
        assertEquals("/hotels/{hotel}", pathMatcher.combine("/hotels/*", "{hotel}"));
        assertEquals("/hotels/**/{hotel}", pathMatcher.combine("/hotels/**", "{hotel}"));
        assertEquals("/hotels/*/booking/{booking}", pathMatcher.combine("/hotels/*/booking", "{booking}"));
    }

}
