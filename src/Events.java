import Exceptions.CriticalStatusException;
import Exceptions.InvalidTradeException;
import Objects.Spaceship;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Events {
    private static final Scanner scanner = new Scanner(System.in);
    private static String playerName;
    private static Spaceship ship;

    public static void eventRun() {
        startMission();
        System.out.println();
        try {
            eventStorm();
            eventAlienTrade();
            eventEngineFailure();
            eventVictory();
        } catch (CriticalStatusException e) {
            System.out.println("❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌");
            System.out.println("Kritisk fejl: " + e.getMessage());
            System.out.println("Dit skib kunne ikke fortsætte rejsen.");
            System.out.println("Bedre held næste gang!");
            System.out.println("❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌❌");
        }
    }

    public static void startMission() throws NumberFormatException {
        try {
            System.out.println("Velkommen til Rumeventyr!");
            System.out.print("Indtast dit navn: ");
            playerName = scanner.nextLine();
            System.out.print("Navngiv dit skib: ");
            ship = new Spaceship(scanner.nextLine());
            System.out.println("Status: Brændstof = " + ship.getFuel() + ", Skibets integritet = " + ship.getIntegrity() + "%");
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("Fejl: Ugyldigt navn. Prøv igen.");
            System.out.println();
            startMission();
        }
    }

    public static void eventStorm() throws CriticalStatusException {
        System.out.println("Event 1: En rumstorm nærmer sig. Vil du:");
        System.out.println("1. Flyve igennem stormen");
        System.out.println("2. Tage en omvej (koster 35 brændstof)");
        try {
            int choice = scanner.nextInt();
            if (choice == 1) {
                int damage = (int) (Math.random() * 100);
                ship.reduceIntegrity(damage);
                System.out.println("Dit skib tog " + damage + "% skade!");
                System.out.println();
            } else if (choice == 2) {
                ship.reduceFuel(35);
                System.out.println("Du tog en omvej. Brændstof: " + ship.getFuel());
                System.out.println();
            }
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Fejl: Ugyldigt valg. Prøv igen.");
            System.out.println();
            scanner.next();

        }
        ship.checkStatus();
        System.out.println();
    }

    public static void eventAlienTrade() throws CriticalStatusException {
        boolean success = false;
        System.out.println("Event 2: Et mystisk rumvæsen tilbyder brændstof mod dine reservedele.");
        System.out.print("Indtast hvor mange reservedele du vil handle: ");
        while (!success) {
            try {
                int trade = scanner.nextInt();
                if (trade < 0) {
                    throw new InvalidTradeException("Handler kræver en positiv værdi.");
                }
                double risk = Math.random();

                if (risk > 0.8) {
                    int lostParts = (int) (trade * 0.3);
                    int damage = (int) (Math.random() * 20);
                    ship.reduceIntegrity(damage);
                    ship.addFuel(trade);
                    System.out.println("Handel gennemført, men det gik ikke som planlagt, rumvæsenet snød dig:");
                    System.out.println("Du mistede " + lostParts + " reservedele og dit skib tog " + damage + "% skade.");
                    System.out.println();
                } else if (risk > 0.5) {
                    int lostParts = (int) (trade * 0.2);
                    ship.reduceIntegrity(5);
                    ship.addFuel(trade);
                    System.out.println("Handel gennemført, men du mistede " + lostParts + " reservedele og dit skib tog lidt skade.");
                    System.out.println();
                } else {
                    ship.addFuel(trade);
                    System.out.println("Handel gennemført med rumvæsem! Du har nu " + ship.getFuel() + " brændstof.");
                    System.out.println();
                }
                success = true;
            } catch (InvalidTradeException e) {
                System.out.println("Fejl: " + e.getMessage());
                System.out.println();
            } catch (InputMismatchException e) {
                System.out.println("Fejl: Indtast venligst et tal.");
                System.out.println();
                scanner.next();
            }
        }
        ship.checkStatus();
        System.out.println();
    }
    public static void eventEngineFailure() throws CriticalStatusException {
        System.out.println("Event 3: Din motor fejler!");
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Vælg en handling for at forsøge at reparere motoren:");
            System.out.println("1. Brug værktøj til at reparere motoren.");
            System.out.println("2. Forsøg at genstarte motoren.");
            System.out.println("3. Kontakt en specialist.");
            System.out.println();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleRepair();
                    System.out.println();
                    break;
                case 2:
                    handleRestart();
                    System.out.println();
                    break;
                case 3:
                    contactSpecialist();
                    System.out.println();
                    break;
                default:
                    System.out.println("Ugyldigt valg.");
                    throw new CriticalStatusException("Motoren kunne ikke repareres.");
            }

        } catch (Exception e) {
            System.out.println("Fejl under reparationen: " + e.getMessage());
            System.out.println("Forsøg mislykkedes. Dit spil er slut.");
            throw new CriticalStatusException("Motoren kunne ikke repareres.");
        } finally {
            System.out.println("Forsøger at genstarte...");
            System.out.println();
        }
    }

    public static void eventVictory() {
        System.out.println("✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅");
        System.out.println("Tillykke! Du har vundet spillet " + playerName + "! Med skibet " + ship.getName() + "!");
        System.out.println("Du havde " + ship.getFuel() + " brændstof og " + ship.getIntegrity() + "% skade på dit skib ved slut");
        System.out.println("✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅");
    }
    private static void handleRepair() throws Exception {
        if (Math.random() > 0.7) {
            System.out.println("Motoren blev repareret succesfuldt!");
            System.out.println();
        } else {
            throw new Exception("Reparation mislykkedes.");
        }
    }
    private static void handleRestart() throws Exception {
        if (Math.random() > 0.5) {
            System.out.println("Motoren blev genstartet succesfuldt!");
            System.out.println();
        } else {
            throw new Exception("Genstart mislykkedes.");
        }
    }
    private static void contactSpecialist() throws Exception {
        if (Math.random() > 0.8) {
            System.out.println("Specialisten har repareret motoren!");
            System.out.println();
        } else {
            throw new Exception("Specialistens hjælp mislykkedes.");
        }
    }
}

