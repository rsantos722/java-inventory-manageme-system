/**
 * @author Rafael Santos
 * <br>
 * InHouse Part class
 * <br>
 * @see Part (extends class)
 */
public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }


    /**
     * Setter for Machine ID <br>
     * @param machineId passes the entered Machine ID <br>
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Getter for Machine ID <br>
     * @return machineID, set by setMachineID(); <br>
     */
    public int getMachineId() {
        return machineId;
    }
}
