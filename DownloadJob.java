package mkobilas.homework.lecturedownloader;
/**
 * The DownloadJob class is used to create objects of type DownloadJob which can help to store the various types of
 *   data regarding the download jobs the simulation may encounter when running the program. The class contains 
 *   necessary accessor and mutator methods. It also contains several methods to return a String of all the information
 *   contained within this DownloadJob object.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class DownloadJob {
    //How large the download is
    private int downloadSize;
    //How large the remainder of the download is
    private int downloadSizeRemaining;
    //The time step when the download was requested
    private int timeRequested;
    //True if this DownloadJob is a premium job
    private boolean isPremium;
    //Id showing when the job was requested in reference to other jobs.
    private int id = 1;
    /**
     * Main constructor for creating DownloadJob objects. Takes input for the download size of the job, when it was
     *   requested, whether or not the job is premium, and the id of the job.
     * @param downSize
     *      int downSize is the job's size in megabytes.
     * @param timeRequest
     *      int timeRequest is the time step in which the job was officially requested by the simulation.
     * @param premium
     *      boolean premium is true if the job being created is premium, and false if it is a regular job.
     * @param setId
     *      int setId is the job's position at the time of creation relative to other DownloadJob objects.
     * @postcondition
     *      Creates a DownloadJob object with these set parameters that may be accessed using accessor and mutator
     *        methods.
     */
    public DownloadJob(int downSize, int timeRequest, boolean premium, int setId){
        downloadSize = downSize;
        downloadSizeRemaining = downSize;
        timeRequested = timeRequest;
        isPremium = premium;
        id = setId;
    }
    /**
     * Accessor method for the download size of this DownloadJob object.
     * @return
     *   Returns this DownloadJob object's int downloadSize in megabytes.
     */
    public int getDownloadSize(){
        return downloadSize;
    }
    /**
     * Mutator method for the download size of this DownloadJob object. This method is not typically used since the
     *   downloadSizeRemaining variable exists to change to notify the user as to how much of the file is remaining to
     *   download.
     * @param newDownSize
     *      int newDownSize is the value that int downloadSize will be set to.
     * @postcondition
     *      int downloadSize is now equal to int newDownSize.
     */
    public void setDownloadSize(int newDownSize){
        downloadSize = newDownSize;
    }
    /**
     * Accessor method for the remaining download size of this DownloadJob object.
     * @return
     *      Returns this DownloadJob object's int downloadSizeRemaining in megabytes.
     */
    public int getRemaining(){
        return downloadSizeRemaining;
    }
    /**
     * Mutator method for the remaining download size of this DownloadJob object, Used to change how much of the file
     *   has already been downloaded.
     * @param newDownRemaining
     *      int newDownRemaining is the value that int downloadSize will be set to.
     * @postcondition
     *      int downloadSize is set to int newDownRemaining.
     */
    public void setRemaining(int newDownRemaining){
        downloadSizeRemaining = newDownRemaining;
    }
    /**
     * Accessor method for when the download job was requested. Used to later find the average time waited by the user
     *   for the download job to finish after it was requested.
     * @return
     *      Returns this DownloadJob object's int timeRequested in terms of time steps in the simulation.
     */
    public int getTimeRequested(){
        return timeRequested;
    }
    /**
     * Mutator method for when the download job was requested. Is not usually used.
     * @param newTimeRequest
     *      int newTimeRequest is the value to set int timeRequested to.
     * @postcondition
     *      int timeRequested is set to int newTimeRequest.
     */
    public void setTimeRequested(int newTimeRequest){
        timeRequested = newTimeRequest;
    }
    /**
     * Accessor method to find whether or not this DownloadJob object is in the premium queue or not.
     * @return
     *      Returns true if this DownloadJob object is premium, and false if it is not.
     */
    public boolean getIsPremium(){
        return isPremium;
    }
    /**
     * Accessor method to find whether or not this DownloadJob object is in the premium queue or not, except that it
     *   will instead return a String specifying whether or not it is.
     * @return
     *      Returns Premium if this DownloadJob object is premium, and Regular if it is not premium.
     */
    public String isPremiumString(){
        if(getIsPremium())
            return "Premium";
        else
            return "Regular";
    }
    /**
     * Mutator method to change whether or not this DownloadJob object is premium.
     * @param newPremiumBoolean
     *      boolean newPremiumBoolean is what boolean isPremium will be set to.
     * @postcondition
     *      boolean isPremium is set to boolean newPremiumBoolean.
     */
    public void setIsPremium(boolean newPremiumBoolean){
        isPremium = newPremiumBoolean;
    }
    /**
     * Accessor method that returns this DownloadJob object's id.
     * @return
     *      Returns this DownloadJob object's int id.
     */
    public int getId(){
        return id;
    }
    /**
     * Mutator method that changes this DownloadJob object's id.
     * @param newId
     *      int newId is the value that int id will be set to.
     */
    public void setId(int newId){
        id = newId;
    }
    /**
     * Copies the data of another DownloadJob object to this one, effectively making this DownloadJob a copy of the
     *   other one.
     * @param other
     *      DownloadJob other is the DownloadJob object from which the data will be copied.
     * @postcondition
     *      This DownloadJob object's data will be set to the other DownloadJob's data, making them identical.
     */
    public void copyFrom(DownloadJob other){
        downloadSize = other.getDownloadSize();
        downloadSizeRemaining = other.getRemaining();
        timeRequested = other.getTimeRequested();
        isPremium = other.getIsPremium();
        id = other.getId();
    }
    /**
     * Returns a String with a small amount of formatted information so that it may be printed to standard out when the
     *   program is run and the information in the queues must be represented in a compact manner.
     * @return
     *      Returns a String conciseString with the id and downloadSize of this DownloadJob object.
     */
    public String toConciseString(){
        String conciseString;
        conciseString = "[#" + id + ":" + downloadSize + "MB]";
        return conciseString;
    }
    /**
     * Returns a String with most of this DownloadJob object's information in a formatted manner so that it may be
     *   printed to standard out when the program is run. Used to represent which DownloadJob is currently being 
     *   uploaded by a particular server.
     * @return
     *      Returns a String detailedString containing this DownloadJob object's id, downloadSize, 
     *        downloadSizeRemaining, timeRequested, and whether or not it isPremium.
     */
    public String toDetailedString(){
        String detailedString;
        detailedString = "[#" + id + ": " + downloadSize + "MB total, " + downloadSizeRemaining + 
          "MB remaining, Request Time: " + timeRequested + ", " + isPremiumString() + "]";
        return detailedString;
    }
}
