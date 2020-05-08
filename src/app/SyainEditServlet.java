package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SyainEditServlet
 */
@WebServlet("/SyainEditServlet")
public class SyainEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SyainEditServlet() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String syainId = request.getParameter("syainId");
		String syainName = request.getParameter("syainName");
		String syainAge = request.getParameter("syainAge");
		String syainSeibetu = request.getParameter("syainSeibetu");
		String syainZyusyo = request.getParameter("syainZyusyo");
		String syainSyozoku = request.getParameter("syainSyozoku");

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
		String sql = "update SK_SYAIN \n" + "set SYAIN_NAME = '" + syainName + "', SYAIN_AGE = '" + syainAge + "', \n"
				+ "SYAIN_SEIBETU = '" + syainSeibetu + "', SYAIN_ZYUSYO = '" + syainZyusyo + "', BUSYO_ID = '"
				+ syainSyozoku + "' \n" + "where SYAIN_ID = '" + syainId + "' \n";

		System.out.println(sql);

		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		// エラーが発生するかもしれない処理はtry-catchで囲みます
		// この場合はDBサーバへの接続に失敗する可能性があります
		try (
				// データベースへ接続します
				Connection con = DriverManager.getConnection(url, user, pass);
				// SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement();) {
			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
			int resultCount = stmt.executeUpdate(sql);
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

				// アクセスした人に応答するためのJSONを用意する
				PrintWriter pw = response.getWriter();
				// JSONで出力する
				pw.append(new ObjectMapper().writeValueAsString("ok"));
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}
	}
}