import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Bug {
    private int id;
    private int page;
    private String title;
    private int priorityId;
    private int day;
    private int month;
    private int year;

    public Bug() {
    }

    public Bug(int id, int page, String title, int priorityId, int day, int month, int year) {
        this.id = id;
        this.page = page;
        this.title = title;
        this.priorityId = priorityId;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", page=" + page +
                ", title='" + title + '\'' +
                ", priorityId=" + priorityId +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}

class QA {
    private int id;
    private String surname;
    private String name;

    public QA() {
    }

    public QA(int id, String surname, String name) {
        this.id = id;
        this.surname = surname;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "QA{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

enum Priority {
    P1(1), P2(2), P3(3), P4(4);
    int weight;

    Priority(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Priority{" +
                this.name() +
                " weight=" + weight +
                '}';
    }
}

public class Task {
    public static List<Bug> readBugs(File file) {
        List<Bug> bugs = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                Bug bug = new Bug();
                bug.setId(Integer.parseInt(data[0]));
                bug.setPage(Integer.parseInt(data[1]));
                bug.setTitle(data[2]);
                bug.setPriorityId(Integer.parseInt(data[3]));
                String[] date = data[4].split("\\.");
                bug.setDay(Integer.parseInt(date[0]));
                bug.setMonth(Integer.parseInt(date[1]));
                bug.setYear(Integer.parseInt(date[2]));
                bugs.add(bug);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bugs;
    }

    public static List<QA> readQAs(File file) {
        List<QA> qas = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                QA qa = new QA();
                qa.setId(Integer.parseInt(data[0]));
                qa.setSurname(data[1]);
                qa.setName(data[2]);
                qas.add(qa);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return qas;
    }

    public static Map<Integer, Integer> readBugToQA(File file) {
        Map<Integer, Integer> bugToQA = new LinkedHashMap<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                bugToQA.put(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bugToQA;
    }

    public static Map<Integer, Priority> readPriorities(File file) {
        Map<Integer, Priority> priorities = new LinkedHashMap<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                priorities.put(Integer.parseInt(data[0]), Priority.valueOf(data[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return priorities;
    }

    public static void main(String[] args) {
        List<Bug> bugs = readBugs(new File("input1.txt"));
        bugs.forEach(System.out::println);

        List<QA> qas = readQAs(new File("input2.txt"));
        qas.forEach(System.out::println);

        Map<Integer, Integer> bugToQA = readBugToQA(new File("input3.txt"));
        bugToQA.forEach((i1, i2) -> System.out.println(i1 + " - " + i2));

        Map<Integer, Priority> priorities = readPriorities(new File("input4.txt"));
        priorities.forEach((i, p) -> System.out.println(i + " - " + p));
    }
}
