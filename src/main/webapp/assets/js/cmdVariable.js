// !!!!!!!!!!!!!!!!!!!!РАБОТА С ПЕРЕМЕННЫМИ
// массив переменных
//var variables = new Map();

// чтение переменных
function addVariable(vrbl) {
	console.log("Add variable " + vrbl["key"] );
	var val = variables.get(vrbl["key"]);
	
	if(val==undefined){
		if(vrbl["value"]==null && vrbl["add"]!=null)
			variables.set(vrbl["key"], vrbl["add"]);
		else if(vrbl["value"]!=null) 
			variables.set(vrbl["key"], vrbl["value"]);
		else variables.set(vrbl["key"], "0");
	}
	
	else if(vrbl["add"]!=null){
		if(!isNaN(Number.parseInt(val)))
			updateNumericVariable(vrbl["key"], vrbl["add"]);
		else updateNonNumericVariable(vrbl["key"], vrbl["add"]);
	}
	else if(vrbl["value"]!=null)
		variables.set(vrbl["key"], vrbl["value"]);
	else variables.set(vrbl["key"], 0);
}
// изменение переменной на заданное значение
function updateNumericVariable(key, value) {
	var val = variables.get(key);
	val = Number.parseInt(val); // Теперь будет числом
	variables.set(key, Number.parseInt(val) + Number.parseInt(value));
}
// изменение не числовых переменных
function updateNonNumericVariable(key, value) {
	variables.set(key, value);
}
