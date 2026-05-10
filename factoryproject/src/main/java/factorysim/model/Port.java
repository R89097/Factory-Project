package factorysim.model;

/**
 * Machine input or output port
 */
public class Port {

    private String itemType;
    private int capacity;
    private int currentItems;
    private String beltName;

    public Port(String itemType, int capacity, String beltName) {
        this.itemType = itemType;
        this.capacity = capacity;
        this.beltName = beltName;
        this.currentItems = 0;
    }

    public String getItemType() {
        return itemType;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentItems() {
        return currentItems;
    }

    public String getBeltName() {
        return beltName;
    }

    public boolean isFull() {
        return currentItems >= capacity;
    }

    public boolean isEmpty() {
        return currentItems == 0;
    }

    public void addItem() {
        if (currentItems < capacity) {
            currentItems++;
        }
    }

    public void removeItem() {
        if (currentItems > 0) {
            currentItems--;
        }
    }
}