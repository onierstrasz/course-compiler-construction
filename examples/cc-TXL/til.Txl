
% Tiny Imperative Language interpreter
% Jim Cordy, August 2005

% This program is a simple interpreter that directly executes TIL programs
% by transforming them to their meaning statement-by-statement.

% Some details not well defined in the TIL grammar are assumed:
%
% - It is not clear whether TIL is scoped or not.  This interpreter assumes
%   all declarations are in one scope.  The change to traditional nested 
%   scoping, if required, is trivial.
%
% - The comment for the "for" statement in the grammar says it is a 
%   "declaring for".  We assume this means the for automatically declares 
%   its control variable, with the same scope as a variable declaration.
%
% - As a consequence of the above, it is assumed that redeclaration is not
%   an error, rather simply uninitializes the variable.
%
% - It is not clear whether TIL write statements output a whole line or a 
%   partial line.  We assume whole.
%
% All these decisions are easy to change.

% Begin with the TIL base grammar, with priority parse
#define PRIORITY
include "TIL.Grm"

% Marker for detecting uninterpretable statements
redefine statement
        ...
    |  'null;
end redefine

% VM (virtual machine) memory cells 
define memory_cell
    [id] [literal]
end define

% Execute the program statement by statement
function main
    % The VM memory
    export Memory [memory_cell*]
        _ % Initially empty
    replace [program]
        Statements [statement*]
    by
        Statements [executeStatements]
end function

% Consume each statement we execute
rule executeStatements
    replace [statement*]
        S [statement]
        Rest [statement*]
    construct Execution [statement]
        S [executeStatement]
    by
        Rest 
end rule

% It must be one of the statement forms we know (all of those in TIL).
% Each statement interpreted is transformed into the special statement 
% "null;" so that we can tell when interpretation of a statement fails 
% for some reason and give an error message
function executeStatement
    replace [statement]
        S [statement]
    by
        S [executeDeclaration]
          [executeAssignment]
          [executeIf]
          [executeWhile]
          [executeFor]
          [executeRead]
          [executeWrite]
          [failure]
end function

% If the statement was not transformed to "null;" by one of the 
% statement interpretations, then we were unable to interpret it 
% so give a message and halt
function failure
    match [statement]
        S [statement]
    deconstruct not S
        'null;
    construct ErrorMessage [stringlit]
        _ [+ "*** Error: unable to interpret statement: '"]
          [quote S] [+ "'"] 
          [print] 
          [quit 101]
end function

% Interpret a declaration statement by adding an entry for the variable 
% to the VM memory.  If a previous one of the same name is already in 
% the memory, remove it
function executeDeclaration
    replace [statement]
        'var Id [id] ;
    import Memory [memory_cell*]
    export Memory
        Id "--UNDEFINED--"
        Memory [removeOldDeclaration Id]
    by
        'null;
end function

function removeOldDeclaration Id [id]
    replace * [memory_cell*]
        Id _ [literal]
        RestOfMemory [memory_cell*]
    by
        RestOfMemory
end function

% Interpret an assignment statement by evaluating the expression and
% setting the VM memory cell for the variable to that value
function executeAssignment
    replace [statement]
        Id [id] := Expn [expression] ;
    construct ExpnValue [expression]
        Expn [evaluateExpression]
    deconstruct ExpnValue
        Value [literal]
    import Memory [memory_cell*]
    export Memory
        Memory [checkDefined Id]
               [setValue Id Value]
    by
        'null;
end function

% Interpret an if statement by evaluating the expression and
% executing the appropriate branch
function executeIf
    replace [statement]
        'if Expn [expression] 'then
            S [statement*]
        OptElse [opt else_statement]
        'end
    construct ExpnValue [expression]
        Expn [evaluateExpression]
    deconstruct ExpnValue
        Value [literal]
    construct TruePart [statement*]
        S [executeThen Value]
    construct FalsePart [opt else_statement]
        OptElse [executeElse Value]
    by
        'null;
end function

function executeThen Value [literal]
    deconstruct not Value
        0
    replace [statement*]
        S [statement*]
    by
        S [executeStatements]
end function

function executeElse Value [literal]
    deconstruct Value
        0
    replace [opt else_statement]
        'else
            S [statement*]
    by
        'else
            S [executeStatements]
end function

% Interpret a while statement by transforming to its recursive meaning, 
% that is:  while E do S end  =>  if E then S while E do S end end
function executeWhile
    replace [statement]
        WhileStatement [statement]
    deconstruct WhileStatement
        'while Expn [expression] 'do
            S [statement*]
        'end
    construct IfStatement [statement]
        'if Expn 'then
            S [. WhileStatement]
        'end
    by
        IfStatement [executeStatement]
end function

% Interpret a for statement by transforming to its while meaning, that is:
% for I := E1 to E2 do S end  =>  
% var I; I := E1; var I_Upper; I_Upper := E2; while I - I_Upper do S end
function executeFor
    replace [statement]
        'for Id [id] := E1 [expression] 'to E2 [expression] 'do
            S [statement*]
        'end
    construct UpperId [id]
        Id [_ 'Upper] [!]
    construct InitialStatements [statement*]
        'var Id;
        Id := E1;
        'var UpperId;
        UpperId := (E2) + 1;
    construct IterateStatement [statement]
        Id := Id + 1;
    construct WhileStatement [statement]
        'while Id - UpperId 'do
            S [. IterateStatement]
        'end
    construct Initialize [statement*]
        InitialStatements [executeStatements]
    by
        WhileStatement [executeStatement]
end function

% Interpret a read statement by reading an input value and transforming
% to an assignment to the variable
function executeRead
    replace [statement]
        'read Id [id] ;
    construct Input [opt literal]
        _ [getp "read: "]
    deconstruct Input 
        Value [literal]
    construct Assignment [statement]
        Id := Value;
    by
        Assignment [executeStatement]
end function

% Interpret a write statement by evaluating the expression and outputting
% it to the terminal
% Note: not clear whether items are output as a whole a line (assume yes)
function executeWrite
    replace [statement]
        'write Expn [expression] ;
    construct ExpnValue [expression]
        Expn [evaluateExpression]
             [writeString]
             [writeInteger]
    by
        'null;
end function

function writeString
    match [expression]
        S [stringlit]
    construct Output [stringlit]
        S [print]
end function

function writeInteger
    match [expression]
        I [integernumber]
    construct Output [integernumber]
        I [print]
end function

% Utility functions for the VM
function checkDefined Id [id]
    match [memory_cell*]
        Memory [memory_cell*]
    deconstruct not * [id] Memory
        Id 
    construct ErrorMessage [stringlit]
        _ [+ "*** Error: undeclared variable : '"]
          [quote Id] [+ "'"] 
          [print] 
          [quit 102]
end function

function setValue Id [id] Value [literal]
    replace * [memory_cell]
        Id _ [literal]
    by
        Id Value
end function

% Expression evaluation
rule evaluateExpression
    replace [expression]
        E [expression]
    construct NewE [expression]
        E [evaluateParens]
          [evaluatePrimaries]
          [evaluateMultiplications]
          [evaluateDivisions]
          [evaluateAdditions]
          [evaluateSubtractions]
          [evaluateEquals]
          [evaluateNotEquals]
    deconstruct not NewE
        E
    by
        NewE
end rule

rule evaluateParens
    replace [primary]
        ( Expn [expression] )
    construct ExpnValue [expression]
        Expn [evaluateExpression]
    deconstruct Expn
        Value [literal]
    by
        Value
end rule

rule evaluatePrimaries
    replace [primary]
        Id [id]
    import Memory [memory_cell*]
    construct Check [memory_cell*]
        Memory [checkDefined Id]
    deconstruct * [memory_cell] Memory
        Id Value [literal]
    by
        Value
end rule

rule evaluateEquals
    replace [expression]
        I1 [integernumber] = I2 [integernumber]
    by
        I1 [- I2] [toBoolean] [+ 1] [rem 2]
end rule

rule evaluateNotEquals
    replace [expression]
        I1 [integernumber] != I2 [integernumber]
    by
        I1 [- I2] [toBoolean]
end rule

function toBoolean
    replace [integernumber]
        N [integernumber]
    deconstruct not N
        0
    by
        1
end function

rule evaluateAdditions
    replace [term]
        I1 [integernumber] + I2 [integernumber]
    by
        I1 [+ I2]
end rule

rule evaluateSubtractions
    replace [term]
        I1 [integernumber] - I2 [integernumber]
    by
        I1 [- I2]
end rule

rule evaluateMultiplications
    replace [factor]
        I1 [integernumber] * I2 [integernumber]
    by
        I1 [* I2]
end rule

rule evaluateDivisions
    replace [factor]
        I1 [integernumber] / I2 [integernumber]
    by
        I1 [div I2]
end rule
