package com.stata.io;

import com.stata.project.Project;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;

import static java.util.Map.entry;

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
     * @param filename The filename to save the rpoject as
     * @param project The project state to save
     * 
     * @throws IOException
     */
    public static void save(String filename, Project project) throws IOException
    {
        // Create the path to write the file to
        Path path = Path.of(filename);

        // Create an output stream for writing a file
        ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path)));

        // Create the file contents
        Map<String, String> contents = Map.ofEntries
        (
            entry("metadata", project.getMetadata().getJSONString().toString())
        );

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
     * @param filename The file to load
     * 
     * @return The new project to be imported
     * 
     * @throws IOException
     */
    public static Project load(String filename) throws IOException
    {
        // Get the input file
        ZipFile file = new ZipFile(filename);

        // Create the project to populate
        Project project = new Project();

        // Iterate through each item in the zip file
        file.entries().asIterator().forEachRemaining(entry -> {
            try 
            {
                // Read the file
                String contents = new String(file.getInputStream(entry).readAllBytes());

                // Check which file we're dealing with
                switch (entry.getName())
                {
                    case "metadata":
                        project.getMetadata().fromJSONString(new JSONObject(contents));
                        break;
                }
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }
        });

        // Close the file
        file.close();

        // And return the new project object
        return project;
    }
}
