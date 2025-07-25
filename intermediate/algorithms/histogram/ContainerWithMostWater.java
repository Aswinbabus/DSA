package algorithms.histogram;

public class ContainerWithMostWater
{
	public int maxArea(int[] height) {

		int leftPointer = 0,rightPointer = height.length-1;

		int maxArea = 0;

		while(leftPointer < rightPointer) {

			int area = (rightPointer - leftPointer) * Math.min(height[rightPointer],height[leftPointer]);
			maxArea = Math.max(maxArea,area);

			if(height[rightPointer] > height[leftPointer]){
				leftPointer++;
			}
			else {
				rightPointer--;
			}

		}

		return maxArea;

	}

	public static void main(String[] args) {
		ContainerWithMostWater container = new ContainerWithMostWater();

		// Test case 1
		int[] height1 = {1,8,6,2,5,4,8,3,7};
		int result1 = container.maxArea(height1);
		System.out.println("Test 1 Output: " + result1); // Expected: 49

		// Test case 2
		int[] height2 = {1,1};
		int result2 = container.maxArea(height2);
		System.out.println("Test 2 Output: " + result2); // Expected: 1
	}

}
