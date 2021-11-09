package utils;

public class StateList implements List {

	Node head;
	private int size;
	
	public StateList() {
		this.head = null;
		this.size = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	//to implement A* search algorithm, child states acquired through expansion are inserted into open list 
	//according to the value of f stored inside each one of them, a state with a lower f indicates higher chance of finding the
	//target state by expanding it and is thus put closer to the front.
	@Override
	public Node addItem(State s) {
		Node new_node = new Node(s);
		if(this.isEmpty()) {
			new_node.next = null;
			this.head = new_node;
		}else {
			Node n = this.head;
			if(n.element().f >= s.f) {
				new_node.next = n;
				this.head = new_node;
			}else {
				while(n.next != null && n.next.element().f < s.f) {
					n = n.next;
				}
				new_node.next = n.next;
				n.next = new_node;
			}
		}
		size++;
		return new_node;
	}

	//used for inserting a node storing the state to be expanded into the closeList, 
	//which records the states that have been expanded. 
	@Override
	public Node headInsert(State s) {
		Node new_node = new Node(s);
		new_node.next = this.head;
		this.head = new_node;
		size++;
		return this.head;
	}

	//used for removing the head of open list before expanding the state stored inside it.
	@Override
	public Node headRemove() {
		if(isEmpty()) {
			System.out.println("The list is already empty!");
			return null;
		}
		Node temp = this.head;
		this.head = temp.next;
		size = size - 1;
		
		return temp;
	}

	//used for traversing the instantiated closeList and check each state 
	//stored inside the list, only a state with a different number of cannibals or missionaries
	//or boat at a different side of the river or with a non-greater depth(i.e. equal depth is permitted
	//in order to find all best solutions) can be counted as not having existed in the close list. The method
	//is used for checking whether a child state acquired after expansion can be added to open list for further
	//expansion.
	@Override
	public boolean SearchIfExist(State s) {
		Node node_temp = this.head;
		State state_temp;
		
		while(node_temp != null) {
			state_temp = node_temp.element();
			if((s.missionaries == state_temp.missionaries)&&(s.cannibals == state_temp.cannibals)&&(s.boat == state_temp.boat)&&(s.depth>state_temp.depth)) {
				return true;
			}
			node_temp = node_temp.next;
		}
		return false;
	}
	
	//prints a list starting from the head node
	public void printList(){
		Node temp = this.head;
		while(temp != null){
			System.out.println("\t("+Integer.toString(temp.element().missionaries)+","+Integer.toString(temp.element().cannibals)+","+Integer.toString(temp.element().boat)+")"
					+" f ="+Integer.toString(temp.element().f)
					+", depth ="+Integer.toString(temp.element().depth)
					+", h ="+Integer.toString(temp.element().compute_H()));
			temp = temp.next;
		}
	}

	
}
