package com.yunhua.czc.zuulfenxiang.gateway.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListUtil {
public static  <T> List<List<T>> split(LinkedList<T> resList, int count){
        
        if(resList==null ||count<1)
            return  null ;
        List<List<T>> ret=new LinkedList<>();
        int size=resList.size();
        if(size<=count){ //数据量不足count指定的大小
            ret.add(resList);
        }else{
            int pre=size/count;
            int last=size%count;
            //前面pre个集合，每个大小都是count个元素
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<count;j++){
                    itemList.add(resList.pollFirst());
                }
                ret.add(itemList);
            }
            //last的进行处理
            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(resList.pollFirst());
                }
                ret.add(itemList);
            }
        }
        return ret;
        
    }
}
