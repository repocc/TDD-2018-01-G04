(ns db.comment-model)
(use 'db.base-model)

(defn db-get-comments-by-ticket [ticket] (db-find-where :comments :ticket ticket))

(defn db-store-comment [comment] (db-store :comments comment))
