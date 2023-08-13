package com.stata.ui.components;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * The main menu bar of the Stata application. This stores the menu items that
 * users can use to control the application.
 * 
 * @author Dan Jenkins
 */
public class MainMenuBar extends MenuBar
{
    /** The file menu */
    private Menu fileMenu;

    /** The open file menu item */
    private MenuItem newMenuItem;

    /** The open file menu item */
    private MenuItem openMenuItem;

    /** The open file menu item */
    private MenuItem saveMenuItem;

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
     * The function used to create the file menu items
     */
    private void createFileMenuItems()
    {
        // Create the file menu
        this.fileMenu = new Menu("File");

        // Create the file menu items
        this.newMenuItem  = new MenuItem("New");
        this.openMenuItem = new MenuItem("Open");
        this.saveMenuItem = new MenuItem("Save");

        // Add all the file menu items
        this.fileMenu.getItems().addAll
        (
            this.newMenuItem,
            this.openMenuItem,
            this.saveMenuItem  
        );

        // And add the file menu
        this.getMenus().add(this.fileMenu);
    }
}
