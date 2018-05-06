(ns db.rule-model)
(use 'db.base-model)

(defn db-find-all-rules [] (db-find-all :rules))

(defn db-count-all-rules [] (db-count-all :rules))

(defn db-drop-rule-by-name [name] (db-drop-where :rules :name name))

(defn db-store-rule [rule] (db-store :rules rule))
