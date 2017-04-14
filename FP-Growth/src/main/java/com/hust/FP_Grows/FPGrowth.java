package com.hust.FP_Grows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class FPGrowth {

	private int minSupport;
	
	private List<TreeNode> headerTable;

	public int getMinSupport() {
		return minSupport;
	}

	public void setMinSupport(int minSupport) {
		this.minSupport = minSupport;
	}

	public List<TreeNode> getHeaderTable(){
		return this.headerTable;
	}
	/**
	 * 根据所给数据集和最小支持度生成一颗fp树
	 * @param dataSet 数据集
	 * @param minSup 最小支持度
	 * @return
	 */
	public TreeNode createTree(List<List<String>> dataSet){
		//创建根节点
		TreeNode root = new TreeNode(null, 0, null);
		//初始化索引表
		headerTable = initHeaderTable(dataSet);
		//遍历事物集构建fp树
		for (List<String> list : dataSet) {
			//对事物数据集重排序并过滤掉非频繁集
			LinkedList<String> orderItems =resortByHeader(list, headerTable);
			updateTree(root,orderItems,headerTable);
		}
		
		return root;
	}
	
	public void updateTree(TreeNode root,LinkedList<String> orderItems,List<TreeNode> headerTable){
		if(orderItems.size()>0){
			while(orderItems.size()>0){
				//取出字符串构建节点
				String item = orderItems.poll();
				
				TreeNode node = root.findChild(item);
				if(null==node){
					node = new TreeNode(item, 1, root);
					root.addChild(node);
				}else{
					node.countCreamter(1);
				}
				
				for (TreeNode treeNode : headerTable) {
					if(item.equals(treeNode.getName())){
						TreeNode list = treeNode.getNodeLink();
						while(list!= null){
							list = list.getNodeLink();
						}
						list.setNodeLink(node);
					}
				}
				updateTree(node, orderItems, headerTable);
			}
		}
	}
	/**
	 * 根据头节点集合将事物重排序并过滤掉非频繁项
	 * @param data 一条事物
	 * @param header 头节点集合
	 * @return
	 */
	private LinkedList<String> resortByHeader(List<String> data,List<TreeNode> header){
		LinkedList<String> res = new LinkedList<String>();
		Map<Integer, String> map = new TreeMap<Integer,String>();
		for (String string : data) {
				int i = header.indexOf(string);
				if(i >= 0){
					map.put(i, string);
				}
		}
		res = (LinkedList<String>) map.values();
		return res;
	}
	
	/**
	 * 通过数据集生成头节点集合
	 * @param dataSet 数据集
	 * @return List<TreeNode> 头节点集合
	 */
	public List<TreeNode> initHeaderTable(List<List<String>> dataSet){
		List<TreeNode> header = null;
		if(dataSet.size()>0){
			header = new ArrayList<TreeNode>();
			//初始化哈希表
			Map<String, TreeNode> map = new HashMap<String,TreeNode>(initHashContains(dataSet));
			//添加节点到哈希表中
			for (List<String> strings : dataSet) {
				for (String string : strings) {
					if(map.keySet().contains(string)){
						map.get(string).countCreamter(1);
					}else{
						TreeNode node = new TreeNode(string, 1, null);
						map.put(string, node);
					}
				}
			}
			//过滤非频繁项，生成头节点链表
			Set<String> keys= map.keySet();
			for (String string : keys) {
				TreeNode node = map.get(string);
				if(node.getCount()>=minSupport)
					header.add(node);
			}
			//将头节点按counts大小降序排序
			Collections.sort(header);
		}
		return header;
	}
	
	
	/**
	 * 根据数据集得到哈希表的应该为多大
	 * @param dataSet 数据集合
	 * @return
	 */
	private int initHashContains(List<List<String>> dataSet){
		int contains = 0;
		for (List<String> strings : dataSet) {
			contains += strings.size();
		}
		contains = (int) Math.ceil(contains/0.75);
		if(contains<=16){
			return 16;
		}
		int k = (int) Math.ceil(Math.log((double)contains)/Math.log(2.0));
		int count = (int) Math.pow(2, k);
		return count;
		
	}
}
