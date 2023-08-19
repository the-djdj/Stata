package com.stata.ui.components;

import com.stata.Stata;
import com.stata.project.Project;
import com.stata.ui.UI;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * The main menu bar of the Stata application. This stores the menu items that
 * users can use to control the application.
 * 
 * @author Dan Jenkins
 */
public class MainMenuBar extends MenuBar implements EventHandler<ActionEvent>
{
    /** The Stata UI. */
    private UI ui;

    /** The project that this menu bar represents. */
    private Project project;

    /** The active stage. */
    private Stage stage;

    /** The file menu */
    private Menu fileMenu;

    /** The new file menu item */
    private MenuItem newMenuItem;

    /** The open file menu item */
    private MenuItem openMenuItem;

    /** The save file menu item */
    private MenuItem saveMenuItem;

    /** The exit menu item. */
    private MenuItem exitMenuItem;

    /** The file chooser for opening and saving files. */
    private FileChooser chooser;

    /** The default location to use in the file choosers. */
    private File directory;

    /** The file that is chosen by the file chooser. */
    private File file;

    /** The extension filter for choosing Stata files. */
    private ExtensionFilter filter;

    /**
     * The default constructor. This creates a new menu bar with the relevant
     * options for the user
     */
    public MainMenuBar()
    {
        // Create the menus
        this.createFileMenuItems();

        // Create the file chooser environment
        this.directory = new File(System.getProperty("user.home"));
        this.filter = new ExtensionFilter("Stata project", "*.stata");

        // Create the file chooser
        this.chooser = new FileChooser();
        this.chooser.getExtensionFilters().add(this.filter);
    }

    /**
     * The function used to create the file menu items. This craetes the items,
     * adds the keyboard shortcuts, sets the action event handlers, and adds
     * them to the file menu.
     */
    private void createFileMenuItems()
    {
        // Create the file menu
        this.fileMenu = new Menu("File");

        // Create the file menu items
        this.newMenuItem  = new MenuItem("New");
        this.openMenuItem = new MenuItem("Open");
        this.saveMenuItem = new MenuItem("Save");
        this.exitMenuItem = new MenuItem("Exit");

        // Create the keyboard shortcuts
        this.newMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
        this.openMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+O"));
        this.saveMenuItem.setAccelerator(KeyCombination.keyCombination("CTRL+S"));

        // Create the action event listeners
        this.newMenuItem.setOnAction(this);
        this.openMenuItem.setOnAction(this);
        this.saveMenuItem.setOnAction(this);
        this.exitMenuItem.setOnAction(this);

        // Add all the file menu items
        this.fileMenu.getItems().addAll
        (
            this.newMenuItem,
            this.openMenuItem,
            this.saveMenuItem,
            new SeparatorMenuItem(),
            this.exitMenuItem
        );

        // And add the file menu
        this.getMenus().add(this.fileMenu);
    }

    /**
     * The function which handles button input whenever a menu item is clicked.
     * 
     * @param event The item click event
     */
    @Override
    public void handle(ActionEvent event)
    {
        // Get the source of the action event
        if (event.getSource() == this.newMenuItem)
        {
            
        }
        else if (event.getSource() == this.openMenuItem)
        {
            // Configure the file chooser
            this.chooser.setTitle("Open project");
            this.chooser.setInitialDirectory(this.directory); 

            // Display the file chooser
            this.file = this.chooser.showOpenDialog(this.stage);

            // And handle the input
            if (this.file != null)
            {
                // Load the file
                this.project.load(this.file);

                // And update the interface
                this.ui.update();
            }
        }
        else if (event.getSource() == this.saveMenuItem)
        {
            // Configure the file chooser
            this.chooser.setTitle("Save project");
            this.chooser.setInitialDirectory(this.directory); 

            // And display the file chooser
            this.file = this.chooser.showSaveDialog(this.stage);

            // And handle the input
            if (this.file != null)
            {
                // Save the file
                this.project.save(this.file);

                // And update the interface
                this.ui.update();
            }
        }
        else if (event.getSource() == this.exitMenuItem)
        {
            // Close the application
            Stata.getInstance().exit();
        }
    }

    /**
     * The function used to set the stage that is active. This is used to
     * provide subcomponents a place to display.
     * 
     * @param stage The active stage
     */
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    /**
     * The update function. This takes a specified project and ensures that the
     * tree reflects the active project state.
     * 
     * @param ui The application UI
     * @param project The project to display
     */
    public void update(UI ui, Project project)
    {
        // Store the updated variables
        this.ui = ui;
        this.project = project;
    }
}
