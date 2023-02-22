fn pow(a, b){
	var result = a;
	for(var i = 1; i < b; ++i){
		result = result * b;
	}
	return result;
}
