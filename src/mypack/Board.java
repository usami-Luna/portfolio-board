package mypack;

public class Board {

	int id;
	String userID;
	String content;
	String bi;
	int cno;

	  /**
	  * @return id
	  */
	  public int getId() {
	    return id;
	  }

	  /**
	  * @param id セットする id
	  */
	  public void setId(int id) {
	    this.id = id;
	  }

	  /**
	  * @return name
	  */
	  public String getUserID() {
			return userID;
		}

		public void setUserID(String userID) {
			this.userID = userID;
		}


	  /**
	  * @return content
	  */
	  public String getContent() {
	    return content;
	  }

	  /**
	  * @param content セットする content
	  */
	  public void setContent(String content) {
	    this.content = content;
	  }



	public String getBi() {
		return bi;
	}

	public void setBi(String bi) {
		this.bi = bi;
	}

	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", name=" + userID + ", content=" + content + ", bi=" + bi + "]";
	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
