package com.hust.FP_Grows;

import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Comparable<TreeNode>{
	//节点名
	private String name;
	//该节点出现的次数
	private int count;
	//节点链表（方便构建条件基）指向下一个同名节点
	private TreeNode nodeLink;
	//父亲节点
	private TreeNode parent;
	//孩子节点（因为只会在末尾添加和查询所以采用arraylist）
	private List<TreeNode> children;
	/**
	 * 
	 * @param name 不能为null
	 * @param count 节点的计数，未知时除了根节点其他的请赋值为1
	 * @param parent 可以为null
	 */
	public TreeNode(String name,int count,TreeNode parent){
		this.name = name;
		this.count = count;
		nodeLink = null;
		this.parent = parent;
		children = null;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeNode getNodeLink() {
		return nodeLink;
	}

	public void setNodeLink(TreeNode nodeLink) {
		this.nodeLink = nodeLink;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	/**
	 * 设置该节点的计数，在原有的基础上增加一个计数
	 * @param numOccur 需要增加的计数
	 */
	public void countCreamter(int numOccur){
		int count = this.getCount() + numOccur;
		this.setCount(count);
	}

	public List<TreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<TreeNode> children) {  
        this.children = children;  
    }
	/**
	 * 添加孩子节点
	 * @param child
	 */
	public void addChild(TreeNode child){
		if(this.getChildren()==null){//孩子节点为空，新建链表，加入孩子节点，再作为该节点的孩子结合  
	        List<TreeNode> list=new ArrayList<>();  
	        list.add(child);  
	        this.setChildren(list);  
	    }  
	    else{  
	        this.getChildren().add(child);  
	    }  
	}
	/**
	 * 根据节点名查找子节点
	 * @param name 节点名
	 * @return 返回子节点，若未找到返回null
	 */
	public TreeNode findChild(String name){
		List<TreeNode> children = this.getChildren();
		if(children != null){
			for (TreeNode treeNode : children) {
				if(treeNode.getName().equals(name))
					return treeNode;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "  "+this.name+"  "+this.count;
		return str;
	}
	/**
	 * 打印该节点一下的所有孩子节点
	 * @param k 第k层 一般从1开始
	 * @return
	 */
	public void printChildren(int...k){
		if(null == k ||0 == k.length){
			k[0] = 1;
		}
		System.out.print("  "+k[0]+" "+this.getName()+" "+this.getCount());
		List<TreeNode> children =  this.getChildren();
		for (TreeNode treeNode : children) {
			treeNode.printChildren(k[0]++);
		}
		System.out.println();
	}

	//使得arrays.sort()方法按照降序排序
	@Override
	public int compareTo(TreeNode node) {
		// TODO Auto-generated method stub
		int count = node.getCount();
		return count - this.getCount();
	}
	
	
}
