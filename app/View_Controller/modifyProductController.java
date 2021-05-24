
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * @author Rafael Santos <br>
 * For WGU C482 <br>
 * Controller for Modify Product window
 */
public class modifyProductController {

    ObservableList<Part> allParts = FXCollections.observableArrayList();
    Product selectedProduct = new Product();

    //Get selected product index from mainFormController
    int index = mainFormController.getSelectedProductIndex();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML
    private Text partSearchFail;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="productSearchBox"
    private TextField productSearchBox; // Value injected by FXMLLoader

    @FXML // fx:id="idTextBox"
    private TextField idTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="nameTextBox"
    private TextField nameTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="invTextBox"
    private TextField invTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="priceTextBox"
    private TextField priceTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="maxTextBox"
    private TextField maxTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="minTextBox"
    private TextField minTextBox; // Value injected by FXMLLoader

    @FXML // fx:id="allPartDataTable"
    private TableView<Part> allPartDataTable; // Value injected by FXMLLoader

    @FXML // fx:id="allPartPartID"
    private TableColumn<?, ?> allPartPartID; // Value injected by FXMLLoader

    @FXML // fx:id="allPartPartName"
    private TableColumn<?, ?> allPartPartName; // Value injected by FXMLLoader

    @FXML // fx:id="allPartInventoryLevel"
    private TableColumn<?, ?> allPartInventoryLevel; // Value injected by FXMLLoader

    @FXML // fx:id="allPartPrice"
    private TableColumn<Part, Double> allPartPrice; // Value injected by FXMLLoader

    @FXML // fx:id="assocPartDataTable"
    private TableView<Part> assocPartDataTable; // Value injected by FXMLLoader

    @FXML // fx:id="assocPartPartID"
    private TableColumn<?, ?> assocPartPartID; // Value injected by FXMLLoader

    @FXML // fx:id="assocPartPartName"
    private TableColumn<?, ?> assocPartPartName; // Value injected by FXMLLoader

    @FXML // fx:id="assocPartInventoryLevel"
    private TableColumn<?, ?> assocPartInventoryLevel; // Value injected by FXMLLoader

    @FXML // fx:id="assocPartPrice"
    private TableColumn<Part, Double> assocPartPrice; // Value injected by FXMLLoader

    @FXML // fx:id="addButton"
    private Button addButton; // Value injected by FXMLLoader

    @FXML // fx:id="removeAssocPartButton"
    private Button removeAssocPartButton; // Value injected by FXMLLoader

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader



    /**
     * Fires when the Add button is pressed (for adding associated part) <br>
     * First checks if any part is selected, otherwise display an error. <br>
     * Then, check if part is already associated, and display error if so. <br>
     * If neither applies, add the Part to the Product, and update the Associated Part table <br>
     */
    @FXML
    void addButtonAction(ActionEvent event) {

        Part selectedPart = allPartDataTable.getSelectionModel().getSelectedItem();
        int selectedPartIndex = allPartDataTable.getSelectionModel().getSelectedIndex();

        if(selectedPart == null) {
            showError("Error", "No Part Selected", "Please select a part to associate" );
            return;
        }

        if((selectedProduct.getAllAssociatedParts().contains(selectedPart))) {
            showError("Error", "Part already associated", "Please select a new part to associate." );
            return;
        }

        //Add Product to Part
        selectedProduct.addAssociatedPart(allParts.get(selectedPartIndex));
        //Update Table
        assocPartDataTable.setItems(selectedProduct.getAllAssociatedParts());

    }

    /**
     * Closes the Add Part Window <br>
     * Stage stage gets the current open window, then uses the close method <br>
     */
    @FXML
    void cancelButtonAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Modifying");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure you want to quit without saving?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }

    }

    /**
     * When anything is typed into the Part search box, this fires. <br>
     * First gets text from the box <br>
     * If the entry is blank, the table is reset with all entries. <br>
     * Otherwise, the items are hidden <br>
     * Then, it determines if the user is searching for an ID or a Name <br>
     * @see Inventory lookupPart for search <br>
     * Once search is complete, sets items based on what was found <br>
     */
    @FXML
    void productSearchBoxAction(KeyEvent event) {
        //Get text
        String input = productSearchBox.getText();

        //If text is blank, repopulate table
        if (input.isBlank()) {
            allPartDataTable.setItems(Inventory.getAllParts());
            partSearchFail.setVisible(false);
            return;
        }

        //Hide items on table
        allPartDataTable.setItems(FXCollections.emptyObservableList());

        ObservableList<Part> matchedParts = FXCollections.observableArrayList();

        //If input is searching for name
        if (input.matches("[a-zA-Z]+")) {
            matchedParts = Inventory.lookupPart(input);
            if (matchedParts.isEmpty()) {
                partSearchFail.setVisible(true);
                return;
            }
        }
        else { //If input is just a number, search for ID
            int inputId = parseInt(input);
            Part singleMatchedPart = Inventory.lookupPart(inputId);
            if (matchedParts.isEmpty()) {
                partSearchFail.setVisible(true);
                return;
            }
            matchedParts.add(singleMatchedPart);
        }

        //If something was added, make sure the fail text is not shown
        partSearchFail.setVisible(false);

        //Set items
        allPartDataTable.setItems(matchedParts);

        //If only one item was found, select it
        if (matchedParts.size() == 1) {
            allPartDataTable.getSelectionModel().selectFirst();
        }
    }

    /**
     * Fires when Remove button is pressed, removing an an Associated Part from the Product <br>
     */
    @FXML
    void removeAssocPartAction(ActionEvent event) {

        if (assocPartDataTable.getSelectionModel().isEmpty()) {
            showError("Error", "No part was selected to remove", "Please select a part to remove");
            return;
        }
        Part selectedPart = assocPartDataTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Part");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to remove this Part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            selectedProduct.deleteAssociatedPart(selectedPart);
        }
    }

    /**
     * When Save is pushed, first determine of entries are valid. <br>
     * If any entry is invalid, showError() is called to display a JavaFX error box <br>
     * Otherwise, function checks if part is InHouse or Outsourced <br>
     * Then, create new instance of Product, set values, then call updateProduct <br>
     */
    @FXML
    void saveButtonAction(ActionEvent event) {

        //Initialize
        int inventory = 0;
        int min = 0;
        int max = 0;
        double price = 0;


        //Make sure that integer inputs are valid
        try { inventory = parseInt(invTextBox.getText()); } catch (NumberFormatException e) {
            showError("Inventory Error", "Inventory must be a number", "Text was entered into the Inventory box.\n Please confirm your entry, and try again");
            return;
        }
        try { price = parseDouble(priceTextBox.getText()); } catch (NumberFormatException e) {
            showError("Price Error", "Price must be a number (without preceding $)", "Text was entered into the Price box.\n Please check your entry and try again\n(Note: $ should not be entered.");
            return;
        }
        try { min = parseInt(minTextBox.getText()); } catch (NumberFormatException e) {
            showError("Min Error", "Min must be a number", "A letter was entered into the Min box.\n Please check your input and try again");
            return;
        }
        try { max = parseInt(maxTextBox.getText()); } catch (NumberFormatException e) {
            showError("Max Error", "Max must be a number", "Text was entered into the Max box.\n Please check your input and try again");
            return;
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


        //Add Part Information
        selectedProduct.setName(nameTextBox.getText());
        selectedProduct.setStock(inventory);
        selectedProduct.setPrice(price);
        selectedProduct.setMin(min);
        selectedProduct.setMax(max);
        //Add Associated Part Information

        Inventory.updateProduct(index, selectedProduct);

        //Close the window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();


    }

    /**
     * Initializes the window on Load <br>
     * Populates part table and Product information, and makes sure everything was loaded <br>
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert productSearchBox != null : "fx:id=\"productSearchBox\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert idTextBox != null : "fx:id=\"idTextBox\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert nameTextBox != null : "fx:id=\"nameTextBox\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert invTextBox != null : "fx:id=\"invTextBox\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert priceTextBox != null : "fx:id=\"priceTextBox\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert maxTextBox != null : "fx:id=\"maxTextBox\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert minTextBox != null : "fx:id=\"minTextBox\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert allPartDataTable != null : "fx:id=\"allPartDataTable\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert allPartPartID != null : "fx:id=\"allPartPartID\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert allPartPartName != null : "fx:id=\"allPartPartName\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert allPartInventoryLevel != null : "fx:id=\"allPartInventoryLevel\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert allPartPrice != null : "fx:id=\"allPartPrice\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert assocPartDataTable != null : "fx:id=\"assocPartDataTable\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert assocPartPartID != null : "fx:id=\"assocPartPartID\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert assocPartPartName != null : "fx:id=\"assocPartPartName\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert assocPartInventoryLevel != null : "fx:id=\"assocPartInventoryLevel\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert assocPartPrice != null : "fx:id=\"assocPartPrice\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert removeAssocPartButton != null : "fx:id=\"removeAssocPartButton\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'addProduct.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'addProduct.fxml'.";




        //Get all parts and narrow down to the selected index
        ObservableList<Product> products = Inventory.getAllProducts();

        index = mainFormController.getSelectedProductIndex();
        try {
            selectedProduct = products.get(index);
        } catch (IndexOutOfBoundsException e) {
            showError("Error!", "No Product was selected", "Please select a product to modify.");
            return;
        }
        //Add values from part to the
        idTextBox.setText(String.valueOf(selectedProduct.getId()));
        nameTextBox.setText(selectedProduct.getName());
        priceTextBox.setText(String.valueOf(selectedProduct.getPrice()));
        invTextBox.setText(String.valueOf(selectedProduct.getStock()));
        maxTextBox.setText(String.valueOf(selectedProduct.getMax()));
        minTextBox.setText(String.valueOf(selectedProduct.getMin()));

        //Populate All Parts Table
        allParts = Inventory.getAllParts();

        allPartPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Format Price Column to be in currency format
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        allPartPrice.setCellFactory(tc -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        allPartDataTable.setItems(Inventory.getAllParts());

        //Populate Associated Parts

        assocPartPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartPrice.setCellFactory(tc -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });

        assocPartDataTable.setItems(selectedProduct.getAllAssociatedParts());



    }



    /**
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
