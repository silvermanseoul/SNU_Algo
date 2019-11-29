import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution1 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution1.java -encoding UTF8


   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output1.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution1

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution1
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution1   // 0.5초 수행
       timeout 1 java Solution1     // 1초 수행
 */

class Solution1 {
	static final int max_n = 1000000;
	static int[] A = new int[max_n];
    static int[] copied = new int[max_n];
	static int n;
    static int Answer1, Answer2, Answer3;
	static long start;
	static double time1, time2, time3;


	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input1.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output1.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output1.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 10; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 배열의 원소의 개수를 n에 읽어들입니다.
			   그리고 각 원소를 A[0], A[1], ... , A[n-1]에 읽어들입니다.
			 */
			stk = new StringTokenizer(br.readLine());
			n = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < n; i++) {
				A[i] = Integer.parseInt(stk.nextToken());
			}

			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
               원본으로 주어진 입력 배열이 변경되는 것을 방지하기 위해,
               여러분이 구현한 각각의 함수에 복사한 배열을 입력으로 넣도록 코드가 작성되었습니다.

			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
               함수를 구현하면 Answer1, Answer2, Answer3에 해당하는 주석을 제거하고 제출하세요.

               문제 1은 java 프로그래밍 연습을 위한 과제입니다.
			*/

			/* Problem 1-1 */
            System.arraycopy(A, 0, copied, 0, n);
			start = System.currentTimeMillis();
			mergeSort1(copied, 0, n - 1);
            Answer1 = makeAnswer(copied);
            time1 = (System.currentTimeMillis() - start) / 1000.;

			/* Problem 1-2 */
			System.arraycopy(A, 0, copied, 0, n);
			start = System.currentTimeMillis();
			mergeSort2(copied, 0, n - 1);
			Answer2 = makeAnswer(copied);
			time2 = (System.currentTimeMillis() - start) / 1000.;

            /* Problem 1-3 */
            System.arraycopy(A, 0, copied, 0, n);
			start = System.currentTimeMillis();
			mergeSort3(copied, 0, n - 1);
			Answer3 = makeAnswer(copied);
            time3 = (System.currentTimeMillis() - start) / 1000.;

            /*
            	여러분의 답안 Answer1, Answer2, Answer3을 비교하는 코드를 아래에 작성.
             	세 개 답안이 동일하다면 System.out.println("YES");
             	만일 어느하나라도 답안이 다르다면 System.out.println("NO");
            */
            if(Answer1 == Answer2 && Answer2 == Answer3) System.out.println("YES");
            else System.out.println("NO");
			/////////////////////////////////////////////////////////////////////////////////////////////

			// output1.txt로 답안을 출력합니다. Answer1, Answer2, Answer3 중 구현된 함수의 답안 출력
			pw.println("#" + test_case + " " + Answer1 + " " + time1 + " " + time2 + " " + time3);

			/*
			   아래 코드를 수행하지 않으면 여러분의 프로그램이 제한 시간 초과로 강제 종료 되었을 때,
			   출력한 내용이 실제로 파일에 기록되지 않을 수 있습니다.
			   따라서 안전을 위해 반드시 flush() 를 수행하시기 바랍니다.
			 */
			pw.flush();
		}

		br.close();
		pw.close();
	} // end main


	private static void mergeSort1(int[] arr, int low, int high) {
		if(low < high) {
			int mid = (low + high) / 2;
			mergeSort1(arr, low, mid);
			mergeSort1(arr, mid + 1, high);
			merge1(arr, low, mid, high);
		}
	} // end mergeSort1


	private static void merge1(int[] arr, int low, int mid, int high) {
		int length = high - low + 1;
		int i = low;
		int j = mid + 1;
		int[] sorted = new int[length];
		int idx = 0;

		while(i <= mid && j <= high) {
			if(arr[i] < arr[j]) sorted[idx++] = arr[i++];
			else sorted[idx++] = arr[j++];
		}
		while(i <= mid)
			sorted[idx++] = arr[i++];
		while(j <= high)
			sorted[idx++] = arr[j++];

		for(idx = 0; idx < length; idx++)
			arr[low+idx] = sorted[idx];
	} // end merge1


	private static void mergeSort2(int[] arr, int low, int high) {
		int length = high - low + 1;
		int gap = length / 16;
		if(length >= 2 && length < 16) {
			for(int i = high - 1; i >= low; i--)
				for(int j = low; j <= i; j++)
					if(arr[j] > arr[j+1]) swap(arr, j, j+1);
		}
		else if(length >= 16){
			mergeSort2(arr, low, low + gap - 1);
			for(int i = 1; i <= 14; i++)
				mergeSort2(arr, low + gap*i, low + gap*(i+1) - 1);
			mergeSort2(arr, low + gap*15, high);
			merge2(arr, low, high, gap);
		}
		/* low=0 high=15 -> gap=1
		 * low=0 ~ low+gap-1=0
		 * low+gap=1 ~ low+gap*2-1=1
		 *
		 * low+gap*14=14 ~ low+gap*15-1=14
		 * low+gap*15=15 ~ high=15
		 */
	} // end mergeSort2


	private static void merge2(int[] arr, int low, int high, int gap) {
		int length = high - low + 1;
		int[] subLow = new int[16]; // subLow[i]: low index of i-th sub array
		int[] subHigh = new int[16]; // subHigh[i]: high index of i-th sub array
		int[] sorted = new int[length];
		int idx = 0;

		subLow[0] = low;
		for(int i = 1; i <= 15; i++) subLow[i] = low + gap*i;
		subHigh[15] = high;
		for(int i = 0; i <= 14; i++) subHigh[i] = low + gap*(i+1) - 1;

		while(idx < length) {
			int min = Integer.MAX_VALUE;
			int minFrom = -1;
			for (int i = 0; i < 16; i++)
				if (subLow[i] <= subHigh[i] && arr[subLow[i]] < min) {
					min = arr[subLow[i]];
					minFrom = i;
				}
			sorted[idx++] = min;
			subLow[minFrom]++;
		}

		for(idx = 0; idx < length; idx++)
			arr[low+idx] = sorted[idx];
	} // end merge2


    private static void mergeSort3(int[] arr, int low, int high) {
        int length = high - low + 1;
        int gap = length / 16;
        if(length >= 2 && length < 16) {
            for(int i = high - 1; i >= low; i--)
                for(int j = low; j <= i; j++)
                    if(arr[j] > arr[j+1]) swap(arr, j, j+1);
        }
        else if(length >= 16){
            mergeSort3(arr, low, low + gap - 1);
            for(int i = 1; i <= 14; i++)
                mergeSort3(arr, low + gap*i, low + gap*(i+1) - 1);
            mergeSort3(arr, low + gap*15, high);
            merge3(arr, low, high, gap);
        }
    } // end mergeSort3


    private static void merge3(int[] arr, int low, int high, int gap) {
		int length = high - low + 1;
        int[] subLow = new int[16]; // subLow[i]: low index of i-th sub array
        int[] subHigh = new int[16]; // subHigh[i]: high index of i-th sub array
        int[] minHeap = new int[16];
        int[] from = new int[16]; // minheap[i] is from from[i]-th sub array
        int[] sorted = new int[length];
        int idx = 0;

        subLow[0] = low;
        for(int i = 1; i <= 15; i++) subLow[i] = low + gap*i;
        subHigh[15] = high;
        for(int i = 0; i <= 14; i++) subHigh[i] = low + gap*(i+1) - 1;

        // fill heap
        for(int i = 0; i < 16; i++) {
            minHeap[i] = arr[subLow[i]];
            subLow[i]++;
            from[i] = i;
        }

        // build heap
        buildHeap(minHeap, from);

        while(idx < length) {
            // delete min value
            sorted[idx++] = minHeap[0];
            int minFrom = from[0];

            // insert new one
			if(subLow[minFrom] <= subHigh[minFrom])
            	minHeap[0] = arr[subLow[minFrom]++];
			else
				minHeap[0] = Integer.MAX_VALUE;

            // heapify
            heapify(minHeap, 0, from);
        }

        for(idx = 0; idx < length; idx++)
            arr[low+idx] = sorted[idx];
    } // end merge3


    private static void buildHeap(int[] arr, int[] extra) {
        int size = arr.length;
        for (int i = (size-2) / 2; i >= 0; i--) heapify(arr, i, extra); // size-1: last node, [(size-1)-1]/2: parent of last node(first non-leaf)
    }


    private static void heapify(int[] arr, int node, int[] extra) {
        int size = arr.length;
        int parent = node;
        int leftChild = node * 2 + 1;
        int rightChild = node * 2 + 2;
//        int smallerChild = 0;
//
//        if(leftChild >= size) // no child
//            return;
//
//        if(rightChild >= size) // only left child
//            smallerChild = leftChild;
//        else
//            smallerChild = arr[leftChild] < arr[rightChild] ? leftChild : rightChild;
//
//        if (arr[node] <= arr[smallerChild])
//            return;
//
//        swap(arr, node, smallerChild);
//        swap(extra, node, smallerChild);
//        heapify(arr, smallerChild, extra);
        if(leftChild < size && arr[parent] > arr[leftChild])
        	parent = leftChild;
		if(rightChild < size && arr[parent] > arr[rightChild])
			parent = rightChild;
		if(node != parent) {
			swap(arr, node, parent);
			swap(extra, node, parent);
			heapify(arr, parent, extra);
		}
    } // end heapify


    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


	private static int makeAnswer(int[] arr) {
		int sum = 0;
		for(int i = 0; i < n; i += 4) sum += (arr[i] % 7);
		return sum;
	}
}



/**
 * log := log_2, Log := log_6
 *
 * 1. mergeSort1
 * T(n) = 재귀호출 + 비교(최솟값 찾기) + 이동(최솟값을 하나씩 임시 배열에 저장 + 완성된 임시 배열 전체를 복사)
 * 		= 2T(n/2) + theta(n) + theta(2n)
 * 		= 2T(n/2) + theta(n)
 * T(n) = theta(nlogn)
 *
 * 2. mergeSort2
 * T(n) = 재귀호출 + 비교 + 이동
 * 		= 16T(n/16) + theta(15n) + theta(2n)
 * 		= 16T(n/16) + theta(n)
 * T(n) = theta(nLogn)
 *
 * 3. mergeSort3
 * T(n) = 재귀호출 + 비교 + 이동
 * 		= 16T(n/16) + theta(log16*n) + theta(2n)
 * 		= 16T(n/16) + theta(n)
 * T(n) = theta(nLogn)
 */
