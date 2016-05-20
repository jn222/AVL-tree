package src;

public class AVLTree {

	protected class Node {
		protected int element, height;
		protected Node left, right;

		protected Node(int e, Node l, Node r) {
			element = e;
			left = l;
			right = r;
			height = 0;
		}
	}

	private Node root;

	public AVLTree() {
		root = null;
	}

	public int getSize() {
		return getSize(root);
	}

	private int getSize(Node r) {
		if (r == null)
			return 0;
		int size = 1;
		size += getSize(r.left);
		size += getSize(r.right);
		return size;
	}

	public void insert(int element) {
		root = insert(element, root);
	}

	private Node insert(int element, Node t) {
		if (t == null)
			return new Node(element, null, null);
		if (element < t.element)
			t.left = insert(element, t.left);
		else if (element > t.element)
			t.right = insert(element, t.right);
		return balance(t);
	}

	private int height(Node t) {
		return t == null ? -1 : t.height;
	}

	private Node balance(Node t) {
		if (t == null)
			return t;
		if (height(t.left) - height(t.right) > 1)
			if (height(t.left.left) >= height(t.left.right))
				t = rotateWithLeftChild(t);
			else
				t = doubleWithLeftChild(t);
		else if (height(t.right) - height(t.left) > 1)
			if (height(t.right.right) >= height(t.right.left))
				t = rotateWithRightChild(t);
			else
				t = doubleWithRightChild(t);
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	private Node rotateWithLeftChild(Node k2) {
		Node k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	private Node doubleWithLeftChild(Node k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	private Node rotateWithRightChild(Node k2) {
		Node k1 = k2.right;
		k2.right = k1.left;
		k1.left = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.right), k2.height) + 1;
		return k1;
	}

	private Node doubleWithRightChild(Node k3) {
		k3.right = rotateWithLeftChild(k3.right);
		return rotateWithRightChild(k3);
	}

	public void remove(int element) {
		root = remove(element, root);
	}

	private Node remove(int element, Node t) {
		if (t == null)
			return t;

		if (element < t.element)
			t.left = remove(element, t.left);
		else if (element > t.element)
			t.right = remove(element, t.right);
		else if (t.left != null && t.right != null) {
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return balance(t);
	}

	private Node findMin(Node t) {
		return t.left == null ? t : findMin(t.left);
	}

	public int[] largerElementsOnTheRight(int[] input) {
		int[] output = new int[input.length];
		for (int i = input.length - 1; i >= 0; i--) {
			output[i] = counter(input[i], root);
			insert(input[i]);
		}

		root = null;
		return output;
	}

	private int counter(int element, Node t) {
		if (t == null)
			return 0;
		if (element < t.element)
			return 1 + getSize(t.right) + counter(element, t.left);

		return counter(element, t.right);
	}
}
