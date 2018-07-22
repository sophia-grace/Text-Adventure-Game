public class State {
	String name;
	String description;
	Dictionary<String, Command> commands;
	Dictionary<String, Item> items;
		
	State(String name, String description) {
		this.name = name;
		this.description = description;
		commands = new Dictionary<String, Command>();
		items = new Dictionary<String, Item>();				
	}
	
	//to go to the next state, check the command and items
	//from command string to command object, from command object to state object (return state object)

}