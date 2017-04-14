package com.hust.FP_Grows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) {
		List<List<String>> list = getDataFromTxt("src/main/java/files/dataset.txt");
		FPGrowth fp = new FPGrowth();
		fp.setMinSupport(3);
		TreeNode tree = fp.createTree(list);
	//	tree.printChildren(1);
		
	}


public static List<List<String>> getDataFromTxt(String name) {
	List<List<String>> list = new ArrayList<>();
	//获取指定路径的文件
	File file = new File(name);
	System.out.println(name);
	try {
		if (file.isFile() && file.exists()) {
			//以UTF-8编码格式读取文件流
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF8");
			BufferedReader buff = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = buff.readLine()) != null) {
				if (lineTxt.isEmpty())
					continue;
				//将读取的每行内容放入文本集合中（实际每行都是一个词语）
				String[] strs = lineTxt.split(",");
				List<String> items = new ArrayList<>();
				for (String string : strs) {
					items.add(string);
				}
				list.add(items);
			}
			buff.close();
			read.close();

		} else {
			System.out.println("文件不存在！请确认输入的文件路径是否正确！");
			System.out.println(file.getAbsolutePath());	
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("文件读取出错！");
	}
	return list;
}
}
