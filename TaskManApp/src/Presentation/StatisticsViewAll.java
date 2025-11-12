package Presentation;

import BusinessLogic.TasksManagement;
import BusinessLogic.Utility;
import DataModel.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class StatisticsViewAll extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private TasksManagement tasksManagement;

    private String[] columnNames = {"Id_employee", "Name", "Work duration", "Completed Tasks", "Uncompleted Tasks"};

    public StatisticsViewAll(TasksManagement tasksManagement) {
        this.tasksManagement = tasksManagement;
        setTitle("Employee Statistics");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        populateTable();
    }

    public void populateTable() {
        List<Employee> filteredEmployees = Utility.getWorkEmployees(tasksManagement);
        filteredEmployees.sort((e1, e2) -> Integer.compare(
                tasksManagement.calculateEmployeeWorkDuration(e1.getIdEmployee()),
                tasksManagement.calculateEmployeeWorkDuration(e2.getIdEmployee())
        ));

        Map<String, Map<String, Integer>> taskStats = Utility.calculateCompletedAndUncompletedTasks(tasksManagement);
        tableModel.setRowCount(0);
        for (Employee employee : filteredEmployees) {
            int workDuration = tasksManagement.calculateEmployeeWorkDuration(employee.getIdEmployee());
            int completedTasks = taskStats.get(employee.getName()).get("Completed");
            int uncompletedTasks = taskStats.get(employee.getName()).get("Uncompleted");

            tableModel.addRow(new Object[]{employee.getIdEmployee(), employee.getName(), workDuration, completedTasks, uncompletedTasks});
        }
    }
}
