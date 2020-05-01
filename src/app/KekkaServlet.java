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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class KekkaServlet
 */
@WebServlet("/KekkaServlet")
public class KekkaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KekkaServlet() {
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
		response.setContentType("text/html; charset=UTF-8");

		String busyoId = request.getParameter("busyoId");
		String syainId = request.getParameter("syainId");
		String syainName = request.getParameter("syainName");

//		console.log(busyoId);
//		console.log(syainId);
//		console.log(syainName);

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
		String sql = "select  \n" + "BUSYO_ID,SYAIN_ID,SYAIN_NAME \n" + "from SK_SYAIN \n" + "where 1=1 \n";
		if(!busyoId.equals("")){
			sql +="and BUSYO_ID = '"+busyoId+"' \n";
		}if(!syainId.equals("")){
			sql +=  "and SYAIN_ID = '"+syainId+"' \n";
		}if(!syainName.equals("")){
			sql += "and SYAIN_NAME like '%"+syainName+"%' \n";
		}
		sql += "order by SYAIN_ID \n";

		List<Syain> list = new ArrayList<>();

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
			// SQL実行結果を保持している変数rsから商品情報を取得
			// rs.nextは取得した商品情報表に次の行があるとき(取得結果があるとき)、trueになり、if文の中が実行される
			// 次の行がないときはfalseになり、実行されない

			while (rs1.next()) {
				Syain syain = new Syain();
				syain.setSyainSyozoku(rs1.getString("BUSYO_ID")); // syain型の変数syainに商品コードをセット
				syain.setSyainId(rs1.getString("SYAIN_ID"));// syain型の変数syainに商品名をセット
				syain.setSyainName(rs1.getString("SYAIN_NAME"));
				list.add(syain);
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
