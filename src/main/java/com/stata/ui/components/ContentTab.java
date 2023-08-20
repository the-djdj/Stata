package com.stata.ui.components;

import com.stata.project.Project;
import com.stata.ui.UI;

import javafx.scene.control.Tab;

/**
 * The content tab class. This represents a tab that can be displayed in
 * the application to the user.
 * 
 * @author Dan Jenkins
 */
public class ContentTab extends Tab
{
    /** The parent content pane. */
    private final ContentPane parent;

    /**
     * The default constructor. This creates a new content tab and configures
     * it correctly for use in Stata
     */
    public ContentTab(ContentPane parent)
    {
        // Store the parent pane
        this.parent = parent;
    }

    /**
     * The update function. This takes a specified project and ensures that the
     * tab reflects the active project state.
     * 
     * @param ui The application UI
     * @param project The project to display
     */
    public void update(UI ui, Project project)
    {

    }
}
