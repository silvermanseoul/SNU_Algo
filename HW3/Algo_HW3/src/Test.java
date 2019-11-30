public class Test {

    public static void main(String[] args) throws Exception {
        int[] x = {1,1,1,1,1,1};
        int[] i2n = {100, 4, 1, 5, 2, 3};
        int[] n2i = {100, 2, 4, 5, 1, 3};
        swap(x,1,5,i2n,n2i);
        for(int i = 1; i<=5; i++)
            System.out.print(i2n[i] + " ");
        for(int i = 1; i<=5; i++)
            System.out.print(n2i[i] + " ");


        int[] minHeap = {100, 8, 9, 11, 999, 999, 999, 0};
        int size = minHeap.length - 1;


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
