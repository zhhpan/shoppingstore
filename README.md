#  在线网站接口文档

## 一. 数据库创建



```mysql
create schema store_user;
CREATE TABLE user (
    user_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,          -- 买家ID，主键
    user_name VARCHAR(50)  NOT NULL,              -- 用户名
    password VARCHAR(50)  NOT NULL,              -- 密码
    user_phonenumber VARCHAR(11),  DEFAULT NULL              -- 手机号  
);
create table address
(
    id      int auto_increment comment '地址id',
    linkman varchar(30) not null comment '联系人',
    phone   varchar(30) not null comment '手机号',
    address varchar(30) null comment '地址',
    user_id int         not null comment '用户id',
    constraint id
        primary key (id),
    constraint user_id
        foreign key (user_id) references user (user_id)
);

create schema store_category;

create table category
(
    category_id   int  not null
        primary key,
    category_name char(10) not null
);

create schema store_product;

create table product
(
    product_id            int auto_increment,
    product_name          char(100)              not null,
    category_id           int                    not null,
    product_title         char(30)               not null,
    product_intro         text                   not null,
    product_picture       char(200) default NULL null,
    product_price         double                 not null,
    product_selling_price double                 not null,
    product_num           int                    not null,
    product_sales         int                    not null,
    constraint product_id
        primary key (prodect_id),
    constraint product_id
        unique (prodect_id),
    constraint product_st__fk
        foreign key (category_id) references store_category.category (category_id)
);
ALTER TABLE product
ADD FULLTEXT INDEX `full_text_index` (product_name,product_title,product_intro) VISIBLE;

create schema store_collect;

create table collect
(
    id           int auto_increment
        primary key,
    user_id      int    not null,
    collect_time bigint not null,
    product_id   int    not null,
    constraint collect_product_product_id_fk
        foreign key (product_id) references store_product.product (product_id),
    constraint collect_user_user_id_fk
        foreign key (user_id) references store_user.user (user_id)
);

create schema store_cart;
create table cart
(
    id         int auto_increment,
    user_id    int           not null,
    product_id int           not null,
    num        int default 1 not null,
    constraint table_name_pk
        primary key (id),
    constraint table_name_product_product_id_fk
        foreign key (product_id) references store_product.product (product_id),
    constraint table_name_user_user_id_fk
        foreign key (user_id) references store_user.user (user_id)
);


create table orders
(
    id            int auto_increment,
    order_id      bigint not null,
    user_id       int    not null,
    product_id    int    not null,
    product_num   int    not null,
    product_price double not null,
    order_time    bigint null,
    constraint orders_pk
        primary key (id),
    constraint orders_product_product_id_fk
        foreign key (product_id) references store_product.product (product_id),
    constraint orders_user_user_id_fk
        foreign key (user_id) references store_user.user (user_id)
);
```

## 二. 需求接口说明

#### 1. 网关接口 --3000

​	所有请求通过3000接口进行数据传递。不向外暴露其他接口

#### 2. 用户接口

##### 2.1 用户账号服务

- 源码位置：store-front-user/src/main/java/org/spm/controller/UserController.java

- 实现功能：用户账号检测是否可用、用户注册、用户登录

###### 2.1.1 用户账号检测是否可用

- 请求URL：/user/check
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数     | 是否必选 | 类型   | 说明   |
| -------- | -------- | ------ | ------ |
| userName | 是       | String | 用户名 |

- 返回结果（JSON）：

如果用户名为空，返回

```json
{
    "code" : "004",
    "msg" : "Account is null, not available"
}
```

如果用户名已经存在，返回：

```json
{
    "code" : "004",
    "msg" : "Account already exists, unable to register"
}
```

其他情况，返回：

```json
{
    "code" : "004",
    "msg" : "Can register"
}
```



###### 2.1.2 用户注册

- 请求URL：/user/register
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数            | 是否必选 | 类型   | 说明   |
| --------------- | -------- | ------ | ------ |
| userName        | 是       | String | 用户名 |
| password        | 是       | String | 密码   |
| userPhonenumber | 是       | String | 手机号 |

- 返回结果（JSON）：

参数中某一项为空，返回

```json
{
    "code" : "004",
    "msg" : "Not filled out, not available"
}
```

账号已经存在，返回

```json
{
    "code" : "004",
    "msg" : "Account already exists, unable to register"
}
```

数据库操作失败，返回

```json
{
    "code" : "004",
    "msg" : "Registration failed, please try again later"
}
```

其他情况，返回

```json
{
    "code" : "001",
    "msg" : "Registration successfully"
}
```



###### 2.1.3 用户登录

- 请求URL：/user/login
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数     | 是否必选 | 类型   | 说明   |
| -------- | -------- | ------ | ------ |
| userName | 是       | String | 用户名 |
| password | 是       | String | 密码   |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code" : "004",
    "msg" : "Not filled out, not available"
}
```

账号和或者密码出现错误，返回

```json
{
    "code" : "004",
    "msg" : "Wrong account or password"
}
```

其他情况，返回

```json
{
    "code" : "001",
    "msg" : "Login Successfully"
}
```



##### 2.2 地址信息服务

- 源码位置：store-front-user/src/main/java/org/spm/controller/AddressController.java

- 实现功能：展示所有地址，添加地址，删除地址

###### 2.2.1 展示所有地址

- 请求URL：/user/address/list
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数    | 是否必选 | 类型 | 说明   |
| ------- | -------- | ---- | ------ |
| user_id | 是       | int  | 用户ID |

- 返回结果（JSON）：

如果参数为空，返回

```json
{
    "code": "004",
    "msg": "Parameter exception, query failure"
}
```

其他情况，返回

```json
{
    "code": "001",
    "msg": "Search Successfully",
    "data": [
        {
            "id": 1,
            "linkman": "张三",
            "phone": "123",
            "address": "北京",
            "userId": 1
        }
    ]//code中可能会返回空值，表示数据库没有该数据，但是成功查找
}
```

###### 2.2.2 添加地址

- 请求URL：/user/address/add
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数    | 是否必选 | 类型   | 说明     |
| ------- | -------- | ------ | -------- |
| linkman | 是       | String | 联系人   |
| phone   | 是       | String | 手机号   |
| address | 是       | String | 详细地址 |
| user_id | 是       | int    | 用户ID   |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Parameter exception, save failure"
}
```

user_id不满足外键要求，返回

```json
{
    "code": "004",
    "msg": "Insert address failure"
}
```

其他情况，返回

```json
{
    "code": "001",
    "msg": "Search Successfully",
    "data": [
        {
            "id": 1,
            "linkman": "张三",
            "phone": "123",
            "address": "北京",
            "userId": 1
        },
        {
            "id": 12,
            "linkman": "张三",
            "phone": "1243",
            "address": "北京",
            "userId": 1
        },
        {
            "id": 13,
            "linkman": "张三",
            "phone": "1243",
            "address": "北京",
            "userId": 1
        }
    ]//显示所有data信息（可以重复）
}
```

###### 2.2.3 删除地址

- 请求URL：/user/address/remove
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数 | 是否必选 | 类型 | 说明         |
| ---- | -------- | ---- | ------------ |
| id   | 是       | int  | 删除的地址id |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Parameter exception, remove failure"
}
```

删除失败，返回

```json
{
    "code": "004",
    "msg": "Remove address failure"
}
```

删除成功，返回

```json
{
    "code": "001",
    "msg": "Remove address successfully"
}
```

#### 3.  静态资源服务

​	静态资源要放入``store-static/src/main/resources/static``中

#### 4. 商品服务

- 源码位置：store-product/src/main/java/org/spm/controller/ProductController.java

##### 4.1 商品单类别展示热门商品

- 请求URL：/product/promo
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数         | 是否必选 | 类型   | 说明   |
| ------------ | -------- | ------ | ------ |
| categoryName | 是       | String | 类别名 |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Search failure"
}
```

在数据库中没有该类别名，返回

```json
{
    "code": "004",
    "msg": "Search category failure"
}
```

成功返回

```json
{
    "code": "001",
    "msg": "Search succssfully",
    "data": [
        {
            "product_id": 1,
            "product_name": "iPhone12",
            "category_id": "1",
            "product_title": "Apple/苹果 iPhone 12",
            "product_intro": "Apple/苹果 iPhone 12苹果12promax双卡双待iphone12promax5G手机",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2500.0,
            "product_selling_price": 2190.0,
            "product_num": 20,
            "product_sales": 8
        },
        {
            "product_id": 2,
            "product_name": "iPhone12max",
            "category_id": "1",
            "product_title": "12max",
            "product_intro": "12m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2600.0,
            "product_selling_price": 2200.0,
            "product_num": 20,
            "product_sales": 7
        },
        {
            "product_id": 6,
            "product_name": "iPhone14max",
            "category_id": "1",
            "product_title": "14max",
            "product_intro": "14m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3000.0,
            "product_selling_price": 2600.0,
            "product_num": 20,
            "product_sales": 6
        },
        {
            "product_id": 4,
            "product_name": "iPhone13max",
            "category_id": "1",
            "product_title": "13max",
            "product_intro": "13m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2800.0,
            "product_selling_price": 2400.0,
            "product_num": 20,
            "product_sales": 5
        },
        {
            "product_id": 8,
            "product_name": "iPhone15max",
            "category_id": "1",
            "product_title": "15max",
            "product_intro": "15m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3200.0,
            "product_selling_price": 2800.0,
            "product_num": 20,
            "product_sales": 4
        },
        {
            "product_id": 3,
            "product_name": "iPhone13",
            "category_id": "1",
            "product_title": "13",
            "product_intro": "13",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2700.0,
            "product_selling_price": 2300.0,
            "product_num": 20,
            "product_sales": 3
        },
        {
            "product_id": 7,
            "product_name": "iPhone15",
            "category_id": "1",
            "product_title": "15",
            "product_intro": "15",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3100.0,
            "product_selling_price": 2700.0,
            "product_num": 20,
            "product_sales": 2
        }
    ]
}
```

##### 4.2 商品多类别展示热门商品

- 请求URL：/product/hots
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数         | 是否必选 | 类型         | 说明         |
| ------------ | -------- | ------------ | ------------ |
| categoryName | 是       | List<String> | 类别名的集合 |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Search failure"
}
```

类别名集合中存在数据库没有的类型名，返回

```json
{
    "code": "004",
    "msg": "Search categories failure"
}
```

成功返回：

```json
{
    "code": "001",
    "msg": "Search succssfully",
    "data": [
        {
            "product_id": 1,
            "product_name": "iPhone12",
            "category_id": "1",
            "product_title": "Apple/苹果 iPhone 12",
            "product_intro": "Apple/苹果 iPhone 12苹果12promax双卡双待iphone12promax5G手机",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2500.0,
            "product_selling_price": 2190.0,
            "product_num": 20,
            "product_sales": 8
        },
        {
            "product_id": 2,
            "product_name": "iPhone12max",
            "category_id": "1",
            "product_title": "12max",
            "product_intro": "12m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2600.0,
            "product_selling_price": 2200.0,
            "product_num": 20,
            "product_sales": 7
        },
        {
            "product_id": 6,
            "product_name": "iPhone14max",
            "category_id": "1",
            "product_title": "14max",
            "product_intro": "14m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3000.0,
            "product_selling_price": 2600.0,
            "product_num": 20,
            "product_sales": 6
        },
        {
            "product_id": 4,
            "product_name": "iPhone13max",
            "category_id": "1",
            "product_title": "13max",
            "product_intro": "13m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2800.0,
            "product_selling_price": 2400.0,
            "product_num": 20,
            "product_sales": 5
        },
        {
            "product_id": 8,
            "product_name": "iPhone15max",
            "category_id": "1",
            "product_title": "15max",
            "product_intro": "15m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3200.0,
            "product_selling_price": 2800.0,
            "product_num": 20,
            "product_sales": 4
        },
        {
            "product_id": 3,
            "product_name": "iPhone13",
            "category_id": "1",
            "product_title": "13",
            "product_intro": "13",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2700.0,
            "product_selling_price": 2300.0,
            "product_num": 20,
            "product_sales": 3
        },
        {
            "product_id": 9,
            "product_name": "华硕",
            "category_id": "2",
            "product_title": "天选2",
            "product_intro": "华硕天选2",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 5000.0,
            "product_selling_price": 4500.0,
            "product_num": 10,
            "product_sales": 3
        }
    ]
}
```

##### 4.3 商品类别信息

- 请求URL：/product/hots
- 请求方式：POST
- 请求类型：JSON
- 请求参数：none

- 返回结果（JSON）：

数据库中无类别信息，返回

```json
{
    "code": "004",
    "msg": "The set of category types is empty"
}
```

成功查询，返回

```json
{
    "code": "001",
    "msg": "Successful query for category type collection",
    "data": [
        {
            "category_id": 1,
            "category_name": "手机"
        },
        {
            "category_id": 2,
            "category_name": "电脑"
        }
    ]
}
```

##### 4.4 类别商品信息

- 请求URL：/product/bycategory
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数        | 是否必选 | 类型  | 说明       |
| ----------- | -------- | ----- | ---------- |
| categoryID  | 是       | int[] | 类别ID集合 |
| currentPage | 是       | int   | 当前页     |
| pageSize    | 是       | int   | 每页容量   |

- 返回结果（JSON）：

如果categoryID为null或者为[]，返回

```json
{
    "code": "004",
    "msg": "Failed to search by category"
}
```

如果类别商品为空或者类别不存在，返回

```json
{
    "code": "004",
    "msg": "Set is empty"
}
```

查询成功，返回

```json
{
    "code": "001",
    "msg": "Search succssfully",
    "data": [
        {
            "product_id": 4,
            "product_name": "iPhone13max",
            "category_id": "1",
            "product_title": "13max",
            "product_intro": "13m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2800.0,
            "product_selling_price": 2400.0,
            "product_num": 20,
            "product_sales": 5
        },
        {
            "product_id": 5,
            "product_name": "iPhone14",
            "category_id": "1",
            "product_title": "14",
            "product_intro": "14",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2900.0,
            "product_selling_price": 2500.0,
            "product_num": 20,
            "product_sales": 1
        },
        {
            "product_id": 6,
            "product_name": "iPhone14max",
            "category_id": "1",
            "product_title": "14max",
            "product_intro": "14m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3000.0,
            "product_selling_price": 2600.0,
            "product_num": 20,
            "product_sales": 6
        }
    ],
    "total": 8
}
```

##### 4.5 全部类别商品信息

- 请求URL：/product/all
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数        | 是否必选       | 类型  | 说明                 |
| ----------- | -------------- | ----- | -------------------- |
| categoryID  | 是，且必须为[] | int[] | 类别ID集合，为空集合 |
| currentPage | 是             | int   | 当前页               |
| pageSize    | 是             | int   | 每页容量             |

- 返回结果（JSON）：

如果categoryID不为[]，返回

```json
{
    "code": "004",
    "msg": "Failed to search by category"
}
```

如果类别商品为空或者类别不存在，返回

```json
{
    "code": "004",
    "msg": "Set is empty"
}
```

查询成功，返回

```json
{
    "code": "001",
    "msg": "Search succssfully",
    "data": [
        {
            "product_id": 7,
            "product_name": "iPhone15",
            "category_id": "1",
            "product_title": "15",
            "product_intro": "15",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3100.0,
            "product_selling_price": 2700.0,
            "product_num": 20,
            "product_sales": 2
        },
        {
            "product_id": 8,
            "product_name": "iPhone15max",
            "category_id": "1",
            "product_title": "15max",
            "product_intro": "15m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3200.0,
            "product_selling_price": 2800.0,
            "product_num": 20,
            "product_sales": 4
        },
        {
            "product_id": 9,
            "product_name": "华硕",
            "category_id": "2",
            "product_title": "天选2",
            "product_intro": "华硕天选2",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 5000.0,
            "product_selling_price": 4500.0,
            "product_num": 10,
            "product_sales": 3
        }
    ],
    "total": 9
}
```

##### 4.6 商品详细信息

- 请求URL：/product/detail
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数      | 是否必选 | 类型 | 说明   |
| --------- | -------- | ---- | ------ |
| productID | 是       | int  | 商品ID |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Category details enquiry failed"
}
```

不存在商品id对应的信息，返回

```json
{
    "code": "004",
    "msg": "The information corresponding to the productID does not exist"
}
```

查询成功，返回

```json
{
    "code": "001",
    "msg": "search detail success",
    "data": {
        "product_id": 1,
        "product_name": "iPhone12",
        "category_id": "1",
        "product_title": "Apple/苹果 iPhone 12",
        "product_intro": "Apple/苹果 iPhone 12苹果12promax双卡双待iphone12promax5G手机",
        "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
        "product_price": 2500.0,
        "product_selling_price": 2190.0,
        "product_num": 20,
        "product_sales": 8
    }
}
```

#### 5. 商品查询

- 源码位置：store-search/src/main/java/org/spm/controller/SearchController.java

- 请求URL：/search
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数       | 是否必选 | 类型   | 说明     |
| ---------- | -------- | ------ | -------- |
| searchName | 是       | String | 搜索名称 |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Search content is empty"
}
```

搜索成功，返回

```json
{
    "code": "001",
    "msg": "Search Successfully",
    "data": [
        {
            "product_id": 1,
            "product_name": "iPhone 12",
            "category_id": "1",
            "product_title": "Apple/苹果 iPhone 12",
            "product_intro": "Apple/苹果 iPhone 12苹果12promax双卡双待iphone12promax5G手机",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2500.0,
            "product_selling_price": 2190.0,
            "product_num": 20,
            "product_sales": 8
        },
        {
            "product_id": 2,
            "product_name": "iPhone 12 max",
            "category_id": "1",
            "product_title": "12max",
            "product_intro": "12m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2600.0,
            "product_selling_price": 2200.0,
            "product_num": 20,
            "product_sales": 7
        },
        {
            "product_id": 8,
            "product_name": "iPhone 15 max",
            "category_id": "1",
            "product_title": "15max",
            "product_intro": "15m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3200.0,
            "product_selling_price": 2800.0,
            "product_num": 20,
            "product_sales": 4
        },
        {
            "product_id": 4,
            "product_name": "iPhone 13 max",
            "category_id": "1",
            "product_title": "13max",
            "product_intro": "13m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2800.0,
            "product_selling_price": 2400.0,
            "product_num": 20,
            "product_sales": 5
        },
        {
            "product_id": 6,
            "product_name": "iPhone 14 max",
            "category_id": "1",
            "product_title": "14max",
            "product_intro": "14m",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3000.0,
            "product_selling_price": 2600.0,
            "product_num": 20,
            "product_sales": 6
        },
        {
            "product_id": 3,
            "product_name": "iPhone 13",
            "category_id": "1",
            "product_title": "13",
            "product_intro": "13",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2700.0,
            "product_selling_price": 2300.0,
            "product_num": 20,
            "product_sales": 3
        },
        {
            "product_id": 7,
            "product_name": "iPhone 15",
            "category_id": "1",
            "product_title": "15",
            "product_intro": "15",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 3100.0,
            "product_selling_price": 2700.0,
            "product_num": 20,
            "product_sales": 2
        },
        {
            "product_id": 5,
            "product_name": "iPhone 14",
            "category_id": "1",
            "product_title": "14",
            "product_intro": "14",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2900.0,
            "product_selling_price": 2500.0,
            "product_num": 20,
            "product_sales": 1
        }
    ]
}
```

#### 6. 商品收藏

- 源码位置：store-search/src/main/java/org/spm/controller/SearchController.java

##### 6.1 保存收藏信息

- 请求URL：/collect/save
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数       | 是否必选 | 类型 | 说明   |
| ---------- | -------- | ---- | ------ |
| user_id    | 是       | int  | 用户ID |
| product_id | 是       | int  | 商品ID |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Parameter is empty"
}
```

收藏已经存在，返回

 ```json
{
    "code": "004",
    "msg": "Already have the collection"
}
 ```

成功收藏，返回

```json
{
    "code": "001",
    "msg": "Insert Collection Successfully"
}
```

##### 6.2 展示所有收藏信息

- 请求URL：/collect/list
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数    | 是否必选 | 类型 | 说明   |
| ------- | -------- | ---- | ------ |
| user_id | 是       | int  | 用户ID |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Parameter is empty"
}
```

user_id不存在或者没有收藏信息，返回

```json
{
    "code": "001",
    "msg": "No collect products"
}
```

成功查询，返回

```json
{
    "code": "001",
    "msg": "Search collect list successfully",
    "data": [
        {
            "product_id": 1,
            "product_name": "iPhone 12",
            "category_id": "1",
            "product_title": "Apple/苹果 iPhone 12",
            "product_intro": "Apple/苹果 iPhone 12苹果12promax双卡双待iphone12promax5G手机",
            "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "product_price": 2500.0,
            "product_selling_price": 2190.0,
            "product_num": 20,
            "product_sales": 8
        }
    ]
}
```

##### 6.3 删除收藏信息

- 请求URL：/collect/remove
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数       | 是否必选 | 类型 | 说明   |
| ---------- | -------- | ---- | ------ |
| user_id    | 是       | int  | 用户ID |
| product_id | 是       | int  | 商品ID |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Parameter is empty"
}
```

收藏表中不存在该数据，返回

```json
{
    "code": "004",
    "msg": "Dont have the collection"
}
```

删除成功，返回

```json
{
    "code": "001",
    "msg": "Remove Collection Successfully"
}
```



#### 7. 购物车功能

- 源码位置：store-cart/src/main/java/org/spm/controller/CartController.java

##### 7.1 添加购物车

- 请求URL：/collect/save
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数       | 是否必选 | 类型 | 说明   |
| ---------- | -------- | ---- | ------ |
| user_id    | 是       | int  | 用户ID |
| product_id | 是       | int  | 商品ID |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Param is null"
}
```

商品信息不存在，返回

```json
{
    "code": "004",
    "msg": "Product has been deleted and cannot be added to cart"
}
```

商品库存为0，返回

```json
{
    "code": "003",
    "msg": "Stock is 0 and cannot be purchased"
}
```

首次添加购物车，返回

```json
{
    "code": "001",
    "msg": "Shopping cart successfully added",
    "data": {
        "id": 2,
        "productID": 2,
        "productName": "iPhone 12 max",
        "productImg": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
        "price": 2200.0,
        "num": 1,
        "maxNum": 20,
        "check": false
    }
}
```

非首次添加购物车，返回

```json
{
    "code": "002",
    "msg": "Add 1 to the number of items in the shopping cart"
}
```

##### 7.2 展示购物车

- 请求URL：/collect/list
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数    | 是否必选 | 类型 | 说明   |
| ------- | -------- | ---- | ------ |
| user_id | 是       | int  | 用户ID |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Shopping cart data query failed!"
}
```

购物车为空或用户不存在，返回

```json
{
    "code": "001",
    "msg": "The shopping cart is empty",
    "data": []
}
```

成功查询，返回

```json
{
    "code": "001",
    "msg": "Database data query successful",
    "data": [
        {
            "id": 1,
            "productID": 1,
            "productName": "iPhone 12",
            "productImg": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "price": 2190.0,
            "num": 1,
            "maxNum": 20,
            "check": false
        },
        {
            "id": 2,
            "productID": 2,
            "productName": "iPhone 12 max",
            "productImg": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "price": 2200.0,
            "num": 56,
            "maxNum": 0,
            "check": false
        },
        {
            "id": 3,
            "productID": 3,
            "productName": "iPhone 13",
            "productImg": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "price": 2300.0,
            "num": 1,
            "maxNum": 19,
            "check": false
        }
    ]
}
```

##### 7.3 修改购物车

- 请求URL：/collect/update
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数       | 是否必选 | 类型 | 说明       |
| ---------- | -------- | ---- | ---------- |
| user_id    | 是       | int  | 用户ID     |
| product_id | 是       | int  | 商品ID     |
| num        | 是       | int  | 修改后数据 |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Param is null"
}
```

修改的num超过库存容量，返回

```json
{
    "code": "004",
    "msg": "Modification failed! Insufficient stock!"
}
```

成功修改，返回

```json
{
    "code": "001",
    "msg": "Modify cart quantity successfully"
}
```

##### 7.4 删除购物车

- 请求URL：/collect/remove
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数       | 是否必选 | 类型 | 说明   |
| ---------- | -------- | ---- | ------ |
| user_id    | 是       | int  | 用户ID |
| product_id | 是       | int  | 商品ID |

- 返回结果（JSON）：

参数为空，返回

```json
{
    "code": "004",
    "msg": "Param is null"
}
```

成功删除，返回

```json
{
    "code": "001",
    "msg": "Delete Shopping Cart Data Successfully!"
}
```

#### 8. 订单服务功能

##### 8.1 订单生成功能

- 请求URL：/collect/remove
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数     | 是否必选 | 类型         | 说明           |
| -------- | -------- | ------------ | -------------- |
| user_id  | 是       | int          | 用户ID         |
| products | 是       | List<CartVo> | 商品信息的集合 |

示例：

```json
{
    "user_id" : 1,
    "products" : [
        {
            "id": 2,
            "productID": 2,
            "productName": "iPhone 12 max",
            "productImg": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "price": 2200.0,
            "num": 56,
            "maxNum": 0,
            "check": false
        },
        {
            "id": 3,
            "productID": 3,
            "productName": "iPhone 13",
            "productImg": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp",
            "price": 2300.0,
            "num": 1,
            "maxNum": 19,
            "check": false
        }
    ]

}
```

- 返回结果（JSON）

保存成功，返回

```json
{
    "code": "001",
    "msg": "Order saved successfully"
}
```

##### 8.2 订单展示功能

- 请求URL：/collect/list
- 请求方式：POST
- 请求类型：JSON
- 请求参数：

| 参数    | 是否必选 | 类型 | 说明   |
| ------- | -------- | ---- | ------ |
| user_id | 是       | int  | 用户ID |

- 返回结果（JSON）

查询成功，返回

```json
{
    "code": "001",
    "msg": "Order Data Fetch Successful",
    "data": [ //同一个order_id在同一个集合中
        [
            {
                "id": 3,
                "order_id": 1709992940752,
                "user_id": 1,
                "product_id": 2,
                "product_num": 56,
                "product_price": 2200.0,
                "order_time": 1709992940752,
                "product_name": "iPhone 12 max",
                "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp"
            },
            {
                "id": 4,
                "order_id": 1709992940752,
                "user_id": 1,
                "product_id": 3,
                "product_num": 1,
                "product_price": 2300.0,
                "order_time": 1709992940752,
                "product_name": "iPhone 13",
                "product_picture": "https://gw.alicdn.com/imgextra/i1/2212470695038/O1CN01Q2z6td1n5SIq9KyTr_!!2212470695038.jpg_Q75.jpg_.webp"
            }
        ]
    ]
}
```

