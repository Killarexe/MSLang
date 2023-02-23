include "exemples/math.ms";

var a = parse_number(input("Enter a: "));
var b = parse_number(input("Enter b: "));

print("cos(a): " + cos(degToRad(a)));
print("cos(b): " + cos(degToRad(b)));
print("sin(a): " + sin(degToRad(a)));
print("sin(b): " + sin(degToRad(b)));
print("tan(a): " + tan(degToRad(a)));
print("tan(b): " + tan(degToRad(b)));
print("sqrt(a): " + sqrt(a));
print("sqrt(b): " + sqrt(b));
print("a!: " + fac(a));
print("b!: " + fac(b));
print("!a: " + !a);
print("!b: " + !b);
print("a + b: " + (a + b));
print("a - b: " + (a - b));
print("a * b: " + (a * b));
print("a / b: " + (a / b));
print("a pow b: " + pow(a, b));
print("a & b: " + (a & b));
print("a | b: " + (a | b));
print("a ^ b: " + (a ^ b));
print("a % b: " + (a % b));
print("a << b: " + (a << b));
print("a >> b: " + (a >> b));
