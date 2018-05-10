(ns db.user-model)
(use 'db.base-model)

(defn db-find-user-by-username [username] (db-find-where :users :username username))
