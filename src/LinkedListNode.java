
public class LinkedListNode {

	private State value;
	private LinkedListNode next, prev;
	private boolean isEndOfLine;
	
	public LinkedListNode(State state){
		this.value = state;
		this.next = null;
		this.prev = null;
	}

	public State getNode() {
		return value;
	}

	public void setNode(State node) {
		this.value = node;
	}

	public LinkedListNode getNext() {
		return next;
	}

	public void setNext(LinkedListNode next) {
		this.next = next;
	}

	public LinkedListNode getPrev() {
		return prev;
	}

	public void setPrev(LinkedListNode prev) {
		this.prev = prev;
	}

	public boolean isEndOfLine() {
		return isEndOfLine;
	}

	public void setEndOfLine(boolean isEndOfLine) {
		this.isEndOfLine = isEndOfLine;
	}

	public State getValue() {
		return value;
	}

	public void setValue(State value) {
		this.value = value;
	}
	
	
}
