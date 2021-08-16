

- Aggregator 负责统计

- MetricsStorage  负责存储

- StasViewer 负责显示

- ConsoleReporter 和 EmailReporter 负责组装上述三个类，将获取原始数据、聚合统计、显示统计结果到终端这三个阶段的工作串联起来，定时触发执行。

除此之外，MetricsStorage、Aggregator、StatViewer 三个类的设计也符合迪米特法则。它们只与跟自己有直接相关的数据进行交互。MetricsStorage 输出的是 RequestInfo 相关数据。Aggregator 类输入的是 RequestInfo 数据，输出的是 RequestStat 数据。StatViewer 输入的是 RequestStat 数据。