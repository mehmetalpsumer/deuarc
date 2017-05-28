
public class LinkedList {
	/* Attributes */
	private LinkedListNode head, tail;

	
	/* Constructor */
	public LinkedList(){
		this.head = null;
		this.tail = null;
	}
	
	/* Functions */
	public State nextMicro(){
		LinkedListNode temp = head;
		for (int i = 0; i < Computer.current; i++) {
			if(temp == null){
				return temp.getValue();
			}
			temp = temp.getNext();
		}
		
		if(temp.getNext()==null){
			return temp.getValue();
		}
		Computer.current += 1;
		System.out.println(Computer.current);
		return temp.getNext().getValue();
		
	}
	public State prevMicro(){
		if(Computer.current==0){
			if(head!=null)
				return head.getValue();
			else
				return null;
		}
		else{
			LinkedListNode temp = head;
			for (int i = 0; i < Computer.current; i++) {
				if(temp == null){
					return temp.getValue();
				}
				temp = temp.getNext();
			}
			Computer.current--;
			return temp.getPrev().getValue();
		}
		
		
	}
	public State nextLine(){
		LinkedListNode temp = head;
		for (int i = 0; i < Computer.current; i++) {
			if(temp == null){
				return temp.getValue();
			}
			temp = temp.getNext();
		}
		temp = temp.getNext();
		Computer.current++;
		while(temp!=null){
			if(temp.isEndOfLine()){
				return temp.getValue();
			}
			temp = temp.getNext();
			Computer.current++;
		}
		return temp.getValue();
	}
	public State prevLine(){
		if(Computer.current==0){
			if(head!=null)
				return head.getValue();
			else
				return null;
		}
		else{
			LinkedListNode temp = head;
			for (int i = 0; i < Computer.current; i++) {
				if(temp == null){
					return temp.getValue();
				}
				temp = temp.getNext();
			}
			temp = temp.getPrev();
			Computer.current--;
			while(temp!=null){
				if(temp.isEndOfLine()){
					return temp.getValue();
				}
				temp = temp.getPrev();
				Computer.current--;
			}
			return temp.getValue();
			
		}
	}
	public State current(){
		LinkedListNode temp = head;
		for (int i = 0; i < Computer.current; i++) {
			if(temp == null){
				return temp.getValue();
			}
			temp = temp.getNext();
		}
		return temp.getValue();
	}
	public void add(State state){
		LinkedListNode node = new LinkedListNode(state);
		
		if(head == null){
			this.head = node;
			this.tail = node;
		}
		else{
			node.setPrev(this.tail);
			this.tail.setNext(node);
			this.tail = node;
		}
	}
	public LinkedListNode getTail(){
		return this.tail;
	}
	public LinkedListNode getHead(){
		return this.head;
	}

	
}
