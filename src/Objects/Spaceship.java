package Objects;

import Exceptions.CriticalStatusException;

public class Spaceship {
    private int fuel = 100;
    private int integrity = 100;
    private final String name;

    public Spaceship(String name) {
        this.name = name;
    }

    public int getFuel() { return fuel; }
    public int getIntegrity() { return integrity; }
    public String getName() { return name; }

    public void reduceFuel(int amount) {
        fuel -= amount;
    }

    public void reduceIntegrity(int amount) {
        integrity -= amount;
    }

    public void addFuel(int amount) {
        fuel += amount;
    }


    public void checkStatus() throws CriticalStatusException {
        if (fuel < 10 || integrity < 20) {
            throw new CriticalStatusException("Brændstof eller skibets integritet er for lav!");
        }
        System.out.println("Status: Brændstof = " + fuel + ", Skibets integritet = " + integrity + "%");
    }
}
