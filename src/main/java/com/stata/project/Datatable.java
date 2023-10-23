package com.stata.project;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The datatable class. This represents a data table in Stata which holds raw
 * data to be manipulated.
 * 
 * @author Dan Jenkins
 */
public class Datatable
{
    /** The constant indicating that we are importing headers from a CSV file. */
    public static final int INPUT_CSV = 0;

    /** The constant indicating that we are importing headers from a JSON file. */
    public static final int INPUT_JSON = 1;

    /** The name of the data table. */
    private String name;

    /** The uuid of the data table. */
    private UUID uuid;

    /** The headers of the data file. */
    private List<String> headers;

    /** A list of the data types to which this table conforms. */
    private List<Datatype> types;

    /** The data stored in this data table */
    private List<List<String>> data;

    /** The number of rows and columns in the data table. */
    private int rows, columns;

    /**
     * The default constructor. This creates a new data table to be read and
     * manipulated elsewhere in the application.
     */
    public Datatable()
    {
        // Create the data table name
        this.name = "New data table";
        this.uuid = UUID.randomUUID();

        // Create the header and data types lists
        this.headers = new ArrayList<>();
        this.types = new ArrayList<>();

        // And note that we have no idea
        this.rows = 0;
        this.columns = 0;
    }

    /**
     * A simple function which returns the name of the data table.
     * 
     * @return The data table name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * A simple function which returns the uuid of the data table.
     */
    public UUID getUUID()
    {
        return this.uuid;
    }

    /**
     * A simple function used to update the name of the data table.
     * 
     * @param name The new data table name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * A simple function used to update the uuid of the data table.
     * 
     * @param uuid The new data table uuid
     */
    public void setUUID(String uuid)
    {
        this.uuid = UUID.fromString(uuid);
    }

    /**
     * The import data function. This reads CSV data from a file and imports it
     * into the current data tables object.
     * 
     * @param file The file to import
     */
    public void importTable(File file) throws FileNotFoundException, IOException
    {
        // Get the contents of the file
        String contents = new String(Files.readAllBytes(file.toPath()));

        // Split the file into headers and data
        String headers = contents.substring(0, contents.indexOf("\n"));
        String data = contents.substring(contents.indexOf("\n"));

        // Import the headers and the data
        this.importHeaders(headers, INPUT_CSV);
        this.importData(data);

        // And calculate the types
        for(int c = 0; c <= this.columns; c++)
        {
            // Store the type
            this.types.add(Datatype.parse(this.data.get(c)));
        }
    }

    /**
     * A function used to expore the data headers and properties for storage in
     * a file.
     * 
     * @return The CSV headers and properties.
     */
    public JSONObject exportHeaders()
    {
        // Create the parent JSON object
        JSONObject object = new JSONObject();
        JSONArray fields = new JSONArray();

        // Store the fields
        for (int h = 0; h < this.headers.size(); h++)
        {
            JSONObject field = new JSONObject();

            field.put("name", this.headers.get(h));
            field.put("type", this.types.get(h).getName());

            fields.put(field);
        }

        // Store the parameters
        object.put("name", this.name);
        object.put("uuid", this.uuid.toString());   
        object.put("fields", fields);     

        // And return the object
        return object;
    }

    /**
     * A function used to export the data in CSV format for storage in a file.
     * 
     * @return The CSV representation of the file
     */
    public String exportData()
    {
        // Create the string for the output
        String output = new String();

        // Print the data
        for(int r = 0; r < this.rows; r++)
        {
            for(int c = 0; c < this.columns - 1; c++) output += this.data.get(c).get(r) + ",";
            output += this.data.get(this.data.size() - 1).get(r) + "\n";
        }

        // And return the data
        return output;
    }

    /**
     * A function used to import headers either from a CSV or a JSON format
     * into the table.
     * 
     * @param headers The headers to import
     * @param type The type of data we are importing
     * 
     * @throws IOException If something goes wrong
     */
    public void importHeaders(String headers, int type) throws IOException
    {
        // Check what type of data we are importing
        if (type == INPUT_CSV)
        {
            // Get the record iterator
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(new StringReader(headers));

            // Get the data headers
            CSVRecord record = records.iterator().next();
            this.headers = Arrays.asList(record.values());
        }
        else if (type == INPUT_JSON)
        {
            // Get the parent JSON Object
            JSONObject data = new JSONObject(headers);

            // Extract the table properties
            this.name = data.getString("name");
            JSONArray fields = data.getJSONArray("fields");

            // And store the fields
            for (int i = 0; i < fields.length(); i++)
            {
                // Get the field
                JSONObject field = fields.getJSONObject(i);

                System.out.println(field.getString("name"));

                // And extract the field properties
                this.headers.add(field.getString("name"));
                this.types.add(Datatype.fromString(field.getString("type")));
            }
        }

        // And set the number of columns
        this.columns = this.headers.size() - 1;
    }

    /**
     * A function used to import data from CSV format into the table
     * 
     * @param data A string with the CSV data
     * 
     * @param IOException If something goes wrong
     */
    public void importData(String data) throws IOException
    {
        // Get the record iterator
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(new StringReader(data));

        // Iterate through each record
        for (CSVRecord record : records)
        {
            // Check that our data variable exists
            if (this.data == null)
            {
                // Create the data array
                this.data = new ArrayList<List<String>>(record.size());

                // Create each column of the data table
                for (int i = 0; i < record.size(); i++) this.data.add(new ArrayList<>());
            }

            // Process each record component
            for (int i = 0; i < record.values().length; i++)
            {
                this.data.get(i).add(record.values()[i]);
            }

            // And note that we've added a column
            this.rows++;
        }
    }

    /**
     * A function used to easily print a string representation of the current
     * data table.
     * 
     * @return The current data table as a string
     */
    @Override
    public String toString()
    {
        // Note the column size
        int width = 8;

        // Create the string for the output
        String output = new String();
        String format = "%" + width + "." + width + "s";

        // Add the headers
        output += String.format(" " + format, this.headers.get(0));
        for(int c = 1; c < this.columns; c++) output += String.format(" | " + format, this.headers.get(c));
        output += "\n";

        // Add a line
        output += "-".repeat(width + 2);
        for(int c = 1; c < this.columns; c++) output += "+" + "-".repeat(width + 2);
        output += "\n";

        // Print the data
        for(int r = 0; r < this.rows; r++)
        {
            output += String.format(" " + format, this.data.get(0).get(r));
            for(int c = 1; c < this.columns; c++) output += String.format(" | " + format, this.data.get(c).get(r));
            output += "\n";
        }

        // And return the data
        return output;
    }
}
