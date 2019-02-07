package life.qbic.portal.presenter;

import com.vaadin.ui.VerticalLayout;
import life.qbic.portal.model.DBConfig;
import life.qbic.portal.model.DBManager;
import life.qbic.portal.utils.ConfigurationManager;
import life.qbic.portal.utils.ConfigurationManagerFactory;
import life.qbic.portal.utils.LiferayIndependentConfigurationManager;
import life.qbic.portal.utils.PortalUtils;
import life.qbic.portal.view.Overview.ProjectsLayout;

public class MainPresenter {

    //TODO in case a project is pressed directly add edit function, but only for admins?

    private final VerticalLayout canvas;
    private final ProjectsLayout projectsLayout;

    private final DBManager db;

    public MainPresenter() {
        this.projectsLayout = new ProjectsLayout();
        this.canvas = new VerticalLayout();


        ConfigurationManager config;

        if (PortalUtils.isLiferayPortlet()) {
            config = ConfigurationManagerFactory.getInstance();
        } else {
            LiferayIndependentConfigurationManager.Instance.init("local.properties");
            config = LiferayIndependentConfigurationManager.Instance;
        }

        db = new DBManager(new DBConfig(config.getMysqlHost(), config.getMysqlPort(),
                config.getNCCTMysqlDB(), config.getMysqlUser(), config.getMysqlPass()));

        initDB();
        addListener();

        loadProjects();
        this.canvas.addComponent(projectsLayout);
    }

    private void initDB() {
        db.initVocabularies();
    }

    private void addListener() {
        addButtonListener();
    }

    private void addButtonListener() {
        this.projectsLayout.getAddNewProjectButton().addClickListener(clickEvent -> {
            this.canvas.removeAllComponents();
            FormPresenter formPresenter = new FormPresenter(this);
            this.canvas.addComponent(formPresenter.getFormLayout());
        });
    }

    public void displayProjects() {
        this.canvas.removeAllComponents();
        this.canvas.addComponent(projectsLayout);
    }

    void loadProjects() {
        this.projectsLayout.getProjects().getContainerDataSource().removeAllItems();
        db.getProjects().forEach(project -> {
            System.out.println(project.getTitle());
            this.projectsLayout.getProjects().addRow(project.getDfgID(),
                    project.getTitle(),
                    project.getContactPerson().getFirstName().concat(" ").concat(project.getContactPerson().getLastName()),
                    project.getDescription());
        });
    }

    public VerticalLayout getCanvas() {
        return canvas;
    }

    public DBManager getDb() {
        return db;
    }
}
