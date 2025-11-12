import BusinessLogic.TasksManagement;
import DataAccess.SerializationOperations;
import Presentation.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        TasksManagement tasksManagement = SerializationOperations.deserializeData();
        StatisticsViewAll statisticsViewAll = new StatisticsViewAll(tasksManagement);
        DashBoardView dashBoardView = new DashBoardView("", tasksManagement);
        NewEmployeeView newEmployeeView= new NewEmployeeView("");
        NewTaskView newTaskView = new NewTaskView("");
        StatisticsView statisticsView = new StatisticsView(tasksManagement);
        Controller controller = new Controller(statisticsViewAll,dashBoardView, newEmployeeView, newTaskView, statisticsView, tasksManagement);
    }
}