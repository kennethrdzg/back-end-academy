CREATE DATABASE IF NOT EXISTS SocialDB;

USE SocialDB;

DROP TABLE IF EXISTS user_data;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS post_comment;

CREATE TABLE user_data(
    userID int NOT NULL AUTO_INCREMENT,
    username VARCHAR(24) NOT NULL,
    salt VARCHAR(12) NOT NULL,
    hashed_password VARCHAR(64) NOT NULL,
    PRIMARY KEY (userID), UNIQUE (username)
) AUTO_INCREMENT=1;

CREATE TABLE post(
    postID INT NOT NULL AUTO_INCREMENT,
    userID INT NOT NULL,
    post_content VARCHAR(128) NOT NULL,
    post_timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (postID),
    FOREIGN KEY (userID) REFERENCES user_data(userID)
) AUTO_INCREMENT=1;

CREATE TABLE post_comment(
    commentID INT NOT NULL AUTO_INCREMENT,
    userID INT NOT NULL,
    postID INT NOT NULL,
    comment_content VARCHAR(128) NOT NULL,
    comment_timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (commentID),
    FOREIGN KEY (userID) REFERENCES user_data(userID),
    FOREIGN KEY (postID) REFERENCES post(postID)
) AUTO_INCREMENT=1;
