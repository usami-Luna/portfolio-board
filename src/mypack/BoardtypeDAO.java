package mypack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardtypeDAO {

	private Connection con;

	private BoardtypeDAO() {
        super();
    }

    public BoardtypeDAO(DataBase database) {
        super();
        this.con = database.getCon();
    }
  //オブジェクトの取得（全行）
    public List<Boardtype> selectAllBoardtype() throws SQLException {
        String sql = "select * from boardtype";
        PreparedStatement stmt = con.prepareStatement(sql);
        List<Boardtype> list = queryAndMakeList(stmt);
        stmt.close();
        return list;
    }
    private List<Boardtype> queryAndMakeList(PreparedStatement stmt) throws SQLException {
        // SQL文を実行する
        ResultSet rs = stmt.executeQuery();
        List<Boardtype> list = new ArrayList<Boardtype>();
        while (rs.next()) {
        	Boardtype result = makeInstanceFromRow(rs);
            list.add(result);
        }
        rs.close();
        return list;
    }
    //noからタイトル取得
    public Boardtype getBoardtype(int cno) throws SQLException {
    	Boardtype result = null;
        String sql = "select cno, title, details, userID from boardtype " +
            " where cno = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        // 入力パラメータに値をセットする ----
        stmt.setInt(1, cno);    // SQL文を実行する
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result = makeInstanceFromRow(rs);
        }
        rs.close();
        stmt.close();
        return result;
    }

    private Boardtype makeInstanceFromRow(ResultSet rs) throws SQLException {


    	Boardtype result = new Boardtype();
        result.setCno(rs.getInt("cno"));
        result.setTitle(rs.getString("title"));
        result.setDetails(rs.getString("details"));
        result.setUserID(rs.getString("userID"));
        return result;
    }
  //1件登録用メソッド
    public void insertBoardtype(Boardtype boardtype) throws SQLException {

    	String sql = "insert into boardtype ( title, details , userID)  " +
                " values( ?, ?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        // 入力パラメータに値をセットする ----
//        stmt.setInt(1, boardtype.getCno());
        stmt.setString(1, boardtype.getTitle());
        stmt.setString(2, boardtype.getDetails());
        stmt.setString(3, boardtype.getUserID());
        // SQL文を実行する
        stmt.executeUpdate();
        stmt.close();
	}

	public static void main(String[] args) {
		try ( DataBase database = new DataBase();  ) {
    		BoardtypeDAO boardtypeDAO = database.getBoardtypeDAO();

    		Boardtype boardtype = new Boardtype();
    		boardtype.setTitle("java");
    		boardtype.setDetails("インスタント、フィールドについて");
    		boardtype.setUserID("aa");

    		//1件追加
    		boardtypeDAO.insertBoardtype(boardtype);

    		List<Boardtype> users = boardtypeDAO.selectAllBoardtype();
            System.out.println(users);

    		for (Boardtype board1 : users) {
                System.out.println(board1);
                System.out.println(board1.getCno());
                System.out.println(board1.getTitle());
                System.out.println(board1.getDetails());
                System.out.println(board1.getUserID());
            }

    		System.out.println("test");
    		System.out.println(boardtypeDAO.getBoardtype(1).getTitle());

//    		System.out.println("test");
//    		System.out.println();
//    		System.out.println();
//    		for (int i = 0; i < users.size(); i++) {
//    			System.out.println(users.get(i).getUserID
//    					());
//    			System.out.println(users.get(i).getContent());
//    			System.out.println(users.get(i).getBi());
//			}


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
