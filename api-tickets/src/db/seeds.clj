(ns db.seeds
  (:require [rage-db.core :as rdb]))

(use 'db.db-client)
(use 'utils.string-util)

;Roles
(rdb/insert db :roles
  {:key "pl"})

(rdb/insert db :roles
  {:key "dev"})

(rdb/insert db :roles
  {:key "qa"})

;Users
(rdb/insert db :users
  {:username "maxiejbe92@gmail.com"})

(rdb/insert db :users
  {:username "mafvidal@gmail.com"})

(rdb/insert db :users
  {:username "jbponce36@gmail.com"})


;Ticket types
(rdb/insert db :tickettypes
  {:id (uuid) :key "Abierto"})

(rdb/insert db :tickettypes
  {:id (uuid) :key "En progreso"})

(rdb/insert db :tickettypes
  {:id (uuid) :key "QA"})

(rdb/insert db :tickettypes
  {:id (uuid) :key "Terminado"})
