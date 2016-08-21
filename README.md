说明
====

一个Java+MySQL的Web应用，提供简单的计数器功能。

运行
===

你需要安装java，gradle，docker和docker-compose

    cd images
    ./build.sh

访问
===

    curl http://localhost:18080/count  # 查看计数
    curl -XPOST http://localhost:18080/incr #增加计数

如果使用远程Docker，需要修改对应的IP地址。

