var PI = 3.141592653589793;

define void fn;

fn pow(a, b){
	var result = a;
	for(var i = 1; i < b; ++i){
		result = result * b;
	}
	return result;
}

fn degToRad(x){
	return x * (PI / 180);
}

fn cos(x){
	var result = 1;
	var term = 1;
	var sign = -1;
	for(var i = 2; i < 20; i += 2){
		term *= x * x / (i * (i - 1));
		result += sign * term;
		sign *= -1;
	}
	return result;
}

fn sin(x){
	var result = x;
	var term = x;
	var sign = -1;
	for(var i = 3; i < 20; i += 2){
		term *= -1 * x * x / (i * (i - 1));
		result += sign * term;
		sign *= -1;
	}
	return result;
}

fn tan(x){
	return sin(x) / cos(x);
}

fn fac(x){
	var result = 1;
	for(var i = 1; i <= x; ++i){
		result *= i;
	}
	return result;
}

fn sqrt(x){
	var guess = 1;
	var prev_guess = 0;
	while(guess != prev_guess){
		prev_guess = guess;
		guess = 0.5 * (guess + x / guess);
	}
	return guess;
}
