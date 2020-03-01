package mypack;
/* ---------------------------------------------------------------
 処理内容     : ドライバのロードとConnectionオブジェクトの生成
                 close処理）
 --------------------------------------------------------------- */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//JDBCテキスト「3.3-2」のDataBase.javaの処理を
//変形しています。
public class DataBase implements AutoCloseable{
    private Connection con;

    //以下の４個のDAOクラスの参照変数を定義します。
    private UserDAO      userDAO;
    private BoardDAO     boardDAO;
    private BoardtypeDAO     boardtypeDAO;
//    private OrderMainDAO orderMainDAO;
//    private OrderItemDAO orderItemDAO;
//    private ShosekiDAO   shosekiDAO;
    //上文のゲッターが、下の方に定義しています。▼■★

    //初期処理（ドライバのロードとConnectionオブジェクトの生成）
    public DataBase() throws ClassNotFoundException, SQLException {
        super();
        Class.forName("com.mysql.cj.jdbc.Driver");
        // データベースに接続する ----
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost/board_db?characterEncoding=UTF-8&serverTimezone=JST",
                "root", "Mysql@2019");
    }

    public void  setTranzactionON() throws SQLException {
        //トランザクション制御を行う
        if(con != null) con.setAutoCommit(false);
    }

    public void  setTranzactionOFF() throws SQLException {
        //トランザクション制御を解除
        if(con != null) con.setAutoCommit(true);
    }

    public void  commit() throws SQLException {
        //コミット
        //Connectionインスタンスが生成され、かつ
        //Connectionインスタンスがopen状態で、かつ
        //トランザクションモードか？
        if(con != null && !(con.isClosed())
                && con.getAutoCommit() == false) con.commit();  //★
    }

    public void  rollback() throws SQLException {
        //ロールバック
        //Connectionインスタンスが生成され、かつ
        //Connectionインスタンスがopen状態で、かつ
        //トランザクションモードか？
        if(con != null && !(con.isClosed())
                && con.getAutoCommit() == false) con.rollback();  //★
    }

    /*
      以下の４個の getXxxxDAO()メソッドを
    マルチスレッド下で実行する場合は、
    その対策が必要ですが、
    「１つのブラウザ」でショッピングサイトを
    起動しているときは、マルチスレッド下で
    実行しませんので、今回は、
    マルチスレッド下での対策を実施していません。
    なお、マルチスレッドに関しては、
    Javaテキスト１６章に記載されています。
    */
    public UserDAO getUserDAO() {
        if(userDAO == null ) {
            userDAO = new UserDAO(this);
        }
        return userDAO;
    }
    public BoardDAO getBoardDAO() {
        if(boardDAO == null ) {
        	boardDAO = new BoardDAO(this);
        }
        return boardDAO;
    }
    public BoardtypeDAO getBoardtypeDAO() {
        if(boardtypeDAO == null ) {
        	boardtypeDAO = new BoardtypeDAO(this);
        }
        return boardtypeDAO;
    }

//    public OrderMainDAO getOrderMainDAO() {
//        if(orderMainDAO == null ) {
//            orderMainDAO = new OrderMainDAO(this);
//        }
//        return orderMainDAO;
//    }
//
//    public OrderItemDAO getOrderItemDAO() {
//        if(orderItemDAO == null ) {
//            orderItemDAO = new OrderItemDAO(this);
//        }
//        return orderItemDAO;
//    }

//    public ShosekiDAO getShosekiDAO() {
//        if(shosekiDAO == null ) {
//            shosekiDAO = new ShosekiDAO(this);
//        }
//        return shosekiDAO;
//    }

    public Connection getCon() {
        return con;
    }

    //終了処理（Connectionのクローズ）
    @Override
    public void close() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
        }
    }
}
