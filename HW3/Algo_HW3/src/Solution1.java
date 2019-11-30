import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

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
    static final int MAX_N = 1000;
	static final int MAX_E = 100000;
	static final int Div = 100000000; // 1억
	static int N, E;
	static int[] U = new int[MAX_E], V = new int[MAX_E], W = new int[MAX_E];
	static int[] Answer1 = new int[MAX_N+1];
	static int[] Answer2 = new int[MAX_N+1];
    static double start1, start2;
    static double time1, time2;


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
			   먼저 정점의 개수와 간선의 개수를 각각 N, E에 읽어들입니다.
			   그리고 각 i번째 간선의 시작점의 번호를 U[i], 끝점의 번호를 V[i]에, 간선의 가중치를 W[i]에 읽어들입니다.
			   (0 ≤ i ≤ E-1, 1 ≤ U[i] ≤ N, 1 ≤ V[i] ≤ N)
			 */
			stk = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stk.nextToken()); E = Integer.parseInt(stk.nextToken());
			stk = new StringTokenizer(br.readLine());
			for (int i = 0; i < E; i++) {
				U[i] = Integer.parseInt(stk.nextToken());
				V[i] = Integer.parseInt(stk.nextToken());
				W[i] = Integer.parseInt(stk.nextToken());
			}

            /* Problem 1-1 */
            start1 = System.currentTimeMillis();
            Answer1 = BellmanFord(1);
            time1 = (System.currentTimeMillis() - start1);

            /* Problem 1-2 */
            start2 = System.currentTimeMillis();
            Answer2 = SPFA(1);
            time2 = (System.currentTimeMillis() - start2);

            // output1.txt로 답안을 출력합니다.
			pw.println("#" + test_case);
            for (int i = 1; i <= N; i++) {
                pw.print(Answer1[i]);
                if (i != N)
                    pw.print(" ");
                else
                    pw.print("\n");
            }
            pw.println(time1);

            for (int i = 1; i <= N; i++) {
                pw.print(Answer2[i]);
                if (i != N)
                    pw.print(" ");
                else
                    pw.print("\n");
            }
            pw.println(time2);
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


	static int[] BellmanFord(int root) {
		int[] dist = new int[N+1]; // dist[0] is not used
		for(int i = 1; i <= N; i++) dist[i] = Integer.MAX_VALUE;
		dist[root] = 0;

		for(int step = 1; step <= N-1; step++) {
			boolean relaxed = false;
			for (int i = 0; i < E; i++) {
				if (dist[U[i]] == Integer.MAX_VALUE) continue;
				if (dist[V[i]] > dist[U[i]] + W[i]) {
					dist[V[i]] = dist[U[i]] + W[i]; // relaxation
					dist[V[i]] %= Div;
					relaxed = true;
				}
			}
			if(!relaxed) break;
		}

		return dist;
	} // end BF


	static int[] SPFA(int root) {
		int[] dist = new int[N+1]; // dist[0] is not used
		for(int i = 1; i <= N; i++) dist[i] = Integer.MAX_VALUE;
		dist[root] = 0;

//		Queue<Integer> q = new LinkedList<Integer>();
//		MyQueue2<Integer> q = new MyQueue2<>(N);
		MyQueue q = new MyQueue(N);

		boolean[] isInQ = new boolean[N+1];

		q.add(root); isInQ[root] = true;
		while(!q.isEmpty()) {
			int u = q.poll(); isInQ[u] = false;
			for (int i = 0; i < E; i++) {
				if (U[i] != u) continue;
				if (dist[V[i]] > dist[u] + W[i]) {
					dist[V[i]] = dist[u] + W[i]; // relaxation
					dist[V[i]] %= Div;
					if(!isInQ[V[i]]) {
						q.add(V[i]); isInQ[V[i]] = true;
					}
				}
			}
		}

		return dist;
	} // end SPFA
}



class MyQueue {
	private int[] data;
	private int rear, front;
	private final int maxSize;

	public MyQueue(int maxSize) {
		front = 0;
		rear = 0;
		this.maxSize = maxSize+1;
		data = new int[this.maxSize];
	}

	public void add(int item) {
		if(isFull()) return;
		rear = (rear + 1) % maxSize;
		data[rear] = item;
	}

	public int poll() {
		if(isEmpty()) return 0;
		front = (front + 1) % maxSize;
		int first = data[front];
		return first;
	}

	public int peek() {
		if(isEmpty()) return 0;
		int first = data[(front + 1) % maxSize];
		return first;
	}

	public boolean isEmpty() {
		return (rear == front);
	}

	public boolean isFull() {
		return ((rear + 1) % maxSize == front);
	}
}


class MyQueue2<E> {
	private E[] data;
	private int rear, front;
	private final int maxSize;

	public MyQueue2(int maxSize) {
		front = 0;
		rear = 0;
		this.maxSize = maxSize+1;
		data = (E[]) new Object[this.maxSize];
	}

	public void add(E item) {
		if(isFull()) return;
		rear = (rear + 1) % maxSize;
		data[rear] = item;
	}

	public E poll() {
		if(isEmpty()) return null;
		front = (front + 1) % maxSize;
		E first = data[front];
		return first;
	}

	public E peek() {
		if(isEmpty()) return null;
		E first = data[(front + 1) % maxSize];
		return first;
	}

	public boolean isEmpty() {
		return (rear == front);
	}

	public boolean isFull() {
		return ((rear + 1) % maxSize == front);
	}
}


/**
 * 1. Bellman-Ford: 완성
 *
 * 2. SPFA: Bellman-Ford보다 빠르게 수정하기. 일단 MyQueue가 너무 큼. 근데 Queue 라이브러리 쓰면 더 느림...
 * https://en.wikipedia.org/wiki/Shortest_Path_Faster_Algorithm
 * https://hongjun7.tistory.com/134
 *
 * 3. Yen: 구현하기
 * https://walkccc.github.io/CLRS/Chap24/Problems/24-1/
 */








