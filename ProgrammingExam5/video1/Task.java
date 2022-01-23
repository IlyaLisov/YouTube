import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

class Employee {
    private int id;
    private String surname;
    private String name;
    private String skill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
}

class Project {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

class Position {
    private int id;
    private int projectId;
    private int employeeId;
    private int workload;
    private String billingType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", employeeId=" + employeeId +
                ", workload=" + workload +
                ", billingType='" + billingType + '\'' +
                '}';
    }
}

class OpenedPosition {
    private int id;
    private int projectId;
    private GregorianCalendar openDate;

    public OpenedPosition() {
        openDate = new GregorianCalendar();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setDate(int date, int month, int year) {
        this.openDate.set(year, month, date);
    }

    @Override
    public String toString() {
        return "OpenedPosition{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", openDate=" + openDate.get(GregorianCalendar.DATE) + "." + (openDate.get(GregorianCalendar.MONTH) + 1) + "." + openDate.get(GregorianCalendar.YEAR) +
                '}';
    }
}

public class Task {

    public static List<Employee> readEmployees(File file) {
        List<Employee> employees = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");

                Employee employee = new Employee();
                employee.setId(Integer.parseInt(data[0]));
                employee.setSurname(data[1]);
                employee.setName(data[2]);
                employee.setSkill(data[3]);

                employees.add(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public static List<Project> readProjects(File file) {
        List<Project> projects = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");

                Project project = new Project();
                project.setId(Integer.parseInt(data[0]));
                project.setTitle(data[1]);

                projects.add(project);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public static List<Position> readPositions(File file) {
        List<Position> positions = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");

                Position position = new Position();
                position.setId(Integer.parseInt(data[0]));
                position.setProjectId(Integer.parseInt(data[1]));
                position.setEmployeeId(Integer.parseInt(data[2]));
                position.setWorkload(Integer.parseInt(data[2]));
                position.setBillingType(data[4]);

                positions.add(position);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return positions;
    }

    public static List<OpenedPosition> readOpenedPositions(File file) {
        List<OpenedPosition> openedPositions = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");

                OpenedPosition openedPosition = new OpenedPosition();
                openedPosition.setId(Integer.parseInt(data[0]));
                openedPosition.setProjectId(Integer.parseInt(data[1]));

                String[] date = data[2].split("\\.");

                openedPosition.setDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));

                openedPositions.add(openedPosition);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return openedPositions;
    }

    public static void main(String[] args) {
        List<Employee> employees = readEmployees(new File("input1.txt"));
        employees.forEach(System.out::println);

        List<Project> projects = readProjects(new File("input2.txt"));
        projects.forEach(System.out::println);

        List<Position> positions = readPositions(new File("input3.txt"));
        positions.forEach(System.out::println);

        List<OpenedPosition> openedPositions = readOpenedPositions(new File("input4.txt"));
        openedPositions.forEach(System.out::println);
    }
}
