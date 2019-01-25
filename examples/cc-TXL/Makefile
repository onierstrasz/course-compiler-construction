
all : parse stats run trace

parse :
	txl factors.til TILparser.Txl

stats :
	txl factors.til TILstats.Txl

run :
	txl factors.til

trace :
	txl factors.til TILtrace.Txl > trace.til
	txl trace.til

test :
	txl Ultimate.Question
