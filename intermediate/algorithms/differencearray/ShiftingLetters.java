package algorithms.differencearray;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ShiftingLetters
{
	public String shiftingLetters(String s, int[] shifts) {

		DifferenceArray differenceArray = new DifferenceArray(IntStream.generate(() -> 0).limit(s.length()).toArray());

		for(int i = 0;i<shifts.length;i++) {
			differenceArray.update(0,i,shifts[i]);
		}

		long[] steps = differenceArray.reconstruct();

		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0;i<steps.length;i++) {
			stringBuilder.append(moveLetter(s.charAt(i),steps[i]));
		}
		return stringBuilder.toString();

	}

	private char moveLetter(char ch, long steps)
	{


		steps = steps % 26;

		if(ch + steps > 'z')
		{
			steps = steps - ('z' - ch + 1);
			return (char) ('a' + steps);
		}
		else {
			return (char) (ch + steps);
		}





	}

	public static void main(String[] args) {
		ShiftingLetters sl = new ShiftingLetters();

		// Test case 1
		String s1 = "abc";
		int[] shifts1 = {3, 5, 9};
		System.out.println(sl.shiftingLetters(s1, shifts1)); // Output: "rpl"

		// Test case 2
		String s2 = "aaa";
		int[] shifts2 = {1, 2, 3};
		System.out.println(sl.shiftingLetters(s2, shifts2)); // Output: "gfd"

		// Test case 3
		String s3 = "mkgfzkkuxownxvfvxasy";
		int[] shifts3 = {505870226,437526072,266740649,224336793,532917782,311122363,567754492,595798950,81520022,684110326,137742843,275267355,856903962,148291585,919054234,467541837,622939912,116899933,983296461,536563513};
		System.out.println(sl.shiftingLetters(s3, shifts3));
	}


}
