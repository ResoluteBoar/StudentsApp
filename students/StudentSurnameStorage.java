package students;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {
    private TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreated(Long id, String surname){
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }
    public void studentDeleted(Long id, String surname){
        surnamesTreeMap.get(surname).remove(id);
    }
    public void studentUpdated(Long id, String oldSurname, String newsurname){
        studentDeleted(id,oldSurname);
        studentCreated(id,newsurname);
    }

    public Set<Long> getStudentBySurnamesLessOrEqualThan(String surname){
        Set<Long> res = surnamesTreeMap.headMap(surname,true)
                .values()
                .stream()
                .flatMap(longs -> longs.stream())
                .collect(Collectors.toSet());
        return res;
    }
}
