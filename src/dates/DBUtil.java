package dates;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;



//connection to mysql
public class DBUtil {
    private static final String driver = "com.mysql.jdbc.Driver";
    //database:JDBCPractice
    private static final String url = "jdbc:mysql://localhost:3306/JDBCPractice";
    private static final String user = "root";
    private static final String passward = "MSRoot";
    private Connection conn = null;
    private Statement stmt = null;

    public void createConnection(){
        try{
            Class.forName(driver);
            System.out.println("Successfully loaded database driver!");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            //get connection
            conn = DriverManager.getConnection(url, user, passward);
            //get statement
            stmt = conn.createStatement();
            System.out.print("Successfully connected to the database!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ;
        }
    }
    public void insertData(String name, String password){
        try{
            String name_=new String(name.getBytes(),"utf8");//字节转码
            String password_=new String(password.getBytes(),"utf8");
            String sql="INSERT INTO user VALUES (null,'" + name_ + "','" + password_ +"')";
            System.out.print(sql);
            stmt.executeUpdate(sql);//更新语句
        }catch (SQLException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
    public void deleteData(int id){
        String sql="DELETE FROM user WHERE id = "+ id +"";
        System.out.print(sql);
        try{
            stmt.executeUpdate(sql);
            //System.out.println("一条记录被删除");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public int selectName(String name2bCheck){
        String sql="SELECT * FROM user where name = '" + name2bCheck + "'";
        try{
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                return rs.getInt("id");
            }else{
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean selectPassword(int id, String pw2bCheck){
        String sql="SELECT * FROM user where id = '" + id + "' and password = '" + pw2bCheck + "'";
        try{
            ResultSet rs=stmt.executeQuery(sql);//返回结果集
            if (rs.next()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
