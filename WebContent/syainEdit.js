
var syainEdit = function(parameter) {
	var inputSyainId = parameter;
	// console.log(parameter);
	var inputSyainName = $('#js-input-busyoName').val();
	var inputSyainAge = $('#js-input-syainAge').val();
	var inputSyainSeibetu = $('#js-input-syainSeibetu').val();
	var inputSyainZyusyo = $('#js-input-syainZyusyo').val();
	var inputsyainSyozoku = $('#js-input-syainSyozoku').val();

	var requestQuery = {
		"syainId" : inputSyainId,
		"syainName" : inputSyainName,
		"syainAge" : inputSyainAge,
		"syainSeibetu" : inputSyainSeibetu,
		"syainZyusyo" : inputSyainZyusyo,
		"syainSyozoku" : inputsyainSyozoku,
	}
	console.log('入力値', requestQuery);
	// サーバーのレコードを削除
	$.ajax({
		type : 'POST',
		url : '/syainDataApp/SyainEditServlet',
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
			alert('変更を保存できませんでした');
		}
	});
}

var syainHensyu = function() {
	var parameter = location.search.substring(1, location.search.length);
	parameter = decodeURIComponent(parameter);
	parameter = parameter.split('=')[1];

	// console.log(parameter);
//	$('#hensyuBusyoId').html(parameter);

	syainEdit(parameter);
}

$(document).ready(function() {
	'use strict';

	$('#syain_hensyu_button').click(syainHensyu);

});