

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Rafael Santos <br>
 * For WGU C482 <br>
 *
 * Product class <br>
 * Contains Getters, Setters, and Contructor for Product <br>
 */
public class Product {

    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product() {

    }

    /**
     * Setter for Product ID <br>
     * @param id Integer storing Product ID <br>
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for Part Name <br>
     * @param name String value with Product name <br>
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     * Setter for Part Price <br>
     * @param price Double value for Product price <br>
     */
    public void setPrice(double price) {
        this.price = price;

    }

    /**
     * Setter for Product Stock/Inventory <br>
     * @param stock Integer containing Product stock <br>
     */
    public void setStock(int stock) {
        this.stock = stock;

    }

    /**
     * Setter for Product minimum <br>
     * @param min Integer containing Product minimum <br>
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter for Product maximum <br>
     * @param max Integer containing Product maximum <br>
     */
    public void setMax(int max) {
        this.max = max;

    }

    /**
     * Getter for Product ID <br>
     * @return Integer for Product ID <br>
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for Product name <br>
     * @return String containing Part name <br>
     */
    public String getName() {
        return name;

    }

    /**
     * Getter for Product price <br>
     * @return Double containing Product price <br>
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter for Product stock/inventory <br>
     * @return Integer with Product stock <br>
     */
    public int getStock() {
        return stock;
    }

    /**
     * Getter for Product minimum <br>
     * @return Integer with Product minimum <br>
     */
    public int getMin() {
        return min;
    }

    /**
     * Getter for Product maximum <br>
     * @return Integer with product maximum <br>
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a designated Associated Part to the Product <br>
     * @param part The Part to associate with the Product <br>
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Deletes a selected Associated Part from he Product <br>
     * @param selectedAssociatedPart The part to delete from the Product <br>
     * @return true if deletion is successful <br>
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        this.associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Get all of the associated Parts of the product <br>
     * @return an ObservableList containing all Parts associated with the Product <br>
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }


}
