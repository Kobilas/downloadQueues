package mkobilas.homework.lecturedownloader;

/**
 * Used to throw exceptions whenever there is a DownloadQueue object that is completely empty.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class EmptyQueueException extends Exception{
    private static final long serialVersionUID = 4450255187750876838L;
    /**
     * Constructor used to throw exceptions without specific error messages.
     * @postcondition
     *      Creates and throws an empty EmptyQueueException.
     */
    public EmptyQueueException(){
    }
    /**
     * Constructor used to throw exceptions with an error message.
     * @param message
     *      String message is the error message that is printed if there is ever an empty queue.
     * @postcondition
     *      Creates and throws an EmptyQueueException with the given error message.
     */
    public EmptyQueueException(String message){
        super(message);
    }
    /**
     * Constructor used to throw exceptions with an error message and the cause for the exception, such as another
     *   exception.
     * @param message
     *      String message is the error message that is printed if there is ever an empty queue.
     * @param cause
     *      Throwable cause is the cause for the exception being thrown, such as another exception.
     * @postcondition
     *      Creates and throws an EmptyQueueException with the given error message along with the cause given for it.
     */
    public EmptyQueueException(String message, Throwable cause){
        super(message, cause);
    }
}
