package com.stata.ui.scenes;

import com.stata.project.Project;
import com.stata.ui.UI;
import com.stata.ui.components.ContentPane;
import com.stata.ui.components.ContentTab;
import com.stata.ui.components.MainMenuBar;
import com.stata.ui.components.ProjectTree;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The main scene. This stores the main interface scene of the Stata
 * application.
 * 
 * @author Dan Jenkins
 */
public class MainScene extends Scene
{
    /** The active stage in the application. */
    private Stage stage;

    /** The menubar displayed at the top of the screen. */
    private MainMenuBar menu;

    /** The project tree for proejct information. */
    private ProjectTree tree;

    /** The tabbed content pane. */
    private ContentPane content;

    /**
     * The default constructor. This creates the scene with a specific parent
     * as well as a width and height, and adds all of the requisite components.
     * 
     * @param parent This scene's parent component
     * @param width The width of the scene
     * @param height The height of the scene
     */
    public MainScene(BorderPane parent, int width, int height)
    {
        // Create the scene
        super(parent, width, height);

        // Create the components
        this.menu = new MainMenuBar();
        this.tree = new ProjectTree();
        this.content = new ContentPane();

        // Add the components
        parent.setTop(this.menu);
        parent.setLeft(this.tree);
        parent.setCenter(this.content);
    }    

    /**
     * The function used to set the stage that is active. This is used to
     * provide subcomponents a place to display.
     * 
     * @param stage The active stage
     */
    public void setStage(Stage stage)
    {
        // Store the stage
        this.stage = stage;

        // And update child components
        this.menu.setStage(this.stage);
    }

    /**
     * The update function. This takes a specified project and ensures that the
     * application reflects the active project state.
     * 
     * @param ui The application UI
     * @param project The project to display
     */
    public void update(UI ui, Project project)
    {
        // Update the components
        this.menu.update(ui, project);
        this.tree.update(ui, project);
        this.content.update(ui, project);
    }

    /**
     * A simple function used to open a tab in the main interface.
     * 
     * @param tab The tab to open
     */
    public void addTab(ContentTab tab)
    {
        this.content.addTab(tab);
    }

    /**
     * The content pane for adding content to the window.
     * 
     * @return The content pane
     */
    public ContentPane getContentPane()
    {
        return this.content;
    }
}
