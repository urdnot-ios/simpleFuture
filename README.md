# Scala Future and Either

I made this as a reference for myself. Anytime you want
to parse an object and put it in a proper data structure
(like JSON or a CSV or just a string) I find it best to account for two main problems:

1. Blocking I/O
2. Bad messages that don't parse

A good way to fix both is to use Scala's Future and Either traits. The basic idea is you parse the
record using a Future to keep the threads free and using Either to catch
errors and still return something.

Comments are in the code.