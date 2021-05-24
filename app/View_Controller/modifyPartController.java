
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * @author Rafael Santos <br>
 * For WGU C482 <br>
 *
 * Controller for Modify Part screen
 */
public class modifyPartController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="inHouseText"
    private Text inHouseText; // Value injected by FXMLLoader

    @FXML // fx:id="outsourcedText"
    private Text outsourcedText; // Value injected by FXMLLoader

    @FXML // fx:id="inHouseRadioButton"
    private RadioButton inHouseRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="outsourcedRadioButton"
    private RadioButton outsourcedRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="partTypeToggle"
    private ToggleGroup partTypeToggle; // Value injected by FXMLLoader

    @FXML // fx:id="idBox"
    private TextField idBox; // Value injected by FXMLLoader

    @FXML // fx:id="nameBox"
    private TextField nameBox; // Value injected by FXMLLoader

    @FXML // fx:id="inventoryBox"
    private TextField inventoryBox; // Value injected by FXMLLoader

    @FXML // fx:id="priceBox"
    private TextField priceBox; // Value injected by FXMLLoader

    @FXML // fx:id="maxBox"
    private TextField maxBox; // Value injected by FXMLLoader

    @FXML // fx:id="minBox"
    private TextField minBox; // Value injected by FXMLLoader

    @FXML // fx:id="inHouseBox"
    private TextField inHouseBox; // Value injected by FXMLLoader

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="companyNameBox"
    private TextField companyNameBox; // Value injected by FXMLLoader

    /**
     * Closes the Modify Part Window <br>
     * Stage gets the current open window, then uses the close method <br>
     */
    @FXML
    void cancelButtonAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Modifying Part");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure you want to exit without saving?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Makes outsourced text and box invisible, <br>
     * and activates the inhouse text and box <br>
     */
    @FXML
    void inHouseRadioButtonAction(ActionEvent event) {
        outsourcedText.setVisible(false);
        companyNameBox.setVisible(false);
        inHouseBox.setVisible(true);
        inHouseText.setVisible(true);

    }


    /**
     * Makes inhouse text and box invisible, <br>
     * and activates the outsourced text and box <br>
     */
    @FXML
    void outsourcedRadioButtonAction(ActionEvent event) {
        outsourcedText.setVisible(true);
        companyNameBox.setVisible(true);
        inHouseBox.setVisible(false);
        inHouseText.setVisible(false);

    }


    /**
     *
     * When Save is pushed, first determine of entries are valid. <br>
     * If any entry is invalid, showError() is called to display a JavaFX error box <br>
     * Otherwise, function checks if part is InHouse or Outsourced <br>
     * Then, create new constructor, set values, then call addPart <br>
     */
    @FXML
    void saveButtonAction(ActionEvent event) {
        //Used to round price to 2 decimal places in the event that the user input more than 2
        DecimalFormat decimalformat = new DecimalFormat("#.00");

        //Initialize
        int id = Integer.parseInt(idBox.getText());
        int inventory = 0;
        int min = 0;
        int max = 0;
        double price = 0;
        int machineId = 0;

        int index = mainFormController.getSelectedPartIndex();



        //Make sure that integer inputs are valid
        try { inventory = parseInt(inventoryBox.getText()); } catch (NumberFormatException e) {
            showError("Inventory Error", "Inventory must be a number", "Text was entered into the Inventory box.\n Please confirm your entry, and try again");
            return;
        }
        try { price = parseDouble(priceBox.getText()); } catch (NumberFormatException e) {
            showError("Price Error", "Price must be a number (without preceding $)", "Text was entered into the Price box.\n Please check your entry and try again\n(Note: $ should not be entered.");
            return;
        }
        try { min = parseInt(minBox.getText()); } catch (NumberFormatException e) {
            showError("Min Error", "Min must be a number", "A letter was entered into the Min box.\n Please check your input and try again");
            return;
        }
        try { max = parseInt(maxBox.getText()); } catch (NumberFormatException e) {
            showError("Max Error", "Max must be a number", "Text was entered into the Max box.\n Please check your input and try again");
            return;
        }
        if (inHouseRadioButton.isSelected()) {
            try {machineId = parseInt(inHouseBox.getText()); } catch (NumberFormatException e) {
                showError("Machine ID Error", "Machine ID must be a number", "Text was entered into the Machine ID box.\n Please check your input and try again");
                return;
            }
        }

        //Make sure integer inputs are not negative
        if (inventory < 0) {
            showError("Inventory Error", "Inventory is negative", "Inventory cannot be a negative number.\n Please check your entry and try again.");
            return;
        }
        if (price < 0) {
            showError("Price Error", "Price is negative", "Price cannot be a negative number.\n Please check your entry and try again.");
            return;
        }
        if (max < 0) {
            showError("Max Error", "Max is negative", "Max cannot be a negative number.\n Please check your entry and try again.");
            return;
        }
        if (min < 0) {
            showError("Min Error", "Min is negative", "Min cannot be a negative number.\n Please check your entry and try again.");
            return;
        }
        if (min > max) {
            showError("Min/Max Error", "Min is greater than Max", "Min cannot exceed Max.\n Please check your entry and try again.");
            return;
        }
        if (inventory < min) {
            showError("Inventory Minimum Error", "Inventory is less yhan minimum", "Inventory is less than the specified Minimum.\n Please check your entry and try again.");
            return;
        }
        if (inventory > max) {
            showError("Inventory Maximum Error", "Inventory is greater than maximum", "Inventory is greater than the specified Maximum.\n Please check your entry and try again.");
            return;
        }

        if (inHouseRadioButton.isSelected()) {
            InHouse inhouse = new InHouse(id,nameBox.getText(),price,inventory,min,max);
            inhouse.setId(id);
            inhouse.setName(nameBox.getText());
            inhouse.setStock(inventory);
            inhouse.setPrice(price);
            inhouse.setMin(min);
            inhouse.setMax(max);
            inhouse.setMachineId(machineId);

            //Update the part
            Inventory.updatePart(index, inhouse);
        } else {

            //If it is not an InHouse, then it has to be an Outsourced part
            Outsourced outsourced = new Outsourced(id,nameBox.getText(),price,inventory,min,max);
            outsourced.setId(id);
            outsourced.setName(nameBox.getText());
            outsourced.setStock(inventory);
            outsourced.setPrice(price);
            outsourced.setMin(min);
            outsourced.setMax(max);
            outsourced.setCompanyName(companyNameBox.getText());

            //Update the part
            Inventory.updatePart(index, outsourced);
        }

        //Close the window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    /**
     * Initializes the screen <br>
     * ensures that all parts of the screen are loaded correctly. <br>
     * Populates selected part information <br>
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert inHouseText != null : "fx:id=\"inHouseText\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert outsourcedText != null : "fx:id=\"outsourcedText\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert inHouseRadioButton != null : "fx:id=\"inHouseRadioButton\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert outsourcedRadioButton != null : "fx:id=\"outsourcedRadioButton\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert partTypeToggle != null : "fx:id=\"partTypeToggle\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert idBox != null : "fx:id=\"idBox\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert nameBox != null : "fx:id=\"nameBox\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert inventoryBox != null : "fx:id=\"inventoryBox\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert priceBox != null : "fx:id=\"priceBox\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert maxBox != null : "fx:id=\"maxBox\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert minBox != null : "fx:id=\"minBox\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert inHouseBox != null : "fx:id=\"inHouseBox\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'modifyPart.fxml'.";
        assert companyNameBox != null : "fx:id=\"companyNameBox\" was not injected: check your FXML file 'modifyPart.fxml'.";


        //Get selected part index from mainFormController
        int index = mainFormController.getSelectedPartIndex();


        //Get all parts and narrow down to the selected index
        ObservableList<Part> parts = Inventory.getAllParts();
        Part selectedPart = parts.get(index);

        //Add values from part to the
        idBox.setText(String.valueOf(selectedPart.getId()));
        nameBox.setText(selectedPart.getName());
        priceBox.setText(String.valueOf(selectedPart.getPrice()));
        inventoryBox.setText(String.valueOf(selectedPart.getStock()));
        maxBox.setText(String.valueOf(selectedPart.getMax()));
        minBox.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            inHouseBox.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else {

            //Select Outsourced radiobutton
            outsourcedRadioButton.fire();
            companyNameBox.setText(((Outsourced) selectedPart).getCompanyName());
        }

    }



    /**showError
     * Displays a JavaFX Alert with a title header, and content. <br>
     * showAndWait is called to keep box alive <br>
     * @param title: set the title of the error box <br>
     * @param header: set contents of header <br>
     * @param content: set contents of message <br>
     */
    private void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }





}
