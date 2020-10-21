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
    },
    NEXT {
        {
            this.command = new NextPageCommand();
        }
    },
    PREVIOUS {
        {
            this.command = new PreviousPageCommand();
        }
    },
    MY_TASKS {
        {
            this.command = new MyTaskCommand();
        }
    },
    TAKE_TASK {
        {
            this.command = new TakeTaskCommand();
        }
    },
    REVOKE_TASK {
        {
            this.command = new RevokeTaskCommand();
        }
    },
    FINISH_TASK {
        {
            this.command = new FinishTaskCommand();
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
        commands.put("дальше", "NEXT");
        commands.put("назад", "PREVIOUS");
        commands.put("мои_задания", "MY_TASKS");
        commands.put("взять_задание", "TAKE_TASK");
        commands.put("отменить_задание", "REVOKE_TASK");
        commands.put("закончить_задание", "FINISH_TASK");
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
