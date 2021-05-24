
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Rafael Santos <br>
 * For WGU C482 <br>
 *
 * Handles Part and Product Data <br>
 * Used to populate and show Part and Products in application <br>
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to allParts <br>
     * @param newPart The part passed in to add <br>
     */
    public static void addPart(Part newPart) {
        allParts.addAll(newPart);
    }

    /**
     * Adds product to allProducts <br>
     * @param newProduct Product entered to add to allProducts <br>
     */
     public static void addProduct(Product newProduct) {
        allProducts.addAll(newProduct);
    }

    /**
     * Searches for desired part in allParts by ID <br>
     * @param partId the Part ID Integer to search for <br>
     * @return The part matching the integer. If no part is found, null is returned. <br>
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for desired Product in allProducts by ID <br>
     * @param productId The Product ID (Integer) to search for <br>
     * @return The matching Product. If no match is found, return null <br>
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Searches for desired Part in allParts by Name <br>
     * @param partName The Part (String) name to look for <br>
     * @return ObservableList containing all matching Parts <br>
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchedParts = FXCollections.observableArrayList();
        //Go through every part in allParts
        for (Part part : allParts) {
            if(part.getName().toLowerCase().contains(partName.toLowerCase())) {
                matchedParts.add(part);
            }
        }
        return matchedParts;
    }

    /**
     * Searches for desired Product in allProducts by Name <br>
     * @param productName Product (String) name to look for <br>
     * @return ObservableList containing all matching Products <br>
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchedProducts = FXCollections.observableArrayList();
        //Go through every Product in allProducts
        for (Product product : allProducts) {
            if(product.getName().toLowerCase().contains(productName.toLowerCase())) {
                matchedProducts.add(product);
            }
        }
        return matchedProducts;
    }

    /**
     * Updates a selected part with information provided on Modify screen <br>
     * @param index The index of the Part (in allParts) to update <br>
     * @param selectedPart The updated data to apply to selected part <br>
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a selected Product with information provided on Modify screen <br>
     * @param index The index of the Product (in allProducts) to update <br>
     * @param selectedProduct The updated data to apply to selected Product <br>
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes selected part in allParts <br>
     * @param selectedPart The part to delete, selected from parts list <br>
     * @return true when part is deleted <br>
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * Deletes selected Product in allProducts <br>
     * @param selectedProduct The Product to delete, selected from products list <br>
     * @return true when product is deleted <br>
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * Retrieves all parts from allParts <br>
     * @return an ObservableList matching allParts <br>
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;

    }

    /**
     * Retrieves all products from allProducts <br>
     * @return an ObservableList matching allProducts <br>
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
