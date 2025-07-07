# `Java`数据结构

## 包装类

### 概念

- `java`中只有八种基础数据类型，都是全小写的，出于一切皆对象的理念和泛型参数对引用类型的需求，`java`提供了八个包装类
- 其中，`Number`是所有数值类型`Byte、Short、Integer、Long、Float、Double`的基类
- `Character`和`Boolean`对应`char`和`boolean`
- 虽说包装类是引用类型，但它们的**构造方法已经被弃用**，取而代之的是自动装箱机制：

  ```java
  Integer i = 1;                // 自动装箱, 不需要new
  Integer i = new Integer(1);   // 已被弃用
  ```

- 包装类的存储位置：
  - 当数值是一个整型且范围为`-128~127`时，`JVM`会使用**缓存池**中的数值

    ```java
    Integer i = 1;
    Integer i2 = 1;
    i == i2;            // 将返回true, 虽然i和i2都是引用, 但由于使用的是缓存池中的值, 所以i和i2指向同一份内存
    ```

  - 其余情况，每次声明包装类的对象并初始化时，`JVM`均会在堆中创建新对象
- 关于常量池的细节：要求这个类实现`Constable`接口，则`JVM`会将这个类的对象存储在常量池

  例如包装类、`String`、`Enum`等都实现了该接口

### `Number`及其子类

- `Number`是一个**抽象类**，在`java.lang`包下，常用实例方法如下：
  - `typeValue()`：将对象转化为类型为`type`的值，例如`byteValue()`等
- 整型包装类，`Integer、Short、Byte、Long`，以`Integer`为例：
  - 构造器：`Integer(String)`和`Integer(int)`

  - 常用静态成员：`MAX_VALUE`和`MIN_VALUE`

  - 常用实例方法：
    - `boolean compareTo()`：实现了`Comparable`接口

    - `String toString()`：实现了转字符串方法
  - 常用静态方法：
    - `int bitCount(int)`：返回二进制表示中`1`的个数

    - `int parseInt(String, int)`：将字符串解析为整数，第二个参数可选，表示基数

    - `Integer valueOf(String, int)`：同上，但是返回引用对象

    - `int signum(int)`：返回符号函数值

    - `String toString(int)`：将一个基础数据类型的整型转化为字符串

    - `String toHexString(int)`：转十六进制字符串，其它进制如`toBinaryString()、toOctalString()`
- 浮点数包装类，`Double、Float`，以`Double`为例：
  - 构造器类似整型类

  - 常用静态成员：
    - `MAX_VALUE`和`MIN_VALUE`
    
    - `NaN`：不是一个数

    - `POSITIVE_INFINITY`和`NEGETIVE_INFINITY`：正负无穷
  - 常用实例方法：
    - `boolean isInfinite()`：是否为无穷

    - `boolean isNaN()`：是否为`NaN`

    - `String toStirng()`：转字符串
  - 常用静态方法：
    - 同样有`parseDouble()、toString()、valueOf()`系列方法

    - 还有`isFinite()、isInfinite()、isNaN()`等判断方法

## 数组

### `[]`语法糖

- 通过`type[]`可声明一个类型为`type`的数组

  ```java
  int[] arr1 = new int[] {};
  int[] arr2 = new int[N];     // 允许N是变量, 即允许动态绑定
  ```

- 只能通过初始化设定数组具体的值，编译器自动解析数组的长度
- `length`是静态数组最重要的属性，用于获取其长度
- 由于静态数组本质是一个引用类型，一个引用可以随时更换其指向，因此二维数组每一行的元素个数可以不同：

  ```java
  int[][] a = new int[8][];    // 声明一个长度为8的二维数组, 每一行的元素个数未知(为null)
  a[0] = new int[3];           // 将int[3]赋给a[0]
  int[][] a = new int[8][9];   // 声明一个长度为8的二维数组, 每一行都是int[9]对象
  ```

### `Array`

- `java`的数组实际上是`java.lang.reflect.Array`类的对象，其构造方法是私有的

- `Array`本身并不提供更多的方法，而是归纳于集合框架里的`Arrays`类和`Collections`类

## 字符串

### `CharSequence`

- `CharSequence`(字符序列)是一个接口，字符串类均实现了该接口
- 该接口的常用方法包括：
  - `char charAt(int)`：`0-idx`地获取字符串的字符

  - `boolean isEmpty()`：判空

  - `int length()`：返回长度

  - `String toString()`：返回`String`对象

### `String`

- `String`是一个**不可变类**，其所有属性都被`final`修饰
- 和包装类的整型常量池同样道理，字符串常用所以不应该经常在堆中创建，希望它能够成为一个封装好的字面量，因此`String`变量同样有两种初始化方式：

  ```java
  String s = "123";     // 在字符串常量池中检索, 若没有则创建, 若有则指向它
  String s2 = "123";    // s == s2 将返回 true
  String s3 = new String("123");    // 单独在堆中创建, 其内容是"123"的副本, 因此 s2 == s3 将返回 false
  ```

  如果你尝试使用`String(String)`构造器而不是`String(StringBuilder)`或其它构造器，你会受到警告`Unless an explicit copy of original is needed, use of this constructor is unnecessary since Strings are immutable.`，即除非真的需要原字符串的副本，否则使用这样的构造器是不必要的，因为`String`对象是不可变的

  重新复习一下不可变对象：其属性全都被`final`修饰，只能通过用`=`赋值修改变量的指向，而不能通过该对象的方法修改其指向的值
- `String`实现了`CharSequence`接口的方法，除此之外还包括以下常用方法：
  - 构造器：接受`byte[]`或`char[]`或`StringBuilder`或`StringBuffer`或`String`参数

  - 常用实例方法：
    - `String concat(String)`：返回拼接后的字符串，和`+`语法糖效果一致

    - `boolean contains(CharSequence)`：判断是否包含目标字符序列，可以是任何实现了该接口的类

    - `boolean contentEquals(CharSequence)`：因为不止含`String`一个字符串类，`StringBuilder`等不是`String`的子类，通过`contentEquals()`可以快速判断两个类型不同的字符串是否内容相等，用`s.equals(sb.toString())`也可

    - `boolean equalsIgnoreCase(String)`：忽略大小写地判断两个`String`是否相等

    - `byte[] getBytes()`：以默认编码转换为`byte[]`，`JVM`默认编码为`UTF-8`，一些`IDE`会偷偷修改默认编码集

    - `int indexOf(s)`：找到字符或子串`s`的起始位置，找不到返回`-1`，诸如`indexOf(char)`或`indexOf(String)`，还可以加上`fromIndex`和`endIndex`参数

      需要注意的是`java`没有正整数一说，若找不到，返回`-1`，是不会像`cpp`一样等同于无穷大的

    - `int lastIndexOf()`：和`indexOf()`的区别是，从右开始找

    - `int length()`：返回长度

    - `boolean matches(String regex)`：判断字符串是否匹配正则表达式`regex`

    - `String replace(char, char)`：将所有的前者字符改为后者字符，该方法还有一个字符串替换成字符串的重载版本

    - `String[] split(String regex)`：根据正则表达式`regex`匹配字符串，并以匹配成功的子串为分隔，分割字符串

    - `String toUpperCase()`和`String toLowerCase()`

    - `String trim()`：去除前后空白(包括空格、制表符)

    - `String substring(int begin, int offset)`：获取子串`[begin, begin + offset)`

### `StringBuilder`

- 先讲一下`StringBuffer`类，这是十分古老的`java`提供的可变字符串类，是线程安全的

  但就算是线程安全的，也不能完全保证拼接的逻辑正确，反而因为加锁导致开销极大，因此基本没有适用场景
- 因此，`StringBuilder`类应运而生，它实现了`CharSequence`接口和`Appendable`接口，提供以下方法：
  - `indexOf()`系列

  - `append()`系列

  - `delete()`系列

  - `insert()`系列

  - `StringBuilder reverse()`：使该对象逆序，然后返回它自己

  - `void setCharAt(int, char)`：将前者指向的字符改为后者

## 集合框架

### `Iterable<E>`接口

- `Iterable<E>`是所有元素集合都实现了的接口
- `Iterator<E> iterator()`：获取一个迭代器
- `void forEach(Consumer<? super E>)`：为集合的每一个元素都执行一遍你提供的`lambda`表达式
- 所有实现了`Iterable`接口的类均可使用语法糖`for (type e : c)`，其中`e`是临时变量，是集合对象`c`中的元素
- `Iterator<E>`接口包含以下方法：
  - `boolean hasNext()`：判断是否有下一个元素
  - `E next()`：返回下一个元素，若没有则抛出`NoSuchElementException`异常
  - `void remove()`：用于安全地删除当前元素

    在使用迭代器遍历时，一旦通过对象进行增删行为，调用迭代器的方法将抛出异常

    因此应该使用迭代器的`remove()`删除元素

    当当前迭代器从未使用过`next()`或已经使用过`remove()`时，调用`remove()`将抛出`IllegalStateException`异常
- 通过非语法糖的方式遍历集合的方法如下(如果需要在遍历过程中删除元素)：
  
  ```java
  List<Integer> list;
  var it = list.iterator();
  while (it.hasNext()) {
    var e = it.next();
    if (e == 1) {
      it.remove();
    }
  }
  ```

- 为什么`Iterator`是一个接口，在文档中也找不到实现它的类，为什么能获取到一个实例化的对象？

  因为实现这个接口的是在各个容器内部定义的私有内部类

### `Collection<E>`接口

- `Collection<E>`接口继承自`Iterable`接口，所有元素集合都实现了该接口
- 定义的方法如下，实现它的类可以有不同的实现，但语义满足：
  - `boolean add(E)`：调用后，集合中应能够找到该元素

  - `void clear()`：调用后，集合应没有元素

  - `boolean contains(Object)`：若集合存在该对象应返回`true`

  - `boolean isEmpty()`：若集合为空应返回`true`

  - `boolean remove(Object)`：若该对象存在于集合中，调用该方法后应保证删除它

  - `int size()`：应返回集合的长度

  - `Object[] toArray()`：应返回由集合中元素构成的静态数组

### `List<E>`接口

- `List`接口继承自`Collection`接口，所有有序集合(列表)都实现了该接口
- 该接口额外包含以下常用方法：
  - `void add(int idx, E)`：向第`idx`位置插入元素，`0-idx`
  
  - `void set(int idx, E)`：设置第`idx`位置的元素

  - `E get(int)`：获取指定位置的元素

  - `E getFirst()`和`E getLast()`

  - `void remove(int)、E removeFirst()、E removeLast()`

  - `List<E> subList(int, int)`：获取子列表，以视图形式存在(没有在堆中新分配内存)
- 实现`List`接口的常用类有`ArrayList、LinkedList、Vector、Stack`，分别是数组实现、链表实现、线程安全列表、线程安全栈，其中`LinkedList`类还实现了`Deque`接口

### `Set<E>`接口

- `Set`接口同样继承自`Collection`接口，所有唯一性集合都实现了该接口
- `Set`本身没有提供更多的方法，实现它的常用类有`HashSet、TreeSet、LinkedHashSet`，分别是哈希表实现(乱序)、红黑树实现(键有序)、哈希表实现(插入顺序排序)

### `Queue<E>`和`Deque<E>`接口

- `Queue`接口同样继承自`Collection`接口，所有只操作端元素的集合都实现了该接口
- `Queue`接口提供了额外的方法，它们的功能和`Collection`接口定义的方法有对应关系，但`Queue`新增的方法在操作失败时会返回特殊值，而`Collection`的方法会抛出异常
  - `offer()`和`add()`

  - `peek()`和`element()`

  - `poll()`和`remove()`
- `Deque`接口猜都能猜到，在方法名后加`First`和`Last`
- 实现了`Queue`的常用类有`PriorityQueue`(优先队列)和`ArrayDeque`(数组实现的双端队列)

### `Map<K, V>`接口

- `Map`接口并不继承`Iterable`和`Collection`，所有映射集合都实现了该接口
- `Map`提供的常用方法有：
  - `clear()`

  - `containsKey()`和`containsValue()`

  - `V get(K)`

  - `put(K, V)`

  - `remove(Object)`
- 含有公有内部类`Entry<K, V>`
- 实现了`Map`接口的常用类有`HashMap、TreeMap、HashTable、WeakHashMap、LinkedMap`，分别是哈希表实现、红黑树实现、线程安全的哈希表实现、基于弱引用的哈希表实现、哈希表实现(插入顺序排序)

## 内置算法

### `Math`

### `Arrays`

### `Collections`