package Presentation;

import BusinessLogic.TasksManagement;
import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.SimpleTask;
import DataModel.Task;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class DashBoardView extends JFrame {

    TasksManagement tm;

    private JButton buttonNewEmployee, buttonNewTask;
    private JButton buttonAssignTasks;
    private JButton buttonViewStatistics;
    private JButton buttonModifyTaskStatus;
    private JButton buttonDeleteEmployee;
    private JButton buttonDeleteTask;

    private JPanel panelButtons;

    private JPanel panelMain1;
    private JPanel panelMain2;

    private JList <Employee> listEmployee;
    private DefaultListModel <Employee> listModelEmployee;

    private JList <Task> listTask;
    private DefaultListModel <Task> listModelTasks;

    private JLabel labelEmployee;
    private JLabel labelTask;

    private JLabel labelMap;
    private JTree employeeTaskTree;

    private JPanel contentPane;

    public DashBoardView(String name, TasksManagement tm) {
        super(name);
        this.tm = tm;
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(1000, 900);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.contentPane = new JPanel();
        this.prepareListsPanel();
        populateListEmployee();
        populateListTasks();
        populateEmployeeTaskMap();
        this.prepareButtonsPanel();

        this.setContentPane(this.contentPane);
    }

    private void prepareButtonsPanel() {
        this.panelButtons = new JPanel();
        this.panelButtons.setLayout(new GridLayout(1,7));
        this.contentPane.add(this.panelButtons, BorderLayout.SOUTH);
        this.panelButtons.setPreferredSize(new Dimension(800, 50));

        this.buttonNewEmployee = new JButton("Add new employee");
        this.buttonNewTask = new JButton("Add new task");
        this.buttonDeleteEmployee = new JButton("Delete employee");
        this.buttonDeleteTask = new JButton("Delete task");
        this.buttonAssignTasks = new JButton("Assign tasks");
        this.buttonViewStatistics = new JButton("View statistics");
        this.buttonModifyTaskStatus = new JButton("Modify task status");

        this.panelButtons.add(this.buttonNewEmployee);
        this.panelButtons.add(this.buttonNewTask);
        this.panelButtons.add(this.buttonDeleteEmployee);
        this.panelButtons.add(this.buttonDeleteTask);
        this.panelButtons.add(this.buttonAssignTasks);
        this.panelButtons.add(this.buttonModifyTaskStatus);
        this.panelButtons.add(this.buttonViewStatistics);
    }

    private void prepareListsPanel() {
        this.panelMain1 = new JPanel();
        this.panelMain1.setLayout(new GridLayout(1, 3));
        this.panelMain1.setPreferredSize(new Dimension(800, 50));

        this.panelMain2 = new JPanel();
        this.panelMain2.setLayout(new GridLayout(1, 3));
        this.panelMain2.setPreferredSize(new Dimension(800, 600));

        this.listModelEmployee = new DefaultListModel();
        this.labelEmployee = new JLabel("Employees");
        this.listEmployee = new JList<>(listModelEmployee);
        this.listEmployee.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.labelTask = new JLabel("Tasks");
        this.listTask = new JList();
        this.listTask.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.listModelTasks = new DefaultListModel<>();
        this.listTask.setModel(listModelTasks);

        listTask.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Task selectedTask = listTask.getSelectedValue();
                    if (selectedTask != null) {
                        showTaskDetails(selectedTask);
                    }
                }
            }
        });

        this.labelMap = new JLabel("Maps");
        this.employeeTaskTree = new JTree();
        this.employeeTaskTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Employees  -> Tasks")));

        this.panelMain1.add(this.labelEmployee);
        this.panelMain1.add(this.labelTask);
        this.panelMain1.add(this.labelMap);
        this.panelMain2.add(new JScrollPane(this.listEmployee));
        this.panelMain2.add(new JScrollPane(this.listTask));
        this.panelMain2.add(new JScrollPane(this.employeeTaskTree));

        this.contentPane.add(this.panelMain1, BorderLayout.NORTH);
        this.contentPane.add(this.panelMain2, BorderLayout.CENTER);
    }

    public void populateListEmployee() {
        listModelEmployee.clear();
        if(tm!=null) {
            for (Employee employee : tm.getAllEmployees()) {
                listModelEmployee.addElement(employee);
            }
        }
        listEmployee.setModel(listModelEmployee);
        revalidate();
        repaint();
    }

    public void populateListTasks() {
        listModelTasks.clear();
        if(tm!=null) {
            for (Task task : tm.getAllTasks()) {
                listModelTasks.addElement(task);
            }
        }
        listTask.setModel(listModelTasks);
        revalidate();
        repaint();
    }

    public void addButtonNewEmployeeListener(ActionListener listener) { buttonNewEmployee.addActionListener(listener);}
    public void addButtonNewTaskListerner(ActionListener listener){ buttonNewTask.addActionListener(listener);}
    public void addButtonViewStatisticsListener(ActionListener listener) {buttonViewStatistics.addActionListener(listener);}
    public void addButtonModifyTaskStatusListener(ActionListener listener) {buttonModifyTaskStatus.addActionListener(listener);}
    public void addButtonDeleteEmployee(ActionListener listener) {buttonDeleteEmployee.addActionListener(listener);}
    public void addButtonDeleteTaskListener(ActionListener listener) {buttonDeleteTask.addActionListener(listener);}

    public List<Task> getSelectedTasks() {return listTask.getSelectedValuesList();}
    public List<Employee> getSelectedEmployees(){return listEmployee.getSelectedValuesList();}

    private void showTaskDetails(Task task) {
        JDialog taskDialog = new JDialog(this, "Task Details", true);
        taskDialog.setSize(400, 300);
        taskDialog.setLayout(new BorderLayout());
        taskDialog.setLocationRelativeTo(this);

        JTextArea taskDetails = new JTextArea();
        DefaultListModel listDetailModelTasks = new DefaultListModel();
        JList <Task> detailsTaskList = new JList<>(listDetailModelTasks);
        detailsTaskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        if(task instanceof SimpleTask) {
            taskDetails.setEditable(false);
            taskDetails.setText(
                "Task ID: " + task.getIdTask() + "\n" +
                "Status: " + task.getStatusTask() + "\n" +
                "Start Hour: " + ((SimpleTask) task).getStartHour() + "\n" +
                "End Hour: " + ((SimpleTask) task).getEndHour()
            );
            taskDialog.add(new JScrollPane(taskDetails), BorderLayout.CENTER);
        } else {
            listDetailModelTasks.clear();
            for (Task detailTask : ((ComplexTask) task).getTasks()) {
                listDetailModelTasks.addElement(detailTask);
            }
            detailsTaskList.setModel(listDetailModelTasks);
            revalidate();
            repaint();

            taskDialog.add(new JScrollPane(detailsTaskList), BorderLayout.CENTER);

            detailsTaskList.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        Task selectedTask = detailsTaskList.getSelectedValue();
                        if (selectedTask != null) {
                            SwingUtilities.invokeLater(() -> showTaskDetails(selectedTask));
                        }
                    }
                }
            });

        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> taskDialog.dispose());

        taskDialog.add(closeButton, BorderLayout.SOUTH);
        taskDialog.setVisible(true);
    }

    public void addButtonAssignTask(ActionListener listener) {
        buttonAssignTasks.addActionListener(listener);
    }

    public void populateEmployeeTaskMap() {

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Employees -> Tasks");
        if (tm != null) {

            for (Employee employee : tm.getAllEmployees()) {
                DefaultMutableTreeNode employeeNode = new DefaultMutableTreeNode(employee);

                List<Task> tasks = tm.getAssignedTasksForEmployee(employee.getIdEmployee());
               // System.out.println(tasks);
                CreateNodes(employeeNode, tasks);
                rootNode.add(employeeNode);
            }

            DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
            employeeTaskTree.setModel(treeModel);
        }

        revalidate();
        repaint();
        employeeTaskTree.updateUI();
    }

    public void CreateNodes(DefaultMutableTreeNode rootNode, List<Task> tasks) {
        try {
            for (Task task : tasks) {
                if (task instanceof SimpleTask) {
                    rootNode.add(new DefaultMutableTreeNode(task));
                } else if (task instanceof ComplexTask) {
                    DefaultMutableTreeNode taskNode = new DefaultMutableTreeNode(task);
                    CreateNodes(taskNode, ((ComplexTask) task).getTasks());
                    rootNode.add(taskNode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public DefaultMutableTreeNode getSelectedNode(){ return (DefaultMutableTreeNode) employeeTaskTree.getLastSelectedPathComponent();}

    public Task getSelectedTask() {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if (selectedNode == null || !(selectedNode.getUserObject() instanceof Task)) {
            return null;
        }
        Task selectedTask = (Task) selectedNode.getUserObject();
        return selectedTask;
    }

    public void updateJTree() {
        DefaultTreeModel model = (DefaultTreeModel) employeeTaskTree.getModel();
        model.reload();
    }
}
