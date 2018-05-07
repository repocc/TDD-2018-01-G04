(ns db.seeds
  (:require [rage-db.core :as rdb]))

(use 'db.db-client)

(rdb/insert db :users
  {:username "admin"
   :role  "admin"})

(rdb/insert db :users
  {:username "guest"
   :role  "guest"})
