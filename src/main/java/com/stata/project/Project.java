package com.stata.project;

import com.stata.io.IOManager;

import java.io.File;
import java.io.IOException;

/**
 * The main project class. This represents an open project in Stata, and stores
 * references to the project data, calculations, and data tables.
 * 
 * @author Dan Jenkins
 */
public class Project {

    /** The metadata of the project storing project information. */
    private Metadata metadata;

    /** A variable storing whether or not the project has been modified. */
    private boolean modified;

    /**
     * The default constructor. This creates the project prototype and creates
     * the relevant data variables.
     */
    public Project()
    {
        // Create the metadata for the project
        this.metadata = new Metadata(this);

        // Note that the project hasn't been modified
        this.modified = false;
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
     * A simple function to mark the project as modified.
     */
    public void modify()
    {
        this.modified = true;
    }

    /**
     * A simple function to return whether or not the project has been
     * modified.
     * 
     * @return Whether the project has been modified
     */
    public boolean isModified()
    {
        return this.modified;
    }

    /**
     * The function used to write the project state to a file. This takes a
     * filename and passes the project along with the name to the IOManager to
     * compress and write to disk.
     * 
     * @param file The file to save the project as
     */
    public void save(File file)
    {
        // Write the project to a file
        try
        {
            // Save the file
            IOManager.save(file, this);

            // And note that the file has not been modified
            this.modified = false;
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
    public void load(File file)
    {
        try
        {
            // Load a project from a file
            Project project = IOManager.load(file);

            // And store the project artefacts
            this.metadata = project.getMetadata();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
