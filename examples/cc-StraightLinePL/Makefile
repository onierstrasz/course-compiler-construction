
# This should probably be done from an ant file, but this is simpler ...

# NB: jtb and javacc need to be locally installed
# http://javacc.org/
# http://compilers.cs.ucla.edu/jtb/

S = src

all : parser

parser :
	if test ! -d $S/parser; \
	then mkdir $S/parser; \
	fi
	cd $S; jtb slpl.jj
	cd $S; javacc -STATIC=FALSE jtb.out.jj
	cd $S; mv *.java parser
	if test ! -d out; \
	then mkdir out; \
	fi

loc :
	wc -l $S/slpl.jj $S/examples/*.java $S/interpreter/*.java $S/compiler/*.java > loc.txt
	echo "---" >> loc.txt
	wc -l $S/jtb.out.jj $S/parser/*java  $S/syntaxtree/*java $S/visitor/*java >> loc.txt

clean :
	rm -rf $S/jtb.out.jj
	rm -rf $S/parser
	rm -rf $S/syntaxtree
	rm -rf $S/visitor
	rm -rf bin
	rm -f out/*.class

# Test if the unprocessed parser spec is ok
test :
	cd $S; \
	mkdir TEST; \
	cp slpl.jj TEST; \
	cd TEST; \
	javacc slpl.jj; \
	cd ..; \
	rm -rf TEST

