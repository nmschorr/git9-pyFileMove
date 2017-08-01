package com.nmschorr;

public class testSibling {
	public static void main(String[] args) {
		System.out.println ("this is testSibling main");
		testChildONE testONEbase = new testChildONE();
		testParent newPARENT = new testParent();
		testChildTWO newPARENTfCAST = (testChildTWO) newPARENT;
		testChildTWO testCAST = (testChildTWO) testONEbase;
	}
	
}

