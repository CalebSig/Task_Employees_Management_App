package BusinessLogic;

import DataAccess.SerializationOperations;
import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.SimpleTask;
import DataModel.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TasksManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Task> tasks;
    private List<Employee> employees;
    private Map<Employee, List<Task>> map;

    public Map.Entry<Employee, List<Task>> getEntryById(int idEmployee) {
        for (Employee employee : map.keySet()) {
            if (employee.getIdEmployee() == idEmployee) {
                //System.out.println(Map.entry(employee, map.get(employee)));
                return Map.entry(employee, map.get(employee));
            }
        }
        return null;
    }


    public TasksManagement()
    {
        this.tasks = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.map = new HashMap<>();
    }

    public Map<Employee,List<Task>> getMap(){ return map;}

    public void assignTaskToEmployee(int idEmployee, Task task) {
        Map.Entry<Employee, List<Task>> entry = getEntryById(idEmployee);
        if(entry == null) {
            return;
        }
        if(!entry.getValue().contains(task)) {
            entry.getValue().add(task);
        }
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        int s=0;
        Map.Entry<Employee, List<Task>> entry = getEntryById(idEmployee);

        for(Task task : entry.getValue()) {
            if(task.getStatusTask().equals("Completed")) {
                s += task.estimateDuration();
            }
        }

        return s;
    }

    public void modifyAllSubtasksToCompleted(List<Task> tasks) {
        for (Task task : tasks) {
            task.setStatusTask("Completed");
            if (task instanceof ComplexTask) {
                modifyAllSubtasksToCompleted(((ComplexTask) task).getTasks());
            }
        }
    }

    public void modifyTaskStatus(int idEmployee, int idTask) {
        for (Task task : tasks) {
            if (idTask == task.getIdTask()) {
                if (task.getStatusTask().equals("Completed")) {
                    task.setStatusTask("Uncompleted");
                } else {
                    task.setStatusTask("Completed");
                    if (task instanceof ComplexTask) {
                        modifyAllSubtasksToCompleted(((ComplexTask) task).getTasks());
                    }
                }
                return;
            }
        }
    }

    public int countCompletedTasks(int idEmployee) {
        Map.Entry<Employee, List<Task>> entry = getEntryById(idEmployee);
        int count = 0;
        if(entry == null) {
            return -1;
        }
        for(Task task : entry.getValue()) {
            if(task.getStatusTask().equals("Completed")) {
                count++;
            }
        }
        return count;
    }

    public int countUncompletedTasks(int idEmployee) {
        Map.Entry<Employee, List<Task>> entry = getEntryById(idEmployee);
        int count = 0;
        if(entry == null) {
            return -1;
        }
        for(Task task : entry.getValue()) {
            if(task.getStatusTask().equals("Uncompleted")) {
                count++;
            }
        }
        return count;
    }

    public void addEmployee(Employee newEmployee) {
        employees.add(newEmployee);
        map.put(newEmployee, new ArrayList<>());
    }


    public void removeEmployee(Employee oldEmployee) {
        employees.remove(oldEmployee);
        map.remove(oldEmployee);
    }

   public void deleteTaskFromList(int idTask, List<Task> tasks) {
        if(tasks!=null) {
            for(Task task : tasks) {
                if(task.getIdTask() == idTask) {
                    tasks.remove(task);
                }else if(task instanceof ComplexTask) {
                    deleteTaskFromList(idTask, ((ComplexTask) task).getTasks());
                }
            }
        }
   }

    public void removeTask(Integer idTask) {
        Task task=getTaskById(idTask);
        if(task != null) {
            tasks.remove(task);
        }
        for(Map.Entry<Employee, List<Task>> entry : map.entrySet()) {
            deleteTaskFromList(idTask, entry.getValue());
        }

    }

    public Task getTaskById(int idTask) {
        for(Task task : tasks) {
            if(task.getIdTask() == idTask) {
                return task;
            }
        }
        return null;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getAllTasks() {
        return this.tasks;
    }

    public List<Employee> getAllEmployees() {
        return this.employees;
    }

    public List<Task> getAssignedTasksForEmployee(int idEmployee) {
        Map.Entry<Employee, List<Task>> entry = getEntryById(idEmployee);
        return (entry != null) ? entry.getValue() : new ArrayList<>();
    }

    /*public void printEmployees(){
        System.out.println("Employees:" + employees);
    }

    public void printMap() {
        for(Employee employee : map.keySet()) {
            System.out.println(getEntryById(employee.getIdEmployee()));
            for(Task task : map.get(employee)) {
                System.out.println(task.toString());
            }
        }
    }*/

}
