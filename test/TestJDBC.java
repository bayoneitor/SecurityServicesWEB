
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class TestJDBC {

    public static void main(String[] args) {
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SecurityServices?"
                    + "user=SecurityServices&password=linuxlinux");

            if (connect != null) {
                System.out.println("Dentro BD");
                statement = connect.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM clients");

                ResultSetMetaData mData = resultSet.getMetaData();
                int ncolumns = mData.getColumnCount();
                for(int i = 1; i <= ncolumns; i++){
                    System.out.print(mData.getColumnName(i)+"("+mData.getColumnTypeName(i)+")"+"\t\t");
                }  
                System.out.println(); 
                
                while(resultSet.next()){
                    for (int i = 1; i <= ncolumns; i++) {
                         System.out.print(resultSet.getString(i)+"\t\t\t");
                    }
                    System.out.println();
                }
            }
            /*
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from feedback.comments");
            writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect
            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
            .executeQuery("select * from feedback.comments");
            writeMetaData(resultSet);
             */

        } catch (Exception e) {
            System.out.println("Error BD:" + e);
        }
    }
}
