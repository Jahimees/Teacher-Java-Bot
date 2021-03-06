package main.constant;

public class MessageConstant {

    public static final String UNKNOWN_COMMAND_MESSAGE = "Такой команды не существует. Воспользуйтесь командой 'Помощь' для получения списка доступных команд";
    public static final String SUCCESS_REGISTRATION = ", Вы успешно зарегистрированы на бирже фриланса 'Непутевый программист'!";
    public static final String HELP_COMMAND_LIST = "Список команд:\n\n" +
            "===ОБЩЕЕ===\n" +
            "= Помощь - выводит список команд\n" +
            "= Начать - регистрирует пользователя в системе и позволяет начать  играть\n" +
            "= Баланс - выводит текущий баланс баллов пользователя\n\n" +
            "===ЗАДАНИЯ===\n" +
            "= Задания - выводит список всех доступных заданий\n" +
            "= Мои_задания - выводит список ваших текущих задания\n" +
            "= Взять/отменить/закончить_задание id_задания - берет в работу, отменяет и завершает задание по id соответственно\n\n" +
            "===КУРСЫ===\n" +
            "= Курсы - выводит список доступых для покупки курсов\n" +
            "= Купить_курс id_курса - покупка курса (если хватает бонусов)\n" +
            "= Мои_курсы - выводит список приобретенных курсов";
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
    public static final String INPUT_ID = "Вы не указали id. Пожалуйста введите команду подобным образом: 'указываемая_команда id'";
    public static final String TOO_MUCH_TASKS = "Вы не можете иметь более 2-х активных заданий одновременно";
    public static final String NO_TASKS = "На бирже сейчас нет заданий :(";
    public static final String NOT_REGISTERED = "Извините, вы не зарегистрированы. Введите команду 'Начать', чтобы зарегистрироваться";
    public static final String UNDEFINED_ENTITY_PAGINATION = "Прежде, чем листать страницы, нужно открыть книгу. Введите 'Задания' или 'Курсы'";
    public static final String NO_COURSES = "Сейчас нет доступных курсов :(";
    public static final String COURSE_ALREADY_BOUGHT = "Указанный курс уже приобретен";
    public static final String NOT_ENOUGH_BONUSES = "Недостаточно средств для приобретения курса";
    public static final String COURSE_WITH_NUMBER = "Курс под номером ";
    public static final String SUCCESSFULLY_BOUGHT = " успешно приобретен";
    public static final String NO_ONE_BOUGHT_COURSE = "У Вас пока нет приобретенных курсов :(";

    //ADMIN
    public static final String NOT_ADMIN = "HEY BRO, NICE DICK, BUT YOU ARE NOT ADMIN";
    public static final String INVALID_ARGUMENTS = "INVALID ARGUMENTS";
    public static final String TASK_CREATED = "Задача успешно создана";
    public static final String ERROR = "ERROR CHECK STACK_TRACE";
    public static final String FOR_USER = "Пользователю ";
    public static final String PAYED_BONUSES = " было передано бонусов в размере ";
    public static final String COURSE_CREATED = "Курс успешно создан";
}
