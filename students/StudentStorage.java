package students;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentStorage {

    private Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();
    private Long currentId = 0L;

    public Long createStudent(Student student){
        try{
            Long nextId = getNextiId();
            studentStorageMap.put(nextId,student);
            studentSurnameStorage.studentCreated(nextId, student.getSurname());
            return nextId;
        }
        catch (NumberFormatException e){
            System.out.println("Введённые данные некорректны");
            return null;
        }

    }
    public boolean updateStudent(Long id, Student student) {
        if (!studentStorageMap.containsKey(id)){
            return false;
        } else{
            String newSurname = student.getSurname();
            String oldSurname = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdated(id, oldSurname, newSurname);
            studentStorageMap.put(id, student);
            return true;
        }
    }
    public Long getNextiId(){
        currentId = currentId +1;
        return currentId;
    }
    public void printAll(){
        System.out.println(studentStorageMap);
    }
    public boolean deleteStudent(Long id){
        Student removed = studentStorageMap.remove(id);
        if (removed != null){
            String surname = removed.getSurname();
            studentSurnameStorage.studentDeleted(id, surname);
        }
        return removed != null;
    }
    public void printMap(Map<String, Long> data){
        data.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey() + " - " + e.getValue());
        });
    }
    public Map<String,Long> getCountByCourse(){
        Map<String, Long> res = new HashMap<>();
        for (Student student : studentStorageMap.values()){
            String key = student.getCourse();
            Long count = res.getOrDefault(key, 0L);
            count++;
            res.put(key,count);
        }
        return res;
    }
    public Map<String,Long> getCountByCity(){
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCity(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }
    public void search(String surname){
        String[] surnames = surname.split(",");
        if (Objects.equals(surnames[0], "") && surnames.length==1){
            for (Map.Entry<Long, Student> entry : studentStorageMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        } else if (surnames.length == 1) {
            Set<Long> students = studentSurnameStorage.getStudentBySurnamesLessOrEqualThan(surname);

            for (Long studentId : students){
                Student student = studentStorageMap.get(studentId);
                System.out.println(studentId);
            }
        } else {
           
        }
    }

}
