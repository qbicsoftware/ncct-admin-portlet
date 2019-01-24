package life.qbic.portal.view.Overview;

import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Grid;

public class Projects extends Grid {


    public Projects(){
        this.addColumn("ID", String.class);
        this.addColumn("Title", String.class);
        this.addColumn("Contact Person", String.class);
        this.addColumn("Description", String.class);

        this.setEditorEnabled(false);
        this.setHeightMode( HeightMode.ROW );
        this.setHeightByRows(5);
        this.setSizeFull();
    }
}