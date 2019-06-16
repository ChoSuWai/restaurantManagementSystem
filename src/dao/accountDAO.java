package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import database.Database;

public class accountDAO {

	public String[] getAccount() throws SQLException {
		Connection conn = Database.getInstance().getConnection();
        String sql = "select * from account";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        //List list = new ArrayList();
        String[] accList=new String[4];
        while(result.next()){
            int id = result.getInt("acc_id");
            String name = result.getString("name");
            String password = result.getString("password");
            String role = result.getString("role");
            //Category category = new Category(id, name);
            //for(int i=0;i<accList.length;i++) {
            	//accList[0]=String.valueOf(id);
            	accList[0]=name;
            	accList[1]=password;
            	accList[2]=role;
            //}
        }
        return accList;
	}
}
