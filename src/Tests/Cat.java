package Tests;

public class Cat {
    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public String getSound() {
        return sound;
    }

    public int getLegs() {
        return legs;
    }

    private String colour;
    private String name;
    private String sound;
    private int legs;


    public Cat() {
        colour = "Grey";
        name = "Smokey";
        sound = "Meow";
        legs = 4;
    }


}
