(ns config
  (:require [rage-db.core :as rdb]))

(use 'utils.string-util)
(use 'db.db-client)

(def states [{:state "open", :roles ["pm", "dev"]},{:state "in-progress", :roles ["dev"]},{:state "close", :roles ["dev"]}])

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
  {:id "1" :name "project-test" :owner "user-test@gmail.com" :ticket-types [] :states states :users []})
  (rdb/insert db :projects
  {:id "2" :name "project-test" :owner "user-test@gmail.com" :ticket-types [] :states states :users []}))


(defn insrt-ticket-test []
  (rdb/insert db :tickets 
    {:id "2" :title "test" :description "description" :type "type" :assigned "user-test@gmail.com" :project "2" :state "open"}))

(defn setup-test []
  (insert-user-test)
  (insert-role-test)
  (insert-ticket-type-test)
  (insert-project-test)
  (insrt-ticket-test)
)

(defn wrap-setup [f]
  (setup-test)
  (f)
  ;(teardown-test)
  )
