package com.stata.ui.components.tabs;

import com.stata.ui.components.ContentPane;
import com.stata.ui.components.ContentTab;

import javafx.scene.control.TableView;

/**
 * The contant tab representing a data table. This displays the data in a table
 * to the user so that they can inspect or modify it later.
 * 
 * @author Dan Jenkins
 */
public class DataTab extends ContentTab
{
    /** The table that forms this tab. */
    private TableView table;

    /**
     * The default constructor. This creates a new data tab with a table of
     * data to display to the user.
     * 
     * @param parent The parent pane
     */
    public DataTab(ContentPane parent)
    {
        // Create the content tab
        super(parent);

        // Create the table
        this.table = new TableView();
    }
}
