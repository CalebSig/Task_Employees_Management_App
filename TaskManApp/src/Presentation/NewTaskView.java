package Presentation;

import BusinessLogic.TasksManagement;
import DataModel.Employee;
import DataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class NewTaskView extends JFrame {

    private JLabel labelId, labelTaskType, labelStartHour, labelEndHour, labelSelectTask;
    private JTextField textId, textStartHour, textEndHour;
    private JComboBox<String> comboTaskType;
    private JButton buttonAddTask, buttonCancel;
    private JPanel panelForm, panelButtons, contentPane;
    private JList<Task> taskList;
    private DefaultListModel<Task> taskListModel;
    private JScrollPane taskScrollPane;

    public NewTaskView(String name) {
        super(name);
        this.prepareGui();
    }

    private void prepareGui() {
        this.setSize(500, 400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.contentPane = new JPanel(new BorderLayout());
        this.prepareFormPanel();
        this.prepareButtonsPanel();

        this.setContentPane(this.contentPane);
    }

    private void prepareFormPanel() {
        this.panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        this.panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        this.labelId = new JLabel("Task ID:");
        this.textId = new JTextField();

        this.labelTaskType = new JLabel("Task Type:");
        this.comboTaskType = new JComboBox<>(new String[]{"Simple Task", "Complex Task"});
        this.comboTaskType.addActionListener(e -> toggleTaskList());

        this.labelStartHour = new JLabel("Start Hour:");
        this.textStartHour = new JTextField();

        this.labelEndHour = new JLabel("End Hour:");
        this.textEndHour = new JTextField();

        this.taskListModel = new DefaultListModel<>();
        this.taskList = new JList<>(taskListModel);
        this.taskList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.taskScrollPane = new JScrollPane(taskList);
        this.taskScrollPane.setVisible(false);

        this.labelSelectTask = new JLabel("Select SubTasks:");
        this.labelSelectTask.setVisible(false);

        this.panelForm.add(labelId);
        this.panelForm.add(textId);
        this.panelForm.add(labelTaskType);
        this.panelForm.add(comboTaskType);
        this.panelForm.add(labelStartHour);
        this.panelForm.add(textStartHour);
        this.panelForm.add(labelEndHour);
        this.panelForm.add(textEndHour);
        this.panelForm.add(labelSelectTask);
        this.panelForm.add(taskScrollPane);

        this.contentPane.add(this.panelForm, BorderLayout.CENTER);
    }

    private void prepareButtonsPanel() {
        this.panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        this.buttonAddTask = new JButton("Add Task");
        this.buttonCancel = new JButton("Cancel");

        this.panelButtons.add(buttonAddTask);
        this.panelButtons.add(buttonCancel);

        this.contentPane.add(this.panelButtons, BorderLayout.SOUTH);
    }

    private void toggleTaskList() {
        boolean isComplexTask = comboTaskType.getSelectedItem().equals("Complex Task");
        labelSelectTask.setVisible(isComplexTask);
        taskScrollPane.setVisible(isComplexTask);
        labelEndHour.setVisible(!isComplexTask);
        labelStartHour.setVisible(!isComplexTask);
        textStartHour.setVisible(!isComplexTask);
        textEndHour.setVisible(!isComplexTask);
        revalidate();
        repaint();
    }

    public void populateListT(List <Task> tasks) {
        taskListModel.clear();
        if(tasks!=null) {
            for (Task task : tasks) {
                taskListModel.addElement(task);
            }
        }
        taskList.setModel(taskListModel);
        revalidate();
        repaint();

    }

    public String getTaskType() {
        return comboTaskType.getSelectedItem().toString();
    }

    public int getTaskId() throws NumberFormatException {
        return Integer.parseInt(textId.getText());
    }


    public int getStartHour() throws NumberFormatException {
        return Integer.parseInt(textStartHour.getText());
    }

    public int getEndHour() throws NumberFormatException {
        return Integer.parseInt(textEndHour.getText());
    }

    public List<Task> getSelectedTasks() {
        return taskList.getSelectedValuesList();
    }

    public void addButtonAddTaskListener(ActionListener listener) {
        buttonAddTask.addActionListener(listener);
    }

    public void addButtonCancelListener(ActionListener listener) {
        buttonCancel.addActionListener(listener);
    }

    public void clearFields() {
        textId.setText("");
        textStartHour.setText("");
        textEndHour.setText("");
    }

    public void addComboBoxListener(ActionListener listener) {
        comboTaskType.addActionListener(listener);
    }
}