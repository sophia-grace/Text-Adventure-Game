public class Command { //the edges, connect the States
	String direction;
	String from_state;
	String to_state;
	String needed;
	LinkedList<String> parsed; //items needed to move to next state, separated by commas
	String[] parts;
	
	
	Command(String direction, String from_state, String to_state, String needed) {
		this.direction = direction;
		this.from_state = from_state;
		this.to_state = to_state;
		this.needed = needed;
		
		//parse needed
		parsed = new LinkedList<String>();
		parts = needed.split(",");
		
		//add to LinkedList of items needed
		for(int i = 0; i < parts.length; i++) {
			Node<String> node = new Node<String>(parts[i]);
			parsed.add(node);
		}
	}
}