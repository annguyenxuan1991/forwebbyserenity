package helpers;

import cucumber.api.DataTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableHelper {

    private DataTableHelper() {
    }

    /**
     * Convert a DataTable representing a list of Objects to List of HashMaps.
     * - Note: the first row is considered the headers describing the data table.
     *
     * @param dataTable DataTable to convert.
     * @return a List of HashMaps
     */
    public static List<Map<String, String>> convertDataTableToListHashMaps(DataTable dataTable) {
        List<Map<String, String>> resultList = new ArrayList<>();
        List<String> header = dataTable.raw().get(0);
        for (int i = 1; i < dataTable.raw().size(); i++) {
            List<String> row = dataTable.raw().get(i);
            Map<String, String> resultHashMap = new HashMap<>();
            for (int j = 0; j < row.size(); j++) {
                resultHashMap.put(header.get(j), row.get(j));
            }
            resultList.add(resultHashMap);
        }
        return resultList;
    }

    /**
     * Convert a DataTable representing a list of Objects to List of HashMaps.
     * - Note: the first row is considered the headers describing the data table.
     *
     * @param dataTable DataTable to convert.
     * @return a List of DataTable
     */
    public static List<String> convertDataTableToList(DataTable dataTable) {
        List<String> result= new ArrayList<>();
        for(int i = 0; i < dataTable.raw().size(); i++) {
            result.add(dataTable.raw().get(i).get(0));
        }

        return result;
    }

    /**
     * Convert a DataTable representing a Hash as input for scenario.
     * - Note: the first row is considered the headers describing the data table.
     *
     * @param dataTable DataTable to convert.
     * @return a HashMap
     */
    public static Map<String, String> convertDataTableToHashMap(DataTable dataTable) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < dataTable.raw().get(0).size(); i++) {
            result.put(dataTable.raw().get(0).get(i), dataTable.raw().get(1).get(i));
        }
        return result;
    }

    /**
     * Convert a DataTable representing a Hash as input for scenario.
     * - Note: the first row is considered the headers describing the data table.
     * @param dataTable DataTable to convert.
     * @param pojoClass POJO class to return
     * @return a POJO
     */
    public static <T> T convertDataTableToPOJO(DataTable dataTable, Class<T> pojoClass) {
        return dataTable.asList(pojoClass).get(0);
    }
}
