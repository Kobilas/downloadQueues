package mkobilas.homework.lecturedownloader;

/**
 * The DownloadScheduler class is used to make the main object that will monitor the simulation and sort through the
 *   randomized download requests that are made by the DownloadRandomizer class object. It contains two DownloadQueue
 *   objects to sort through both regular and premium download requests. It has an additional DownloadQueue object that
 *   is used to temporarily store any finished download jobs so that they may be printed to standard out at the end of
 *   each time step. It also contains an array of DownloadJob objects that is used to represent the servers throughout
 *   the program and what jobs are currently being uploaded to the requests made.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class DownloadScheduler {
    //Stores the results of each time step in the simulation and returns the result of toString() so that the 
      //simulation may be printed by the user.
    private StringBuilder returnSimulation;
    //Stores the regular DownloadJob object requests in a queue.
    private DownloadQueue regularQ;
    //Stores the premium DownloadJob object requests in a queue. These DownloadJob objects are fulfilled before the
      //DownloadScheduler looks at regular DownloadJob objects.
    private DownloadQueue premiumQ;
    //Temporarily stores any finished DownloadJob requests so that they may be added to the StringBuilder at the end of
      //each time step so that the user may know what DownloadJob requests were completed that time step.
    private DownloadQueue finishedQ;
    //Represents the current time step of the simulation.
    private int currentTime = 0;
    //Represents the last time step of the simulation.
    private int simulationEndTime;
    //The DownloadRandomizer random object decides when a new premium and new regular DownloadJob objects are requested
    private DownloadRandomizer random;
    //Stores all DownloadJob requests that are being uploaded by the selected number of servers that the user input for
      //the simulation.
    private DownloadJob[] currentJobs;
    //Represents the download speed of the DownloadJob requests and how long they will stay in the servers before they
      //are complete. Download speed is in megabytes per second.
    private int downloadSpeed;
    //Represents the number of servers the user input for there to be.
    private int serverQuantity;
    //Represents the id-1 of the next job that is added to any of the queues, as well as the total number of jobs
      //completed so far.
    private int jobNumber = 0;
    //Represents the amount of data downloaded in megabytes by regular DownloadJob requests.
    private int regularDataServed = 0;
    //Represents the amount of data downloaded in megabytes by premium DownloadJob requests.
    private int premiumDataServed = 0;
    //Represents the number of regular DownloadJob requests completed in the span of the simulation.
    private int regularJobsComplete = 0;
    //Represents the number of premium DownloadJob requests completed in the span of the simulation.
    private int premiumJobsComplete = 0;
    //Represents the total wait time of any premium DownloadJob requests so that the average might be found later.
    private int premiumWaitTime = 0;
    //Represents the total wait time of any regular DownloadJob requests so that the average might be found later.
    private int regularWaitTime = 0;
    /**
     * The constructor for this DownloadScheduler that is used as a way for the user to input the various data that is
     *   used throughout the simulation. This user-input data includes the number of servers used to complete
     *   DownloadJob requests, the download speed of the servers, the amount of time steps for the simulation to run,
     *   the probability of regular DownloadJob objects appearing, and the probability of premium DownloadJob objects
     *   appearing.
     * @param numServers
     *      Represents the max size of the array in this DownloadScheduler and the number of DownloadJob requests that
     *        may be worked at the same time.
     * @param downSpeed
     *      Represents how quickly the servers in the array in this DownloadScheduler complete DownloadJob requests.
     * @param amountTime
     *      Represents the maximum amount of time the simulation may run in terms of time steps.
     * @param regProb
     *      Represents the probability of new regular DownloadJob requests being requested in the simulation.
     * @param premProb
     *      Represents the probability of new premium DownloadJob requests being requested in the simulation.
     * @postcondition
     *      Creates a DownloadScheduler with all the user-specified data.
     */
    public DownloadScheduler(int numServers, int downSpeed, int amountTime, double regProb, double premProb){
        //All crucial object initializations are made according to the data sent to the constructor.
        serverQuantity = numServers;
        downloadSpeed = downSpeed;
        simulationEndTime = amountTime;
        currentJobs = new DownloadJob[serverQuantity];
        returnSimulation = new StringBuilder();
        regularQ = new DownloadQueue();
        premiumQ = new DownloadQueue();
        finishedQ = new DownloadQueue();
        random = new DownloadRandomizer(premProb, regProb);
    }
    /**
     * Main simulation method which is used to simulate the download process of all the randomized download requests
     *   made. Returns a String containing the result of the simulation made.
     * @return
     *      Returns the simulation neatly formatted with information presented at each time step in the download
     *        simulation.
     * @precondition
     *      There must be DownloadJobNode objects in the DownloadQueue objects before the user calls peek and dequeue
     *        methods somewhere in the method.
     * @throws EmptyQueueException
     *      Throws an exception if any of the DownloadQueue objects used in the method are empty.
     */
    public String simulate() throws EmptyQueueException{
        int tempFileSize = 0;
        returnSimulation.append("\n\n-------------------------Simulation Starting-------------------------\n");
        //Main for loop used to run the program until the simulationEndTime
        for(currentTime = 0; currentTime <= simulationEndTime; currentTime++ ){
            returnSimulation.append("Timestep " + currentTime + ":\n");
            //Checks if any random regular DownloadJob objects appear in this time step.
            tempFileSize = random.getRegular();
            if(tempFileSize != -1){
                jobNumber++ ;
                returnSimulation.append("        New Regular Job: Job#" + jobNumber + ": Size: " + 
                  tempFileSize + "MB\n");
                regularQ.enqueue(new DownloadJob(tempFileSize, currentTime, false, jobNumber));
            }
            else
                returnSimulation.append("        New Regular Job: N/A\n");
            //Checks if any random premium DownloadJob objects appear in this time step.
            tempFileSize = random.getPremium();
            if(tempFileSize != -1){
                jobNumber++ ;
                returnSimulation.append("        New Premium Job: Job#" + jobNumber + ": Size: " + 
                  tempFileSize + "MB\n");
                premiumQ.enqueue(new DownloadJob(tempFileSize, currentTime, true, jobNumber));
            }
            else
                returnSimulation.append("        New Premium Job: N/A\n");
            //Processes whether or not a new DownloadJob object should be getting worked on depending on whether or not
              //there is any currently empty servers. Loops for the number of servers in the simulation, which is
              //represented by the size of the array.
            for(int i = 0; i < serverQuantity; i++ ){
                if(!(isServerEmpty(i))){
                    //Decrements the downloadSize of any DownloadJob objects currently in servers to represent that
                      //they are being downloaded.
                    if((currentJobs[i].getRemaining() - downloadSpeed) <= 0){
                        if(currentJobs[i].getIsPremium()){
                            premiumDataServed += currentJobs[i].getDownloadSize();
                            premiumJobsComplete++ ;
                            premiumWaitTime += currentTime - currentJobs[i].getTimeRequested();
                        }
                        else{
                            regularDataServed += currentJobs[i].getDownloadSize();
                            regularJobsComplete++ ;
                            regularWaitTime += currentTime - currentJobs[i].getTimeRequested();
                        }
                        //Places any finished DownloadJob objects into the finishedQ DownloadQueue which is used to
                          //later print out any finished jobs at the end of each time step.
                        finishedQ.enqueue(currentJobs[i]);
                        currentJobs[i] = null;
                    }
                    else{
                        currentJobs[i].setRemaining(currentJobs[i].getRemaining() - downloadSpeed);
                    }
                }
                //Adds DownloadJob objects to the array if any of the spots in the array are empty (this represents
                  //the particular server that the index represents being empty).
                if(isServerEmpty(i)){
                    if(!(premiumQ.isEmpty())){
                        currentJobs[i] = premiumQ.dequeue();
                    }
                    else if(!(regularQ.isEmpty())){
                        currentJobs[i] = regularQ.dequeue();
                    }
                }
            }
            //Stores the items currently in each of the queues into the resultant String.
            returnSimulation.append("        RegularQueue:" + regularQ.toString() + "\n");
            returnSimulation.append("        PremiumQueue:" + premiumQ.toString() + "\n");
            //Stores the status of each of the servers into the resultant String.
            for(int i = 0; i < serverQuantity; i++ ){
                if(isServerEmpty(i))
                    returnSimulation.append("        Server " + (i+1) + ":idle\n");
                else
                    returnSimulation.append("        Server " + (i+1) + ":" + currentJobs[i].toDetailedString() +
                      "\n");
            }
            //Stores the finished jobs during the current time step into the resultant String.
            while(!(finishedQ.isEmpty())){
                returnSimulation.append("Job " + finishedQ.peek().getId() + " finished, " + 
                  finishedQ.peek().isPremiumString() + ". " + finishedQ.peek().getDownloadSize() + "MB served, " +
                  "Total wait time: " + (currentTime - finishedQ.dequeue().getTimeRequested()) + "\n");
            }
            returnSimulation.append("\n---------------------------------------------------------------------------\n");
        }
        //Stores the statistics of the current simulation in the resultant String.
        returnSimulation.append("Simulation Ended:\n");
        returnSimulation.append("        Total Jobs Served: " + (regularJobsComplete + premiumJobsComplete) + "\n");
        returnSimulation.append("        Total Premium Jobs Served: " + premiumJobsComplete + "\n");
        returnSimulation.append("        Total Regular Jobs Served: " + regularJobsComplete + "\n");
        returnSimulation.append("        Total Data Served: " + (regularDataServed + premiumDataServed) + "MB\n");
        returnSimulation.append("        Total Premium Data Served: " + premiumDataServed + "MB\n");
        returnSimulation.append("        Total Regular Data Served: " + regularDataServed + "MB\n");
        returnSimulation.append("        Average Premium Wait Time: " + (premiumWaitTime/premiumJobsComplete) + "\n");
        returnSimulation.append("        Average Regular Wait Time: " + (regularWaitTime/regularJobsComplete) + "\n");
        returnSimulation.append("\n--------------------Thank You For Running the Simulator--------------------\n");
        return returnSimulation.toString();
    }
    /**
     * Accessor method for the regular DownloadQueue object used in this DownloadScheduler object.
     * @return
     *      Returns a reference to the regular DownloadQueue object in this DownloadScheduler object.
     */
    public DownloadQueue getRegularQ(){
        return regularQ;
    }
    /**
     * Accessor method for the premium DownloadQueue object used in this DownloadScheduler object.
     * @return
     *      Returns a reference to the premium DownloadQueue object in this DownloadScheduler object.
     */
    public DownloadQueue getPremiumQ(){
        return premiumQ;
    }
    /**
     * Accessor method for the finished DownloadQueue object used in this DownloadScheduler object.
     * @return
     *      Returns a reference to the finished DownloadQueue object in this DownloadScheduler object.
     */
    public DownloadQueue getFinishedQ(){
        return finishedQ;
    }
    /**
     * Accessor method for the current time step that the simulation is on.
     * @return
     *      Returns the current time step that the simulation is on.
     */
    public int getCurrentTime(){
        return currentTime;
    }
    /**
     * Mutator method that sets the current time step that the simulation is on.
     * @param newCurrentTime
     *      int newCurrentTime is the value that int currentTime will be set to.
     * @postcondition
     *      int currentTime is set to int newCurrentTime.
     */
    public void setCurrentTime(int newCurrentTime){
        currentTime = newCurrentTime;
    }
    /**
     * Accessor method for the given end time for the simulation.
     * @return
     *      Returns the ending time step of the simulation.
     */
    public int getEndTime(){
        return simulationEndTime;
    }
    /**
     * Mutator method for the given end time for the simulation.
     * @param newEndTime
     *      int newEndTime is the value that int simulationEndTime will be set to.
     * @postcondition
     *      int simulationEndTime is set to int newEndTime.
     */
    public void setEndTime(int newEndTime){
        simulationEndTime = newEndTime;
    }
    /**
     * Accessor method for the randomizer used to determine when new regular and premium jobs will be created.
     * @return
     *      Returns a reference to the randomizer used throughout this DownloadScheduler object.
     */
    public DownloadRandomizer getRandom(){
        return random;
    }
    /**
     * Accessor method for the array used in this DownloadScheduler objects.
     * @return
     *      Returns a reference to the array used throughout this DownloadScheduler object.
     */
    public DownloadJob[] getCurrentJobs(){
        return currentJobs;
    }
    /**
     * Accessor method for the download speed of the servers in this DownloadScheduler objects.
     * @return
     *      Returns the download speed of this DownloadScheduler object.
     */
    public int getDownloadSpeed(){
        return downloadSpeed;
    }
    /**
     * Mutator method for the download speed of the servers in this DownloadScheduler objects.
     * @param newDownloadSpeed
     *      int newDownloadSpeed is the new download speed.
     * @postcondition
     *      int downloadSpeed is set to int newDownloadSpeed.
     */
    public void setDownloadSpeed(int newDownloadSpeed){
        downloadSpeed = newDownloadSpeed;
    }
    /**
     * Accessor method for the server quantity to be used in the simulation in this DownloadScheduler object.
     * @return
     *      Returns the download speed of this DownloadScheduler object.
     */
    public int getServerQuantity(){
        return serverQuantity;
    }
    /**
     * Mutator method for the server quantity to be used in the simulation in this DownloadScheduler object.
     * @param newNumServers
     *      int newNumServers is the number of servers that the simulation will be set to.
     * @postcondition
     *      int serverQuantity is set to int newNumServers.
     */
    public void setServerQuantity(int newNumServers){
        serverQuantity = newNumServers;
    }
    /**
     * Checks whether the given index in the array is empty, meaning that the particular server is empty.
     * @param index
     *      int index is the given index by the user that the index in the array will be checked for emptiness.
     * @return
     *      Returns true if the index in the array is true, and false otherwise.
     */
    public boolean isServerEmpty(int index){
        if(currentJobs[index] ==  null)
            return true;
        return false;
    }
}
