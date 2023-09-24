package com.stata.ui.components;

import com.stata.project.Project;
import com.stata.ui.UI;
import com.stata.ui.components.tabs.DataTab;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TabPane;

/**
 * The content pane. This is a tab pane that allows multiple tabs to be
 * displayed to the user at a time.
 * 
 * @author Dan Jenkins
 */
public class ContentPane extends TabPane
{
    private final List<ContentTab> tabs;

    /**
     * The default constructor. This creates the content pane so that tabs can
     * be added later.
     */
    public ContentPane()
    {
        // Create the tab list
        this.tabs = new ArrayList<>();

        this.addTab(new DataTab(this));
    }

    /**
     * The function used to add a tab. This adds the tab to our own list of
     * tabs and ensures that it is visible in the user interface.
     * 
     * @param tab The tab to add.
     */
    public void addTab(ContentTab tab)
    {
        // Add the tab to our list
        this.tabs.add(tab);

        // And ensure that it appears in the tabbed pane
        this.getTabs().add(tab);
    }

    /**
     * The function used to remove a tab. This removes the tab from our own
     * list of tabs and ensures that it is hidden from the user interface.
     * 
     * @param tab The tab to remove
     */
    public void removeTab(ContentTab tab)
    {
        // Remove the tab from our list
        this.tabs.remove(tab);

        // And ensure that it is hidden in the tabbed pane
        this.getTabs().remove(tab);
    }

    /**
     * The update function. This takes a specified project and ensures that the
     * pane reflects the active project state.
     * 
     * @param ui The application UI
     * @param project The project to display
     */
    public void update(UI ui, Project project)
    {
        // Update each of the tabs
        for (ContentTab tab : this.tabs)
        {
            tab.update(ui, project);
        }
    }
}
