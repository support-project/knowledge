package org.support.project.web.control.sub;

import org.support.project.web.control.service.Post;

public class TestSubControl {

	public TestSubControl() {
		System.out.println("TestSubControl コンストラクタ");
	}
	@Post
	public void testMethod() {
		System.out.println("テストメソッド実行");
	}
	
}
