
# Generic rules
%.def : %.sdf
	pack-sdf -i $< -o $@

%.tbl : %.def
	sdf2table -i $< -o $@ -m $*

%.rtg : %.def
	sdf2rtg -i $< -o $@ -m $*

%.str : %.rtg
	rtg2sig -i $< -o $@

# ---

all : test eval

# --- simple expression language

EX=ultimate-question.txt

test : Exp.tbl
	parse-unit -i Exp.testsuite -p Exp.tbl

exp-ast : Exp.tbl
	sglri -p Exp.tbl -i $(EX) | pp-aterm > $@.txt
	open $@.txt

eval : Exp.tbl Exp.str ExpEval
	sglri -p Exp.tbl -i $(EX) | ./ExpEval

Exp.def : Exp.sdf
Exp.tbl : Exp.def
Exp.rtg : Exp.def
Exp.str : Exp.rtg

ExpEval : ExpEval.str
	strc -i ExpEval.str -la stratego-lib

# --- StraightLine parser

SL=StraightLine

$(SL).def : $(SL).sdf
$(SL).tbl : $(SL).def

testSL : $(SL).tbl
	parse-unit -i SLParse.testsuite -p $(SL).tbl

# ---

clean :
	rm -f *.def *.tbl *.rtg
	rm -f *.c *.o *.lo *.dep
	rm -f Exp.str exp-ast.txt ExpEval
	rm -f $(SL).str
