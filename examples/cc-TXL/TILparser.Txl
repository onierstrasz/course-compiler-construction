% TXL parser / pretty-printer for Tiny Imperative Language
% Jim Cordy, April 2005

% All TXL parsers are automatically also pretty-printers if the
% grammar includes the optional formatting cues, as in this case

% Use the TIL grammar shown below
include "TIL.Grm"

% No need to do anything except recognize the input, since the grammar
% includes the output formatting cues
function main
   match [program]
      _ [program]
end function