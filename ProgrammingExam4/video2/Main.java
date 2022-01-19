import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

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

    public int getDays() {
        return Math.toIntExact((endDate.getTimeInMillis() - startDate.getTimeInMillis()) / 1000 / 86400);
    }

    public int getEndDay() {
        return endDate.get(GregorianCalendar.DATE);
    }

    public int getEndMonth() {
        return endDate.get(GregorianCalendar.MONTH);
    }

    public int getEndYear() {
        return endDate.get(GregorianCalendar.YEAR);
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

    public static void solve1(File output, List<Employee> employees, List<Request> requests) {
        try(PrintWriter writer = new PrintWriter(output)) {
            List<Request> result = requests.stream()
                    .filter(r -> {
                        Employee employee = employees.stream()
                                .filter(e -> e.getId() == r.getEmployeeId())
                                .collect(Collectors.toList())
                                .get(0);

                        boolean key = true;

                        switch(r.getDaysOffId()) {
                            case 1:
                                if(employee.getVacationDays() >= r.getDays()) {
                                    return false;
                                }
                                break;
                            case 2:
                                if(employee.getCompensatoryDays() >= r.getDays()) {
                                    return false;
                                }
                                break;
                        }

                        return key;
                    })
                    .collect(Collectors.toList());

            if(result.size() > 0) {
                writer.print(result.get(0).getId() + ";" + result.get(0).getEmployeeId() + ";" + result.get(0).getDaysOffId() + ";" + result.get(0).getDays());
            }
            for(int i = 1; i < result.size(); i++) {
                writer.print(System.lineSeparator() + result.get(i).getId() + ";" + result.get(i).getEmployeeId() + ";" + result.get(i).getDaysOffId() + ";" + result.get(i).getDays());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve2(File output, File input, List<Employee> employees) {
        try(Scanner scanner = new Scanner(input);
            PrintWriter writer = new PrintWriter(output)) {
            int daysAmount = scanner.nextInt();

            List<Employee> result = employees.stream()
                    .filter(e -> e.getVacationDays() >= daysAmount)
                    .sorted(Comparator.comparing(Employee::getSurname))
                    .collect(Collectors.toList());

            if(result.size() > 0) {
                writer.print(result.get(0).getSurname() + ";" + result.get(0).getName() + ";" + result.get(0).getVacationDays());
            }
            for(int i = 1; i < result.size(); i++) {
                writer.print(System.lineSeparator() + result.get(i).getSurname() + ";" + result.get(i).getName() + ";" + result.get(i).getVacationDays());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve3(File output, List<Employee> employees) {
        try(PrintWriter writer = new PrintWriter(output)) {
            List<Employee> result = employees.stream()
                    .sorted((e1, e2) -> {
                        if(e2.getDays() > e1.getDays()) {
                            return 1;
                        } else if(e2.getDays() < e1.getDays()){
                            return -1;
                        } else {
                            return e1.getSurname().compareTo(e2.getSurname());
                        }
                    })
                    .collect(Collectors.toList());

            if(result.size() > 0) {
                writer.print(result.get(0).getSurname() + ";" + result.get(0).getName() + ";" + result.get(0).getDays());
            }
            for(int i = 1; i < result.size(); i++) {
                writer.print(System.lineSeparator() + result.get(i).getSurname() + ";" + result.get(i).getName() + ";" + result.get(i).getDays());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve4(File output, File input, List<Employee> employees) {
        try(Scanner scanner = new Scanner(input);
            PrintWriter writer = new PrintWriter(output)) {
            int employeeId = scanner.nextInt();

            List<Employee> result = employees.stream()
                    .filter(e -> e.getId() == employeeId)
                    .collect(Collectors.toList());

            if(result.size() > 0) {
                int currentDay = new GregorianCalendar().get(GregorianCalendar.DATE);
                int currentMonth = new GregorianCalendar().get(GregorianCalendar.MONTH);
                int currentYear = new GregorianCalendar().get(GregorianCalendar.YEAR);

                Employee employee = result.get(0);

                int day = employee.getEndDay();
                int month = employee.getEndMonth();
                int year = employee.getEndYear();

                int monthAmount = (year - currentYear) * 12 + month - currentMonth;

                if(currentDay > day) {
                    monthAmount--;
                }

                int daysAmount = employee.getVacationDays() - 2 * monthAmount;

                if(daysAmount >= 0) {
                    writer.print(daysAmount);
                }
            } else {
                writer.print(-1);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Employee> employees = readEmployees(new File("input1.txt"));
//        employees.forEach(System.out::println);

        List<Request> requests = readRequests(new File("input2.txt"));
//        requests.forEach(System.out::println);

        solve1(new File("output1.txt"), employees, requests);
        solve2(new File("output2.txt"), new File("input3.txt"), employees);
        solve3(new File("output3.txt"), employees);
        solve4(new File("output4.txt"), new File("input4.txt"), employees);
    }

}
