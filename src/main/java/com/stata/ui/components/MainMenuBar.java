package com.stata.ui.components;

import com.stata.Stata;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

/**
 * The main menu bar of the Stata application. This stores the menu items that
 * users can use to control the application.
 * 
 * @author Dan Jenkins
 */
public class MainMenuBar extends MenuBar implements EventHandler<ActionEvent>
{
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

    /**
     * The default constructor. This creates a new menu bar with the relevant
     * options for the user
     */
    public MainMenuBar()
    {
        // Create the menus
        this.createFileMenuItems();
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

        }
        else if (event.getSource() == this.saveMenuItem)
        {

        }
        else if (event.getSource() == this.exitMenuItem)
        {
            // Close the application
            Stata.getInstance().exit();
        }
    }
}
