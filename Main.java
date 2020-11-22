import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            while (DriverManager.getConnection("jdbc:mysql://localhost:3306/chmuraTest", "JRadomski", "root") != null){
                System.out.println("Lączenie z bazą, proszę czekać");
                TimeUnit.SECONDS.sleep(10);

            }

        }catch (SQLException | InterruptedException e) {

        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chmuraTest", "JRadomski", "root");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Students (ID int NOT NULL AUTO_INCREMENT, Imie varchar(255), Nazwisko varchar(255), Miasto varchar(255), Indeks integer(255), PRIMARY KEY (ID) );");
            String selectedOperation;
            do {
                System.out.println("1. Pokaż dane bazy\n2. Dodaj studenta\n3. Edytuj studenta\n4. Usuń studenta\nS. Naciśnij E by wyjść");
                selectedOperation = in.nextLine();
                switch (selectedOperation) {
                    case "1":
                        getResults(stmt);
                        break;
                    case "2":
                        insertStudent(stmt);
                        break;
                    case "3":
                        updateStudent(stmt);
                        break;
                    case "4":
                        deleteStudent(stmt);
                        break;
                }
            } while (!selectedOperation.toUpperCase().equals("E"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent(Statement stmt) throws SQLException {
        ResultSet rsss = stmt.executeQuery("SELECT ID, Imie, Nazwisko, Miasto, Indeks FROM Students");
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Podaj ID studenta, który chcesz usunąć");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Students WHERE ID= '" + id + "';";
        stmt.executeUpdate(deleteSql);
    }

    private static void updateStudent(Statement stmt) throws SQLException {
        String id;
        String imie;
        String nazwisko;
        String miaso;
        int indeks;
        String sql;

        ResultSet rss = stmt.executeQuery("SELECT ID, Imie, Nazwisko, Miasto, Indeks FROM Students");
        printOutHeader();

        printOutResult(rss);
        rss.close();
        System.out.println("Podaj ID studenta, którego chcesz edytować");
        id = in.nextLine();

        System.out.println("Imie: ");
        imie = in.nextLine();

        System.out.println("Nazwisko: ");
        nazwisko = in.nextLine();

        System.out.println("Miasto:");
        miaso = in.nextLine();

        System.out.println("Numer indeksu");
        indeks = in.nextInt();
        sql = " UPDATE Students SET Imie = '" + imie + "' , Nazwisko = '" + nazwisko + "', Miasto = '" + miaso + "',Indeks ='" + indeks + "' WHERE ID= '" + id + "';";
        stmt.executeUpdate(sql);
    }

    private static void insertStudent(Statement stmt) throws SQLException {


        System.out.println("Imie:");
        final String imie = in.nextLine();

        System.out.println("Nazwisko");
        final String nazwisko = in.nextLine();

        System.out.println("Miasto");
        final String miasto = in.nextLine();

        System.out.println("Numer indeksu");
        final int indeks = in.nextInt();

        String sql = " INSERT INTO Students (Imie, Nazwisko, Miasto, Indeks) VALUES ('" + imie + "', '" + nazwisko + "', '" + miasto + "','" + indeks + "')";
        stmt.executeUpdate(sql);
    }

    private static void getResults(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT ID, Imie, Nazwisko, Miasto, Indeks FROM Students");
        printOutHeader();
        printOutResult(rs);
        rs.close();
    }

    private static void printOutHeader() {
        System.out.println("ID    Imie    Nazwisko    Miasto    Indeks");
    }

    private static void printOutResult(ResultSet rs) throws SQLException {
        int id;
        String imie;
        String nazwisko;
        String miasto;
        int indeks;
        while (rs.next()) {
            id = rs.getInt("ID");
            imie = rs.getString("Imie");
            nazwisko = rs.getString("Nazwisko");
            miasto = rs.getString("Miasto");
            indeks = rs.getInt("Indeks");

            System.out.println(id + "    " + imie + "    " + nazwisko + "    " + miasto + "    " + indeks);
        }
    }
}
