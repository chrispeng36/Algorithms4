package Chapter2;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Sort {
    static Scanner scanner = new Scanner(System.in);
    static Comparable[] arr;//相当于在当前类内作用域下申请一个全局变量

    /**
     * 选择排序
     * @param a
     */
    public static void selectSort(Comparable[] a){
        int len = a.length;
        for (int i = 0; i < len; i++) {
            int temp_minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (less(a[j],a[temp_minIndex])) temp_minIndex = j;
            }
            //交换
            exch(a, i, temp_minIndex);
        }
    }

    /**
     * 冒泡排序
     * @param a
     */
    public static void bubbleSort(Comparable[] a){
        int len = a.length;
        for (int i = 0; i < len - 1; i++) {//外循环保证大的排序到后面，到后面的就不需要再排序了
            for (int j = 0; j < len - i - 1; j++) {
                if (less(a[j + 1], a[j]))
                    exch(a, j, j + 1);
            }
        }
    }

    /**
     * 插入排序
     * @param a
     */
    public static void insertSort(Comparable[] a){
        int len = a.length;
        Comparable cur;
        for (int i = 0; i < len - 1; i++) {
            cur = a[i + 1];
            int preIndex = i;//需要一个当前指针和前序的指针
            //前序指针不断向前，把当前值插入到顺序的位置
            while (preIndex >= 0 && less(cur, arr[preIndex])){
                arr[preIndex + 1] = arr[preIndex];
                preIndex --;
            }
            arr[preIndex + 1] = cur;
        }
    }

    /**
     * 快速排序
     * @param a
     */
    public static void quickSort(Comparable[] a){
        quickSort(a, 0, a.length - 1);
    }
    /**函数重载**/
    public static void quickSort(Comparable[] a, int left, int right){
        if (left > right) return;

        //保存基准数
        Comparable base = a[left];
        //定义变量i指向最左边
        int i = left;
        //定义j指向最右边
        int j = right;

        //当i和j不相遇
        while (i != j){
            //j从右向左检索比基准数小的，如果检索到比基准数小的就停下
            //如果检索比基准数大或者相等即继续检索
            while (!less(a[j], base) && i < j) j --;//一直找到比基准数小的
            //i从左往右检索
            while (less(a[i], base) && i < j) i ++;//一直检索到比基准数大的
            //走到这i找到了，j找到了，交换
            exch(a,i,j);//交换这俩
        }

        /**
         * while条件不成立，说明相遇了
         * 相遇了就将基准数和相遇位置的元素
         * 意思就是交换相遇位置的数和开始元素的数
         * 多出来了base这个指针，所以当重合的时候，此时这个指针是没有对应值的，这个值应该用base填充
         */
        //先把相遇位置的元素赋值给基准数位置的元素
        a[left] = a[i];
        //把基准数赋值给相遇的位置的元素
        a[i] = base;
        //此时基准数左边比它小，右边比它大
        //排基准数的左边
        quickSort(a, left, i - 1);
        quickSort(a, j + 1, right);
    }

    public static void heapSort(Comparable[] a){
        Comparable temp;
        //第一步将无序序列构成一个堆
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            adjustHeap(a, i, arr.length);
        }
        //第二步：将堆顶元素与末尾元素交换，将最大元素沉到数组末端
        //第三步：重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，
        //反复执行调整+交换步骤，直到整个序列有序
        for (int j = a.length - 1; j > 0; j--) {
            //交换
            exch(a,0, j);
            adjustHeap(a, 0, j);
        }
    }

    /**
     * 将一个数组调整成大顶堆，完成将以i对应的非叶子节点的数调整成大顶堆
     * @param a
     * @param i 非叶子结点在数组中的索引
     * @param length 表示对多少个元素进行继续调整，length是在逐渐减少的
     */
    public static void adjustHeap(Comparable[] a, int i, int length){
        Comparable temp = a[i];
        //j = i * 2 + 1指向的是i节点的左子节点
        for (int j = i * 2 + 1; j < length; j = j * 2 + 1) {
            if (j + 1 < length && less(a[j], a[j + 1])){
                //说明左子节点小于右子节点的值
                j ++;//j指向右子节点
            }
            if (! less(a[j], temp)){
                //如果子结点大于父节点
                a[i] = a[j];
                i = j;//把较大的赋值给当前的结点，然后让i指向k，继续循环比较
            }else break;
        }
        //for循环结束后，已经将以i为父节点的树的最大值放在了最顶
        a[i] = temp;//将temp值放到调整后的位置
    }

    public static void mergeSort(Comparable[] a){
        arr = mergeSort1(a);
    }
    //重载保证格式上的相等
    public static Comparable[] mergeSort1(Comparable[] a){
        if (a.length < 2) return a;
        int mid = a.length / 2;
        Comparable[] left = Arrays.copyOfRange(a, 0, mid);
        Comparable[] right = Arrays.copyOfRange(a, mid, a.length);
        return merge(mergeSort1(left), mergeSort1(right));
    }
    /**将两端排好序的数组组合成一个排序数组**/
    public static Comparable[] merge(Comparable[] left, Comparable[] right){
        Comparable[] res = new Comparable[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < res.length; index ++){
            if (i >= left.length)
                res[index] = right[j ++];
            else if (j >= right.length)
                res[index] = left[i ++];
            else if (less(right[j], left[i]))
                res[index] = right[j ++];
            else
                res[index] = left[i ++];
        }
        return res;
    }
    public static void bucketSort(Comparable[] a){
        /**这里是对输入的字符串（英文）进行排序的，
         * 只算上小写吧
         * 一共有26个**/
        //创建一个桶数组，下标表示元素，下标的对应值表示这个值出现的次数
        int[] bucketArr = new int[26];
        for (int i = 0; i < a.length; i++) {
            bucketArr[(Character) a[i] - 'a'] ++;
        }
        
        //遍历桶数组，外层循环从桶的第一位开始，内层循环遍历桶数组中下标为i的值出现的次数
        int index = 0;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < bucketArr[i]; j++) {
                a[index ++] = (char) (Integer.valueOf('a') + i);
            }
        }
    }

    /**
     * 希尔排序
     * @param a
     */
    public static void shellSort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;
        while (h >= 1){
            //执行插入排序，将a[i]插入到a[i - h], a[i - 2 * h], a[i - 3 * h]...中
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    /**
     * 基数排序
     * @param a
     */
    public static void radixSort(Comparable[] a){
        if (a == null) return;
        int maxLen = 0;//只记录最大的位数
        //先获取数组的最大位数
        for (Comparable str : a) {
            if (str.toString().length() > maxLen)
                maxLen = str.toString().length();
        }
        //空间换时间，大桶套小桶
        ArrayList<ArrayList<String>> buckets = null;
        //从最后一个字母开始排序，低位开始排
        for (int i = maxLen - 1; i >= 0; i--) {
            //声明27个桶
            buckets = new ArrayList<ArrayList<String>>();
            for (int j = 0; j < 27; j++) {
                buckets.add(new ArrayList<String>());
            }
            //排序，主要需要考虑到桶的容量
            for (Comparable str : a) {//遍历所有的字符串，分别放入对应的桶内，按照最后一个字母的单词index，再依次往前
                buckets.get(getIndex(str.toString(), i)).add(str.toString());
            }
            //重新赋值，每针对一位都需要重新调整
            int index = 0;
            for (ArrayList<String> bucket : buckets) {
                for (String str : bucket) {
                    a[index ++] = str;
                }
            }
        }
    }

    public static int getIndex(String str, int charIndex){
        /**将字符对应的ASCII码映射到0-26的范围，其中26是其他字符**/
        if (charIndex >= str.length()) return 0;//第0个桶存非零字母情况
        int index = 26;//还有非字母项，都丢到最后了
        int n = (int) str.charAt(charIndex);//字母转数字
        if (64 < n && n < 91)
            index = n - 64;//大写字母区间
        else if (96 < n && n < 123)
            index = n - 96;//小写字母区间
        else
            index = 26;//其余非字母的排在最后
        return index;
    }
    public static void countSort(Comparable[] a){
        if (a == null || a.length < 2) return;
        int len = a.length;
        int[] countArr = new int[27];
        //统计每个值出现的次数，并放到计数数组对应的下标中
        for (int i = 0; i < len; i++) {
            countArr[(Character) a[i] - 'a'] ++;
        }
        //依次从计数数组中取出，并重新赋给a数组
        int index = 0;
        for (int i = 0; i < len + 1; i++) {
            while (countArr[i] -- > 0)
                a[index ++] = (char) (Integer.valueOf('a') + i);
        }
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] =t;
    }

    private static void show(Comparable[] a){
        //在单行中打印数组
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
        System.out.println();
    }

    private static void showStr(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a){
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    public static void getData(int caseIndex){
        if (caseIndex < 9 || caseIndex == 10){
            System.out.println("请输入待排序的字符串: ");
            String s = scanner.nextLine();
            arr = new Comparable[s.length()];
            for (int i = 0; i < s.length(); i++) {
                arr[i] = s.charAt(i);
            }
        }
        if (caseIndex == 9){
            //基数排序，可以测试一下其排序用在字符串上
            System.out.println("请输入要排序的字符串：（以空格分隔）");
            String s = scanner.nextLine();
            arr = s.split(" ");
        }
    }

    public static void main(String[] args) {

        System.out.println("选择排序的方式：\n" +
                "1: 选择排序\n" + "2: 冒泡排序\n" + "3: 插入排序\n" +
                "4: 快速排序\n" + "5: 堆排序\n" + "6: 归并排序\n" +
                "7: 桶排序\n" + "8: 希尔排序\n" + "9: 基数排序\n" + "10: 计数排序\n");
        int sortWay = Integer.parseInt(scanner.nextLine());
        getData(sortWay);

        switch (sortWay){
            case 1:
                selectSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;

            case 2:
                bubbleSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;

            case 3:
                insertSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;

            case 4:
                quickSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;
            case 5:
                heapSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;

            case 6:
                mergeSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;

            case 7:
                bucketSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;

            case 8:
                shellSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;

            case 9:
                radixSort(arr);
                showStr(arr);
                System.out.println("排序完毕");
                break;

            case 10:
                countSort(arr);
                show(arr);
                System.out.println("排序完毕");
                break;
        }
    }
}
