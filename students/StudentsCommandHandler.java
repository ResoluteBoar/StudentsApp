package students;

import java.util.Map;

public class StudentsCommandHandler {

    private StudentStorage studentStorage = new StudentStorage();
    boolean hasError = false;

    public void processCommand(Command command){
        Action action = command.getAction();
        switch (action) {
            case CREATE -> {
                processCreateCommand(command);
                break;
            }
            case UPDATE -> {
                processUpdateCommand(command);
                break;
            }
            case DELETE -> {
                processDeleteCommand(command);
                break;
            }
            case STATS_BY_COURSE -> {
                processStatsByCourceCommand(command);
                break;
            }
            case STATS_BY_CITY -> {
                processStatsByCityCommand(command);
                break;
            }
            case SEARCH -> {
                processSearchCommand(command);
                break;
            }
            default -> {
                System.out.println("Дейтсвие "+ action + " не поддерживается");
            }
        }
        if (!hasError) {
            System.out.println("Обработка команд. Действие: " + command.getAction().name() + " , данные: " + command.getData());
        }

    }
    private void processCreateCommand(Command command){
        String data = command.getData();
        String[] dataArray = data.split(",");
        Student student = new Student();
        try{
            student.setSurname(dataArray[0]);
            student.setName(dataArray[1]);
            student.setCourse(dataArray[2]);
            student.setCity(dataArray[3]);
            student.setAge(Integer.valueOf(dataArray[4]));
            studentStorage.createStudent(student);
            studentStorage.printAll();

        } catch (NumberFormatException e) {
            System.out.println("Введённые данные некорректны");
            hasError = true;
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Некорректная величина массива данных");
            hasError = true;
        }

    }
    public void processUpdateCommand(Command command){
        String data = command.getData();
        String[] dataArray = data.split(",");
        Student student = new Student();

        try {
            Long id = Long.valueOf(dataArray[0]);

            student.setName(dataArray[1]);
            student.setSurname(dataArray[2]);
            student.setCourse(dataArray[3]);
            student.setCity(dataArray[4]);
            student.setAge(Integer.valueOf(dataArray[5]));

            studentStorage.updateStudent(id, student);
            studentStorage.printAll();
        } catch (NumberFormatException e) {
            System.out.println("Введённые данные некорректны");
            hasError = true;
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Некорректная величина массива данных");
            hasError = true;
        }

    }
    public void processDeleteCommand(Command command){
        String data = command.getData();
        Long id = Long.valueOf(data);
    }
    public void processStatsByCourceCommand(Command command){
        Map<String,  Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);
    }
    public void processStatsByCityCommand(Command command){
        Map<String,  Long> data = studentStorage.getCountByCity();
        studentStorage.printMap(data);
    }
    private void processSearchCommand(Command command){
        String surname = command.getData();
        studentStorage.search(surname);
    }

}
