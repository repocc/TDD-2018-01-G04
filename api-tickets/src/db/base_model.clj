(ns db.base-model
  (:require [rage-db.core :as rdb]))

(use 'utils.string-util)
(use 'db.db-client)

(defn db-find-all [keyword] (rdb/keyspace db keyword))

(defn db-count-all [keyword] (rdb/size db keyword))

(defn db-find-where [keyword field value] (rdb/where db keyword field value))  

(defn db-drop-where [keyword field value] (rdb/drop-where db keyword field value))  

(defn db-store [keyword model] (rdb/insert db keyword model))

