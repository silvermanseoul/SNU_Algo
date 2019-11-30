import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;



/*
   1. 아래와 같은 명령어를 입력하면 컴파일이 이루어져야 하며, Solution2 라는 이름의 클래스가 생성되어야 채점이 이루어집니다.
       javac Solution2.java -encoding UTF8

   2. 컴파일 후 아래와 같은 명령어를 입력했을 때 여러분의 프로그램이 정상적으로 출력파일 output2.txt 를 생성시켜야 채점이 이루어집니다.
       java Solution2

   - 제출하시는 소스코드의 인코딩이 UTF8 이어야 함에 유의 바랍니다.
   - 수행시간 측정을 위해 다음과 같이 time 명령어를 사용할 수 있습니다.
       time java Solution2
   - 일정 시간 초과시 프로그램을 강제 종료 시키기 위해 다음과 같이 timeout 명령어를 사용할 수 있습니다.
       timeout 0.5 java Solution2   // 0.5초 수행
       timeout 1 java Solution2     // 1초 수행
 */

class Solution2 {
	static final int MAX_N = 20000;
	static final int MAX_E = 80000;
	static int N, E;
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int Answer;

	public static void main(String[] args) throws Exception {
		/*
		   동일 폴더 내의 input2.txt 로부터 데이터를 읽어옵니다.
		   또한 동일 폴더 내의 output2.txt 로 정답을 출력합니다.
		 */
		BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
		StringTokenizer stk;
		PrintWriter pw = new PrintWriter("output2.txt");

		/*
		   10개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		 */
		for (int test_case = 1; test_case <= 1; test_case++) {
			/*
			   각 테스트 케이스를 표준 입력에서 읽어옵니다.
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 양 끝점의 번호를 V[i], V[i]에 읽어들이고, i번째 간선의 가중치를 W[i]에 읽어들입니다. (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
			}


			/////////////////////////////////////////////////////////////////////////////////////////////
			/*
			   이 부분에서 여러분의 알고리즘이 수행됩니다.
			   문제의 답을 계산하여 그 값을 Answer에 저장하는 것을 가정하였습니다.
			 */
			/////////////////////////////////////////////////////////////////////////////////////////////
			Answer = Prim(1);


			// output2.txt로 답안을 출력합니다.
			pw.println("#" + test_case + " " + Answer);
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


	private static int Prim(int rootNum) {
        for(int i = 0; i < E; i++) {
            W[i] *= -1;
        }

		int[][] weight = new int[N+1][N+1];
		for(int i = 0; i < E; i++) {
			weight[U[i]][V[i]] = W[i];
			weight[V[i]][U[i]] = W[i];
		}

		// graph. G[0] is not used
//		Vertex[] G = new Vertex[N+1];
//		for(int i = 1; i <= N; i++) G[i] = new Vertex(Integer.MAX_VALUE, i);
//		G[rootNum] = new Vertex(0, rootNum);
//		for(int i = 0; i < E; i++) {
//			G[U[i]].addAdj(V[i]);
//			G[V[i]].addAdj(U[i]);
//		}

		// minHeap[pos] -> key. minHeap[0] is not used
		int[] minHeap = new int[N+1];
		int heapSize = N;
		for(int i = 1; i <= N; i++) minHeap[i] = Integer.MAX_VALUE;
		minHeap[rootNum] = 0;

		// pos2num[pos] -> num
		int[] pos2num = new int[N+1];
		for(int i = 1; i <= N; i++) pos2num[i] = i;

		// num2key[num] -> key
		int[] num2key = new int[N+1];
		for(int i = 1; i <= N; i++) num2key[i] = Integer.MAX_VALUE;
		num2key[rootNum] = 0;

		// num2pos[num] -> pos
		int[] num2pos = new int[N+1];
		for(int i = 1; i <= N; i++) num2pos[i] = i;

		// num2adjNums[num][] -> {adjNum}
		ArrayList<ArrayList<Integer>> num2adjNums = new ArrayList<ArrayList<Integer>>(N+1);
		for(int i = 0; i <= N; i++) num2adjNums.add(new ArrayList<Integer>());
		for(int i = 0; i < E; i++) {
			num2adjNums.get(U[i]).add(V[i]);
			num2adjNums.get(V[i]).add(U[i]);
		}

		// included[num] -> T/F
		boolean[] included = new boolean[N+1];


		/*********************/
//		buildHeap(minHeap, heapSize, pos2num, num2pos);

		for(int step = 1; step <= N; step++) {
			int minNum = pos2num[1];
			included[minNum] = true;

			System.out.println("min: " + minNum + " " + num2key[minNum]);
			System.out.println();

			swap(minHeap, 1, heapSize--, pos2num, num2pos);

//			System.out.println("swap");
//			for(int i = 1; i <= N; i++)
//				System.out.println(minHeap[i].getNum() + " " + minHeap[i].getIdx() + " " + minHeap[i].getKey());
//			System.out.println();

			heapifyDown(minHeap, 1, heapSize, pos2num, num2pos);

//			System.out.println("heapify down " + heapSize);
//			for(int i = 1; i <= N; i++)
//				System.out.println(minHeap[i].getNum() + " " + minHeap[i].getIdx() + " " + minHeap[i].getKey());
//			System.out.println();

//						System.out.println("min: " + min + " " + G[min].getKey());

			for(int adjNum : num2adjNums.get(minNum)) {
				int w = weight[minNum][adjNum];

				System.out.println("adj: " + adjNum + " " + num2key[adjNum]);
				System.out.println("weight: " + w);

				if(!included[adjNum] && num2key[adjNum] > w) {
					minHeap[num2pos[minNum]] = w; // relaxation
					num2key[adjNum] = w; // relaxation

					System.out.println("-> adj: " + adjNum + " " + num2key[adjNum]);
					System.out.println();

//					System.out.println("relaxation");
//					for(int i = 1; i <= N; i++)
//						System.out.println(minHeap[i].getNum() + " " + minHeap[i].getIdx() + " " + minHeap[i].getKey());
//					System.out.println();

					heapifyUp(minHeap, num2pos[minNum], pos2num, num2pos); // heapify-up because the key is decreased by relaxation

//					System.out.println("heapify up");
//					for(int i = 1; i <= N; i++)
//						System.out.println(minHeap[i].getNum() + " " + minHeap[i].getIdx() + " " + minHeap[i].getKey());
//					System.out.println();
				}
				System.out.println();
			}
		}
		/***************************/

		int sum = 0;
		for(int key : minHeap) {
			sum += key;
		}
		System.out.println(sum);

		return sum;
	} // end Prim


	private static void buildHeap(int[] heap, int heapSize, int[] pos2num, int[] num2pos) {
		for (int i = heapSize / 2; i >= 1; i--) heapifyDown(heap, i, heapSize, pos2num, num2pos); // last node: size, parent of last node(first non-leaf): last node / 2 = size / 2:
	}


	private static void heapifyDown(int[] heap, int i, int heapSize, int[] pos2num, int[] num2pos) {
		int parent = i;
		int leftChild = i * 2;
		int rightChild = i * 2 + 1;

		if(leftChild <= heapSize && heap[parent] > heap[leftChild])
			parent = leftChild;
		if(rightChild <= heapSize && heap[parent] > heap[rightChild])
			parent = rightChild;
		if(i != parent) {
			swap(heap, i, parent, pos2num, num2pos);
			heapifyDown(heap, parent, heapSize, pos2num, num2pos);
		}
	}

	private static void heapifyUp(int[] heap, int i, int[] pos2num, int[] num2pos) {
		int parent = i / 2;

		if(i > 1 && heap[i] < heap[parent]) {
			swap(heap, i, parent, pos2num, num2pos);
			heapifyUp(heap, parent, pos2num, num2pos);
		}
//		while(i > 1 && heap[i] < heap[parent]) {
//			swap(heap, i, parent);
//			i = parent;
//			parent = i / 2;
//		}
	}


	private static void swap(int[] heap, int a, int b, int[] pos2num, int[] num2pos) {
		int temp = heap[a];
		heap[a] = heap[b];
		heap[b] = temp;


		temp = pos2num[a];
		pos2num[a] = pos2num[b];
		pos2num[b] = temp;

		int x = pos2num[a];
		int y = pos2num[b];
		temp = num2pos[x];
		num2pos[x] = num2pos[y];
		num2pos[y] = temp;
	}
}

/**
 * 1. Prim: weight 음수로 만들지 말고 maxheap으로 구현하기. ArrayList 안쓰도록 수정하기.
 * 테스트케이스1 강의자료 답48
 * 7 9
 * 1 2 8 2 3 10 4 5 7 5 6 8 6 1 11 1 7 9 3 7 5 4 7 12 6 7 13
 * 테스트케이스2 https://ratsgo.github.io/data%20structure&algorithm/2017/11/28/MST/ 답37
 * 9 14
 * 1 2 4 2 3 8 3 4 7 4 5 9 5 6 10 6 7 2 7 8 1 8 1 8 8 2 11 8 9 7 7 9 6 3 9 2 3 6 4 4 6 14
 *
 * 2. Kruskal: 구현하기
*/

