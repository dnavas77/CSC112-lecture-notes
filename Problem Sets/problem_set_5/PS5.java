public class PS5 {
    public static void main(String[] args) {
        /* Problem Set 5 Homework */

        int[] numbers = {10, 15, 25, 30, 45, 46, 48, 72, 76, 80, 93};

        int left = 0;
        int right = numbers.length - 1;
        int t = 40;
        int count;

        //>>>>>>>>>>>>> LAZY BINARY SEARCH <<<<<<<<<<<<<<
        count = 0;
        while(left < right) {
            int mid = (left + right) / 2;

            if (t > numbers[mid]) {
              count++;
              left = mid + 1;
            }
            else {
              count++;
              right = mid;
            }
        }//

        if (t == numbers[left]) {
          count++;
          System.out.println("Lazy binary found at " + left);
        }
        else {
          count++;
          System.out.println("Lazy binary not found");
        }
        
        System.out.println(count);

        //>>>>>>>>>>>>>>> BINARY SEARCH <<<<<<<<<<<<<<<<<
        count = 0;
        left = 0;
        right = numbers.length - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            
            if (numbers[mid] == t) {
              count++;
              System.out.println("binary found");
              break;
            }
            else if (numbers[mid] > t) {
                right = mid - 1;
            }
            else {
              left = mid + 1;
            }

            count += 2;
        }// 

        System.out.println("binary not found");
        System.out.println(count);
    }
}
