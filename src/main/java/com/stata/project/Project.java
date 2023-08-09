package com.stata.project;

import java.io.IOException;

import com.stata.io.IOManager;

/**
 * The main project class. This represents an open project in Stata, and stores
 * references to the project data, calculations, and data tables.
 * 
 * @author Dan Jenkins
 */
public class Project {

    /** The metadata of the project storing project information. */
    private Metadata metadata;

    /**
     * The default constructor. This creates the project prototype and creates
     * the relevant data variables.
     */
    public Project()
    {
        // Create the metadata for the project
        this.metadata = new Metadata();
    }

    /**
     * A simple function to return the project metadata.
     * 
     * @return The project metadata
     */
    public Metadata getMetadata()
    {
        return this.metadata;
    }

    /**
     * The function used to write the project state to a file. This takes a
     * filename and passes the project along with the name to the IOManager to
     * compress and write to disk.
     * 
     * @param file The file to save the project as
     */
    public void save(String file)
    {
        // Write the project to a file
        try
        {
            IOManager.save(file, this);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * The function used to read a project state from a file. This takes a
     * filename and sets all of the project variables to match those in the
     * file on disk.
     * 
     * @param file The file to read the project from
     */
    public void load(String file)
    {

    }
}
