package app;

//ログイン情報を保持するクラス

public class User {
	public User(){
	}
//
//	//ログイン結果
//	private String result;
	//ログインユーザーID(社員IDと同様)
	private String userId;
	//ログインパスワード
	private String passWord;
	//ロール
	private String roll;


//	public String getResult() {
//		return result;
//	}
//	public void setResult(String result) {
//		this.result = result;
//	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
}
