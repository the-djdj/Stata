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

    /** The JSON representation of this object. */
    private JSONObject object;

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
        this.object = new JSONObject();

        // Store the parameters
        this.object.put("date_create", this.date_create);

        // And return the object
        return this.object;
    }
}
