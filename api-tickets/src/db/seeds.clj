(ns db.seeds
  (:require [rage-db.core :as rdb]))

(use 'db.db-client)

(rdb/insert db :users
  {:username "pl"
   :role  "pl"})

(rdb/insert db :users
  {:username "dev"
   :role  "dev"})

(rdb/insert db :users
  {:username "qa"
   :role  "qa"})
