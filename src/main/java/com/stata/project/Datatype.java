package com.stata.project;

import java.util.List;
import java.util.regex.Pattern;

/**
 * The datatypes class. This stores a list of datatypes supported by Stata.
 * 
 * @author Dan Jenkins
 */
public enum Datatype
{
    NUMBER("Number", "^(([0-9]+)|([0-9]*(\\.|,)[0-9]+))$"),
    BOOLEAN("Boolean", "^((t(rue)?)|(f(alse)?))|([01]^abc)$"),
    STRING("Text", ".*"),
    NULL("NULL", "");

    /** The name of the class type. */
    private String name;

    /** The regular expression to test for this datatype. */
    private String regex;

    /** The regex pattern for use in matching. */
    private Pattern pattern;
    
    /**
     * The default constructor. This creates a new datatype with a Java class
     * type.
     * 
     * @param type The Java class of this type
     * @param name The name of the class type
     */
    private Datatype(String name, String regex)
    {
        // Store the details
        this.name = name;
        this.regex = regex;

        // Create the regular expression pattern
        this.pattern = Pattern.compile(this.regex, Pattern.CASE_INSENSITIVE);
    }

    /**
     * A simple function which returns the name of the type of this datatype.
     * 
     * @return The name of this datatype.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * A simple function which returns the regex criteria for matching a type.
     * @return
     */
    public String getRegex()
    {
        return this.regex;
    }

    /**
     * A simple function which returns the regex pattern used when testing this
     * type.
     * 
     * @return The regex pattern
     */
    public Pattern getPattern()
    {
        return this.pattern;
    }

    /**
     * A simple function which determines what datatype we are dealing with.
     * This takes a single datum and tries to parse it as various types to
     * see what it is.
     * 
     * @param datum The datum to parse
     * 
     * @return The datatype
     */
    public static Datatype parse(String datum)
    {
        // Try each of the regexes in turn
        for (Datatype type : Datatype.values())
        {
            // Test for a match and return
            if (type.getPattern().matcher(datum).matches())
                return type;
        }

        // If there are no matches, parse as unknown
        return NULL;
    }

    /**
     * A function which determines the datatype for an array of data. This
     * calculates the datatype for each value and compares them to see if they
     * match, moving to the next as appropriate.
     * 
     * @param data The data to parse
     * 
     * @return The collective datatype
     */
    public static Datatype parse(List<String> data)
    {
        // Check that we have items
        if (data.isEmpty()) return Datatype.NULL;

        // Store the type of the first item
        Datatype type = Datatype.parse(data.get(0));

        // Iterate through each value
        for (String datum : data)
        {
            // Check that our types are equal
            if (Datatype.parse(datum) != type)
            {
                // Default back to string
                type = Datatype.STRING;
            }
        }

        // And return our final type
        return type;
    }

    /**
     * A simple function to get a Datatype from a string. This tables a string
     * and iterates through each Datatype until we have a match.
     * 
     * @param string The datatype we're looking for
     * 
     * @return The Datatype match
     */
    public static Datatype fromString(String string)
    {
        // Iterate through each of our types
        for (Datatype type : Datatype.values())
        {
            // Test for a match and return
            if (type.getName().equals(string))
                return type;
        }

        // If we haven't found anything, return null
        return NULL;
    }
}
