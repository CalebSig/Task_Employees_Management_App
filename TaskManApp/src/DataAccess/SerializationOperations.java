package DataAccess;

import BusinessLogic.TasksManagement;
import java.io.*;

public class SerializationOperations {

    private static final String FILE_PATH = "tasks_management.dat";

    public static void serializeData(TasksManagement tasksManagement) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(tasksManagement);
            System.out.println("Data successfully saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving data.");
        }
    }

    public static TasksManagement deserializeData() {
        try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object obj = objectInputStream.readObject();
            if (obj instanceof TasksManagement) {
                return (TasksManagement) obj;
            } else {
                System.err.println("Invalid data format. Creating a new instance.");
                return new TasksManagement();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Creating a new instance.");
            return new TasksManagement();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error loading data. Creating a new instance.");
            return new TasksManagement();
        }
    }
}
