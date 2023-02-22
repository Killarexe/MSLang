fn fib(input){
	if(input <= 1){
		return input;
	}
	return fib(input - 1) + fib(input - 2);
}
