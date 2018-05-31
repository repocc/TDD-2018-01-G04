(ns db.db-client
  (:require [rage-db.core :as rdb]))

(def db (rdb/create "db-tickets"))
