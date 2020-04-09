package day08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sofency
 * @date 2020/4/9 19:39
 * @package IntelliJ IDEA
 * @description
 */
public class demo1 {


    public static void main(String[] args) {
        Integer[] array = {3,2,1,3,4,6,74,3,2,121};
        demo1 demo1 = new demo1();

        System.out.println("快速排序");
        demo1.QuickSort(array,0,array.length-1);
        List<Integer> demo1s = Arrays.asList(array);
        demo1s.forEach(item -> System.out.print(item+" "));

        System.out.println("归并排序");
        int[] temp = new int[array.length];
        demo1.sort(array,0,array.length-1,temp);
        List<Integer> demo2s = Arrays.asList(array);
        demo2s.forEach(item -> System.out.print(item+" "));

        System.out.println("希尔排序");
        demo1.shellSort(array);
        List<Integer> demo3s = Arrays.asList(array);
        demo3s.forEach(item -> System.out.print(item+" "));

        System.out.println("堆排序");
        demo1.HeapSort(array);
        List<Integer> demo4s = Arrays.asList(array);
        demo4s.forEach(item -> System.out.print(item+" "));
        //堆排序
    }

    //找基准
    public int Middle(Integer[] array,int left,int right){
        int key = array[left];//将左边的数值作为基准
        int index = left;
        //基准的优化
        //从左右和中间比较找出中间的数字
        int middle = (left+right)/2;
        if (array[left]>array[middle]&&array[middle]>array[right]){
            key=array[middle];
            index=middle;
        }
        if(array[left]>array[right]&&array[middle]<array[right]){
            key=array[right];
            index=right;
        }

        while(left<right){
            while(array[right]>=key&&left<right){//从右边找比基准小的数字
                right--;
            }
            while(array[left]<=key&&left<right){//从左边找比基准大的数字
                left++;
            }
            //交换数字的大小
            if(left!=right&&left<right){
                int temp = array[left];
                array[left]=array[right];
                array[right]=temp;
            }
        }
        //将基准和找到的中间值交换
        array[index]=array[right];
        array[right] = key;
        return right;
    }

    //快速排序
    public void QuickSort(Integer[] array,int left,int right){
        //防止栈溢出 改为冒泡排序 left-right<值的时候 使用冒泡排序
        if(left>right){
            return;
        }
        int middle = Middle(array,left,right);
        QuickSort(array,left,middle-1);
        QuickSort(array,middle+1,right);
    }

    //归并排序
    public void Merge(Integer[] dst, int left,int mid, int right,int[] temp){
        int i=left;
        int j= mid+1;
        int t =0;
        while (i<=mid&&j<=right){
            if(dst[i]<=dst[j]){
                temp[t++]=dst[i++];
            }else{
                temp[t++]=dst[j++];
            }
        }
        while(i<=mid){
            temp[t++]=dst[i++];
        }
        while(j<=right){
            temp[t++]=dst[j++];
        }
        t=0;//拷贝到原来的数组中
        while(left <= right){
            dst[left++]=temp[t++];
        }
    }

    public void sort(Integer[] dst,int left,int right,int[] temp){
        if(left<right){
            int mid = (left+right)/2;
            sort(dst,left,mid,temp);
            sort(dst,mid+1,right,temp);
            Merge(dst,left,mid,right,temp);
        }
    }

    //希尔排序
    public void shellSort(Integer[] array){
        int gap = array.length/2;//设置gap的范围
        while(gap>=1){
            for(int i=0;i+gap<array.length;i++){
                if(array[i]>array[i+gap]){
                    int temp = array[i];
                    array[i]=array[i+gap];
                    array[i+gap]=temp;
                }
            }
            gap/=2;
        }
    }


    //
    public void HeapSort(Integer[] array){
        Heap heap = new Heap();
        for(int i=0;i<array.length;i++){
            heap.add(array[i]);
        }
        for(int i=0;i<array.length;i++){
            array[i]=heap.remove();
        }
    }
    class Heap{
        //堆排序使用的是完全二叉树
        public List<Integer> list = new ArrayList<>();//用数组
        public Heap() {
        }
        //添加方法
        public void add(int num){
            list.add(num);
            int currentIndex = list.size()-1;
            //向上排序
            while (currentIndex>0){
                int parentIndex = (currentIndex-1)/2;
                int parentData = list.get(parentIndex);
                int lastData = list.get(currentIndex);
                if(parentData<lastData){
                    list.set(parentIndex,lastData);
                    list.set(currentIndex,parentData);
                }else{//遇到顺序的就退出
                    break;
                }
                currentIndex=parentIndex;
            }
        }
        //删除方法
        public int remove(){//删除元素
            if(list.size()==0) return 0;
            int result = list.get(0);//获取首元素
            list.set(0,list.size()-1);//将最后一个元素放到首元素
            list.remove(list.size()-1);
            //向下排序
            //从左右孩子比较找出最大的
            int parentIndex=0;
            while(parentIndex<list.size()){
                int leftIndex =parentIndex*2+1; //左孩子
                int rightIndex = parentIndex*2+2;//右孩子
                if (leftIndex>=list.size()) break;
                int maxIndex = leftIndex;
                //将首元素和左右孩子中最大的进行交换
                if(rightIndex<list.size()){
                    if(list.get(rightIndex).compareTo(list.get(maxIndex))>0){
                        maxIndex= rightIndex;
                    }
                }
                if(list.get(parentIndex).compareTo(list.get(maxIndex))<0){
                    Integer num =  list.get(parentIndex);
                    list.set(parentIndex,list.get(maxIndex));
                    list.set(maxIndex,num);
                    parentIndex = maxIndex;
                }else{
                    break;
                }
            }
            return result;
        }
    }
}
