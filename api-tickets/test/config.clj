(ns config
  (:require [rage-db.core :as rdb]))

(use 'utils.string-util)
(use 'db.db-client)

(defn insert-user-test []
  (rdb/insert db :users
    {:username "user-test@gmail.com"})
)

(defn insert-role-test []
  (rdb/insert db :roles
    {:id "role-test"})
)

(defn insert-ticket-type-test []
  (rdb/insert db :tickettypes
  {:id "0" :name "ticket-type-test"}))

(defn insert-project-test []
  (rdb/insert db :projects
  {:id "1" :name "project-test" :owner "user-test@gmail.com" :ticket-types [] :states [] :users []})
  (rdb/insert db :projects
  {:id "2" :name "project-test" :owner "user-test@gmail.com" :ticket-types [] :states [] :users []}))

(defn setup-test []
  (insert-user-test)
  (insert-role-test)
  (insert-ticket-type-test)
  (insert-project-test)
)

(defn wrap-setup [f]
  (setup-test)
  (f)
  ;(teardown-test)
  )
