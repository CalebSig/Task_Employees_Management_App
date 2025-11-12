package Presentation;

import DataModel.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEmployeeView extends JFrame {

    private JLabel labelName;
    private JLabel labelId;
    private JTextField textName;
    private JTextField textId;
    private JButton buttonAddEmployee;
    private JButton buttonCancel;
    private JPanel panelForm;
    private JPanel panelButtons;
    private JPanel contentPane;

    public NewEmployeeView(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui() {
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.contentPane = new JPanel(new BorderLayout());
        this.prepareFormPanel();
        this.prepareButtonsPanel();

        this.setContentPane(this.contentPane);
    }

    private void prepareFormPanel() {
        this.panelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        this.panelForm.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        this.labelName = new JLabel("Employee Name:");
        this.textName = new JTextField();

        this.labelId = new JLabel("Employee ID:");
        this.textId = new JTextField();

        this.panelForm.add(labelName);
        this.panelForm.add(textName);
        this.panelForm.add(labelId);
        this.panelForm.add(textId);

        this.contentPane.add(this.panelForm, BorderLayout.CENTER);
    }

    private void prepareButtonsPanel() {
        this.panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        this.buttonAddEmployee = new JButton("Add Employee");
        this.buttonCancel = new JButton("Cancel");

        this.panelButtons.add(buttonAddEmployee);
        this.panelButtons.add(buttonCancel);

        this.contentPane.add(this.panelButtons, BorderLayout.SOUTH);
    }

    public void addButtonAddEmployeeListener(ActionListener listener) { buttonAddEmployee.addActionListener(listener); }

    public void addButtonCancelListener(ActionListener listener) { buttonCancel.addActionListener(listener); }

    public String getEmployeeName() { return textName.getText().trim(); }

    public int getEmployeeId() throws NumberFormatException { return Integer.parseInt(textId.getText().trim()); }

    public void clearFields() {
        textName.setText("");
        textId.setText("");
    }



}
