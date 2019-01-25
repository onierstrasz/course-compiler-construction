EDITOR=subl

all: O0 O1 O2 
	$(EDITOR) mainO*.s main.c

O0:
	cc -S main.c -o mainO0.s 

O1:
	cc -S -O1 main.c -o mainO1.s 

O2:
	cc -S -O2 main.c -o mainO2.s 

clean:
	rm -f *.out *.s

