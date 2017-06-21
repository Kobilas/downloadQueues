package mkobilas.homework.lecturedownloader;
/**
 * The DownloadQueue class is a singly-linked list acting as a queue. This linked list stores DownloadJobNode objects
 *   so that it might be used to keep the proper order in which the simulation requests download jobs. Two 
 *   DownloadQueue objects are used in conjunction in order to make a complete queue which sorts premium DownloadJob
 *   objects in front of regular DownloadJob objects.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class DownloadQueue {
    //Reference to the first DownloadJobNode object in this DownloadQueue
    private DownloadJobNode front;
    //Reference to the last DownloadJobNode object in this DownloadQueue
    private DownloadJobNode rear;
    /**
     * Constructor for DownloadQueue objects that initializes the front and rear references to null.
     * @postcondition
     *      Creates an empty singly-linked list with methods to allow use as a queue.
     */
    public DownloadQueue(){
        front = null;
        rear = null;
    }
    /**
     * Enqueue method for this DownloadQueue object which adds a DownloadJobNode object to the end of the linked list.
     * @param d
     *      DownloadJob d is the new DownloadJob object that is to be wrapped in a DownloadJobNode object and then
     *        added to the end of this DownloadQueue object.
     * @postcondition
     *      If this DownloadQueue was empty before calling enqueue, then the new DownloadJobNode created is also
     *        initialized as the front as well as the rear. Otherwise, the enqueue call will result in the size of the
     *        DownloadQueue incrementing by one with the new DownloadJobNode object being added to the end of the
     *        DownloadQueue object. This new DownloadJobNode object is also set to the rear reference.
     */
    public void enqueue(DownloadJob d){
        DownloadJobNode newNode = new DownloadJobNode(d);
        if(isEmpty()){
            front = newNode;
            rear = front;
        }
        else{
            rear.setLink(newNode);
            rear = newNode;
        }
    }
    /**
     * Dequeue method for this DownloadQueue object which removes the first DownloadJobNode object in the linked list 
     *   and returns a reference to its data.
     * @return
     *      Returns DownloadJob result, which was the data that was wrapped in the DownloadJobNode object that was
     *        removed from the list. The second object in the linked list is set to the front reference.
     * @precondition
     *      This DownloadQueue object must have at least one DownloadJobNode object in it before a call to this method
     *        is made.
     * @postcondition
     *      The size of this DownloadQueue is decremented and the front reference is set to the next object in the
     *        linked list.
     * @throws EmptyQueueException
     *      Throws an exception if the DownloadQueue object was empty and that nothing can be removed.
     */
    public DownloadJob dequeue() throws EmptyQueueException{
        DownloadJob result;
        if(isEmpty())
            throw new EmptyQueueException("Cannot dequeue because this DownloadQueue is empty.");
        result = front.getData();
        front = front.getLink();
        if(isEmpty())
            rear = null;
        return result;
    }
    /**
     * Peek method for this DownloadQueue object which returns the reference to the data wrapped in the first
     *   DownloadJobNode object in this DownloadQueue without removing it from the list.
     * @return
     *      Returns the result of getData() from the front reference to this DownloadQueue, which is a reference to the
     *        first DownloadJob object in this DownloadQueue.
     * @precondition
     *      This DownloadQueue object must have at least one DownloadJobNode object in it before a call to this method
     *        is made.
     * @throws EmptyQueueException
     *      Throws an exception if the DownloadQueue is empty and there is no DownloadJobNode to peek at.
     */
    public DownloadJob peek() throws EmptyQueueException{
        if(isEmpty())
            throw new EmptyQueueException("Cannot peek because this DownloadQueue is empty.");
        return front.getData();
    }
    /**
     * Method used to check whether or not there are any DownloadJobNode objects in this DownloadQueue object.
     * @return
     *      Returns true if the front reference in this DownloadQueue object is equal to null. Returns false if it is
     *        set to anything else.
     */
    public boolean isEmpty(){
        if(front ==  null)
            return true;
        return false;
    }
    /**
     * Returns a formatted String presenting the list of DownloadJobNode objects in this DownloadQueue object. Used to
     *   show in what order servers will begin to work on the download jobs next.
     * @return
     *      Returns the result of toConciseString() method call for all data in the DownlodaJobNode objects in this
     *        DownloadQueue object.
     */
    public String toString(){
        if(isEmpty())
            return "empty";
        DownloadJobNode cursor = front;
        StringBuilder qString = new StringBuilder();
        qString.append(cursor.getData().toConciseString());
        while(cursor.getLink() != null){
            cursor = cursor.getLink();
            qString.append(cursor.getData().toConciseString());
        }
        return qString.toString();
    }
}
