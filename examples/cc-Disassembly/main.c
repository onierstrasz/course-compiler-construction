
// Declare a global variable that we will use later
const int coefficient = 30;

// Use two temporary variables to increment the value of the 
// argument with 41
int f(int a)
{
	int b,c;
	b = a+11;
	c = b+13;
	return c+17;
}

// two local variables and a call to f(int) and the global 
// coefficient to compute a secret result
int g(int a, int b) 
{
	int x, y;
	x = a * 5;
	y = b * 7;
	return f(x+y) - coefficient;
}


int main(int count, char ** args) {
	return g(2, 3);
	//return 0;
}