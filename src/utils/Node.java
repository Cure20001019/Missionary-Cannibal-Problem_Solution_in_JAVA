package utils;

public class Node implements Position{
	private State element;
	Node next;
	public Node(State i) {
		element = i;
	}
	
	public State element() {
		return element;
	}
	
}
