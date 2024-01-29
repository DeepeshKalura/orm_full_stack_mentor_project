package  com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.Scanner;




public class App {


    public static void main(String[] args) {
        String url = "jdbc:sqlite:sample.db";   
        
        try {
            
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            for(int i = 0; i<20; i++)
                System.err.print("*");
            
            System.err.print("Welcome to our expesne tracker");
            for(int i = 0; i<20; i++)
                System.err.print("*");
            System.out.println();
            try {

                statement.executeUpdate(createTable());
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            System.out.println("Enter 1 for the insert operation");
            System.out.println("Enter 2 for the Select operation");
            System.out.println("Enter 3 for the update operation");
            System.out.println("Enter 4 for the delete operation");
            System.out.println("Enter exit to stop");
            System.out.print("Enter your choice:      ");
            Scanner sc = new Scanner(System.in);
            String text = sc.nextLine();
            while(!text.equals("exit")){
                if (text.equals("2")) {
                    System.err.print("Enter the id for unqiue identy or negative number for all the value:    ");
                        int id = sc.nextInt(); 
                        ResultSet result = statement.executeQuery(selectValue(id));
                        while (result.next()) {
                            int expenseId = result.getInt("expenseId");
                            int amount = result.getInt("amount");
                            String message = result.getString("message");
                            Time created_at = result.getTime("created_at");
                            System.out.println("ExpenseId: " + expenseId + ", Amount: " + amount + ", Message: " + message + ", Created_at: " + created_at);
                        }

                } else if(text.equals("1")) {
                    
                   
                    System.err.print("Enter the id:    ");
                    int id = sc.nextInt(); 
                    sc.nextLine();
                    System.err.print("Enter the amount:    ");
                    int amount = sc.nextInt();
                    sc.nextLine();
                    System.err.print("Enter the message:    ");
                    String message = sc.nextLine();
                    String query = insertValues(id, message, amount);

                    System.err.println(query);
                    try {
                        statement.executeUpdate(query);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                } else if (text.equals("3")) {
                    int id = sc.nextInt(); 
                    int amount = sc.nextInt();
                    String message = sc.nextLine();

                    statement.executeQuery(updateValue(id, message, amount));

                } else if (text.equals("4")) {
                    int id = sc.nextInt(); 

                    statement.executeQuery(deleteValue(id));

                }else {
                    System.out.println("Please Enter 1, 2, 3, 4 for CRUD operation or exit to stop");
                }
                System.out.print("Please Continue with enter your choice:      ");
                text = sc.nextLine();

            }

            sc.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
    
    public static String insertValues(int id, String text, int amount){ 
        String insertQuery = String.format("Insert into Expense (expenseId, amount, message) VALUES (%d, %d, '%s')", id, amount, text);
        return insertQuery;
    }

    public static String updateValue(int id, String text, float amount) {
        String updateQuery = String.format("UPDATE Expense SET expenseId = %d, amount = %d, message = '%s' WHERE expenseId = %d", id, amount, text, id);
        return updateQuery;
    }

    public static String deleteValue(int id) {
        String deleteQuery = String.format("Delete from Expense where expenseId = %d", id);
        return deleteQuery;
    }

    public static String selectValue(int id) {
        if(id>0){
            return "Select * from Expense";
        }else {
            return String.format("Select * from Expense where exepenseId = %d" , id);
        }
    }

    public static String createTable(){
        String createTableQuery = "Create Table  IF NOT EXISTS Expense(expenseId integer primary key, amount integer NOT NULL, message TEXT, created_at DATETIME DEFAULT CURRENT_TIMESTAMP)";
        return createTableQuery;
    }

}
