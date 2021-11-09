package utils;

public interface List {
	/* function prototypes */

	/* operation:        determine if a list is empty                 */
	/*                   l is an initialized list        */
	/* postconditions:   method returns True if l is empty     */
	/*                   and returns False otherwise                */
	public boolean isEmpty();

	/* operation:        return the number of objects in list          */
	/*                   l is an initialized list        */
	/* postconditions:   method returns number of objects in list   */
	public int size();

	/* operation:        add a state to list                    */
	/* preconditions:    s is a state to be added to list        */
	/* postconditions:   if possible, method adds item to the correct position */
	/*                   of the list and returns True; otherwise the    */
	/*                   method returns False                     */
	public Node addItem(State s);

	/*operation:         insert a state to the head of the list*/
	public Node headInsert(State s);

	/*operation:         remove the head node along with the state*/ 
	/*                   stored inside from the list              */
	public Node headRemove();

	/* operation:        search if a state duplicates while having a lower depth */
	/*                   s is a state to be searched for    */
	public boolean SearchIfExist(State s);

}
