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
					//	+ '<td><input type="button" onclick="location.href='Busyo_Hensyu.html'" value="編集"></td>'
						+ '<td><button class="busyo-sakuzyo" value = "' + busyo.busyoId + '">削除</button></td></tr>'
//						+ '<td><input type="button" placehokder="削除" value=' + busyo.busyoId + ' onclick="busyoDelete(this)"></td></tr>';
//						$('#hobby_link').attr('href', 'Busyo_Hensyu.html?q=' + busyo.busyoId);
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


// location.href = "http://localhost:8080/syainDataApp/Busyo_Touroku.html";
// 編集ボタンを押すと、入力したデータが取得される

var edit = function(){
	var inputBusyoId = $('#js-edit-busyoId').val();
	var inputBusyoName = $('#js-edit-busyoName').val();

	var requestQuery = {
		busyoId: "inputBusyoId",
		busyoName: "inputBusyoName",
	}
	console.log('入力値', requestQuery);
	// サーバーのレコードを削除
	$.ajax({
		type : 'POST',
		url : '/syainDataApp/EditServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(data) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('編集後' + data);
			// アラートを出す
			alert('変更を保存しました');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
		}
	});
}

// 削除ボタンを押すと、レコードが削除されるファンクション(POST)
var busyoDelete = function() {
	var inputBusyoId = $('.busyo-sakuzyo').val();

	var requestQuery = {
			busyoId : inputBusyoId,
		}
		console.log('入力値', requestQuery);

	$.ajax({
		type : 'POST',
		url : '/DeleteServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(data) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log( data+'を削除しました');
			// アラートを出す
			alert('削除しました');
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
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('登録できませんでした');
		}
	});
}

$(document).ready(function() {
	'use strict';

	// 初期表示用
	executeAjax();

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click', executeAjax);

	// 削除ボタンを押したときのイベント
	$('.busyo-sakuzyo').click(function(){
		$(this).class(busyoDelete);
//
//		$(".busyo-sakuzyo").click(function(){
//			    $(this).addClass("delete");
//			});
});

	// 編集ボタンを押したときのイベント
	$('#button_hensyu').click(edit);

	// 新規追加ボタンを押したときのイベント
	$('#tuika_button').click(tuika);

});