
% Gather statement statistics for a Tiny Imperative Language program
% Jim Cordy, October 2005

% Gathers and outputs statistics on the number of each kind of statement
% in a program.  Replaces the program itself with an empty one.

% Begin with the TIL base grammar
include "TIL.Grm"

% Compute and output statement kind statistics, replace program with an empty one.
% There are many different ways to do this - this naive way is simple and
% obvioulsy correct, but exposes TXL's need for generics.  
% Another less clear solution could use polymorphism to avoid the repetition.

function main
    replace [program]
        Program [program]

    % Count each kind of statement we're interested in
    % by extracting all of each kind from the program

    construct Statements [statement*]
        _ [^ Program]
    construct StatementCount [number]
        _ [length Statements] [putp "Total: %"]

    construct Declarations [declaration*]
        _ [^ Program]
    construct DeclarationsCount [number]
        _ [length Declarations] [putp "Declarations: %"]

    construct Assignments [assignment_statement*]
        _ [^ Program]
    construct AssignmentsCount [number]
        _ [length Assignments] [putp "Assignments: %"]

    construct Ifs [if_statement*]
        _ [^ Program]
    construct IfCount [number]
        _ [length Ifs] [putp "Ifs: %"]

    construct Whiles [while_statement*]
        _ [^ Program]
    construct WhileCount [number]
        _ [length Whiles] [putp "Whiles: %"]

    construct Fors [for_statement*]
        _ [^ Program]
    construct ForCount [number]
        _ [length Fors] [putp "Fors: %"]

    construct Reads [read_statement*]
        _ [^ Program]
    construct ReadCount [number]
        _ [length Reads] [putp "Reads: %"]

    construct Writes [write_statement*]
        _ [^ Program]
    construct WriteCount [number]
        _ [length Writes] [putp "Writes: %"]

    by
        % nothing
end function
