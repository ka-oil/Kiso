
/* ログインファンクション */
function login(id) {
	// 入力されたユーザーIDとパスワード
	var imputUserId = $('#js-login-id').val();
	var imputPassWord = $('#js-login-pass').val();
	var imputLoginRequest = id;

	var requestQuery = {
		userId : imputUserId,
		password : imputPassWord,
		loginRequest : imputLoginRequest
	};

	// サーバーからデータを取得する
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/syainDataApp/LoginServlet',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			if(json.result === "ok"){
				alert("ログイン完了。");
				console.log(json);
			sessionStorage.setItem('userId',json.userId);
			sessionStorage.setItem('userName',json.userName);
			sessionStorage.setItem('roll',json.roll);
			}else{
				alert(json);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

//ログイン画面へ画面遷移させるメソッド
function loginPage(){
	window.location.href = './index.html';
}
/**
 * 読み込み時の動作
 */
$(document).ready(function() {

//	// ログインボタンを押したときのイベント
//	$('#js-login-button').click(login);
//
//	// ログインボタンを押したときのイベント
//	$('#js-logout-button').click(login);


});