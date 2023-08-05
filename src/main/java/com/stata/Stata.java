package com.stata;

import com.stata.runtime.Runtime;

import java.util.Arrays;
import java.util.ListIterator;

/**
 * The main class of Stata. This handles the program start and arguments.
 * 
 * @author Dan Jenkins
 */
public class Stata 
{
    /** The current Stata instance. */
    public static Stata instance;

    /** The runtime of the Stata instance. */
    public Runtime runtime;

    /**
     * The main method. This is the entrypoint to Stata.
     * 
     * @param args An array of arguments to pass to the application
     */
    public static void main(String[] args)
    {
        // Set the default runtime
        Runtime runtime = new Runtime();

        // Handle the application arguments
        Stata.handleArguments(args, runtime);

        // And create the Stata instance
        Stata.instance = new Stata(runtime);
    }

    /**
     * The function used to handle the arguments in the application. This scans
     * the arguments and sets the runtime appropriately.
     * 
     * @param arguments The arguments to scan
     * @param runtime The runtime to pass to the application
     */
    public static void handleArguments(String[] arguments, Runtime runtime)
    {
        // Iterate through each argument and process it
        for (ListIterator<String> iterator = Arrays.asList(arguments).listIterator(); iterator.hasNext();)
        {
            // Handle each argument
            switch (iterator.next().toLowerCase())
            {
                case "gui":
                    runtime.setRuntimeValue("gui", true);
                    break;

                case "input":
                    runtime.setRuntimeValue("input", iterator.next());
                    break;

                case "nogui":
                    runtime.setRuntimeValue("gui", false);
                    break;
            }
        }
    }

    /**
     * The Stata object. This houses the main Stata instance and collects all
     * of the variables.
     * 
     * @param runtime The runtime of the Stata instance
     */
    public Stata(Runtime runtime)
    {
        // Store the runtime
        this.runtime = runtime;
    }
}
