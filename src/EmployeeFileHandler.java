import java.io.*;
import java.util.*;

public class EmployeeFileHandler {
    private static final String FILE = "employees.dat";

    public static void save(List<Employee> list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));
        oos.writeObject(list);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    public static List<Employee> load() throws IOException, ClassNotFoundException {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
        List<Employee> list = (List<Employee>) ois.readObject();
        ois.close();
        return list;
    }
}