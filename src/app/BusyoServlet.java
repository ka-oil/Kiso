package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class BusyoServlet
 */
@WebServlet("/BusyoServlet")
public class BusyoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusyoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け処理
		response.setContentType("text/html; charset=UTF-8");

		String busyoId = request.getParameter("busyoId");

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
		String sql = "select BUSYO_ID,BUSYO_NAME \n" + "from SK_BUSYO \n" + "where 1=1 \n" + "order by BUSYO_ID \n";

		List<Busyo> list = new ArrayList<>();

		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();
				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {
			// SQL実行後の処理内容

			HttpSession session = request.getSession();
			// セッションからユーザーコードを取得
			String status = (String) session.getAttribute("login");
			// セッションにユーザーが保存されてない(ログインしてない)、もしく䛿画面から送られてくるユーザー
			// コードと違う場合䛿エラー

			if (status == null) { // ログインしていない場合䛾処理
				// 処理終了
				return;
			} else {
				while (rs1.next()) {
					Busyo busyo = new Busyo();
					busyo.setBusyoId(rs1.getString("BUSYO_ID")); // 社員IDを変数empに代入
					busyo.setBusyoName(rs1.getString("BUSYO_NAME"));// SQL実行結果のsyainname列の値を取得し変数empに代入します
					list.add(busyo);
				}

				// アクセスした人に応答するためのJSONを用意する
				PrintWriter pw = response.getWriter();

				// JSONで出力する
				pw.append(new ObjectMapper().writeValueAsString(list));
			}
		} catch (

		Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字化け処理
		response.setContentType("text/html; charset=UTF-8");

		// 入力されたパラメータ(q)を取得
		String busyoId = request.getParameter("busyoId");

		// アクセスした人に応答するためのJSONを用意する
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
		String sql = "delete from SK_BUSYO \n" + "where BUSYO_ID = 'D01' \n" + "commit; \n";

		// DBへ接続してSQLを実行
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();
				// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {
			{
				// SQLの取得結果がある時（ユーザIDとパスワードが一致しているユーザーがいる）は「ok」という文字列を画面に返却
				// そうでないときは「ng」を返却
				// 返却データを作成
				Map<String, String> responseData = new HashMap<>();
				if (rs1.next()) {
					// ログインの結果
					responseData.put("result", "ok");
					// ユーザーコードとユーザー名（画面でユーザー名を表示したいため）
					responseData.put("userCd", rs1.getString("USER_CD"));
					responseData.put("userName", rs1.getString("USER_NAME"));
				} else {
					responseData.put("result", "ng");

				}
				pw.append(new ObjectMapper().writeValueAsString(responseData));
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);

		}
	}
}
