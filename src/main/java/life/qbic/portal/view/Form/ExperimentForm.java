package life.qbic.portal.view.Form;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import life.qbic.portal.view.utils.CustomStyle;

import java.util.Date;

/**
 * @author fhanssen
 */


public class ExperimentForm extends VerticalLayout {

    private final Grid allExperiments;

    private final TextField numberOfSamplesExperiment;
    private final TextField coverage;
    private final TextField costs;
    private final TextField genomeSize;
    private final ComboBox material;
    private final ComboBox instrument;
    private final ComboBox species;
    private final ComboBox type;
    private final ComboBox library;
    private final ComboBox nucleicAcid;

    private final Button deleteExperimentButton;


    private final TabSheet batches = new TabSheet();
    private int counter = 0;

    public ExperimentForm() {

        this.allExperiments = new Grid();
        this.allExperiments.addColumn("ID", Integer.class);
        this.allExperiments.addColumn("Read Type", String.class);
        this.allExperiments.addColumn("Species", String.class);
        this.allExperiments.addColumn("Material", String.class);
        this.allExperiments.addColumn("Instrument", String.class);
        this.allExperiments.addColumn("Library", String.class);
        this.allExperiments.addColumn("Genome Size(Gb)", String.class);
        this.allExperiments.addColumn("Nucleic Acid", String.class);
        this.allExperiments.addColumn("Coverage(X)", String.class);
        this.allExperiments.addColumn("Number of Samples", String.class);
        this.allExperiments.addColumn("Cost(EUR)", String.class);

        CustomStyle.addGridSettings(allExperiments);

        this.type = new ComboBox("Read Type");
        CustomStyle.addComboboxSettings(type);

        this.species = new ComboBox("Species");
        CustomStyle.addComboboxSettings(species);

        this.material = new ComboBox("Material");
        CustomStyle.addComboboxSettings(material);

        this.instrument = new ComboBox("Instrument");
        CustomStyle.addComboboxSettings(instrument);

        this.library = new ComboBox("Library");
        CustomStyle.addComboboxSettings(library);

        this.genomeSize = new TextField("Genome Size(Gb)");
        CustomStyle.addTextFieldSettings(genomeSize, "[0-9]+\\.?[0-9]*", "Number must be formatted as positive double");

        this.nucleicAcid = new ComboBox("Nucleic Acid");
        CustomStyle.addComboboxSettings(nucleicAcid);

        this.coverage = new TextField("Coverage(X)");
        CustomStyle.addTextFieldSettings(coverage, "[0-9]+", "Must be positive number");

        this.numberOfSamplesExperiment = new TextField("Number of Samples");
        CustomStyle.addTextFieldSettings(numberOfSamplesExperiment, "[0-9]+", "Must be positive number");

        this.costs = new TextField("Cost(EUR)");
        CustomStyle.addTextFieldSettings(costs, "[0-9]+(\\.[0-9][0-9]?)?", "Number must be formatted as 123.45!");

        this.allExperiments.getColumn("Read Type").setEditorField(type);
        this.allExperiments.getColumn("Species").setEditorField(species);
        this.allExperiments.getColumn("Material").setEditorField(material);
        this.allExperiments.getColumn("Instrument").setEditorField(instrument);
        this.allExperiments.getColumn("Library").setEditorField(library);
        this.allExperiments.getColumn("Genome Size(Gb)").setEditorField(genomeSize);
        this.allExperiments.getColumn("Nucleic Acid").setEditorField(nucleicAcid);
        this.allExperiments.getColumn("Coverage(X)").setEditorField(coverage);
        this.allExperiments.getColumn("Number of Samples").setEditorField(numberOfSamplesExperiment);
        this.allExperiments.getColumn("Cost(EUR)").setEditorField(costs);

        this.deleteExperimentButton = new Button("Delete");
        this.deleteExperimentButton.setVisible(false);
        this.deleteExperimentButton.addStyleName("corners");

        this.setSpacing(true);


        this.addComponents(new Label("<b><u> Current Experiments: </u></b>", ContentMode.HTML), allExperiments, deleteExperimentButton,
                new Label("<b><u>Batches:</u></b>", ContentMode.HTML), batches);


    }

    public void addEmptyExperimentRow() {// whenever a experiment is successfully added to grid, then add new line
        this.allExperiments.addRow(counter + 1, "", "", "", "", "", "", "", "", "", "");
        BatchForm batchForm = new BatchForm();
        batchForm.addEmptyBatchRow();
        batches.addTab(batchForm, "Experiment " + (counter + 1));
        counter++;
    }

    public BatchForm getLastTab() {
        return (BatchForm) batches.getTab(counter - 1).getComponent();
    }

    public TextField getNumberOfSamplesExperiment() {
        return numberOfSamplesExperiment;
    }

    public TextField getCoverage() {
        return coverage;
    }

    public TextField getCosts() {
        return costs;
    }

    public TextField getGenomeSize() {
        return genomeSize;
    }

    public ComboBox getMaterial() {
        return material;
    }

    public ComboBox getInstrument() {
        return instrument;
    }

    public ComboBox getSpecies() {
        return species;
    }

    public ComboBox getType() {
        return type;
    }

    public ComboBox getLibrary() {
        return library;
    }

    public ComboBox getNucleicAcid() {
        return nucleicAcid;
    }

    public Grid getAllExperiments() {
        return allExperiments;
    }

    public TabSheet getBatches() {
        return batches;
    }

    public void addExperimentRow(String type, String species, String material, String instrument, String library, String
            genomeSize, String nucleicAcid, String coverage, String numSamples, String cost){

        this.allExperiments.addRow(counter+1, type, species, material, instrument, library, genomeSize, nucleicAcid, coverage, numSamples, cost);
        BatchForm batchForm = new BatchForm();
        batches.addTab(batchForm, "Experiment " + (counter + 1));
    }

    public void incrementCounter() {
        counter++;
    }

    public void addBatch(Date date, String numSamples){
        ((BatchForm)this.batches.getTab(counter).getComponent()).addDataRow(date, numSamples);
    }

    public int getCounter() {
        return counter;
    }

    public void addDeleteExperimentButton(){
        deleteExperimentButton.setVisible(true);
    }

    public void removeDeleteExperimentButton(){
        deleteExperimentButton.setVisible(false);
    }

    public Button getDeleteExperimentButton() {
        return deleteExperimentButton;
    }

    public void deleteBatch(int counter){
        batches.getUI().access(new Runnable() {
            @Override
            public void run() {
                for(Component component : batches){
                    TabSheet.Tab tab = batches.getTab(component);

                    if ("Experiment ".concat(String.valueOf(counter)).equals(tab.getCaption())) {
                        batches.removeTab(tab);
                    }
                }
            }
        });

    }

}
