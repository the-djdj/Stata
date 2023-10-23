package com.stata.io;

import com.stata.project.Datatable;
import com.stata.project.Project;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;
/**
 * The IOManager class. This static class is responsible for reading and
 * writing project data from the disk. Project data files take the form of a
 * zip archive with the following structure:
 * 
 * Zip archive
 *   - Metadata
 * 
 *  @author Dan Jenkins
 */
public class IOManager
{
    /**
     * The save function. This saves a project state to the disk so that it can
     * be exported or loaded again elsewhere.
     * 
     * @param file The file to save the project as
     * @param project The project state to save
     * 
     * @throws IOException
     */
    public static void save(File file, Project project) throws IOException
    {
        // Create the path to write the file to
        Path path = file.toPath();

        // Create an output stream for writing a file
        ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path)));

        // Create the file contents
        Map<String, String> contents = new HashMap<>();
        contents.put("metadata", project.getMetadata().getJSONString().toString());

        // Add the datatables
        for (Datatable table : project.getDatatables().values())
        {
            contents.put("datatables" + File.separator
                + table.getUUID().toString() + File.separator + "data", table.exportData());
            contents.put("datatables" + File.separator
                + table.getUUID().toString() + File.separator + "headers", table.exportHeaders().toString());
        }

        // And iterate through the contents of the zip and write them
        for (var entry : contents.entrySet())
        {
            // Add the entry
            output.putNextEntry(new ZipEntry(entry.getKey()));

            // Write the entry
            output.write(entry.getValue().getBytes());

            // And close the entry
            output.closeEntry();            
        }

        // Finally, close the output stream
        output.close();
    }

    /**
     * The load function. This loads a project from the disk so that it can be
     * used within Stata.
     * 
     * @param file The file to load
     * 
     * @return The new project to be imported
     * 
     * @throws IOException
     */
    public static Project load(File file) throws IOException
    {
        // Get the input file
        ZipFile zip = new ZipFile(file);

        // Create the project to populate
        Project project = new Project();

        // Iterate through each item in the zip file
        zip.entries().asIterator().forEachRemaining(entry -> {
            try 
            {
                // Read the file
                String contents = new String(zip.getInputStream(entry).readAllBytes());

                // Check which file we're dealing with
                if (entry.getName().equals("metadata"))
                {
                    // Load the metadata from the file
                    project.getMetadata().fromJSONString(new JSONObject(contents));
                }
                else if (entry.getName().startsWith("datatables"))
                {
                    // Get the path separator
                    String separator = File.separator;
                    if (separator.equals("\\")) separator += "\\";

                    // Store the table uuid
                    String uuid = entry.getName().split(separator)[1];

                    // Check whether we have the datatable header or data
                    if (entry.getName().endsWith("headers"))
                    {
                        // Load the headers of the table
                        project.loadTable(uuid, contents, "");
                    }
                    else if (entry.getName().endsWith("data"))
                    {
                        // Load the data into the table
                        project.loadTable(uuid, "", contents);
                    }
                }
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        });

        // Close the file
        zip.close();

        // And return the new project object
        return project;
    }
}
