(ns db.rule-model
  (:require [rage-db.core :as rdb]))

(use 'db.db-client)

(defn db-find-all-rules [] (rdb/keyspace db :rules))

;;(defn db-find-rule-by-id [id] (rdb/where db :rules :id (parse-number id)))  

(defn db-store-rule [rule] (rdb/insert db :rules rule))
