package mkobilas.homework.lecturedownloader;
/**
 * Wrapper used to wrap DownloadJob objects in another object that is then used to be placed into a singly-linked list
 *   so that they might be accessed with new methods. This DownloadJobNode object comes with necessary accessor and
 *   mutator methods so that it might be manipulated while still in the linked list.
 * @author Matthew
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class DownloadJobNode {
    //Contains the DownloadJob object that is being wrapped
    private DownloadJob data;
    //Contains the reference to the next DownloadJob object in the singly-linked list
    private DownloadJobNode link;
    /**
     * Constructor for this DownloadJobNode object asking the user for the data to initialize the DownloadJobNode
     *   object with since the data cannot be null.
     * @param initData
     *      DownloadJob initData is the initial DownloadJob object to be wrapped in this DownloadJobNode object. Cannot
     *        be null.
     * @precondition
     *      DownloadJob initData is not null.
     * @postcondition
     *      Creates a DownloadJobNode object that might be used in a singly-linked list. This object is wrapping
     *        downloadJob initData so that it might be accessed while in the linked list.
     * @throws IllegalArgumentException
     *      Throws an exception if DownloadJob initData is null.
     */
    public DownloadJobNode(DownloadJob initData){
        if(initData ==  null)
            throw new IllegalArgumentException("Argument DownloadJob initData cannot be null.");
        data = initData;
        link = null;
    }
    /**
     * Constructor for this DownloadJobNode object asking the user for the data to initialize the DownloadJobNode 
     *   object with as well as the next DownloadJobNode in the linked list.
     * @param initData
     *      DownloadJob initData is the initial DownloadJob object to be wrapped in this DownloadJobNode object. Cannot
     *        be null.
     * @param initLink
     *      DownloadJobNode initLink is the reference to the next DownloadJobNode object in the linked list.
     * @precondition
     *      DownloadJob initData is not null.
     * @postcondition
     *      Creates a DownloadJobeNode object that might be used in a singly-linked list. This object is wrapping
     *        downloadJob initData so that it might be accessed while in the linked list. It has been initialized with 
     *        the user's designated DownloadJobNode initLink.
     * @throws IllegalArgumentExcception
     *      Throws an exception if DownloadJob initData is null.
     */
    public DownloadJobNode(DownloadJob initData, DownloadJobNode initLink){
        if(initData ==  null)
            throw new IllegalArgumentException("Argument DownloadJob initData cannot be null.");
        data = initData;
        link = initLink;
    }
    /**
     * Accessor method for this DownloadJobNode object's data. Used to access the methods of the DownloadJob object 
     *   being wrapped by this DownloadJobNode object.
     * @return
     *      Returns the reference to the DownloadJob object being wrapped by this DownloadJobNode object.
     */
    public DownloadJob getData(){
        return data;
    }
    /**
     * Mutator method for this DownloadJobNode object's data.
     * @param newData
     *      DownloadJob newData is what this DownloadJobNode object's data will be set to.
     * @precondition
     *      DownloadJob newData is not null.
     * @postcondition
     *      DownloadJob data is set to DownloadJob newData.
     * @throws IllegalArgumentException
     *      Exception is thrown if DownloadJob newData is null.
     */
    public void setData(DownloadJob newData){
        if(newData ==  null)
            throw new IllegalArgumentException("Argument DownloadJob newData cannot be null.");
        data.copyFrom(newData);
    }
    /**
     * Accessor method for the reference to the next DownloadJobNode object in the linked list that this 
     *   DownloadJobNode object is in.
     * @return
     *      Returns this DownloadJobNode object's link, which is the reference to the next DownloadJobNode object in
     *        the linked list which this DownloadJobNode object is in.
     */
    public DownloadJobNode getLink(){
        return link;
    }
    /**
     * Mutator method for this DownloadJobNode object's link. Used if a DownloadjobNode object must be deleted from a
     *   linked list, and it is achieved by not allowing a reference to it from anywhere.
     * @param newLink
     *      DownloadJobNode newLink is the what this DownloadJobNode object's link will be set to. This link can be
     *        null.
     * @postcondition
     *      DownloadJobNode link is set to the DownloadJobNode newLink specified by the user.
     */
    public void setLink(DownloadJobNode newLink){
        link = newLink;
    }
}
