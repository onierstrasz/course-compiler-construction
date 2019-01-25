
% Simple transform to make a Tiny Imperative Language program self-tracing
% Jim Cordy, August 2005

% Replaces every statement in the program with a write statement of its text
% followed by itself.  For example:
%
%    write ("x := 5;");
%    x := 5;

% Begin with the TIL base grammar
include "TIL.Grm"

% We don't bother preserving comments in this transformation
% since we're not interested in maintaining the result, just running it

% Tell TXL what our string escape convention is
#pragma -esc "\"

% Allow for concise elided structured statements
redefine statement
        ... 
    |     [SP] '... [SP]
end redefine

% Allow for traced statements - the attribute TRACED marks statements already done
redefine statement
        ...
    |    [traced_statement]
end redefine

define traced_statement
    [statement] [attr 'TRACED]
end define

% Replace every statement by a tracing write statement followed by itself
rule main
    % Since our result requires two statements where one was before,
    % we work on the statement sequence one level up
    replace [repeat statement]
        S [statement]
        Rest [repeat statement]
    % Semantic guard: if it's already done don't do it again
    deconstruct not S
        % _ [traced_statement]
        _ [statement] 'TRACED
    % Make a concise version of structured statements
    construct ConciseS [statement]
        S [deleteBody]
    % Make a string literal of the text of the concise statement
    construct QuotedS [stringlit]
        _ [+ "Trace: "] [quote ConciseS] 
    by
        'write QuotedS;    'TRACED
        S                  'TRACED
        Rest
end rule

% Replace any embedded statement sequence in the printed version of 
% a traced statement with an elision symbol for conciseness
function deleteBody
    replace * [statement*]
        _ [statement*]
    by
        '...
end function