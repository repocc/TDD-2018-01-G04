(ns db.counter-model)
(use 'db.base-model)


(defn db-store-subcounter [subcounter] (db-store :subcounters subcounter))

(defn db-find-all-subcounters [] (db-find-all :subcounters))

(defn db-drop-subcounters [] (db-drop-where :subcounters :removable true))
