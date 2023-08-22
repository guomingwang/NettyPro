# BIO
## Server
- 创建一个线程池
- 创建一个ServerSocket
- 开始循环监听连接
- 建立连接并获取客户端socket
- 从线程池中执行一个线程任务：
> - 从客户端socket获取输入流
> - 从输入流读取数据并打印【输出】
> - 关闭客户端socket
## Client
- 创建一个客户端Socket
- 从客户端socket获取输出流
- 创建一个扫描流
- 扫描流循环读取键盘输入数据，并填充到输出流【输入】

# NIO
## Server
- 创建一个selector
- 创建一个ServerSocketChannel
- ServerSocketChannel绑定端口
- ServerSocketChannel设置非阻塞
- ServerSocketChannel注册到Selector，并指定用于监听连接事件
- 开始循环监听事件
- 获取待处理事件的SelectionKey
- 遍历每一个SelectionKey
- 如果是连接事件
> - 建立连接并获取客户端socketChannel
> - 将客户端SocketChannel配置为非阻塞
> - 将客户端SocketChannel注册到Selector，并指定监听某个事件
- 如果是读事件
> - 获取客户端socketChannel
> - 从客户端socketChannel读取数据
> - 将数据写入到除了消息发送方的其它socketChannel
- 移除SelectionKey
## Client
- 创建一个客户端socketChannel
- socketChannel与远程地址建立连接
- socketChannel设置为非阻塞
- SocketChannel注册到Selector，并指定用于监听读事件
- 将数据写入socketChannel中
- 创建一个线程任务：
> - 获取待处理事件的SelectionKey
> - 如果是读事件
> - 从客户端socketChannel读取数据并打印【输出】
> - 移除SelectionKey
- 扫描流读取键盘输入并且写入到socketChannel【输入】
