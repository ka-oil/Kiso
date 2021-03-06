package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 文字化け処理
		response.setContentType("text/html;charset=UTF-8");

		// 入力されたユーザーIDとパスワードを取得
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		// 出力のためのJSONを用意
		PrintWriter pw = response.getWriter();

		// JDBCドライバの準備
		try {
			// JDBCドライバのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// ドライバが設定されていない場合はエラーになります
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}
		// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "webapp";
		String pass = "webapp";
		// 実行するSQL文
		String sql ="select ss.SYAIN_ID, ss.SYAIN_NAME, sl.SYAIN_ROLL \n" +
				"from SK_LOGIN sl, SK_SYAIN ss \n" +
				"where 1=1 \n" +
				"and ss.SYAIN_ID = '"+userId+"' \n" +
				"and sl.LOGIN_PASS = '"+password+"' \n";

		System.out.println(sql);

		// DBへ接続してSQLを実行
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);

				// SQLの命令文を実行するための準備をおこないます
				// Statement stmt = con.createStatement();
				Statement stmt = con.createStatement();

				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				// ResultSet rs1 = stmt.executeQuery(sql);
				ResultSet rs1 = stmt.executeQuery(sql);) {

			//マップを用意
			Map<String, String> userDeta = new HashMap<>();

//			if (rs1.next()) {

				// セッションの取得
				HttpSession session = request.getSession(true);
				// セッションキーからセッションバリューの取得、statusに代入
				String status = (String) session.getAttribute("login");
				// loginRequestからパラメーターを取得→login/logout
				String loginRequest = request.getParameter("loginRequest");
				{
					// statusがnull→セッションバリューに何も入ってない→ログイン情報がない
					if (status == null) {
						if (rs1.next() && loginRequest != null && loginRequest.equals("login")) {
							// セッションに要素を保存
							session.setAttribute("login", "ok");
							// 結果を出力
							userDeta.put("result", "ok");
							userDeta.put("userId", rs1.getString("SYAIN_ID"));
							userDeta.put("userName", rs1.getString("SYAIN_NAME"));
							userDeta.put("roll", rs1.getString("SYAIN_ROLL"));
							// JSONを出力
							pw.append(new ObjectMapper().writeValueAsString(userDeta));
							// pw.append(new
							// ObjectMapper().writeValueAsString("ログイン完了。"));
						} else {
							pw.append(new ObjectMapper().writeValueAsString("ログインしてください"));
						}
					} else {
						if (loginRequest != null && loginRequest.equals("logout")) {
							// "login"キーを削除→ログアウト状態へ
							session.removeAttribute("login");
							// 結果を出力
							pw.append(new ObjectMapper().writeValueAsString("ログアウト完了。"));
						} else {
							pw.append(new ObjectMapper().writeValueAsString("ログイン済み"));
						}
					}
				}
//			} else {
//				pw.append(new ObjectMapper().writeValueAsString("ログインIDとパスワードが間違っています"));
//			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}

		// private PreparedStatement createPreparedStatement(Connection con,
		// String userId, String password) throws SQLException {
		// System.out.println("userId="+userId);
		// System.out.println("password="+password);
		// // 実行するSQL文
		// String sql = "select \n" +
		// " MU.USER_CD \n" +
		// " ,MU.USER_NAME \n" +
		// "from \n" +
		// " MS_USER MU \n" +
		// "where \n" +
		// " 1=1 \n" +
		// " and MU.USER_CD =? " +
		// " and MU.PASSWORD=?";
		//
		// PreparedStatement stmt = con.prepareStatement(sql);
		// stmt.setString(1, userId);
		// stmt.setString(2, password);
		//
		//
		// return stmt;
		// }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// // 文字化け処理
		// response.setContentType("text/html; charset=UTF-8");
		//
		// // 入力されたユーザーIDとパスワードを取得
		// String userId = request.getParameter("userId");
		// String password = request.getParameter("password");
		//
		// // JDBCドライバの準備
		// try {
		// // JDBCドライバのロード
		// Class.forName("oracle.jdbc.driver.OracleDriver");
		// } catch (ClassNotFoundException e) {
		// // ドライバが設定されていない場合はエラーになります
		// throw new
		// RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]",
		// e.getMessage()), e);
		// }
		// // データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
		// String url = "jdbc:oracle:thin:@localhost:1521:XE";
		// String user = "webapp";
		// String pass = "webapp";
		// // 実行するSQL文
		// String sql = "select SYAIN_ID, LOGIN_PASS, TEMPORARY_PASS, SYAIN_ROLL
		// \n" + "from SK_LOGIN \n" + "where 1=1 \n"
		// + "and SYAIN_ID = '0001' \n" + "and LOGIN_PASS = '0001pw' \n";
		//
		// System.out.println(sql);
		//
		// // DBへ接続してSQLを実行
		// try (
		// // データベースへ接続します
		// Connection con = DriverManager.getConnection(url, user, pass);
		//
		// // SQLの命令文を実行するための準備をおこないます
		// // Statement stmt = con.createStatement();
		// Statement stmt = con.createStatement();
		//
		// // SQLの命令文を実行し、その結果をResultSet型のrsに代入します
		// // ResultSet rs1 = stmt.executeQuery(sql);
		// ResultSet rs1 = stmt.executeQuery(sql);) {
		// // SQL実行後の処理内容
		// {
		// // SQLの取得結果がある時（ユーザIDとパスワードが一致しているユーザーがいる）は「ok」という文字列を画面に返却
		// // そうでないときは「ng」を返却
		// // 返却データを作成
		// Map<String, String> responseData = new HashMap<>();
		// if (rs1.next()) {
		// // ログインの結果
		// responseData.put("result", "ok");
		// // ユーザーコードとユーザー名（画面でユーザー名を表示したいため）
		// responseData.put("userCd", rs1.getString("USER_CD"));
		// responseData.put("userName", rs1.getString("USER_NAME"));
		// } else {
		// responseData.put("result", "ng");
		//
		// }
		// // アクセスした人に応答するためのJSONを用意する
		// PrintWriter pw = response.getWriter();
		// pw.append(new ObjectMapper().writeValueAsString(responseData));
		// }
		// } catch (Exception e) {
		// throw new
		// RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]",
		// e.getMessage()), e);
		// }
	}
}
