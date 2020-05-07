package app;

import java.io.IOException;
import java.io.PrintWriter;

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
		//文字化け処理
		response.setContentType("text/html;charset=UTF-8");

		//セッションの取得
		HttpSession session = request.getSession(true);
		//セッションキーからセッションバリューの取得、statusに代入
		String status = (String) session.getAttribute("login");
		//loginRequestからパラメーターを取得→login/logout
		String loginRequest = request.getParameter("loginRequest");
		//出力のためのJSONを用意
		PrintWriter pw = response.getWriter();

		//statusがnull→セッションバリューに何も入ってない→ログイン情報がない
		if (status == null) {
			if (loginRequest != null && loginRequest.equals("login")) {
				//セッションの要素を保存
				session.setAttribute("login", "ok");
				//結果を出力
				pw.append(new ObjectMapper().writeValueAsString("ログイン完了。"));
			} else {
				pw.append(new ObjectMapper().writeValueAsString("ログインして下さい。"));
			}
		} else {
			if (loginRequest != null && loginRequest.equals("logout")) {
				//"login"キーを削除→ログアウト状態へ
				session.removeAttribute("login");
				//結果を出力
				pw.append(new ObjectMapper().writeValueAsString("ログアウト完了。"));
			} else {
				pw.append(new ObjectMapper().writeValueAsString("ログイン済み"));
			}
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
