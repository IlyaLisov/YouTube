import javafx.geometry.Pos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

class Position {
    private int id;
    private String mainSkill;
    private String name;
    private Priority priority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", mainSkill='" + mainSkill + '\'' +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}

enum Priority {
    A(4), B(3), C(2), D(1);
    int weight;

    Priority(int weight) {
        this.weight = weight;
    }
}

class Recruiter {
    private int id;
    private String surname;
    private String name;

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
        return "Recruiter{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

class Bonus {
    private Priority priority;
    private int days;
    private int bonus;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Bonus{" +
                "priority=" + priority +
                ", days=" + days +
                ", bonus=" + bonus +
                '}';
    }
}

class ClosedPosition {
    private int id;
    private boolean isClosed = true;
    private GregorianCalendar dateOpened = new GregorianCalendar();
    private GregorianCalendar dateClosed = new GregorianCalendar();
    private int recruiterId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    public long getOpenedDate() {
        return dateOpened.getTimeInMillis();
    }

    public long getClosedDate() {
        return dateClosed.getTimeInMillis();
    }

    public void setOpenDate(int year, int month, int date) {
        dateOpened.set(year, month, date);
    }

    public void setClosedDate(int year, int month, int date) {
        dateClosed.set(year, month, date);
    }

    public String getDate() {
        return (dateOpened.get(GregorianCalendar.DATE) >= 10 ? dateOpened.get(GregorianCalendar.DATE) : "0" + dateOpened.get(GregorianCalendar.DATE)) + "."
                + (dateOpened.get(GregorianCalendar.MONTH) >= 10 ? dateOpened.get(GregorianCalendar.MONTH) : "0" + dateOpened.get(GregorianCalendar.MONTH)) + "."
                + dateOpened.get(GregorianCalendar.YEAR);
    }

    @Override
    public String toString() {
        return "ClosedPosition{" +
                "id=" + id +
                ", isClosed=" + isClosed +
                ", dateOpened=" + dateOpened +
                ", dateClosed=" + dateClosed +
                ", recruiterId=" + recruiterId +
                '}';
    }
}

public class Task {

    public static List<Position> readPositions(File file) {
        List<Position> positions = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                Position position = new Position();
                position.setId(Integer.parseInt(data[0]));
                position.setMainSkill(data[1]);
                position.setName(data[2]);
                Priority priority = Priority.valueOf(data[3]);
                position.setPriority(priority);
                positions.add(position);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return positions;
    }

    public static List<Recruiter> readRecruiters(File file) {
        List<Recruiter> recruiters = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                Recruiter recruiter = new Recruiter();
                recruiter.setId(Integer.parseInt(data[0]));
                recruiter.setSurname(data[1]);
                recruiter.setName(data[2]);
                recruiters.add(recruiter);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return recruiters;
    }

    public static List<Bonus> readBonuses(File file) {
        List<Bonus> bonuses = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                Bonus bonus = new Bonus();
                Priority priority = Priority.valueOf(data[0]);
                bonus.setPriority(priority);
                bonus.setDays(Integer.parseInt(data[1]));
                bonus.setBonus(Integer.parseInt(data[2]));
                bonuses.add(bonus);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bonuses;
    }

    public static List<ClosedPosition> readClosedPositions(File file) {
        List<ClosedPosition> closedPositions = new ArrayList<>();
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                ClosedPosition closedPosition = new ClosedPosition();
                closedPosition.setId(Integer.parseInt(data[0]));
                String[] date1 = data[1].split("\\.");
                closedPosition.setOpenDate(Integer.parseInt(date1[2]), Integer.parseInt(date1[1]), Integer.parseInt(date1[0]));
                String[] date2 = data[2].split("\\.");
                if(date2.length > 1) {
                    closedPosition.setClosedDate(Integer.parseInt(date2[2]), Integer.parseInt(date2[1]), Integer.parseInt(date2[0]));
                } else {
                    closedPosition.setClosed(false);
                }
                closedPosition.setRecruiterId(Integer.parseInt(data[3]));
                closedPositions.add(closedPosition);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return closedPositions;
    }

    public static void main(String[] args) {
        List<Position> positions = readPositions(new File("input1.txt"));
//        positions.forEach(System.out::println);

        List<Recruiter> recruiters = readRecruiters(new File("input2.txt"));
//        recruiters.forEach(System.out::println);

        List<Bonus> bonuses = readBonuses(new File("input3.txt"));
//        bonuses.forEach(System.out::println);

        List<ClosedPosition> closedPositions = readClosedPositions(new File("input4.txt"));
        //closedPositions.forEach(System.out::println);
    }

}
