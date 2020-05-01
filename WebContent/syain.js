// AjaxでJSONを取得する
function executeAjax() {
	'use strict';
	//
	// // ?以降のパラメータを取得
	// // 今回で言うとhttp://localhost:8080/wt1/hobby.html?q=0001でいう0001が取得される
	// var parameter = location.search.substring( 1, location.search.length );
	// parameter = decodeURIComponent( parameter );
	// parameter = parameter.split('=')[1];
	//
	// --------------- TODO 編集ここから---------------
	var requestQuery = {
		syainId : $('#kensaku_input').val()
	};
	$
			.ajax({
				type : 'GET',
				dataType : 'json',
				url : '/syainDataApp/SyainServlet',
				data : requestQuery,
				success : function(json) {
					// サーバーとの通信に成功した時の処理
					// 確認のために返却値を出力
					console.log('返却値', json);

					// 取得したデータを画面に表示する
					var tableElemnt = '';
					for (var i = 0; i < json.length; i++) {
						var syain = json[i];
						tableElemnt += '<tr> <td>'
								+ syain.syainId
								+ '</td><td>'
								+ syain.syainName
								+ '</td>'
								+ '<td><input type="button" id="'+ syain.syainId +'" value="編集" onclick="location.href=\'./syainHensyu.html?syainId='+ syain.syainId +'\'"></td>'
								+ '<td><input type="button" id="'+ syain.syainId +'" value="削除" onclick="syainSakuzyo(this.id)"></td></tr>';
					}
					// HTMLに挿入
					$('#syain').append(tableElemnt);

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// サーバーとの通信に失敗した時の処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
				}
			});
}

// 削除ボタンを押すと、レコードが削除されるファンクション(POST)
var syainSakuzyo = function(id) {
	var inputSyainId = id;

	console.log(inputSyainId);

	var requestQuery = {
		busyoId : inputBusyoId,
	}
	console.log('入力値', requestQuery);

	$.ajax({
		type : 'POST',
		url : '/syainDataApp/DeleteServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(data) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log(data);
			// アラートを出す
			alert('削除しました');
			doReload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('削除できませんでした');
		}
	});
}

// tuika_buttonを押すと、情報を取得し、DBに登録
var tuika = function() {
	var inputSyainId = $('#js-input-syainId').val();
	var inputSyainName = $('#js-input-syainName').val();
	var inputSyainAge = $('#js-input-syainAge').val();
	var inputSyainSeibetu = $('#js-input-syainSeibetu').val();
	var inputSyainZyusyo = $('#js-input-syainZyusyo').val();
	var inputSyainSyozoku = $('#js-input-syainSyozoku').val();

	var requestQuery = {
		syainId : inputSyainId,
		syainName : inputSyainName,
		syainAge : inputSyainAge,
		syainSeibetu : inputSyainSeibetu,
		syainZyusyo : inputSyainZyusyo,
		syainSyozoku : inputSyainSyozoku,
	}
	console.log('入力値', requestQuery);

	$.ajax({
		type : 'POST',
		url : '/syainDataApp/AddServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(data) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log(data);
			// アラートを出す
			alert('新規登録を行いました');
			doReload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
		}
	});
}

// 画面リロードするメソッド
function doReload() {
	// reloadメソッドによりページをリロード
	window.location.reload();
}

$(document).ready(function() {
	'use strict';

	// 初期表示用
	executeAjax();

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click', executeAjax);

	// 新規登録ボタンを押したときのイベント
	$('#tuika_button').click(tuika);

});