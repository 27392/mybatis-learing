import java.util.*;

/**
 * @author lwh
 */
public class ParamTest {

    public static Object wrapToMapIfCollection(Object object, String actualParamName) {
        if (object instanceof Collection) {
            Map<String,Object> map = new HashMap<>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            Optional.ofNullable(actualParamName).ifPresent(name -> map.put(name, object));
            return map;
        } else if (object != null && object.getClass().isArray()) {
            Map<String,Object> map = new HashMap<>();
            map.put("array", object);
            Optional.ofNullable(actualParamName).ifPresent(name -> map.put(name, object));
            return map;
        }
        return object;
    }

    public static void main(String[] args) {
        System.out.println(ParamTest.wrapToMapIfCollection(new Object[]{"1", "2"}, null));

    }
}
