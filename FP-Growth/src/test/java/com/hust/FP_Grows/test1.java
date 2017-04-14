package com.hust.FP_Grows;

import java.util.ArrayList;

import org.junit.Test;

public class test1 {
	@Test
	public void test1(){
		TreeNode tree = new TreeNode("11", 1, null);
		test2(tree);
		System.out.println(tree.getChildren());
	}
	
	public void test2(TreeNode i){
		i.setName("22");
		i.setChildren(new ArrayList<>());
		
	}
}
