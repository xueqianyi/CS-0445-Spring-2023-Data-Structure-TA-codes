// CS 0445 Spring 2023
// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a circular, doubly linked list of nodes.  See more comments below on the
// specific requirements for the class.

// You should use this class as the starting point for your implementation. 
// Note that all of the methods are listed -- you need to fill in the method
// bodies.  Note that you may want to add one or more private methods to help
// with your implementation -- that is fine.

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  

import javafx.scene.media.Media;

public class MyStringBuilder {
	// These are the only two instance variables you are allowed to have.
	// See details of CNode class below. In other words, you MAY NOT add
	// any additional instance variables to this class. However, you may
	// use any method variables that you need within individual methods.
	// But remember that you may NOT use any variables of any other
	// linked list class or of the predefined StringBuilder or
	// StringBuffer class in any place in your code. You may only use the
	// String class where it is an argument or return type in a method.
	private CNode firstC; // reference to front of list. This reference is necessary
	// to keep track of the list
	private int length; // number of characters in the list

	// You may also add any additional private methods that you need to
	// help with your implementation of the public methods.

	// Create a new MyStringBuilder initialized with the chars in String s
	// Note: This method is implemented for you. See code below. Also read
	// the comments. The code here may be helpful for some of your other
	// methods.
	public MyStringBuilder(String s) {
		if (s == null || s.length() == 0) // special case for empty String
		{
			firstC = null;
			length = 0;
		} else {
			firstC = new CNode(s.charAt(0)); // create first node
			length = 1;
			CNode currNode = firstC;
			// Iterate through remainder of the String, creating a new
			// node at the end of the list for each character. Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < s.length(); i++) {
				CNode newNode = new CNode(s.charAt(i)); // create Node
				currNode.next = newNode; // link new node after current
				newNode.prev = currNode; // line current before new node
				currNode = newNode; // move down the list
				length++;
			}
			// After all nodes are created, connect end back to front to make
			// list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Return the entire contents of the current MyStringBuilder as a String
	// For this method you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	// return new String(charArray);
	// Note: This method is implemented for you. See code below.
	public String toString() {
		char[] c = new char[length];
		int i = 0;
		CNode currNode = firstC;

		// Since list is circular, we cannot look for null in our loop.
		// Instead we count within our while loop to access each node.
		// Note that in this code we don't even access the prev references
		// since we are simply moving from front to back in the list.
		while (i < length) {
			c[i] = currNode.data;
			i++;
			currNode = currNode.next;
		}
		return new String(c);
	}

	// Create a new MyStringBuilder initialized with the chars in array s.
	// You may NOT create a String from the parameter and call the first
	// constructor above. Rather, you must build your MyStringBuilder by
	// accessing the characters in the char array directly. However, you
	// can approach this in a way similar to the other constructor.
	public MyStringBuilder(char[] s) {
		if (s == null || s.length == 0) {
			firstC = null;
			length = 0;
		} else {
			firstC = new CNode(s[0]);
			length = 1;
			CNode currNode = firstC;

			for (int i = 1; i < s.length; i++) {
				CNode newNode = new CNode(s[i]);
				currNode.next = newNode;
				newNode.prev = currNode;
				currNode = newNode;
				length++;
			}
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Copy constructor -- make a new MyStringBuilder from an old one. Be sure
	// that you make new nodes for the copy (traversing the nodes in the original
	// MyStringBuilder as you do)
	public MyStringBuilder(MyStringBuilder old) {
		length = old.length;
		if (old == null || length == 0) {
			firstC = null;
		} else {
			firstC = new CNode(old.firstC.data);
			CNode currNode = firstC;
			CNode oldTmpNode = old.firstC;
			int flag = 1;
			while (flag < length) {
				oldTmpNode = oldTmpNode.next;
				CNode newNode = new CNode(oldTmpNode.data);
				currNode.next = newNode;
				newNode.prev = currNode;
				currNode = newNode;
				flag++;
			}
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Create a new empty MyStringBuilder
	public MyStringBuilder() {
		length = 0;
		firstC = null;
	}

	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder. Be careful for special cases! Note
	// that you cannot simply link the two MyStringBuilders together -- that is
	// very simple but it will intermingle the two objects, which you do not want.
	// Thus, you should copy the data in argument b to the end of the current
	// MyStringBuilder.
	public MyStringBuilder append(MyStringBuilder b) {
		if (b == null) {
			return this;
		}
		// original sb is null.
		if (firstC == null || length == 0) {
			CNode tmpNode = b.firstC;
			firstC = new CNode(tmpNode.data);
			CNode currNode = firstC;
			length = b.length;
			for (int i = 0; i < length; i++) {
				tmpNode = tmpNode.next;
				CNode newNode = new CNode(tmpNode.data);
				currNode.next = newNode;
				newNode.prev = currNode;
				currNode = newNode;
			}
			currNode.next = firstC;
			firstC.prev = currNode;
			return this;
		}
		MyStringBuilder bb = new MyStringBuilder(b);
		CNode firstNodeLast = firstC.prev;
		CNode secondNodeLast = bb.firstC.prev;
		firstNodeLast.next = bb.firstC;
		bb.firstC.prev = firstNodeLast;
		firstC.prev = secondNodeLast;
		secondNodeLast.next = firstC;
		length = length + b.length;
		return this;

	}

	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder. Be careful for special cases!
	public MyStringBuilder append(String s) {
		if (s == null || s.length() == 0) {
			return this;
		}

		for (int i = 0; i < s.length(); i++) {
			CNode newNode = new CNode(s.charAt(i));
			if (firstC != null) {
				firstC.prev.next = newNode;
				newNode.prev = firstC.prev;
				firstC.prev = newNode;
				newNode.next = firstC;
			} else {
				firstC = newNode;
				firstC.prev = firstC;
				firstC.next = firstC;
			}
		}
		length = length + s.length();
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder. Be careful for special cases!
	public MyStringBuilder append(char[] c) {
		if (c == null || c.length == 0) {
			return this;
		} else {
			MyStringBuilder mySB = new MyStringBuilder(c);
			return append(mySB);
		}
	}

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder. Be careful for special cases!
	public MyStringBuilder append(char c) {
		CNode newNode = new CNode(c);
		if (firstC == null) {
			firstC = newNode;
			firstC.prev = firstC;
			firstC.next = firstC;
		} else {
			firstC.prev.next = newNode;
			newNode.prev = firstC.prev;
			newNode.next = firstC;
			firstC.prev = newNode;
		}
		length++;
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index) {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		}
		CNode tmp = firstC;
		for (int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		return tmp.data;
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is). If "end" is past the end of the MyStringBuilder,
	// only remove up until the end of the MyStringBuilder. Be careful for
	// special cases!
	public MyStringBuilder delete(int start, int end) {
		// "start" is invalid or "end" <= "start"
		if (end <= start || start < 0 || start >= length)
			return this;
		// "end" is past the end of the MyStringBuilder
		if (end > length)
			end = length;
		// delete all the sb
		if (start == 0 && end == length)
			return null;

		CNode tmpFirst = firstC;
		// when start = 0
		if (start == 0) {
			length = length - (end - start);
			for (int i = 0; i < end; i++) {
				firstC.prev.next = firstC.next;
				firstC.next.prev = firstC.prev;
				firstC = firstC.next;
			}

			return this;
		}

		// delete (other situations)
		for (int i = 0; i < start; i++) {
			tmpFirst = tmpFirst.next;
		}
		CNode tmpFirst2 = new CNode(tmpFirst.data, tmpFirst.next, tmpFirst.prev);
		CNode tmpLast = tmpFirst;
		for (int i = start; i < end; i++) {
			tmpLast = tmpLast.next;
		}
		CNode prevNode = tmpFirst2.prev;
		CNode nextNode = tmpLast;
		prevNode.next = nextNode;
		nextNode.prev = prevNode;
		length = length - (end - start);
		return this;
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder. If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is). If "index"
	// is the last character in the MyStringBuilder, go backward in the list in
	// order to make this operation faster (since the last character is simply
	// the previous of the first character)
	// Be careful for special cases!
	// Binary Search no
	public MyStringBuilder deleteCharAt(int index) {
		if (index < 0 || index >= length)
			return this;
		// when index = length-1;
		if (index == length - 1) {
			if (length == 1) {
				length--;
				return null;
			} else {
				firstC.prev.prev.next = firstC;
				firstC.prev = firstC.prev.prev;
				length--;
				return this;
			}
		} else {
			// delete when index = 0;
			if (index == 0) {
				if (length == 1) {

					length = 0;
					firstC = null;
					return this;
				} else {
					if (length > 2) {
						firstC.prev.next = firstC.next;
						firstC.next.prev = firstC.prev;
						firstC = firstC.next;
					} else {
						firstC.prev = null;
						firstC.next = null;
					}
					length--;
					return this;
				}
			}
			CNode currNode = firstC;
			for (int i = 0; i < index; i++) {
				currNode = currNode.next;
			}
			CNode preNode = currNode.prev;
			CNode nexNode = currNode.next;
			preNode.next = nexNode;
			nexNode.prev = preNode;
			length--;
			return this;
		}
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder. If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1. Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str) {
		if (firstC == null && length == 0 && str == null && str.length() == 0)
			return -1;
		if (str != null && str.length() != 0 && str.length() <= length) {
			CNode currNode = firstC;
			for (int i = 0; i < length && currNode != null; i++, currNode = currNode.next) {
				int flag = 0;
				if (currNode.data == str.charAt(0)) {
					CNode newNode = currNode;
					int j = 0;
					while (j < str.length() && newNode.data == str.charAt(j) && newNode != null) {
						flag++;
						newNode = newNode.next;
						j++;
					}
					if (flag == str.length())
						return i;
				}
			}
		}
		return -1;
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder. if "offset" ==
	// length, this is the same as append. If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str) {
		if (firstC == null || length == 0) {
			for (int i = 0; i < str.length(); i++) {
				CNode newNode = new CNode(str.charAt(0));
				if (firstC == null) {
					firstC = newNode;
					firstC.prev = firstC;
					firstC.next = firstC;
				} else {
					firstC.prev.next = newNode;
					newNode.prev = firstC.prev;
					newNode.next = firstC;
					firstC.prev = newNode;
				}
				length++;
			}
			return this;
		}

		if (str == null || str.length() == 0)
			return this;
		// insert at the end of the sb
		if (offset == length)
			return append(str);
		// insert at the start of the sb
		if (offset == 0) {
			for (int i = 0; i < str.length(); i++) {
				CNode newNode = new CNode(str.charAt(str.length() - i - 1));
				firstC.prev.next = newNode;
				newNode.prev = firstC.prev;
				newNode.next = firstC;
				firstC.prev = newNode;
				firstC = firstC.prev;
			}
			length = str.length() + length;
			return this;
		}
		// insert anywhere else
		CNode curNode = firstC;
		for (int i = 0; i < offset; i++) {
			curNode = curNode.next;
		}
		CNode preNode = curNode.prev;
		MyStringBuilder mySB = new MyStringBuilder(str);
		CNode mySbLast = mySB.firstC.prev;
		preNode.next = mySB.firstC;
		mySB.firstC.prev = preNode;
		if (mySbLast == null) {
			mySB.firstC.next = curNode;
			curNode.prev = mySB.firstC;
		} else {
			mySbLast.next = curNode;
			curNode.prev = mySbLast;
		}
		length = length + str.length();
		return this;
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder. If "offset" ==
	// length, this is the same as append. If "offset" is invalid,
	// do nothing.
	public MyStringBuilder insert(int offset, char c) {
		CNode newNode = new CNode(c);
		// offset is invalid
		if (offset < 0 || offset >= length)
			return this;
		// original sb is null.
		if (firstC == null || length == 0) {

			MyStringBuilder mySb = new MyStringBuilder();
			mySb.firstC = newNode;
			mySb.length = 1;
			return mySb;
		}
		// "offset" == length (at the end of the sb)
		if (offset == length) {
			return append(c);
		}
		// insert at the start of the sb.
		if (offset == 0) {
			MyStringBuilder mySb = new MyStringBuilder();
			mySb.firstC = newNode;
			length = 1;
			return new MyStringBuilder(mySb.append(this));
		}
		// insert anywhere else
		CNode currNode = firstC;
		for (int i = 0; i < offset; i++) {
			currNode = currNode.next;
		}
		CNode prevNode = currNode.prev;
		prevNode.next = newNode;
		newNode.prev = prevNode;
		newNode.next = currNode;
		currNode.prev = newNode;
		return this;
	}

	// Return the length of the current MyStringBuilder
	public int length() {
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder. If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert. This method should be done
	// as efficiently as possible. In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder replace(int start, int end, String str) {
		// "start" is invalid or "end" <= "start"
		if (start < 0 || start >= length || start >= end || end < 0)
			return this;
		// str is null
		if (str == null || str.length() == 0)
			return this;
		// "end" is past the end of the MyStringBuilder
		if (end > length)
			end = length;
		// delete all the sb
		if (start == 0 && end == length)
			return new MyStringBuilder(str);

		// other situations
		CNode tmpFirst = firstC;
		for (int i = 0; i < start; i++) {
			tmpFirst = tmpFirst.next;
		}
		CNode stopNode = new CNode('0');
		int flag = 0;
		for (int i = start; i < end; i++) {
			if ((i - start + 1) <= str.length()) {
				tmpFirst.data = str.charAt(i - start);
			} else {
				if (flag == 0) {
					stopNode.data = tmpFirst.data;
					stopNode.prev = tmpFirst.prev;
					stopNode.next = tmpFirst.next;
					flag = 1;
				}
			}
			tmpFirst = tmpFirst.next;
		}
		// flag = 0, str >= end - start; else flag = 1, str < end - start;
		if (flag == 1) {
			CNode prevNode = stopNode.prev;
			prevNode.next = tmpFirst;
			tmpFirst.prev = prevNode;
			length = length - (end - start) + str.length();
			return this;
		}
		// str.length() = end-start
		if (str.length() == end - start)
			return this;
		// str.length() > end-start
		CNode prevNode = tmpFirst.prev;
		for (int i = end - start; i < str.length(); i++) {
			CNode newNode = new CNode(str.charAt(i), prevNode, null);
			prevNode.next = newNode;
			prevNode = newNode;
		}
		prevNode.next = tmpFirst;
		tmpFirst.prev = prevNode;
		length = length - (end - start) + str.length();
		return this;
	}

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder. For this method
	// you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	// return new String(charArray);
	public String substring(int start, int end) {
		// valid
		if (start < 0 || end > length || start >= length || start >= end)
			return null;
		// sb is null
		if (firstC == null || length == 0)
			return null;
		// end is past the "end"
		if (end > length)
			end = length;
		// Create a character array of the appropriate length
		char[] charArray = new char[end - start];
		// Fill the array with the proper characters from your MyStringBuilder
		CNode currNode = firstC;
		for (int i = 0; i < start; i++) {
			currNode = currNode.next;
		}
		for (int i = 0; i < end - start; i++) {
			charArray[i] = currNode.data;
			currNode = currNode.next;
		}
		return new String(charArray);
	}

	// Return as a String the reverse of the contents of the MyStringBuilder. Note
	// that this does NOT change the MyStringBuilder in any way. See substring()
	// above for the basic approach.
	public String revString() {
		if (firstC == null || length == 0)
			return null;
		if (length == 1)
			return String.valueOf(firstC.data);
		// length >= 2
		CNode currNode = firstC.prev;
		char[] charArray = new char[length];
		for (int i = 0; i < length; i++) {
			charArray[i] = currNode.data;
			currNode = currNode.prev;
		}
		return new String(charArray);
	}

	// You must use this inner class exactly as specified below. Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data, next and prev fields directly.
	private class CNode {
		private char data;
		private CNode next, prev;

		public CNode(char c) {
			data = c;
			next = null;
			prev = null;
		}

		public CNode(char c, CNode n, CNode p) {
			data = c;
			next = n;
			prev = p;
		}
	}
}
