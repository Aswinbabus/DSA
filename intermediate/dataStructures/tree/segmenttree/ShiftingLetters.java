package dataStructures.tree.segmenttree;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ShiftingLetters
{
	public String shiftingLetters(String s, int[][] shifts) {

         SegmentTreeWithLazyPropagation tree = new SegmentTreeWithLazyPropagation(IntStream.range(0,s.length()).toArray());

		 for(int[] shift : shifts) {
			 tree.rangeUpdate(shift[0],shift[1],shift[2] == 0 ? -1 : 1);
		 }

		 StringBuilder stringBuilder = new StringBuilder();

		 for(int i = 0;i<s.length();i++) {

			 int steps = tree.queryRange(i,i);

			 if(steps < 0) {
				 stringBuilder.append(moveLetter(s.charAt(i),-steps % 26,true));
			 }
			 else {
				 stringBuilder.append(moveLetter(s.charAt(i),steps % 26,false));
			 }


		 }

		System.out.println(Arrays.toString(tree.tree));

		 return stringBuilder.toString();

	}

	private char moveLetter(char ch, int steps, boolean reverse)
	{

		 // forward
		if(!reverse) {

			if(ch + steps > 'z')
			{
				steps = steps - ('z' - ch + 1);
				return (char) ('a' + steps);
			}
			else {
				return (char) (ch + steps);
			}


		}
		else {

			steps = Math.abs(steps);
			if(ch - steps < 'a') {
				steps = steps - (ch - 'a' + 1);
				return (char) ('z' - steps);
			}
			else {
				return (char) (ch-steps);
			}



		}

	}

	public static void main(String[] args)
	{
		ShiftingLetters letters = new ShiftingLetters();

		// Test case 1
		String s1 = "abc";
		int[][] shifts1 = {{0,1,0},{1,2,1},{0,2,1}};
		String result1 = letters.shiftingLetters(s1, shifts1);
		System.out.println(result1);// Output: "ace"


		// Test case 2
		String s2 = "dztz";
		int[][] shifts2 = {{0,0,0},{1,1,1}};
		String result2 = letters.shiftingLetters(s2, shifts2);
		System.out.println(result2); // Output: "catz"
	}


}
