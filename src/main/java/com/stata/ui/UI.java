package com.stata.ui;

import com.stata.Stata;
import com.stata.project.Project;
import com.stata.ui.scenes.MainScene;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The user-interface manager. This stores the objects for the JavaFX-based
 * the graphical user interface
 * 
 * @author Dan Jenkins
 */
public class UI extends Application
{
    /** The parent Stata instance. */
    private final Stata stata;

    /** The current stage in the window. */
    private Stage stage;

    /** The root note of the interface. */
    private final BorderPane root;

    /** The current scene in the application. */
    private MainScene scene;

    /**
     * The default constructor. This creates a new UI system in which to run
     * Stata.
     */
    public UI()
    {
        // Store the stata instance
        this.stata = Stata.getInstance();

        // Store the root node
        this.root = new BorderPane();

        // Create the default scene
        this.scene = new MainScene(root, 300, 250);
    }

    /**
     * The start function. This is called whenever the JavaFX application
     * starts up.
     * 
     * @param stage The primary stage
     */
    public void start(Stage stage)
    {
        // Store the stage
        this.stage = stage;

        // Make sure that all child components are updated
        this.update();

        // Set the default scene
        this.stage.setScene(this.scene);

        // Update the scene's stage
        this.scene.setStage(this.stage);

        // And show the interface
        this.stage.show();
    }

    /**
     * The update function. This is used to trigger UI updates whenever the
     * project state changes.
     */
    public void update()
    {
        // Get the updated project
        Project project = Stata.getInstance().getProject();

        // Update the title and icon of the window
        this.stage.setTitle(this.createTitle(project));
        this.stage.getIcons().add(new Image("/resources/stata.png"));

        // Update the main scene
        this.scene.update(this, project);
    }

    /**
     * A simple function used to craft a title for the application. This takes
     * the title of the application, appends the project information, and notes
     * whether or not the file is saved.
     * 
     * @param project The project to use in the title.
     * 
     * @return The title of the application
     */
    public String createTitle(Project project)
    {
        // Create the title string
        String title = this.stata.getRuntime().getRuntimeValue("title", String.class);

        // Add the project information
        if (project != null)
        {
            // Add the project title
            title += " - " + project.getMetadata().getName();

            // And add an asterisk if the file isn't saved
            if (project.isModified())
            {
                title += "*";
            }
        }

        // And return the string
        return title;
    }
}
