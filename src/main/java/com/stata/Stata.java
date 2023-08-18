package com.stata;

import com.stata.project.Project;
import com.stata.runtime.Runtime;
import com.stata.ui.UI;

import java.io.File;
import java.util.Arrays;
import java.util.ListIterator;

/**
 * The main class of Stata. This handles the program start and arguments.
 * 
 * @author Dan Jenkins
 */
public enum Stata
{
    /** The current Stata instance. */
    INSTANCE();

    /** The arguments used to start the Stata instance. */
    private String[] arguments;

    /** The runtime of the Stata instance. */
    private Runtime runtime;

    /** The application user interface. */
    private UI ui;

    /** The open Stata project. */
    private Project project;

    /**
     * The main method. This is the entrypoint to Stata.
     * 
     * @param args An array of arguments to pass to the application
     */
    public static void main(String[] args)
    {
        // Create the Stata instance
        Stata.getInstance().handleArguments(args);
        Stata.getInstance().start();
    }

    /**
     * The function used to get a Stata instance. This returns the enum
     * instance in a thread-safe manner.
     * 
     * @return The active Stata instance
     */
    public static Stata getInstance()
    {
        return INSTANCE;
    }

    /**
     * The Stata object. This houses the main Stata instance and collects all
     * of the variables.
     * 
     * @param arguments The arguments passed to the application
     */
    private Stata()
    {
        // Create the runtime
        this.runtime = new Runtime();

        // Create the empty project
        this.project = new Project();
    }

    /**
     * The function used to handle the arguments in the application. This scans
     * the arguments and sets the runtime appropriately.
     * 
     * @param arguments The arguments to scan
     */
    public void handleArguments(String[] arguments)
    {
        // Store the arguments
        this.arguments = arguments;

        // Iterate through each argument and process it
        for (ListIterator<String> iterator = Arrays.asList(arguments).listIterator(); iterator.hasNext();)
        {
            // Handle each argument
            switch (iterator.next().toLowerCase())
            {
                case "gui":
                    this.runtime.setRuntimeValue("gui", true);
                    break;

                case "input":
                    this.runtime.setRuntimeValue("input", iterator.next());
                    break;

                case "nogui":
                    this.runtime.setRuntimeValue("gui", false);
                    break;
            }
        }
    }

    /**
     * The function used to start the Stata application. This processes the
     * input arguments and sets everything up.
     */
    private void start()
    {
        // Launch JavaFX
        UI.launch(UI.class, this.arguments);

        // If there is an input argument, load the project
        if (this.runtime.getRuntimeValue("input", String.class) != null)
        {
            this.project.load(new File(this.runtime.getRuntimeValue("input", String.class)));
        }

        // Create the usr interface system
        this.ui = new UI();
    }

    /**
     * The exit function. This is called whenever the application closes to
     * ensure that it exits gracefully.
     */
    public void exit()
    {
        // And close the application
        System.exit(0);
    }

    /**
     * The function used to return the active project. This allows the project
     * to be accessed elsewhere in the application.
     * 
     * @return The active project
     */
    public Project getProject()
    {
        return this.project;
    }

    /**
     * The function used to return the runtime. This allows the runtime to be
     * accessed elsewhere in the application.
     * 
     * @return The current runtime
     */
    public Runtime getRuntime()
    {
        return this.runtime;
    }
}
