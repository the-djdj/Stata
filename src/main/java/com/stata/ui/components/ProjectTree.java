package com.stata.ui.components;

import com.stata.project.Project;
import com.stata.ui.UI;

import java.util.Arrays;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * The ProjectTree class. This is displayed on the side of the main display and
 * shows the user the components of the project.
 * 
 * @author Dan Jenkins
 */
public class ProjectTree extends TreeView<String> implements EventHandler<TreeItem.TreeModificationEvent<String>>
{
    /** The Stata UI. */
    private UI ui;

    /** The project that this tree represents. */
    private Project project;

    /** The tree item that represents the project. */
    private TreeItem<String> root;

    /** The default factory used by the TreeView. */
    private Callback<TreeView<String>, TreeCell<String>> factory;

    /** The data tables in the project. */
    private TreeItem<String> datatables;

    /**
     * The default constructor. This creates a new ProjectTree based off of a
     * provided project.
     */
    public ProjectTree()
    {
        // Create the main items
        this.root = new TreeItem<>();
        this.datatables = new TreeItem<>("Data tables");

        // Add the graphics
        this.root.setGraphic(new ImageView("resources/project.png"));
        this.datatables.setGraphic(new ImageView("resources/datatable.png"));

        // Add the listeners
        this.root.addEventHandler(TreeItem.valueChangedEvent(), this);

        // Create the default tree cell factory
        this.factory = TextFieldTreeCell.forTreeView();

        // Configure the tree
        this.setEditable(true);
        this.setCellFactory(
            (TreeView<String> view) -> {
                TreeCell<String> cell = this.factory.call(view);
                cell.treeItemProperty().addListener(
                    (obs, oldTreeItem, newTreeItem) -> {
                        // Check whether the item should be editable or not
                        if (newTreeItem == null)
                        {
                            cell.setEditable(false);
                        }
                        else
                        {
                            cell.setEditable(!this.getUneditableItems().contains(newTreeItem));
                        }
                    }
                );
                return cell;
            }
        );

        // Add the main items
        this.root.getChildren().add(this.datatables);

        // And add the root item
        this.setRoot(this.root);
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

        // Configure the main items
        this.root.setExpanded(true);
        this.root.setValue(this.project.getMetadata().getName());
    }

    /**
     * The handle function. This tree modification events on the project tree.
     * 
     * @param event The change event
     */
    @Override
    public void handle(TreeItem.TreeModificationEvent<String> event)
    {
        // Check the source of the event
        if (event.getSource() == this.root)
        {
            // Update the project name
            this.project.getMetadata().setName(event.getNewValue());

            // And update the project
            this.ui.update();
        }        
    }

    /**
     * A simple function used to return a list of the items that should not be
     * edited by users. Items that should not be edited include headings such
     * as datatables and graphs.
     * 
     * @return A list of uneditable items
     */
    public List<TreeItem<String>> getUneditableItems()
    {
        return Arrays.asList(
            this.datatables
        );
    }
}
