package com.stata.ui.components;

import com.stata.project.Project;
import com.stata.ui.UI;

import javafx.scene.control.TabPane;

/**
 * The content pane. This is a tab pane that allows multiple tabs to be
 * displayed to the user at a time.
 * 
 * @author Dan Jenkins
 */
public class ContentPane extends TabPane
{
    /**
     * The default constructor. This creates the content pane so that tabs can
     * be added later.
     */
    public ContentPane()
    {

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

    }
}
