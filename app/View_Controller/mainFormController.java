import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

/**
 * @author Rafael Santos <br>
 * For WGU C482 <br>
 * Controller for mainForm.fxml <br>
 * Contains main screen, including Part and Product Tables <br>
 */
public class mainFormController {


    //Used to sent the highlighted part to modifyPartController
    static int selectedPartIndex;


    @FXML
    private Text partSearchFail;

    @FXML
    private Text productSearchFail;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="partTable"
    private TableView<Part> partTable; // Value injected by FXMLLoader

    @FXML // fx:id="partIdColumn"
    private TableColumn<?, ?> partIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="partNameColumn"
    private TableColumn<?, ?> partNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="partInventoryLevelColumn"
    private TableColumn<?, ?> partInventoryLevelColumn; // Value injected by FXMLLoader

    @FXML // fx:id="partCostColumn"
    private TableColumn<Part, Double> partCostColumn; // Value injected by FXMLLoader

    @FXML // fx:id="partSearch"
    private TextField partSearch; // Value injected by FXMLLoader

    @FXML // fx:id="deletePartButton"
    private Button deletePartButton; // Value injected by FXMLLoader

    @FXML // fx:id="modifyPartButton"
    private Button modifyPartButton; // Value injected by FXMLLoader

    @FXML // fx:id="addPartButton"
    private Button addPartButton; // Value injected by FXMLLoader

    @FXML // fx:id="productTable"
    private TableView<Product> productTable; // Value injected by FXMLLoader

    @FXML // fx:id="productIdColumn"
    private TableColumn<?, ?> productIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="productNameColumn"
    private TableColumn<?, ?> productNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="productInventoryColumn"
    private TableColumn<?, ?> productInventoryColumn; // Value injected by FXMLLoader

    @FXML // fx:id="productCostColumb"
    private TableColumn<Product,Double> productCostColumb; // Value injected by FXMLLoader

    @FXML // fx:id="productSearch"
    private TextField productSearch; // Value injected by FXMLLoader

    @FXML // fx:id="deleteProductButton"
    private Button deleteProductButton; // Value injected by FXMLLoader

    @FXML // fx:id="modifyProductButton"
    private Button modifyProductButton; // Value injected by FXMLLoader

    @FXML // fx:id="addProductButton"
    private Button addProductButton; // Value injected by FXMLLoader

    @FXML // fx:id="mainExitButton"
    private Button mainExitButton; // Value injected by FXMLLoader

    /**
     * Fires when "Add" button is pressed, <br>
     * calls windowLoader to open the Add Part screen. <br>
     */
    @FXML
    void addPartButtonAction(ActionEvent event) {

        try {
            windowLoader("addPart.fxml", "Add Part");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fires when Add Product button is pressed. <br>
     * uses windowLoader to load Add Product Screen <br>
     */
    @FXML
    void addProductButtonAction(ActionEvent event) {
        try {
            windowLoader("addProduct.fxml", "Add Product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes selected part from Part Table <br>
     * Removes from allParts <br>
     * @see Inventory <br>
     */
    @FXML
    void deletePartButtonAction(ActionEvent event) {

        //Get Selected Item
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            showError("Error", "No Part Selected", "Please select a part to delete.");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to delete this Part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }

        //Updates table
        if (partSearch.getText().isEmpty()) {
            updateParts();
        } else {
            partSearchAction();
        }
    }


    /**
     * Deletes selected part from table and allProducts <br>
     */
    @FXML
    void deleteProductButtonAction(ActionEvent event) {

        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showError("Error", "No Product Selected", "Please select a product to delete.");
            return;
        }

        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            showError("Error!", "Product currently associated", "Product cannot be deleted if associated with a part");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Warning!");
        alert.setContentText("Are you sure you want to delete this Part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }
        updateProducts();
        if (!(productSearch.getText().isEmpty())) {
            productSearchAction();
        }

    }

    /**
     * Fires when Exit button is pressed <br>
     * Leaves the program <br>
     */
    @FXML
    void mainExitButtonAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setHeaderText("Warning");
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) addPartButton.getScene().getWindow();
            stage.close();
        }
    }



    /**
     * modifyPartButtonAction <br>
     * When Modify part is pressed, windowLoader is called, <br>
     * loading modifyPart.fxml and using window name "Modify Part" <br>
     * @exception IOException if window is not loaded <br>
     */
    @FXML
    void modifyPartButtonAction(ActionEvent event) {



        selectedPartIndex = partTable.getSelectionModel().getSelectedIndex();

        if(selectedPartIndex == -1) {
            showError("Error", "No Part Selected", "Please select a part to modify" );
            return;
        }

        try {
            windowLoader("modifyPart.fxml", "Modify Part");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Used to sent the highlighted part to modifyPartController
    static int selectedProductIndex;

    /**
     * Gets the index of the selected product <br>
     * @return Integer with Product index <br>
     */
    static int getSelectedProductIndex() {
        return selectedProductIndex;
    }


    /**
     *
     * When Modify Product is pressed, windowLoader is called, <br>
     * loading modifyPart.fxml and using window name "Modify Product" <br>
     * @exception IOException if window is not loaded <br>
     */
    @FXML
    void modifyProductButtonAction(ActionEvent event) {

        selectedProductIndex = productTable.getSelectionModel().getSelectedIndex();

        if(selectedProductIndex == -1) {
            showError("Error", "No Product Selected", "Please select a product to modify" );

        } else {
            try {
                windowLoader("modifyProduct.fxml", "Modify Product");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
     *      <br>
     *      <br>
     * An issue I ran into was finding the best way to determine of the search box was a number (ID) or text(Name) <br>
     * After many iterations, I found that using regex to find if anything in the input was a letter was the best option. <br>
     * If no letters are found, the input is parsed into an Integer and passed into the lookup function <br>
     */
    @FXML
    void partSearchAction() {

        String input = partSearch.getText();
        if (input.isBlank()) {
            updateParts();
            partSearchFail.setVisible(false);
            return;
        }
        //Hide items on table
        partTable.setItems(FXCollections.emptyObservableList());

        //Get Search
        //String input = partSearch.getText();

        ObservableList<Part> matchedParts = FXCollections.observableArrayList();

        //If input is searching for name
        /**
          An issue I ran into was finding the best way to determine of the search box was a number (ID) or text(Name) <br>
          After many iterations, I found that using regex to find if anything in the input was a letter was the best option. <br>
          If no letters are found, the input is parsed into an Integer and passed into the lookup function <br>
         **/
        //If a string
        if (input.matches("[a-zA-Z]+")) {
            //If nothing was added
            matchedParts = Inventory.lookupPart(input);
            if (matchedParts.isEmpty()) {
                partSearchFail.setVisible(true);
                return;
            }
        }
        //If not a string
        else {
            int inputId = parseInt(input);
            Part singleMatchedPart = Inventory.lookupPart(inputId);

            if(matchedParts.isEmpty()) {
                //Need to break now or else will not be null
                partSearchFail.setVisible(true);
                return;
            }
            matchedParts.add(singleMatchedPart);
        }

        //If something was added, make sure the fail text is not shown

        partSearchFail.setVisible(false);

        //Set the items
        partTable.setItems(matchedParts);
        //If only one item was found, select it
        if (matchedParts.size() == 1) {
            partTable.getSelectionModel().selectFirst();
        }

    }


    /**
     * When anything is typed into the Product search box, this fires. <br>
     * First gets text from the box <br>
     * If the entry is blank, the table is reset with all entries. <br>
     * Otherwise, the items are hidden <br>
     * Then, it determines if the user is searching for an ID or a Name <br>
     * @see Inventory lookupProduct for search <br>
     * Once search is complete, sets items based on what was found <br>
     */
    @FXML
    void productSearchAction() {

        String input = productSearch.getText();
        if (input.isBlank()) {
            updateProducts();
            productSearchFail.setVisible(false);
            return;
        }
        //Hide items on table
        productTable.setItems(FXCollections.emptyObservableList());

        ObservableList<Product> matchedProducts = FXCollections.observableArrayList();

        //If string
        if (input.matches("[a-zA-Z]+")) {
            matchedProducts = Inventory.lookupProduct(input);
            if (matchedProducts.isEmpty()) {
                productSearchFail.setVisible(true);
                return;
            }
        }
        //If not string
        else {
            int inputId = parseInt(input);
            Product singleMatchedPart = Inventory.lookupProduct(inputId);
            if (matchedProducts.isEmpty()) {
                productSearchFail.setVisible(true);
                return;
            }
            matchedProducts.add(singleMatchedPart);
        }

        //If something was added, make sure the fail text is not shown

        productSearchFail.setVisible(false);

        productTable.setItems(matchedProducts);
        //If only one result, select it
        if (matchedProducts.size() == 1) {
            productTable.getSelectionModel().selectFirst();
        }
    }

    /**
     * Window Loader
     * Pass file name and window name and load respective new window <br>
     * @param fxmlFile The name of the FXML file to load inside View_Controller <br>
     * @param windowName Title of the window to be displayed on the new window <br>
     * @exception IOException if there is an issue loading window <br>
     */
    private void windowLoader(String fxmlFile, String windowName) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        //Open an new window(with exception catch)
        try {
            fxmlLoader.setLocation(getClass().getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(windowName);
            stage.setScene(scene);
            stage.show();
        } catch (IOException windowError) {
            windowError.printStackTrace();
        }
    }


    /**
     * Initializes the main screen <br>
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert partTable != null : "fx:id=\"partTable\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert partIdColumn != null : "fx:id=\"partIdColumn\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert partNameColumn != null : "fx:id=\"partNameColumn\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert partInventoryLevelColumn != null : "fx:id=\"partInventoryLevelColumn\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert partCostColumn != null : "fx:id=\"partCostColumn\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert partSearch != null : "fx:id=\"partSearch\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert deletePartButton != null : "fx:id=\"deletePartButton\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert modifyPartButton != null : "fx:id=\"modifyPartButton\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert addPartButton != null : "fx:id=\"addPartButton\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert productTable != null : "fx:id=\"productTable\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert productIdColumn != null : "fx:id=\"productIdColumn\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert productNameColumn != null : "fx:id=\"productNameColumn\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert productInventoryColumn != null : "fx:id=\"productInventoryColumn\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert productCostColumb != null : "fx:id=\"productCostColumb\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert productSearch != null : "fx:id=\"productSearch\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert deleteProductButton != null : "fx:id=\"deleteProductButton\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert modifyProductButton != null : "fx:id=\"modifyProductButton\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert addProductButton != null : "fx:id=\"addProductButton\" was not injected: check your FXML file 'mainForm.fxml'.";
        assert mainExitButton != null : "fx:id=\"mainExitButton\" was not injected: check your FXML file 'mainForm.fxml'.";


        //Setup Part Table
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Format Price Column to be in currency format
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        partCostColumn.setCellFactory(tc -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (price == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        updateParts();

        //Setup Product Table
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumb.setCellValueFactory(new PropertyValueFactory<>("price"));
        productCostColumb.setCellFactory(tc -> new TableCell<Product, Double>() {
            //Format Price Column to be in currency format
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (price == null) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        partTable.setAccessibleText("No parts found");
        productTable.setAccessibleText("No products found");
        updateProducts();

    }

    /**
     * Updates the Part table <br>
     */
    private void updateParts() {

        partTable.setItems(Inventory.getAllParts());

    }

    /**
     * Updates the Products table <br>
     */
    private void updateProducts() {
        productTable.setItems(Inventory.getAllProducts());
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

    /**
     * Gets the index of the selected Part <br>
     * @return Integer index of the selected Part <br>
     */
    static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

}
