package BusinessLogic;

import DataModel.Employee;
import DataModel.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

    public static List<Employee> filterEmployees(TasksManagement tasksManagement) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : tasksManagement.getMap().keySet()) {
            if(tasksManagement.calculateEmployeeWorkDuration(employee.getIdEmployee())>=40)
                employees.add(employee);
        }
        return employees;
    }

    public static List<Employee> getWorkEmployees(TasksManagement tasksManagement) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : tasksManagement.getMap().keySet()) {
            employees.add(employee);
        }
        return employees;
    }

    public static Map<String, Map<String, Integer>> calculateCompletedAndUncompletedTasks(TasksManagement tasksManagement) {
        Map<String, Map<String, Integer>> completedAndUncompletedTasks = new HashMap<>();
        for (Employee employee : tasksManagement.getMap().keySet()) {

            int completed = tasksManagement.countCompletedTasks(employee.getIdEmployee());
            int uncompleted = tasksManagement.countUncompletedTasks(employee.getIdEmployee());
            Map<String, Integer> complUncompletedTasks = new HashMap<>();

            complUncompletedTasks.put("Completed", completed);
            complUncompletedTasks.put("Uncompleted", uncompleted);
            completedAndUncompletedTasks.put(employee.getName(), complUncompletedTasks);
        }
        return completedAndUncompletedTasks;
    }
    
}
