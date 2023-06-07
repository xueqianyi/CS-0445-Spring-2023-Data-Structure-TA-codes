
# CS 0445 Spring 2023 Midterm Exam Projects on Linked Lists
***Project 1***
### Introduction:
Recently in lecture we have discussed the list interface and the author's LList<T> class. In this project you will add some useful functionality to the LList<T> class that is not part of the list interface. Specifically, you will add a copy constructor and an equals() method to the LList<T> class. You should be familiar with these concepts from previous assignments. However, you implemented these in an array-based class before. Now you will implement them in a linked-list based class. Specifically, you will implement the following methods:  
```  
// Creates a new list that contains the same data in the    
// same order that was contained in old. The lists should    
// be separated from each other (so you must make new    
// Nodes in the copy) but the data values within the lists   
// can be shared (so the copy is not totally deep).    
public LList(LList<T> old)
```  
```  
// Return true if the current LList<T> and the argument
// LList<T> contain the same data (based on the equals() method
// for class T) in the same order; return false otherwise
public boolean equals(LList<T> rhs)
```  
Once you have completed your modified LList<T> class, test it with the main program MidProj1.java. Your output should match that shown in file P1Out.txt. To run this program you will need the following files:  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**LList.java** (author's original class)  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**ListInterface.java** (even though we are not using ListInterface<T> in the main program, since LList<T> implements ListInterface<T>, we still need the interface file.  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**MidProj1.java** (main program to test your class)  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**P1Out.txt** (output to use for comparison)

### Hints:
For the copy constructor you will need to iterate through the nodes of your old LList<T>, making a new Node corresponding to each old Node (and containing the same data). This can be done with a loop, adding each new Node to the end of the list. Be careful to make sure that you are linking the Nodes correctly. Also be careful about "one off" errors – don't miss any Nodes (ex: first or last) and don't iterate too far (could give a NullPointerException).   
  For the equals() method you will need to iterate through both lists, keeping separate Node pointers as you proceed down each one. You should first test the lengths of the lists, since clearly if they are not the same the answer will be false – this will save you the trouble of having to deal with lists of different lengths in your loop.
