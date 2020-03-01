package mypack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection con;

    private UserDAO() {
        super();
    }

    public UserDAO(DataBase database) {
        super();
        this.con = database.getCon();
    }

    // 認証のチェック User
    //引数で指定された「userID　と password」は、すでに、
    //ユーザ登録されているか？（DBに存在するか）？
    //引数：ユーザがキー入力した「ユーザID、パスワード」
    //戻り値：
    //　　true：引数で指定した
    //　　　　　「ユーザID、パスワード」が登録されていたことを意味します。
    //　　　　　　→ログイン処理成功を意味します。★
    //　　false：登録されていないことを意味します。
    //　　　　　　→ログイン処理失敗を意味します。★
    //注：DB-userテーブルのuserID列、password列ともに
    //  NOT NULL 属性、つまり、null をDBに格納できな列として定義されています。
    //このため、引数「userID　と password」のどちらか、または両方に
    // null が指定されて時、このメソッドは、 false を返します。
    public boolean userCertify(String userID, String password) throws SQLException {
        if(userID == null || password == null) return false;
        String sql = "select userID, password from user " +
                " where userID = ? AND password = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        // 入力パラメータに値をセットする ----
        stmt.setString(1, userID);
        stmt.setString(2, password);
        // SQL文を実行する
        ResultSet rs = stmt.executeQuery();
        boolean result = false;
        if (rs.next()) {
            result = true;
        } else {
            result = false;
        }
        rs.close();
        stmt.close();
        return result;
    }

    //新規ユーザの登録処理
    //引数：ユーザがキー入力した「ユーザID、パスワード」などを元に生成した
    //　　　　Userインスタンス
    //戻り値：
    //　　true：新規ユーザの登録、成功
    //　　false：新規ユーザの登録、失敗
    //　　　（ユーザがキー入力した「ユーザID」が既に登録済みで
    //　　　　　登録失敗）
    //注：DB-userテーブルのuserID列、password列ともに
    //  NOT NULL 属性、つまり、null をDBに格納できな列として定義されています。
    //このため、引数「Userインスタンス」の「userID　と password」のフィールドの
    //ちらか、または両方に
    // null が指定されて時、このメソッドは、 false を返します。
    public boolean addUser(User user) throws SQLException {
        if(user.getUserID() == null ||
           user.getPassword() == null) return false;
        String name;
        if(user.getName() == null) {
        	name = "名無し";
        } else {
        	name = user.getName();
        }
        String sql = "insert into user(userID, password,name,mailaddress) " +
                " values(?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            // 入力パラメータに値をセットする ----
            stmt.setString(1, user.getUserID());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, name);
            stmt.setString(4, user.getMailaddress());
            // SQL文を実行する
            stmt.executeUpdate();
            ///////stmt.close();
            return true;
        } catch (Exception e) {
            //「主キーの重複エラー」が発生したか？
            if(e instanceof SQLException  &&
                 ((SQLException)e).getSQLState() != null &&
                 ((SQLException)e).getSQLState().equals("23000") ) {
                return false;
            } else {
                throw e;
            }
        } finally {
            if(stmt != null) stmt.close();
        }
    }


    //Userオブジェクトの削除
    //戻り値：DB-DELETE処理された件数
    public int deleteUser(String userID) throws SQLException {
        String sql = "delete from user where userID = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        // 入力パラメータに値をセットする ----
        stmt.setString(1, userID);
        // SQL文を実行する
        int i1 = stmt.executeUpdate();
        stmt.close();
        return i1;
    }
    //Userオブジェクトの変更
    public boolean updateUser(User user) throws SQLException {
    	if(user.getUserID() == null ||
    	           user.getPassword() == null) return false;
        String sql = "UPDATE user SET password = ?, name = ?, mailaddress = ? WHERE userID = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        // 入力パラメータに値をセットする ----
        stmt.setString(1, user.getPassword());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getMailaddress());
        stmt.setString(4, user.getUserID());
        // SQL文を実行する
        stmt.executeUpdate();
        stmt.close();
        return true;
    }

    //UserDAOオブジェクトの取得（userID指定）
    //指定された注文が見つからなかったとき　null　が返る。
    public User getUser(String userID) throws SQLException {
        User result = null;
        String sql = "select userID, password, name, mailaddress from user " +
            " where userID = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        // 入力パラメータに値をセットする ----
        stmt.setString(1, userID);    // SQL文を実行する
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result = makeInstanceFromRow(rs);
        }
        rs.close();
        stmt.close();
        return result;
    }

    //Userオブジェクトの取得（全て）
    public List<User> getAllUsers() throws SQLException {
        String sql = "select userID, password, name, mailaddress from user ";
        //ここでは、Statementで十分で、
        //PreparedStatement不要ですが、
        //以下のメソッドを使用する関係で、PreparedStatementを使用します。
        //Statement stmt = con.createStatement();
        PreparedStatement stmt = con.prepareStatement(sql);
        List<User> list = queryAndMakeList(stmt);
        stmt.close();
        return list;
    }

    private List<User> queryAndMakeList(PreparedStatement stmt) throws SQLException {
        // SQL文を実行する
        ResultSet rs = stmt.executeQuery();
        List<User> list = new ArrayList<User>();
        while (rs.next()) {
            User result = makeInstanceFromRow(rs);
            list.add(result);
        }
        rs.close();
        return list;
    }

    private User makeInstanceFromRow(ResultSet rs) throws SQLException {
        User result = new User();
        result.setUserID(rs.getString("userID"));
        result.setPassword(rs.getString("password"));
        result.setName(rs.getString("name"));
        result.setMailaddress(rs.getString("mailaddress"));
        return result;
    }

    public static void main(String[] args) {
        //DB接続する。
        try ( DataBase database = new DataBase();  ) {
            UserDAO userDAO = database.getUserDAO();

            System.out.println("\"user01\", \"pass01\" のユーザが既に登録済みか？　"
                + userDAO.userCertify("user01", "pass01"));
            System.out.println("\"user01\", \"xxx\" のユーザが既に登録済みか？　" +
                userDAO.userCertify("user01", "xxx"));
            System.out.println("\"user04\", \"pass04\" のユーザが既に登録済みか？　" +
                userDAO.userCertify("user04", "pass04"));
            System.out.println("\"user01\", null のユーザが既に登録済みか？　" +
                    userDAO.userCertify("user01", null));
            System.out.println("null  , \"pass01\" のユーザが既に登録済みか？　" +
                    userDAO.userCertify( null , "pass01"));

            User user = new User();
            user.setUserID("user04");
            user.setPassword("pass04");
            System.out.println("\"user04\", \"pass04\" のユーザ登録は成功か？　" +
                userDAO.addUser(user));

            user.setUserID("user01");
            System.out.println("\"user01\" のユーザ登録は成功か？　" +
                userDAO.addUser(user));

            user.setUserID("user99");
            user.setPassword(null);
            System.out.println("\"user99\" でパスワード null のユーザ登録は成功か？　" +
                userDAO.addUser(user));

            user.setUserID(null);
            user.setPassword("pass99");
            System.out.println("ユーザID  null  でパスワード \"pass99\"  の" +
                "ユーザ登録は成功か？　" +
                userDAO.addUser(user));

            System.out.println("\"user04\"のユーザ削除処理の該当行数は（1 ならＯＫ） " +
                userDAO.deleteUser("user04"));

            System.out.println("\"user99\"のユーザ削除処理の該当行数は（0 なら該当ユーザが存在しないため\n" +
                "該当ユーザの削除が出来なかった） " +
                    userDAO.deleteUser("user99"));

            System.out.println("------------------------------");
            user = userDAO.getUser("user01");
            System.out.println("userDAO.getUser(\"user01\");");
            System.out.println(user);

            user = userDAO.getUser("userXXX");
            System.out.println("userDAO.getUser(\"userXXX\");");
            System.out.println(user);

            List<User> users = userDAO.getAllUsers();
            System.out.println("userDAO.getAllUser();");
            for (User user1 : users) {
                System.out.println(user1);
            }

            //
            System.out.println("\n--------------\n");


            User user2 = new User();
            user2.setUserID("user02");
            user2.setPassword("pass02123");
            user2.setName("3kaime");

            boolean addUser_success = false;


            addUser_success = userDAO.updateUser(user2);

            System.out.println(addUser_success);

            System.out.println(user2);

            for (User user1 : users) {
                System.out.println(user1);
            }

//            System.out.println("\"user04\"のユーザ削除処理の該当行数は（1 ならＯＫ） " +
//                    userDAO.updateUser(user2));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
/*
"user01", "pass01" のユーザが既に登録済みか？　true
"user01", "xxx" のユーザが既に登録済みか？　false
"user04", "pass04" のユーザが既に登録済みか？　false
"user01", null のユーザが既に登録済みか？　false
null  , "pass01" のユーザが既に登録済みか？　false
"user04", "pass04" のユーザ登録は成功か？　true
"user01" のユーザ登録は成功か？　false
"user99" でパスワード null のユーザ登録は成功か？　false
ユーザID  null  でパスワード "pass99"  のユーザ登録は成功か？　false
"user04"のユーザ削除処理の該当行数は（1 ならＯＫ） 1
"user99"のユーザ削除処理の該当行数は（0 なら該当ユーザが存在しないため
該当ユーザの削除が出来なかった） 0
------------------------------
userDAO.getAllUser();
User [userID=user01, password=pass01, name=伊藤]
User [userID=user02, password=pass02, name=佐藤]
User [userID=user03, password=pass03, name=石田]
*/

