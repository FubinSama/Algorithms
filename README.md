# [wfb's Coursea Algorithms的编程题](https://www.coursera.org/learn/algorithms-part1)

1. [Union-Find: percolation](./1$percolation/percolation.zip): 用github上的algs4.jar会发现connection方法已过时，让你用两个find方法自己实现。但是给出的课程一里面的这个方法是没被标记为过时的，而且如果直接用两个find实现，会导致OperationCountLimitExceededException Number of calls to methods in WeightedQuickUnionUF exceeds limit: 250000000，因为这个导致只有98分，调了好长时间BUG。。。
2. [Deques and Randomized Queues](./2$queues/queues.zip)：别忘了在删到容量只用了1/4时，进行数组的缩小，否则只能得99分
3. [Collinear Points](./3$collinear/collinear.zip)：要求不能改变原来数组的数据，所以要copy一份，再进行操作
4. [8 Puzzle](./4$8puzzle/8puzzle.zip)：Um...内存超限，各种改最后发现是一个类数组没加static关键字。另外twin要求随便的两个非0位置交换就可以，但是要多次调用返回相同的值。