package DataModel;

import java.io.Serializable;

public class Employee implements Serializable{

    private String name;
    private int idEmployee;

    public Employee(String name, int idEmployee) {
        this.name = name;
        this.idEmployee = idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Override
    public String toString() {
        return  '\t' +  name + "  id= " + idEmployee;
    }


}
