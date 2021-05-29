#Set和List重要区别: 不重复

| 实现 | 原理 | 特点|
|---|---|---|
|HashSet| 基于HashMap实现 |非线程安全
|CopyOnWriteArraySet| 基于 CopyOnWriteArrayList |线程安全
|ConcurrentSkipListSet| 基于ConcurrentSkipListMap| 线程安全,有序,查询快