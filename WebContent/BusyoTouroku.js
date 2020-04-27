// tuika_buttonを押すと、情報を取得し、DBに登録
var tuika = function(){
	var inputBusyoId = $('#js-input-busyoId').val();
	var inputBusyoName = $('#js-input-busyoName').val();
	var requestQuery = {
			busyoId: "inputBusyoId",
			busyoName: "inputBusyoName",
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
			//アラートを出す
			alert('新規登録を行いました');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
		}
	});
}

$(document).ready(function() {
	'use strict';

	//新規追加ボタンを押したときのイベント
	$('#tuika_button').click(tuika);

});