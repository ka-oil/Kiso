//編集ボタンを押すと、編集画面に移行するファンクション(画面遷移)

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
		busyoId : '0001'
	};
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/syainDataApp/BusyoServlet',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);

			// 取得したデータを画面に表示する
			var tableElemnt = '';
			for (var i = 0; i < json.length; i++) {
				var busyo = json[i];

				tableElemnt += '<tr> <td>' + busyo.busyoId + '</td><td>'
						+ busyo.busyoName + '</td>'
						//+ '<td><button class="busyo-hensyu" value = "' + busyo.busyoId + '">編集</button></td>'
						+ '<td><input type="button" id="'+ busyo.busyoId +'" value="編集" onclick="location.href=\'./Busyo_Hensyu.html?busyoId='+ busyo.busyoId +'\'"></td>'
						+ '<td><input type="button" id="'+ busyo.busyoId +'" value="削除" onclick="busyoSakuzyo(this.id)"></td></tr>';
			}
			// HTMLに挿入
			$('#busyo').append(tableElemnt);

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}


// 削除ボタンを押すと、レコードが削除されるファンクション(POST)
var busyoSakuzyo = function(id) {
	var inputBusyoId = id;

	console.log(id);

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
var tuika = function(){
	var inputBusyoId = $('#js-input-busyoId').val();
	var inputBusyoName = $('#js-input-busyoName').val();
	var requestQuery = {
			busyoId: inputBusyoId,
			busyoName: inputBusyoName,
		}
		console.log('入力値', requestQuery);

	$.ajax({
		type : 'POST',
		url : 'BusyoTourokuServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(data) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log( data+'を登録しました');
			// アラートを出す
			alert('新規登録を行いました');
			doReload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('登録できませんでした');
		}
	});
}

//画面リロードするメソッド
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

//	// 削除ボタンを押したときのイベント
//	$('.busyo-sakuzyo').click(function(){
//		$(this).busyoSakuzyo();
//
//		$(".busyo-sakuzyo").click(function(){
//			    $(this).addClass("delete");
//			});
//});
//	// 編集ボタンを押したときのイベント
//	$('#js-hensyu-button').click(busyoEdit);

	// 新規追加ボタンを押したときのイベント
	$('#tuika_button').click(tuika);
});