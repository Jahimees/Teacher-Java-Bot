package main.constant;

public class MessageConstant {

    public static final String UNKNOWN_COMMAND_MESSAGE = "Такой команды не существует. Воспользуйтесь командой 'Помощь' для получения списка доступных команд";
    public static final String SUCCESS_REGISTRATION = ", Вы успешно зарегистрированы на бирже фриланса 'Непутевый программист'!";
    public static final String HELP_COMMAND_LIST = "Список команд:\n\n" +
            "= Помощь - выводит список команд\n" +
            "= Начать - регистрирует пользователя в системе и позволяет начать  играть\n" +
            "= Баланс - выводит текущий баланс баллов пользователей\n" +
            "= Задания - выводит список всех доступных заданий";
    public static final String ALREADY_REGISTERED = ", извините, Вы уже зарегистрированы";
    public static final String CURRENT_BALANCE = "Ваш текущий баланс баллов составляет ";
    public static final String PLUS_BONUS_1 = "Вам начислено ";
    public static final String PLUS_BONUS_2 = " бонусов за выполнение заданий!";
    public static final String INVALID_ID = "Убедитесь в правильности введнного id";
    public static final String TASK_ALREADY_IN_WORK = "Данная задача уже взята в работу";
    public static final String TASK_OWNER_NOT_CURRENT_USER = "Указанная задача не является вашей";
    public static final String TASK_ALREADY_FINISHED = "Указанная задача уже была завершена";
    public static final String TASK_WITH_NUMBER = "Задача под номером ";
    public static final String TASK_TOOK = " успешно взята в работу";
    public static final String TASK_REVOKED = " успешно удалена из вашего списка задач";
    public static final String TASK_FINISHED = " успешно завершена";
    public static final String HAS_NOT_ACTIVE_TASKS = "У вас сейчас нет активных задач";
    public static final String INPUT_TASK_ID = "Вы не указали id задания. Пожалуйста введите команду подобным образом: 'взять/закончить/отменить_задание id_задания'";
    public static final String TOO_MUCH_TASKS = "Вы не можете иметь более 2-х активных заданий одновременно";
    public static final String NO_TASKS = "На бирже сейчас нет заданий :(";
}
