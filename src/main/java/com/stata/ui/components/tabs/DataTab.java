package com.stata.ui.components.tabs;

import com.stata.ui.UI;
import com.stata.ui.components.ContentPane;
import com.stata.ui.components.ContentTab;
import com.stata.Stata;
import com.stata.project.Datatable;
import com.stata.project.Project;

import javafx.scene.control.TableView;

/**
 * The contant tab representing a data table. This displays the data in a table
 * to the user so that they can inspect or modify it later.
 * 
 * @author Dan Jenkins
 */
public class DataTab extends ContentTab
{
    /** The data that this table presents. */
    private Datatable data;

    /** The table that forms this tab. */
    private TableView table;

    /**
     * The default constructor. This creates a new data tab with a table of
     * data to display to the user.
     * 
     * @param parent The parent pane
     * @param data The table for the tab to represent
     */
    public DataTab(ContentPane parent, String data)
    {
        // Create the content tab
        super(parent);

        // Store the underlying data
        this.data = Stata.getInstance().getProject().getDatatables().get(data);

        // Set the title
        this.setText(this.data.getName());

        // Create the table
        this.table = new TableView();
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
        this.setText(this.data.getName());
    }
}
