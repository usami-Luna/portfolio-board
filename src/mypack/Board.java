package mypack;

public class Board {

	int id;
	String userID;
	String content;
	String bi;
	int cno;
	String gazou;

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



	public String getGazou() {
		return gazou;
	}

	public void setGazou(String gazou) {
		this.gazou = gazou;
	}



	@Override
	public String toString() {
		return "Board [id=" + id + ", userID=" + userID + ", content=" + content + ", bi=" + bi + ", cno=" + cno
				+ ", gazou=" + gazou + "]";
	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
