package Chapter3_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 拉链法将相同的散列值放在同一个链表中
 * 查找时，首先根据散列值找到对应的链表，其次沿着链表顺序查找相应的键
 */
public class ChaningHash {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ChaningHash test = new ChaningHash();
        ListNode []node = new ListNode[10];//key%10
        test.read_file(node);
//        test.show(node);
        while(true){
            System.out.println("请选择查找方式：1.按姓名查找，2.按电话查找,（输出其他退出）");
            int choice = in.nextInt();
            if (choice == 1){
                String name = in.next();
                long startTime = System.currentTimeMillis();//计算查找所消耗的时间
                test.search1(node,name);
                long endTime = System.currentTimeMillis();
                System.out.println("本次查找消耗时间为"+(endTime-startTime)+"微秒");
            }
            else if(choice == 2){
                String number = in.next();
                long startTime = System.currentTimeMillis();
                test.search2(node,number);
                long endTime = System.currentTimeMillis();
                System.out.println("本次查找消耗时间为"+(endTime-startTime)+"微秒");
            }
            else
                break;
        }
    }

    static class ListNode{
        String name;
        String number;
        Object key;
        ListNode next;

        public ListNode() {
        }

        public ListNode(String name, String number) {
            this.name = name;
            this.number = number;
        }

        public ListNode(String name, String number, ListNode next) {
            this.name = name;
            this.number = number;
            this.next = next;
        }
    }

    public void read_file(ListNode[] node){
        File file = new File("D:\\AL4_data\\phonrNumber.txt");
        if (! file.exists()){
            System.out.println("file not exist !!!");
            System.exit(0);
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String name = scanner.next();
                String number = scanner.next();
                ListNode listNode = new ListNode(name, number);
                listNode.next = null;
                int i = (number.charAt(9) - '0') % 10;//很简单的除法取整的hash
                if (node[i] == null){
                    node[i] = listNode;
                }else {//node[i]是一个链表，下面的操作将listnode插到后面
                    listNode.next = node[i].next;
                    node[i].next = listNode;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show(ListNode[] node){
        ListNode listNode = new ListNode();
        for (int i = 0; i < 10; i++) {
            listNode = node[i];
            while (listNode != null){
                System.out.println(listNode.name + " " + listNode.number);
                listNode = listNode.next;
            }
        }
    }

    public void search1(ListNode []node, String name){//根据姓名查找
        ListNode listnode = new ListNode();
        for (int i = 0; i < 10; i++) {
            listnode = node[i];
            while (listnode != null){
                if (listnode.name.equals(name)){
                    System.out.println(listnode.name+" "+listnode.number);
                }
                listnode = listnode.next;
            }
        }
    }

    public void search2(ListNode []node, String number){//根据电话查找
        ListNode listnode = new ListNode();
        for (int i = 0; i < 10; i++) {
            listnode = node[i];
            while (listnode != null){
                if (listnode.number.equals(number)){
                    System.out.println(listnode.name+" "+listnode.number);
                }
                listnode = listnode.next;
            }
        }
    }

}
