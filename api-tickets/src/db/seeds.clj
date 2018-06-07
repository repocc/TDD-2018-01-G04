(ns db.seeds
  (:require [rage-db.core :as rdb]))

(use 'db.db-client)
(use 'utils.string-util)

;Roles
(rdb/insert db :roles
  {:id "pl"})

(rdb/insert db :roles
  {:id "dev"})

(rdb/insert db :roles
  {:id "qa"})

;Users
(rdb/insert db :users
  {:username "maxiejbe92@gmail.com"})

(rdb/insert db :users
  {:username "mafvidal@gmail.com"})

(rdb/insert db :users
  {:username "jbponce36@gmail.com"})


;Ticket types
(rdb/insert db :tickettypes
  {:id (uuid) :name "bug"})

(rdb/insert db :tickettypes
  {:id (uuid) :name "feature"})

(rdb/insert db :tickettypes
  {:id (uuid) :name "improvement"})
