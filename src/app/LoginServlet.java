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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け処理
		response.setContentType("text/html;charset=UTF-8");

		//ユーザー情報をセッションに保存
		//セッションを取得
		HttpSession session = request.getSession(true);
		//status変数にlogin情報を渡す
		String status = (String) session.getAttribute("login");
		//JSから値を取得
		String loginRequest = request.getParameter("loginRequest");
		// アクセスした人に応答するためのJSONを用意
		PrintWriter pw = response.getWriter();

		//ログインされているか判定
		//(status == null)→ログインしていない状態
		if(status == null) {
			if(loginRequest != null &&  loginRequest.equals("login")) {
				//セッションに値を保存する
				session.setAttribute("login", "ok");
				// JSONで出力する
				pw.append(new ObjectMapper().writeValueAsString("ログイン完了。"));
			}else {
				pw.append(new ObjectMapper().writeValueAsString("ログインして下さい。"));
			}
		}else {
			if (loginRequest != null && loginRequest.equals("logout")){
				session.removeAttribute("login");
				pw.append(new ObjectMapper().writeValueAsString("ログアウト完了。"));
			}else {
				pw.append(new ObjectMapper().writeValueAsString("ログイン済み"));
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
