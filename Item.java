import java.util.HashMap;

public class Item {
    private String description;
    private String name;
    private int weight;
    //private Room room;
    private boolean active = false;
    private boolean canWear = false;
    private boolean canEat = false;


    public Item(String name, String description, int weight, boolean canWear, boolean canEat, boolean active) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.canWear = canWear;
        this.canEat = canEat;
        this.active = active;
    }


    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public boolean isCanWear() {
        return canWear;
    }

    public boolean isCanEat() {
        return canEat;
    }


}
