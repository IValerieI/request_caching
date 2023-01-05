package uni2022.samarchenko_v_l.task_4.treerb;


import java.awt.*;

public class TreeRB<V extends Comparable<V>> implements ITreeRB<V>{


    class NodeRB {

        private V value;
        // red - true, black - false
        private boolean color;
        NodeRB parent;
        NodeRB left;
        NodeRB right;

        public NodeRB(V value, boolean color, NodeRB parent, NodeRB left, NodeRB right) {
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public NodeRB(V value, NodeRB parent) {
            this(value, false, null, null, parent);
        }

        public NodeRB(V value) {
            this(value, null);
        }

        public V getValue() {
            return value;
        }

        public NodeRB getLeft() {
            return left;
        }

        public NodeRB getRight() {
            return right;
        }

        public Color getColor() {
            return color == true ? Color.RED : Color.BLACK;
        }

    }

    NodeRB root = null;
    private int size = 0;

    public NodeRB getRoot() {
        return root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {return size() <= 0;}

    public NodeRB getNode(NodeRB node, V value) {
        if (node == null) {
            return null;
        }
        int cmp = node.getValue().compareTo(value);
        if (cmp == 0) {
            return node;
        } else if (cmp > 0) {
            return getNode(node.getLeft(), value);
        } else {
            return getNode(node.getRight(), value);
        }
    }


    public V get(V value) {
        NodeRB valueNode = getNode(getRoot(), value);
        return (valueNode == null) ? null : valueNode.getValue();
    }

    private NodeRB getMinNode(NodeRB node) {
        if (node == null || node.getLeft() == null) {
            return node;
        }
        return getMinNode(node.getLeft());
    }

    public boolean contains(V value) {
        return getNode(getRoot(), value) != null;
    }

    // put and next correct
    public V put(V value) {
        // 1) первый узел - корень
        if (root == null) {
            setRoot(new NodeRB(value));
            size++;
            return null;
        }
        // 2) узел не первый, корень уже есть
        NodeRB node = root;
        while (true) {
            // сравниваем добавляемое значение со значением в корне
            int cmp = value.compareTo(node.value);
            // добавляемое значение равно значению в корне
            // просто обновляем значение и возвращаем null
            if (cmp == 0) {
                V prev = node.value;
                node.value = value;
                return prev;
            } else if (cmp < 0) {
                // если нет левого узла, то создаем его и корректируем дерево
                if (node.left == null) {
                    setLeft(node, new NodeRB(value));
                    size++;
                    //correct
                    return null;
                }
                // если лев узел есть, то идем к нему
                node = node.left;
            } else {
                // аналогично для правого
                if (node.right == null) {
                    setRight(node, new NodeRB(value));
                    size++;
                    //correct
                    return null;
                }
                node = node.right;
            }
        }
    }

    public void correctAfterPut(NodeRB node) {
        // 1 - перекраска узла в красный, т.к. не сделали этого в put
        if (node != null) {
            node.color = true;
        }
        // при добавлении могут быть нарушены 2/5 св-тв (красный корень и два подряд красных узла)
        // red - true, black - false

        // 2 - родитель-красный
        if (node != null && node != root && colorOf(node.parent)) {

            // 2a - еще перекраска, проверка на необходимость еще изменений
            // если дед - не корень, а дядя - красный
            if (isRed(siblingOf(parentOf(node)))) {
                setColor(parentOf(node), false);
                setColor(siblingOf(parentOf(node)), false);
                setColor(grandparentOf(node), true);
                correctAfterPut(grandparentOf(node));


                // случай, когда дядя - черный, а parent в левом поддереве
                // 2b - если еще node левый ребенок, то один Правый поворот
                // если node правый ребенок, то Левый-Правый поворот
            } else if (parentOf(node) == leftOf(grandparentOf(node))) {
                if (node == rightOf(parentOf(node))) {
                    leftRotate(node = parentOf(node));
                }
                setColor(parentOf(node), false);
                setColor(grandparentOf(node), true);
                rightRotate(grandparentOf(node));

                // аналогичный предыдущему случай, но теперь
                // родитель правый потомок
                // 2с - если еще node правый ребенок, то один Левый поворот
                // если node левый ребенок, то Правый-Левый поворот
            } else if (parentOf(node) == rightOf(grandparentOf(node))) {
                if (node == leftOf(parentOf(node))) {
                    rightRotate(node = parentOf(node));
                }
                setColor(parentOf(node), false);
                setColor(grandparentOf(node), true);
                leftRotate(grandparentOf(node));

            }
        }
        // меняем цвет корня
        setColor(root, false);
    }


    // remove and next correct
    public V remove(V value) {
        NodeRB node = getNode(getRoot(), value);
        if (node == null) {
            // в дереве не оказалось такого узла
            return null;
        }
        // два потомка
        V oldValue = node.value;
        if (node.left != null && node.right != null) {
            // меняем удаляемый узел на узел с мин знач из
            // правого поддерева
            // у нового узла всего один ребенок
            NodeRB nextValueNode = getMinNode(node.right);
            node.value = nextValueNode.value;
            node = nextValueNode;
        }
        // один потомок (или нет потомков)
        NodeRB child = (node.left != null) ? node.left : node.right;
        if (child != null) {
            if (node == root) {
                setRoot(child);
                root.color = false;
            } else if (node.parent.left == node) {
                // child - left from parent
                setLeft(node.parent, child);
            } else {
                // child - right from parent
                setRight(node.parent, child);
            }

            if (!node.color) {
                // нарушили черную высоту удалением 1 черного узла
                correctAfterRemove(child);
            }
        } else if (node == root) {
            root = null;
        } else {
            // нарушили черную высоту удалением 1 черного узла
            if (!node.color) {
                correctAfterRemove(node);
            }
            removeFromParent(node);
        }
        size--;
        return oldValue;
    }

    private void correctAfterRemove(NodeRB node) {
        while (node != root && isBlack(node)) {
            if (node == leftOf(parentOf(node))) {
                // Pulled up node is a left child
                // брат
                NodeRB sibling = rightOf(parentOf(node));
                if (isRed(sibling)) {
                    // брат станет черным
                    setColor(sibling, false);
                    // родитель красным
                    setColor(parentOf(node), true);
                    leftRotate(parentOf(node));
                    sibling = rightOf(parentOf(node));
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    setColor(sibling, true);
                    node = parentOf(node);
                } else {
                    if (isBlack(rightOf(sibling))) {
                        setColor(leftOf(sibling), false);
                        setColor(sibling, true);
                        rightRotate(sibling);
                        sibling = rightOf(parentOf(node));
                    }
                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), false);
                    setColor(rightOf(sibling), false);
                    leftRotate(parentOf(node));
                    node = root;
                }
            } else {
                // pulled up node is a right child
                NodeRB sibling = leftOf(parentOf(node));
                if (isRed(sibling)) {
                    setColor(sibling, false);
                    setColor(parentOf(node), true);
                    rightRotate(parentOf(node));
                    sibling = leftOf(parentOf(node));
                }
                if (isBlack(leftOf(sibling)) && isBlack(rightOf(sibling))) {
                    setColor(sibling, true);
                    node = parentOf(node);
                } else {
                    if (isBlack(leftOf(sibling))) {
                        setColor(rightOf(sibling), false);
                        setColor(sibling, true);
                        leftRotate(sibling);
                        sibling = leftOf(parentOf(node));
                    }
                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), false);
                    setColor(leftOf(sibling), false);
                    rightRotate(parentOf(node));
                    node = root;
                }
            }
        }
        setColor(node, false);
    }


    // methods to check relation
    private NodeRB parentOf(NodeRB node) {
        return node == null ? null : node.parent;
    }

    private NodeRB grandparentOf(NodeRB node) {
        return (node == null || node.parent == null) ? null : node.parent.parent;
    }

    private NodeRB siblingOf(NodeRB node) {
        return (node == null || node.parent == null)
                ? null
                : (node == node.parent.left)
                ? node.parent.right : node.parent.left;
    }

    // set methods
    private void setLeft(NodeRB node, NodeRB left) {
        if (node != null) {
            node.left = left;
            if (left != null) {
                left.parent = node;
            }
        }
    }

    private void setRight(NodeRB node, NodeRB right) {
        if (node != null) {
            node.right = right;
            if (right != null) {
                right.parent = node;
            }
        }
    }

    private void setRoot(NodeRB node) {
        root = node;
        if (node != null) {
            node.parent = null;
        }
    }

    private void setColor(NodeRB node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    private void removeFromParent(NodeRB node) {
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = null;
            } else if (node.parent.right == node) {
                node.parent.right = null;
            }
            node.parent = null;
        }
    }

    // color methods
    private boolean colorOf(NodeRB node) {
        if (node != null) {
            return node.color;
        }
        return false;
    }


    private boolean isBlack(NodeRB node) {
        return !colorOf(node);
    }

    private boolean isRed(NodeRB node) {
        return colorOf(node);
    }

    // direction methods
    private NodeRB leftOf(NodeRB node) {
        return node == null ? null : node.left;
    }

    private NodeRB rightOf(NodeRB node) {
        return node == null ? null : node.right;
    }

    // rotation methods
    private void leftRotate(NodeRB node) {
        // если нет правого потомка, то ничего не делаем
        if (node.right == null) {
            return;
        }
        NodeRB right = node.right;
        // к node вместо right вставляем левого потомка right
        setRight(node, right.left);
        // если родителя нет, то сразу right в корень
        // в обратном случае рашем right станет левым/правым ребеном
        // родителя node
        if (node.parent == null) {
            setRoot(right);
        } else if (node.parent.left == node) {
            setLeft(node.parent, right);
        } else {
            setRight(node.parent, right);
        }
        setLeft(right, node);
    }

    // симметрично предыдущему методу
    private void rightRotate(NodeRB node) {
        if (node.left == null) {
            return;
        }
        NodeRB left = node.left;
        setLeft(node, left.right);
        if (node.parent == null) {
            setRoot(left);
        } else if (node.parent.left == node) {
            setLeft(node.parent, left);
        } else {
            setRight(node.parent, left);
        }
        setRight(left, node);
    }



}

