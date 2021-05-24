
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
 * Controller for Add Part screen <br>
 *
 */
public class addPartController {

    static int currentId = 0;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="inHouseRadioButton"
    private RadioButton inHouseRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="partTypeToggle"
    private ToggleGroup partTypeToggle; // Value injected by FXMLLoader

    @FXML // fx:id="outsourcedRadioButton"
    private RadioButton outsourcedRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="nameBox"
    private TextField nameBox; // Value injected by FXMLLoader

    @FXML // fx:id="inventoryBox"
    private TextField inventoryBox; // Value injected by FXMLLoader

    @FXML // fx:id="costBox"
    private TextField costBox; // Value injected by FXMLLoader

    @FXML // fx:id="maxBox"
    private TextField maxBox; // Value injected by FXMLLoader

    @FXML // fx:id="inhouseText"
    private Text inhouseText; // Value injected by FXMLLoader

    @FXML // fx:id="minBox"
    private TextField minBox; // Value injected by FXMLLoader

    @FXML // fx:id="outsourcedText"
    private Text outsourcedText; // Value injected by FXMLLoader

    @FXML
    private TextField companyNameTextBox;

    @FXML
    private TextField machineIdTextBox;

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    /**
     * Closes the Add Part Window <br>
     * Stage gets the current open window, then uses the close method <br>
     */
    @FXML
    void cancelButtonAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Removing Part");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure you want to close without saving?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        }
    }

    /**
     * When the In-House radio button is selected, the last entry box
     * displays Machine ID. In addition, the Add Part Window will save
     * an InHouse part while this is active.
     */
    @FXML
    void inHouseRadioButtonAction(ActionEvent event) {
        outsourcedText.setVisible(false);
        companyNameTextBox.setVisible(false);
        inhouseText.setVisible(true);
        machineIdTextBox.setVisible(true);
    }


    /**
     * When the Outsourced radio button is selected, the last entry box
     * displays Company Name. In addition, the Add Part Window will save
     * an Outsourced part while this is active.
     */
    @FXML
    void outsourcedRadioButtonAction(ActionEvent event) {
        outsourcedText.setVisible(true);
        companyNameTextBox.setVisible(true);
        inhouseText.setVisible(false);
        machineIdTextBox.setVisible(false);
    }

    /**
     * When Save is pushed, first determine of entries are valid. <br>
     * If any entry is invalid, showError() is called to display a JavaFX error box <br>
     * Otherwise, function checks if part is InHouse or Outsourced <br>
     * Then, create new constructor, set values, then call addPart <br>
     */
    @FXML
    void saveButtonAction(ActionEvent event) {

        //Initialize
        int inventory;
        int min;
        int max;
        double price;
        int machineId = 0;


        //Make sure that integer inputs are valid
        try { inventory = parseInt(inventoryBox.getText()); } catch (NumberFormatException e) {
            showError("Inventory Error", "Inventory must be a number", "Text was entered into the Inventory box.\n Please confirm your entry, and try again");
            return;
        }
        try { price = parseDouble(costBox.getText()); } catch (NumberFormatException e) {
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
                try {machineId = parseInt(machineIdTextBox.getText()); } catch (NumberFormatException e) {
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


        //Increase the current ID
        currentId ++;

        //Add InHouse
        if (inHouseRadioButton.isSelected()) {
            InHouse inhouse = new InHouse(currentId,nameBox.getText(),price,inventory,min,max);
            inhouse.setName(nameBox.getText());
            inhouse.setStock(inventory);
            inhouse.setPrice(price);
            inhouse.setMin(min);
            inhouse.setMax(max);
            inhouse.setMachineId(machineId);
            inhouse.setId(currentId);

            //Add the Part
            Inventory.addPart(inhouse);
        } else { //Otherwise, add Outsourced
            Outsourced outsourced = new Outsourced(currentId,nameBox.getText(),price,inventory,min,max);
            outsourced.setName(nameBox.getText());
            outsourced.setStock(inventory);
            outsourced.setPrice(price);
            outsourced.setMin(min);
            outsourced.setMax(max);
            outsourced.setCompanyName(companyNameTextBox.getText());
            outsourced.setId(currentId);

            //Add the part
            Inventory.addPart(outsourced);
        }

        //Close the window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays a JavaFX Alert with a title header, and content. <br>
     * showAndWait is called to keep box alive <br>
     * @param title: set the title of the error box <br>
     * @param header: set contents of header <br>
     * @param content: set contents of  <br>
     */
    private void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Initializes the screen <br>
     * ensures that all parts of the screen are loaded correctly <br>
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert inHouseRadioButton != null : "fx:id=\"inHouseRadioButton\" was not injected: check your FXML file 'addPart.fxml'.";
        assert partTypeToggle != null : "fx:id=\"partTypeToggle\" was not injected: check your FXML file 'addPart.fxml'.";
        assert outsourcedRadioButton != null : "fx:id=\"outsourcedRadioButton\" was not injected: check your FXML file 'addPart.fxml'.";
        assert nameBox != null : "fx:id=\"nameBox\" was not injected: check your FXML file 'addPart.fxml'.";
        assert inventoryBox != null : "fx:id=\"inventoryBox\" was not injected: check your FXML file 'addPart.fxml'.";
        assert costBox != null : "fx:id=\"costBox\" was not injected: check your FXML file 'addPart.fxml'.";
        assert maxBox != null : "fx:id=\"maxBox\" was not injected: check your FXML file 'addPart.fxml'.";
        assert inhouseText != null : "fx:id=\"inhouseText\" was not injected: check your FXML file 'addPart.fxml'.";
        assert minBox != null : "fx:id=\"minBox\" was not injected: check your FXML file 'addPart.fxml'.";
        assert outsourcedText != null : "fx:id=\"outsourcedText\" was not injected: check your FXML file 'addPart.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'addPart.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'addPart.fxml'.";

    }
}
