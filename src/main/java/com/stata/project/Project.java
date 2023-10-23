package com.stata.project;

import com.stata.Stata;
import com.stata.io.IOManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main project class. This represents an open project in Stata, and stores
 * references to the project data, calculations, and data tables.
 * 
 * @author Dan Jenkins
 */
public class Project
{
    /** The metadata of the project storing project information. */
    private Metadata metadata;

    /** A list of the datatables in the current project. */
    private List<Datatable> datatables;

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

        // Create the list of datatables
        this.datatables = new ArrayList<>();

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
     * A simple function used to return all of the data tables associated with
     * this project.
     * 
     * @return The list of datatables
     */
    public List<Datatable> getDatatables()
    {
        return this.datatables;
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

            // Store the project artefacts
            this.metadata = project.getMetadata();
            this.datatables = project.getDatatables();

            // And set the file as an environment variable
            Stata.getInstance().getRuntime().setRuntimeValue("project_file", file);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * The function used to import data from a file. This takes a filename and
     * imports the data into the current project.
     * 
     * @param file The file from which to import data.
     */
    public void importTable(File file) throws FileNotFoundException, IOException
    {
        // Create the new data table
        Datatable table = new Datatable();

        // Import the data
        table.importTable(file);

        // Add the data table to the current project
        if (!this.datatables.contains(table)) this.datatables.add(table);

        // And note that the project has been modified
        this.modify();
    }

    /**
     * A function used to load contents and headers from a project file into a
     * datatable. This first checks whether a table with the uuid exists and
     * creates one if necessary, then loads the headers.
     * 
     * @param uuid The uuid of the table
     * @param headers The headers of the table
     * @param data The data of the table
     */
    public void loadTable(String uuid, String headers, String data) throws IOException
    {
        // Check if the datatables list has a table with the uuid
        for (Datatable table : this.datatables)
        {
            // If we have a match, import the headers
            if (table.getUUID().toString().equals(uuid))
            {
                if (!headers.isEmpty()) table.importHeaders(headers, Datatable.INPUT_JSON);
                if (!data.isEmpty())    table.importData(data);
                return;
            }
        }

        // If there is no match, create the table
        Datatable table = new Datatable();

        // Set the properties
        table.setUUID(uuid);

        // Load the headers and contents
        if (!headers.isEmpty())  table.importHeaders(headers, Datatable.INPUT_JSON);
        if (!data.isEmpty())     table.importData(data);

        // And add the table
        if (!this.datatables.contains(table)) this.datatables.add(table);
    }
}
