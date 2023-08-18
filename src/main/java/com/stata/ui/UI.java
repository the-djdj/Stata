package com.stata.ui;

import com.stata.Stata;
import com.stata.ui.scenes.MainScene;

import javafx.application.Application;
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
     * @param primary The primary stage
     */
    public void start(Stage primary)
    {
        // Set the window title
        primary.setTitle(this.stata.getRuntime().getRuntimeValue("title", String.class));

        // Set the default scene
        primary.setScene(this.scene);

        // Update the scene's stage
        this.scene.setStage(primary);

        // And show the interface
        primary.show();
    }
}
