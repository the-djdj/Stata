package com.stata.runtime;

import java.util.Map;

import static java.util.Map.entry;

/**
 * The constants used throughout Stata.
 * 
 * @author Dan Jenkins
 */
public final class Constants
{
    /** The list of the constants for this application. */
    public static Map<String, Object> constants = Map.ofEntries
    (
        entry("gui", true),
        entry("window_height", 800),
        entry("window_title", new String("Stata")),
        entry("window_width", 1024)
    );
}
