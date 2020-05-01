// AjaxでJSONを取得する
function executeAjax() {
	'use strict';

	var requestQuery = {
		syainId : $('#q').val()
	};
	console.log(requestQuery)

	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/syainDataApp/SyainKensaku',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);

			// 取得したデータを画面に表示する
			var selectElemnt = '';
			for (var i = 0; i < json.length; i++) {
				var busyo = json[i];
				selectElemnt += '<option value="' + busyo.busyoId + '">'
						+ busyo.busyoName + '</option>';

				// <option id="q" value="D00">選択してください</option>
				// </select>
				// <input type="text" placeholder="備考" id="description">
				// <input type="button" value="新規登録" id="touroku-button">
				// </form>
			}
			// HTMLに挿入
			$('#js-input-busyoId').append(selectElemnt);

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

var kensaku = function(inputBusyoId, inputSyainId, inputSyainName) {
	var busyoId = inputBusyoId;
	var syainId = inputSyainId;
	var syainName = inputSyainName;

	var requestQuery = {
		busyoId : inputBusyoId,
		syainId : inputSyainId,
		syainName : inputSyainName
	}
	console.log(requestQuery);

//	$.ajax({
//		type : 'GET',
//		dataType : 'json',
//		url : '/syainDataApp/SyainKensaku',
//		data : requestQuery,
//		success : function(json) {
//			// サーバーとの通信に成功した時の処理
//			// 確認のために返却値を出力
//			console.log('返却値', json);
//
//			// 取得したデータを画面に表示する
//			var selectElemnt = '';
//			for (var i = 0; i < json.length; i++) {
//				var busyo = json[i];
//				selectElemnt += '<option value="' + busyo.busyoId + '">'
//						+ busyo.busyoName + '</option>';
//
//				// <option id="q" value="D00">選択してください</option>
//				// </select>
//				// <input type="text" placeholder="備考" id="description">
//				// <input type="button" value="新規登録" id="touroku-button">
//				// </form>
//			}
//			// HTMLに挿入
//			$('#js-input-busyoId').append(selectElemnt);
//
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//			// サーバーとの通信に失敗した時の処理
//			alert('データの通信に失敗しました');
//			console.log(errorThrown)
//		}
//	});
}

$(document).ready(function() {
	'use strict';

	// 初期表示用
	executeAjax();

	// 更新ボタンにイベント設定
	$('#searchBtn').bind('click', executeAjax);

	$('#js-kensaku-button').click(function() {
		var inputBusyoId = $('#js-input-busyoId').val();
		var inputSyainId = $('#js-input-syainId').val();
		var inputSyainName = $('#js-input-syainName').val();
		console.log(inputBusyoId);
		console.log(inputSyainId);
		console.log(inputSyainName);
		kensaku(inputBusyoId, inputSyainId, inputSyainName);
	})
});