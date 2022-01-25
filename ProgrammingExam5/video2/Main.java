import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

class Employee {
    private int id;
    private String surname;
    private String name;
    private String skill;
    private int workLoadSum;

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

    public int getWorkLoadSum() {
        return workLoadSum;
    }

    public void setWorkLoadSum(int workLoadSum) {
        this.workLoadSum = workLoadSum;
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
    private int projectId;
    private int positionId;
    private GregorianCalendar openDate;

    public OpenedPosition() {
        openDate = new GregorianCalendar();
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setOpenDate(int year, int monthId, int day) {
        openDate.set(year, monthId, day);
    }

    public long getOpenDateInMillis() {
        return openDate.getTimeInMillis();
    }

    @Override
    public String toString() {
        return "OpenedPosition{" +
                "projectId=" + projectId +
                ", positionId=" + positionId +
                ", openDate=" + openDate.get(GregorianCalendar.DATE) + "." + (openDate.get(GregorianCalendar.MONTH) + 1) + "." + openDate.get(GregorianCalendar.YEAR) +
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
                position.setWorkload(Integer.parseInt(data[3]));
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
                openedPosition.setProjectId(Integer.parseInt(data[0]));
                openedPosition.setPositionId(Integer.parseInt(data[1]));
                String[] date = data[2].split("\\.");
                openedPosition.setOpenDate(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1,Integer.parseInt(date[0]));
                openedPositions.add(openedPosition);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return openedPositions;
    }

    public static void solve1(File output, List<Employee> employees, List<Position> positions) {
        try(PrintWriter writer = new PrintWriter(output)) {
            List<Employee> result = employees.stream()
                    .filter(e -> {
                        int workloadSum = positions.stream()
                                .filter(p -> p.getEmployeeId() == e.getId())
                                .mapToInt(Position::getWorkload).sum();
                        e.setWorkLoadSum(workloadSum);
                        return workloadSum > 100;
                    })
                    .collect(Collectors.toList());


            if(result.size() > 0) {
                writer.print(result.get(0).getId() + ";" + result.get(0).getSurname() + ";" + result.get(0).getName() + ";" + result.get(0).getWorkLoadSum());
            }
            for(int i = 1; i < result.size(); i++) {
                writer.print(System.lineSeparator() + result.get(i).getId() + ";" + result.get(i).getSurname() + ";" + result.get(i).getName() + ";" + result.get(i).getWorkLoadSum());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve2(File output, File input, List<Employee> employees, List<Position> positions) {
        try(Scanner scanner = new Scanner(input);
            PrintWriter writer = new PrintWriter(output)) {
            int projectId = scanner.nextInt();

            List<Employee> result = employees.stream()
                    .filter(r -> positions.stream()
                            .filter(p -> p.getProjectId() == projectId).anyMatch(p -> p.getEmployeeId() == r.getId()))
                    .sorted(Comparator.comparing(Employee::getId))
                    .collect(Collectors.toList());

            if(result.size() > 0) {
                writer.print(result.get(0).getId() + ";" + result.get(0).getSurname() + ";" + result.get(0).getName());
            }
            for(int i = 1; i < result.size(); i++) {
                writer.print(System.lineSeparator() + result.get(i).getId() + ";" + result.get(i).getSurname() + ";" + result.get(i).getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void solve3(File output, File inputDates, File inputProjectId, List<Project> projects, List<OpenedPosition> openedPositions) {
        try(Scanner scannerDates = new Scanner(inputDates);
            Scanner scannerProjectId = new Scanner(inputProjectId);
            PrintWriter writer = new PrintWriter(output)) {
            String[] dates = scannerDates.nextLine().split(";");
            int projectId = scannerProjectId.nextInt();

            String[] date1String = dates[0].split("\\.");
            String[] date2String = dates[1].split("\\.");

            Project project = projects.stream()
                    .filter(p -> p.getId() == projectId)
                    .collect(Collectors.toList())
                    .get(0);

            GregorianCalendar date1 = getDateFromString(date1String);
            GregorianCalendar date2 = getDateFromString(date2String);

            long date1InMillis = date1.getTimeInMillis();
            long date2InMillis = date2.getTimeInMillis();

            List<OpenedPosition> result = openedPositions.stream()
                    .filter(p -> p.getProjectId() == projectId && (p.getOpenDateInMillis() > date1InMillis && p.getOpenDateInMillis() < date2InMillis))
                    .collect(Collectors.toList());

            writer.print(project.getTitle() + ";" + result.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static GregorianCalendar getDateFromString(String[] dates) {
        GregorianCalendar date = new GregorianCalendar();
        date.set(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[0]));

        return date;
    }

    public static void solve4(File output, File input, List<Employee> employees, List<Position> positions) {
        try(Scanner scanner = new Scanner(input);
            PrintWriter writer = new PrintWriter(output)) {
            List<Employee> readedEmployees = new ArrayList<>();
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");

                Employee employee = new Employee();
                employee.setSkill(data[0]);
                employee.setWorkLoadSum(Integer.parseInt(data[1]));

                readedEmployees.add(employee);
            }

            List<Employee> result = readedEmployees.stream()
                    .filter(readedEmployee -> {
                        List<Employee> availableSkillEmployee = employees.stream().filter(e -> e.getSkill().equals(readedEmployee.getSkill())).collect(Collectors.toList());

                        List<Position> availablePositions =
                                positions.stream()
                                        .filter(position -> position.getBillingType().equals("non-billable"))
                                        .filter(position -> availableSkillEmployee.stream().anyMatch(e -> position.getEmployeeId() == e.getId()))
                                        .collect(Collectors.toList());

                        int workLoad = availablePositions.stream()
                                .mapToInt(Position::getWorkload)
                                .sum();

                        return !(workLoad >= readedEmployee.getWorkLoadSum());
                    })
                    .collect(Collectors.toList());

            if(result.size() == 0) {
                writer.print("Yes");
            } else {
                writer.print("No");
                for(Employee employee : result) {
                    writer.print(System.lineSeparator() + employee.getSkill() + ";" + employee.getWorkLoadSum());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Employee> employees = readEmployees(new File("input1.txt"));
//        employees.forEach(System.out::println);

        List<Project> projects = readProjects(new File("input2.txt"));
//        projects.forEach(System.out::println);

        List<Position> positions = readPositions(new File("input3.txt"));
//        positions.forEach(System.out::println);

        List<OpenedPosition> openedPositions = readOpenedPositions(new File("input4.txt"));
        openedPositions.forEach(System.out::println);

        solve1(new File("output1.txt"), employees, positions);
        solve2(new File("output2.txt"), new File("input8.txt"), employees, positions);
        solve3(new File("output3.txt"), new File("input6.txt"), new File("input7.txt"), projects, openedPositions);
        solve4(new File("output4.txt"), new File("input5.txt"), employees, positions);
    }
}
