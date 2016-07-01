-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 建立日期: Jun 30, 2016, 02:12 AM
-- 伺服器版本: 5.1.44
-- PHP 版本: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- 資料庫: `eshine`
--

-- --------------------------------------------------------

--
-- 資料表格式： `articles`
--

CREATE TABLE IF NOT EXISTS `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobid` int(11) NOT NULL DEFAULT '0',
  `type` varchar(100) NOT NULL,
  `author` varchar(30) NOT NULL DEFAULT '',
  `grade` int(11) NOT NULL,
  `classname` varchar(10) NOT NULL,
  `classnum` int(11) NOT NULL DEFAULT '0',
  `title` varchar(200) NOT NULL DEFAULT '',
  `email` varchar(100) NOT NULL,
  `comment` text NOT NULL,
  `filename` varchar(255) NOT NULL,
  `filesize` bigint(20) NOT NULL DEFAULT '0',
  `filetype` varchar(100) NOT NULL,
  `postdate` datetime DEFAULT NULL,
  `visible` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `exceptions`
--

CREATE TABLE IF NOT EXISTS `exceptions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uri` varchar(100) NOT NULL DEFAULT '',
  `account` varchar(20) NOT NULL DEFAULT '',
  `ipaddr` varchar(20) NOT NULL DEFAULT '',
  `exceptiontype` varchar(255) NOT NULL,
  `exception` text,
  `exceptiontime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `jobs`
--

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `niandu` int(11) NOT NULL DEFAULT '0',
  `title` varchar(200) NOT NULL,
  `comment` text NOT NULL,
  `starttime` datetime NOT NULL,
  `finishtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `upfiles`
--

CREATE TABLE IF NOT EXISTS `upfiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleid` int(11) NOT NULL DEFAULT '0',
  `filename` varchar(100) NOT NULL,
  `filesize` bigint(20) DEFAULT '0',
  `filetype` varchar(255) DEFAULT NULL,
  `binary` longblob NOT NULL,
  `hitnum` int(11) NOT NULL DEFAULT '0',
  `visible` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `NAME` (`articleid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 資料表格式： `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) NOT NULL DEFAULT '',
  `passwd` varchar(20) NOT NULL,
  `role` varchar(50) NOT NULL DEFAULT 'USER',
  `visible` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;
