import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Test {
    static final int max_n = 1000000;
    static int[] A = {2121810708, 589144711, 1842546865, 1679831951,
        1257250914, 2140803304, 1579232195, 870742547,
        1261414478, 1245282864, 1231806674, 325659098,
        635116165, 1682486637, 1475973997, 1110302022,
        1164733425, 1492704922, 1115198155, 1594228669,
            1628843257, 497322532, 1847870004, 355806083,
            1428030480, 1926752873, 1735523424, 1485188993,
            787943575, 1438144837};
    static int[] copied = new int[max_n];
    static int n;
    static int Answer1, Answer2, Answer3;
    static long start;
    static double time1, time2, time3;

    public static void main(String[] args) throws Exception {
//        BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
//        StringTokenizer stk;
//        stk = new StringTokenizer(br.readLine());
//        n = Integer.parseInt(stk.nextToken());
//        stk = new StringTokenizer(br.readLine());
//        for (int i = 0; i < n; i++) {
//            A[i] = Integer.parseInt(stk.nextToken());
//        }

        n = 30;
        System.arraycopy(A, 0, copied, 0, n);
        mergeSort3(copied, 0, n - 1);
        for(int i = 0; i < n; i++)
            System.out.print(copied[i]+" ");
    } // end main




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
            for(int i = 0 ; i < length; i++)
                System.out.print(arr[i]+" ");
            System.out.println();
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
        for(int i = 0; i < 16; i++)
            System.out.print(subLow[i]+" "+subHigh[i]+" / ");
        System.out.println();

        // fill heap
        System.out.print("fill: ");
        for(int i = 0; i < 16; i++) {
            minHeap[i] = arr[subLow[i]];
            subLow[i]++;
            from[i] = i;
        }
        for(int i = 0; i < 16; i++)
            System.out.print(minHeap[i]+"["+from[i]+"] ");
        System.out.println();

        // build heap
        System.out.print("build: ");
        buildHeap(minHeap, from);
        for(int i = 0; i < 16; i++)
            System.out.print(minHeap[i]+"["+from[i]+"] ");
        System.out.println();

        while(idx < length) {
            // delete min value
            System.out.println("min: "+minHeap[0]+"["+from[0]+"] ");
            sorted[idx++] = minHeap[0];
            int minFrom = from[0];

            // insert new one
            if(subLow[minFrom] <= subHigh[minFrom]) {
                minHeap[0] = arr[subLow[minFrom]++];

                System.out.println("new: " + arr[subLow[minFrom]]+"["+minFrom+"] ");
            }
            else {
                minHeap[0] = Integer.MAX_VALUE;
                System.out.println("new: "+Integer.MAX_VALUE+"["+minFrom+"] ");
                //minFrom = (minFrom + 1) % 16;
            }
            System.out.print("insert: ");
            for(int i = 0; i < 16; i++)
                System.out.print(minHeap[i]+"["+from[i]+"] ");
            System.out.println();

            // heapify
            heapify(minHeap, 0, from);
            System.out.print("heapify: ");
            for(int i = 0; i < 16; i++)
                System.out.print(minHeap[i]+"["+from[i]+"] ");
            System.out.println();
            System.out.println();
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


}