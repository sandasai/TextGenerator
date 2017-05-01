# TextGenerator
A pseudo random text generator based upon character patterns in a file.
This is done by using a custom HashMap based upon Java's built in .hashCode() functions. 
The program utilizes a HashMap to store substrings as keys to Markov objects which contain the frequency of suffix characters to that substring. New characters are generated based on the frequency of possible suffix characters and the process is repeated

To execute, 'java -jar textGeneratorRunnable.jar X Y Z' where argument X is an integer corresponding to number of prefix characters to consider when generating a new character, Y is the number of characters in the output file, and Z is the input file to base character generation from.

Note: make sure your input file is in the same directory as textGeneratorRunnable when executing the program from .jar.
