/**
 * @author Rafael Santos <br>
 * For WGU C482 <br>
 *
 * Outsourced Part class <br>
 * @see Part (extends class) <br>
 */
public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }


    /**
     * Setter for Company Name <br>
     * @param companyName The entered Company Name String to save <br>
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter for Company Name <br>
     * @return String containing the Company Name of the Part <br>
     */
    public String getCompanyName() {
        return companyName;
    }
}

