package mkobilas.homework.lecturedownloader;

import java.util.Scanner;

/**
 * DownloadManager is the class that the program is run from in order to print out the simulation created to the 
 *   specifications of the user.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class DownloadManager {
    //Used to read input from the user
    private static Scanner keyboard;
    //Used to run the simulation
    private static DownloadScheduler scheduler;
    public static void main(String[] args) {
        int serverCount;
        int downloadSpeed;
        int timeLength;
        double premiumProbability;
        double regularProbability;
        keyboard = new Scanner(System.in);
        System.out.println("Hello and welcome to the Download Scheduler.");
        System.out.print("\nPlease enter the number of servers: ");
        while(true){
            try{
                //The number of servers to be used in the simulation.
                serverCount = Integer.parseInt(keyboard.nextLine());
                if(serverCount <= 0)
                    System.out.print("\nValue entered must be greater than 0.\nPlease enter the number of servers: ");
                else
                    break;
            }
            catch(NumberFormatException err){
                System.out.print("\nValue entered must be an integer.\nPlease enter the number of servers: ");
            }
        }
        System.out.print("\nPlease enter a download speed: ");
        while(true){
            try{
                //The download speed of the servers in the simulation.
                downloadSpeed = Integer.parseInt(keyboard.nextLine());
                if(downloadSpeed <= 0)
                    System.out.print("\nValue entered must be greater than 0.\nPlease enter the download speed: ");
                else
                    break;
            }
            catch(NumberFormatException err){
                System.out.print("\nValue entered must be an integer.\nPLease enter the download speed: ");
            }
        }
        System.out.print("\nPlease enter the length of time: ");
        while(true){
            try{
                //The number of time steps to run the simulation for.
                timeLength = Integer.parseInt(keyboard.nextLine());
                if(timeLength <= 0)
                    System.out.print("\nValue entered must be greater than 0.\nPlease enter the length of time: ");
                else
                    break;
            }
            catch(NumberFormatException err){
                System.out.print("\nValue entered must be an integer.\nPlease enter the length of time: ");
            }
        }
        System.out.print("\nPlease enter the probability of new premium jobs appearing per timestep: ");
        while(true){
            try{
                //The probability of premium jobs appearing each time step.
                premiumProbability = Double.parseDouble(keyboard.nextLine());
                if((premiumProbability >= 1) ||  (premiumProbability <= 0))
                    System.out.print("\nValue entered must be between 0 and 1 exclusive.\nPlease enter the " +
                      "probability of new premium jobs appearing per timestep: ");
                else
                    break;
            }
            catch(NumberFormatException err){
                System.out.print("\nValue entered must be a number.\nPlease enter the probability of new premium " +
                  "jobs appearing per timestep: ");
            }
        }
        System.out.print("\nPlease enter the probability of new regular jobs appearing per timestep: ");
        while(true){
            try{
                //The probability of regular jobs appearing each time step.
                regularProbability = Double.parseDouble(keyboard.nextLine());
                if((regularProbability >= 1) ||  (regularProbability <= 0))
                    System.out.print("\nValue entered must be between 0 and 1 exclusive.\nPlease enter the " +
                      "probability of new regular jobs appearing per timestep: ");
                else
                    break;
            }
            catch(NumberFormatException err){
                System.out.print("\nValue entered must be a number.\nPlease enter the probability of new regular " +
                  "jobs appearing per timestep: ");
            }
        }
        //Initializes the DownloadScheduler
        scheduler = new DownloadScheduler(serverCount, downloadSpeed, timeLength, regularProbability,
          premiumProbability);
        try{
            System.out.println(scheduler.simulate());
        }
        catch(EmptyQueueException err){
            System.out.println("FATAL ERROR: Queue was empty at some point in the program.");
        }
        //Exits the program gracefully.
        keyboard.close();
        System.exit(0);
    }

}
