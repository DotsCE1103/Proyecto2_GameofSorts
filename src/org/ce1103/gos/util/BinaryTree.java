package org.ce1103.gos.util;

import java.util.concurrent.ThreadLocalRandom;

import org.ce1103.gos.entities.DragonEnemy;

public class BinaryTree {

	private BinaryTreeNode root;
	private int nodeCount;
	public static int treeLevel;

	public BinaryTree() {
		root = null;
		treeLevel=1;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public BinaryTreeNode getRoot() {
		return root;
	}

	int max(int a, int b)  
	{  
		return (a > b) ? a : b;  
	} 

	public void inOrderTraverseTree(BinaryTreeNode focusNode) {

		if (focusNode != null) {

			inOrderTraverseTree(focusNode.left);
			inOrderTraverseTree(focusNode.right);

		}
	}

	public void preorderTraverseTree(BinaryTreeNode focusNode) {


		if (focusNode != null) {

			preorderTraverseTree(focusNode.left);
			preorderTraverseTree(focusNode.right);
		}
	}
	
	public void deleteNode(int element) {
		if(!containsElement(element)) {
		}
		else {
			root = deleteNode(root, element);
			nodeCount--;
		}
	}
	
	private BinaryTreeNode deleteNode(BinaryTreeNode currentNode, int element) {  

		if (currentNode == null)  
			return currentNode;  

		if (element < currentNode.dragon.getAge())  
			currentNode.left = deleteNode(currentNode.left, element);  

		else if (element > currentNode.dragon.getAge())  
			currentNode.right = deleteNode(currentNode.right, element);  

		else {  
			if ((currentNode.left == null) || (currentNode.right == null)) {  
				BinaryTreeNode temp = null;  
				if (temp == currentNode.left)  
					temp = currentNode.right;  
				else
					temp = currentNode.left;  

				if (temp == null) {  
					temp = currentNode;  
					currentNode = null;  
				} else 
					currentNode = temp; 
			}  
			else {  

				BinaryTreeNode temp = findMin(currentNode.right);  

				currentNode.dragon = temp.dragon;  

				currentNode.right = deleteNode(currentNode.right, temp.dragon.getAge());  
			}  
		}  

		if (currentNode == null) 	
			return currentNode;  

		updatehight(currentNode);
		return balance(currentNode);
	}  


	public BinaryTreeNode findMin(BinaryTreeNode node) {
		if (node == null)
			return null;
		else if (node.left == null)
			return node;
		else
			return findMin(node.left);
	}

	public void addNode(DragonEnemy dragon) {
		if(containsElement(dragon.getAge())) {
			dragon.setAge();
			addNode(dragon);
		}
		else {
			root = addNode(root,dragon);
			nodeCount++;
		}
	}

	private BinaryTreeNode addNode(BinaryTreeNode currentNode,DragonEnemy dragon) {

		if(isEmpty()) {

			return new BinaryTreeNode(dragon);

		}else {
			if(currentNode == null){

				return new BinaryTreeNode(dragon);


			}else if(dragon.getAge() < currentNode.dragon.getAge()) {

				currentNode.left = addNode(currentNode.left,dragon);

			}else {

				currentNode.right = addNode(currentNode.right,dragon);

			}

			updatehight(currentNode);

			return balance(currentNode);
		}

	}


	public boolean containsElement(int element) {

		BinaryTreeNode currentNode = root;
		while(true) {

			if(currentNode == null) {

				return false;

			}else {

				if(element < currentNode.dragon.getAge()) {

					currentNode = currentNode.left;

				}else if(element > currentNode.dragon.getAge()) {

					currentNode = currentNode.right;

				}else {
					return true;
				}
			}
		}
	}

	public int height() {
		if(root == null) {
			return 0;
		}
		return root.height;
	}

	public void updatehight(BinaryTreeNode node) {

		int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
		int rightNodeHeight = (node.right == null) ? -1 : node.right.height;

		node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);

		node.balanceFactor =  rightNodeHeight - leftNodeHeight;

	}

	public BinaryTreeNode balance(BinaryTreeNode node) {

		if (node.balanceFactor == -2) {

			if (node.left.balanceFactor <= 0) {

				return llCase(node);

			}else {
				return lrCase(node);
			}

		}else if (node.balanceFactor == 2) {

			if (node.right.balanceFactor >= 0) {
				return rrCase(node);
			}else {
				return rlCase(node);
			}

		}

		return node;

	}

	private BinaryTreeNode rrCase(BinaryTreeNode node) {
		return leftRotation(node);
	}


	private BinaryTreeNode llCase(BinaryTreeNode node) {
		return rightRotation(node);
	}

	private BinaryTreeNode rlCase(BinaryTreeNode node) {
		node.right = rightRotation(node.right);
		return rrCase(node);
	}

	private BinaryTreeNode lrCase(BinaryTreeNode node) {
		node.left = leftRotation(node.left);
		return llCase(node);
	}

	private BinaryTreeNode leftRotation(BinaryTreeNode node) {

		BinaryTreeNode newParent = node.right;
		node.right = newParent.left;
		newParent.left = node;

		updatehight(node);
		updatehight(newParent);

		return newParent;
	}

	private BinaryTreeNode rightRotation(BinaryTreeNode node) {

		BinaryTreeNode newParent = node.left;
		node.left = newParent.right;
		newParent.right = node;

		updatehight(node);
		updatehight(newParent);

		return newParent;
	}

}