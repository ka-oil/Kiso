package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class SyainTourokuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SyainTourokuServlet() {
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

			// アクセス元のHTMLでitemCdに設定された値を取得して、String型の変数itemCdに代入
			String addId = request.getParameter("addId");
			String addName = request.getParameter("addName");
			String addAge = request.getParameter("addAge");
			String addSeibetu = request.getParameter("addSeibetu");
			String addZyusyo = request.getParameter("addZyusyo");
			String addSyozoku = request.getParameter("addSyozoku");
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
			String sql ="insert into SK_SYAIN \n" +
					"(SYAIN_ID,SYAIN_NAME,SYAIN_AGE,SYAIN_SEIBETU,SYAIN_ZYUSYO,BUSYO_ID) \n" +
					"values  \n" +
					"('"+addId+"','"+addName+"','"+addAge+"','"+addSeibetu+"','"+addZyusyo+"','"+addSyozoku+"'); \n" +
					" commit;" ;



			// エラーが発生するかもしれない処理はtry-catchで囲みます
			// この場合はDBサーバへの接続に失敗する可能性があります
			try (
			// データベースへ接続します
			Connection con = DriverManager.getConnection(url, user, pass);
			// SQLの命令文を実行するための準備をおこないます
			Statement stmt = con.createStatement();
			// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
			ResultSet rs1 = stmt.executeQuery(sql);
			) {
			// SQL実行後の処理内容

				Syain syain = new Syain();
				while(rs1.next()){
					syain.setSyainId(rs1.getString("SYAIN_ID"));
					syain.setSyainName(rs1.getString("SYAIN_NAME"));
					syain.setSyainAge(rs1.getString("SYAIN_AGE"));
					syain.setSyainSeibetu(rs1.getString("SYAIN_SEIBETU"));
					syain.setSyainZyusyo(rs1.getString("SYAIN_ZYUSYO"));
					syain.setSyainSyozoku(rs1.getString("BUSYO_ID"));
				}
				// アクセスした人に応答するためのJSONを用意する
				PrintWriter pw = response.getWriter();
				pw.append(new ObjectMapper().writeValueAsString(syain));

			} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
			}



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
