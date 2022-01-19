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
    private GregorianCalendar startDate;
    private GregorianCalendar endDate;
    private int vacationDays;
    private int compensatoryDays;

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

    public void setStartDate(int day, int month, int year) {
        startDate = new GregorianCalendar(year, month, day);
    }

    public void setEndDate(int day, int month, int year) {
        endDate = new GregorianCalendar(year, month, day);
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public int getCompensatoryDays() {
        return compensatoryDays;
    }

    public void setCompensatoryDays(int compensatoryDays) {
        this.compensatoryDays = compensatoryDays;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate.get(GregorianCalendar.DATE) + "." + (startDate.get(GregorianCalendar.MONTH) + 1) + "." + startDate.get(GregorianCalendar.YEAR) +
                ", endDate=" + endDate.get(GregorianCalendar.DATE) + "." + (endDate.get(GregorianCalendar.MONTH) + 1) + "." + endDate.get(GregorianCalendar.YEAR) +
                ", vacationDays=" + vacationDays +
                ", compensatoryDays=" + compensatoryDays +
                '}';
    }
}

class Request {
    private int id;
    private int employeeId;
    private int daysOffId;
    private int days;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getDaysOffId() {
        return daysOffId;
    }

    public void setDaysOffId(int daysOffId) {
        this.daysOffId = daysOffId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", daysOffId=" + daysOffId +
                ", days=" + days +
                '}';
    }
}

public class Main {

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
                String[] date1 = data[3].split("\\.");
                employee.setStartDate(Integer.parseInt(date1[0]), Integer.parseInt(date1[1]) - 1, Integer.parseInt(date1[2]));
                String[] date2 = data[4].split("\\.");
                employee.setEndDate(Integer.parseInt(date2[0]), Integer.parseInt(date2[1]) - 1, Integer.parseInt(date2[2]));
                employee.setVacationDays(Integer.parseInt(data[5]));
                employee.setCompensatoryDays(Integer.parseInt(data[6]));

                employees.add(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return employees;
    }

    public static List<Request> readRequests(File file) {
        List<Request> requests = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                Request request = new Request();
                request.setId(Integer.parseInt(data[0]));
                request.setEmployeeId(Integer.parseInt(data[1]));
                request.setDaysOffId(Integer.parseInt(data[2]));
                request.setDays(Integer.parseInt(data[3]));

                requests.add(request);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return requests;
    }

    public static void main(String[] args) {
        List<Employee> employees = readEmployees(new File("input1.txt"));
        employees.forEach(System.out::println);

        List<Request> requests = readRequests(new File("input2.txt"));
        requests.forEach(System.out::println);
    }

}
