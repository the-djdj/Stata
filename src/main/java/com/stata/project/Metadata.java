package com.stata.project;

import org.json.JSONObject;

/**
 * The metadata class. This stores information about the open project such as
 * authors, edited dates, etc.
 * 
 * @author Dan Jenkins
 */
public class Metadata
{
    /** The date that this project was created. */
    private long date_create;

    /**
     * The main constructor. This creates a new metadata object with the default values.
     */
    public Metadata()
    {
        // Set the creation data for this project
        this.date_create = System.currentTimeMillis();
    }

    /**
     * The function which condenses this object into a JSON string for saving.
     * 
     * @return The string representation of this object
     */
    public JSONObject getJSONString()
    {
        // Create the parent JSON object
        JSONObject object = new JSONObject();

        // Store the parameters
        object.put("date_create", this.date_create);

        // And return the object
        return object;
    }

    /**
     * The function used to load a metadata object from a JSON string. This
     * reads in the JSON string and allocates the appropriate variables.
     * 
     * @param object The object being read in
     */
    public void fromJSONString(JSONObject object)
    {
        // Extract the relevant variables
        this.date_create = object.getLong("date_create");
    }
}
