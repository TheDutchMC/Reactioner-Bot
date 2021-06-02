package dev.array21.reactioner.commands;

import java.util.HashMap;

public class CommandManager {

	private HashMap<String, ICommand> executors = new HashMap<>();
	
	public void registerCommand(ICommand executor) {
		Command commandAnnotation = executor.getClass().getAnnotation(Command.class);
		assert commandAnnotation != null;
		
		String command = commandAnnotation.command();
		this.executors.put(command, executor);
	}
	
	public void fireCommand(String command, CommandParameters params) {
		ICommand executor = this.executors.get(command);
		if(executor == null) {
			return;
		}
		
		executor.runCommand(params);
	}
}
