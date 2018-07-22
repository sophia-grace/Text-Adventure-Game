import java.io.BufferedReader;
import java.io.*; //for file/use input/output

public class Game {
	LinkedList<String> backpack; // LinkedList of Strings
	Dictionary<String, State> states;
	// Dictionary<String, Command> commands;
	Dictionary<String, Item> items;
	State current_state; // where you currently are, starts null then set it to
							// first state
	int moves = 5;

	Game(String fileName) { // constructor
		// initialize attributes
		backpack = new LinkedList<String>();
		states = new Dictionary<String, State>();
		items = new Dictionary<String, Item>();

		// read in game file
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println("line: " + line);
				String[] parts = line.split("::");
				if (line.trim().equals(" ")) {
					continue;
				}
				if (line.startsWith("#")) {
					continue;
				}
				if (parts[0].equals("state")) {
					State first = new State(parts[1], parts[2]);
					states.add(parts[1], first); // add to the dictionary in
													// State
					if (current_state == null) { // only set the first state to
													// current_state
						current_state = first;
					}
				} else if (parts[0].equals("item")) {
					Item item = new Item(parts[1], parts[2], parts[3]);
					// add to items dictionary in state class
					State location = states.get(parts[3]); // get the location
															// of the item,
															// returning state
															// object
					location.items.add(parts[1], item);
					items.add(parts[1], item);
				} else if (parts[0].equals("command")) {
					// refers to either state or item?
					Command command = new Command(parts[1], parts[2], parts[3], parts[4]);
					State fromLocation = states.get(parts[2]); // items go in
																// here
					fromLocation.commands.add(parts[1], command);
					// commands.add(parts[1], command);
				} else {
					System.out.println("Ignored: " + line);
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("The file " + fileName + "cannot be found.");
		} catch (IOException io) {
			System.out.println("Exception found.");
		}
	}

	public String getInput(String prompt) {
		System.out.print(prompt);
		String input = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			input = br.readLine();
		} catch (IOException io) {
			io.printStackTrace();
		}
		return input;
	}

	public void print(State current_state) {
		if (current_state != null) {
			System.out.print("\n" + "You are in the " + current_state.name + ". " + "\n");
			System.out.print(current_state.description + " " + "\n");
			if (current_state.items.keys != 0) {
				for (int i = 0; i < current_state.items.table.length; i++) {
					NodeHT<String, Item> current = current_state.items.table[i].list;
					while (current != null) {
						System.out.print(
								"There is a " + current.key + ", " + current.value.description + " here. " + "\n");
						current = current.next;
					}
				}
			}
		}
	}

	// special verb methods using the input
	public void play() {
		System.out.print("WELCOME TO THE GAME: ESCAPE MERION" + "\n");
		System.out
				.print("You are alone brushing your teeth late at night. You are tired from a long day of coding.  Suddenly, you hear footsteps behind you. You look in the mirror and see a woman wearing a long, white dress. IT'S LILLIAN, THE GHOST OF MERION! NOW RUN FOR YOUR LIFE!"
						+ "\n");
		System.out.print("Type 'help' for instructions." + "\n");
		print(current_state);

		while (true) {

			String prompt = " >> ";
			String input = this.getInput(prompt);
			Command command = current_state.commands.get(input); // return the
																	// command
																	// associated
			// with input
			boolean bpcontains = false;

			if (input.length() >= 4 && input.substring(0, 4).equals("drop")) {
				if (input.length() == 4) {
					System.out.print("What do you want to drop?" + "\n");
				} else {
					Item item = items.get(input.substring(5, input.length()));
					if (backpack.contains(item.name)) {
						// remove item from backpack
						backpack.delete(item.name);
						System.out.print(item.name + " has been dropped from the backpack." + "\n");
						current_state.items.add(item.name, item); // add it to
																	// the
																	// current
																	// state
					} else {
						System.out.print("You do not have the " + item.name + " in your backpack." + "\n");
					}
				}
			} else if (input.length() >= 7 && input.substring(0, 7).equals("pick up")) {
				if (input.length() == 7) {
					System.out.print("What do you want to pick up?" + "\n");
				} else {
					Item item = items.get(input.substring(8, input.length()));
					if (item != null) {
						if (current_state.items.contains(item.name)) {
							System.out.print(item.name + " has been added to the backpack." + "\n");
							backpack.add(item.name); // add to the backpack
							current_state.items.delete(item.name); // remove the
																	// item the
																	// state
						} else if (backpack.contains(item.name)) {
							System.out.print("You have already picked up the " + item.name + "." + "\n");
						}
					} else {
						System.out.print("Trust me, you dont want to pick up the " + input.substring(8, input.length())
								+ "." + "\n");
					}
				}

			} else if (input.length() >= 4 && input.substring(0, 4).equals("quit")) {
				State quit = new State("quit", "You have given up. I guess Lillian will get you now." + "\n");
				current_state = quit;
				System.out.print("\n" + quit.description);
				moves = 1;
				moves();

			} else if (input.length() >= 4 && input.substring(0, 4).equals("look")) {
				print(current_state);
			} else if (input.length() >= 4 && input.substring(0, 4).equals("hint")) {
				// maybe use breadth first search to print out all the items
				// stored
				// in the commands of the next state
				System.out.print("Here are the directions you can go: " + current_state.commands.toString() + "\n");
				String needed = "";
				for (int i = 0; i < current_state.items.table.length; i++) {
					NodeHT<String, Item> current = current_state.items.table[i].list;
					while (current != null) {
						if (!backpack.contains(current.key)) {
							needed = needed + current.key;
						}
						current = current.next;
					}
				}
				System.out.print(
						"Here are the items in this state that are missing from your backpack: " + needed + "\n");

			} else if (input.length() >= 4 && input.substring(0, 4).equals("help")) {
				System.out.print("\n" + "Instructions: " + "\n");
				System.out.print("'go' + direction changes your location." + "\n");
				System.out.print("'pick up' + item name adds items to your backpack." + "\n");
				System.out.print("'drop' + item name removes items from your backpack." + "\n");
				System.out.print("'use' + item name uses the item." + "\n");
				System.out.print("'move' + object name moves the object." + "\n");
				System.out.print("'put' + object name + 'back' returns the object to its original location." + "\n");
				System.out.print("'inventory' lists the items in your backpack." + "\n");
				System.out.print("'look' describes your surroundings." + "\n");
				System.out.print("'quit' stops the game." + "\n");
				System.out.print("'restart' restarts the game." + "\n");
				System.out.print("Other cheat codes may be used..." + "\n");
			} else if (input.length() >= 5 && input.substring(0, 5).equals("go to")) {
				if (input.length() == 5) {
					System.out.print("Where do you want to go?" + "\n");
				} else {
					State toState = states.get(input.substring(6, input.length()));
					if (toState != null) {
						current_state = toState;
						// print
						print(current_state);
					} else {
						System.out.print(
								"I'm sorry, but that location doesn't exist...at least in this nightnmare." + "\n");
					}
				}
			} else if (input.length() >= 5 && input.substring(0, 5).equals("solve")) {
				// breadth first search!!!
				search(current_state.name, "end");
			} else if (input.length() >= 7 && input.substring(0, 7).equals("restart")) {
				Game game = new Game("C:/Users/zjfisie/textadventure_escapemerion/EscapeMerion/src/MyGame.txt");	
				game.play();
			} else if (input.length() >= 9 && input.substring(0, 9).equals("inventory")) {
				if (backpack.length() != 0) {
					System.out.print("Your backpack contains " + backpack.toString() + "\n");
				} else {
					System.out.print("Your backpack is empty." + "\n");
				}

			} else if (command == null) {
				System.out.print("Hmmm...I don't think that's a good idea." + "\n");
				moves();
			} else {
				// returns the input of the user
				Node<String> current = command.parsed.list;
				while (current != null) { // check that bpcontains has all
											// of
					// the needed items
					if (backpack.contains(current.key)) {
						bpcontains = true;
					} else {
						bpcontains = false;
						break; // leave as soon as a needed item is not in
								// backpack
					}
					current = current.next;
				}
				if (bpcontains == true) { // search the current_state for
											// that
											// command) {
					State toState = states.get(command.to_state); // return
																	// the
																	// state
																	// associated
																	// with
																	// that
																	// command
					current_state = toState;
					if (current_state.name == "end") {
						System.out.print("CONGRATULATIONS! YOU'VE SURVIVED" + "\n");
						System.out.print(current_state.description + "\n");
					} else {
						print(current_state);
						moves = 6;
						moves();
					}
				} else {
					System.out.print("You don't have the items needed." + "\n");
					moves();
				}

			}

		}
	}

	public void moves() {
		moves--;
		if (moves > 0) {
			System.out.print("Better find a way out. Lillian is " + moves + " steps behind you." + "\n");
		} else if (moves == 0) {
			current_state = states.get("game over");
			System.out.print(current_state.description);
		} else {
			System.out.print("Sorry, but the game is over. Accept your fate, or type 'restart' to play again" + "\n");
		}
	}

	public boolean search(String current_state, String end_state) {
		State current = states.get(current_state);
		boolean found = false;
		Dictionary<String, State> visited = new Dictionary<String, State>();
		Queue<State> queue = new Queue<State>();
		queue.append_end(current);
		String path = "";
		while (!queue.isEmpty()) {
			current = queue.pop_front();
			path += current.name + "---> ";
			if (current.name.equals(end_state)) {
				path += "You have finished the game!";
				found = true;
				break;
			}
			if (!visited.contains(current.name)) {
				visited.add(current.name, current);
				// expand:

				for (int i = 0; i < current.commands.table.length; i++) {
					NodeHT<String, Command> pointer = current.commands.table[i].list;
					while (pointer != null) {
						State tostate = states.get(pointer.value.to_state);
						queue.append_end(tostate);
						pointer = pointer.next;
					}
				}
			}
		}
		if (!found) {
			System.out.println("ERROR: No path exists!");
		}
		System.out.print(path);
		return found;
	}

	public static void main(String[] args) {
			Game game = new Game("C:/Users/zjfisie/textadventure_escapemerion/EscapeMerion/src/MyGame.txt");	
			game.play();
	}
}