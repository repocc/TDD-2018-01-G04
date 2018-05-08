(ns db.snapshot-model)
(use 'db.base-model)

(defn db-store-snapshot [snapshot] (db-store :snapshot snapshot))
(defn db-find-all-snapshot [] (db-find-all :snapshot))