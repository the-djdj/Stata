package com.stata.runtime;

import java.util.HashMap;
import java.util.Map;

/**
 * The current runtime of Stata. This stores all of the environmental variables
 * as-is, and should be referred to rather than Constants.java
 * 
 * @author Dan Jenkins
 */
public class Runtime
{
    /** The stored runtime currently in use. */
    private final Map<String, Object> runtime;

    /**
     * The default constructor. This creates a new Runtime class, and copies
     * the default constant values.
     */
    public Runtime()
    {
        // Create the runtime map
        this.runtime = new HashMap<>();

        // Copy the default values
        for (String key : Constants.constants.keySet())
        {
            this.runtime.put(key, Constants.constants.get(key));
        }
    }

    /**
     * The function used to set a runtime variable. This stores the variable in
     * a map to be retrieved later.
     * 
     * @param key A key to store the variable with
     * @param value The variable to store
     */
    public void setRuntimeValue(String key, Object value)
    {
        runtime.put(key, value);
    }

    /**
     * The function used to fetch a runtime variable. This gets a variable from
     * a specific key and casts it to a type automatically.
     * 
     * @param <T> The class type of the returned variable
     * @param key The key to fetch
     * @param type The class type of the returned variable
     * 
     * @return The variable associated with the key, cast as type
     */
    public <T> T getRuntimeValue(String key, Class<T> type)
    {
        return type.cast(runtime.get(key));
    }
}
