package Presentation;

import BusinessLogic.TasksManagement;
import DataAccess.SerializationOperations;
import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.SimpleTask;
import DataModel.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private TasksManagement tasksManagement;
    private DashBoardView dashBoardView;
    private NewEmployeeView newEmployeeView;
    private NewTaskView newTaskView;
    private StatisticsView statisticsView;
    private StatisticsViewAll statisticsViewAll;

    public Controller(StatisticsViewAll statisticsViewAll,DashBoardView dashBoardView, NewEmployeeView newEmployeeView, NewTaskView newTaskView, StatisticsView statisticsView, TasksManagement tasksManagement) {
        this.dashBoardView = dashBoardView;
        this.newEmployeeView = newEmployeeView;
        this.newTaskView = newTaskView;
        this.statisticsView = statisticsView;
        this.tasksManagement = tasksManagement;
        this.statisticsViewAll = statisticsViewAll;

        dashBoardView.addButtonNewEmployeeListener(ActionListener -> newEmployeeView.setVisible(true));
        dashBoardView.addButtonNewTaskListerner(ActionListener -> {
            newTaskView.populateListT(tasksManagement.getAllTasks());
            newTaskView.setVisible(true);
        });
        dashBoardView.addButtonViewStatisticsListener(ActionListener -> {
            statisticsView.populateTable();
            statisticsView.setVisible(true);
            statisticsViewAll.populateTable();
            statisticsViewAll.setVisible(true);
        });
        dashBoardView.addButtonModifyTaskStatusListener(new ModifyStatusButton());
        dashBoardView.addButtonAssignTask(new ButtonAssignTask() );
        dashBoardView.addButtonDeleteEmployee(new ButtonDeleteEmployee());
        dashBoardView.addButtonDeleteTaskListener(new ButtonDeleteTask());

        newEmployeeView.addButtonAddEmployeeListener(new AddEmployeeButton());
        newEmployeeView.addButtonCancelListener(ActionListener -> newEmployeeView.dispose());

        newTaskView.addComboBoxListener(new TaskTypeChangeListener());
        newTaskView.addButtonAddTaskListener(new AddTaskButton());
        newTaskView.addButtonCancelListener(ActionListener -> newTaskView.dispose());

        dashBoardView.setVisible(true);
    }

    private class AddEmployeeButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = newEmployeeView.getEmployeeName();
                int id = newEmployeeView.getEmployeeId();

                Employee newEmployee = new Employee(name, id);
                tasksManagement.addEmployee(newEmployee);
                dashBoardView.populateListEmployee();
                newEmployeeView.clearFields();
                SerializationOperations.serializeData(tasksManagement);
                JOptionPane.showMessageDialog(newEmployeeView, "Employee Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                newEmployeeView.setVisible(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(newEmployeeView, "Invalid employee ID. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class TaskTypeChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (newTaskView.getTaskType().equals("Complex Task")) {
                List<Task> tasks = tasksManagement.getAllTasks();
                newTaskView.populateListT(tasks);
            }
        }
    }

    private class AddTaskButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = newTaskView.getTaskId();
                String type = newTaskView.getTaskType();

                if (type.equals("Simple Task")) {
                    int startHour = newTaskView.getStartHour();
                    int endHour = newTaskView.getEndHour();
                    if(startHour > endHour){
                        JOptionPane.showMessageDialog(newTaskView, "Invalid input. The startHour field should contain a number less or equal to the one in StopHoour!", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                    else{
                        Task task = new SimpleTask("Uncompleted", id, startHour, endHour);
                        tasksManagement.addTask(task);
                    }
                } else {
                    List<Task> selectedTasks = newTaskView.getSelectedTasks();
                    ComplexTask complexTask = new ComplexTask("Uncompleted", id, selectedTasks);
                    /*for (Task t : selectedTasks) {
                        tasksManagement.removeTask(t.getIdTask());
                    }*/
                    tasksManagement.addTask(complexTask);
                }
                SerializationOperations.serializeData(tasksManagement);
                dashBoardView.populateListTasks();
                newTaskView.clearFields();
                newTaskView.setVisible(false);
                newTaskView.dispose();
                JOptionPane.showMessageDialog(newTaskView, "Task Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(newTaskView, "Invalid input. Please make sure all fields are completed with the right information.", "Input Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public class ModifyStatusButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Task task= dashBoardView.getSelectedTask();
            if(task!=null){
                tasksManagement.modifyTaskStatus(0,task.getIdTask());
                dashBoardView.populateListTasks();
                dashBoardView.populateEmployeeTaskMap();
                dashBoardView.updateJTree();
                SerializationOperations.serializeData(tasksManagement);
            } else {
                JOptionPane.showMessageDialog(dashBoardView, "Please select a task from the tree!", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    public class ButtonAssignTask implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Employee> selectedEmployees=dashBoardView.getSelectedEmployees();
            List<Task> selectedTasks=dashBoardView.getSelectedTasks();

            if(selectedEmployees!=null && selectedTasks!=null && !selectedEmployees.isEmpty() && !selectedTasks.isEmpty()){
                for(Employee employee: selectedEmployees) {
                    for(Task task: selectedTasks) {
                        tasksManagement.assignTaskToEmployee(employee.getIdEmployee(), task);
                    }
                }
                dashBoardView.populateEmployeeTaskMap();
                SerializationOperations.serializeData(tasksManagement);
            }else{
                JOptionPane.showMessageDialog(dashBoardView, "Please select at least an employee and at least a task!", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    public class ButtonDeleteEmployee implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Employee> selectedEmployees=dashBoardView.getSelectedEmployees();
            for(Employee employee: selectedEmployees) {
                tasksManagement.removeEmployee(employee);
            }
            dashBoardView.populateListEmployee();
            dashBoardView.populateEmployeeTaskMap();
            dashBoardView.updateJTree();
            SerializationOperations.serializeData(tasksManagement);
        }
    }
    public class ButtonDeleteTask implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Task> selectedTasks=dashBoardView.getSelectedTasks();
            for(Task task: selectedTasks) {
                tasksManagement.removeTask(task.getIdTask());
            }
            dashBoardView.populateListTasks();
            dashBoardView.populateEmployeeTaskMap();
            dashBoardView.updateJTree();
            SerializationOperations.serializeData(tasksManagement);
        }
    }
}
