1drop database  hiphop_ghetto;
create database hiphop_ghetto;
use hiphop_ghetto;
-- 用户表
create table user (
    id bigint not null comment '用户ID(雪花算法生成)',
    username varchar(16) not null comment '用户名',
    phone varchar(20) default null comment '手机号',
    email varchar(100) default null comment '邮箱',
    password varchar(128) not null comment '(加密后)密码',
    nickname varchar(16) not null comment '用户昵称',
    sex tinyint default 0 comment '性别(0-未知 1-男 2-女)',
    avatar varchar(255) default null comment '头像url',
    background varchar(255) default null comment '背景图url',
    description varchar(60) default null comment '简介',
    birthday datetime default null comment '生日',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间',
    status tinyint not null default 1 comment '数据状态(0-禁用,1-正常)',
    primary key (id),
    unique key uk_phone (phone),
    unique key uk_email (email)
) engine = InnoDB default charset = utf8mb4;
# 评论区表
create table user_comment (
    id bigint comment '评论id(雪花算法生成)',
    user_id bigint not null comment '用户id',
    parent_id bigint not null comment '评论上级id',
    parent_type tinyint not null comment '评论上级类型 3-评论 4-话题',
    content text not null comment '评论内容',
    reply_count int default 0 comment '回复数量',
    like_count int default 0 comment '点赞数量',
    create_time datetime not null comment '创建时间',
    status tinyint not null default 1 comment '数据状态(0-禁用,1-正常)',
    primary key (id),
    unique index uk_user_parent (user_id, parent_id, parent_type),
    index idx_user_id (user_id),
    index idx_parent (parent_id, parent_type),
    constraint fk_user_comment foreign key (user_id) references user(id)
) engine = InnoDB default charset = utf8mb4;
# 点赞记录表
create table user_like (
    id bigint comment '点赞id(雪花算法生成)',
    user_id bigint not null comment '用户id',
    resource_id bigint not null comment '点赞对象id',
    resource_type tinyint not null comment '点赞对象类型',
    is_liked bool default true not null comment '是否点赞',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间',
    primary key (id),
    unique index uk_user_resource (user_id, resource_id, resource_type) ,
    index idx_like_user (user_id),
    index idx_like_resource (resource_id, resource_type),
    constraint fk_user_like foreign key (user_id) references user(id)
) engine = InnoDB default charset = utf8mb4;
# 收藏记录表
create table user_collection (
    id bigint comment '收藏id(雪花算法生成)',
    user_id bigint not null comment '用户id',
    resource_id bigint not null comment '收藏对象id',
    resource_type tinyint not null comment '收藏对象类型',
    is_collected bool default true not null comment '是否收藏',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间',
    primary key (id),
    unique index uk_user_resource (user_id, resource_id, resource_type),
    index idx_collection_user (user_id),
    index idx_collection_resource (resource_id, resource_type),
    constraint fk_user_collection foreign key (user_id) references user(id)
)engine = InnoDB default charset = utf8mb4;
# 评分表
create table user_rating (
    id bigint comment '评分id',
    user_id bigint not null comment '评分用户id',
    resource_id bigint not null comment '评分对象曲id',
    resource_type tinyint not null comment '评分对象类型',
    score tinyint not null comment '评分',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间',
    primary key (id),
    unique index uk_user_resource (user_id, resource_id, resource_type),
    index idx_user (user_id),
    index idx_resource (resource_id, resource_type),
    constraint fk_user_rating foreign key (user_id) references user(id)
) engine = InnoDB default charset = utf8mb4;
# 专辑表
create table album (
    id bigint comment '专辑id(网易云id)',
    album_name varchar(20) not null comment '# 专辑名',
    artists varchar(50) not null comment '歌手名',
    release_time date not null comment '发行日期',
    cover_url varchar(255) not null comment '专辑封面',
    description text not null comment '简介',
    avg_score tinyint default null comment '平均评分(百分制)',
    rating_count int default 0 comment '评分数量',
    collection_count int default 0 comment '收藏数量',
    comment_count int default 0 comment '评论数量',
    primary key (id)
) engine = InnoDB default charset = utf8mb4;
# 歌曲表
create table song (
                      id bigint comment '歌曲id(网易云id)',
                      song_name varchar(20) not null comment '歌曲名',
                      album_id bigint not null comment '所属专辑 Id',
                      album_name varchar(20) not null comment '所属专辑名',
                      artists varchar(100) not null comment '歌手名',
                      release_time date not null comment '发行日期',
                      duration int not null comment '时长(秒)',
                      cover_url varchar(255) not null comment '封面url',
                      avg_score tinyint default null comment '平均评分(百分制)',
                      rating_count int default 0 comment '评分数量',
                      collection_count int default 0 comment '收藏数量',
                      comment_count int default 0 comment '评论数量',
                      primary key (id),
                      index idx_song_album_id (album_id),
                      constraint fk_album_song foreign key (album_id) references album(id)
) engine = InnoDB default charset = utf8mb4;
# 话题表
create table topic (
    id bigint comment '话题id(雪花算法生成)',
    user_id bigint not null comment '发起用户id',
    title varchar(20) not null comment '标题',
    content text comment '内容',
    cover_url varchar(255) comment '封面url',
    view_count int default 0 comment '浏览量',
    comment_count int default 0 comment '评论量',
    like_count int default 0 comment '点赞量',
    create_time datetime not null comment '创建时间',
    update_time datetime not null comment '修改时间',
    status tinyint not null default 1 comment '数据状态(0-禁用,1-正常)',
    primary key (id),
    index idx_topic_user (user_id),
    constraint fk_topic_user foreign key (user_id) references user(id)
) engine = InnoDB default charset = utf8mb4;


