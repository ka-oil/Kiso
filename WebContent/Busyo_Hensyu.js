//function executeAjax() {
//	'use strict';
//
//	var requestQuery = {
//		busyoId : parameter
//	};
//	$.ajax({
//		type : 'GET',
//		dataType : 'json',
//		url : '/syainDataApp/EditServlet',
//		data : requestQuery,
//		success : function(json) {
//			// サーバーとの通信に成功した時の処理
//			// 確認のために返却値を出力
//			console.log('返却値', json);
//			// 取得したデータを画面に表示する
//			$('#hensyuBusyoId').html(json.busyoId);
//
//			// '<tr><th>部署ID</th>'
//			// +'<td id="hensyu_busyoId"></td>'
//			// </tr>
//			// <tr>
//			// <th>部署名</th>
//			// <td><input type="text" placeholder="変更後部署名"
//			// id="js-input-busyoName"></td>
//			// </tr>';
//
//			// HTMLに挿入
//			$('#busyoHensyu').append(tableElemnt);
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//			// サーバーとの通信に失敗した時の処理
//			alert('データの通信に失敗しました');
//			console.log(errorThrown)
//		}
//	});
//
//	// ---------------ここまで---------------
//
//}

var busyoEdit = function(parameter) {
	var inputBusyoId = parameter;
	// console.log(parameter);
	var inputBusyoName = $('#js-input-busyoName').val();
	console.log(inputBusyoName);

	var requestQuery = {
		"busyoId" : inputBusyoId,
		"busyoName" : inputBusyoName,
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

var busyoHensyu = function() {
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];

	// console.log(parameter);
//	$('#hensyuBusyoId').html(parameter);

	busyoEdit(parameter);
}

$(document).ready(function() {
	'use strict';

	$('#js-hensyu-button').click(busyoHensyu);

});