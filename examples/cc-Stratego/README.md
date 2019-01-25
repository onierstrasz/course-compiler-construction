
---

Install Stratego/XT

http://strategoxt.org/Stratego/StrategoDownload

- Download the three parts (aterm, sdf2 and strategoxt) for your platform
- Unpack and install them somewhere (eg /opt)
- Make sure the three bin folders are accessible in your path

***NB: there are load errors in the latest version (0.17) as of Feb 2011***

---

The prop-* examples are from the Stratego tutorial.

http://releases.strategoxt.org/strategoxt-manual/unstable/manual/chunk-chapter/stratego-term-rewriting.html#id3315360

---

Fibonacci example:

% stratego-shell
stratego> import libstratego-lib
stratego> !(1,1)
(1,1)
stratego> fib = !(<Snd>,<add>)
stratego> fib
(1,2)
stratego> fib
(2,3)
stratego> fib
(3,5)
stratego> fib
(5,8)

---

stratego-shell
import libstratego-lib
!(1,1)
fib = !(<Snd>,<add>)

fib

...

:quit
