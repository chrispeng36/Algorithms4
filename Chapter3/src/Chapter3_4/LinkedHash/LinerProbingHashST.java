package Chapter3_4.LinkedHash;

import java.util.Iterator;

//基于线性探测法的散列表
public class LinerProbingHashST<Key, Value> {
    private int N;//符号表中键值对的总数
    private int M = 16;//线性探测表的大小
    private Key[] keys;
    private Value[] values;

    public LinerProbingHashST() {
        this.keys = (Key[]) new Object[M];
        this.values = (Value[]) new Object[M];

    }

    public LinerProbingHashST(int m) {
        M = m;
        this.keys = (Key[]) new Object[M];
        this.values = (Value[]) new Object[M];

    }

    private int hash(Key key) {
        return (key.hashCode() & 0xfffffff) % M;
    }

    /*
     * 增加节点，分三种情况：
     * 1.找到key相同的，就替换value(命中）
     * 2.该位置的键和被查找的键不相同（继续查找）
     * 3.插入的地方为空就直接插入（未命中）
     * */
    public void put(Key key, Value value) {
        if (N >= M / 2) {
            resize(2 * M);
        }

        int i = hash(key);//得到hash值
        //使用的是两个平行数组来实现的
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
            i = (i + 1) % M;
        }
        keys[i] = key;
        values[i] = value;
        N++;
    }

    //和put逻辑相似
    public Value get(Key key) {
        int i = hash(key);
        while (keys[i] != null) {
            if (key.equals(keys[i])) {
                return values[i];
            }
            i = (i + 1) % M;
        }
        return null;
    }

    /*
     * 先判断是否有要删除的键，没有就返回，否则
     * 先找出要删除的键，然后删除，从删除的节点的下一个开始遍历然后重新put
     * */
    public void delete(Key key) {
        if (!contains(key)) {
            return;
        }
        int i = hash(key);
        while (!keys[i].equals(key)) {
            i = (i + 1) % M;
        }
        keys[i] = null;
        values[i] = null;
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key tempKey = keys[i];
            Value tempVal = values[i];
            keys[i] = null;
            values[i] = null;
            N--;
            put(tempKey, tempVal);
            i = (i + 1) % M;
        }

        if (N > 0 && N <= M / 8) {
            resize(M / 2);
        }
    }

    private boolean contains(Key key) {
        return get(key) != null;
    }

    /*
     * resize条件（M/8<N<M/2在这个范围不resize，其他情况resize
     * new一个新的LinerProbingHashST，遍历keys，values然后put给新表
     * */
    /**risize条件保证散列表的使用率永远不会超过1/2**/
    private void resize(int cap) {
        //新建一个表，保存原表中的keys和values
        LinerProbingHashST<Key, Value> t = new LinerProbingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                t.put(keys[i], values[i]);
            }
        }
        /**扩展表的长度为两倍**/
        keys = t.keys;
        values = t.values;
        M = t.M;
        N = t.N;
    }

    public  Key[] getKeys() {
        return keys;
    }

    public Value[] getValues() {
        return values;
    }

    public Iterable<Integer> iterable(){
        return () ->{
            return new Iterator<Integer>() {
                private int index = 0;
                @Override
                public boolean hasNext() {
                    return index < M;
                }

                @Override
                public Integer next() {
                    return index++;
                }
            };
        };
    }



    public static void main(String[] args) {
        LinerProbingHashST<String, Integer> lps = new LinerProbingHashST<>(4);
        lps.put("dsf", 12);
        lps.put("xzcd", 34);
        lps.put("dsfwe", 32432);
        lps.put("dfs", 876);
        lps.put("dfs", 66);
        lps.put("1g", 876);
        lps.put("6d", 23);
        lps.put("rt2", 67);
        lps.put("r34", 563);
        lps.put("zxce", 783);
        System.out.println(lps.get("dsf"));
        System.out.println(lps.get("dsfwe"));
        System.out.println(lps.get("zxce"));
        lps.delete("zxce");
        System.out.println(lps.get("zxce"));
        Iterator<Integer> iterator = lps.iterable().iterator();
    }

}

