package com.mhdt.dataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;


/**
 * 
 *<pre>
 * 二叉树节点
 * 特点: 左节点始终比父节点小，右节点始终比父节点大大
 * @author 懒得出风头
 * Date: 2017年2月23日
 * Time: 下午8:41:46
 *</pre>
 */
public class BinaryElement<T>{
	T root;
	BinaryElement<T> leftElement;
	BinaryElement<T> rightElement;
	
	public BinaryElement(T root){
		this.root = root;
	}
	
	public void add(Comparator<T> comparator,T t){
		/** 父节点为空添加到父节点 */
		if(root==null){
			root = t;
			return;
		}
		
		/** 父节点不为空 */
		if(comparator.compare(root, t)>=0){/** 如果父节点大于t */
			if(leftElement==null){
				leftElement = new BinaryElement<T>(t);
			}else{
				leftElement.add(comparator, t);
			}
		}else{								/**如果父节点小于t*/
			if(rightElement==null){
				rightElement = new BinaryElement<T>(t);
			}else{
				rightElement.add(comparator, t);
			}
		}
		
	}
	
	public LinkedList<T> getList(){
		LinkedList<T> list = new LinkedList<T>();
		inorder(this, list);
		return list;
	}
	
	private void inorder(BinaryElement<T> element, List<T> list) {
		if(element!=null){
			inorder(element.leftElement,list);
			list.add(element.root);
			inorder(element.rightElement,list);
		}
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		TreeSet<Integer> treeSet = new TreeSet<Integer>();
		
		int n =100;
		
		/** 生成且装配测试数据数据 */
		populate(testDate(n),treeSet,list);
		long t0 = System.currentTimeMillis();
		Collections.sort(list);
		System.out.println("Collection测试排序"+n+"条数据耗时:"+(System.currentTimeMillis()-t0)+"(毫秒)");
		t0 = System.currentTimeMillis();
		treeSet.toArray();
		System.out.println("二叉树测试排序"+n+"条数据耗时:"+(System.currentTimeMillis()-t0)+"(毫秒)");
	}
	
	private static void populate(int[] testDate,TreeSet<Integer> binaryTree, List<Integer> list) {
		for(int i : testDate){
			binaryTree.add(i);
			list.add(i);
		}
	}

	private static int[] testDate(int n){
		Random  ran = new Random();
			int[]  date = new int[n];
			for(int i=0;i<n;i++){
				date[i] = ran.nextInt(100000000);
		}
			
			return date;
	}


	

	
	
}