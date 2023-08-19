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
    /** The parent project for this meta data. */
    private Project project;

    /** The date that this project was created. */
    private long date_create;

    /** The name of the project. */
    private String name;

    /**
     * The main constructor. This creates a new metadata object with the default values.
     * 
     * @param project The project that this metadata belongs to
     */
    public Metadata(Project project)
    {
        // Store the parent project
        this.project = project;

        // Set the creation data for this project
        this.date_create = System.currentTimeMillis();

        // Set the name of the project
        this.name = "New project";
    }

    /**
     * The function used to return the name of the stored project.
     * 
     * @return The name of the project
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * A function used to set the name of the stored project.
     * 
     * @param name The name of the project
     */
    public void setName(String name)
    {
        // Note that the project is updated
        if (!this.name.equals(name))
        {
            this.project.modify();
        }

        // And store the new name
        this.name = name;
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
        object.put("project_name", this.name);

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
        this.name = object.getString("project_name");
    }
}
