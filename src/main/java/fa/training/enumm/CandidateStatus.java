package fa.training.enumm;

import java.lang.reflect.Field;
import java.util.*;

public class CandidateStatus {

    public static final String WaitingInterview = "Waiting for interview";  //open

    public static final String FailedInterview = "Failed Interview"; // failed

    public static final String PassInterview = "Pass Interview";    // pass

    public static final String CancelInterview = "Cancel Interview"; //cancel

    public static final String Open = "Open";
    public static final String Banned = "Banned";

    public static final Map<String, String> getMapCandidateStatus() throws IllegalAccessException {
        Map<String, String> candidateStatustMap = new HashMap<>();
        Field[] fields = CandidateStatus.class.getFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                candidateStatustMap.put((String) field.get(null), (String) field.get(null));
            }
        }
        return candidateStatustMap;
    }

    public static final List<String> getListCandidateStatus() throws IllegalAccessException {
        List<String> candidateStatustList = new ArrayList<>();
        Field[] fields = CandidateStatus.class.getFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                candidateStatustList.add((String) field.get(null));
            }
        }
        return candidateStatustList;
    }

    public static final Map<String, String> getCandidateStatusHasKeyIsResult() throws IllegalAccessException {
        List<String> resultList = Status.getListStatus();
        List<String> candidateStatusList = CandidateStatus.getListCandidateStatus();

        Iterator<String> resultListIterator = resultList.iterator();
        Iterator<String> candidateStatusListIterator = candidateStatusList.iterator();

        Map<String, String> candidateStatustMap = new HashMap<>();
        while (resultListIterator.hasNext() && candidateStatusListIterator.hasNext()) {
            String key = resultListIterator.next();
            String value = candidateStatusListIterator.next();
            candidateStatustMap.put(key, value);
        }
        return candidateStatustMap;
    }
}
