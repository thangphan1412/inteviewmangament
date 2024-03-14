package fa.training.enumm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status {
    public static final String Open = "Open";

    public static final String Fail = "Fail";

    public static final String Pass = "Pass";

    public static final String Cancel = "Cancel";


    public static final Map<String, String> getMapStatus() throws IllegalAccessException {
        Map<String, String> resultMap = new HashMap<>();
        Field[] fields = Status.class.getFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                resultMap.put((String) field.get(null), (String) field.get(null));
            }
        }
        return resultMap;
    }

    public static final List<String> getListStatus() throws IllegalAccessException {
        List<String> resultList = new ArrayList<>();
        Field[] fields = Status.class.getFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                resultList.add((String) field.get(null));
            }
        }
        return resultList;
    }
}
