package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SyainKensaku
 */
@WebServlet("/SyainKensaku")
public class SyainKensaku extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SyainKensaku() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		//
		// // アクセス元のHTMLでitemCdに設定された値を取得して、String型の変数itemCdに代入
		// String syainId = request.getParameter("syainId");
		//
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
					busyo.setBusyoId(rs1.getString("BUSYO_ID")); // syain型の変数syainに商品コードをセット
					busyo.setBusyoName(rs1.getString("BUSYO_NAME"));// syain型の変数syainに商品名をセット
					list.add(busyo);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}

		// アクセスした人に応答するためのJSONを用意する
		PrintWriter pw = response.getWriter();
		// JSONで出力する
		pw.append(new ObjectMapper().writeValueAsString(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
