import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://Full2020_086292:3306/AGLINKA";

    static final String USER = "root";
    static final String PASS = "root";

    static Scanner in = new Scanner( System.in);
    private static final String CREATE_TABLE_CARS = "CREATE TABLE IF NOT EXISTS Cars (ID int, BRAND varchar(255), MODEL varchar(255), PRODUCTION_YEAR varchar(255), TYPE varchar(255) );";
    private static final String SELECT_ALL_FROM_CARS = "SELECT ID, BRAND, MODEL, PRODUCTION_YEAR, TYPE FROM Cars";

    public static void main(String[] args) {

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement()) {
            Class.forName("com.mysql.jdbc.Driver");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Connecting to database...");
            stmt.executeUpdate(CREATE_TABLE_CARS);
            String selectedOperation;
            do
            {
                System.out.println("1. Show DB data\n2. Insert car\n3. Edit car by ID\n4. Delete car by ID\nS. Press E to Exit");
                selectedOperation = in.nextLine();
                switch( selectedOperation )
                {
                    case "1" :
                        getResults(stmt);
                        break;
                    case "2" :
                        insertCar(stmt);
                        break;
                    case "3" :
                        updateCar(stmt);
                        break;
                    case "4" :
                        deleteCarById(stmt);
                        break;
                }
            }while (!selectedOperation.toUpperCase().equals("E"));
        } catch (InterruptedException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCarById(Statement stmt) throws SQLException {
        ResultSet rsss = stmt.executeQuery(SELECT_ALL_FROM_CARS);
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Enter ID to delete");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Cars WHERE ID= '"+id+"';";
        stmt.executeUpdate(deleteSql);
    }

    private static void updateCar(Statement stmt) throws SQLException {
        String type;
        String id;
        String brand;
        String model;
        String sql;
        String productionYear;
        ResultSet rss = stmt.executeQuery(SELECT_ALL_FROM_CARS);
        printOutHeader();

        printOutResult(rss);
        rss.close();
        System.out.println("Enter ID to edit");
        id = in.nextLine();

        System.out.println("Brand: ");
        brand = in.nextLine();

        System.out.println("Model: ");
        model = in.nextLine();

        System.out.println("Production year:");
        productionYear = in.nextLine();

        System.out.println("Vehicle type");
        type = in.nextLine();
        sql = " UPDATE Cars SET MODEL = '"+model+"' , BRAND = '"+brand+"', PRODUCTION_YEAR = '"+productionYear+"',TYPE ='"+type+"' WHERE ID= '"+id+"';";
        stmt.executeUpdate(sql);
    }

    private static void insertCar(Statement stmt) throws SQLException {
        System.out.println("ID");
        final String id = in.nextLine();

        System.out.println("Brand:");
        final String brand = in.nextLine();

        System.out.println("Model");
        final String model = in.nextLine();

        System.out.println("Production year:");
        final String productionYear = in.nextLine();

        System.out.println("Vehicle type");
        final String type = in.nextLine();
        
        String sql = " INSERT INTO Cars (ID, MODEL, BRAND, PRODUCTION_YEAR,TYPE) VALUES ('"+id+"', '"+model+"', '"+brand+"', '"+productionYear+"','"+type+"')";
        stmt.executeUpdate(sql);
    }

    private static void getResults(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(SELECT_ALL_FROM_CARS);
        printOutHeader();
        printOutResult(rs);
        rs.close();
    }

    private static void printOutHeader() {
        System.out.println("ID    BRAND    MODEL    PRODUCTION_YEAR    TYPE");
    }

    private static void printOutResult(ResultSet rs) throws SQLException {
        int id;
        String first;
        String last;
        String address;
        String city;
        while (rs.next()) {
            id = rs.getInt("ID");
            first = rs.getString("BRAND");
            last = rs.getString("MODEL");
            address = rs.getString("PRODUCTION_YEAR");
            city = rs.getString("TYPE");

            System.out.println(id + "    " + first + "    " + last + "    " + address + "    " + city);
        }
    }
}
