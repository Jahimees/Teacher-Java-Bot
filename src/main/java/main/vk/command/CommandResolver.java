package main.vk.command;

import java.util.HashMap;
import java.util.Map;

public enum CommandResolver {

    START {
        {
            this.command  = new StartCommand();
        }
    },
    HELP {
        {
            this.command = new HelpCommand();
        }
    },
    BALANCE {
        {
            this.command = new BalanceCommand();
        }
    },
    TASKS {
        {
            this.command = new TaskCommand();
        }
    };

    protected Command command;
    private static HashMap<String, String> commands;

    public Command getCurrentCommand() {
        return command;
    }

    static {
        commands = new HashMap<>();
        commands.put("начать", "START");
        commands.put("помощь", "HELP");
        commands.put("баланс", "BALANCE");
        commands.put("задания", "TASKS");
    }

    public static HashMap<String, String> getAllCommands() {
        return commands;
    }

    public static Command resolveCommand(String commandName) {
        HashMap<String, String> allCommands = getAllCommands();

        for (Map.Entry<String, String> existingCommand : allCommands.entrySet()) {
            if (existingCommand.getKey().equalsIgnoreCase(commandName)) {
                return CommandResolver.valueOf(existingCommand.getValue()).getCurrentCommand();
            }
        }

        return null;
    }
}
