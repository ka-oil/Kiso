
/* ログインファンクション */
function login() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
		userId : $('#js-login-id').val()
		,password:$('#js-login-pass').val()
	};
	// サーバーからデータを取得する
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/myCart/LoginServlet',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			/**
			 * 【localStorage01】
			 *  ・LoginServletは入力されたユーザーが存在する場合は「返却値のjson.result」に「ok」、しない場合は「ng」を返却してきます。
			 *  ・okの場合
			 *  	①「userName」「userCd」というキーでローカルストレージにユーザー名を保存しましょう。
			 *      （返却値のjson.userNameでユーザー名、json.userCdでユーザーコードが帰ってきます）
			 *  	②「itemList.html」に画面遷移しましょう。
			 *  ・ngの場合
			 *  	①「ユーザーIDかパスワードが間違っています」とアラートを出しましょう。
			 *  	  （JavaScriptのalertを使用）
			 **/
			/** localStorage01 実装ここから part1 **/
			if(json.result === "ok"){
				// ユーザー名をローカルストレージに保存
				localStorage.setItem('userName',json.userName);
				localStorage.setItem('userCd',json. userCd);
				// 画面遷移
				location.href='./itemList.html';
				}else{
				alert('ユーザーIDかパスワードが間違っています');
				}
			/** localStorage01 実装ここまで part1 **/
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}


/**
 * 読み込み時の動作
 */
$(document).ready(function() {

	// ログインボタンを押したときのイベント
	$('#js-login-button').click(login);


});