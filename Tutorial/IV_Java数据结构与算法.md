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

## 字符串

### `CharSequence`

- `CharSequence`是一个接口

### `String`

- `String`是一个不可变类，其所有属性

### `StringBuilder`