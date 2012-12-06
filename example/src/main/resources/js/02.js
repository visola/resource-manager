var x = 10;
var f = 'File 02.js';

var d = function () {
	console.log('Teste');
};

(function () {
	
	var variableVeryLong = 'This is a pen.';
	variableVeryLong += ' Hello World!';
	console.log(variableVeryLong);
	
	return variableVeryLong;
})();